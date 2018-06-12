package estimation.controller;

import estimation.bean.Description;
import estimation.service.DescriptionService;
import estimation.service.ManagerService;
import estimation.service.RequirementService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by xuawai on 03/05/2017.
 */
@RestController
@RequestMapping(value="/estimation")
public class DescriptionController {


    @Autowired
    private DescriptionService descriptionService;

    @Autowired
    private RequirementService requirementService;

    @Autowired
    private ManagerService managerService;

    //添加描述信息或更新现有的描述信息
    @RequestMapping(value = "/addDescription/{id}",method = RequestMethod.POST)
    public Object addDescription(HttpServletRequest request, @RequestBody JSONObject jsonObject, @PathVariable String id) {
        String userId = "@";
        try {
            userId = requirementService.getAccount(request);
        } catch (NullPointerException e){
            e.printStackTrace();
            return -1;
        }
        if(!requirementService.checkIdentity(id, userId))
            return -2;

        String projectName = jsonObject.getString("projectName");
        String projectDescription = jsonObject.getString("projectDescription");
        String projectLeader = jsonObject.getString("projectLeader");
        String projectContact = jsonObject.getString("projectContact");
        String estimationMethod = jsonObject.getString("estimationMethod");
        Description description = new Description();
        description.setProjectName(projectName);
        description.setProjectDescription(projectDescription);
        description.setProjectLeader(projectLeader);
        description.setProjectContact(projectContact);
        description.setEstimationMethod(estimationMethod);
        descriptionService.add(id, description);
        return 0;
    }
}
