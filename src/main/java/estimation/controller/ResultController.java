package estimation.controller;

import java.util.ArrayList;
import java.util.List;

import estimation.service.ManagerService;
import estimation.service.RequirementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import estimation.bean.EstimationFileData;
import estimation.bean.EstimationTransactionData;
import estimation.bean.Requirement;
import estimation.bean.Transaction;
import estimation.service.ResultService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/estimation")
public class ResultController {
	@Autowired
	private ResultService resultService;

	@Autowired
	private RequirementService requirementService;

	@Autowired
	private ManagerService managerService;
	
	@RequestMapping(value = "/updateResult/{id}",method = RequestMethod.POST)
	public Object updateResult(HttpServletRequest request, @RequestBody JSONObject jsonObject, @PathVariable String id) {
		String userId = requirementService.getAccount(request);
		if(!managerService.judgeIdentity(userId) && !requirementService.checkIdentity(id, userId))
			return null;

		JSONArray TransactionArray = jsonObject.getJSONArray("eTDs");
		JSONArray FileArray = jsonObject.getJSONArray("eFDs");
		String tId = jsonObject.getString("tId");
		
		List<EstimationTransactionData> eTDs = new ArrayList<>();
		for(int i = 0;i < TransactionArray.size();i++) {
			EstimationTransactionData eTD = new EstimationTransactionData();
			JSONObject transactionData = TransactionArray.getJSONObject(i);
			String name = transactionData.getString("name");
			String transactionType = transactionData.getString("transactionType");
			String logicalFile = transactionData.getString("logicalFile");
			String dET = transactionData.getString("DET");
			String fileNumStr = transactionData.getString("fileNum");
			String detNumStr = transactionData.getString("detNum");
			int fileNum = new Integer(fileNumStr);
			int detNum = new Integer(detNumStr);
			
			eTD.setName(name);
			eTD.setTransactionType(transactionType);
			eTD.setLogicalFile(logicalFile);
			eTD.setDET(dET);
			eTD.setFileNum(fileNum);
			eTD.setDETNum(detNum);
			resultService.calTransactionComplexity(eTD);
			eTDs.add(eTD);
		}
		
		List<EstimationFileData> eFDs = new ArrayList<EstimationFileData>();
		for(int i = 0;i < FileArray.size();i++) {
			EstimationFileData eFD = new EstimationFileData();
			JSONObject fileData = FileArray.getJSONObject(i);
			String name = fileData.getString("name");
			String fileType = fileData.getString("fileType");
			String RET = fileData.getString("RET");
			String dET = fileData.getString("DET");
			String retNumStr = fileData.getString("retNum");
			String detNumStr = fileData.getString("detNum");
			
			int retNum = new Integer(retNumStr);
			int detNum = new Integer(detNumStr);
			
			eFD.setName(name);
			eFD.setFileType(fileType);
			eFD.setRET(RET);
			eFD.setDET(dET);
			eFD.setRETNum(retNum);
			eFD.setDETNum(detNum);
			resultService.calFileComplexity(eFD);
			eFDs.add(eFD);
		}
		
		JSONObject msg = new JSONObject().fromObject(resultService.updateResult(id, tId, eFDs, eTDs));
		msg.accumulate("estimationFileDatas", requirementService.getRequirement(id).getEstimationFileDatas());
		return msg;
	}
	
	@RequestMapping(value = "/getReport/{id}",method = RequestMethod.GET)
	public JSONObject getReport(HttpServletRequest request, @PathVariable String id) {
		String userId = requirementService.getAccount(request);
		if(!managerService.judgeIdentity(userId) && !requirementService.checkIdentity(id, userId))
			return null;
		Requirement requirement = resultService.getReport(id);
		JSONObject jsonObject = new JSONObject().fromObject(requirement);
		jsonObject.accumulate("vafState", resultService.getState(requirement));
		return jsonObject;
	}
}
