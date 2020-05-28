package com.pc.screen;

import org.apache.log4j.Logger;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.PCThreadCache;

public class CreateShellPolicyNote {

	public static String sheetname = "CreateShellPolicyNote";
	Common common = CommonManager.getInstance().getCommon();
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));

	public Boolean SCRCreateShellPolicyNote() throws Exception {

		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			return false;
		}
		if (common.WaitForElementExist(Common.o.getObject("eleShellPolicySummary"), Integer.valueOf(HTML.properties.getProperty

("LONGWAIT")))) {
			logger.info("System displayed Shell Policy Summary page");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "System should display Shell Policy Summary page", "System displayed Shell Policy Summary page", "PASS");
			status = true;
		} else {
			logger.info("System not displayed Shell Policy Summary page");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "System should display Shell Policy Summary page", "System not displayed Shell Policy Summary page", "FAIL");
			status = false;
		}
		return status;
	}

	public boolean Wait(String value) throws Exception {
		if (value.contains("Long")) {
			common.WaitUntilClickable(Common.o.getObject("edtOrganizationName"), Integer.valueOf(HTML.properties.getProperty

("VERYLONGWAIT")));
			// Thread.sleep(5000);
		}
		return true;

	}

	public boolean CheckUpdate() throws Exception {
		boolean sStatus = true;
		// String st =
		// driver.findElement(Common.o.getObject("eleUpdate")).getAttribute("");
		boolean status = common.WaitUntilClickable(Common.o.getObject("eleUpdate"), Integer.valueOf(HTML.properties.getProperty

("NORMALWAIT")));
		if (!status) {
			status = common.SafeAction(Common.o.getObject("eleWorkeComp"), "value", "eleWorkeComp");
		}
		if (!status) {
			sStatus = false;
		}
		return sStatus;
	}

	/**
	 * @function Verify the results for all the conditions, Error messages etc..
	 * @param strValue
	 * @return status
	 * @throws Exception
	 **/
	public boolean VerifyExpectedResults(String strValue) throws Exception {
		boolean status = false;
		String[] sValue = strValue.split(":::");
		String Value = null;
		switch (sValue[0].toUpperCase()) {
		case "TOPIC":
			logger.info("Validating the '" + sValue[0] + "'");
			Value = common.ReadElement(Common.o.getObject("eleShellPolicyTopicErrorMsgs"), Integer.valueOf

(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			break;

		case "TEXT":
			logger.info("Validating the '" + sValue[0] + "'");
			Value = common.ReadElement(Common.o.getObject("eleShellPolicyTextErrorMsgs"), Integer.valueOf

(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			break;

		}
		return status;
	}

}