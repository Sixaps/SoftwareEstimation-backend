package estimation.bean;

import java.util.List;

import org.springframework.data.annotation.Id;

/**
 * Created by xuawai on 03/05/2017.
 */
public class Transaction {
	@Id
	private String id;
	
    private String transactionName;

    private List<Step> steps;
    
    private String nameOfILF;
    
    private String nameOfEIF;
    
    private List<EstimationFileData> estimationFileDatas;
    
    private List<EstimationTransactionData> estimationTransactionDatas;

	public List<EstimationFileData> getEstimationFileDatas() {
		return estimationFileDatas;
	}

	public void setEstimationFileDatas(List<EstimationFileData> estimationFileDatas) {
		this.estimationFileDatas = estimationFileDatas;
	}

	public List<EstimationTransactionData> getEstimationTransactionDatas() {
		return estimationTransactionDatas;
	}

	public void setEstimationTransactionDatas(List<EstimationTransactionData> estimationTransactionDatas) {
		this.estimationTransactionDatas = estimationTransactionDatas;
	}

    public String getTransactionName() {
        return transactionName;
    }

    public void setTransactionName(String transactionName) {
        this.transactionName = transactionName;
    }


    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }
    
    public String getNameOfILF() {
		return nameOfILF;
	}
    
    public void setNameOfILF(String nameOfILF) {
		this.nameOfILF = nameOfILF;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNameOfEIF() {
		return nameOfEIF;
	}

	public void setNameOfEIF(String nameOfEIF) {
		this.nameOfEIF = nameOfEIF;
	}
}
