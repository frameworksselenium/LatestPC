/**
 * @ClassPurpose Access all the data's from RenewalPolicy sheet
 * @Scriptor Rathish Kuppusamy
 * @ReviewedBy
 * @ModifiedBy Rathish Kuppusamy
 * @LastDateModified 3/21/2017
 */
package com.pc.screen;

import org.apache.log4j.Logger;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.PCThreadCache;

public class RenewalPolicy {

	public static String sheetname = "RenewalPolicy";
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();

	public Boolean SCRRenewalPolicy() throws Exception {

		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

	public boolean selectTotalCost(String sFunctionValue) throws Throwable {
		boolean status = false;
		String[] sValue = sFunctionValue.split(":::");
		int sRowCount = SCRCommon.TableRowCount(Common.o.getObject("eleCLAQTTable"));
		switch (sValue[0].toUpperCase()) {

		case "TOTALCOST":
			try {

				status = SCRCommon.DataWebTable(Common.o.getObject("eleCLAQTTable"), sRowCount, 6, "", "double", "div");
				// status=SCRCommon.FillCellValue(Common.o.getObject("eleQuoteTransactionstbl"),
				// sRowCount, 6,"", "single", sValue[1]);
				// driver.findElement(Common.o.getObject("lstTotalAnnualCost")).sendKeys(sValue[1]);

				status = common.SafeAction(Common.o.getObject("lstTotalAnnualCost"), sValue[1], "lstTotalAnnualCost");
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}

		return status;
	}

	/**
	 * @function use to verify the results of Renewal Policy use case
	 * @param funValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean VerifyResults(String funValue) throws Exception {
		boolean status = false;
		String[] sValue = funValue.split(":::");
		String Value = null;
		switch (sValue[0].toUpperCase()) {
		case "VERIFYMESSAGE":
			Value = common.GetTextFromTable(Common.o.getObject("eleRenewalHistoryTable"), 0, 3, sValue[1]);
			status = common.CompareStringResult(sValue[0], sValue[2], Value);
			break;
		case "VERIFYTOPIC":
			Value = common.ReadElement(Common.o.getObject("elePreRenewalTopic"), Integer.valueOf(HTML.properties.getProperty

("NORMALWAIT")));
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			break;
		case "VERIFYPOLICYSTATUS":
			Value = common.ReadElement(Common.o.getObject("eleCLAPolicyStatus"), Integer.valueOf(HTML.properties.getProperty

("NORMALWAIT")));
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			break;
		case "VERIFYTRANSACTIONSTATUS":
			Value = common.ReadElement(Common.o.getObject("eleTransactionStatus"), Integer.valueOf(HTML.properties.getProperty

("NORMALWAIT")));
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			break;
		case "VERIFYCONFIRMATION":
			Value = common.ReadElement(Common.o.getObject("txtPopUpWindowMsg"), Integer.valueOf(HTML.properties.getProperty

("NORMALWAIT")));
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			break;
		case "VERIFYLABEL":
			Value = common.ReadElement(Common.o.getObject("eleSummaryPreRenewalLabel"), Integer.valueOf(HTML.properties.getProperty

("NORMALWAIT")));
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			break;
		case "VERIFYQUOTEPRESENTED":
			Value = common.ReadElement(Common.o.getObject("eleQuotePre"), Integer.valueOf(HTML.properties.getProperty

("NORMALWAIT")));
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			break;
		case "VERIFYQUOTESUCCESSFUL":
			Value = common.ReadElement(Common.o.getObject("eleQuoteSuc"), Integer.valueOf(HTML.properties.getProperty

("NORMALWAIT")));
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			break;
		case "VERIFYQUOTEDATE":
			logger.info(sValue[0]);
			Value = common.ReadElement(Common.o.getObject("eleQuoteDate"), Integer.valueOf(HTML.properties.getProperty

("NORMALWAIT")));
			status = common.CompareStringResult("QuotePresentedDate", SCRCommon.ReturnCurrentDate(), Value);
			break;
		case "VERIFYTITLE":
			Value = common.ReadElement(Common.o.getObject("eleCLATitlePolicyInfo"), Integer.valueOf(HTML.properties.getProperty

("NORMALWAIT")));
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			break;
		/*
		 * case "VERIFYEXPIRYFIELDS": logger.info(sValue[0]); Value =
		 * common.ReadElementFromListEditBox(Common.o.getObject("edtExpiringPremium"),
		 * Integer.valueOf(HTML.properties.getProperty("NORMALWAIT"))); status =
		 * common.CompareStringResult("Expiry Field - Expiring Premium", sValue[1],
		 * Value); Value = common.ReadElement(Common.o.getObject("lstRenewalType"),
		 * Integer.valueOf(HTML.properties.getProperty("NORMALWAIT"))); status =
		 * common.CompareStringResult("Expiry Field - Renewal Type", sValue[2], Value);
		 * status = common.ElementExistOrNotTrue("Previous PolicyNumber Label",
		 * "Label - Previous policy number should be displayed",
		 * "Previous Policy number dispalyed", Common.o.getObject("lblPreviousPolNo"));
		 * status = common.ElementExistOrNotTrue("Previous PolicyNumber Editbox field",
		 * "Editbox - Previous policy number should be displayed",
		 * "Previous Policy number textbox  dispalyed",
		 * Common.o.getObject("EdtPreviousPolNo")); break;
		 */
		case "VERIFYEXPIRYFIELDS":
			logger.info(sValue[0]);
			Value = common.ReadElementFromListEditBox(Common.o.getObject("edtExpiringPremium"), Integer.valueOf

(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("Expiry Field - Expiring Premium", sValue[1], Value);
			Value = common.ReadElement(Common.o.getObject("lstRenewalType"), Integer.valueOf(HTML.properties.getProperty

("NORMALWAIT")));
			status = common.CompareStringResult("Expiry Field - Renewal Type", sValue[2], Value);
			status = common.ElementExistOrNotTrue("Previous PolicyNumber Label", "Label - Previous policy number should be displayed", "Previous Policy number dispalyed", Common.o.getObject("lblPreviousPolNo"));
			status = common.ElementExistOrNotTrue("Previous PolicyNumber Editbox field", "Editbox - Previous policy number should be isplayed", "Previous Policy number textbox  dispalyed", Common.o.getObject("EdtPreviousPolNo"));
			break;
		case "VERIFYWARNINGLABEL":
			Value = common.ReadElement(Common.o.getObject("eleRenewalWarning"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			break;
		case "VERIFYUPLOADTOQUOTEWARNING":
			Value = common.ReadElement(Common.o.getObject("eleCLAPIErrorMsgs"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			break;
		}
		if (!status) {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "" + sValue[0] + " case should PASS", "" + sValue[0] + " case should not fail", "FAIL");
			status = true;
		}
		return status;
	}

	/**
	 * @function use to select policy term from the account file summary screen
	 * @param sReadString
	 * @return true/false
	 * @throws Exception
	 */
	public boolean SelectPolicyTab(String sReadString) throws Exception {
		boolean status = false;
		status = common.ActionOnTable(Common.o.getObject("eleAccountSummary_PolicyTermsTbl"), 7, 1, "", "div");
		return status;
	}

	public boolean DefaultEffectiveDate(String strFuncValue) throws Exception {
		boolean status = false;
		String[] strSplitValue = strFuncValue.split(":::");
		String strDate = null;
		switch (strSplitValue[0].toUpperCase()) {
		case "CURRENTDATE":
			strDate = SCRCommon.ReturnCurrentDate();
			status = common.SafeAction(Common.o.getObject("edtCLAEffDate"), strDate, "edt");
			break;
		case "INCREASEDECREASEDATE":
			strDate = SCRCommon.returnDate(Integer.parseInt(strSplitValue[1]));
			status = common.SafeAction(Common.o.getObject("edtCLAEffDate"), strDate, "edt");
			break;
		}
		return status;
	}

	public boolean DefaultExpiryDate(String strFuncValue) throws Exception {
		boolean status = false;
		String[] strSplitValue = strFuncValue.split(":::");
		String strDate = null;
		switch (strSplitValue[0].toUpperCase()) {
		case "CURRENTDATE":
			strDate = SCRCommon.ReturnCurrentDate();
			status = common.SafeAction(Common.o.getObject("edtCLAExpDate"), strDate, "edt");
			break;
		case "INCREASEDECREASEDATE":
			strDate = SCRCommon.returnDate(Integer.parseInt(strSplitValue[1]));
			status = common.SafeAction(Common.o.getObject("edtCLAExpDate"), strDate, "edt");
			break;
		}
		return status;
	}
}