package estimation.controller;

import estimation.bean.VAF;
import estimation.service.VAFService;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by xuawai on 16/06/2017.
 */
@RestController
@RequestMapping(value = "/estimation")
public class VAFController {

	@Autowired
	private VAFService vafService;

	//增加VAF
	@RequestMapping(value = "/addVAF/{id}", method = RequestMethod.POST)
	public void addVAF(@RequestBody JSONObject jsonObject, @PathVariable String id) {
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
        System.out.println(id);
		vafService.add(id, vaf);
	}

	@RequestMapping(value = "/changeVAF/{id}", method = RequestMethod.POST)
	public Object changeVAF(@RequestBody JSONObject jsonObject, @PathVariable String id) {
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
			// TODO: handle exception
			msg.put("flag", false);
		}
		return msg;
	}

}
