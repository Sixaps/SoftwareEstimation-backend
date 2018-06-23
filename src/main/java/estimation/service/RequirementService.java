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
        List<Transaction> transactions = new ArrayList<>();
        Folder treeOfTransactions = new Folder();
        Folder childFolder = new Folder();
        treeOfTransactions.setId("00000");
        treeOfTransactions.setName("tree");
        childFolder.setId("00001");
        childFolder.setName("功能模块1");
        List<Folder> folders1 = new ArrayList<>();
        List<File> files1 = new ArrayList<>();
        List<Folder> folders = new ArrayList<>();
        List<File> files = new ArrayList<>();
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

        List<EstimationFileData> estimationFileDatas = new ArrayList<>();
        EstimationFileData estimationFileData = new EstimationFileData();
        estimationFileData.setDET("");
        estimationFileData.setDETNum(0);
        estimationFileData.setFileType("");
        estimationFileData.setName("");
        estimationFileData.setRET("");
        estimationFileData.setRETNum(0);
        estimationFileDatas.add(estimationFileData);
        requirement.setEstimationFileDatas(estimationFileDatas);
        //requirement level--

        List<FileTable> fileTables = new ArrayList<>();
        FileTable fileTable = new FileTable();
        fileTables.add(fileTable);
        requirement.setAllEIFData(fileTables);
        requirement.setAllILFData(fileTables);


        this.requirementDAO.add(requirement);
        //这里应该修改为，如果插入成功，则返回id
        return id;
    }

    public Requirement getRequirement(String id){
        return requirementDAO.getRequirement(id);
    }

    public List<Requirement> getAllRequirementsByUser(String userId){
        return requirementDAO.getAllRequirementsByUser(userId);
    }

    public List<Requirement> getAllRequirements(){
        return requirementDAO.getAllRequirements();
    }
    
    public void deleteRequirement(String id) {
    	this.requirementDAO.deleteRequirement(id);
    }
    
    public Boolean changeState(String id, String state, String remark) {
    	return this.requirementDAO.changeStateAndRemark(id, state, remark);
    }

    public String getAccount(HttpServletRequest request)
    {
        java.lang.String token = request.getHeader("Authorization");
        Boolean tokenExist = stringRedisTemplate.hasKey(token);
        if (tokenExist) {
            return stringRedisTemplate.opsForValue().get(token);
        }
        else {
            return null;
        }
    }

    public void changeStatus(String id, String status){
        requirementDAO.changeStatus(id,status);
    }

    public boolean checkIdentity(String id, String userId){
        return requirementDAO.checkIdentity(id, userId);
    }
}
