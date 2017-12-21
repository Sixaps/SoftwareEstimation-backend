package estimation.bean;

import java.util.ArrayList;
import java.util.List;

public class EIFDataSet {
	private String ExternalInterfaceFileName;

    private List<String> DET = new ArrayList<String>();


	public String getExternalInterfaceFileName() {
		return ExternalInterfaceFileName;
	}

	public void setExternalInterfaceFileName(String externalInterfaceFileName) {
		ExternalInterfaceFileName = externalInterfaceFileName;
	}

	public List<String> getDET() {
		return DET;
	}

	public void setDET(List<String> dET) {
		DET = dET;
	}


	
}
