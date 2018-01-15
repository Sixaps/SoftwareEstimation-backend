package estimation.controller;


import estimation.bean.Requirement;
import estimation.service.ManagerService;
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

    @Autowired
    private ManagerService managerService;

    //增加一个新需求
    @RequestMapping(value = "/addRequirement",method = RequestMethod.GET)
    public String addRequirement(HttpServletRequest request) {
        String userId = requirementService.getAccount(request);
        return requirementService.add(userId);
    }

    //返回一条记录
    @RequestMapping(value = "/getRequirement/{id}",method = RequestMethod.GET)
    public Requirement getRequirement(HttpServletRequest request, @PathVariable String id) {
        String userId = requirementService.getAccount(request);
        if(!managerService.judgeIdentity(userId) && !requirementService.checkIdentity(id, userId))
            return null;
        return requirementService.getRequirement(id);
    }

    //返回所有记录
    @RequestMapping(value = "/getAllRequirementsByUser",method = RequestMethod.GET)
    public List<Requirement> getAllRequirementsByUser(HttpServletRequest request) {
        String userId = requirementService.getAccount(request);
        return requirementService.getAllRequirementsByUser(userId);
    }

    //返回所有记录
    @RequestMapping(value = "/getAllRequirements",method = RequestMethod.GET)
    public List<Requirement> getAllRequirements(HttpServletRequest request) {
        String userId = requirementService.getAccount(request);
        if(!managerService.judgeIdentity(userId))
            return null;
        return requirementService.getAllRequirements();
    }

    @RequestMapping(value = "/deleteRequirement/{id}", method = RequestMethod.POST)
    public void deleteRequirement(HttpServletRequest request, @PathVariable String id) {
        String userId = requirementService.getAccount(request);
        if(!requirementService.checkIdentity(id, userId))
            return;

        this.requirementService.deleteRequirement(id);
    }
    
    @RequestMapping(value = "/changeState/{id}", method = RequestMethod.POST)
    public void changeState(HttpServletRequest request, @RequestBody JSONObject jsonObject,@PathVariable String id) {
        String userId = requirementService.getAccount(request);
        if(!managerService.judgeIdentity(userId))
            return;

        String state = jsonObject.getString("state");
    	String remark = jsonObject.getString("remark");
    	this.requirementService.changeState(id, state, remark);
    }

}
