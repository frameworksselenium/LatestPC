
package com.pc.screen;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import com.pc.constants.PCConstants;
import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.FlatFile;
import com.pc.utilities.HTML;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.PCThreadCache;
import com.pc.utilities.XlsxReader;

public class ICONValidation {

	public static String sheetname = "ICONValidation";
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();

	public Boolean SCRICONValidation() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = true;
		}
		return status;
	}

	/**
	 * Function to Navigate to pages
	 * 
	 * @param funValue
	 * @return
	 * @throws Exception
	 */
	public boolean ReferencePageNavigation(String strPageName) throws Exception {
		boolean status = false;
		boolean pageExist = false;
		logger.info("Verifying the PageReference");
		String value;
		String[] arrPageName;
		String wait = HTML.properties.getProperty("NORMALWAIT");
		Integer x = Integer.valueOf(wait);
		arrPageName = strPageName.split(":::");
		switch (arrPageName[0].toUpperCase()) {
		case "ACCOUNT_SUMMARY":
			logger.info("Verification stated for " + strPageName);
			int iCheckAccountSummary = common.ElementSize(Common.o.getObject("eleAccountFileSummary"));
			// status =
			// common.WaitUntilClickable(Common.o.getObject("eleAccountFileSummary"), x);
			// status=common.ElementExistOrNotTrue(strPageName, "AccountSummary Page should
			// be displayed", "AccountSummary Page is displayed on screen",
			// Common.o.getObject("eleAccountFileSummary"));
			if (iCheckAccountSummary == 0 && !status) {
				// Clicking on Account Number on Info to Navigate'
				if (common.ElementExist(Common.o.getObject("eljAccountNumber")))
				// if(common.ElementExist(Common.o.getObject("eleAccountNumberClickPolicy")))
				{
					status = common.SafeAction(Common.o.getObject("eljAccountNumber"), "elj", "elj");
					status = common.WaitUntilClickable(Common.o.getObject("eleAccountFileSummary"), x);
					status = common.ElementExistOrNotTrue(strPageName, "AccountSummary Page should be displayed on clicking the 'AccountNumber' in InfoBar", "AccountSummary Page is displayed", Common.o.getObject("eleAccountFileSummary"));
				}
			}
			break;
		case "ACCOUNT_CONTACTS":
			logger.info("Verification stated for " + strPageName);
			int iCheckAccountSummary2 = common.ElementSize(Common.o.getObject("eleAccountFileSummary"));
			// pageExist =
			// common.WaitUntilClickable(Common.o.getObject("eleAccountFileSummary"), x);
			// status=common.ElementDisplayed(Common.o.getObject("eleAccountFileSummary"));
			// if(status)
			if (iCheckAccountSummary2 == 1) {
				// clicking on Contacts from Account summary screen
				if (common.ElementExist(Common.o.getObject("eleContacts"))) {
					status = common.SafeAction(Common.o.getObject("eleContacts"), "ele", "ele");
					pageExist = common.WaitUntilClickable(Common.o.getObject("eleAssertContact"), x);
					status = common.ElementExistOrNotTrue(strPageName, "Account Contacts Page should be displayed on clicking the 'Contacts' tab", "Account Contacts Page is displayed", Common.o.getObject("eleAssertContact"));

				} else {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Verify -Account - Contacts element is displayed ", "Account-Contact Element is not dispalyed to navigate to Contact screen", "FAIL");
				}

			} else {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Verify -Account Summary is displayed ", "Navigation to AccountContact page failed since Account Summary page is NOT displayed.", "FAIL");
			}
			break;
		case "POLICY_INFO":
			logger.info("Verification stated for " + strPageName);
			// status =
			// common.WaitUntilClickable(Common.o.getObject("eleAccountFileSummary"), x);
			int iCheckAccountSummary1 = common.ElementSize(Common.o.getObject("eleAccountFileSummary"));
			// if it is not in AccountSummary page then navigate to AccountSummary
			// if(common.WaitUntilClickable(Common.o.getObject("eleAccountSummary"),x) &&
			// !status)
			if (iCheckAccountSummary1 == 0) {
				status = common.SafeAction(Common.o.getObject("eleAccountSummary"), "ele", "ele");
				status = common.WaitUntilClickable(Common.o.getObject("eleAccountFileSummary"), x);
				status = common.ElementExistOrNotTrue(strPageName, "AccountSummary Page should be displayed on clicking the 'Summary' tab", "AccountSummary Page is displayed", Common.o.getObject("eleAccountFileSummary"));
			}
			/*
			 * else if(common.WaitUntilClickable(Common.o.getObject(
			 * "eleCommonUpToAccountFileSummary"), x)) //landing page is not Account Summary
			 * & no AccoutnSummary tab is avaialble to navigate {
			 * status=common.SafeAction(Common.o.getObject("eleCommonUpToAccountFileSummary"
			 * ), "ele", "ele"); status=common.ElementExistOrNotTrue(strPageName,
			 * "AccountSummary Page should be displayed on clicking the 'Upto AccountSummary'"
			 * , "AccountSummary Page is displayed",
			 * Common.o.getObject("eleAccountFileSummary")); }
			 */
			if (status || iCheckAccountSummary1 == 1) {
				// clicking on Contacts from Account summary screen
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Verify - AccountFileSummary Page is displayed", "AccountFileSummary Page displayed", "PASS");
				if (common.ElementExist(Common.o.getObject("eleAccountSummary_PolicyTermsTbl"))) {
					// value=common.GetTextFromTable(Common.o.getObject("eleAccountSummary_PolicyTermsTbl"),
					// 0, 1);
					// status =
					// common.ActionOnTable(Common.o.getObject("eleAccountSummary_PolicyTermsTbl"),
					// 1, 1, arrPageName[1], arrPageName[1], "a");
					status = SCRCommon.ActionOnTableForICON(Common.o.getObject("eleAccountSummary_PolicyTermsTbl"), 1, 1, arrPageName[1], "a");
					pageExist = common.WaitUntilClickable(Common.o.getObject("elePolicyInfo_tab"), x);
					status = common.SafeAction(Common.o.getObject("elePolicyInfo_tab"), "ele", "ele");
					status = common.ElementExistOrNotTrue(strPageName, "PolicyInfo Page should be navigated on clicking the PolicyInfo tab", "PolicyInfo Page is displayed", Common.o.getObject("elePolicyInfo_Title"));
				}
			} else if (common.WaitUntilClickable(Common.o.getObject("elePolicyInfo_tab"), x)) // directly clicking on Policy Info tab if dispayed
			{
				status = common.SafeAction(Common.o.getObject("elePolicyInfo_tab"), "ele", "ele");
				status = common.ElementExistOrNotTrue(strPageName, "PolicyInfo Page should be navigated on clicking the PolicyInfo tab", "PolicyInfo Page is displayed", Common.o.getObject("elePolicyInfo_Title"));
			} else {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Verify - AccountFileSummary Page is displayed", "AccountFileSummary Page is NOT displayed", "FAIL");

			}
			break;
		case "POLICY_SUMMARY":
			logger.info("Verification stated for " + strPageName);
			if (common.WaitUntilClickable(Common.o.getObject("elePolicyInfo_Title"), x)) // policy info
			{
				status = common.SafeAction(Common.o.getObject("elePolicySummary_Tab"), "ele", "ele");
				status = common.ElementExistOrNotTrue(strPageName, "PolicySummary Page should be navigated on clicking the PolicySummary from PolicyInfo screen.", "PolicySummary Page is displayed", Common.o.getObject("elePolicySummary"));

			} else if (common.WaitUntilClickable(Common.o.getObject("eleAccountFileSummary"), x)) {
				if (common.WaitUntilClickable(Common.o.getObject("eleAccountSummary_PolicyTermsTbl"), x)) {
					value = common.GetTextFromTable(Common.o.getObject("eleAccountSummary_PolicyTermsTbl"), 1, 1);
					status = common.ActionOnTable(Common.o.getObject("eleAccountSummary_PolicyTermsTbl"), 1, 1, value, value, "a");
					pageExist = common.WaitUntilClickable(Common.o.getObject("elePolicySummary"), x);
					status = common.ElementExistOrNotTrue(strPageName, "PolicySummary Page should be navigated on clicking the PolicyNumber in Table", "PolicyInfo Page is displayed", Common.o.getObject("elePolicySummary"));
				}
			}
			break;
		case "POLICY_INFO_DIRECT":
			logger.info("Verification stated for " + strPageName);
			if (common.WaitUntilClickable(Common.o.getObject("elePolicyInfoByBound"), x)) // policy info
			{
				status = common.SafeAction(Common.o.getObject("elePolicyInfoByBound"), "ele", "ele");
				status = common.ElementExistOrNotTrue(strPageName, "PolicyInfo Page should be navigated on clicking the PolicyInfo tab", "PolicySummary Page is displayed", Common.o.getObject("elePolicyInfoPage"));

			}
			break;
		case "POLICY_SUMMARY_DIRECT":
			logger.info("Verification stated for " + strPageName);
			if (common.WaitUntilClickable(Common.o.getObject("elePolicySummaryByPolicyNum"), x)) // policy info
			{
				status = common.SafeAction(Common.o.getObject("elePolicySummaryByPolicyNum"), "ele", "ele");
				status = common.ElementExistOrNotTrue(strPageName, "PolicySummary Page should be navigated on clicking the PolicySummary from PolicyInfo screen.", "PolicySummary Page is displayed", Common.o.getObject("elePolicySummary"));

			}
			break;
		case "LOCATIONS":
			logger.info("Verification stated for " + strPageName);
			if (common.WaitUntilClickable(Common.o.getObject("eleLocationsSummary"), x)) // policy info
			{
				status = common.SafeAction(Common.o.getObject("eleLocationsSummary"), "ele", "ele");
				status = common.ElementExistOrNotTrue(strPageName, "Locations Page should be navigated on clicking the Locations from PolicyInfo screen.", "Locations Page is displayed", Common.o.getObject("eleLocationsTitle"));

			}
			break;
		}
		return status;
	}

	/**
	 * @function Verify Page Assert
	 * @param strPageName
	 * @return boolean
	 * @throws Exception
	 */
	public static Boolean PageVerify(String strPageName) throws Exception {
		 loggers  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty

("TCID"));
		Common common = CommonManager.getInstance().getCommon();
		String ele = "ele" + strPageName;
		Boolean status = true;
		if (common.WaitUntilClickable(Common.o.getObject(ele), Integer.valueOf(HTML.properties.getProperty("LONGESTWAIT")))) {
			loggers.info("System displayed '" + strPageName + "'");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "System should display '" + strPageName + "'", "System displayed '" + strPageName + "'", "PASS");
			status = true;
		} else {
			loggers.info("System not displayed '" + strPageName + "'");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "System should display '" + strPageName + "'", "System not displayed '" + strPageName + "'", "FAIL");
			status = false;
		}
		return status;
	}

	/**
	 * @function use to verify the use case results
	 * @param funValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean VerifyResults(String funValue) {
		boolean status = false;
		String[] sValue = funValue.split(":::");
		logger.info("Verifying the Results");
		String Value = null;
		try {
			switch (sValue[0].toUpperCase()) {

			case "CHECKPOLICYSTATUS":
				logger.info(sValue[0]);
				Value = common.ReadElement(Common.o.getObject("elePolicyInfo_PolicyStatus"), Integer.valueOf

(HTML.properties.getProperty("NORMALWAIT")));
				status = common.CompareStringResult("Policy Status", sValue[1], Value);
				break;
			case "CHECKSUBMISSIONDRAFTSTATUS":
				logger.info(sValue[0]);
				Value = common.ReadElement(Common.o.getObject("eleSubmissionDraft"), Integer.valueOf(HTML.properties.getProperty

("NORMALWAIT")));
				status = common.CompareStringResult("Policy Status", sValue[1], Value);
				break;
			case "CHECKSUBMISSIONQUOTESTATUS":
				logger.info(sValue[0]);
				Value = common.ReadElement(Common.o.getObject("eleSubmissionQuoted"), Integer.valueOf

(HTML.properties.getProperty("NORMALWAIT")));
				status = common.CompareStringResult("Policy Status", sValue[1], Value);
				break;
			case "CHECKSUBMISSIONNOTTAKINGSTATUS":
				logger.info(sValue[0]);
				Value = common.ReadElement(Common.o.getObject("eleSubmissionNotTaken"), Integer.valueOf

(HTML.properties.getProperty("NORMALWAIT")));
				status = common.CompareStringResult("Policy Status", sValue[1], Value);
				break;
			case "CLASUBMISSIONSTATUS":
				logger.info(sValue[0]);
				Value = common.ReadElement(Common.o.getObject("eleTransactionStatus"), Integer.valueOf

(HTML.properties.getProperty("NORMALWAIT")));
				status = common.CompareStringResult("Policy Status", sValue[1], Value);
				break;
			}
			if (!status) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "" + sValue[0] + " case should PASS", "" + sValue[0] + " case should not fail", "FAIL");
				status = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			status = true;
		}
		return status;
	}
	/* *//**
			 * @function use to verify the use case results
			 * @param funValue
			 * @return true/false
			 * @throws Exception
			 *//*
				 * public boolean splitNumberAndName(String funValue) { // ALLMOND, RICHARD, K :
				 * 8998840R Boolean blnStatus = false; HashMap<String,Object>
				 * updateColumnNameValues = new HashMap<String,Object>(); HashMap<String,Object>
				 * whereConstraint = new HashMap<String,Object>(); XlsxReader sXL =
				 * XlsxReader.getInstance();
				 * 
				 * // Spliting number String[] sValue = funValue.split(":"); String NPNNumber =
				 * sValue[1]; // Splitting Firstname and last name
				 * 
				 * String[] Name =sValue[0].split(","); String FirstName=Name[0]; String
				 * LastName=Name[1]; String MiddleName=Name[2];
				 * 
				 * // Updating npn number
				 * PCThreadCache.getInstance().setProperty(PCConstants.SHEET_ICONValidation,
				 * NPNNumber); updateColumnNameValues.put("valelePolicyInfo_NPNNumber",
				 * NPNNumber); blnStatus =
				 * sXL.executeUpdateQuery(PCConstants.SHEET_ICONValidation ,
				 * updateColumnNameValues, whereConstraint); updateColumnNameValues.clear();
				 * 
				 * // Updating First Name
				 * updateColumnNameValues.put("valelePolicyInfo_NPNFirstName", FirstName);
				 * blnStatus = sXL.executeUpdateQuery(PCConstants.SHEET_ICONValidation ,
				 * updateColumnNameValues, whereConstraint); updateColumnNameValues.clear();
				 * 
				 * // Updating Last Name
				 * updateColumnNameValues.put("valelePolicyInfo_NPNLastName", LastName);
				 * blnStatus = sXL.executeUpdateQuery(PCConstants.SHEET_ICONValidation ,
				 * updateColumnNameValues, whereConstraint); updateColumnNameValues.clear();
				 * 
				 * // Updating Middle Name
				 * updateColumnNameValues.put("valelePolicyInfo_NPNMiddleName", MiddleName);
				 * blnStatus = sXL.executeUpdateQuery(PCConstants.SHEET_ICONValidation ,
				 * updateColumnNameValues, whereConstraint); updateColumnNameValues.clear();
				 * 
				 * return blnStatus; }
				 */

}