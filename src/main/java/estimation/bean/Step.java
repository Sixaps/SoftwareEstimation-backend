package estimation.bean;

import java.util.List;
import estimation.bean.EIFDataSet;
import estimation.bean.ILFDataSet;

/**
 * Created by xuawai on 03/05/2017.
 */
public class Step {

    private String stepName;

    private List<EIFDataSet> eifDataSets;
    
    private List<ILFDataSet> ilfDataSets;
    
    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

	public List<EIFDataSet> getEifDataSets() {
		return eifDataSets;
	}

	public void setEifDataSets(List<EIFDataSet> eifDataSets) {
		this.eifDataSets = eifDataSets;
	}

	public List<ILFDataSet> getIlfDataSets() {
		return ilfDataSets;
	}

	public void setIlfDataSets(List<ILFDataSet> ilfDataSets) {
		this.ilfDataSets = ilfDataSets;
	}


}
