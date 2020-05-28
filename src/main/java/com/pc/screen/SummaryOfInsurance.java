/**
 * @ClassPurpose Access all the data's for summary of insurance page
 * @Scriptor Sojan David
 * @ReviewedBy
 * @ModifiedBy Sojan David
 * @LastDateModified 3/21/2017
 */
package com.pc.screen;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.pc.constants.PCConstants;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.PCThreadCache;
import com.pc.utilities.XlsxReader;

public class SummaryOfInsurance {

	public static String sheetname = "SummaryOfInsurance";
	static Logger logger = Logger.getLogger(sheetname);
	public static String sFormNumber;
	Common common = CommonManager.getInstance().getCommon();

	public Boolean SCRSummaryOfInsurance() throws Exception {

		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

	public boolean VerifyResults(String sFunValue) throws Exception, Exception {
		boolean status = false;
		String[] sValue = sFunValue.split(":::");
		String Value = null;
		HashMap<String, Object> updateColumnNameValues = new HashMap<String, Object>();
		HashMap<String, Object> whereConstraint = new HashMap<String, Object>();
		String tcID = PCThreadCache.getInstance().getProperty("TCID");
		switch (sValue[0].toUpperCase()) {
		case "VALIDATEWARNING":
			logger.info(sValue[0]);
			Value = common.ReadElement(Common.o.getObject("txtAddNewPolicyWarning"), Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			break;
		case "FORMNUMBER":
			logger.info(sValue[0]);
			common.WaitForElementExist(Common.o.getObject("txtFormNumber"), Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
			sFormNumber = common.ReadElement(Common.o.getObject("txtFormNumber"), Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
			if (sFormNumber.contains(sValue[1])) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Summary of Insurance Series should start from SOI", "Summary of Insurance '" + sValue[1] + "' Starting at SOI", 

"PASS");
			} else {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Summary of Insurance Series should start from SOI", "Summary of Insurance '" + sValue[1] + "' should at SOI", 

"FAIL");
			}

			updateColumnNameValues.clear();
			whereConstraint.clear();
			updateColumnNameValues.put(PCConstants.FormNumber, sFormNumber);
			whereConstraint.put(PCConstants.ID, tcID);
			PCThreadCache.getInstance().setProperty("FormNumber", sFormNumber);
			//status = XlsxReader.getInstance().executeUpdateQuery(sheetname, updateColumnNameValues, whereConstraint);

			// status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"),
			// "Update "+sheetname+" Set FormNumber = '"+sFormNumber+"' where
			// ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");

			status = true;
			break;
		case "VALIDATEFORMTABLE":
			logger.info(sValue[0]);
			Value = common.GetTextFromTable(Common.o.getObject("eleCFTSAddNewCustomerFormTable"), 0, sFormNumber);
			status = common.CompareStringResult("Certification of Insurance successfully created", sFormNumber, Value);
			break;
		case "VALIDATESAVEDRAFT":
			logger.info(sValue[0]);
			Value = common.ReadElementGetAttribute(Common.o.getObject("lstCFTSFormName"), "value", Integer.valueOf

(HTML.properties.getProperty("VERYLONGWAIT")));
			status = common.CompareStringResult("SaveDraftFormName", sValue[1], Value);
			Value = common.ReadElementGetAttribute(Common.o.getObject("lstCFTSSourceOfRequest"), "value", Integer.valueOf

(HTML.properties.getProperty("VERYLONGWAIT")));
			status = common.CompareStringResult("SaveDraftSourceofRequest", sValue[2], Value);
			Value = common.ReadElementGetAttribute(Common.o.getObject("lstCFTSAsOfDate"), "value", Integer.valueOf

(HTML.properties.getProperty("VERYLONGWAIT")));
			status = common.CompareStringResult("AsOfDate", SCRCommon.ReturnCurrentDate(), Value);
			Value = common.ReadElementGetAttribute(Common.o.getObject("lstCFTSHowSent"), "value", Integer.valueOf

(HTML.properties.getProperty("VERYLONGWAIT")));
			status = common.CompareStringResult("SaveDraftHowSent", sValue[3], Value);
			Value = common.ReadElementGetAttribute(Common.o.getObject("edtCFTSEMail"), "value", Integer.valueOf

(HTML.properties.getProperty("VERYLONGWAIT")));
			status = common.CompareStringResult("SaveDraftEmail", sValue[4], Value);
			break;
		case "VALIDATEEDITEDACCOUNTRECEPIENT":
			logger.info(sValue[0]);
			Value = common.ReadElement(Common.o.getObject("txtCFTSName"), Integer.valueOf(HTML.properties.getProperty

("VERYLONGWAIT")));
			status = common.CompareStringResult("Recipients Name", sValue[1], Value);
			Value = common.ReadElement(Common.o.getObject("txtCFTSPrimaryEmail"), Integer.valueOf(HTML.properties.getProperty

("VERYLONGWAIT")));
			status = common.CompareStringResult("Recipients Email", sValue[2], Value);
			Value = common.ReadElement(Common.o.getObject("txtCFTSAddress"), Integer.valueOf(HTML.properties.getProperty

("VERYLONGWAIT")));
			status = common.CompareStringResult("Recipients Address", sValue[3], Value);
			Value = common.ReadElement(Common.o.getObject("txtCFTSAnnual"), Integer.valueOf(HTML.properties.getProperty

("VERYLONGWAIT")));
			status = common.CompareStringResult("Recipients CertType", sValue[4], Value);
			break;
		case "ZERORESULTWARNING":
			logger.info(sValue[0]);
			Value = common.ReadElement(Common.o.getObject("txtZeroResults"), Integer.valueOf(HTML.properties.getProperty

("VERYLONGWAIT")));
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			break;
		case "RESETBUTTONVALIDATE":
			logger.info(sValue[0]);
			status = common.ElementExistOrNotFalse(sValue[0], "The Certificate of Insureance should be cleared", "The Certificate of Insureance cleared", Common.o.getObject("txtZeroResults"));
			break;
		case "SELECTEXISTINGRECEIPIENT":
			status = common.ActionOnTable(Common.o.getObject("eleCFTSAccountReceipientsTable"), 2, 0, sValue[1], "", "div");
			break;
		case "REQUESTBUTTONAVAILABLE":
			logger.info(sValue[0]);
			status = common.ElementExistOrNotFalse(sValue[0], "The Request button should be disappeared", "The Request button is disappeared", Common.o.getObject("eleCFTSRequestForm"));
			status = common.ElementExistOrNotTrue(sValue[0], "Cancel Button should appeared", "Cancel Button appeared", Common.o.getObject("eleCFTSFinalizeCancel"));
			status = common.ElementExistOrNotTrue(sValue[0], "View Button should appeared", "View Button appeared", Common.o.getObject("eleCFTSView"));
			break;
		case "VALIDATECANCEL":
			logger.info(sValue[0]);
			status = common.ElementExistOrNotTrue(sValue[0], "Cancel Should Work", "Cancel is working fine", Common.o.getObject("eleCFTSAddNewCustomerForm"));
			break;
		case "VALIDATEFORMTABLEDATA":
			logger.info(sValue[0]);
			Value = common.GetTextFromTable(Common.o.getObject("eleCFTSAddNewCustomerFormTable"), 1, sValue[1]);
			Value = common.GetTextFromTable(Common.o.getObject("eleCFTSAddNewCustomerFormTable"), 2, sValue[2]);
			status = true;
			break;
		case "VALIDATEWITHDRAW":
			logger.info(sValue[0]);
			status = common.ElementExistOrNotFalse(sValue[0], "The Forms should be removed", "The forms removed", Common.o.getObject("eleCFTSAddNewCustomerFormTable"));
			break;
		case "SELECTFORMNUMBER":
			logger.info(sValue[0]);
			status = common.ActionOnTable(Common.o.getObject("eleCFTSAddNewCustomerFormTable"), 0, 0, sValue[1], sValue[1], "a");
			break;
		case "VALIDATEFORMTABLEDATAFINAL":
			logger.info(sValue[0]);
			Value = common.GetTextFromTable(Common.o.getObject("eleCFTSAddNewCustomerFormTable"), 1, sValue[1]);
			status = true;
			break;
		case "VALIDATEREADONLYINFO":
			logger.info(sValue[0]);
			status = common.ElementExistOrNotTrue("CheckingFormNumberIsReadOnly", "Form Number should be on read only", "Form Number is read only", Common.o.getObject("eleCFTSFormNumberRO"));
			status = common.ElementExistOrNotTrue("CheckingFormNameIsReadOnly", "Form Name should be on read only", "Form Name is read only", Common.o.getObject("eleCFTSFormNameRO"));
			status = common.ElementExistOrNotTrue("CheckingSourceOfRequestReadOnly", "Source of Request should be on read only", 

"Source of Request is read only", Common.o.getObject("eleCFTSSourceOfRequestRO"));
			status = common.ElementExistOrNotTrue("CheckingFinalStatus", "Staus should be on read only", "Status is read only", 

Common.o.getObject("eleCFTSFinalWorkStatusRO"));
			status = common.ElementExistOrNotFalse("RequestFormButton", "Request Form Button should not available", "Request Form Button is not available", Common.o.getObject("eleCFTSRequestForm"));
			status = common.ElementExistOrNotFalse("SaveDraftButton", "Save Draft Button should not available", "Save Draft Button is not available", Common.o.getObject("eleCFTSSaveDraft"));
			status = common.ElementExistOrNotFalse("EditFormButton", "Edit Form Button should not available", "Edit Form Button is not available", Common.o.getObject("eleCFTSEditForm"));
			status = common.ElementExistOrNotTrue("VEIWBUTTONCHECK", "View Button should present", "Viwe Button is present", Common.o.getObject("eleCFTSView"));
			break;
		}
		if (!status) {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "" + sValue[0] + " case should PASS", "" + sValue[0] + " case should not fail", "FAIL");
			status = true;
		}
		return status;
	}

	public boolean SelectAccountRecepient(String sFunValue) throws Exception {
		boolean status = false;
		status = common.ActionOnTable(Common.o.getObject("eleCFTSAccountReceipientsTable"), 2, 2, sFunValue, sFunValue, "a");
		return status;
	}

	/**
	 * @function use to select the form number from the table
	 * @param sFuncValue
	 * @return true/false
	 */
	public boolean selectFormNumber(String sFuncValue) {
		boolean status = false;
		try {
			status = common.ActionOnTable(Common.o.getObject("eleCFTSAddNewCustomerFormTable"), 0, 0, sFuncValue, "a");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;

	}
}