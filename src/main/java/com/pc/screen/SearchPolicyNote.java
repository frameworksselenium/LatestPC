package com.pc.screen;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.PCThreadCache;

public class SearchPolicyNote {

	public static String sheetname = "SearchPolicyNote";
	Common common = CommonManager.getInstance().getCommon();
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));

	public Boolean SCRSearchPolicyNote() throws Exception {

		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			return false;
		}
		// Updated in SCRcommon
		/*
		 * if(common.WaitUntilClickable(Common.o.getObject("elePolicyNotePage"),
		 * Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")))) {
		 * logger.info("System displayed Policy Search Note Page");
		 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
		 * PCThreadCache.getInstance().getProperty("methodName"),
		 * "System should display Policy Search Note Page"
		 * ,"System displayed Policy Search Note Page", "PASS"); status = true; } else {
		 * logger.info("System not displayed Policy Search Note Page");
		 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
		 * PCThreadCache.getInstance().getProperty("methodName"),
		 * "System should display Policy Search Note Page"
		 * ,"System not displayed Policy Search Note Page", "FAIL"); status = false; }
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

	public boolean Verify(String value) throws Exception {
		Thread.sleep(5000);
		String[] sValue = value.split(":::");
		boolean sStatus = false;
		String sstrVal;
		switch (sValue[0]) {
		case "VerifySearchAccountNotesName":
			sstrVal = driver.findElement(Common.o.getObject("funAccountNotesVerify")).getText();
			if (sstrVal.contains(sValue[1])) {
				logger.info("System displayed Account Notes Results - Account Note Name - " + sstrVal);
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should display Account Notes Results", "System displayed Account notes results - Account Notes Name -" + 

sstrVal, "PASS");
				sStatus = true;
			} else {
				logger.info("System not displayed Account Notes Results");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should display Account Notes Results", "System not displayed Account notes results", "FAIL");
				sStatus = false;
			}
		case "VerifySearchPolicyNotesName":
			sstrVal = driver.findElement(Common.o.getObject("funPolicyNotesVerify1")).getText();
			if (sstrVal.contains(sValue[1])) {
				logger.info("System displayed Policy Notes Results - Account Note Name - " + sstrVal);
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should display Policy Notes Results", "System displayed Policy notes results - Account Notes Name -" + 

sstrVal, "PASS");
				sStatus = true;
			} else {
				logger.info("System not displayed Policy Notes Results");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should display Policy Notes Results", "System not displayed Policy notes results", "FAIL");
				sStatus = false;
			}
		}
		return sStatus;
	}
}