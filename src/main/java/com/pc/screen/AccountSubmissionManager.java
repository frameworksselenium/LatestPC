package com.pc.screen;

import org.apache.log4j.Logger;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.PCThreadCache;

public class AccountSubmissionManager {

	public static String sheetname = "AccountSubmissionManager";
	Common common = CommonManager.getInstance().getCommon();
	//static Logger logger = Logger.getLogger(sheetname);
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));
	public static String AccountNumber;
	public static String AccountName;

	public Boolean SCRAccountSubmissionManager() throws Exception {

		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			return false;
		}
		String sScreenToNavigate = common.getSpecifiedExcelValue(sheetname, "NaviToScreen");

		if (sScreenToNavigate.equals("New Submission")) {
			if (common.WaitForElementExist(Common.o.getObject("eleNewSubmissionTitle"), Integer.valueOf(HTML.properties.getProperty

("VERYLONGWAIT")))) {
				logger.info("System displayed New Submission Page");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should display New Submission Page", "System displayed New Submission Page", "PASS");
				status = true;
			} else {
				logger.info("System not displayed New Submission Page");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should display New Submission Page", "System not displayed New Submission Page", "FAIL");
				status = false;
			}
		} else if (sScreenToNavigate.equals("Submission Manager")) {
			if (common.WaitForElementExist(Common.o.getObject("eleASMSubmissionManagerTitle"), Integer.valueOf

(HTML.properties.getProperty("LONGWAIT")))) {
				logger.info("System displayed Submission Manager");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should display Submission Manager Page", "System displayed Submission Manager Page", "PASS");
				status = true;
			} else {
				logger.info("System not displayed Submission Manager Page");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should display Submission Manager Page", "System not displayed Submission Manager Page", "FAIL");
				status = false;
			}
		}

		return status;
	}

	/**
	 * @function This function use to select the LOB dynamically
	 * @param value(LOB
	 *            & System)
	 * @return status
	 * @throws Exception
	 */
	public boolean VerifySubmissions(String value) throws Exception {
		boolean status = false;
		String strValue = "";
		String sValue = value;
		try {

			strValue = common.GetTextFromTable(Common.o.getObject("eleASMSearchResultTable"), 2, sValue);
			if (strValue.equalsIgnoreCase(sValue)) {
				status = true;
			} else {
				status = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

}