/**
 * @ClassPurpose Access all the data's from RiskAnalysis sheet
 * @Scriptor Krishna
 * @ReviewedBy
 * @ModifiedBy Rajeshwari & Siva
 * @LastDateModified 4/3/2017
 */
package com.pc.screen;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.PCThreadCache;

public class RiskAnalysis {

	public static String sheetname = "RiskAnalysis";
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();

	public Boolean SCRRiskAnalysis() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

	/**
	 * Function to verify Results for different conditions
	 * 
	 * @param funValue
	 * @return
	 * @throws Exception
	 */
	public boolean VerifyResults(String funValue) throws Exception {
		boolean status = false;
		int rwCnt = 0;
		String[] sValue = funValue.split(":::");
		logger.info("Verifying the Results");
		String value = null;
		String wait = HTML.properties.getProperty("NORMALWAIT");
		Integer x = Integer.valueOf(wait);
		switch (sValue[0].toUpperCase()) {
		case "VERIFYERRMESSAGE":
			logger.info("Verification stated for " + sValue[0]);
			value = common.ReadElement(Common.o.getObject(sValue[1]), x);
			status = common.CompareStringResult("Verify Error message '" + sValue[2] + "'", sValue[2], value);
			break;
		case "VALIDATE_REFERRAL_DETAILS":
			logger.info("Verification stated for " + sValue[0]);
			status = common.ActionOnTable(Common.o.getObject("eleUWReferralReason_tbl"), 2, 1, sValue[1], "img");
			status = common.SafeAction(Common.o.getObject("eleViewDetails_btn"), "ele", "ele");
			break;
		case "VERIFY_ACCOUNTFLAG_ICON":
			logger.info("Verification stated for " + sValue[0]);
			// status=common.ElementDisplayed(common.o.getObject("eleFlagIcon_AccInfoBar"));
			status = common.ElementExistOrNotTrue("hI", "a", "b", Common.o.getObject("eleFlagIcon_AccInfoBar"));

			if (!status) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Element Displayed - Flag Icon", "Flag Icon is NOT displayed in InfoBar", "FAIL");
			}
			status = common.ElementExistOrNotTrue("validating the Flag Icon on InfoBar", "flag icon should be available in Account Info Bar", "Flag Icon is available in Info Bar", Common.o.getObject("eleFlagIcon_AccInfoBar"));
			break;
		case "VERIFY_NO_ACCOUNTFLAG_ICON":
			logger.info("Verification stated for " + sValue[0]);
			status = common.ElementExistOrNotFalse("validating the Flag Icon on InfoBar", "flag icon should NOT be available in Account Info Bar", "Flag Icon is NOT available in Info Bar", Common.o.getObject("eleFlagIcon_AccInfoBar"));
			break;

		case "VERIFY_NOOPENUWREFERRAL":
			logger.info("Verification stated for " + sValue[0]);
			int sRowCount = SCRCommon.TableRowCount(Common.o.getObject("eleUWReferralReason_tbl"));
			if (sRowCount > 0) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Records availablity in  UW Referral Reason", "Records are available in Table -UW Referral Reason ", "FAIL");
			} else {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Records availablity in  UW Referral Reason", "NO Records available in Table -UW Referral Reason ", "PASS");
				status = true;
			}

			break;
		case "CHECKPOLICYSTATUS":
			logger.info(sValue[0]);
			value = common.ReadElement(Common.o.getObject("elePolicyInfo_PolicyStatus"), Integer.valueOf(HTML.properties.getProperty

("NORMALWAIT")));
			status = common.CompareStringResult("Policy Status", sValue[1], value);
			break;
		case "CHECKNOISSUANCE":
			logger.info(sValue[0]);
			value = common.ReadElement(Common.o.getObject("eleRiskAnalysis_NoissuanceFlag"), Integer.valueOf

(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("Policy Status", sValue[1], value);
			break;
		case "CHECKNOISSUANCEQUOTE":
			value = common.ReadElement(Common.o.getObject("eleRiskAnalysisQuote_NoissuanceFlag"), Integer.valueOf

(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("Policy Status", sValue[1], value);
			break;

		}
		return status;

	}

	/**
	 * @function used to validate the details ReferralReason details screen
	 * @param strDetails
	 * @return boolean
	 * @throws Exception
	 */
	public boolean VerifyAdded_UWReferralReason(String strDetails) throws Exception {
		boolean status = false;
		String value = null;
		String[] sValue = strDetails.split(":::");
		logger.info("Verifying the Added ReferralReason");
		status = common.ActionOnTable(Common.o.getObject("eleUWReferralReason_tbl"), 1, 0, sValue[1], "img");
		status = common.SafeAction(Common.o.getObject("eleViewDetails_btn"), "ele", "ele");
		status = common.ElementExistOrNotTrue("Referral Reason Details", "Referral Reason details should be present", "Referral reason details screen is displayed", Common.o.getObject("eleReferralReasonDetails_Title"));
		// value=common.GetTextFromTable(common.o.getObject("eleHistory_Referral_tbl"),
		// 0, 1);
		value = common.GetTextFromTableTagName(Common.o.getObject("eleHistory_Referral_tbl"), 0, 1, "div"); // date
		status = common.CompareStringResult("Referral Details screen- Status", SCRCommon.ReturnCurrentDate(), value);
		value = common.GetTextFromTableTagName(Common.o.getObject("eleHistory_Referral_tbl"), 0, 0, "div");
		HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "Validate UserName field value", "Value in UserName field -" + value, "PASS");
		value = common.GetTextFromTableTagName(Common.o.getObject("eleHistory_Referral_tbl"), 0, 2, "div"); // effective date
		status = common.CompareStringResult("Referral Details screen- Status", SCRCommon.ReturnCurrentDate(), value);
		value = common.GetTextFromTableTagName(Common.o.getObject("eleHistory_Referral_tbl"), 0, 3, "div"); // alert type
		status = common.CompareStringResult("Referral Details screen- Status", sValue[2], value);
		value = common.GetTextFromTableTagName(Common.o.getObject("eleHistory_Referral_tbl"), 0, 5, "div"); // status
		status = common.CompareStringResult("Referral Details screen- Status", sValue[3], value);
		value = common.GetTextFromTableTagName(Common.o.getObject("eleHistory_Referral_tbl"), 0, 4, "div");
		HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "Validate Action Taken field value", "Value in ActionTaken field -" + value, "PASS");
		// clicking on cancel button
		return status;
	}

	public boolean selectReferralReason(String activityName) throws Exception {
		Boolean blnStatus = false;
		blnStatus = common.ActionOnTable(Common.o.getObject("eleUWReferralReason_tbl"), 1, 0, activityName, "img");
		return blnStatus;
	}

	public boolean HistoricalLossTbl(String sFunValue) throws Exception {
		boolean status = false;
		String[] sSetValue = sFunValue.split(":::");
		logger.info("Verifying the Results");

		status = common.SafeAction(Common.o.getObject("eleTotalIncured"), "YES", "ele");
		status = common.SafeAction(Common.o.getObject("edtRiskTotalIncurred"), sSetValue[0], "edtRiskTotalIncurred");

		/*
		 * boolean status = false; String[] sValue = sFunValue.split(":::");
		 * status=common.SafeAction(Common.o.getObject("eleExposureField"), "YES",
		 * "ele"); int sRowCount =
		 * SCRCommon.TableRowCount(Common.o.getObject("eleHistoricalLossTbl")); try {
		 * status = SCRCommon.DataWebTable(Common.o.getObject("eleHistoricalLossTbl"),
		 * sRowCount, 1, "","single","input"); status
		 * =common.SafeAction(Common.o.getObject("eleHistoricalLossTbl"), sValue[0],
		 * "edt");
		 * 
		 * } catch(Exception e) { e.printStackTrace(); }
		 */
		return status;
	}

	/**
	 * @function used to Verify the expected approval message is displayed in screen
	 * @param sFuncValue
	 * @return boolean
	 * @throws Exception
	 */

	public boolean VerifyApproval(String sFuncValue) throws Exception {
		Boolean status = false;
		Common common = CommonManager.getInstance().getCommon();

		logger.info("Verifying the Approvals");
		String strApproval = null;
		strApproval = common.GetTextFromTable(Common.o.getObject("eleApproveButtonType"), 1, sFuncValue);
		if (strApproval.equals(sFuncValue)) {
			status = true;
			logger.info("Expected Approval message is displayed :'" + strApproval + "'");
		}
		return status;
	}
	
	public boolean SearchClaims(String funvalue) throws Exception {
		Boolean status = false;
		status = common.SafeAction(Common.o.getObject("eleRiskAnalysisClaimsTab"), "YES", "ele");
		
		if(funvalue.trim().toUpperCase().contains("SEARCH POLICY"))
		{
			String Acc_no;
			String Pol_no;
			String Pol_no2;
			String[] Acc_no_temp=ManagerDriver.getInstance().getWebDriver().findElement(Common.o.getObject

("eljRA_AccountNo")).getText().trim().split(" ");
			Acc_no=Acc_no_temp[Acc_no_temp.length-1];
			//span[contains(@id,'AccountNumber-btnInnerEl')]
			//Acc_no=PCThreadCache.getInstance().getProperty("AccountNumber");
			status = common.SafeAction(Common.o.getObject("eleRA_Claims_SrhBy"), "", "ele");
			status = common.SafeAction(Common.o.getObject("eleRA_Claims_SrhPol_Accno"), Acc_no, "edt");
			status = common.SafeAction(Common.o.getObject("eleRASc_ClaimsTab_Search"), "YES", "elj");
			Pol_no=ManagerDriver.getInstance().getWebDriver().findElement(By.xpath("//div[contains(@id,'PolicySearch_Results')]/div/table/tbody/tr[1]/td[3]")).getText().trim();
			status = common.SafeAction(By.xpath("//a[contains(text(),'Select')]"), "", "ele");
			Pol_no2=ManagerDriver.getInstance().getWebDriver().findElement(By.xpath("//input[contains(@id,'RiskAnalysisCV:PolicyNumberPicker-inputEl')]")).getAttribute("value").trim();
			if(Pol_no.equals(Pol_no2))
			{
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Compare Pol# in Search By policy field with value selected in Search Policies Screen value", "Value Matched", 

"PASS");
				status=true;
			}
			else
			{
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Compare Pol# in Search By policy field with value selected in Search Policies Screen value", "Value Mismatched", 

"FAIL");
				status=false;
			}
		}
		else
		{
			status = common.SafeAction(Common.o.getObject("eleRASc_ClaimsTab_Search"), "YES", "ele");
		}
		
		return status;
	}
	

}