package estimation.controller;

import estimation.bean.EIFDataSet;
import estimation.bean.EstimationFileData;
import estimation.bean.EstimationTransactionData;
import estimation.bean.Folder;
import estimation.bean.ILFDataSet;
import estimation.bean.Step;
import estimation.bean.Transaction;
import estimation.service.TransactionService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    //增加一个新事务
    @RequestMapping(value = "/addTransaction/{id}",method = RequestMethod.POST)
    public void addTransaction(@RequestBody JSONObject jsonObject, @PathVariable String id) {
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
        transactionService.add(id, transaction);
    }


    //增加所有新事务
    @RequestMapping(value = "/addAllTransaction/{id}",method = RequestMethod.POST)
    //可以优化，一方面利用一个service的函数完成，一方面添加事务支持，防止删除完成但是添加未完成，其他同理
    public void addAllTransaction(@RequestBody JSONObject jsonObject, @PathVariable String id) {
        transactionService.deleteArray(id, "transactions");

        JSONArray transactionsArray = jsonObject.getJSONArray("transactions");
        for(int i=0; i<transactionsArray.size(); i++){
            JSONObject transactionObject = (JSONObject) transactionsArray.get(i);
            addTransaction(transactionObject, id);
        }
    }
    
    @RequestMapping(value = "/addTree/{id}", method = RequestMethod.POST)
    public void addAllTree(@RequestBody JSONObject jsonObject, @PathVariable String id) {    	
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
    public List<Transaction> getAllTransactions(@PathVariable String id){
    	return transactionService.getAllTransactions(id);
    }
    
    @RequestMapping(value = "/getTree/{id}", method = RequestMethod.GET)
    public Folder getTree(@PathVariable String id) {
    	return transactionService.getTree(id);
    }
    
    @RequestMapping(value = "/addFile/{id}", method = RequestMethod.POST)
    public void addFile(@RequestBody JSONObject jsonObject, @PathVariable String id) {
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
    public Transaction geTransaction(@RequestBody JSONObject jsonObject,@PathVariable String id) {
    	String tId = jsonObject.getString("tId");
    	return transactionService.geTransaction(id, tId);
    }
    
    @RequestMapping(value = "/deleteTransaction/{id}", method = RequestMethod.POST)
    public void deleteTransaction(@RequestBody JSONObject jsonObject,@PathVariable String id) {
    	String tId = jsonObject.getString("tId");
    	transactionService.deleteTransaction(id, tId);
    }
    
    @RequestMapping(value = "/updateTransaction/{id}", method = RequestMethod.POST)
    public void updateTransaction(@RequestBody JSONObject jsonObject,@PathVariable String id) {
    	String tId = jsonObject.getString("tId");
    	transactionService.deleteTransaction(id, tId);
    	addTransaction(jsonObject, id);
    }
    
    @RequestMapping(value = "/TransactionReName/{id}", method = RequestMethod.POST)
    public void TransactionReName(@RequestBody JSONObject jsonObject,@PathVariable String id) {
    	String tId = jsonObject.getString("tId");
    	String tName = jsonObject.getString("tName");
    	transactionService.reName(id, tId, tName);
    }
}
