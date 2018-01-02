package estimation.service;

import estimation.DAO.RequirementDAO;
import estimation.bean.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuawai on 03/05/2017.
 */
@Service
public class RequirementService {
    @Autowired
    private RequirementDAO requirementDAO;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public String add(String userId){


        //--requirement level
        Requirement requirement = new Requirement();
        long time = System.currentTimeMillis();
        String id = String.valueOf(time);
        String createTime = LocalDateTime.now().toString();
             //--description level
        Description description = new Description();
        description.setProjectName("");
        description.setProjectDescription("");
        description.setEstimationMethod("");
        description.setProjectContact("");
        description.setProjectLeader("");
             //description level--
        List<Transaction> transactions = new ArrayList<Transaction>();
        Folder treeOfTransactions = new Folder();
        Folder childFolder = new Folder();
        treeOfTransactions.setId("00000");
        treeOfTransactions.setName("tree");
        childFolder.setId("00001");
        childFolder.setName("功能模块1");
        List<Folder> folders1 = new ArrayList<Folder>();
        List<File> files1 = new ArrayList<File>();
        List<Folder> folders = new ArrayList<Folder>();
        List<File> files = new ArrayList<File>();
        childFolder.setChildFiles(files1);
        childFolder.setChildFolders(folders1);
        folders.add(childFolder);
        treeOfTransactions.setChildFiles(files);
        treeOfTransactions.setChildFolders(folders);

        requirement.setId(id);
        requirement.setUserId(userId);
        requirement.setDescription(description);
        requirement.setTransactions(transactions);
        requirement.setState("待审核");
        requirement.setTreeOfTransactions(treeOfTransactions);
        requirement.setCreateTime(createTime);
        requirement.setRemark("");
        //requirement level--



        this.requirementDAO.add(requirement);
        //这里应该修改为，如果插入成功，则返回id
        return id;
    }

    public Requirement getRequirement(String id, String userId){
        Requirement requirement = requirementDAO.getRequirement(id);
        if(requirement.getUserId().equals(userId)){
            return requirement;
        }
        else{
            return null;
        }
    }

    public List<Requirement> getAllRequirements(String userId){
        return requirementDAO.getAllRequirements(userId);
    }
    
    public void deleteRequirement(String id, String userId) {
    	this.requirementDAO.deleteRequirement(id, userId);
    }
    
    public void changeState(String id, String userId, String state, String remark) {
    	this.requirementDAO.changeState(id, userId, state, remark);
    }

    public String getAccount(HttpServletRequest request)
    {
        java.lang.String token = request.getHeader("Authorization");
        Boolean tokenExist = stringRedisTemplate.hasKey(token);
        if (tokenExist) {
            java.lang.String username = stringRedisTemplate.opsForValue().get(token);
            return username;
        }
        else {
            return null;
        }
    }

    public boolean checkIdentity(String id, String userId){
        return requirementDAO.checkIdentity(id, userId);
    }
}
