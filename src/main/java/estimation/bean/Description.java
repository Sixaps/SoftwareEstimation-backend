package estimation.bean;

/**
 * Created by xuawai on 03/05/2017.
 */
public class Description {

    private String projectName;

    private String projectDescription;
    
    private String projectLeader;
    
    private String projectContact;
    
    private String estimationMethod;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }
    
    public String getProjectContact() {
		return projectContact;
	}
    
    public String getProjectLeader() {
		return projectLeader;
	}
    
    public void setProjectContact(String projectContact) {
		this.projectContact = projectContact;
	}
    
    public void setProjectLeader(String projectLeader) {
		this.projectLeader = projectLeader;
	}
    
    public String getEstimationMethod() {
		return estimationMethod;
	}
    
    public void setEstimationMethod(String estimationMethod) {
		this.estimationMethod = estimationMethod;
	}
}
