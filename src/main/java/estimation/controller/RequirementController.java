package estimation.controller;


import estimation.bean.Requirement;
import estimation.service.ManagerService;
import estimation.service.RequirementService;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public Object addRequirement(HttpServletRequest request) {
        String userId = requirementService.getAccount(request);
        if(userId == null){
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<String>("",status);
        }
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
    public Object getAllRequirementsByUser(HttpServletRequest request) {
        String userId = requirementService.getAccount(request);
        if(userId == null){
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<Object>("",status);
        }
        return requirementService.getAllRequirementsByUser(userId);
    }

    //返回所有记录
    @RequestMapping(value = "/getAllRequirements",method = RequestMethod.GET)
    public Object getAllRequirements(HttpServletRequest request) {
        String userId = requirementService.getAccount(request);
        if(!managerService.judgeIdentity(userId)) {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<Object>("",status);
        }
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
    public Object changeState(HttpServletRequest request, @RequestBody JSONObject jsonObject,@PathVariable String id) {
        HttpStatus status = HttpStatus.ACCEPTED;
        String userId = requirementService.getAccount(request);
        if(!managerService.judgeIdentity(userId)) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<Object>("", status);
        }
        String state = jsonObject.getString("state");
        String[] trueState = {"待审核","待修改","完成","估算中"};
        Boolean flag = false;
        for (String str: trueState) {
            if(str.equals(state)){
                flag = true;
                break;
            }
        }
        if(!flag) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<Object>("", status);
        }
    	String remark = jsonObject.getString("remark");
    	if(!this.requirementService.changeState(id, state, remark)){
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Object>("",status);
    }
}
