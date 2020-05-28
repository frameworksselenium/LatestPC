
/**
 * @ClassPurpose This class to analysis the MM Classfication page
 * @Scriptor Siva
 * @ReviewedBy
 * @ModifiedBy 
 * @LastDateModified 
 */
package com.pc.screen;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.PCThreadCache;

public class MMClassification {

	public static String sheetname = "MMClassification";
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();

	public Boolean SCRMMClassification() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

	/*
	 * public boolean VerifyResults(String funValue) throws Exception { boolean
	 * status = false; logger.info("Verify the Results"); String[] sValue =
	 * funValue.split(":::"); String value = null;
	 * 
	 * switch (sValue[0].toUpperCase()) { case "VERIFYERRORMESSAGE":
	 * logger.info("Validation started for the case '"+sValue[0]); value =
	 * common.ReadElement(Common.o.getObject(sValue[1]),
	 * Integer.valueOf(HTML.properties.getProperty("NORMALWAIT"))); status =
	 * common.CompareStringResult("Verify Error message", sValue[2], value); break;
	 * } if(!status) { logger.info(""+sValue[0]+" Fails" );
	 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
	 * PCThreadCache.getInstance().getProperty("methodName"),
	 * ""+sValue[0]+" case should PASS",""+sValue[0]+" case should not fail",
	 * "FAIL"); status = true; } return status; }
	 */

	/**
	 * @function use to verify the results of the use case
	 * @param funValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean VerifyResults(String funValue) throws Exception {
		boolean status = false;
		logger.info("Verify the Results");
		String[] sValue = funValue.split(":::");
		String value = null;

		switch (sValue[0].toUpperCase()) {
		case "VERIFYERRORMESSAGE":
			logger.info("Validation started for the case '" + sValue[0]);
			value = common.ReadElement(Common.o.getObject(sValue[1]), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("Verify Error message", sValue[2], value);
			break;
		case "VERIFYRESULTS":
			logger.info("Validation started for the case '" + sValue[0]);
			value = common.ReadElement(Common.o.getObject(sValue[1]), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("Verify display of value for '" + sValue[1] + "'", sValue[2], value);
			break;
		case "VERIFYELEMENT":
			logger.info("Validation started for the case '" + sValue[0]);
			status = common.WaitUntilClickable(Common.o.getObject(sValue[1]), Integer.valueOf(HTML.properties.getProperty

("LONGESTWAIT")));
		}
		if (!status) {
			logger.info("" + sValue[0] + " Fails");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "" + sValue[0] + " case should PASS", "" + sValue[0] + " case should not fail", "FAIL");
			status = true;
		}
		return status;
	}

	public Boolean AddClassificationClasses(String funValue) throws Exception {
		boolean status = false;
		// String[] sPair = funValue.split(":::");
		String[] sValue = funValue.split(":::");
		String sPrimaryWindow = driver.getWindowHandle();
		java.util.Set<String> allWindowHandles = driver.getWindowHandles();
		for (String handle : allWindowHandles) {
			if (driver.switchTo().window(handle).getTitle().contains("CUE classification")) {
				driver.switchTo().window(handle);
				logger.info("Classification window is displayed and swithced to that window");
				status = true;
				break;
			}
		}
		if (status) {
			status = common.SafeAction(Common.o.getObject("nedtClassificationSCI"), sValue[0], "nedtClassificationSCI");
			status = common.SafeAction(Common.o.getObject("nedtClassificationDesc"), sValue[1], "nedtClassificationDesc");
			status = common.SafeAction(Common.o.getObject("DrpClassificationState"), sValue[2], "DrpClassificationState");
			status = common.SafeAction(Common.o.getObject("btnClassificationSearch"), "", "btnClassificationSearch");
			// Thread.sleep(3);
			status = common.SafeAction(Common.o.getObject("btnSelectClassCode"), "", "btnSelectClassCode");
			// Thread.sleep(3);
			status = common.SafeAction(Common.o.getObject("drpTXWC"), sValue[3], "drpTXWC");
			status = common.SafeAction(Common.o.getObject("drpFCode"), sValue[4], "drpFCode");
			status = common.SafeAction(Common.o.getObject("sclAssign"), "", "sclAssign");
			status = common.SafeAction(Common.o.getObject("btnAssign"), "", "btnAssign");
			// status= SCRCommon.PageVerify("EditClassification");
			// close the child window
			// driver.close();
			// Switch to the parent window

			driver.switchTo().window(sPrimaryWindow);
		} else {
			status = false;
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "Switch to Classify window", " Could not swithc to classify window", "FAIL");
		}

		return status;
	}

	/**
	 * @function Use to upadte the policy class data
	 * @param funValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean UpdatePolicyClassData(String funValue) throws Exception {
		boolean status = false;
		status = common.ActionOnTable(Common.o.getObject("MMCla_PolTerms_Tbl"), 2, 0, funValue, "img");
		status = common.SafeAction(Common.o.getObject("eleUpdatePolicyClassData"), "eleUpdatePolicyClassData", 

"eleUpdatePolicyClassData");
		return status;

	}
}
