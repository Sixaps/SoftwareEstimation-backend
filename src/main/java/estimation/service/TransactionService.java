package estimation.service;

import estimation.DAO.RequirementDAO;
import estimation.DAO.TransactionDAO;
import estimation.bean.*;
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

	public TransactionService(){ }

	public TransactionService(TransactionDAO transactionDAO){
	    this.transactionDAO = transactionDAO;
    }

	public boolean add(String id, Transaction transaction) {
		try {
			return transactionDAO.add(id, transaction);
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public boolean buildTree(Folder parent, JSONObject jsonObject) {
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
		return true;
	}

	public boolean addTree(String id, Folder tree) {
		this.transactionDAO.addTree(id, tree);
		return true;
	}

	public boolean addFile(String id, String name, String tId) {
		try {
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

			List<EstimationTransactionData> estimationTransactionDatas = new ArrayList<>();
			EstimationTransactionData estimationTransactionData = new EstimationTransactionData();

			estimationTransactionData.setDET("");
			estimationTransactionData.setDETNum(0);
			estimationTransactionData.setFileNum(0);
			estimationTransactionData.setLogicalFile("");
			estimationTransactionData.setTransactionType("");
			estimationTransactionData.setName("");

			estimationTransactionDatas.add(estimationTransactionData);

			transaction.setEstimationTransactionDatas(estimationTransactionDatas);
			transaction.setId(tId);
			transaction.setSteps(steps);
			transaction.setTransactionName(name);

			this.transactionDAO.add(id, transaction);
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public Transaction geTransaction(String id, String tId) {
		return transactionDAO.geTransaction(id, tId);
	}

	public void deleteTransaction(String id, String tId) {
		this.transactionDAO.deleteTransaction(id, tId);
	}

	public Boolean reName(String id, String tId, String tName) {
		return this.transactionDAO.reName(id, tId, tName);
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

	public void updateILFAndEIFData(String id, JSONArray jsonArray, int flag){
		JSONObject jsonObject;
		List<FileTable> fileTables = new ArrayList<>();
		for(int i = 0; i < jsonArray.size(); i++){
			jsonObject = (JSONObject)jsonArray.get(i);
			String file = jsonObject.getString("name");
			String DETs = jsonObject.getString("DET");
			FileTable fileTable = new FileTable();
			fileTable.setAllDET(DETs);
			fileTable.setFileName(file);
			fileTables.add(fileTable);
		}
		if(flag == 1)
		    requirementDAO.updateTableOfILF(id, fileTables);
        else
            requirementDAO.updateTableOfEIF(id, fileTables);
	}

	public void updateETDs(String id,String tId, List<EstimationTransactionData> eTDs){
		try {
			transactionDAO.updateETDs(id, tId, eTDs);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
