package estimation.controller;

import estimation.bean.Manager;
import estimation.service.ManagerService;
import estimation.service.RequirementService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : MianHong Li
 * @date : 2018/1/14
 */
@RestController
@RequestMapping(value = "/manager")
public class ManagerController {

    @Autowired
    ManagerService managerService;

    @Autowired
    RequirementService requirementService;

    @GetMapping(value = "/identity")
    public Object judgeIdentity(HttpServletRequest request){
        Map<String, Object> result = new HashMap<>();
        try{
            String username = requirementService.getAccount(request);
            if(username != null){
                result.put("code", managerService.judgeIdentity(username) ? 0 : 1);
            }
            else
                result.put("code", 200);
        }
        catch(Exception e){
            result.put("code", 500);
        }


        return result;
    }

    @PostMapping(value = "/add")
    public Object addManager(@RequestBody JSONObject jsonObject){
        Map<String, Object> result = new HashMap<>();
        try{
            String username = jsonObject.getString("username");
            if(managerService.add(username))
                result.put("code", 0);
            else
                result.put("code", 200);
        }
        catch(Exception e){
            result.put("code", 500);
        }
        return result;
    }

    @PostMapping(value = "/delete")
    public Object deleteManager(@RequestBody JSONObject jsonObject){
        Map<String, Object> result = new HashMap<>();
        try{
            String username = jsonObject.getString("username");
            if(managerService.delete(username))
                result.put("code", 0);
            else
                result.put("code", 200);
        }
        catch(Exception e){
            result.put("code", 500);
        }
        return result;
    }

    @GetMapping(value = "/getAll")
    public Object getAll(){
        Map<String, Object> result = new HashMap<>();
        try{
            List<Manager> managers = managerService.getAll();
            if(managers != null) {
                List<String> names = new ArrayList<>();
                for(Manager manager : managers){
                    names.add(manager.getUsername());
                }
                result.put("list", names);
                result.put("code", 0);
            }
            else
                result.put("code", 200);
        }
        catch(Exception e){
            result.put("code", 500);
        }
        return result;
    }

}
