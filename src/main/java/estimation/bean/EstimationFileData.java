package estimation.bean;

public class EstimationFileData {
	private String name;
    private	String FileType;
    private String RET;
    private String DET;
    private int RETNum;
    private int DETNum;
    private String Complexity;
    private int UFP;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFileType() {
		return FileType;
	}
	public void setFileType(String fileType) {
		FileType = fileType;
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
	public String getRET() {
		return RET;
	}
	public void setRET(String rET) {
		RET = rET;
	}
	public String getDET() {
		return DET;
	}
	public void setDET(String dET) {
		DET = dET;
	}
	public int getRETNum() {
		return RETNum;
	}
	public void setRETNum(int rETNum) {
		RETNum = rETNum;
	}
	public int getDETNum() {
		return DETNum;
	}
	public void setDETNum(int dETNum) {
		DETNum = dETNum;
	}
	
}
