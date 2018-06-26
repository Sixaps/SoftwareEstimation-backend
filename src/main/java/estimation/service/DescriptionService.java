package estimation.service;

import estimation.DAO.DescriptionDAO;
import estimation.bean.Description;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xuawai on 03/05/2017.
 */
@Service
public class DescriptionService {
    @Autowired
    private DescriptionDAO descriptionDAO;

    public DescriptionService(){

    }

    public DescriptionService(DescriptionDAO descriptionDAO){
        this.descriptionDAO = descriptionDAO;
    }

    public boolean add(String id, JSONObject jsonObject){
        try {
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
            return descriptionDAO.add(id, description);
        } catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
