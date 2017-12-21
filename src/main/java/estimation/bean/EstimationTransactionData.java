package estimation.bean;

public class EstimationTransactionData {
	private String name;
    private	String TransactionType;
    private String LogicalFile;
    private String DET;
    private int FileNum;
    private int DETNum;
    private String Complexity;
    private int UFP;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTransactionType() {
		return TransactionType;
	}
	public void setTransactionType(String transactionType) {
		TransactionType = transactionType;
	}
	public int getFileNum() {
		return FileNum;
	}
	public void setFileNum(int fileNum) {
		FileNum = fileNum;
	}
	public int getDETNum() {
		return DETNum;
	}
	public void setDETNum(int dET) {
		DETNum = dET;
	}
	public String getComplexity() {
		return Complexity;
	}
	public void setComplexity(String complexity) {
		Complexity = complexity;
	}
	public int getUFP() {
		return UFP;
	}
	public void setUFP(int uFP) {
		UFP = uFP;
	}
	public String getLogicalFile() {
		return LogicalFile;
	}
	public void setLogicalFile(String logicalFile) {
		LogicalFile = logicalFile;
	}
	public String getDET() {
		return DET;
	}
	public void setDET(String dET) {
		DET = dET;
	}
}
