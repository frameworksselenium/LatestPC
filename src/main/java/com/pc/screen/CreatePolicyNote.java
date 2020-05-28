package com.pc.screen;

import org.apache.log4j.Logger;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.PCThreadCache;

public class CreatePolicyNote {

	public static String sheetname = "CreatePolicyNote";
	Common common = CommonManager.getInstance().getCommon();
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));

	public Boolean SCRCreatePolicyNote() throws Exception {

		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			return false;
		}
		// Updated in SCRcommon
		/*
		 * if(common.WaitUntilClickable(Common.o.getObject("elePolicySummaryPage"),
		 * Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")))) {
		 * logger.info("System displayed Policy Summary page");
		 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
		 * PCThreadCache.getInstance().getProperty("methodName"),
		 * "System should display Policy Summary page"
		 * ,"System displayed Policy Summary page", "PASS"); status = true; } else {
		 * logger.info("System not displayed Policy Summary page");
		 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
		 * PCThreadCache.getInstance().getProperty("methodName"),
		 * "System should display Policy Summary page"
		 * ,"System not displayed Policy Summary page", "FAIL"); status = false; }
		 */
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

}