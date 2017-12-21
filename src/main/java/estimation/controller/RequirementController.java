package estimation.controller;


import estimation.bean.Requirement;
import estimation.service.RequirementService;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by xuawai on 03/05/2017.
 */
@RestController
@RequestMapping(value="/estimation")
public class RequirementController {
    @Autowired
    private RequirementService requirementService;

    //增加一个新需求
    @RequestMapping(value = "/addRequirement",method = RequestMethod.GET)
    public String addRequirement(HttpServletResponse response) {
        String id = requirementService.add();
        Cookie cookie = new Cookie("RID", id);
        response.addCookie(cookie);
        return id;
    }

    //返回一条记录
    @RequestMapping(value = "/getRequirement/{id}",method = RequestMethod.GET)
    public Requirement getRequirement(@PathVariable String id) {
        return requirementService.getRequirement(id);
    }
    
    @RequestMapping(value = "/setId/{id}",method = RequestMethod.POST)
    public void setId(HttpServletResponse response, @PathVariable String id) {
    	response.addCookie(new Cookie("RID", id));
    }

    //返回所有记录
    @RequestMapping(value = "/getAllRequirements",method = RequestMethod.GET)
    public List<Requirement> getAllRequirements() {
        return requirementService.getAllRequirements();
    }
    
    @RequestMapping(value = "/deleteRequirement/{id}", method = RequestMethod.POST)
    public void deleteRequirement(@PathVariable String id) {
    	this.requirementService.deleteRequirement(id);
    }
    
    @RequestMapping(value = "/changeState/{id}", method = RequestMethod.POST)
    public void changeState(@RequestBody JSONObject jsonObject,@PathVariable String id) {
    	String state = jsonObject.getString("state");
    	String remark = jsonObject.getString("remark");
    	this.requirementService.changeState(id, state, remark);
    }

}
