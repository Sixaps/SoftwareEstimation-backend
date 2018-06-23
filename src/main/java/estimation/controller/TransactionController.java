package estimation.controller;

import estimation.bean.*;
import estimation.service.ManagerService;
import estimation.service.RequirementService;
import estimation.service.TransactionService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private ManagerService managerService;

    //增加一个新事务
    @RequestMapping(value = "/addTransaction/{id}",method = RequestMethod.POST)
    public void addTransaction(HttpServletRequest request, @RequestBody JSONObject jsonObject, @PathVariable String id) {
        String userId = requirementService.getAccount(request);
        if(!managerService.judgeIdentity(userId) && !requirementService.checkIdentity(id, userId))
            return;
        Transaction transaction = new Transaction();
        String transactionName = jsonObject.getString("transactionName");
        String transactionId = jsonObject.getString("tId");
        JSONArray stepsArray;

        List<Step> steps = new ArrayList<>();
        //--step level

        if(jsonObject.get("steps") != null)
        	stepsArray = jsonObject.getJSONArray("steps");
        else {
        	stepsArray = new JSONArray();
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
            stepsArray.add(step);
        }
        for(int i=0; i<stepsArray.size(); i++){
            Step step = new Step();
            JSONObject stepObject = (JSONObject) stepsArray.get(i);
            String stepName = stepObject.getString("name");
            List<ILFDataSet> ilfDataSets = new ArrayList<>();
            List<EIFDataSet> eifDataSets = new ArrayList<>();
            //--concerningDataSet level
            JSONArray EIFDataSetsArray = stepObject.getJSONArray("eifs");
            JSONArray ILFDataSetsArray = stepObject.getJSONArray("ilfs");
            for(int j=0; j<EIFDataSetsArray.size(); j++){
                EIFDataSet eifDataSet = new EIFDataSet();
                JSONObject eifDataSetObject = (JSONObject) EIFDataSetsArray.get(j);
                String logicalFileName = eifDataSetObject.getString("name");

                JSONArray eifDETArray = eifDataSetObject.getJSONArray("dataFields");
                List<String> eifDETs = new ArrayList<>();
                if(eifDETArray.size()==0){
                    eifDETs.add("");
                }
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
                List<String> ilfDETs = new ArrayList<>();
                if(ilfDETArray.size()==0){
                    ilfDETs.add("");
                }
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
        transaction.setTransactionName(transactionName);
        transaction.setId(transactionId);
        transaction.setSteps(steps);
        transactionService.add(id, transaction);
    }

    
    @RequestMapping(value = "/addTree/{id}", method = RequestMethod.POST)
    public void addAllTree(HttpServletRequest request, @RequestBody JSONObject jsonObject, @PathVariable String id) {
        String userId = requirementService.getAccount(request);
        if(!managerService.judgeIdentity(userId) && !requirementService.checkIdentity(id, userId))
            return;
        JSONObject tree = jsonObject.getJSONObject("tree");
    	Folder folder = new Folder();
    	folder.setName(tree.getString("name"));
    	folder.setId(tree.getString("id"));
    	List<String> tIds = new ArrayList<>();
    	transactionService.buildTree(folder, tree);
    	transactionService.getAllTransactionId(folder, tIds);
    	transactionService.updateTransactionList(id, tIds);
    	transactionService.addTree(id, folder);
    }
    
    @RequestMapping(value = "/getTree/{id}", method = RequestMethod.GET)
    public Object getTree(HttpServletRequest request, @PathVariable String id) {
        String userId = requirementService.getAccount(request);
        Requirement requirement = requirementService.getRequirement(id);
        if(!managerService.judgeIdentity(userId) && !requirementService.checkIdentity(id, userId))
            return null;
        Folder folder = requirement.getTreeOfTransactions();
        JSONObject jsonObject = new JSONObject().fromObject(folder);
        jsonObject.accumulate("eifTable", requirement.getAllEIFData());
        jsonObject.accumulate("ilfTable", requirement.getAllILFData());
        return jsonObject;
    }
    
    @RequestMapping(value = "/addFile/{id}", method = RequestMethod.POST)
    public void addFile(HttpServletRequest request, @RequestBody JSONObject jsonObject, @PathVariable String id) {
        String userId = requirementService.getAccount(request);
        if(!managerService.judgeIdentity(userId) && !requirementService.checkIdentity(id, userId))
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
    public Object geTransaction(HttpServletRequest request, @RequestBody JSONObject jsonObject,@PathVariable String id) {
        String userId = requirementService.getAccount(request);
        if(!managerService.judgeIdentity(userId) && !requirementService.checkIdentity(id, userId))
            return null;
    	String tId = jsonObject.getString("tId");
        JSONObject jsonObject2 = new JSONObject().fromObject(transactionService.geTransaction(id, tId));
        jsonObject2.accumulate("estimationFileDatas", requirementService.getRequirement(id).getEstimationFileDatas());
    	return jsonObject2;
    }
    
    @RequestMapping(value = "/deleteTransaction/{id}", method = RequestMethod.POST)
    public void deleteTransaction(HttpServletRequest request, @RequestBody JSONObject jsonObject,@PathVariable String id) {
        String tId = jsonObject.getString("tId");
        String userId = requirementService.getAccount(request);
        if(!managerService.judgeIdentity(userId) && !requirementService.checkIdentity(id, userId))
            return;
    	transactionService.deleteTransaction(id, tId);
    }
    
    @RequestMapping(value = "/updateTransaction/{id}", method = RequestMethod.POST)
    public void updateTransaction(HttpServletRequest request, @RequestBody JSONObject jsonObject,@PathVariable String id) {
        String tId = jsonObject.getString("tId");

        Transaction transaction = transactionService.geTransaction(id, tId);
        List<EstimationTransactionData> eTDs =  transaction.getEstimationTransactionDatas();
    	transactionService.deleteTransaction(id, tId);
    	addTransaction(request, jsonObject, id);
    	transactionService.updateETDs(id, tId, eTDs);
    	JSONArray jsonArray = jsonObject.getJSONArray("ILFTable");
        transactionService.updateILFAndEIFData(id, jsonArray, 1);
        jsonArray = jsonObject.getJSONArray("EIFTable");
        transactionService.updateILFAndEIFData(id, jsonArray, 2);
    }
    
    @RequestMapping(value = "/TransactionReName/{id}", method = RequestMethod.POST)
    public Object TransactionReName(HttpServletRequest request, @RequestBody JSONObject jsonObject,@PathVariable String id) {
        String userId = requirementService.getAccount(request);
        HttpStatus status = HttpStatus.ACCEPTED;
        if(!managerService.judgeIdentity(userId) && !requirementService.checkIdentity(id, userId)){
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<String>("",status);
        }
    	String tId = jsonObject.getString("tId");
    	String tName = jsonObject.getString("tName");
    	if(tName.equals("")){
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
    	if(!transactionService.reName(id, tId, tName)){
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<String>("",status);
    }
}
