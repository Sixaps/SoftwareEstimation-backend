package estimation.controller;

import estimation.bean.VAF;
import estimation.service.ManagerService;
import estimation.service.RequirementService;
import estimation.service.VAFService;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by xuawai on 16/06/2017.
 */
@RestController
@RequestMapping(value = "/estimation")
public class VAFController {

	@Autowired
	private VAFService vafService;

	@Autowired
	private RequirementService requirementService;

	@Autowired
	private ManagerService managerService;

	//增加VAF
	@RequestMapping(value = "/addVAF/{id}", method = RequestMethod.POST)
	public Object addVAF(HttpServletRequest request, @RequestBody JSONObject jsonObject, @PathVariable String id) {
        HttpStatus status = HttpStatus.ACCEPTED;
		String userId = requirementService.getAccount(request);
		if(!requirementService.checkIdentity(id, userId)) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<Object>("",status);
        }
		VAF vaf = new VAF();
		String developmentType = jsonObject.getString("developmentType");
		String developmentPlatform = jsonObject.getString("developmentPlatform");
		String languageType = jsonObject.getString("languageType");
		String DBMS_Used = jsonObject.getString("DBMS_Used");


		String RELY = jsonObject.getString("RELY");
		String CPLX = jsonObject.getString("CPLX");
		String TIME = jsonObject.getString("TIME");
		String SCED = jsonObject.getString("SCED");


		String productivity = jsonObject.getString("productivity");
		String cost = jsonObject.getString("cost");
        if(Integer.valueOf(productivity) <= 0 || Integer.valueOf(cost) <= 0){
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<Object>("",status);
        }

		vaf.setDevelopmentType(developmentType);
		vaf.setDevelopmentPlatform(developmentPlatform);
		vaf.setLanguageType(languageType);
		vaf.setDBMS_Used(DBMS_Used);
		vaf.setRELY(RELY);
		vaf.setCPLX(CPLX);
		vaf.setTIME(TIME);
		vaf.setSCED(SCED);
		vaf.setProductivity(productivity);
		vaf.setCost(cost);
		vafService.add(id, vaf);
		requirementService.changeStatus(id, "待审核");
        return new ResponseEntity<Object>("",status);
	}

	@RequestMapping(value = "/changeVAF/{id}", method = RequestMethod.POST)
	public Object changeVAF(HttpServletRequest request, @RequestBody JSONObject jsonObject, @PathVariable String id) {
        HttpStatus status = HttpStatus.ACCEPTED;
        String userId = requirementService.getAccount(request);
		if(!managerService.judgeIdentity(userId) && !requirementService.checkIdentity(id, userId)) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<Object>("",status);
        }
		Map<Object, Object> msg = new HashMap<>();
		try {
			VAF vaf = new VAF();
			String developmentType = jsonObject.getString("developmentType");
			String developmentPlatform = jsonObject.getString("developmentPlatform");
			String languageType = jsonObject.getString("languageType");
			String DBMS_Used = jsonObject.getString("DBMS_Used");


			String RELY = jsonObject.getString("RELY");
			String CPLX = jsonObject.getString("CPLX");
			String TIME = jsonObject.getString("TIME");
			String SCED = jsonObject.getString("SCED");


			String productivity = jsonObject.getString("productivity");
			String cost = jsonObject.getString("cost");
            if(Integer.valueOf(productivity) <= 0 || Integer.valueOf(cost) <= 0){
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                return new ResponseEntity<Object>("",status);
            }

            vaf.setDevelopmentType(developmentType);
			vaf.setDevelopmentPlatform(developmentPlatform);
			vaf.setLanguageType(languageType);
			vaf.setDBMS_Used(DBMS_Used);
			vaf.setRELY(RELY);
			vaf.setCPLX(CPLX);
			vaf.setTIME(TIME);
			vaf.setSCED(SCED);
			vaf.setProductivity(productivity);
			vaf.setCost(cost);

			vafService.change(id, vaf);
			msg.put("flag", true);
		}
		catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<Object>("",status);
		}
		return msg;
	}

}
