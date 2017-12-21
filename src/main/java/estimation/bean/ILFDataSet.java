package estimation.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuawai on 03/05/2017.
 */
public class ILFDataSet {

    private String InnerlogicalFileName;

    private List<String> DET = new ArrayList<String>();


	public String getInnerlogicalFileName() {
		return InnerlogicalFileName;
	}

	public void setInnerlogicalFileName(String innerlogicalFileName) {
		InnerlogicalFileName = innerlogicalFileName;
	}

	public List<String> getDET() {
		return DET;
	}

	public void setDET(List<String> dET) {
		DET = dET;
	}



}
