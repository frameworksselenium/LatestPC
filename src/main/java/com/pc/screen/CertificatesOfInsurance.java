
/**
 * @ClassPurpose Access all the data's for driver information sheet
 * @Scriptor Sojan David
 * @ReviewedBy
 * @ModifiedBy Sojan David
 * @LastDateModified 3/21/2017
 */
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

public class CertificatesOfInsurance {

	public static String sheetname = "CertificatesOfInsurance";
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
	public static String sFormNumber;
	Common common = CommonManager.getInstance().getCommon();

	public Boolean SCRCertificatesOfInsurance() throws Exception {

		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

	/**
	 * @function Use to verify the results of the usecase
	 * @param sFunValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean VerifyResults(String sFunValue) throws Exception {
		boolean status = false;
		String[] sValue = sFunValue.split(":::");
		HashMap<String, Object> updateColumnNameValues = new HashMap<String, Object>();
		HashMap<String, Object> whereConstraint = new HashMap<String, Object>();
		try {
			XlsxReader sXL = XlsxReader.getInstance();
			String Value = null;
			switch (sValue[0].toUpperCase()) {
			case "VALIDATEWARNING":
				Value = common.ReadElement(Common.o.getObject("txtAddNewPolicyWarning"), Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
				status = common.CompareStringResult(sValue[0], sValue[1], Value);
				break;
			case "FORMNUMBER":
				common.WaitForElementExist(Common.o.getObject("txtFormNumber"), Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
				sFormNumber = common.ReadElement(Common.o.getObject("txtFormNumber"), Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
				if (sFormNumber.contains(sValue[1])) {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Certificate Series should start from CF", "Certificate Series '" + sValue[1] + "' Starting at CF", "PASS");
				}
				updateColumnNameValues.clear();
				whereConstraint.clear();
				updateColumnNameValues.put(PCConstants.FormNumber, sFormNumber);
				whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
				PCThreadCache.getInstance().setProperty("FormNumber", sFormNumber);
				//status = sXL.executeUpdateQuery(sheetname, updateColumnNameValues, whereConstraint);

				// status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"),
				// "Update "+sheetname+" Set FormNumber = '"+sFormNumber+"' where
				// ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
				// Common.WaitForElementExist(Common.o.getObject("lstCFTSHowSent"),
				// Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
				status = true;
				break;
			case "VALIDATEFORMTABLE":
				Value = common.GetTextFromTable(Common.o.getObject("eleCFTSAddNewCustomerFormTable"), 0, sFormNumber);
				status = common.CompareStringResult("Certification of Insurance successfully created", sFormNumber, Value);
				break;
			case "VALIDATESAVEDRAFT":
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
				Value = common.ReadElement(Common.o.getObject("txtZeroResults"), Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
				status = common.CompareStringResult(sValue[0], sValue[1], Value);
				break;
			case "RESETBUTTONVALIDATE":
				status = common.ElementExistOrNotFalse(sValue[0], "The Certificate of Insureance should be cleared", "The Certificate of Insureance cleared", Common.o.getObject("txtZeroResults"));
				break;
			case "SELECTEXISTINGRECEIPIENT":
				status = common.ActionOnTable(Common.o.getObject("eleCFTSAccountReceipientsTable"), 2, 0, sValue[1], "", "div");
				break;
			case "REQUESTBUTTONAVAILABLE":
				status = common.ElementExistOrNotFalse(sValue[0], "The Request button should be disappeared", "The Request button is disappeared", Common.o.getObject("eleCFTSRequestForm"));
				status = common.ElementExistOrNotTrue(sValue[0], "Cancel Button should appeared", "Cancel Button appeared", Common.o.getObject("eleCFTSFinalizeCancel"));
				status = common.ElementExistOrNotTrue(sValue[0], "View Button should appeared", "View Button appeared", Common.o.getObject("eleCFTSView"));
				break;
			case "VALIDATECANCEL":
				status = common.ElementExistOrNotTrue(sValue[0], "Cancel Should Work", "Cancel is working fine", Common.o.getObject("eleCFTSAddNewCustomerForm"));
				break;
			case "VALIDATEFORMTABLEDATA":
				Value = common.GetTextFromTable(Common.o.getObject("eleCFTSAddNewCustomerFormTable"), 1, sValue[1]);
				Value = common.GetTextFromTable(Common.o.getObject("eleCFTSAddNewCustomerFormTable"), 2, sValue[2]);
				status = true;
				break;
			case "VALIDATEWITHDRAW":
				status = common.ElementExistOrNotFalse(sValue[0], "Draft Entry should be removed", "Draft Entry Removed", Common.o.getObject("eleCFTSFormNumber1"));
				status = common.ElementExistOrNotTrue(sValue[0], "WithDraw Should Work", "WithDraw is working fine", 

Common.o.getObject("eleCFTSAddNewCustomerForm"));
				break;
			case "SELECTFORMNUMBER":
				status = common.ActionOnTable(Common.o.getObject("eleCFTSAddNewCustomerFormTable"), 0, 0, sValue[1], sValue[1], "a");
				break;
			case "VALIDATEFORMTABLEDATAFINAL":
				Value = common.GetTextFromTable(Common.o.getObject("eleCFTSAddNewCustomerFormTable"), 1, sValue[1]);
				status = true;
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
	 * @function Use to select the account recipient from the table
	 * @param sFunValue
	 * @return
	 */
	public boolean SelectAccountRecepient(String sFunValue) {
		boolean status = false;
		try {
			status = common.ActionOnTable(Common.o.getObject("eleCFTSAccountReceipientsTable"), 2, 2, sFunValue, sFunValue, "a");
		} catch (Exception e) {
			e.printStackTrace();
		}
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