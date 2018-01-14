package estimation.service;

import estimation.DAO.RequirementDAO;
import estimation.DAO.TransactionDAO;
import estimation.bean.EIFDataSet;
import estimation.bean.EstimationFileData;
import estimation.bean.EstimationTransactionData;
import estimation.bean.File;
import estimation.bean.Folder;
import estimation.bean.ILFDataSet;
import estimation.bean.Requirement;
import estimation.bean.Step;
import estimation.bean.Transaction;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuawai on 03/05/2017.
 */
@Service
public class TransactionService {
	@Autowired
	private TransactionDAO transactionDAO;

	@Autowired
	private RequirementDAO requirementDAO;

	public void add(String id, Transaction transaction) {
		this.transactionDAO.add(id, transaction);
	}

	public void deleteArray(String id, String key) {
		this.transactionDAO.deleteArray(id, key);
	}

	//读取某需求所有的transaction信息
	public List<Transaction> getAllTransactions(String id){
		Requirement requirement = this.requirementDAO.getRequirement(id);
		return requirement.getTransactions();
	}

	public void buildTree(Folder parent, JSONObject jsonObject) {
		JSONArray childFiles = jsonObject.getJSONArray("childFiles");
		JSONObject childFile;
		File file;
		List<File> files = new ArrayList<File>();
		for(int i = 0; i < childFiles.size();i++) {
			childFile = (JSONObject)childFiles.get(i);
			file = new File();
			file.setId(childFile.getString("id"));
			file.setName(childFile.getString("name"));
			files.add(file);
		}
		parent.setChildFiles(files);
		JSONArray childFolders = jsonObject.getJSONArray("childFolders");
		JSONObject childFolder;
		Folder folder;
		List<Folder> folders = new ArrayList<Folder>();
		for(int i = 0; i < childFolders.size(); i++) {
			childFolder = (JSONObject)childFolders.get(i);
			folder = new Folder();
			folder.setId(childFolder.getString("id"));
			folder.setName(childFolder.getString("name"));
			this.buildTree(folder, childFolder);
			folders.add(folder);
		}
		parent.setChildFolders(folders);
	}

	public void addTree(String id, Folder tree) {
		this.transactionDAO.addTree(id, tree);
	}

	public Folder getTree(String id) {
		return transactionDAO.getTree(id);
	}

	public void addFile(String id, String name, String tId) {
		Transaction transaction = new Transaction();

		List<Step> steps = new ArrayList<>();

		Step step = new Step();

		List<EIFDataSet> eifDataSets = new ArrayList<>();
		List<ILFDataSet> ilfDataSets = new ArrayList<>();

		EIFDataSet eifDataSet = new EIFDataSet();
		ILFDataSet ilfDataSet = new ILFDataSet();
		List<String> dets = new ArrayList<>();
		dets.add("");
		eifDataSet.setExternalInterfaceFileName("");
		ilfDataSet.setInnerlogicalFileName("");
		eifDataSet.setDET(dets);
		ilfDataSet.setDET(dets);
		eifDataSets.add(eifDataSet);
		ilfDataSets.add(ilfDataSet);

		step.setStepName("");
		step.setEifDataSets(eifDataSets);
		step.setIlfDataSets(ilfDataSets);
		steps.add(step);
		
		List<EstimationFileData> estimationFileDatas = new ArrayList<>();
        List<EstimationTransactionData> estimationTransactionDatas = new ArrayList<>();
        EstimationFileData estimationFileData = new EstimationFileData();
        EstimationTransactionData estimationTransactionData = new EstimationTransactionData();
        estimationFileData.setDET("");
        estimationFileData.setDETNum(0);
        estimationFileData.setFileType("");
        estimationFileData.setName("");
        estimationFileData.setRET("");
        estimationFileData.setRETNum(0);
        
        estimationTransactionData.setDET("");
        estimationTransactionData.setDETNum(0);
        estimationTransactionData.setFileNum(0);
        estimationTransactionData.setLogicalFile("");
        estimationTransactionData.setTransactionType("");
        estimationTransactionData.setName("");

        estimationFileDatas.add(estimationFileData);
        estimationTransactionDatas.add(estimationTransactionData);
        
        transaction.setEstimationFileDatas(estimationFileDatas);
        transaction.setEstimationTransactionDatas(estimationTransactionDatas);
		transaction.setId(tId);
		transaction.setNameOfEIF("");
		transaction.setNameOfILF("");
		transaction.setSteps(steps);
		transaction.setTransactionName(name);

		this.transactionDAO.add(id, transaction);
	}

	public Transaction geTransaction(String id, String tId) {
		return transactionDAO.geTransaction(id, tId);
	}

	public void deleteTransaction(String id, String tId) {
		this.transactionDAO.deleteTransaction(id, tId);
	}

	public void reName(String id, String tId, String tName) {
		this.transactionDAO.reName(id, tId, tName);
	}

	public void getAllTransactionId(Folder tree, List<String> tIds) {
		for(int i = 0;i<tree.getChildFiles().size();i++) {
			tIds.add(tree.getChildFiles().get(i).getId());
		}
		for(int i = 0;i < tree.getChildFolders().size();i++) {
			getAllTransactionId(tree.getChildFolders().get(i), tIds);
		}
	}
	
	public void updateTransactionList(String id, List<String> tIds) {
		this.transactionDAO.updateTransaction(id, tIds);
	}
}
