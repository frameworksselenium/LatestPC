//@Component - This Component used to verify the functionalities of "Evidence of Property" Screen
package com.pc.screen;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.pc.constants.PCConstants;
import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.PCThreadCache;
import com.pc.utilities.XlsxReader;

public class EOP {

	public static String sheetname = "EOP";
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
	public static String sEOPNumber;
	Common common = CommonManager.getInstance().getCommon();

	public Boolean SCREOP() throws Exception {

		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

	/**
	 * @function - This function used to verify the Search Result
	 * @param -
	 *            value
	 * @return - true/false
	 * @throws -
	 *             No Exception
	 */
	public boolean VerifyResults(String sFunValue) throws Exception, Exception {
		boolean status = false;
		HashMap<String, Object> updateColumnNameValues = new HashMap<String, Object>();
		HashMap<String, Object> whereConstraint = new HashMap<String, Object>();
		XlsxReader sXL = XlsxReader.getInstance();
		String[] sValue = sFunValue.split(":::");
		String Value = null;
		try {
			logger.info("Verfying the Results");
			switch (sValue[0].toUpperCase()) {
			case "VALIDATEWARNING":
				logger.info(sValue[0]);
				Value = common.ReadElement(Common.o.getObject("txtAddNewPolicyWarning"), Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
				status = common.CompareStringResult(sValue[0], sValue[1], Value);
				break;
			case "FORMNUMBER":
				logger.info(sValue[0]);
				common.WaitForElementExist(Common.o.getObject("txtFormNumber"), Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
				sEOPNumber = common.ReadElement(Common.o.getObject("txtFormNumber"), Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
				if (sEOPNumber.contains(sValue[1])) {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "EOP Series should start from EOP", "EOP Series '" + sValue[1] + "' Starting at EOP", "PASS");
				}
				updateColumnNameValues.clear();
				whereConstraint.clear();
				updateColumnNameValues.put(PCConstants.EOPNumber, sEOPNumber);
				whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
				PCThreadCache.getInstance().setProperty("EOPNumber", sEOPNumber);
				//status = sXL.executeUpdateQuery(sheetname, updateColumnNameValues, whereConstraint);

				// status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"),
				// "Update "+sheetname+" Set EOPNumber = '"+sEOPNumber+"' where
				// ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
				// Common.WaitForElementExist(Common.o.getObject("lstCFTSHowSent"),
				// Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
				status = true;
				break;
			case "VALIDATEFORMTABLE":
				logger.info(sValue[0]);
				Value = common.GetTextFromTable(Common.o.getObject("eleCFTSAddNewCustomerFormTable"), 0, sEOPNumber);
				status = common.CompareStringResult("Certification of Insurance successfully created", sEOPNumber, Value);
				break;
			case "VALIDATESAVEDRAFT":
				logger.info(sValue[0]);
				Value = common.ReadElementGetAttribute(Common.o.getObject("lstCFTSFormName"), "value", Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
				status = common.CompareStringResult("SaveDraftFormName", sValue[1], Value);
				Value = common.ReadElementGetAttribute(Common.o.getObject("lstCFTSSourceOfRequest"), "value", Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
				status = common.CompareStringResult("SaveDraftSourceofRequest", sValue[2], Value);
				Value = common.ReadElementGetAttribute(Common.o.getObject("lstCFTSAsOfDate"), "value", Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
				status = common.CompareStringResult("AsOfDate", SCRCommon.ReturnCurrentDate(), Value);
				Value = common.ReadElementGetAttribute(Common.o.getObject("lstCFTSHowSent"), "value", Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
				status = common.CompareStringResult("SaveDraftHowSent", sValue[3], Value);
				Value = common.ReadElementGetAttribute(Common.o.getObject("edtCFTSEMail"), "value", Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
				status = common.CompareStringResult("SaveDraftEmail", sValue[4], Value);
				break;
			case "VALIDATEEDITEDACCOUNTRECEPIENT":
				logger.info(sValue[0]);
				Value = common.ReadElement(Common.o.getObject("txtCFTSName"), Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
				status = common.CompareStringResult("Recipients Name", sValue[1], Value);
				Value = common.ReadElement(Common.o.getObject("txtCFTSPrimaryEmail"), Integer.valueOf

(HTML.properties.getProperty("VERYLONGWAIT")));
				status = common.CompareStringResult("Recipients Email", sValue[2], Value);
				Value = common.ReadElement(Common.o.getObject("txtCFTSAddress"), Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
				status = common.CompareStringResult("Recipients Address", sValue[3], Value);
				Value = common.ReadElement(Common.o.getObject("txtCFTSAnnual"), Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
				status = common.CompareStringResult("Recipients CertType", sValue[4], Value);
				break;
			case "ZERORESULTWARNING":
				logger.info(sValue[0]);
				Value = common.ReadElement(Common.o.getObject("txtZeroResults"), Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
				status = common.CompareStringResult(sValue[0], sValue[1], Value);
				break;
			case "RESETBUTTONVALIDATE":
				logger.info(sValue[0]);
				status = common.ElementExistOrNotFalse(sValue[0], "The Certificate of Insureance should be cleared", "The Certificate of Insureance cleared", Common.o.getObject("txtZeroResults"));
				break;
			case "SELECTEXISTINGRECEIPIENT":
				logger.info(sValue[0]);
				status = common.ActionOnTable(Common.o.getObject("eleCFTSAccountReceipientsTable"), 2, 0, sValue[1], "", "div");
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
				status = common.ElementExistOrNotFalse(sValue[0], "Draft Entry should be removed", "Draft Entry Removed", Common.o.getObject("eleCFTSFormNumber1"));
				status = common.ElementExistOrNotTrue(sValue[0], "WithDraw Should Work", "WithDraw is working fine", Common.o.getObject("eleCFTSAddNewCustomerForm"));
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
			case "MULTIPLERECEPIENTCHECK":
				logger.info(sValue[0]);
				Value = common.GetTextFromTable(Common.o.getObject("eleAccountRecepientsTable"), 1, sValue[1]);
				Value = common.GetTextFromTable(Common.o.getObject("eleAccountRecepientsTable"), 1, sValue[2]);
				status = true;
				break;
			case "VALIDATEBUTTONS":
				logger.info(sValue[0]);
				status = common.ElementExistOrNotFalse("Request Form Button", "Request Form Button should not available", "Request Form Button is not available", Common.o.getObject("eleCFTSRequestForm"));
				status = common.ElementExistOrNotFalse("Add New Policy Button", "Add New Policy Button should not available", "Add NewPolicy Button is not available", Common.o.getObject("eleCFTSAddNewPolicy"));
				status = common.ElementExistOrNotFalse("New Location Button", "New Location Button should not available", "New Location Button is not available", Common.o.getObject("eleCFTSAddNewLocation"));
				status = common.ElementExistOrNotFalse("Account Recepient Button", "Account Recepient Button should not available", "Account Recepient Button is not available", Common.o.getObject("eleCFTSAddAccountRecipients"));
				status = common.ElementExistOrNotTrue("VIEWBUTTON", "View Button one should available", "View Button is available", Common.o.getObject("eleCFTSView"));
				// status = Common.ElementExistOrNotTrue("VIEWBUTTON1", "View Button two should
				// available", "View Button is available", Common.o.getObject("eleCFTSView1"));
				Value = common.ReadElement(Common.o.getObject("txtFormNumber"), Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
				status = common.CompareStringResult("FORMNUMBER", sValue[2], Value);
				Value = common.ReadElement(Common.o.getObject("lstCFTSSourceOfRequest"), Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
				status = common.CompareStringResult("SOURCEOFREQUEST", sValue[1], Value);
				Value = common.ReadElement(Common.o.getObject("lstCFTSAsOfDate"), Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
				status = common.CompareStringResult("ASOFDATE", SCRCommon.ReturnCurrentDate(), Value);
				break;
			case "VALIDATEREADONLYINFO":
				logger.info(sValue[0]);
				status = common.ElementExistOrNotTrue("CheckingFormNumberIsReadOnly", "Form Number should be on read only", "Form Number is read only", Common.o.getObject("eleCFTSFormNumberRO"));
				status = common.ElementExistOrNotTrue("CheckingFormNameIsReadOnly", "Form Name should be on read only", "Form Name is read only", Common.o.getObject("eleCFTSFormNameRO"));
				status = common.ElementExistOrNotTrue("CheckingSourceOfRequestReadOnly", "Source of Request should be on read only", "Source of Request is read only", Common.o.getObject("eleCFTSSourceOfRequestRO"));
				status = common.ElementExistOrNotTrue("CheckingFinalStatus", "Staus should be on read only", "Status is read only", Common.o.getObject("eleCFTSFinalWorkStatusRO"));
				status = common.ElementExistOrNotFalse("RequestFormButton", "Request Form Button should not available", "Request Form Button is not available", Common.o.getObject("eleCFTSRequestForm"));
				status = common.ElementExistOrNotFalse("SaveDraftButton", "Save Draft Button should not available", "Save Draft Button is not available", Common.o.getObject("eleCFTSSaveDraft"));
				status = common.ElementExistOrNotFalse("EditFormButton", "Edit Form Button should not available", "Edit Form Button is not available", Common.o.getObject("eleCFTSEditForm"));
				break;
			case "EDITFORMVALIDATION":
				logger.info(sValue[0]);
				status = common.ElementExistOrNotTrue("", "Form Number should be on read only", "Form Number is read only", Common.o.getObject("eleCFTSAccountRecepientDataEntry"));
				break;
			}
			if (!status) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "" + sValue[0] + " case should PASS", "" + sValue[0] + " case should not fail", "FAIL");
				status = true;
			}
		} finally {
			updateColumnNameValues = null;
			whereConstraint = null;
		}
		return status;
	}

	/**
	 * @function - This function used to Select Account Receipient from the screen	 * @param -
	 *            value
	 * @return - true/false
	 * @throws -
	 *             Exception
	 */
	public boolean SelectAccountRecepient(String sFunValue) throws Exception {
		boolean status = false;
		status = common.ActionOnTable(Common.o.getObject("eleCFTSAccountReceipientsTable"), 2, 2, sFunValue, sFunValue, "a");
		return status;
	}

}