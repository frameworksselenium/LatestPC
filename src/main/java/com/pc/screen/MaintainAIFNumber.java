

//@Component - This Component used to Verify the AIF NUmber in Account Summary Screen
package com.pc.screen;

import org.apache.log4j.Logger;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.PCThreadCache;

public class MaintainAIFNumber {

	public static String sheetname = "MaintainAIFNumber";
	Common common = CommonManager.getInstance().getCommon();
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));
	public static String AIFNumber;

	public Boolean SCRMaintainAIFNumber() throws Exception {

		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			return false;
		}
		String sScreenToNavigate = common.getSpecifiedExcelValue(sheetname, "NaviToScreen");

		if (sScreenToNavigate.equals("AccountFileSummary")) {
			if (common.WaitForElementExist(Common.o.getObject("eleAccountFileSummary"), Integer.valueOf(HTML.properties.getProperty

("LONGWAIT")))) {
				logger.info("System displayed Account File Summary Page");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should display Account File Summary Page", "System displayed Account File Summary Page", "PASS");
				status = true;
			} else {
				logger.info("System not displayed Account File Summary Page");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should display Account File Summary Page", "System not displayed Account File Summary Page", "FAIL");
				status = false;
			}
		}

		return status;
	}

	/**
	 * @function - This function used to Retrieve AIF Number
	 * @param -
	 *            No Param
	 * @return - true/false
	 * @throws Exception
	 */
	public String RetrieveAIFNumber() throws Exception {

		try {
			AIFNumber = common.ReadElement(Common.o.getObject("txtAIFNum"), Integer.valueOf(HTML.properties.getProperty

("LONGWAIT")));
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "System should display AIF Number", "System displayed" + AIFNumber, "PASS");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return AIFNumber;
	}

	/**
	 * @function This function used to Verify AIF Number
	 * @param -
	 *            No Param
	 * @return - true/false
	 * @throws -
	 *             Exception
	 */
	public boolean VerifyAIFNumber() throws Exception {
		boolean status = false;
		String sApplnValue = "";
		try {

			sApplnValue = common.ReadElement(Common.o.getObject("txtAIFNum"), Integer.valueOf(HTML.properties.getProperty

("LONGWAIT")));

			if (!sApplnValue.equals("")) {
				status = true;
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should display updated AIF Number", "AIF Number is updated to" + sApplnValue, "PASS");
			} else {
				status = false;
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should display updated AIF Number", "AIF Number is not updated to" + sApplnValue, "FAIL");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

}