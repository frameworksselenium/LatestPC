
package com.pc.screen;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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

public class Payment {

	public static String sheetname = "Payment";
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
	public String policyNumber;
	public String policyNumberSearch;
	public String submissionNumber;
	Common common = CommonManager.getInstance().getCommon();

	public Boolean SCRPayment() throws Exception {
		logger.info("In SCRPayment");
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

	public boolean VerifyNotDropDown(String val) throws Exception {
		Boolean status = false;
		By ele = Common.o.getObject("lstPay");
		String id = ManagerDriver.getInstance().getWebDriver().findElement(ele).getAttribute("id");
		status = SCRCommon.VerifyDropDownFalse(id, val);
		return status;
	}

	public Boolean VerifyInstallmentPlan(String val) throws Exception {
		Boolean status = true;
		By ele = Common.o.getObject("lstPay");
		String id = ManagerDriver.getInstance().getWebDriver().findElement(ele).getAttribute("id");
		String[] sValue = val.split(":::");
		int num, var = 0;
		for (String str : sValue) {
			num = VerifyDropDown(id, str);
			System.out.println(num);
			if (num != 0 && var == 0) {
				var = num;
			} else if (num - var == 1) {
				var = num;
				continue;
			} else {
				status = false;
			}
		}
		if (status) {
			logger.info("All The Given DropDowns are in Sorted Order");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Validate whether the given Dropdown values are in the Sorted Order", "All The DropDowns are in Sorted Order", "PASS");
		} else {
			logger.info("All The Given DropDowns are NOT in Sorted Order");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Validate whether the given Dropdown values are in the Sorted Order", "All The DropDowns are NOT in Sorted Order", "FAIL");
		}
		return status;
	}

	public int VerifyDropDown(String eleObjID, String expValues) throws Exception {
		Boolean status = false;
		int num = 0;
		boolean blnMatchFound = false;
		String[] sActListValue = null;
		status = CommonManager.getInstance().getCommon().SafeAction(By.id(eleObjID), "ele", "eleDropdown");
		CommonManager.getInstance().getCommon().WaitForPageToBeReady();
		ManagerDriver.getInstance().getWebDriver().findElement(By.id(eleObjID)).sendKeys(Keys.ARROW_DOWN);
		CommonManager.getInstance().getCommon().WaitForPageToBeReady();
		List<WebElement> gwListBox = ManagerDriver.getInstance().getWebDriver().findElements(By.tagName("LI"));
		for (int k = 0; k < gwListBox.size(); k++) {
			if (expValues.equals(gwListBox.get(k).getText())) {
				logger.info("Expected UI dropdown value - " + expValues + " is available in List");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Validate whether the Dropdown values are available in UI List", "Expected Dropdown Value - " + expValues + "available in UI list", "PASS");
				blnMatchFound = true;
				return k;
			}
		}
		status = CommonManager.getInstance().getCommon().SafeAction(By.id(eleObjID), "ele", "eleDropdown");
		if (!blnMatchFound) {
			logger.info("Expected UI dropdown value - " + expValues + " is not available in List");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Validate whether the Dropdown values are available in UI List", "Expected Dropdown Value - " + expValues + "NOT available in UI list", "FAIL");
			status = CommonManager.getInstance().getCommon().SafeAction(By.id(eleObjID), "ele", "eleDropdown");
		}
		return num;
	}

	public boolean PolicyNumber() throws Exception {
		boolean status = true;
		HashMap<String, Object> updateColumnNameValues = new HashMap<String, Object>();
		HashMap<String, Object> whereConstraint = new HashMap<String, Object>();
		XlsxReader sXL = XlsxReader.getInstance();
		common.WaitForElementExist(Common.o.getObject("elePolicyNumbetInPolicySummaryPage"), Integer.valueOf(HTML.properties.getProperty("LONGWAIT")));
		policyNumber = common.ReadElement(Common.o.getObject("elePolicyNumbetInPolicySummaryPage"), Integer.valueOf

(HTML.properties.getProperty("LONGWAIT")));
		System.out.println("policyNumber is :::" + policyNumber);
		PCThreadCache.getInstance().setProperty(PCConstants.CACHE_POLICY_NUMBER, policyNumber);
		policyNumberSearch = policyNumber.substring(policyNumber.length() - 6);
		System.out.println("policyNumberSearch is :::" + policyNumberSearch);
		PCThreadCache.getInstance().setProperty(PCConstants.CACHE_POLICY_NUMBER_SEARCH, policyNumberSearch);
		logger.info("Policy Created::Policy Number '" + policyNumber + "'");
		logger.info("Policy Created::Last Six Digit Policy Number '" + policyNumberSearch + "'");
		updateColumnNameValues.put(PCConstants.PolicyNumber, policyNumber);
		whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
		PCThreadCache.getInstance().setProperty("PolicyNumber", policyNumber);
		//status = sXL.executeUpdateQuery(sheetname, updateColumnNameValues, whereConstraint);
		if (HTML.properties.getProperty("EXECUTIONAPP").contains("ODS")) {
			FlatFile sReadWrite = FlatFile.getInstance();
			SCRCommon SCRCommon = new SCRCommon();
			//String FlatFileName = SCRCommon.FlatFileName();
			String FlatFileName = PCThreadCache.getInstance().getProperty("FlatFileName");
			String policyNumebrSearch = "%" + policyNumberSearch;
			status = sReadWrite.write(PCThreadCache.getInstance().getProperty("TCID"), PCThreadCache.getInstance().getProperty("methodName"), "PolicyNumber", policyNumebrSearch, "OUTPUT", FlatFileName);
			status = sReadWrite.write(PCThreadCache.getInstance().getProperty("TCID"), PCThreadCache.getInstance().getProperty("methodName"), "FPolicyNumber", policyNumber, "OUTPUT", FlatFileName);
			// status =
			// sReadWrite.write(PCThreadCache.getInstance().getProperty("TCID"),PCThreadCache.getInstance().getProperty("methodName"),
			// "PolicyNumber", policyNumber, "OUTPUT", FlatFileName);
		}
		// Marking objects for Garbage collection
		whereConstraint = null;
		updateColumnNameValues = null;
		HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Policy Number shold be generated", "Policy Number generated: Policy Number is '" + policyNumber + "'", "PASS");
		return status;
	}

	public boolean PolicyNumber1() throws Exception {
		HashMap<String, Object> updateColumnNameValues = new HashMap<String, Object>();
		HashMap<String, Object> whereConstraint = new HashMap<String, Object>();
		XlsxReader sXL = XlsxReader.getInstance();
		boolean status = false;
		String wait = HTML.properties.getProperty("LONGWAIT");
		Integer x = Integer.valueOf(wait);
		common.WaitForElementExist(Common.o.getObject("elePolicyNumbetInPolicySummaryPage"), x);
		String sPolicyNumber = common.ReadElement(Common.o.getObject("elePolicyNumbetInPolicySummaryPage"), x);
		logger.info("Policy Created::Policy Number '" + sPolicyNumber + "'");
		whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
		//PCThreadCache.getInstance().setProperty("AffGroupName", value);
		/*sXL.executeUpdateQuery(sheetname, updateColumnNameValues, whereConstraint);
		sXL.executeUpdateQuery(sheetname, updateColumnNameValues, whereConstraint);*/
		// Marking objects for Garbage collection
		/*
		 * whereConstraint = null; updateColumnNameValues = null;
		 */
		updateColumnNameValues.clear();
		whereConstraint.clear();
		updateColumnNameValues.put(PCConstants.PolicyNumber, sPolicyNumber);
		updateColumnNameValues.put(PCConstants.funPolicyNumberSearch, sPolicyNumber);
		whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
		PCThreadCache.getInstance().setProperty("PolicyNumber", sPolicyNumber);
		//status = sXL.executeUpdateQuery(PCConstants.SHEET_SEARCHTABACTION, updateColumnNameValues, whereConstraint);
		// common.UpdateQuery(HTML.properties.getProperty("DataSheetName"), "Update
		// Payment Set PolicyNumber = '"+ sPolicyNumber +"' where
		// ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
		HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Policy Number shold be generated", "Policy Number generated: Policy Number is '" + sPolicyNumber + "'", "PASS");
		return true;

	}

	public boolean SubmissionNumber() throws Exception {
		HashMap<String, Object> updateColumnNameValues = new HashMap<String, Object>();
		HashMap<String, Object> whereConstraint = new HashMap<String, Object>();
		XlsxReader sXL = XlsxReader.getInstance();
		common.WaitForElementExist(Common.o.getObject("elePolicyNumbetInPolicySummaryPage"), Integer.valueOf(HTML.properties.getProperty("LONGWAIT")));
		submissionNumber = common.ReadElement(Common.o.getObject("eleSubmissionNumber"), Integer.valueOf(HTML.properties.getProperty("LONGWAIT")));
		logger.info("Policy Created::Submission Number '" + submissionNumber + "'");
		PCThreadCache.getInstance().setProperty(PCConstants.CACHE_SUBMISSION_NUMBER, submissionNumber);
		updateColumnNameValues.put(PCConstants.SubmissionNumber, submissionNumber);
		whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
		PCThreadCache.getInstance().setProperty("SubmissionNumber", submissionNumber);
		//sXL.executeUpdateQuery(PCConstants.SHEET_PAYMENT, updateColumnNameValues, whereConstraint);
		// Marking objects for Garbage collection
		whereConstraint = null;
		updateColumnNameValues = null;
		HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Submission Number shold be generated", "Submission Number generated and the number is '" + submissionNumber + "'", "PASS");
		return true;
	}

	public boolean RetrivePolicyNumber() throws Exception {
		boolean status = false;
		HashMap<String, Object> updateColumnNameValues = new HashMap<String, Object>();
		HashMap<String, Object> whereConstraint = new HashMap<String, Object>();
		XlsxReader sXL = XlsxReader.getInstance();
		// sXL = new XlsxReader(HTML.properties.getProperty("DataSheetName"));
		common.WaitForElementExist(Common.o.getObject("eleShell_PolicyNumber"), Integer.valueOf(HTML.properties.getProperty("LONGWAIT")));
		policyNumber = common.ReadElement(Common.o.getObject("eleShell_PolicyNumber"), Integer.valueOf(HTML.properties.getProperty("LONGWAIT")));
		policyNumberSearch = policyNumber.substring(policyNumber.length() - 6);
		logger.info("Policy Created::Policy Number '" + policyNumber + "'");
		logger.info("Policy Created::Last Six Digit Policy Number '" + policyNumberSearch + "'");
		// status = Common.UpdateQuery(HTML.properties.getProperty("DataSheetName"),
		// "Update "+sheetname+" Set PolicyNumber = '"+PolicyNumber+"' where
		// ID='"+HTML.properties.getProperty("TCID")+"'");
		PCThreadCache.getInstance().setProperty(PCConstants.CACHE_SUBMISSION_NUMBER, policyNumber);
		updateColumnNameValues.put(PCConstants.PolicyNumber, policyNumber);
		whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
		sXL.executeUpdateQuery(PCConstants.SHEET_PAYMENT, updateColumnNameValues, whereConstraint);
		// Marking objects for Garbage collection
		whereConstraint = null;
		updateColumnNameValues = null;
		HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Policy Number shold be generated", "Policy Number generated: Policy Number is '" + policyNumber + "'", "PASS");
		return status;
	}

	/**
	 * function used to fill the Umbrella section details
	 * 
	 * @param sFuncValue
	 * @return boolean
	 * @throws Exception
	 */
	public Boolean FillBillingContact(String sFuncValue) throws Exception {
		Boolean status = false;

		logger.info("Entering into Add NEW Billing Contact Function");
		XlsxReader sXL = XlsxReader.getInstance();// new XlsxReader( PCThreadCache.getInstance().getProperty("DataSheetName)
		String TCID = null;
		String TCIteration = null;
		// Common common = CommonManager.getInstance().getCommon();
		Boolean tcAvailable = false;
		int rowcount = sXL.getRowCount(sFuncValue);
		for (int i = 2; i <= rowcount; i++) {
			TCID = PCThreadCache.getInstance().getProperty("TCID");
			TCIteration = PCThreadCache.getInstance().getProperty("Iteration");
			String TCIDAdd = sXL.getCellData(sFuncValue, "ID", i);
			String TCIDIteration = sXL.getCellData(sFuncValue, "Iteration", i);

			if (TCIDAdd.equals(TCID) && TCIDIteration.equals(TCIteration)) {
				tcAvailable = true;
				int colcount = sXL.getColumnCount(sFuncValue);
				for (int j = 2; j <= colcount; j++) {
					String sColName = sXL.getCellData(sFuncValue, j, 1);
					String sCellValue = sXL.getCellData(sFuncValue, j, i);
					if (!sCellValue.isEmpty()) {
						status = common.SafeAction(Common.o.getObject(sColName), sCellValue, sColName);

					}

				}

			}
		}
		if (!tcAvailable) {
			logger.info("" + TCID + " is not available in the LocationData Sheet");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Validate the Testcase availability in datasheet - " + sFuncValue, "Testcass not available in Datasheet", "FAIL");

		} else if (!status) {
			logger.info("" + TCID + " is not available in the LocationData Sheet");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Validate the Testcase availability in datasheet - " + sFuncValue, "Testcass not available in Datasheet", "FAIL");
		}
		return status;
	}

	/**
	 * @function Safe Method for User Select option from list menu, waits until the
	 *           element is loaded and then selects an option from list menu
	 * @param bylocator
	 * @param sOptionToSelect
	 * @param iWaitTime
	 * @return true/false
	 * @throws Exception
	 **/
	public boolean SafeNotSelectGWListBox(By bylocator, String sOptionToSelect, int iWaitTime) throws Exception {
		common.WaitUntilClickable(bylocator, iWaitTime);
		WebElement element = ManagerDriver.getInstance().getWebDriver().findElement(bylocator);
		element.click();
		Thread.sleep(1000);
		ManagerDriver.getInstance().getWebDriver().findElement(bylocator).sendKeys(Keys.ARROW_DOWN);
		Thread.sleep(1500);
		boolean bFlag = false;
		boolean status = false;
		common.WaitForElementExist(bylocator, iWaitTime);
		List<WebElement> gwListBox = ManagerDriver.getInstance().getWebDriver().findElements(By.tagName("LI"));
		for (int i = 0; i < gwListBox.size(); i++) {
			String strListValue = gwListBox.get(i).getText();
			try {
				if (strListValue.contains(sOptionToSelect)) {
					System.out.println(gwListBox.get(i).getText());
					gwListBox.get(i).click();
					bFlag = true;
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Thread ID = " + Thread.currentThread().getId() + " Error Occured =" + e.getMessage(), e);
				bFlag = false;
			}
		}
		if (bFlag == false) {
			status = true;
		}
		return status;
	}
	

	public boolean PolicyIssuance(String funvalue) throws Exception {
		boolean status = false;
		String[] sValue = funvalue.split(":::");
		if(sValue.length>1)
		{
			status=common.WaitForElementExist(Common.o.getObject("lstBillingMethod"), Integer.valueOf(HTML.properties.getProperty("LONGWAIT")));
			if(status)
			{
				
				status = common.SafeAction(Common.o.getObject("lstBillingMethod"), sValue[0].trim(), "lstBillingMethod");
				status = common.SafeAction(Common.o.getObject("lstBillingNumber"), sValue[1].trim(), "lstBillingNumber");
				status = common.SafeAction(Common.o.getObject("edtBillingNumber"), sValue[2].trim(), "edtBillingNumber");
				status = common.SafeAction(Common.o.getObject("lstPay"), sValue[3].trim(), "lstPay");
				status = common.SafeAction(Common.o.getObject("elePayBindOption"), "", "elePayBindOption");
				status = common.SafeAction(Common.o.getObject("elePayIssuePolicy"), "", "elePayIssuePolicy");
				status = common.SafeAction(Common.o.getObject("eleAlertPopUp"), "", "eleAlertPopUp");
				
				if(ManagerDriver.getInstance().getWebDriver().findElements(Common.o.getObject("eleWorkSpaceClear")).size() != 0)
				{
					status = common.SafeAction(Common.o.getObject("elePayBindOption"), "", "elePayBindOption");
					status = common.SafeAction(Common.o.getObject("elePayIssuePolicy"), "", "elePayIssuePolicy");
					status = common.SafeAction(Common.o.getObject("eleAlertPopUp"), "", "eleAlertPopUp");
				}
				
			}
			else
			{
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Validate whether if it's Payment screen", "Payment screen is not available", "FAIL");
			}
		}
		else
		{
			logger.info("Invalid values are passed to the function "+PCThreadCache.getInstance().getProperty("methodName"));
			return false;
		}
		
		
		
		//eleWorkSpaceClear
		return status;
	}
				
	
}