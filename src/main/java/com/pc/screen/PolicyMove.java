
/**
 * @ClassPurpose This Class used for the Policy Move usecase
 * @Scriptor Sojan David
 * @ReviewedBy
 * @ModifiedBy Sojan David
 * @LastDateModified 3/27/2017
 */
package com.pc.screen;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.pc.constants.PCConstants;
import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.PCThreadCache;
import com.pc.utilities.XlsxReader;

public class PolicyMove {

	public static String sheetname = "PolicyMove";
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();

	/**
	 * @function Use to perform all the objects/elements/functions from the policy
	 *           move excel sheet
	 * @return true/false
	 * @throws Exception
	 */
	public Boolean SCRPolicyMove() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

	/**
	 * @function use to put the data in the excel sheet
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
		String tcID = PCThreadCache.getInstance().getProperty("TCID");
		String policyNumber = PCThreadCache.getInstance().getProperty(PCConstants.CACHE_POLICY_NUMBER);
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
						//status = sXL.executeUpdateQuery(PCConstants.SHEET_POLICYMOVE, updateColumnNameValues, whereConstraint);

						updateColumnNameValues.clear();
						whereConstraint.clear();
						updateColumnNameValues.put(PCConstants.edtaccountNumber, accountNumber);
						whereConstraint.put(PCConstants.ID, tcID);
						PCThreadCache.getInstance().setProperty("edtaccountNumber", accountNumber);
						//status = sXL.executeUpdateQuery(PCConstants.SHEET_SEARCHACCOUNT, updateColumnNameValues, whereConstraint);

						// status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"),
						// "Update PolicyMove Set AccountNumber = '"+CreateAccount.AccountNumber+"'
						// where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
						// status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"),					// "Update SearchAccount Set edtaccountNumber =
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
						//status = sXL.executeUpdateQuery(PCConstants.SHEET_POLICYMOVE, updateColumnNameValues, whereConstraint);

						updateColumnNameValues.clear();
						whereConstraint.clear();
						updateColumnNameValues.put(PCConstants.edtaccountNumber, accountNumber);
						whereConstraint.put(PCConstants.ID, tcID);
						PCThreadCache.getInstance().setProperty("edtaccountNumber", accountNumber);
						//status = sXL.executeUpdateQuery(PCConstants.SHEET_SEARCHACCOUNT, updateColumnNameValues, whereConstraint);

						updateColumnNameValues.clear();
						whereConstraint.clear();
						updateColumnNameValues.put(PCConstants.PolicyNumber, policyNumber);
						whereConstraint.put(PCConstants.ID, tcID);
						PCThreadCache.getInstance().setProperty("PolicyNumber", policyNumber);
						//status = sXL.executeUpdateQuery(PCConstants.SHEET_POLICYMOVE, updateColumnNameValues, whereConstraint);

						updateColumnNameValues.clear();
						whereConstraint.clear();
						updateColumnNameValues.put(PCConstants.PolicyNumber, policyNumber);
						whereConstraint.put(PCConstants.ID, tcID);
						PCThreadCache.getInstance().setProperty("PolicyNumber", policyNumber);
						//status = sXL.executeUpdateQuery(PCConstants.SHEET_ACCOUNTHISTORY, updateColumnNameValues, whereConstraint);

						// status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"),
						// "Update PolicyMove Set AccountNumber = '"+CreateAccount.AccountNumber+"'
						// where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
						// status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"),
						// "Update SearchAccount Set edtaccountNumber =
						// '"+CreateAccount.AccountNumber+"' where
						// ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
						// status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"),
						// "Update PolicyMove Set PolicyNumber = '"+Payment.PolicyNumber+"' where
						// ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
						status = true;
						break;
					}
				}
			}
		}
		return status;
	}

	/**
	 * @function Use to verify the test case validation
	 * @param funValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean PolicyMoveVerification(String funValue) throws Exception {
		boolean status = true;
		String[] sValue = funValue.split(":::");
		String wait = HTML.properties.getProperty("SHORTWAIT");
		Integer x = Integer.valueOf(wait);
		switch (sValue[0].toUpperCase()) {
		case "POLICYMOVEVALIDATION":
			String expectedValue = sValue[1];
			int lenOfFuncValue = sValue.length;
			boolean policyCheck = false;
			String actualValue = common.ReadElement(Common.o.getObject("txtPolicyMoveMsg"), x);
			logger.info("Validating the '" + sValue[0] + "'");
			if (expectedValue.equals(actualValue)) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "'" + expectedValue + "'", "'" + actualValue + "'", "PASS");
			} else {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "'" + expectedValue + "'", "'" + actualValue + "'", "FAIL");
			}
			String sPolicyNumber = common.GetTextFromTable(Common.o.getObject("eleAccountSummary_PolicyTermsTbl"), 1, 1, sValue[2]);
			policyCheck = common.CompareStringResult("CompareStringResult", sValue[2], sPolicyNumber);
			/*
			 * for(int i=0;i<lenOfFuncValue;i++) { WebElement tableRowXpath =
			 * driver.findElement(Common.o.getObject("eleAfterPolicyMoveVerify"));
			 * List<WebElement> rows_table = tableRowXpath.findElements(By.tagName("tr"));
			 * int rows_count = rows_table.size(); for(int j=1;j<=rows_count;j++){ String
			 * policyText = driver.findElement(By.xpath(
			 * ".//*[@id='AccountFile_Summary:AccountFile_SummaryScreen:PSPolicyTermInfo_ExtLV-body']/div/table/tbody/tr["
			 * +j+"]/td[1]")).getText(); if(sValue[i].equals(policyText)){ policyCheck =
			 * true;
			 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
			 * PCThreadCache.getInstance().getProperty("methodName"),
			 * "Policy '"+sValue[i]+"' Should Match", "Policy '"+sValue[i]+"' matched",
			 * "PASS"); } } }
			 */
			if (policyCheck == false) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Policy Number should match", "Policy does not match", "FAIL");
				status = false;
			}
			break;

		case "SelectMovePolicies":
			status = SelectMovePolicies(sValue[1]);
			break;

		case "CheckMultiplePolicyCheck":
			status = CheckMultiplePolicyCheck(sValue[1]);
			break;

		case "POLICYMERGEVALIDATION":
			String expectedValue1 = sValue[1];
			int lenOfFuncValue1 = sValue.length;
			boolean policyMergeValdiationStatus = false;
			status = SCRCommon.PageVerify("MergeTranMsg");
			String actualValue1 = common.ReadElement((Common.o.getObject("txtMergeTranMsg")), x);
			logger.info("Validating the '" + sValue[0] + "'");
			if (expectedValue1.equals(actualValue1)) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "'" + expectedValue1 + "'", "'" + actualValue1 + "'", "PASS");
				policyMergeValdiationStatus = true;
			} else {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "'" + expectedValue1 + "'", "'" + actualValue1 + "'", "FAIL");
				policyMergeValdiationStatus = false;
			}
			String Value = common.GetTextFromTable(Common.o.getObject("eleAccountSummary_PolicyTermsTbl"), 1, 1, sValue[2]);
			status = common.CompareStringResult("CompareStringResult", sValue[2], Value);
			/*
			 * for(int i=0;i<lenOfFuncValue1;i++) { status =
			 * SCRCommon.PageVerify("AfterPolicyMoveVerify"); WebElement tableRowXpath =
			 * driver.findElement(Common.o.getObject("eleAfterPolicyMoveVerify"));
			 * List<WebElement> rows_table = tableRowXpath.findElements(By.tagName("tr"));
			 * int rows_count = rows_table.size(); for(int j=1;j<=rows_count;j++){ String
			 * policyText = driver.findElement(By.xpath(
			 * ".//*[@id='AccountFile_Summary:AccountFile_SummaryScreen:PSPolicyTermInfo_ExtLV-body']/div/table/tbody/tr["
			 * +j+"]/td[1]")).getText(); if(sValue[i].equals(policyText)){
			 * policyMergeValdiationStatus = true;
			 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
			 * PCThreadCache.getInstance().getProperty("methodName"),
			 * "Policy '"+sValue[i]+"' Should Match", "Policy '"+policyText+"' matched",
			 * "PASS"); String policyStatus = driver.findElement(By.xpath(
			 * ".//*[@id='AccountFile_Summary:AccountFile_SummaryScreen:PSPolicyTermInfo_ExtLV-body']/div/table/tbody/tr["
			 * +j+"]/td[7]")).getText(); if(sValue[3].equals(policyStatus)) {
			 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
			 * PCThreadCache.getInstance().getProperty("methodName"),
			 * "Policy Status '"+sValue[3]+"' Should Match",
			 * "Policy Status '"+policyStatus+"' matched", "PASS"); }else{
			 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
			 * PCThreadCache.getInstance().getProperty("methodName"),
			 * "Policy Status '"+sValue[3]+"' Should Match",
			 * "Policy Status '"+policyStatus+"' not matched", "FAIL"); } } } }
			 */
			if (policyMergeValdiationStatus == false) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Policy Number should match", "Policy does not match", "FAIL");
				status = false;
			}
			break;

		case "POLICYMOVEVWITHOUTPERMISSION":
			boolean checkPolicyMoveOption = driver.findElements(Common.o.getObject("elePolicyMove")).size() != 0;
			logger.info("Validating the '" + sValue[0] + "'");
			if (!checkPolicyMoveOption == true) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "User should not have policy move permission", "User don't have the policy move permission", "PASS");
			} else {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "User should not have policy move permission", "User have the policy move permission", "FAIL");
			}
			break;

		case "SOURCEACCOUNTNOTEXIST":
			String expectedValue2 = sValue[1];
			String actualValue2 = common.ReadElement(Common.o.getObject("eleSameTgtasSrc"), x);
			System.out.println(actualValue2);
			logger.info("Validating the '" + sValue[0] + "'");
			if (expectedValue2.contains(actualValue2)) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "'" + expectedValue2 + "'", "'" + actualValue2 + "'", "PASS");
			} else {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "'" + expectedValue2 + "'", "'" + actualValue2 + "'", "FAIL");
			}
			break;

		case "TARGETACCOUNTNOTEXIST":
			String accNumber = sValue[1];
			String expectedValue3 = "The account number #" + accNumber + " matches the current account. Please provide a different one for move policies account selection.";
			String actualValue3 = common.ReadElement(Common.o.getObject("txtTagetAccNotExistMsg"), x);
			System.out.println(actualValue3);
			logger.info("Validating the '" + sValue[0] + "'");
			if (expectedValue3.contains(actualValue3)) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "'" + expectedValue3 + "'", "'" + actualValue3 + "'", "PASS");
				status = true;
			} else {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "'" + expectedValue3 + "'", "'" + actualValue3 + "'", "FAIL");
				// status = false;
			}
			break;

		}
		if (status == false) {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "PolicyMoveVerification function should have executed properly", "PolicyMoveVerification function should not executed properly", 

"FAIL");
			status = false;
		}
		return status;
	}

	/**
	 * @function Use to select the policies
	 * @param funValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean SelectMovePolicies(String funValue) throws Exception {
		boolean status = true;
		String[] sValue = funValue.split(":::");
		int x = sValue.length;
		logger.info("Validating the '" + sValue[0] + "'");
		status = SCRCommon.PageVerify("PMCount");
		for (int i = 0; i < x; i++) {
			WebElement tableRowXpath = driver.findElement(Common.o.getObject("rowPMCount"));
			List<WebElement> rows_table = tableRowXpath.findElements(By.tagName("tr"));
			int rows_count = rows_table.size();
			boolean policyCheck = false;
			for (int j = 1; j <= rows_count; j++) {
				String policyText = driver.findElement(By.xpath(".//*[@id='AccountFile_MovePoliciesSelection:PSAccountFile_MovePoliciesSelection_ExtLV-body']/div/table/tbody/tr[" + j + "]/td[2]")).getText();
				if (sValue[i].equals(policyText)) {
					driver.findElement(By.xpath(".//*[@id='AccountFile_MovePoliciesSelection:PSAccountFile_MovePoliciesSelection_ExtLV-body']/div/table/tbody/tr[" + j + "]/td[1]/div/img")).click();
					policyCheck = true;
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "It should click the '" + sValue[i] + "'", "Clicked the policy '" + sValue[i] + "'", "PASS");
				}
			}
			if (policyCheck == false) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "It should select the policy given to move", "Unable to select the given move policy", "FAIL");
				status = false;
			}
		}
		return status;
	}

	/**
	 * @function Use to execute the
	 * @param funValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean verifyErrorMessage(String funValue) throws Exception {
		boolean status = false;
		String sValue[] = funValue.split(":::");
		SCRCommon.PageVerify("MutilplePolicyMsgCheck");
		String expectedValue = "Moving ALL policies will result in the deletion of account #" + sValue[0] + ". Please click the ‘Move Policies to this Account’ button again to perform an Account Merge.";
		String actualValue = common.ReadElement(Common.o.getObject("txtMutilplePolicyMsgCheck"), Integer.valueOf

(HTML.properties.getProperty("LONGESTWAIT")));
		status = common.CompareStringResult("MoveAllPolicies", expectedValue, actualValue);
		return status;
	}

	/**
	 * @function Use to execute the
	 * @param funValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean CheckMultiplePolicyCheck(String funValue) throws Exception {
		boolean status = false;
		String[] sValue = funValue.split(":::");
		String wait = HTML.properties.getProperty("SHORTWAIT");
		Integer x = Integer.valueOf(wait);
		/*
		 * boolean policyCheck = false;
		 * logger.info("Validating the CheckMultiplePolicyCheck"); status =
		 * SCRCommon.PageVerify("MutilplePolicyMsgCheck"); boolean
		 * checkMultiplePolicyMsg =
		 * driver.findElements(Common.o.getObject("txtMutilplePolicyMsgCheck")).size()
		 * != 0; if(checkMultiplePolicyMsg == true){ String expectedValue =
		 * "Moving ALL policies will result in the deletion of account #"+sValue[0]
		 * +". Please click the ‘Move Policies to this Account’ button again to perform an Account Merge."
		 * ; // String expectedValue =
		 * "Moving ALL policies will result in the deletion of account #"+sValue[0]
		 * +". Please click OK to perform an Account Merge."; status =
		 * SCRCommon.PageVerify("MutilplePolicyMsgCheck"); String actualValue =
		 * common.ReadElement(Common.o.getObject("txtMutilplePolicyMsgCheck"), x);
		 * if(expectedValue.equals(actualValue)){
		 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
		 * PCThreadCache.getInstance().getProperty("methodName"), "'"+expectedValue+"'",
		 * "'"+actualValue+"'", "PASS"); status = SCRCommon.PageVerify("MergePolicy");
		 * driver.findElement(Common.o.getObject("btnMergePolicy")).click(); policyCheck
		 * = true; }else {
		 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
		 * PCThreadCache.getInstance().getProperty("methodName"), "'"+expectedValue+"'",
		 * "'"+actualValue+"'", "FAIL"); // policyCheck = false; } }else {
		 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
		 * PCThreadCache.getInstance().getProperty("methodName"),
		 * "Mulitple Policy Message should avilable",
		 * "Mulitple Policy Message is not found", "FAIL"); // policyCheck = false; }
		 */
		status = SCRCommon.PageVerify("MergeAccounts");
		boolean checkMergeButton = driver.findElements(Common.o.getObject("eleMergeAccounts")).size() != 0;
		if (checkMergeButton == true) {
			String expectedSrcAccnum = sValue[0];
			String expectedTgtAccnum = sValue[1];
			String expetedAccmergeMessage = common.ReadElement(Common.o.getObject("txtMergeAccountinPolicyMove"), x);
			String actualAccmergeMessage = "All information from account #" + expectedSrcAccnum + " will be merged into account #" + 

expectedTgtAccnum + ".";
			common.CompareStringResult("Policy Move Validation", expetedAccmergeMessage, actualAccmergeMessage);
			String actualAccmergeMessage1 = common.ReadElement(Common.o.getObject("txtMergeAccountinPolicyMove2"), x);
			// String expetedAccmergeMessage1 = "Account "+expectedSrcAccnum+" will be
			// removed.";
			String expetedAccmergeMessage1 = "Account #" + expectedSrcAccnum + " will be removed.";
			common.CompareStringResult("Policy Move Validation", expetedAccmergeMessage1, actualAccmergeMessage1);
			status = SCRCommon.PageVerify("MergeAccounts");
			driver.findElement(Common.o.getObject("eleMergeAccounts")).click();
			boolean mergePopUp = driver.findElements(Common.o.getObject("eleAlertPopUp")).size() != 0;
			if (mergePopUp == true) {
				driver.findElement(Common.o.getObject("eleAlertPopUp")).click();
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Policy Move Pop Up should occur", "Policy Move pop up is available", "PASS");
			} else {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Policy Move Pop Up should occur", "Policy move pop up is not available", "FAIL");
				// mergePopUp = false;
			}
		}
		/**
		 * if(policyCheck == false){
		 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
		 * PCThreadCache.getInstance().getProperty("methodName"), "It should select the
		 * policy to move", "Policy not found", "FAIL"); status = false; }
		 */
		return status;
	}
}
