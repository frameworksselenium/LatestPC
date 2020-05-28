package com.pc.screen;

import org.apache.log4j.Logger;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.PCThreadCache;

public class VehicleInfo {

	public static String sheetname = "VehicleInfo";
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();

	public Boolean SCRVehicleInfo() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			return false;
		}
		String sScreentoNavi = common.getSpecifiedExcelValue(sheetname, "NaviToScreen");
		if (sScreentoNavi.contains("VehicleIDCard")) {
			if (common.WaitForElementExist(Common.o.getObject("eleAutoIdCardTitle"), Integer.valueOf(HTML.properties.getProperty

("LONGWAIT")))) {
				logger.info("System displayed Auto Id Card Page");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should display Auto Id Card Page", "System displayed Auto Id Card Page", "PASS");
				status = true;
			} else {
				logger.info("System not displayed Auto Id Card Page");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should display Auto Id Card Page", "System not displayed Auto Id Card Page", "FAIL");
				status = false;
			}
		} else if (sScreentoNavi.contains("PolicyInfo")) {
			if (common.WaitForElementExist(Common.o.getObject("eleCLATitlePolicyInfo"), Integer.valueOf(HTML.properties.getProperty

("LONGWAIT")))) {
				logger.info("System displayed CLA Policy Info Page");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should display CLA Policy Info Page", "System displayed CLA Policy Info Page", "PASS");
				status = true;
			} else {
				logger.info("System not displayed CLA Policy Info Page");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should display CLA Policy Info Page", "System not displayed CLA Policy Info Page", "FAIL");
				status = false;
			}
		}

		return status;
	}

	/**
	 * @function This function use to select Vehicle dynamically
	 * @param value(VIN)
	 * @return status
	 * @throws Exception
	 */
	public boolean SelectVehicle(String value) throws Exception {
		boolean status = false;
		String sValue[] = value.split(":::");
		// String sValue = value;
		try {
			if (sValue.length > 1) {
				for (int j = 0; j <= sValue.length - 1; j++) {
					status = common.SelectCheckBoxOnTable(Common.o.getObject("eleAutoIdCardVehicleDetailsTable"), 6, 0, 

sValue[j], "CheckBox", "img");
				}
			} else {
				status = common.SelectCheckBoxOnTable(Common.o.getObject("eleAutoIdCardVehicleDetailsTable"), 6, 0, sValue[0], 

"CheckBox", "img");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

}