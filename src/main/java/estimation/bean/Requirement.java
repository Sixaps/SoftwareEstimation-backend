package estimation.bean;

/**
 * Created by xuawai on 03/05/2017.
 */
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "requirement")
public class Requirement {
    @Id
    private String id;

    private String userId;

    private Description description;

    private List<Transaction> transactions;
    
    private Folder treeOfTransactions;

    private VAF originalVAF;
    
    private VAF newVAF;

    private String state;
    
    private String createTime;
    
    private String remark;
    
    private int UFP;
    
    private double AFP;
    
    private double workTime;
    
    private double workCost;

    private List<FileTable> allEIFData;

    private List<FileTable> allILFData;

    private List<EstimationFileData> estimationFileDatas;

    public List<FileTable> getAllEIFData() {
        return allEIFData;
    }

    public void setAllEIFData(List<FileTable> allEIFData) {
        this.allEIFData = allEIFData;
    }

    public List<FileTable> getAllILFData() {
        return allILFData;
    }

    public void setAllILFData(List<FileTable> allILFData) {
        this.allILFData = allILFData;
    }

    public List<EstimationFileData> getEstimationFileDatas() {
        return estimationFileDatas;
    }

    public void setEstimationFileDatas(List<EstimationFileData> estimationFileDatas) {
        this.estimationFileDatas = estimationFileDatas;
    }
    
    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    
    public String getState() {
		return state;
	}
    
    public void setState(String state) {
		this.state = state;
	}

	public Folder getTreeOfTransactions() {
		return treeOfTransactions;
	}

	public void setTreeOfTransactions(Folder treeOfTransactions) {
		this.treeOfTransactions = treeOfTransactions;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getUFP() {
		return UFP;
	}

	public void setUFP(int uFP) {
		UFP = uFP;
	}

	public double getAFP() {
		return AFP;
	}

	public void setAFP(double aFP) {
		AFP = aFP;
	}

	public VAF getOriginalVAF() {
		return originalVAF;
	}

	public void setOriginalVAF(VAF originalVAF) {
		this.originalVAF = originalVAF;
	}

	public VAF getNewVAF() {
		return newVAF;
	}

	public void setNewVAF(VAF newVAF) {
		this.newVAF = newVAF;
	}

	public double getWorkTime() {
		return workTime;
	}

	public void setWorkTime(double workTime) {
		this.workTime = workTime;
	}

	public double getWorkCost() {
		return workCost;
	}

	public void setWorkCost(double workCost) {
		this.workCost = workCost;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}
}
