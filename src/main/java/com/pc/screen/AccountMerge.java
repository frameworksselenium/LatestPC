/**
 * @ClassPurpose This Class used for the account merge usecase
 * @Scriptor Sojan David
 * @ReviewedBy
 * @ModifiedBy
 * @LastDateModified 3/17/2017
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

public class AccountMerge {

	public static String sheetname = "AccountMerge";
	Common common = CommonManager.getInstance().getCommon();
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();
	//static Logger logger = Logger.getLogger(sheetname);
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));

	/**
	 * @function Use to perform all the objects/elements/functions from the
	 *           accountmerge excel sheet
	 * @return true/false
	 * @throws Exception
	 */
	public Boolean SCRAccountMerge() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

	/**
	 * @function Used to write the testdata for the dependency testcase
	 * @param sValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean DataFill(String sValue) throws Exception {
		HashMap<String, Object> updateColumnNameValues = new HashMap<String, Object>();
		HashMap<String, Object> whereConstraint = new HashMap<String, Object>();
		boolean status = false;
		try {
			XlsxReader sXL = XlsxReader.getInstance();
			int rowcount = sXL.getRowCount(sheetname);
			for (int i = 1; i < rowcount; i++) {
				String value = sXL.getCellData(sheetname, 0, i);
				if (!value.isEmpty()) {
					if (PCThreadCache.getInstance().getProperty("TCID").equals(value)) {
						if (sValue.equals("AccountNumber")) {
							updateColumnNameValues.clear();
							whereConstraint.clear();
							updateColumnNameValues.put(PCConstants.AccountNumber, PCThreadCache.getInstance().getProperty(PCConstants.CACHE_ACCOUNT_NUMBER));
							whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
							PCThreadCache.getInstance().setProperty("AccountNumber", PCThreadCache.getInstance().getProperty(PCConstants.CACHE_ACCOUNT_NUMBER));
						//	status = sXL.executeUpdateQuery(PCConstants.SHEET_ACCOUNTMERGE, updateColumnNameValues,whereConstraint);
							updateColumnNameValues.clear();
							whereConstraint.clear();
							updateColumnNameValues.put(PCConstants.edtaccountNumber, PCThreadCache.getInstance().getProperty(PCConstants.CACHE_ACCOUNT_NUMBER));
							whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
							PCThreadCache.getInstance().setProperty("edtaccountNumber", PCThreadCache.getInstance().getProperty(PCConstants.CACHE_ACCOUNT_NUMBER));
							//status = sXL.executeUpdateQuery(PCConstants.SHEET_SEARCHACCOUNT, updateColumnNameValues, whereConstraint);

							// status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"),
							// "Update AccountMerge Set AccountNumber = '"+CreateAccount.AccountNumber+"'
							// where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
							// status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"),
							// "Update SearchAccount Set edtaccountNumber =
							// '"+CreateAccount.AccountNumber+"' where
							// ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
							status = true;
							break;
						} else {
							updateColumnNameValues.clear();
							whereConstraint.clear();
							updateColumnNameValues.put(PCConstants.AccountNumber, PCThreadCache.getInstance().getProperty(PCConstants.CACHE_ACCOUNT_NUMBER));
							whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
							PCThreadCache.getInstance().setProperty("AccountNumber", PCThreadCache.getInstance().getProperty(PCConstants.CACHE_ACCOUNT_NUMBER));
							//status = sXL.executeUpdateQuery(PCConstants.SHEET_ACCOUNTMERGE, updateColumnNameValues, whereConstraint);

							updateColumnNameValues.clear();
							whereConstraint.clear();
							updateColumnNameValues.put(PCConstants.edtaccountNumber, PCThreadCache.getInstance().getProperty(PCConstants.CACHE_ACCOUNT_NUMBER));
							whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
							PCThreadCache.getInstance().setProperty("edtaccountNumber", PCThreadCache.getInstance().getProperty(PCConstants.CACHE_ACCOUNT_NUMBER));
							//status = sXL.executeUpdateQuery(PCConstants.SHEET_SEARCHACCOUNT, updateColumnNameValues, whereConstraint);

							updateColumnNameValues.clear();
							whereConstraint.clear();
							updateColumnNameValues.put(PCConstants.PolicyNumber, PCThreadCache.getInstance().getProperty(PCConstants.CACHE_POLICY_NUMBER));
							whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
							PCThreadCache.getInstance().setProperty("PolicyNumber", PCThreadCache.getInstance().getProperty(PCConstants.CACHE_POLICY_NUMBER));
							//status = sXL.executeUpdateQuery(PCConstants.SHEET_ACCOUNTMERGE, updateColumnNameValues, whereConstraint);

							// status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"),
							// "Update AccountMerge Set AccountNumber = '"+CreateAccount.AccountNumber+"'
							// where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
							// status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"),
							// "Update SearchAccount Set edtaccountNumber =
							// '"+CreateAccount.AccountNumber+"' where
							// ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
							// status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"),
							// "Update AccountMerge Set PolicyNumber = '"+Payment.PolicyNumber+"' where
							// ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
							status = true;
							break;
						}
					}
				}
			}
		} finally {
			updateColumnNameValues = null;
			whereConstraint = null;
		}
		return status;
	}

	/**
	 * @function Used to write the testdata for the dependency testcase
	 * @param sValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean PolDataFill() throws Exception {
		boolean status = false;
		HashMap<String, Object> updateColumnNameValues = new HashMap<String, Object>();
		HashMap<String, Object> whereConstraint = new HashMap<String, Object>();
		try {
			XlsxReader sXL = XlsxReader.getInstance();
			int rowcount = sXL.getRowCount(sheetname);
			for (int i = 1; i < rowcount; i++) {
				String value = sXL.getCellData(sheetname, 0, i);
				if (!value.isEmpty()) {
					if (PCThreadCache.getInstance().getProperty("TCID").equals(value)) {

						updateColumnNameValues.clear();
						whereConstraint.clear();
						updateColumnNameValues.put(PCConstants.AccountNumber, PCThreadCache.getInstance().getProperty

(PCConstants.CACHE_ACCOUNT_NUMBER));
						whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
						PCThreadCache.getInstance().setProperty("AccountNumber", PCThreadCache.getInstance().getProperty

(PCConstants.CACHE_ACCOUNT_NUMBER));
						//status = sXL.executeUpdateQuery(PCConstants.SHEET_ACCOUNTMERGE, updateColumnNameValues, whereConstraint);

						updateColumnNameValues.clear();
						whereConstraint.clear();
						updateColumnNameValues.put(PCConstants.edtaccountNumber, PCThreadCache.getInstance().getProperty

(PCConstants.CACHE_ACCOUNT_NUMBER));
						whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
						PCThreadCache.getInstance().setProperty("edtaccountNumber", PCThreadCache.getInstance

().getProperty(PCConstants.CACHE_ACCOUNT_NUMBER));
						//status = sXL.executeUpdateQuery(PCConstants.SHEET_SEARCHACCOUNT, updateColumnNameValues, whereConstraint);

						// status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"),
						// "Update AccountMerge Set AccountNumber = '"+CreateAccount.AccountNumber+"'
						// where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
						// status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"),
						// "Update SearchAccount Set edtaccountNumber =
						// '"+CreateAccount.AccountNumber+"' where
						// ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
						status = true;
						break;
					}
				}
			}
		} finally {
			updateColumnNameValues = null;
			whereConstraint = null;
		}
		return status;
	}

	/**
	 * @function Verify the results of the account merge usecase
	 * @param funValue
	 * @return
	 * @throws Exception
	 */
	public boolean VerifyAccMerge(String funValue) throws Exception {
		boolean fvalue = true;
		String[] sValue = funValue.split(":::");
		String wait = HTML.properties.getProperty("SHORTWAIT");
		Integer x = Integer.valueOf(wait);
		if (fvalue) {
			switch (sValue[0].toUpperCase()) {
			case "MERGEVALIDATION":
				String expectedValue = sValue[1];
				String actualValue = common.ReadElement(Common.o.getObject("txtMergeTranMsg"), x);
				logger.info("Validating the '" + sValue[0] + "'");
				if (expectedValue.equals(actualValue)) {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "'" + expectedValue + "'", "'" + actualValue + "'", "PASS");
				} else {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "'" + expectedValue + "'", "'" + actualValue + "'", "FAIL");
				}
				CheckingMergePolicy(sValue[2]);
				break;

			case "CHECKINGSOURCEACCOUNT":
				String expectedValue1 = sValue[1];
				String actualValue1 = common.ReadElement(Common.o.getObject("eleZeroResultMsg"), x);
				logger.info("Validating the '" + sValue[0] + "'");
				if (expectedValue1.equals(actualValue1)) {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "'" + expectedValue1 + "'", "'" + actualValue1 + "'", "PASS");
				} else {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "'" + expectedValue1 + "'", "'" + actualValue1 + "'", "FAIL");
				}
				break;

			case "ACCOUNTMERGEWITHOUTPERMISSION":
				boolean checkMergeAccOption = driver.findElements(Common.o.getObject("eleAccMerge")).size() != 0;
				System.out.println(checkMergeAccOption);
				logger.info("Validating the '" + sValue[0] + "'");
				if (!checkMergeAccOption == true) {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "User should not have permission", "User don't have the permission", "PASS");
				} else {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "User should not have permission", "User have the permission", "FAIL");
				}
				break;

			case "ACCOUNTMERGECANCEL":
				String expectedValue3 = sValue[1];
				String actualValue3 = common.ReadElement(Common.o.getObject("txtMergeCancelMsg"), x);
				logger.info("Validating the '" + sValue[0] + "'");
				if (expectedValue3.contains(actualValue3)) {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "'" + expectedValue3 + "'", "'" + actualValue3 + "'", "PASS");
				} else {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "'" + expectedValue3 + "'", "'" + actualValue3 + "'", "FAIL");
				}
				break;

			case "SOURCEACCOUNTNOTEXIST":
				String expectedValue4 = sValue[1];
				String actualValue4 = common.ReadElement(Common.o.getObject("eleZeroResultMsg"), x);
				System.out.println(actualValue4);
				logger.info("Validating the '" + sValue[0] + "'");
				if (expectedValue4.contains(actualValue4)) {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "'" + expectedValue4 + "'", "'" + actualValue4 + "'", "PASS");
				} else {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "'" + expectedValue4 + "'", "'" + actualValue4 + "'", "FAIL");
				}
				break;

			case "TARGETACCOUNTNOTEXIST":
				String capAccNum = common.ReadElement(Common.o.getObject("txtCapAccNum"), x);
				String expectedValue5 = "The account number #" + capAccNum + " matches the current account. Please choose another to merge with.";
				// String expectedValue5 = "The account number #'"+capAccNum+"' matches the
				// current account. Please provide a different one for move policies account
				// selection.";
				String actualValue5 = common.ReadElement(Common.o.getObject("txtTagetAccNotExistMsg"), x);
				System.out.println(actualValue5);
				logger.info("Validating the '" + sValue[0] + "'");
				if (expectedValue5.contains(actualValue5)) {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "'" + expectedValue5 + "'", "'" + actualValue5 + "'", "PASS");
				} else {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "'" + expectedValue5 + "'", "'" + actualValue5 + "'", "FAIL");
				}
				break;
			}
		}
		return true;
	}

	/*
	 * public boolean CheckingMergePolicy(String sPolicyNumber) throws Exception {
	 * boolean status = false; WebElement tableRowXpath =
	 * driver.findElement(Common.o.getObject("eleAfterPolicyMoveVerify"));
	 * List<WebElement> rows_table = tableRowXpath.findElements(By.tagName("tr"));
	 * int rows_count = rows_table.size(); for(int j=1;j<=rows_count;j++) { String
	 * policyText = driver.findElement(By.xpath(
	 * ".//*[@id='AccountFile_Summary:AccountFile_SummaryScreen:PSPolicyTermInfo_ExtLV-body']/div/table/tbody/tr["
	 * +j+"]/td[1]")).getText(); if(sPolicyNumber.equals(policyText)) { status =
	 * true;
	 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
	 * PCThreadCache.getInstance().getProperty("methodName"),
	 * "Policy '"+sPolicyNumber+"' Should Match",
	 * "Policy '"+sPolicyNumber+"' matched", "PASS"); } } return status; } }
	 */
	/**
	 * @function use to check the policy number avaible in the account summary
	 *           policy table
	 * @param sPolicyNumber
	 * @return true/false
	 * @throws Exception
	 */
	public boolean CheckingMergePolicy(String sPolicyNumber) throws Exception {
		boolean status = false;
		String Value = common.GetTextFromTable(Common.o.getObject("eleAccountSummary_PolicyTermsTbl"), 1, 1, sPolicyNumber);
		status = common.CompareStringResult("VerifyMergedPolicyNumber", sPolicyNumber, Value);
		return status;
	}

	/**
	 * @function use to verify the use case results
	 * @param funValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean VerifyResults(String funValue) throws Exception {
		boolean status = false;
		String[] sValue = funValue.split(":::");
		logger.info("Verifying the Results");
		String Value = null;
		switch (sValue[0].toUpperCase()) {

		case "VERIFYMERGEMESSAGE":
			logger.info(sValue[0]);
			Value = common.ReadElement(Common.o.getObject("txtMergeAccountinPolicyMove"), Integer.valueOf

(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			break;

		}
		if (!status) {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "" + sValue[0] + " case should PASS", "" + sValue[0] + " case should not fail", "FAIL");
			status = true;
		}
		return status;
	}

}
