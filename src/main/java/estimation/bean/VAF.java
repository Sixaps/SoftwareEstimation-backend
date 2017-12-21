package estimation.bean;

/**
 * Created by xuawai on 16/06/2017.
 */
public class VAF {

    private String developmentType;
    private String developmentPlatform;
    private String languageType;
    private String DBMS_Used;
    private String RELY;
    private String CPLX;
    private String TIME;
    private String SCED;
    private String productivity;
    private String cost;

    public String getDevelopmentType() {
        return developmentType;
    }

    public void setDevelopmentType(String developmentType) {
        this.developmentType = developmentType;
    }

    public String getDevelopmentPlatform() {
        return developmentPlatform;
    }

    public void setDevelopmentPlatform(String developmentPlatform) {
        this.developmentPlatform = developmentPlatform;
    }

    public String getLanguageType() {
        return languageType;
    }

    public void setLanguageType(String languageType) {
        this.languageType = languageType;
    }

	public String getDBMS_Used() {
		return DBMS_Used;
	}

	public void setDBMS_Used(String dBMS_Used) {
		DBMS_Used = dBMS_Used;
	}

	public String getRELY() {
		return RELY;
	}

	public void setRELY(String rELY) {
		RELY = rELY;
	}

	public String getCPLX() {
		return CPLX;
	}

	public void setCPLX(String cPLX) {
		CPLX = cPLX;
	}

	public String getTIME() {
		return TIME;
	}

	public void setTIME(String tIME) {
		TIME = tIME;
	}

	public String getSCED() {
		return SCED;
	}

	public void setSCED(String sCED) {
		SCED = sCED;
	}

	public String getProductivity() {
		return productivity;
	}

	public void setProductivity(String productivity) {
		this.productivity = productivity;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}
    
}
