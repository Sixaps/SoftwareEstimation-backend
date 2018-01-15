package estimation.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : MianHong Li
 * @date : 2018/1/14
 */
public class FileTable {
    private String fileName;

    private String allDET;

    public FileTable(){
        this.fileName = "";
        this.allDET = "";
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getAllDET() {
        return allDET;
    }

    public void setAllDET(String allDET) {
        this.allDET = allDET;
    }
}
