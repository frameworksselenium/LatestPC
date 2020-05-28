
/**
 * @ClassPurpose This Class used for the policy move verification use case
 * @Scriptor Sojan David
 * @ReviewedBy
 * @ModifiedBy Sojan David
 * @LastDateModified 3/27/2017
 */
package com.pc.screen;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.pc.constants.PCConstants;
import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.PCThreadCache;
import com.pc.utilities.XlsxReader;

public class PolicyMoveVerification {

	public static String sheetname = "PolicyMoveVerification";
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();

	/**
	 * @function Use to perform all the objects/elements/functions from the policy
	 *           move verification move excel sheet
	 * @return true/false
	 * @throws Exception
	 */
	public Boolean SCRPolicyMoveVerification() throws Exception {

		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			return false;
		}
		/*
		 * if(Common.WaitUntilClickable(driver, o.getObject("assertforPolicyINFOPage"),
		 * Integer.valueOf(Common.pro.getProperty("VERYLONGWAIT")))) {
		 * logger.info("System displayed Organisation Page");
		 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
		 * PCThreadCache.getInstance().getProperty("methodName"),
		 * "System should display Organisation Page"
		 * ,"System displayed Organisation Page", "PASS"); status = true; } else {
		 * logger.info("System not displayed Organisation Page");
		 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
		 * PCThreadCache.getInstance().getProperty("methodName"),
		 * "System should display Organisation Page"
		 * ,"System not displayed Organisation Page", "FAIL"); status = false; }
		 */
		return status;
	}

	/**
	 * @function use to fill the data sheet for testdata
	 * @param sValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean DataFill(String sValue) throws Exception {
		boolean status = false;
		HashMap<String, Object> updateColumnNameValues = new HashMap<String, Object>();
		HashMap<String, Object> whereConstraint = new HashMap<String, Object>();
		XlsxReader sXL = XlsxReader.getInstance();
		String accountNumber = PCThreadCache.getInstance().getProperty(PCConstants.CACHE_ACCOUNT_NUMBER);
		String policyNumber = PCThreadCache.getInstance().getProperty(PCConstants.CACHE_POLICY_NUMBER);
		String tcID = PCThreadCache.getInstance().getProperty("TCID");
		int rowcount = sXL.getRowCount(sheetname);
		for (int i = 1; i < rowcount; i++) {
			String value = sXL.getCellData(sheetname, 0, i);
			if (!value.isEmpty()) {
				if (PCThreadCache.getInstance().getProperty("TCID").equals(value)) {
					if (sValue.equals("AccountNumber")) {
						updateColumnNameValues.clear();
						whereConstraint.clear();
						updateColumnNameValues.put(PCConstants.AccountNumber, accountNumber);
						whereConstraint.put(PCConstants.ID, tcID);
						PCThreadCache.getInstance().setProperty("AccountNumber", accountNumber);
						//status = sXL.executeUpdateQuery(PCConstants.SHEET_POLICYMOVEVERIFICATION, updateColumnNameValues, whereConstraint);
						// status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"),
						// "Update PolicyMoveVerification Set AccountNumber =
						// '"+CreateAccount.AccountNumber+"' where
						// ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
						status = true;
						break;
					} else {
						updateColumnNameValues.clear();
						whereConstraint.clear();
						updateColumnNameValues.put(PCConstants.AccountNumber, accountNumber);
						whereConstraint.put(PCConstants.ID, tcID);
						PCThreadCache.getInstance().setProperty("AccountNumber", accountNumber);
						//status = sXL.executeUpdateQuery(PCConstants.SHEET_POLICYMOVEVERIFICATION, updateColumnNameValues, whereConstraint);

						updateColumnNameValues.clear();
						whereConstraint.clear();
						updateColumnNameValues.put(PCConstants.PolicyNumber, policyNumber);
						whereConstraint.put(PCConstants.ID, tcID);
						PCThreadCache.getInstance().setProperty("PolicyNumber", policyNumber);
						//status = sXL.executeUpdateQuery(PCConstants.SHEET_POLICYMOVEVERIFICATION, updateColumnNameValues, whereConstraint);
						// status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"),
						// "Update PolicyMoveVerification Set AccountNumber =
						// '"+CreateAccount.AccountNumber+"' where
						// ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
						// status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"),
						// "Update PolicyMoveVerification Set PolicyNumber = '"+Payment.PolicyNumber+"'
						// where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
						status = true;
						break;
					}
				}
			}
		}
		return status;
	}

	/**
	 * @function use to verify the results of the testcase
	 * @param funValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean PolicyMoveVerify(String funValue) throws Exception {

		boolean status = true;
		String[] sValue = funValue.split(":::");
		String wait = HTML.properties.getProperty("SHORTWAIT");
		Integer x = Integer.valueOf(wait);
		switch (sValue[0].toUpperCase()) {
		/*
		 * case "CHECKINGRESET": logger.info("Validating the '"+sValue[0]+"'"); boolean
		 * checkSelectisResetted =
		 * driver.findElements(Common.o.getObject("eleSelect")).size() != 0;
		 * if(!checkSelectisResetted == true){
		 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
		 * PCThreadCache.getInstance().getProperty("methodName"),
		 * "System should clear the field after clicking the reset",
		 * "All the fileds are resetted", "PASS"); status = true; }else{
		 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
		 * PCThreadCache.getInstance().getProperty("methodName"),
		 * "System should clear the field after clicking the reset",
		 * "Fileds are not resetted", "FAIL"); // status = false; } break;
		 */
		case "CHECKINGRESET":
			boolean checkSelectisResetted = driver.findElements(Common.o.getObject("eleSelect")).size() != 0;
			logger.info("Validating the '" + sValue[0] + "'");
			if (!checkSelectisResetted == true) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should clear the field after clicking the reset", "All the fileds are resetted", "PASS");
			} else {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should clear the field after clicking the reset", "Fileds are not resetted", "FAIL");
			}
			break;

		case "POLICYMOVECANCEL":
			logger.info("Validating the '" + sValue[0] + "'");
			String expectedValue1 = sValue[1];
			String actualValue1 = common.ReadElement(Common.o.getObject("txtCancelConfirmScreen"), x);
			if (expectedValue1.contains(actualValue1)) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "'" + expectedValue1 + "'", "'" + actualValue1 + "'", "PASS");
				status = true;
			} else {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "'" + expectedValue1 + "'", "'" + actualValue1 + "'", "FAIL");
				// status = false;
			}
			break;

		case "ACCOUNTMERGEPOPUPCANCEL":
			logger.info("Validating the '" + sValue[0] + "'");
			status = SCRCommon.PageVerify("PopUpCancel");
			boolean chkPopUpCancel = driver.findElements(Common.o.getObject("elePopUpCancel")).size() != 0;
			if (chkPopUpCancel == true) {
				driver.findElement(Common.o.getObject("elePopUpCancel")).click();
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "It should Cancel PopUp Button", "Clicked the Cancel PopUp Button", "PASS");
				status = true;
				status = SCRCommon.PageVerify("MergeAccounts");
				boolean chkMergeButton = driver.findElements(Common.o.getObject("eleMergeAccounts")).size() != 0;
				if (chkMergeButton == true) {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Merge Button should available", "Merge Button is available", "PASS");
					status = true;
				} else {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Merge Button should available", "Merge Button is not available", "FAIL");
					// status = false;
				}
			} else {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "It should Cancel PopUp Button", "Unable to click the Cancel PopUp Button", "FAIL");
				// status = false;
			}
			break;

		case "CHECKINGMINFIELDVALIDATION":
			String expectedValue = sValue[1];
			status = SCRCommon.PageVerify("SameTgtasSrc");
			String actualValue = common.ReadElement(Common.o.getObject("eleSameTgtasSrc"), x);
			logger.info("Validating the '" + sValue[0] + "'");
			if (expectedValue.equals(actualValue)) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "'" + expectedValue + "'", "'" + actualValue + "'", "PASS");
				status = true;
			} else {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "'" + expectedValue + "'", "'" + actualValue + "'", "FAIL");
			}
			break;

		case "CHECKINGATTHREECHAR":
			logger.info("Validating the '" + sValue[0] + "'");
			String twoAlphaAccName = sValue[1];
			String expectedValue2 = "" + twoAlphaAccName + " At least three characters are required when Company Name is not an exact match";
			status = SCRCommon.PageVerify("SameTgtasSrc");
			String actualValue2 = common.ReadElement(Common.o.getObject("eleSameTgtasSrc"), x);
			if (expectedValue2.equals(actualValue2)) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "'" + expectedValue2 + "'", "'" + actualValue2 + "'", "PASS");
				status = true;
			} else {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "'" + expectedValue2 + "'", "'" + actualValue2 + "'", "FAIL");
				// status = false;
			}
			break;

		case "CHECKINGEXACTMATCHWITHCHECK":
			logger.info("Validating the '" + sValue[0] + "'");
			String twoAlphaAccName1 = sValue[1];
			String expectedValue3 = "" + twoAlphaAccName1 + " Wildcard (*) can not be used when Company Name is an exact match";
			status = SCRCommon.PageVerify("SameTgtasSrc");
			String actualValue3 = common.ReadElement(Common.o.getObject("eleSameTgtasSrc"), x);
			if (expectedValue3.equals(actualValue3)) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "'" + expectedValue3 + "'", "'" + actualValue3 + "'", "PASS");
				status = true;
			} else {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "'" + expectedValue3 + "'", "'" + actualValue3 + "'", "FAIL");
				// status = false;
			}
			break;

		case "VERIFALLIANCEMESSAGE":
			logger.info("Validating the '" + sValue[0] + "'");
			// String excelAlliDataValue = sValue[1];
			String expectedValue4 = sValue[1];
			// String expectedValue4 = ""+excelAlliDataValue+" must be 7 to 9 digit number";
			status = SCRCommon.PageVerify("SameTgtasSrc");
			String actualValue4 = common.ReadElement(Common.o.getObject("eleSameTgtasSrc"), x);
			if (expectedValue4.equals(actualValue4)) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "'" + expectedValue4 + "'", "'" + actualValue4 + "'", "PASS");
				status = true;
			} else {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "'" + expectedValue4 + "'", "'" + actualValue4 + "'", "FAIL");
				// status = false;
			}
			break;

		case "VERIFAIFNUMBER":
			logger.info("Validating the '" + sValue[0] + "'");
			// String excelAIFDataValue = sValue[1];
			// String expectedValue5 = ""+excelAIFDataValue+" must be 10 digit number";
			String expectedValue5 = sValue[1];
			status = SCRCommon.PageVerify("SameTgtasSrc");
			String actualValue5 = common.ReadElement(Common.o.getObject("eleSameTgtasSrc"), x);
			if (expectedValue5.equals(actualValue5)) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "'" + expectedValue5 + "'", "'" + actualValue5 + "'", "PASS");
				status = true;
			} else {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "'" + expectedValue5 + "'", "'" + actualValue5 + "'", "FAIL");
				// status = false;
			}
			break;

		case "VERIFYACCNUMMESSAGE":
			logger.info("Validating the '" + sValue[0] + "'");
			// String excelAccNumDataValue = sValue[1];
			// String expectedValue6 = ""+excelAccNumDataValue+" must be 15 digit number";
			String expectedValue6 = sValue[1];
			status = SCRCommon.PageVerify("SameTgtasSrc");
			String actualValue6 = common.ReadElement(Common.o.getObject("eleSameTgtasSrc"), x);
			if (expectedValue6.equals(actualValue6)) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "'" + expectedValue6 + "'", "'" + actualValue6 + "'", "PASS");
				status = true;
			} else {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "'" + expectedValue6 + "'", "'" + actualValue6 + "'", "FAIL");
				// status = false;
			}
			break;

		case "VERIFYFEINNUMMESSAGE":
			logger.info("Validating the '" + sValue[0] + "'");
			String expectedValue7 = sValue[1];
			// String expectedValue7 = ""+excelFEINNumDataValue1+" must be 15 digit number";
			status = SCRCommon.PageVerify("SameTgtasSrc");
			String actualValue7 = common.ReadElement(Common.o.getObject("eleSameTgtasSrc"), x);
			if (expectedValue7.equals(actualValue7)) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "'" + expectedValue7 + "'", "'" + actualValue7 + "'", "PASS");
				status = true;
			} else {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "'" + expectedValue7 + "'", "'" + actualValue7 + "'", "FAIL");
				// status = false;
			}
			break;

		case "VERIFYEXPERIANNUMMESSAGE":
			logger.info("Validating the '" + sValue[0] + "'");
			String expectedValue8 = sValue[1];
			// String expectedValue8 = ""+excelExperianNumDataValue1+" must be 15 digit
			// number";
			status = SCRCommon.PageVerify("SameTgtasSrc");
			String actualValue8 = common.ReadElement(Common.o.getObject("eleSameTgtasSrc"), x);
			if (expectedValue8.equals(actualValue8)) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "'" + expectedValue8 + "'", "'" + actualValue8 + "'", "PASS");
				status = true;
			} else {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "'" + expectedValue8 + "'", "'" + actualValue8 + "'", "FAIL");
				// status = false;
			}
			break;
		}
		/**
		 * if(status == false){
		 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
		 * PCThreadCache.getInstance().getProperty("methodName"),
		 * "PolicyMoveVerification function should have executed properly",
		 * "PolicyMoveVerification function is not executed properly", "FAIL"); }
		 */
		return status;
	}
}