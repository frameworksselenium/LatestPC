package com.pc.screen;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.PCThreadCache;

public class SearchAccountNote {

	public static String sheetname = "SearchAccountNote";
	Common common = CommonManager.getInstance().getCommon();
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));

	public Boolean SCRSearchAccountNote() throws Exception {

		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			return false;
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

	public boolean Verify(String value) throws Exception {
		Thread.sleep(5000);
		String[] sValue = value.split(":::");
		// boolean sStatus = false;
		switch (sValue[0]) {
		case "VerifySearchAccountNotesName":
			String sstrVal = driver.findElement(Common.o.getObject("funAccountNotesVerify")).getText();
			if (sstrVal.contains(sValue[1])) {
				logger.info("System displayed Account Notes Results - Account Note Name - " + sstrVal);
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should display Account Notes Results", "System displayed Account notes results - Account Notes Name -" + 

sstrVal, "PASS");
				// sStatus = true;
			} else {
				logger.info("System not displayed Account Notes Results");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should display Account Notes Results", "System not displayed Account notes results", "FAIL");
				// sStatus = false;
			}
		}
		return true;
	}

	public boolean CurrentDateFrom() throws Exception {
		boolean status = false;
		String sCurrentDate = SCRCommon.ReturnCurrentDate();
		status = common.SafeAction(Common.o.getObject("edtAccNoteSearchDateFrom"), sCurrentDate, "edt");
		return status;
	}

	public boolean CurrentDateTo() throws Exception {
		boolean status = false;
		String sCurrentDate = SCRCommon.ReturnCurrentDate();
		status = common.SafeAction(Common.o.getObject("edtAccNoteSearchDateTo"), sCurrentDate, "edt");
		return status;
	}

}