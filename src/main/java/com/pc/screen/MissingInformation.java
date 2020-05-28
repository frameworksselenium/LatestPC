
/**
 * @ClassPurpose This Class used for the Missing Info Screen
 * @Scriptor Sojan David
 * @ReviewedBy
 * @ModifiedBy Sojan David
 * @LastDateModified 4/25/2017
 */
package com.pc.screen;

import java.io.IOException;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.pc.constants.PCConstants;
import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.PCThreadCache;
import com.pc.utilities.XlsxReader;

public class MissingInformation {

	public static String sheetname = "MissingInformation";
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
	public static String MissingInfoNumber;
	Common common = CommonManager.getInstance().getCommon();

	/**
	 * @function Use to perform all the objects/elements/functions from the
	 *           MissingInformation excel sheet
	 * @return true/false
	 * @throws Exception
	 */
	public Boolean SCRMissingInformation() throws Exception {

		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

	/**
	 * @function use to fill the current date
	 * @return true/false
	 * @throws Exception
	 */
	public boolean originalReceivedDate() throws Exception {
		boolean status = false;
		status = common.SafeAction(Common.o.getObject("lsjOriginalReceivedDate"), SCRCommon.ReturnCurrentDate(), "lst");
		return status;
	}

	/**
	 * @function use to verify the results cases
	 * @param sFuncValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean verifyResults(String sFuncValue) throws Exception {
		boolean status = false;
		HashMap<String, Object> updateColumnNameValues = new HashMap<String, Object>();
		HashMap<String, Object> whereConstraint = new HashMap<String, Object>();
		XlsxReader sXL = XlsxReader.getInstance();
		String[] sValue = sFuncValue.split(":::");
		logger.info("Verifying the Results");
		String Value = null;
		try {
			switch (sValue[0].toUpperCase()) {
			case "CHECKMISSINGINFONUMBER":
				logger.info(sValue[0]);
				MissingInfoNumber = common.ReadElement(Common.o.getObject("txtMissingInformationID"), Integer.valueOf

(HTML.properties.getProperty("VERYLONGWAIT")));
				updateColumnNameValues.clear();
				whereConstraint.clear();
				updateColumnNameValues.put(PCConstants.MissingInformationID, MissingInfoNumber);
				whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
				PCThreadCache.getInstance().setProperty("MissingInformationID", MissingInfoNumber);
				//status = sXL.executeUpdateQuery(sheetname, updateColumnNameValues, whereConstraint);

				// status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"),
				// "Update "+sheetname+" Set MissingInformationID = '"+MissingInfoNumber+"'
				// where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
				// if(MissingInfoNumber.contains("LTQ"))
				/*
				 * if(MissingInfoNumber.contains("MI")) {
				 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
				 * PCThreadCache.getInstance().getProperty("methodName"),
				 * "Missing Info ID should start from MI","Missing Info ID '"
				 * +MissingInfoNumber+"' Starting at MI", "PASS"); status = true; }else {
				 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
				 * PCThreadCache.getInstance().getProperty("methodName"),
				 * "Missing Info ID should start from MI","Missing Info ID '"
				 * +MissingInfoNumber+"' should at MI", "FAIL"); }
				 */
				break;
			case "VERIFYERRORMSG":
				logger.info(sValue[0]);
				Value = common.ReadElement(Common.o.getObject("eleMIErrorMessage"), Integer.valueOf(HTML.properties.getProperty

("VERYLONGWAIT")));
				status = common.CompareStringResult(sValue[0], sValue[1], Value);
				break;
			case "VERIFYMILETTER":
				logger.info(sValue[0]);
				String MIStatus = common.ReadElement(Common.o.getObject("eleMIStatus"), Integer.valueOf

(HTML.properties.getProperty("VERYLONGWAIT")));
				if (MIStatus.equals(sValue[1])) {
					String MIMissingInformationFirstLetterSent = common.ReadElement(Common.o.getObject

("eleMIMissingInformationFirstLetterSent"), Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
					status = common.CompareStringResult(sValue[0], sValue[2], MIMissingInformationFirstLetterSent);
				} else {
					status = false;
				}
				break;
			case "MISSINGINFOSEARCHRESULTBYDEFAULT":
				logger.info(sValue[0]);
				Value = common.GetTextFromTable(Common.o.getObject("eleMIMissingInformationTable"), 0, sValue[1]);
				status = common.CompareStringResult(sValue[0], sValue[1], Value);
				Value = common.GetTextFromTable(Common.o.getObject("eleMIMissingInformationTable"), 0, sValue[2]);
				status = common.CompareStringResult(sValue[0], sValue[2], Value);
				Value = common.GetTextFromTable(Common.o.getObject("eleMIMissingInformationTable"), 0, sValue[3]);
				status = common.CompareStringResult(sValue[0], sValue[3], Value);
				break;
			case "ACCOUNTSUMMARYDETAILS":
				logger.info(sValue[0]);
				Value = common.GetTextFromTable(Common.o.getObject("eleAccountSummaryMissingInfoTable"), 0, sValue[1]);
				status = common.CompareStringResult(sValue[0], sValue[1], Value);
				Value = common.GetTextFromTable(Common.o.getObject("eleAccountSummaryMissingInfoTable"), 1, 

SCRCommon.ReturnCurrentDate());
				status = common.CompareStringResult(sValue[0], SCRCommon.ReturnCurrentDate(), Value);
				Value = common.GetTextFromTable(Common.o.getObject("eleAccountSummaryMissingInfoTable"), 2, sValue[2]);
				status = common.CompareStringResult(sValue[0], sValue[2], Value);
				Value = common.GetTextFromTable(Common.o.getObject("eleAccountSummaryMissingInfoTable"), 3, sValue[3]);
				status = common.CompareStringResult(sValue[0], sValue[3], Value);
				break;
			case "SORPOLICYSUMMARYDETAILS":
				logger.info(sValue[0]);
				Value = common.GetTextFromTable(Common.o.getObject("elePolicySummaryMissingInfoTable"), 0, sValue[1]);
				status = common.CompareStringResult(sValue[0], sValue[1], Value);
				Value = common.GetTextFromTable(Common.o.getObject("elePolicySummaryMissingInfoTable"), 1, 

SCRCommon.ReturnCurrentDate());
				status = common.CompareStringResult(sValue[0], SCRCommon.ReturnCurrentDate(), Value);
				Value = common.GetTextFromTable(Common.o.getObject("elePolicySummaryMissingInfoTable"), 2, sValue[2]);
				status = common.CompareStringResult(sValue[0], sValue[2], Value);
				// Value =
				// common.GetTextFromTable(Common.o.getObject("elePolicySummaryMissingInfoTable"),
				// 3, sValue[3]);
				// status = common.CompareStringResult(sValue[0], sValue[3], Value);
				break;
			case "SHELLPOLICYSUMMARYDETAILS":
				logger.info(sValue[0]);
				Value = common.GetTextFromTable(Common.o.getObject("elePolicyShellSummaryMissingInfoTable"), 0, sValue[1]);
				status = common.CompareStringResult(sValue[0], sValue[1], Value);
				Value = common.GetTextFromTable(Common.o.getObject("elePolicyShellSummaryMissingInfoTable"), 1, 

SCRCommon.ReturnCurrentDate());
				status = common.CompareStringResult(sValue[0], SCRCommon.ReturnCurrentDate(), Value);
				// Value =
				// common.GetTextFromTable(Common.o.getObject("elePolicyShellSummaryMissingInfoTable"),
				// 2, sValue[2]);
				// status = common.CompareStringResult(sValue[0], sValue[2], Value);
				// Value =
				// common.GetTextFromTable(Common.o.getObject("elePolicyShellSummaryMissingInfoTable"),
				// 3, sValue[3]);
				// status = common.CompareStringResult(sValue[0], sValue[3], Value);
				break;
			case "ACTIONNEWMISSINGINFOBUTTON":
				logger.info(sValue[0]);
				common.SafeAction(Common.o.getObject("eleActions"), "ele", "ele");
				status = common.ElementExistOrNotFalse("Check Action Missing Info Button", "The Action New Missing Info Button should not be available", "The Action New Missing Info Button is available", Common.o.getObject("eleNewActionMissingInfo"));
				break;
			case "VERIFYWITHDRAWN":
				logger.info(sValue[0]);
				// common.SafeAction(Common.o.getObject("edtMissingInfoID"), MissingInfoNumber,
				// "edt");
				// common.SafeAction(Common.o.getObject("eleCommonSearch"), "ele", "ele");
				// status =
				// Common.ActionOnTable(Common.o.getObject("eleAccountSummary_PolicyTermsTbl"),
				// 0, 1, MissingInfoNumber, sValue[1], "a");
				Value = common.GetTextFromTable(Common.o.getObject("eleMIMissingInformationTable"), 2, sValue[1]);
				status = common.CompareStringResult(sValue[0], sValue[1], Value);
				break;
			case "SELECTMISSINGINFO":
				logger.info(sValue[0]);
				// status =
				// Common.ActionOnTable(Common.o.getObject("eleMIMissingInformationTable"), 0,
				// 0, MissingInfoNumber, MissingInfoNumber, "a");
				status = common.ActionOnTable(Common.o.getObject("eleMIMissingInformationTable"), 0, 0, MissingInfoNumber, MissingInfoNumber, "a");
				break;
			case "CHECKPHSNOEMAIL":
				logger.info(sValue[0]);
				Value = common.ReadElementGetAttribute(Common.o.getObject(""), "value", Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
				status = common.CompareStringResult(sValue[0], sValue[1], Value);
				break;
			case "CHECKPHSNOFAX":
				logger.info(sValue[0]);
				Value = common.ReadElementGetAttribute(Common.o.getObject(""), "value", Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
				status = common.CompareStringResult(sValue[0], sValue[1], Value);
				break;
			case "ZERORESULTWARNING":
				logger.info(sValue[0]);
				Value = common.ReadElement(Common.o.getObject("txtZeroResults"), Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
				status = common.CompareStringResult(sValue[0], sValue[1], Value);
				break;
			case "VERIFYFIRSTLETTERSENT":
				logger.info(sValue[0]);
				Value = common.ReadElement(Common.o.getObject(sValue[1]), Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
				status = common.CompareStringResult(sValue[0], sValue[2].toUpperCase(), Value.toUpperCase());
				break;
			case "VERIFYEMAILID":
				status = false;
				logger.info(sValue[0]);
				if (sValue[2].trim().equals("NON_BLANK")) {
					Value = common.ReadElementGetAttribute(Common.o.getObject(sValue[1]), "value", Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
					if (Value.trim() != null || Value.trim() != "") {
						HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "" + sValue[0] + " should NOT be blank", "" + sValue[0] + "is NOT blank", "Pass");
						status = true;
					}
				} else if (sValue[2].trim().equals("BLANK")) {
					Value = common.ReadElement(Common.o.getObject(sValue[1]), Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
					if (Value.trim().equals("") || Value.trim().equals(null)) {
						HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "" + sValue[0] + " should be blank", "" + sValue[0] + "is blank", "Pass");
						status = true;
					}

				}
				break;

			}
			if (!status) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "" + sValue[0] + " case should PASS", "" + sValue[0] + " case should not fail", "FAIL");
				status = false;
			}
		} finally {
			updateColumnNameValues = null;
			whereConstraint = null;
		}
		return status;
	}

	public boolean getMissingInfoID() throws Exception {
		boolean status = true;
		MissingInfoNumber = common.ReadElement(Common.o.getObject("txtMissingInformationID"), Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
		return status;
	}

	/**
	 * @function use to select the created activity to create Missing info
	 * @param sFuncValue
	 * @return status
	 * @throws Exception
	 */
	public boolean selectActivity(String sFuncValue) throws Exception {
		boolean status = false;
		String[] sValue = sFuncValue.split(":::");
		switch (sValue[0].toUpperCase()) {
		case "ACCOUNT":
			status = common.ActionOnTable(Common.o.getObject("eleActivityTable"), 2, 2, sValue[1], sValue[1], "a");
			break;
		case "CLA":
			status = common.ActionOnTable(Common.o.getObject("eleShellActivityTable"), 2, 2, sValue[1], "a");
			break;
		case "SOR":
			status = common.ActionOnTable(Common.o.getObject("elePolicyActivityTable"), 2, 2, sValue[1], sValue[1], "a");
			break;
		}
		return status;
	}

	/**
	 * @function use to select the missing info reason
	 * @param sValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean selectMissingReason(String sValue) throws Exception {
		boolean status = false;
		status = common.ActionOnTable(Common.o.getObject("eleMIAddReasonTable"), 1, 1, "<none>", "<none>", "div");
		// status = common.SafeAction(Common.o.getObject("lsjMIReason"), sValue,
		// "lsjMIReason");
		status = common.SafeAction(Common.o.getObject("lstMIReason"), sValue, "lstMIReason");

		return status;
	}

	/**
	 * @function use to select policy number from the account summary screen
	 * @param sFuncValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean selectPolicyNumber(String sFuncValue) throws Exception {
		boolean status = true;
		String[] sValue = sFuncValue.split(":::");
		status = common.ActionOnTable(Common.o.getObject("eleAccountSummary_PolicyTermsTbl"), 0, 0, sValue[0], sValue[0], "a");
		return status;
	}

	/**
	 * @function use to click the missing info id using the values
	 * @param strValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean selectMI(String sFuncValue) throws Exception {
		boolean status = true;
		String[] sValue = sFuncValue.split(":::");
		status = common.ActionOnTable(Common.o.getObject("eleMIMissingInformationTable"), 0, 0, sValue[0], sValue[0], "a");
		return status;
	}

	/**
	 * @function use to select the activity
	 * @param strValue
	 * @return true/false
	 * @throws IOException
	 */
	public boolean selectActivityText(String strValue) throws IOException {
		boolean status = false;
		status = common.SelectActivity(strValue);
		return status;
	}

	/**
	 * @function use to click the missing info id using the values
	 * @param strValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean selectMissingInfoID(String strValue) throws Exception {
		boolean status = false;
		status = common.ActionOnTable(Common.o.getObject("eleMIMissingInformationTable"), 0, 0, strValue, "a");
		return status;
	}

}