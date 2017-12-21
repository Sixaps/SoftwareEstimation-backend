package estimation.bean;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;

public class Folder {
	private String name;
	@Id
	private String id;
	private List<Folder> childFolders = new ArrayList<Folder>();
	private List<File> childFiles = new ArrayList<File>();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<Folder> getChildFolders() {
		return childFolders;
	}
	public void setChildFolders(List<Folder> childFolders) {
		this.childFolders = childFolders;
	}
	public List<File> getChildFiles() {
		return childFiles;
	}
	public void setChildFiles(List<File> childFiles) {
		this.childFiles = childFiles;
	}
	
	
}
