package estimation.controller;


import estimation.bean.Requirement;
import estimation.service.RequirementService;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
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
    public String addRequirement(HttpServletRequest request) {
        String userId = requirementService.getAccount(request);
        String id = requirementService.add(userId);

        return id;
    }

    //返回一条记录
    @RequestMapping(value = "/getRequirement/{id}",method = RequestMethod.GET)
    public Requirement getRequirement(HttpServletRequest request, @PathVariable String id) {
        String userId = requirementService.getAccount(request);

        return requirementService.getRequirement(id, userId);
    }

    //返回所有记录
    @RequestMapping(value = "/getAllRequirements",method = RequestMethod.GET)
    public List<Requirement> getAllRequirements(HttpServletRequest request) {
        String userId = requirementService.getAccount(request);

        return requirementService.getAllRequirements(userId);
    }
    
    @RequestMapping(value = "/deleteRequirement/{id}", method = RequestMethod.POST)
    public void deleteRequirement(HttpServletRequest request, @PathVariable String id) {
        String userId = requirementService.getAccount(request);

        this.requirementService.deleteRequirement(id, userId);
    }
    
    @RequestMapping(value = "/changeState/{id}", method = RequestMethod.POST)
    public void changeState(HttpServletRequest request, @RequestBody JSONObject jsonObject,@PathVariable String id) {
        String userId = requirementService.getAccount(request);

        String state = jsonObject.getString("state");
    	String remark = jsonObject.getString("remark");
    	this.requirementService.changeState(id, userId, state, remark);
    }

}
