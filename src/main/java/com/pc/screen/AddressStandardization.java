
/**
 * @ClassPurpose This Class used for the Address Standardization usecase
 * @Scriptor Kumar
 * @ReviewedBy
 * @ModifiedBy Sojan David
 * @LastDateModified 3/30/2017
 */

package com.pc.screen;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.PCThreadCache;

public class AddressStandardization {

	public static String sheetname = "AddressStandardization";
	//static Logger logger = Logger.getLogger(sheetname);
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();

	/**
	 * @function Use to perform all the objects/elements/functions from the Address
	 *           Standardization excel sheet
	 * @return true/false
	 * @throws Exception
	 */
	public Boolean SCRAddressStandardization() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			return false;
		}
		String strScreenNavi = common.getSpecifiedExcelValue(sheetname, "NaviToScreen");

		if (strScreenNavi.equals("AccountFileLocation")) {
			if (common.WaitForElementExist(Common.o.getObject("eleAccountFileLocationTitle"), Integer.valueOf

(HTML.properties.getProperty("LONGWAIT")))) {
				logger.info("System displayed Account File Location Page");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should display Account File Location Page", "System displayed Account File Location Page", "PASS");
				status = true;
			} else {
				logger.info("System not displayed Account File Location Page");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should display Account File Location Page", "System not displayed Account File Location Page", "FAIL");
				status = false;
			}
		} else if (strScreenNavi.equals("LocationInformation")) {
			if (common.WaitForElementExist(Common.o.getObject("eleLocationInformationTitle"), Integer.valueOf

(HTML.properties.getProperty("LONGWAIT")))) {
				logger.info("System displayed Location Information Page");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should display Location Information Page", "System displayed Location Information Page", "PASS");
				status = true;
			} else {
				logger.info("System not displayed Location Information Page");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should display Location Information Page", "System not displayed Location Information Page", "FAIL");
				status = false;
			}
		}
		return status;
	}

	/**
	 * @function This function
	 * @param value
	 *            (Success Messages)
	 * @return status
	 * @throws Exception
	 */
	public boolean SelectState(String strFunValue) throws Exception {
		boolean status = false;
		String sValue = strFunValue;
		JavascriptExecutor js = (JavascriptExecutor) ManagerDriver.getInstance().getWebDriver();
		status = common.SafeSelectGWListBox(Common.o.getObject("lstLocationState"), sValue, Integer.valueOf(HTML.properties.getProperty

("VERYLONGWAIT")));
		WebElement sZipcode = common.returnObject(Common.o.getObject("lstLocationState"));
		status = SCRCommon.JavaScriptDynamicWait(sZipcode, js);
		return status;
	}

	/**
	 * @function This function
	 * @param value
	 *            (Success Messages)
	 * @return status
	 * @throws Exception
	 */
	public boolean SelectZipcode(String strFunValue) throws Exception {
		boolean status = false;
		String sValue = strFunValue;
		JavascriptExecutor js = (JavascriptExecutor) ManagerDriver.getInstance().getWebDriver();
		status = common.SafeSelectGWListBox(Common.o.getObject("lsjLocationZipCode"), sValue, Integer.valueOf

(HTML.properties.getProperty("VERYLONGWAIT")));
		WebElement sZipcode = common.returnObject(Common.o.getObject("lsjLocationZipCode"));
		status = SCRCommon.JavaScriptDynamicWait(sZipcode, js);
		return status;
	}

	/**
	 * @function This function verify Messages
	 * @param value
	 *            (Success Messages)
	 * @return status
	 * @throws Exception
	 */
	/*
	 * public boolean VerifyStandardizationMessages(String strFunValue) throws
	 * Exception { boolean status = false; String sApplnValue = ""; String[] sValue
	 * = strFunValue.split(":::");
	 * 
	 * try { switch (sValue[0]) { case "AccountLocation": sApplnValue =
	 * Common.ReadElement(Common.o.getObject("eleLocationInfoMsg"),
	 * Integer.valueOf(HTML.properties.getProperty("LONGWAIT"))); case
	 * "AccountContact": sApplnValue =
	 * Common.ReadElement(Common.o.getObject("eleAccount_Message"),
	 * Integer.valueOf(HTML.properties.getProperty("LONGWAIT"))); }
	 * 
	 * 
	 * if (sApplnValue.equals(sValue[1])) { status = true;
	 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
	 * PCThreadCache.getInstance().getProperty("methodName"),
	 * "System should display expected standardized message","System displayed"
	 * +sApplnValue+ "message as expected", "PASS"); } else { status = false;
	 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
	 * PCThreadCache.getInstance().getProperty("methodName"),
	 * "System should display expected standardized message"
	 * ,"System did not display" +sApplnValue+ "message as expected", "FAIL"); }
	 * 
	 * } catch(Exception e) { e.printStackTrace(); } return status; }
	 */

	/**
	 * @function Use to enter the current date
	 * @return true/false
	 * @throws Exception
	 */
	public boolean DefaultEffectiveDate() throws Exception {
		boolean status = false;
		String sCurrentDate = SCRCommon.ReturnCurrentDate();
		status = common.SafeAction(Common.o.getObject("edtEffDate"), sCurrentDate, "edt");
		return status;
	}

	/**
	 * @function This function use to select the LOB dynamically
	 * @param value(LOB
	 *            & System)
	 * @return status
	 * @throws Exception
	 */
	public boolean SelectLOB(String value) throws Exception {
		boolean status = false;
		String[] sValue = value.split(":::");
		try {
			status = common.ActionOnTable(Common.o.getObject("funSelectLOB"), 1, 0, sValue[0], sValue[1], "a");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * @function This function use to select multiple LOB based on param
	 * @param value(LOB
	 *            & System)
	 * @return status
	 * @throws Exception
	 */
	public boolean SelectMultipleLOB(String value) throws Exception {
		boolean status = false;
		String[] sValue = value.split(":::");
		try {
			status = common.ActionOnTableSelect(Common.o.getObject("eleProductNameTable"), 2, 0, sValue[0], sValue[1], sValue[2], 

"div");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
}
