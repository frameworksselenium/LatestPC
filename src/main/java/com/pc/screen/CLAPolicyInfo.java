/**
 * @ClassPurpose Access all the data's for driver inforamtion sheet
 * @Scriptor Sojan David
 * @ReviewedBy
 * @ModifiedBy Sojan David
 * @LastDateModified 3/21/2017
 */
package com.pc.screen;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.pc.constants.PCConstants;
import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.FlatFile;
import com.pc.utilities.HTML;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.PCThreadCache;
import com.pc.utilities.XlsxReader;

public class CLAPolicyInfo {

	public static String sheetName = "CLAPolicyInfo";
	//static Logger logger = Logger.getLogger(sheetName);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
	public static String CLAPolicyNumber;
	public String PolicyEffDate;
	public String PolicyExpDate;
	Common common = CommonManager.getInstance().getCommon();
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();

	public Boolean SCRCLAPolicyInfo() throws Exception {
		Boolean status = false;
		status = common.ClassComponent(sheetName, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

	/**
	 * @function use to get the CLA policy number and update the sheets
	 * @return true/false
	 * @throws Exception
	 */
	public boolean PolicyNumber() throws Exception {
		boolean status = false;
		HashMap<String, Object> updateColumnNameValues = new HashMap<String, Object>();
		HashMap<String, Object> whereConstraint = new HashMap<String, Object>();
		XlsxReader sXL = XlsxReader.getInstance();
		try {
			status = common.WaitForElementExist(Common.o.getObject("eleCLAPolicyStatus"), Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
			if (status) {
				String sPolicyNumber = common.ReadElement(Common.o.getObject("eleCLAPolicyNumber"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
				int sPolicyNumberSize = sPolicyNumber.length();
				if (sPolicyNumberSize > 9) {
					String sPolicyNumberTrim[] = sPolicyNumber.split("\\# ");
					String policyNumber = sPolicyNumberTrim[1].toString();
					String policyNumberSearch = null;
					PCThreadCache.getInstance().setProperty(PCConstants.CACHE_POLICY_NUMBER, policyNumber);
					policyNumberSearch = policyNumber.substring(policyNumber.length() - 6);
					PCThreadCache.getInstance().setProperty(PCConstants.CACHE_POLICY_NUMBER_SEARCH, policyNumberSearch);
					logger.info("Policy Created::Policy Number '" + policyNumber + "'");
					logger.info("Policy Created::Last Six Degit Policy Number '" + policyNumberSearch + "'");
					updateColumnNameValues.clear();
					whereConstraint.clear();
					updateColumnNameValues.put(PCConstants.PolicyNumber, policyNumber);
					whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
					PCThreadCache.getInstance().setProperty("PolicyNumber", policyNumber);
					//status = sXL.executeUpdateQuery(sheetName, updateColumnNameValues, whereConstraint);
					if (HTML.properties.getProperty("EXECUTIONAPP").contains("ODS")) {
						FlatFile sReadWrite = FlatFile.getInstance();
						SCRCommon scrCommon = new SCRCommon();
						String FlatFileName = scrCommon.FlatFileName();
						status = sReadWrite.write(PCThreadCache.getInstance().getProperty("TCID"), 

PCThreadCache.getInstance().getProperty("methodName"), "PolicyNumber", policyNumber, "OUTPUT", FlatFileName);
					}
					/*
					 * updateColumnNameValues.clear(); whereConstraint.clear();
					 * updateColumnNameValues.put(PCConstants.edtPolicyNumber, policyNumberSearch);
					 * whereConstraint.put(PCConstants.ID,
					 * PCThreadCache.getInstance().getProperty("TCID")); status =
					 * sXL.executeUpdateQuery(PCConstants.SHEET_SEARCHPOLICY,
					 * updateColumnNameValues, whereConstraint );
					 * 
					 * updateColumnNameValues.clear(); whereConstraint.clear();
					 * updateColumnNameValues.put(PCConstants.PolicyNumber, policyNumberSearch);
					 * whereConstraint.put(PCConstants.ID,
					 * PCThreadCache.getInstance().getProperty("TCID")); status =
					 * sXL.executeUpdateQuery(PCConstants.SHEET_ACCOUNTHISTORY,
					 * updateColumnNameValues, whereConstraint );
					 */
					// status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"),
					// "Update "+sheetName+" Set PolicyNumber = '"+policyNumber+"' where
					// ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "CLA Policy Number shold be generated", "CLA Policy Number generated: Policy Number is '" + policyNumber + "'", "PASS");
				} else {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "CLA Policy Number shold generated with 11 Digit", "CLA Policy Number is not generated with 11 Digit", "FAIL");
				}
			} else {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "CLA Policy Status shold be generated", "CLA Policy Status is not generated", "FAIL");
			}
		} finally {
			updateColumnNameValues = null;
			whereConstraint = null;
		}
		return status;
	}

	/**
	 * @function use to validate the error msg
	 * @return true/false
	 * @throws Exception
	 */
	public boolean VerifyErrorMsg() throws Exception {
		boolean status = driver.findElements(Common.o.getObject("eleCLAPIErrorMsgs")).size() != 0;
		String strErrorMsg = common.ReadElement(Common.o.getObject("eleCLAPIErrorMsgs"), Integer.valueOf(HTML.properties.getProperty("LONGWAIT")));
		if (status == true) {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Verify whether error message is displayed", "Error message is displayed : '" + strErrorMsg + "'", "WARNING");
			status = true;
		} else {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Verify whether error message is displayed or not", "Error message is not displayed : '" + strErrorMsg + "'", "FAIL");
			status = false;
		}
		return status;
	}

	/**
	 * @function use to validate the error msg
	 * @return true/false
	 * @throws Exception
	 */
	public boolean VerifySegmentErrorMsg() throws Exception {
		boolean status = driver.findElements(Common.o.getObject("elePCShellPolicyInfoErrorMessage")).size() != 0;
		String strErrorMsg = common.ReadElement(Common.o.getObject("elePCShellPolicyInfoErrorMessage"), Integer.valueOf(HTML.properties.getProperty("LONGWAIT")));
		if (status == true) {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Verify whether error message is displayed", "Error message is displayed : '" + strErrorMsg + "'", "WARNING");
			status = true;
		} else {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Verify whether error message is displayed or not", "Error message is not displayed : '" + strErrorMsg + "'", "FAIL");
			status = false;
		}
		return status;
	}

	/**
	 * @function Use to fill the data for line of business
	 * @param sFunctionValue
	 * @return true/false
	 * @throws Throwable
	 */
	public boolean lineOfBusiness(String sFunctionValue) throws Throwable {
		boolean status = false;
		String[] sValue = sFunctionValue.split(":::");
		int sRowCount = SCRCommon.TableRowCount(Common.o.getObject("eleCLAtblLineOfBusiness"));
		switch (sValue[0].toUpperCase()) {
		case "LOB":
			logger.info(sValue[0]);
			try {
				status = SCRCommon.DataWebTable(Common.o.getObject("eleCLAtblLineOfBusiness"), sRowCount, 1, "<none>", "single", "div");
				status = common.SafeAction(Common.o.getObject("lstLOBval"), sValue[1], "lst");
				// status =
				// SCRCommon.DataWebTable(Common.o.getObject("eleCLAtblLineOfBusiness"),
				// sRowCount, 2, sValue[3],"single");
				status = common.SafeAction(Common.o.getObject("lstLOSval"), sValue[2], "lst");
				// status = common.ActionOnTable(Common.o.getObject("eleLOBtbl"), sRowCount, 3,
				// "<none>","a");
				status = SCRCommon.DataWebTable(Common.o.getObject("eleLOBtbl"), sRowCount, 3, "<none>", "single", "a");
				status = getExposureStateList(sValue[3]);
				status = common.SafeAction(Common.o.getObject("eleCommonUpdate"), "", "eleCommonUpdate");
				status = SCRCommon.PageVerify("CLATitlePolicyInfo");
			} catch (Throwable e) {
				e.printStackTrace();
				if (status == false) {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should complete performing the add class", "System not completed performing the Line of Business table", "FAIL");
				}
			}
			break;
		/*
		 * case "LOS": logger.info(sValue[0]); try { status =
		 * SCRCommon.DataWebTable(Common.o.getObject("eleCLAtblLineOfBusiness"),
		 * sRowCount, 2, "<none>","single", "div"); status
		 * =common.SafeAction(Common.o.getObject("lstLOSval"), sValue[1], "lst"); status
		 * = common.ActionOnTable(Common.o.getObject("eleLOBtbl"), sRowCount, 3,
		 * "<none>","a"); status = getExposureStateList(sValue[2]); status =
		 * common.SafeAction(Common.o.getObject("eleCommonUpdate"), "",
		 * "eleCommonUpdate"); status = SCRCommon.PageVerify("CLATitlePolicyInfo"); }
		 * catch(Exception e) { e.printStackTrace(); if(status == false) {
		 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
		 * PCThreadCache.getInstance().getProperty("methodName"),
		 * "System should complete performing the add class"
		 * ,"System not completed performing the Line of Business table", "FAIL"); } }
		 * break;
		 */
		case "LOS":
			logger.info(sValue[0]);
			try {
				status = SCRCommon.DataWebTable(Common.o.getObject("eleCLAtblLineOfBusiness"), sRowCount, 2, "<none>", "single", "div");
				status = common.SafeAction(Common.o.getObject("lstLOSval"), sValue[1], "lst");
				status = SCRCommon.DataWebTable(Common.o.getObject("eleLOBtbl"), sRowCount, 3, "<none>", "single", "a");
				status = getExposureStateList(sValue[2]);
				status = common.SafeAction(Common.o.getObject("eleCommonUpdate"), "", "eleCommonUpdate");
				status = SCRCommon.PageVerify("CLATitlePolicyInfo");
			} catch (Exception e) {
				e.printStackTrace();
				if (status == false) {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should complete performing the add class", "System not completed performing the Line of Business table", 

"FAIL");
				}
			}
			break;
		case "EXPOSURESTATE":
			logger.info(sValue[0]);
			try {
				status = common.ActionOnTable(Common.o.getObject("eleLOBtbl"), 3, 3, "<none>", "a");
				status = getExposureStateList(sValue[1]);
				status = common.SafeAction(Common.o.getObject("eleCommonUpdate"), "", "eleCommonUpdate");
				status = SCRCommon.PageVerify("CLATitlePolicyInfo");
			} catch (Exception e) {
				e.printStackTrace();
				if (status == false) {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should complete performing the add class", "System not completed performing the Line of Business table", 

"FAIL");
				}
			}
			break;

		}
		return status;
	}

	/**
	 * @function Use to get the list of exposure state and select the state
	 *           according to the sheet
	 * @param sFuncValue
	 * @return true/false
	 */
	public boolean getExposureStateList(String sFuncValue) {
		boolean status = false;
		logger.info("Entering into the exposure state function");
		Actions objActions = new Actions(ManagerDriver.getInstance().getWebDriver());
		WebElement sExpsoureStateList = common.returnObject(Common.o.getObject("eleExposureStateList"));
		List<WebElement> sGetList = sExpsoureStateList.findElements(By.tagName("li"));
		for (WebElement element : sGetList) {
			String objName = element.getText();
			if (objName.contains(sFuncValue)) {
				objActions.doubleClick(element).build().perform();
				status = true;
				break;
			}
		}
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
		case "VERIFYCUEBUTTONSELECTABLE":
			logger.info(sValue[0]);
			status = common.ElementExistOrNotTrue("VerifyCUEButtonselectable", "CUEButton Should  be selectable", "CUEButton Should be selectable", (Common.o.getObject("eleCueButton")));
			break;

		case "VERIFYERRORMESSAGE":
			logger.info(sValue[0]);
			Value = common.ReadElement(Common.o.getObject("eleCommonZeroResults"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			break;
		case "CHECKPOLICYSTATUS":
			logger.info(sValue[0]);
			Value = common.ReadElement(Common.o.getObject("eleCLAPolicyStatus"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("Policy Status", sValue[1], Value);
			break;
		case "CHECKTRANSACTIONSTATUS":
			logger.info(sValue[0]);
			Value = common.ReadElement(Common.o.getObject("eleTransactionStatus"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("TransactionStatus", sValue[1], Value);
			break;
		case "CLABORDATE":
			logger.info(sValue[0]);
			Value = common.ReadElement(Common.o.getObject("eleCLABORStartDate"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("BORStartDate", SCRCommon.ReturnCurrentDate(), Value);
			Value = common.ReadElement(Common.o.getObject("eleCLABOREndDate"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("BOREndDate", SCRCommon.ReturnHundredAndEightyDaysFromDate(), Value);
			break;
		case "VERIFYPOLICYINFOSCREEN":
			logger.info(sValue[0]);
			status = common.ElementExistOrNotTrue("VerifyPolicyInfoScreen", "Policy Info Screen should not be editable", "Policy Info Screen should not be editable", (Common.o.getObject("elePolEffecDate")));
			status = common.ElementExistOrNotTrue("VerifyPolicyInfoScreen", "Policy Info Screen should not be editable", "Policy Info Screen should not be editable", (Common.o.getObject("elePolExpDate")));
			status = common.ElementExistOrNotTrue("VerifyPolicyInfoScreen", "Policy Info Screen should not be editable", "Policy Info Screen should not be editable", (Common.o.getObject("elePolSIC")));
			status = common.ElementExistOrNotTrue("VerifyPolicyInfoScreen", "Policy Info Screen should not be editable", "Policy Info Screen should not be editable", (Common.o.getObject("elePolMSI")));
			status = common.ElementExistOrNotTrue("VerifyPolicyInfoScreen", "Policy Info Screen should not be editable", "Policy Info Screen should not be editable", (Common.o.getObject("elePolBusinessSeg")));
			status = common.ElementExistOrNotTrue("VerifyPolicyInfoScreen", "Policy Info Screen should not be editable", "Policy Info Screen should not be editable", (Common.o.getObject("elePolProducer")));
			/*
			 * Value = Common.ReadElement(Common.o.getObject("elePolEffecDate"),
			 * Integer.valueOf(HTML.properties.getProperty("NORMALWAIT"))); status =
			 * Common.CompareStringResult("PolicyEffectiveDate",
			 * SCRCommon.ReturnCurrentDate(), Value); Value =
			 * Common.ReadElement(Common.o.getObject("elePolExpDate"),
			 * Integer.valueOf(HTML.properties.getProperty("NORMALWAIT"))); status =
			 * Common.CompareStringResult("PolicyExpirationDate", sValue[2], Value); Value =
			 * Common.ReadElement(Common.o.getObject("elePolSIC"),
			 * Integer.valueOf(HTML.properties.getProperty("NORMALWAIT"))); status =
			 * Common.CompareStringResult("PolicySICNumber", sValue[3], Value); Value =
			 * Common.ReadElement(Common.o.getObject("elePolMSI"),
			 * Integer.valueOf(HTML.properties.getProperty("NORMALWAIT"))); status =
			 * Common.CompareStringResult("PolicyMSINumber", sValue[4], Value); Value =
			 * Common.ReadElement(Common.o.getObject("elePolBusinessSeg"),
			 * Integer.valueOf(HTML.properties.getProperty("NORMALWAIT"))); status =
			 * Common.CompareStringResult("PolicyBusinessSegment", sValue[5], Value); Value
			 * = Common.ReadElement(Common.o.getObject("elePolProducer"),
			 * Integer.valueOf(HTML.properties.getProperty("NORMALWAIT"))); status =
			 * Common.CompareStringResult("PolicyProducer", sValue[6], Value);
			 */
			break;
		case "BORDATE":
			logger.info(sValue[0]);
			Value = common.ReadElement(Common.o.getObject("eleBORStartDate"), Integer.valueOf(HTML.properties.getProperty

("NORMALWAIT")));
			status = common.CompareStringResult("PolicyBORStartDate", SCRCommon.ReturnCurrentDate(), Value);
			Value = common.ReadElement(Common.o.getObject("eleBOREndDate"), Integer.valueOf(HTML.properties.getProperty

("NORMALWAIT")));
			status = common.CompareStringResult("PolicyBOREndDate", SCRCommon.ReturnSixtyDaysFromDate(), Value);
			break;
		case "RECEIVEDDATE":
			logger.info(sValue[0]);
			Value = common.ReadElement(Common.o.getObject("eleBORReceivedDate"), Integer.valueOf(HTML.properties.getProperty

("NORMALWAIT")));
			status = common.CompareStringResult("PolicyReceivedDate", SCRCommon.ReturnCurrentDate(), Value);
			break;
		case "VERIFYBORNOTSET":
			logger.info(sValue[0]);
			Value = common.ReadElement(Common.o.getObject("eleBORStartDate"), Integer.valueOf(HTML.properties.getProperty

("NORMALWAIT")));
			if (Value.equals(SCRCommon.ReturnCurrentDate())) {
				status = false;
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Current Date should not match", "Current Date is matching", "FAIL");
			} else {
				status = true;
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Current Date should not match", "Current Date is not matching", "PASS");
			}

			Value = common.ReadElement(Common.o.getObject("eleBOREndDate"), Integer.valueOf(HTML.properties.getProperty

("NORMALWAIT")));
			if (Value.equals(SCRCommon.ReturnSixtyDaysFromDate())) {
				status = false;
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Sixty Days from Current Date should not match", "Sixty Days from Current Date is matching", "FAIL");
			} else {
				status = true;
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Sixty Days from Current Date should not match", "Sixty Days from Current Date is not matching", "PASS");
			}
			break;
		case "VERIFYBORSTARTDATE":
			logger.info(sValue[0]);
			Value = common.ReadElement(Common.o.getObject("eleBORStartDate"), Integer.valueOf(HTML.properties.getProperty

("NORMALWAIT")));
			status = common.CompareStringResult("PolicyBORStartDate", SCRCommon.ReturnCurrentDate(), Value);
			break;
		case "VERIFYBORENDDATE":
			logger.info(sValue[0]);
			Value = common.ReadElementGetAttribute(Common.o.getObject("eleBOREndDate"), "value", Integer.valueOf

(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("PolicyBOREnd", sValue[1], Value);
			break;
		/*
		 * case "VERIFYFIELDS": logger.info(sValue[0]);
		 * //status=ActionOnFieldExistence(Common.o.getObject("eleTypeofCuisineLbl")),
		 * "Input"); status=ActionOnFieldExistence("Type of Cuisine","Input");
		 * status=ActionOnFieldExistence("Risk Management Solutions","YES");
		 * status=ActionOnFieldExistence("Motor Carrier Filings","NO");
		 * status=ActionOnFieldExistence("Individual Financial Responsibility (SR22)"
		 * ,"YES");
		 * status=ActionOnFieldExistence("TX Wind/Hail Exclusion Received","NO"); break;
		 */
		/*
		 * case "VERIFY_CANADIAN_POLICY_FIELDS": logger.info(sValue[0]); //String
		 * exlValue=Common.getSpecifiedExcelValue(sheetName, "edtOtherPolicyNo"); Value
		 * = common.ReadElement(Common.o.getObject("edtOtherPolicyNo"),
		 * Integer.valueOf(HTML.properties.getProperty("NORMALWAIT"))); status =
		 * common.CompareStringResult("OtherPolicy number - Canadian Policy", sValue[1],
		 * Value); Value = common.ReadElement(Common.o.getObject("edtExchangeRate"),
		 * Integer.valueOf(HTML.properties.getProperty("NORMALWAIT"))); status =
		 * common.CompareStringResult("Exchange Rate - Canadian Policy", sValue[2],
		 * Value); Value =
		 * common.ReadElement(Common.o.getObject("edtTotalCanadianCost"),
		 * Integer.valueOf(HTML.properties.getProperty("NORMALWAIT"))); status =
		 * common.CompareStringResult("Total Canadian cost  - Canadian Policy",
		 * sValue[3], Value); Value =
		 * common.ReadElement(Common.o.getObject("edtMailDate"),
		 * Integer.valueOf(HTML.properties.getProperty("NORMALWAIT"))); status =
		 * common.CompareStringResult("Mail Date  - Canadian Policy", sValue[4], Value);
		 * //total cost field exitence
		 * status=common.SafeAction(Common.o.getObject("edtTotalCost"), sValue[5],
		 * "edt"); String
		 * ActValue=common.ReadElementFromListEditBox(Common.o.getObject("edtTotalCost")
		 * , Integer.valueOf(HTML.properties.getProperty("NORMALWAIT"))); status =
		 * common.CompareStringResult("Total cost  - Canadian Policy", sValue[5],
		 * ActValue); break;
		 */
		case "WARNINGALERT":
			logger.info(sValue[0]);
			// String exlValue=Common.getSpecifiedExcelValue(sheetName, "edtOtherPolicyNo");
			// Value = common.ReadElement(Common.o.getObject("edtOtherPolicyNo"),
			// Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			// status = common.CompareStringResult("OtherPolicy number - Canadian Policy",
			// sValue[1], Value);
			status = common.ElementExistOrNotTrue(sValue[0], "Warning Alert should found", "Warning alert found", Common.o.getObject

("eleCLAPIErrorMsgs"));
			break;
		case "VERIFYFIELDS":
			logger.info(sValue[0]);
			// status=ActionOnFieldExistence(Common.o.getObject("eleTypeofCuisineLbl")),"Input");
			status = ActionOnFieldExistence("Type of Cuisine", "Input");
			status = ActionOnFieldExistence("Risk Management Solutions", "YES");
			status = ActionOnFieldExistence("Motor Carrier Filings", "NO");
			status = ActionOnFieldExistence("Individual Financial Responsibility (SR22)", "YES");
			status = ActionOnFieldExistence("TX Wind/Hail Exclusion Received", "NO");
			break;
		case "VERIFY_CANADIAN_POLICY_FIELDS":
			logger.info(sValue[0]);
			// String exlValue=Common.getSpecifiedExcelValue(sheetName, "edtOtherPolicyNo");
			Value = common.ReadElementGetAttribute(Common.o.getObject("edtOtherPolicyNo"), "value", Integer.valueOf

(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("OtherPolicy number - Canadian Policy", sValue[1], Value);
			Value = common.ReadElementGetAttribute(Common.o.getObject("edtExchangeRate"), "value", Integer.valueOf

(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("Exchange Rate - Canadian Policy", sValue[2], Value);
			Value = common.ReadElementGetAttribute(Common.o.getObject("edtTotalCanadianCost"), "value", Integer.valueOf

(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("Total Canadian cost  - Canadian Policy", sValue[3], Value);
			Value = common.ReadElementGetAttribute(Common.o.getObject("edtMailDate"), "value", Integer.valueOf

(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("Mail Date  - Canadian Policy", sValue[4], Value);
			// total cost field exitence
			status = common.SafeAction(Common.o.getObject(""), sValue[5], "edt");
			String ActValue = common.ReadElementFromListEditBox(Common.o.getObject("edtTotalCost"), Integer.valueOf

(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("Total cost  - Canadian Policy", sValue[5], ActValue);
			break;
		case "VERIFYCUEBUTTONUNSELECTABLE":
			logger.info(sValue[0]);
			status = common.ElementExistOrNotTrue("VerifyCUEButtonUnselectable", "CUEButton Should not be selectable", "CUEButton Should not be selectable", (Common.o.getObject("eleCueButton")));
			break;
		case "VERIFYRESERVEPOLICYNUMBER":
			logger.info(sValue[0]);
			status = common.ElementExistOrNotTrue("VerifyReservePolicyButtonselectable", "ReservePolicyButton Should  be selectable", "ReservePolicyButton Should  be selectable", (Common.o.getObject("eljCLAReservePolicyNumber")));
			break;
		case "VERIFY_TOTALCOST_FIELD":
			logger.info(sValue[0]);
			Value = common.ReadElementFromListEditBox(Common.o.getObject("edtTotalCost"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("Validate value is set into Editable - total cost field", sValue[1], Value);
			break;
		case "VALIDATE_CANADIAN_FIELD":
			Value = common.ReadElementGetAttribute(Common.o.getObject("elePI_ExchangeRate"), "value", Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("Exchange Rate - Canadian Policy", sValue[1], Value);
			Value = common.ReadElementGetAttribute(Common.o.getObject("elePI_TotalCostCandaian"), "value", Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("Total Canadian cost  - Canadian Policy", sValue[2], Value);
			Value = common.ReadElementFromListEditBox(Common.o.getObject("elePI_TotalCost"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("Total cost  - Canadian Policy", sValue[3], Value);
			break;

		}
		if (!status) {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "" + sValue[0] + " case should PASS", "" + sValue[0] + " case should not fail", "FAIL");
			status = true;
		}
		return status;
	}

	/**
	 * @function created to check the calendar control existence
	 * @param funValue
	 * @return true/false
	 * @throws Exception
	 */
	public Boolean VerifyCalendarControlExistence(String funValue) throws Exception {
		boolean status = false;
		status = SCRCommon.VerifyCalendarControlExistence(funValue);
		return status;
	}

	/*
	 * public Boolean ActionOnFieldExistence(String sLabelValue, String
	 * sRadioButtonValue) throws Exception { boolean Status =false; boolean
	 * SearchString=false; String readText = null, sYes = null, sNo =null;
	 * List<WebElement> sTable = driver.findElements(By.tagName("label"));
	 * for(WebElement ele : sTable) { readText = ele.getText(); if
	 * (readText.contains(sLabelValue)) { SearchString = true;
	 * logger.info("Search String available in the table. '" + sLabelValue + "'");
	 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
	 * PCThreadCache.getInstance().getProperty("methodName"),
	 * "System should search string in table and Search string is '" + sLabelValue +
	 * "'","System searched string in table and srarch string is  '" + readText +
	 * "'", "PASS"); String sLabelId = ele.getAttribute("id"); String[] sSplitReadID
	 * = sLabelId.split("-"); if(sRadioButtonValue.contains("Input")) {
	 * logger.info("Search Element with label '"
	 * +readText+"' coressponding Input field should be verified for existence");
	 * sYes = sSplitReadID[0].concat("-inputEl"); //Status =
	 * Common.SafeAction(By.id(sYes), "ele", "ele"); //String
	 * value=Common.ReadElement(By.id(sYes),
	 * Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
	 * Status=common.ElementDisplayed(By.id(sYes));
	 * 
	 * if (Status) {
	 * logger.info("Search and find object in the table cell and object name is '" +
	 * sYes + "'");
	 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
	 * PCThreadCache.getInstance().getProperty("methodName"),
	 * "System should search object '"
	 * +sLabelValue+"' in the table cell and corresponding field '" + sYes +
	 * "'","System searched object the object '"
	 * +sLabelValue+"' in the table and correspoding field exists '" + sYes + "'",
	 * "PASS"); } else {
	 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
	 * PCThreadCache.getInstance().getProperty("methodName"),
	 * "System should search object '"
	 * +sLabelValue+"' in the table cell and click on object '" + sYes +
	 * "'","System searched object the object '"
	 * +sLabelValue+"' in the table. correspoding field  doesnt exists  '" + sYes +
	 * "'", "FAIL"); } } else if(sRadioButtonValue.contains("YES")) {
	 * logger.info("Search Element with label '"
	 * +readText+"' with option YES to be selected"); sYes =
	 * sSplitReadID[0].concat("_true-boxLabelEl"); Status =
	 * common.SafeAction(By.id(sYes), "ele", "ele"); //String
	 * value=Common.ReadElement(By.id(sYes),
	 * Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
	 * //Status=Common.ElementDisplayed(By.id(sYes));
	 * 
	 * if (Status) {
	 * logger.info("Search and find object in the table cell and object name is '" +
	 * sYes + "'");
	 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
	 * PCThreadCache.getInstance().getProperty("methodName"),
	 * "System should search object '"
	 * +sLabelValue+"' in the table cell and corresponding field '" + sYes +
	 * "'","System searched object the object '"
	 * +sLabelValue+"' in the table and correspoding field exists '" + sYes + "'",
	 * "PASS"); } else {
	 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
	 * PCThreadCache.getInstance().getProperty("methodName"),
	 * "System should search object '"
	 * +sLabelValue+"' in the table cell and click on object '" + sYes +
	 * "'","System searched object the object '"
	 * +sLabelValue+"' in the table. correspoding field  doesnt exists  '" + sYes +
	 * "'", "FAIL"); } } else { logger.info("Search Element with label '"
	 * +readText+"' with option No to be selected"); sNo =
	 * sSplitReadID[0].concat("_false-boxLabelEl"); Status =
	 * common.SafeAction(By.id(sNo), "ele", "ele"); if (Status) { logger.
	 * info("Search and click on object in the table cell and object name is '" +
	 * sYes + "'");
	 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
	 * PCThreadCache.getInstance().getProperty("methodName"),
	 * "System should search object '"
	 * +sLabelValue+"' in the table cell and click on object '" + sNo +
	 * "'","System searched object the object '"
	 * +sLabelValue+"' in the table and clicked on object '" + sNo + "'", "PASS"); }
	 * else {
	 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
	 * PCThreadCache.getInstance().getProperty("methodName"),
	 * "System should search object '"
	 * +sLabelValue+"' in the table cell and click on object '" + sYes +
	 * "'","System searched object the object '"
	 * +sLabelValue+"' in the table. But NOT able to perform action on object '" +
	 * sYes + "'", "PASS"); } } } } if(!SearchString) {
	 * logger.info("Search String is NOT available in the table. '" + sLabelValue +
	 * "'"); HTML.fnInsertResult(HTML.properties.getProperty("testcasename"),
	 * PCThreadCache.getInstance().getProperty("methodName"),
	 * "System should search string in table and Search string is '" + sLabelValue +
	 * "'","Coluld not find string '" + sLabelValue + "'", "FAIL"); } if(!Status) {
	 * logger.info("could not find object '" + sYes + "' for the filed '"+"'"+
	 * sLabelValue);
	 * HTML.fnInsertResult(HTML.properties.getProperty("testcasename"),
	 * PCThreadCache.getInstance().getProperty("methodName"),
	 * "System should search object '"
	 * +sLabelValue+"' in the table cell and click on object '" + sYes +
	 * "'","Could not find object '" + sYes + "'for '"+"'sLabelValue", "FAIL"); }
	 * return Status; }
	 */

	/**
	 * @function Uses to click the radio button
	 * @param sLabelValue
	 * @param sRadioButtonValue
	 * @return true/false
	 * @throws Exception
	 */
	public Boolean ActionOnFieldExistence(String sLabelValue, String sRadioButtonValue) throws Exception {
		boolean Status = false;
		boolean SearchString = false;
		String readText = null, sYes = null, sNo = null;
		List<WebElement> sTable = driver.findElements(By.tagName("label"));
		for (WebElement ele : sTable) {
			readText = ele.getText();
			if (readText.contains(sLabelValue)) {
				SearchString = true;
				logger.info("Search String available in the table. '" + sLabelValue + "'");
				HTML.fnInsertResult(HTML.properties.getProperty("testcasename"), HTML.properties.getProperty("methodName"), 

"System should search string in table and Search string is '" + sLabelValue + "'", "System searched string in table and srarch string is  '" + 

readText + "'", "PASS");
				String sLabelId = ele.getAttribute("id");
				String[] sSplitReadID = sLabelId.split("-");
				if (sRadioButtonValue.contains("Input")) {
					logger.info("Search Element with label '" + readText + "' coressponding Input field should be verified for existence");
					sYes = sSplitReadID[0].concat("-inputEl");
					// Status = Common.SafeAction(By.id(sYes), "ele", "ele");
					// String value=Common.ReadElement(By.id(sYes),
					// Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
					Status = common.ElementDisplayed(By.id(sYes));
				} else if (sRadioButtonValue.contains("YES")) {
					logger.info("Search Element with label '" + readText + "' with option YES to be selected");
					sYes = sSplitReadID[0].concat("_true-boxLabelEl");
					Status = common.SafeAction(By.id(sYes), "ele", "ele");
				} else {
					logger.info("Search Element with label '" + readText + "' with option No to be selected");
					sNo = sSplitReadID[0].concat("_false-boxLabelEl");
					Status = common.SafeAction(By.id(sNo), "ele", "ele");
				}
			}
		}
		if (!SearchString) {
			logger.info("Search String is NOT available in the table. '" + sLabelValue + "'");
			HTML.fnInsertResult(HTML.properties.getProperty("testcasename"), HTML.properties.getProperty("methodName"), "System should search string in table and Search string is '" + sLabelValue + "'", "Coluld not find string '" + sLabelValue + "'", "FAIL");
		}

		if (Status) {
			logger.info("Search and click on object in the table cell and object name is '" + sYes + "'");
			HTML.fnInsertResult(HTML.properties.getProperty("testcasename"), HTML.properties.getProperty("methodName"), "System should search object '" + sLabelValue + "' in the table cell and click on object '" + sNo + "'", "System searched object the object '" + 

sLabelValue + "' in the table and clicked on object '" + sNo + "'", "PASS");
		} else {
			HTML.fnInsertResult(HTML.properties.getProperty("testcasename"), HTML.properties.getProperty("methodName"), "System should search object '" + sLabelValue + "' in the table cell and click on object '" + sYes + "'", "System searched object the object '" + 

sLabelValue + "' in the table. But NOT able to perform action on object '" + sYes + "'", "PASS");
		}
		return Status;
	}

	/**
	 * @function Use to
	 * @param sFunValue
	 * @return
	 */
	public boolean LOBDetailsTable(String sFunValue) {
		common.WaitForPageToBeReady();
		boolean status = false;
		String[] sValue = sFunValue.split(":::");
		int sRowCount = SCRCommon.TableRowCount(Common.o.getObject("eleCLAtblLineOfBusiness"));
		try {
			if (sValue[0].equalsIgnoreCase("null")) {
				status = SCRCommon.DataWebTable(Common.o.getObject("eleCLAtblLineOfBusiness"), sRowCount, 1, "", "single", "div");
				status = common.SafeAction(Common.o.getObject("lstLOBType"), sValue[0], "lst");
				driver.findElement(Common.o.getObject("lstLOBLevelSupport")).sendKeys(Keys.ESCAPE);
			}
			if (sValue[1].equalsIgnoreCase("null")) {
				status = SCRCommon.DataWebTable(Common.o.getObject("eleCLAtblLineOfBusiness"), sRowCount, 2, "", "single", "div");
				status = common.SafeAction(Common.o.getObject("lstLOBLevelSupport"), sValue[1], "lst");
			}

			if (!sValue[2].equalsIgnoreCase("null")) {
				status = SCRCommon.DataWebTable(Common.o.getObject("eleCLAtblLineOfBusiness"), sRowCount, 3, "<none>", "single", "div");
				status = common.SafeAction(Common.o.getObject("eleExposureStateColumn"), "ele", "ele");
				status = common.SafeAction(By.xpath("//li[contains(text(),'" + sValue[2] + "')]"), "dbl", "dbl");
				status = common.SafeAction(Common.o.getObject("eleCommonUdpate"), "ele", "ele");
			}

			if (sValue[3].equalsIgnoreCase("null")) {
				status = SCRCommon.DataWebTable(Common.o.getObject("eleCLAtblLineOfBusiness"), sRowCount, 4, "", "single", "div");
				status = common.SafeAction(Common.o.getObject("edtLOBTotalCost"), sValue[3], "edt");
			}
			//
			if (!sValue[4].equalsIgnoreCase("null")) {
				status = SCRCommon.DataWebTable(Common.o.getObject("eleCLAtblLineOfBusiness"), sRowCount, 5, "<none>", "single", "div");
				status = common.SafeAction(Common.o.getObject("lstLOBAMPUWCompany"), sValue[4], "lst");
			}

			if (sValue[5].equalsIgnoreCase("null")) {
				status = SCRCommon.DataWebTable(Common.o.getObject("eleCLAtblLineOfBusiness"), sRowCount, 6, "", "single", "div");
			}

			if (sValue[6].equalsIgnoreCase("null")) {
				status = SCRCommon.DataWebTable(Common.o.getObject("eleCLAtblLineOfBusiness"), sRowCount, 7, "", "single", "div");
				status = common.SafeAction(Common.o.getObject("edtLOBNumMotrizedVehicle"), sValue[6], "edt");
			}
			if (sValue[7].equalsIgnoreCase("null")) {
				status = SCRCommon.DataWebTable(Common.o.getObject("eleCLAtblLineOfBusiness"), sRowCount, 8, "", "single", "div");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * method to Update Policy Eff & Exp date in Sheet
	 * 
	 * @return boolean
	 * @throws Exception
	 */
	public Boolean UpdatePolicyEffDate() throws Exception {
		HashMap<String, Object> updateColumnNameValues = new HashMap<String, Object>();
		HashMap<String, Object> whereConstraint = new HashMap<String, Object>();
		try {
			Boolean status = true;
			if (common.WaitUntilClickable(Common.o.getObject("edtCLAEffDate"), Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")))) {
				XlsxReader sXL = XlsxReader.getInstance();
				PolicyEffDate = common.ReadElementGetAttribute(Common.o.getObject("edtCLAEffDate"), "value", 30);
				PolicyExpDate = common.ReadElementGetAttribute(Common.o.getObject("edtCLAExpDate"), "value", 30);
				PCThreadCache.getInstance().setProperty(PCConstants.CACHE_POLICY_EFF_DATE, PolicyEffDate);
				updateColumnNameValues.put(PCConstants.CLAPolicyEffDate, PolicyEffDate);
				whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
				PCThreadCache.getInstance().setProperty("CLAPolicyEffDate", PolicyEffDate);
				//status = sXL.executeUpdateQuery(sheetName, updateColumnNameValues, whereConstraint);

				PCThreadCache.getInstance().setProperty(PCConstants.CACHE_POLICY_EXP_DATE, PolicyEffDate);
				updateColumnNameValues.put(PCConstants.CLAPolicyExpDate, PolicyExpDate);
				whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
				PCThreadCache.getInstance().setProperty("CLAPolicyExpDate", PolicyExpDate);
				//status = sXL.executeUpdateQuery(sheetName, updateColumnNameValues, whereConstraint);
				// status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"),
				// "Update CreateAccount Set AccountNumber = '"+accountNumber+"' where
				// ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
				logger.info("System displayed Policy Info Page with Policy Eff Date : " + PolicyEffDate);
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should display Policy Info Page with Policy Eff Date", "System displayed Policy Info Page with Policy Eff Datr: '" + PolicyEffDate + "'", "PASS");
				status = true;
			} else {
				logger.info("System not displayed Policy Info Page");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should display Policy Info Page with Policy Eff Dat", "System not displayed Policy Info Page with Policy Eff Dat", "FAIL");
				status = false;
			}
			return status;
		} finally {
			updateColumnNameValues = null;
			whereConstraint = null;
		}

	}

	/**
	 * @function Uses to enter current date in BOR Control Start Date field
	 * @param
	 * @return true/false
	 * @throws Exception
	 */
	public boolean BORCtrlStartDate() throws Exception {
		boolean status = false;
		String sCurrentDate = SCRCommon.ReturnCurrentDate();
		status = common.SafeAction(Common.o.getObject("edtBORCtrlStartDate"), sCurrentDate, "edt");
		return status;
	}

	/**
	 * @function Uses to enter current date in BOR Control End Date field
	 * @param
	 * @return true/false
	 * @throws Exception
	 */
	public boolean BORCtrlEndDate() throws Exception {
		boolean status = false;
		String sCurrentDate = SCRCommon.ReturnCurrentDate();
		status = common.SafeAction(Common.o.getObject("edtBORCtrlEndDate"), sCurrentDate, "edt");
		return status;
	}

	/**
	 * Function to change exp date. Can be updated for any other date field
	 * 
	 * @param strValue
	 * @return boolean
	 * @throws Exception
	 */
	public boolean Date(String strValue) throws Exception {
		boolean status = false;
		String strDate;

		Calendar Date = Calendar.getInstance();
		try {
			switch (strValue.toUpperCase()) {
			case "EXPDT_LESSTHAN_A_YEAR":
				Date.add(Calendar.MONTH, 7);
				String strMonth = String.valueOf(Date.get(Calendar.MONTH));
				if (strMonth.length() == 1) {
					strMonth = "0" + strMonth;
				}
				strDate = strMonth + "/" + (Date.get(Calendar.DATE) + "/" + (Date.get(Calendar.YEAR)));
				status = common.SafeAction(Common.o.getObject("edtCLAExpDate"), strDate, "edt");
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	public boolean lineofBusiness(String strFuncValue) {
		Boolean blnStatus = false;
		/*
		 * if(HTML.properties.getProperty("Region").contains("QA02")) { blnStatus =
		 * SCRCommon.tableHandling(strFuncValue, "eleCLAtblLineOfBusiness",
		 * "elePolInfoLOBColumnSearch_QA2", "elePolInfoLOB");
		 * 
		 * }else { blnStatus = SCRCommon.tableHandling(strFuncValue,
		 * "eleCLAtblLineOfBusiness", "elePolInfoLOBColumnSearch", "elePolInfoLOB"); }
		 */
		blnStatus = SCRCommon.tableHandling(strFuncValue, "eleCLAtblLineOfBusiness", "elePolInfoLOBColumnSearch_QA2", "elePolInfoLOB");
		return blnStatus;
	}

	public boolean submissionOriginalDate(String strFuncValue) throws Exception {
		boolean status = false;
		String[] strSplitValue = strFuncValue.split(":::");
		String strDate = null;
		switch (strSplitValue[0].toUpperCase()) {
		case "CURRENTDATE":
			strDate = SCRCommon.ReturnCurrentDate();
			status = common.SafeAction(Common.o.getObject("edtCLASubmissionOrginalDate"), strDate, "edt");
			break;
		case "INCREASEDECREASEDATE":
			strDate = SCRCommon.returnDate(Integer.parseInt(strSplitValue[1]));
			status = common.SafeAction(Common.o.getObject("edtCLASubmissionOrginalDate"), strDate, "edt");
			break;
		}
		return status;
	}

	public boolean mMUserNBReceivedDate(String strFuncValue) throws Exception {
		boolean status = false;
		String[] strSplitValue = strFuncValue.split(":::");
		String strDate = null;
		switch (strSplitValue[0].toUpperCase()) {
		case "CURRENTDATE":
			strDate = SCRCommon.ReturnCurrentDate();
			status = common.SafeAction(Common.o.getObject("edtCLAMMUserReceivedDate"), strDate, "edt");
			break;
		case "INCREASEDECREASEDATE":
			strDate = SCRCommon.returnDate(Integer.parseInt(strSplitValue[1]));
			status = common.SafeAction(Common.o.getObject("edtCLAMMUserReceivedDate"), strDate, "edt");
			break;
		}
		return status;
	}

}