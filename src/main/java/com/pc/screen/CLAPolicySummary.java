
package com.pc.screen;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.PCThreadCache;

public class CLAPolicySummary {

	public static String sheetname = "CLAPolicySummary";
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();

	public Boolean SCRCLAPolicySummary() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

	public boolean VerifyResults(String funValue) throws Exception {
		boolean status = false;
		logger.info("Verify the Results");
		String[] sValue = funValue.split(":::");
		String Value = null;
		String ExpDesc = null;
		switch (sValue[0].toUpperCase()) {
		case "BORDATE":
			logger.info(sValue[0]);
			// to verify the BOR DATE has been updated
			// Value = Common.ReadElement(Common.o.getObject("elePolSumBORStartDate"),
			// Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			// status = Common.CompareStringResult("PolicyBORStartDate",
			// SCRCommon.ReturnCurrentDate(), Value);
			// Value = Common.ReadElement(Common.o.getObject("elePolSumBOREndDate"),
			// Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			// status = Common.CompareStringResult("PolicyBOREndDate",
			// SCRCommon.ReturnSixtyDaysFromDate(), Value);
			status = false;
			break;
		case "RECEIVEDDATE":
			logger.info(sValue[0]);
			Value = common.ReadElement(Common.o.getObject("eleBORReceivedDate"), Integer.valueOf(HTML.properties.getProperty

("NORMALWAIT")));
			status = common.CompareStringResult("PolicyReceivedDate", SCRCommon.ReturnCurrentDate(), Value);
			break;
		case "VERIFYBORENDDATECurrent":
			logger.info(sValue[0]);
			Value = common.ReadElementGetAttribute(Common.o.getObject("eleBOREndDate"), "value", Integer.valueOf

(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("PolicyBOREndDate", SCRCommon.ReturnCurrentDate(), Value);
			break;
		case "VERIFYPOLICYFILE":
			logger.info(sValue[0]);
			Value = common.ReadElement(Common.o.getObject("eleShell_AccountNumber"), Integer.valueOf(HTML.properties.getProperty

("NORMALWAIT")));
			status = common.CompareStringResult("VerifyAccountNumber", sValue[1], Value);
			Value = common.ReadElement(Common.o.getObject("eleCLAAccountName"), Integer.valueOf(HTML.properties.getProperty

("NORMALWAIT")));
			status = common.CompareStringResult("VerifyAccountName", sValue[2], Value);
			break;
		case "VERIFYPOLICYACTIVITY":
			logger.info(sValue[0]);
			status = common.ActionOnTable(Common.o.getObject("elepolicySummary_Activitytbl"), 2, 2, sValue[1], "a");
			status = common.ElementExistOrNotTrue("Policy Acitivy Screen", "Policy Activity Details should display", "Policy Activity Details displayed", Common.o.getObject("lstActivityStatus"));
			break;
		case "VERIFYRECENTNOTES":
			logger.info("Verification started for  '" + sValue[0] + "'");
			status = common.ElementExistOrNotTrue("Element exist", "Recent notes Title should be displayed", "Recent Notes title displayed", Common.o.getObject("eleRecentNotesHeader"));
			Value = common.ReadElement(Common.o.getObject("eleRecentNotesTopic"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("Recent Notes Section - topic", sValue[2], Value);
			Value = common.ReadElement(Common.o.getObject("eleRecentNotesDesc"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			if (sValue[1].equalsIgnoreCase("TransactionID")) {
				NavigatetoGuidedWrkFlw_MT("eleMaintainanceTransactions:::eleGuidedWrkFlw_MT");
				String TrsansactionId = common.GetTextFromTable(Common.o.getObject("eleGuidedWrkFlwTable"), 1, 2, SCRCommon.ReturnCurrentDate());
				ExpDesc = sValue[3].replace("<Trans_ID>", TrsansactionId);
				String TrsansactionId1 = common.GetTextFromTable(Common.o.getObject("eleGuidedWrkFlwTable"), 1, 2, SCRCommon.ReturnCurrentDate());
			}
			status = common.SafeAction(Common.o.getObject("eleSummary"), "ele", "ele");
			status = common.CompareStringResult("Recent Notes Section - Description", ExpDesc, Value);
			break;
		case "VERIFYPOLICYACTIVITY_SST":
			logger.info(sValue[0]);
			String strTransactionId = common.GetTextFromTable(Common.o.getObject("elePolicySummary_PendingTransactn_Tble"), 3, 4, "SST");
			// status =
			// common.ActionOnTable(Common.o.getObject("elePolicySummary_PendingTransactn_Tble"),
			// 2, 2, sValue[1], "a");
			status = common.ActionOnTable(Common.o.getObject("elepolicySummary_Activitytbl"), 0, 2, SCRCommon.ReturnCurrentDate(), "a");
			if (sValue[1].equalsIgnoreCase("TARGETDATE_CURRDATE")) {
				sValue[1] = SCRCommon.ReturnCurrentDate();
			}
			Value = common.ReadElement(Common.o.getObject("eleActivity_TargetDate"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("Activity - Target Date", sValue[1], Value);
			Value = common.ReadElement(Common.o.getObject("eleActivity_AssignTo"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("Activity - Assign to", sValue[3], Value);
			ExpDesc = sValue[2].replace("<Trans_ID>", strTransactionId);
			Value = common.ReadElement(Common.o.getObject("eleActivity_ActivityDesc"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("Activity - Description", ExpDesc, Value);
			break;
		case "VERIFYPOLICYADDEDACTIVITY":
			logger.info(sValue[0]);
			status = common.ActionOnTable(Common.o.getObject("eleActivityTable"), 2, 2, sValue[1], "a");
			status = common.ElementExistOrNotTrue("Policy Acitivy Screen", "Activity '" + sValue[1] + "' should should get added", "Policy Activity got added", Common.o.getObject("lstActivityStatus"));
			break;
		case "VERIFYRECENTNOTES_DEFAULT": // changed to manage sheel policy
			logger.info("Verification started for  '" + sValue[0] + "'");
			status = common.ElementExistOrNotTrue("Element exist", "Recent notes Title should be displayed", "Recent Notes title displayed", Common.o.getObject("eleRecentNotesHeader"));
			Value = common.ReadElement(Common.o.getObject("eleRecentNotesTopic"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("Recent Notes Section - topic", sValue[1], Value);
			Value = common.ReadElement(Common.o.getObject("eleRecentNotesDesc"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("Recent Notes Section - Description", sValue[2], Value);
			break;
		case "VERIFY_BILLING_METHOD":
			logger.info(sValue[0]);
			Value = common.ReadElement(Common.o.getObject("ele" + sValue[1]), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("Billing Method - Completed Transaction Table", sValue[2], Value);
			break;
		case "VERIFY_XACTPAY_LAUNCH_MSG":
			logger.info("Verification started for  '" + sValue[0] + "'");
			status = common.ElementExistOrNotTrue("Element exist", "XAct Pay Launch screen", "XAct Pay Launch screen dispalyed as expected", Common.o.getObject("eleXactPayLaunchTitle"));
			Value = common.ReadElement(Common.o.getObject("elePCVerifyErrorMessages"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("XAct Pay Launch Scree- Error message", sValue[1], Value);
			break;
		case "VERIFY_ERR_MSG": // changed to manage sheel policy
			logger.info("Verification started for  '" + sValue[0] + "'");
			// status=common.ElementExistOrNotTrue("Element exist", "XAct Pay Launch
			// screen", "XAct Pay Launch screen dispalyed as expected",
			// Common.o.getObject("eleXactPayLaunchTitle"));
			Value = common.ReadElement(Common.o.getObject("elePCVerifyErrorMessages"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult(sValue[1] + "- Error message", sValue[2], Value);
			break;
		case "VERIFYCOMPLETEDTRANSTABLE":
			try {
				logger.info("vallidation started for '" + sValue[0]);
				Value = common.GetTextFromTable(Common.o.getObject(sValue[1]), 6, 6, sValue[2]);
				status = common.CompareStringResult("verify display of biling Number", sValue[2], Value);
				if (sValue[3].toUpperCase().equalsIgnoreCase("SPAN") || sValue[3].toUpperCase().equalsIgnoreCase("DIV")) {
					status = common.ActionOnTable(Common.o.getObject(sValue[1]), 6, 6, sValue[2], sValue[3].toLowerCase());
					if (status) {
						HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), 

PCThreadCache.getInstance().getProperty("methodName"), "Billing method '" + sValue[1] + "' should display without hyperlink", "'" + sValue[1] + "' displayed without hyperlink", "PASS");
					} else {
						HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), 

PCThreadCache.getInstance().getProperty("methodName"), "Billing method '" + sValue[1] + "' should display without hyperlink", "'" + sValue[1] + "' NOT displayed without hyperlink", "FAIL");
					}
				} else if (sValue[3].toUpperCase().equalsIgnoreCase("LINK")) {
					status = common.ActionOnTable(Common.o.getObject(sValue[1]), 6, 6, sValue[2], "a");
					if (status) {
						HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), 

PCThreadCache.getInstance().getProperty("methodName"), "Billing method '" + sValue[1] + "' should display with hyperlink", "'" + sValue[1] + "'displayed with hyperlink", "PASS");
					} else {
						HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), 

PCThreadCache.getInstance().getProperty("methodName"), "Billing method '" + sValue[1] + "' should display with hyperlink", "'" + sValue[1] + "' NOT displayed with hyperlink", "FAIL");
					}
				} else {
					status = false;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "VERIFY_ALTERNATE_PAYER":
			logger.info("vallidation started for '" + sValue[0]);
			Value = common.ReadElement(Common.o.getObject("eleAltPayNamelbl"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("verify display of Alternate Payer name Lable", "Alternate Payer Name", Value);
			Value = common.ReadElement(Common.o.getObject("eleAltPayName"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("verify display of Alternate Payer Name", sValue[1], Value);

			Value = common.ReadElement(Common.o.getObject("eleAltPayAddresslbl"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("verify display of Alternate Payer Address Label", "Address", Value);
			Value = common.ReadElement(Common.o.getObject("eleAltPayAddress"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("verify display of Alternate Payer address", sValue[2], Value);
			break;

		}
		if (!status) {
			logger.info("" + sValue[0] + " Fails");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "" + sValue[0] + " case should PASS", "" + sValue[0] + " case should not fail", "FAIL");
			status = true;
		}
		return status;
	}

	/**
	 * @function this is used to navigate to the maintainence transaction
	 *           screen->guided workflow
	 * @param strActions
	 * @return boolean
	 * @throws IOException
	 */

	public boolean NavigatetoGuidedWrkFlw_MT(String strActions) {
		boolean status = false;
		String[] arrAction = strActions.split(":::");
		try {
			status = common.SafeAction(Common.o.getObject(arrAction[0]), "", "ele");

			status = common.SafeAction(Common.o.getObject(arrAction[1]), "", "ele");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return status;
	}

}