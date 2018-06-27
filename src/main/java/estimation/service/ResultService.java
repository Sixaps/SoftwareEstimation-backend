package estimation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import estimation.DAO.ResultDAO;
import estimation.bean.EstimationFileData;
import estimation.bean.EstimationTransactionData;
import estimation.bean.Requirement;
import estimation.bean.Transaction;
import estimation.bean.VAF;
import estimation.bean.VAFState;

@Service
public class ResultService {
	@Autowired
	private ResultDAO resultDAO;
	private int[] UFPLevel1 = {5,7,10};
	private int[] UFPLevel2 = {7,10,15};
	private int[] UFPLevel3 = {3,4,6};
	private int[] UFPLevel4 = {4,5,7};
	private int[] UFPLevel;
	private String[] complexityLevel = {"低","中","高"};
	
	private VAFState vafState = new VAFState();

	public Transaction updateResult(String id, String tId, List<EstimationFileData> eFDs, List<EstimationTransactionData> eTDs) {
		try {
			return resultDAO.UpdateResult(id, tId, eFDs, eTDs);
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public void calFileComplexity(EstimationFileData eFD) {
		int detNum = eFD.getDETNum();
		if(eFD.getFileType().equals("ILF")) {
			UFPLevel = UFPLevel2;
		}
		else if(eFD.getFileType().equals("EIF")) {
			UFPLevel = UFPLevel1;
		}

		if(detNum >= 1&&detNum <= 19) {
			calFileUFP(0,0,1,eFD);
		}
		else if(detNum >= 20 && detNum <= 50) {
			calFileUFP(0,1,2,eFD);
		}
		else if(detNum > 50) {
			calFileUFP(1,2,2,eFD);
		}
		else {
			eFD.setComplexity("无");
			eFD.setUFP(0);
		}
	}

	private void calFileUFP(int level1, int level2, int level3, EstimationFileData eFD) {
		int retNum = eFD.getRETNum();
		if(retNum == 1) {
			eFD.setComplexity(complexityLevel[level1]);
			eFD.setUFP(UFPLevel[level1]);
		}
		else if(retNum > 1 && retNum <= 5 ) {
			eFD.setComplexity(complexityLevel[level2]);
			eFD.setUFP(UFPLevel[level2]);
		}
		else if(retNum > 5) {
			eFD.setComplexity(complexityLevel[level3]);
			eFD.setUFP(UFPLevel[level3]);
		}
		else {
			eFD.setComplexity("无");
			eFD.setUFP(0);
		}
	}

	public void calTransactionComplexity(EstimationTransactionData eTD) {
		int detNum = eTD.getDETNum();
		if(eTD.getTransactionType().equals("EI")) {
			UFPLevel = UFPLevel3;
			if(detNum >= 1&&detNum <= 19) {
				calTransactionUTF(0,0,1,eTD);
			}
			else if(detNum >= 20 && detNum <= 50) {
				calTransactionUTF(0,1,2,eTD);
			}
			else if(detNum > 50) {
				calTransactionUTF(1,2,2,eTD);
			}
			else {
				eTD.setComplexity("无");
				eTD.setUFP(0);
			}
		}
		else {
			if(eTD.getTransactionType().equals("EO")) {
				UFPLevel = UFPLevel4;
			}
			else{
				UFPLevel = UFPLevel3;
			}
			if(detNum >= 1&&detNum <= 5) {
				calTransactionUTF(0,0,1,eTD);
			}
			else if(detNum >= 6 && detNum <= 19) {
				calTransactionUTF(0,1,2,eTD);
			}
			else if(detNum > 19) {
				calTransactionUTF(1,2,2,eTD);
			}
			else {
				eTD.setComplexity("无");
				eTD.setUFP(0);
			}
		}
	}

	private void calTransactionUTF(int level1, int level2, int level3, EstimationTransactionData eTD) {
		int FTRNum = eTD.getFileNum();
		if(FTRNum == 1) {
			eTD.setComplexity(complexityLevel[level1]);
			eTD.setUFP(UFPLevel[level1]);
		}
		else if(FTRNum > 1 && FTRNum <= 5 ) {
			eTD.setComplexity(complexityLevel[level2]);
			eTD.setUFP(UFPLevel[level2]);
		}
		else if(FTRNum > 5) {
			eTD.setComplexity(complexityLevel[level3]);
			eTD.setUFP(UFPLevel[level3]);
		}
		else {
			eTD.setComplexity("无");
			eTD.setUFP(0);
		}
	}
	
	public Requirement getReport(String id) {
		Requirement requirement =  resultDAO.getReport(id);
		
		List<Transaction> transactions = requirement.getTransactions();
		VAF vaf = requirement.getNewVAF();
		
		Transaction transaction;

		List<EstimationFileData> eFDs;
		List<EstimationTransactionData> eTDs;
		int ufp = 0;

		eFDs = requirement.getEstimationFileDatas();
		for(int j = 0; j < eFDs.size(); j++) {
			ufp += eFDs.get(j).getUFP();
		}
		for(int i = 0;i < transactions.size();i++) {
			transaction = transactions.get(i);
			eTDs = transaction.getEstimationTransactionDatas();
			
			for(int j = 0; j < eTDs.size(); j++) {
				ufp += eTDs.get(j).getUFP();
			}
		}
		double afp = ufp*CalVAF(vaf);
		
		requirement.setUFP(ufp);
		requirement.setAFP(afp);
		requirement.setWorkCost(afp*Double.parseDouble(vaf.getProductivity())*Double.parseDouble(vaf.getCost())/8.0);
		requirement.setWorkTime(afp*Double.parseDouble(requirement.getNewVAF().getProductivity())/(8.0));//*20.0
		return requirement;
	}
	
	private double CalVAF(VAF vaf) {
		String developmentType = vaf.getDevelopmentType();
	    String developmentPlatform = vaf.getDevelopmentPlatform();
	    String languageType = vaf.getLanguageType();
	    String RELY = vaf.getRELY();
	    String CPLX = vaf.getCPLX();
	    String TIME = vaf.getTIME();
	    String SCED = vaf.getSCED();
	    double iVAF = 1;
	    
	    switch(developmentType) {
	    case "New Development": iVAF*=0.857;break;
	    case "Enhancement": iVAF*=0.858;break;
	    case "Re-development": iVAF*=0.863;break;
	    }
	    
	    switch(developmentPlatform) {
	    case "Personal Computer": iVAF*=0.61;break;
	    case "Mainframe": iVAF*=1.06;break;
	    case "Mid-range": iVAF*=1.01;break;
	    }
	    
	    switch(languageType) {
	    case "3GL": iVAF*=1.06;break;
	    case "4GL": iVAF*=0.87;break;
	    }
	    
	    switch(RELY) {
	    case "Very Low": iVAF*=0.82;break;
	    case "Low": iVAF*=0.92;break;
	    case "Normal": iVAF*=1.00;break;
	    case "High": iVAF*=1.10;break;
	    case "Very High": iVAF*=1.26;break;
	    }
	    
	    switch(CPLX) {
	    case "Very Low": iVAF*=0.73;break;
	    case "Low": iVAF*=0.87;break;
	    case "Normal": iVAF*=1.00;break;
	    case "High": iVAF*=1.17;break;
	    case "Very High": iVAF*=1.34;break;
	    case "Extra High": iVAF*=1.74;break;
	    }
	    
	    switch(TIME) {
	    case "Normal": iVAF*=1.00;break;
	    case "High": iVAF*=1.11;break;
	    case "Very High": iVAF*=1.29;break;
	    case "Extra High": iVAF*=1.63;break;
	    }
	    
	    switch(SCED) {
	    case "Very Low": iVAF*=1.43;break;
	    case "Low": iVAF*=1.14;break;
	    case "Normal": iVAF*=1.00;break;
	    case "High": iVAF*=1.00;break;
	    case "Very High": iVAF*=1.00;break;
	    }
	    
	    
		return iVAF;
	}
	
	public VAFState getState(Requirement requirement) {
		VAF oldVaf = requirement.getOriginalVAF();
		VAF newVaf = requirement.getNewVAF();
		
		vafState.setDevelopmentPlatformState(judge(oldVaf.getDevelopmentPlatform(), newVaf.getDevelopmentPlatform()));
		vafState.setDevelopmentTypeState(judge(oldVaf.getDevelopmentType(), newVaf.getDevelopmentType()));
		vafState.setLanguageTypeState(judge(oldVaf.getLanguageType(), newVaf.getLanguageType()));
		vafState.setRELYState(judge(oldVaf.getRELY(), newVaf.getRELY()));
		vafState.setCPLXState(judge(oldVaf.getCPLX(), newVaf.getCPLX()));
		vafState.setTIMEState(judge(oldVaf.getTIME(), newVaf.getTIME()));
		vafState.setSCEDState(judge(oldVaf.getSCED(), newVaf.getSCED()));
		vafState.setDBMS_UsedState(judge(oldVaf.getDBMS_Used(), newVaf.getDBMS_Used()));
		vafState.setProductivityState(judge(oldVaf.getProductivity(), newVaf.getProductivity()));
		vafState.setCostState(judge(oldVaf.getCost(), newVaf.getCost()));
		
		return vafState;
	}
	
	private boolean judge(String a, String b) {
		if(a.equals(b))
			return false;
		else 
			return true;
	}
}
