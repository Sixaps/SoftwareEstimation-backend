package estimation.service;

import estimation.DAO.RequirementDAO;
import estimation.bean.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public String add(){


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

    public Requirement getRequirement(String id){
        return requirementDAO.getRequirement(id);
    }

    public List<Requirement> getAllRequirements(){
        return requirementDAO.getAllRequirements();
    }
    
    public void deleteRequirement(String id) {
    	this.requirementDAO.deleteRequirement(id);
    }
    
    public void changeState(String id, String state, String remark) {
    	this.requirementDAO.changeState(id, state, remark);
    }
}
