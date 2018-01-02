package estimation.controller;

import estimation.bean.EIFDataSet;
import estimation.bean.EstimationFileData;
import estimation.bean.EstimationTransactionData;
import estimation.bean.Folder;
import estimation.bean.ILFDataSet;
import estimation.bean.Step;
import estimation.bean.Transaction;
import estimation.service.RequirementService;
import estimation.service.TransactionService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuawai on 03/05/2017.
 */
@RestController
@RequestMapping(value="/estimation")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private RequirementService requirementService;

    //增加一个新事务
    @RequestMapping(value = "/addTransaction/{id}",method = RequestMethod.POST)
    public void addTransaction(HttpServletRequest request, @RequestBody JSONObject jsonObject, @PathVariable String id) {
        String userId = requirementService.getAccount(request);

        Transaction transaction = new Transaction();
        String transactionName = jsonObject.getString("transactionName");
        String transactionId = jsonObject.getString("tId");
        String nameOfEIF;
        String nameOfILF;
        JSONArray stepsArray;
        
        if(jsonObject.has("nameOfEIF"))
        	nameOfEIF = jsonObject.getString("nameOfEIF");
        else
        	nameOfEIF = new String();
        
        if(jsonObject.has("nameOfILF"))
        	nameOfILF = jsonObject.getString("nameOfILF");
        else
        	nameOfILF = new String();
        
        List<Step> steps = new ArrayList<Step>();
        //--step level
        
        if(jsonObject.has("steps"))
        	stepsArray = jsonObject.getJSONArray("steps");
        else {
        	stepsArray = new JSONArray();
        	Step step = new Step();
            
            List<EIFDataSet> eifDataSets = new ArrayList<EIFDataSet>(); 
            List<ILFDataSet> ilfDataSets = new ArrayList<ILFDataSet>();
            
            EIFDataSet eifDataSet = new EIFDataSet();
            ILFDataSet ilfDataSet = new ILFDataSet();
            List<String> dets = new ArrayList<String>();
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
            stepsArray.add(step);
        }
        for(int i=0; i<stepsArray.size(); i++){
            Step step = new Step();
            JSONObject stepObject = (JSONObject) stepsArray.get(i);
            String stepName = stepObject.getString("name");
            List<ILFDataSet> ilfDataSets = new ArrayList<ILFDataSet>();
            List<EIFDataSet> eifDataSets = new ArrayList<EIFDataSet>();
            //--concerningDataSet level
            JSONArray EIFDataSetsArray = stepObject.getJSONArray("eifs");
            JSONArray ILFDataSetsArray = stepObject.getJSONArray("ilfs");
            for(int j=0; j<EIFDataSetsArray.size(); j++){
                EIFDataSet eifDataSet = new EIFDataSet();
                JSONObject eifDataSetObject = (JSONObject) EIFDataSetsArray.get(j);
                String logicalFileName = eifDataSetObject.getString("name");
                
                JSONArray eifDETArray = eifDataSetObject.getJSONArray("dataFields");
                List<String> eifDETs = new ArrayList<String>();
                for(int k = 0; k < eifDETArray.size();k++) {
                	JSONObject eifDET = (JSONObject)eifDETArray.get(k);
                	String DET = eifDET.getString("value");
                	eifDETs.add(DET);
                }

                
                eifDataSet.setExternalInterfaceFileName(logicalFileName);
                eifDataSet.setDET(eifDETs);
                eifDataSets.add(eifDataSet);
            }
            
            for(int j=0; j<ILFDataSetsArray.size(); j++){
                ILFDataSet ilfDataSet = new ILFDataSet();
                JSONObject ilfDataSetObject = (JSONObject) ILFDataSetsArray.get(j);
                String logicalFileName = ilfDataSetObject.getString("name");
                
                JSONArray ilfDETArray = ilfDataSetObject.getJSONArray("dataFields");
                List<String> ilfDETs = new ArrayList<String>();
                for(int k = 0; k < ilfDETArray.size();k++) {
                	JSONObject ilfDET = (JSONObject)ilfDETArray.get(k);
                	String DET = ilfDET.getString("value");
                	ilfDETs.add(DET);
                }

                
                ilfDataSet.setInnerlogicalFileName(logicalFileName);
                ilfDataSet.setDET(ilfDETs);
                ilfDataSets.add(ilfDataSet);
            }
            //--concerningDataSet level
            step.setStepName(stepName);
            step.setEifDataSets(eifDataSets);
            step.setIlfDataSets(ilfDataSets);
            steps.add(step);
        }
        //step level--
        
        List<EstimationFileData> estimationFileDatas = new ArrayList<EstimationFileData>();
        List<EstimationTransactionData> estimationTransactionDatas = new ArrayList<EstimationTransactionData>();
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
        transaction.setTransactionName(transactionName);
        transaction.setId(transactionId);
        transaction.setNameOfEIF(nameOfEIF);
        transaction.setNameOfILF(nameOfILF);
        transaction.setSteps(steps);
        transactionService.add(id, userId, transaction);
    }


    @RequestMapping(value = "/addAllTransaction/{id}",method = RequestMethod.POST)
    public void addAllTransaction(HttpServletRequest request, @RequestBody JSONObject jsonObject, @PathVariable String id) {
        String userId = requirementService.getAccount(request);
        transactionService.deleteArray(id, userId, "transactions");

        JSONArray transactionsArray = jsonObject.getJSONArray("transactions");
        for(int i=0; i<transactionsArray.size(); i++){
            JSONObject transactionObject = (JSONObject) transactionsArray.get(i);
            addTransaction(request, transactionObject, id);
        }
    }
    
    @RequestMapping(value = "/addTree/{id}", method = RequestMethod.POST)
    public void addAllTree(HttpServletRequest request, @RequestBody JSONObject jsonObject, @PathVariable String id) {
        String userId = requirementService.getAccount(request);
        if(!requirementService.checkIdentity(id, userId))
            return;
        JSONObject tree = jsonObject.getJSONObject("tree");
    	Folder folder = new Folder();
    	folder.setName(tree.getString("name"));
    	folder.setId(tree.getString("id"));
    	List<String> tIds = new ArrayList<String>();
    	transactionService.buildTree(folder, tree);
    	transactionService.getAllTransactionId(folder, tIds);
    	transactionService.updateTransactionList(id, tIds);
    	transactionService.addTree(id, folder);
    }
    
    @RequestMapping(value = "/getAllTransactions/{id}", method = RequestMethod.GET)
    public List<Transaction> getAllTransactions(HttpServletRequest request, @PathVariable String id){
        String userId = requirementService.getAccount(request);
        if(!requirementService.checkIdentity(id, userId))
            return null;
    	return transactionService.getAllTransactions(id);
    }
    
    @RequestMapping(value = "/getTree/{id}", method = RequestMethod.GET)
    public Folder getTree(HttpServletRequest request, @PathVariable String id) {
        String userId = requirementService.getAccount(request);
        if(!requirementService.checkIdentity(id, userId))
            return null;
    	return transactionService.getTree(id);
    }
    
    @RequestMapping(value = "/addFile/{id}", method = RequestMethod.POST)
    public void addFile(HttpServletRequest request, @RequestBody JSONObject jsonObject, @PathVariable String id) {
        String userId = requirementService.getAccount(request);
        if(!requirementService.checkIdentity(id, userId))
            return;
    	String name = jsonObject.getString("name");
    	String tId = jsonObject.getString("id");
    	transactionService.addFile(id, name, tId);
    	
    	JSONObject tree = jsonObject.getJSONObject("tree");
    	Folder folder = new Folder();
    	folder.setName(tree.getString("name"));
    	folder.setId(tree.getString("id"));
    	transactionService.buildTree(folder, tree);
    	transactionService.addTree(id, folder);
    }
    
    @RequestMapping(value = "/getTransaction/{id}", method = RequestMethod.POST)
    public Transaction geTransaction(HttpServletRequest request, @RequestBody JSONObject jsonObject,@PathVariable String id) {
        String userId = requirementService.getAccount(request);
        if(!requirementService.checkIdentity(id, userId))
            return null;
    	String tId = jsonObject.getString("tId");
    	return transactionService.geTransaction(id, tId);
    }
    
    @RequestMapping(value = "/deleteTransaction/{id}", method = RequestMethod.POST)
    public void deleteTransaction(HttpServletRequest request, @RequestBody JSONObject jsonObject,@PathVariable String id) {
        String userId = requirementService.getAccount(request);

        String tId = jsonObject.getString("tId");
    	transactionService.deleteTransaction(id, userId, tId);
    }
    
    @RequestMapping(value = "/updateTransaction/{id}", method = RequestMethod.POST)
    public void updateTransaction(HttpServletRequest request, @RequestBody JSONObject jsonObject,@PathVariable String id) {
        String userId = requirementService.getAccount(request);

        String tId = jsonObject.getString("tId");
    	transactionService.deleteTransaction(id, userId, tId);
    	addTransaction(request, jsonObject, id);
    }
    
    @RequestMapping(value = "/TransactionReName/{id}", method = RequestMethod.POST)
    public void TransactionReName(HttpServletRequest request, @RequestBody JSONObject jsonObject,@PathVariable String id) {
        String userId = requirementService.getAccount(request);
        if(!requirementService.checkIdentity(id, userId))
            return;
    	String tId = jsonObject.getString("tId");
    	String tName = jsonObject.getString("tName");
    	transactionService.reName(id, tId, tName);
    }
}
