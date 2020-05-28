/**
 * @ClassPurpose This Class used for the Documents Screen
 * @Scriptor Nishantini
 * @ReviewedBy 
 * @ModifiedBy Sojan David
 * @LastDateModified 4/17/2017
 */
package com.pc.screen;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.HTML;
import com.pc.utilities.PCThreadCache;

public class Documents {

	public static String sheetname = "Documents";
	Common common = CommonManager.getInstance().getCommon();
	//static Logger logger = Logger.getLogger(sheetname);
	//private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));

	/**
	 * @function Use to perform all the objects/elements/functions from the
	 *           Documents sheet
	 * @return true/false
	 * @throws Exception
	 */
	public Boolean SCRDocuments() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			return status;
		}
		return status;
	}

	/**
	 * @function Use to select the document from the d
	 * @param sFuncValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean selectDocument(String sFuncValue) throws Exception {
		Boolean status = false;
		String sValue[] = sFuncValue.split(":::");
		try {
			switch (sValue[0].toUpperCase()) {
			case "ACCOUNT":
				status = common.ActionOnTable(Common.o.getObject("eleDocumentTable"), 2, 2, sValue[1], "a");
				break;
			case "POLICY":
				status = common.ActionOnTable(Common.o.getObject("eleDocumentTable"), 2, 2, sValue[1], "a");
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * @function verify the expected behaviour
	 * @param funValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean VerifyResults(String funValue) throws Exception {
		boolean status = true;
		String[] sValue = funValue.split(":::");
		String wait = HTML.properties.getProperty("SHORTWAIT");
		Integer x = Integer.valueOf(wait);
		String ActValue;
		String value;
		switch (sValue[0].toUpperCase()) {
		case "VERIFY_DOC_ACTIONS":
			logger.info("Validating the '" + sValue[0] + "'");
			ActValue = common.ReadElement(Common.o.getObject("eleView_DocButton"), x);
			status = common.CompareStringResult("document Actions - View", sValue[2], ActValue);
			ActValue = common.ReadElement(Common.o.getObject("eleCheckOut"), x);
			status = common.CompareStringResult("document Actions - CheckOut", sValue[1], ActValue);
			break;
		case "VERIFY_DOC_ACTIONS_AFTER_CHKOUT":
			logger.info("Validating the '" + sValue[0] + "'");
			ActValue = common.ReadElement(Common.o.getObject("eleCheckIn"), x);
			status = common.CompareStringResult("document Actions - CheckIn", sValue[1], ActValue);
			ActValue = common.ReadElement(Common.o.getObject("eleView_DocButton"), x);
			status = common.CompareStringResult("document Actions - View", sValue[2], ActValue);
			ActValue = common.ReadElement(Common.o.getObject("eleDownload_DocButton"), x);
			status = common.CompareStringResult("document Actions - View", sValue[3], ActValue);
			ActValue = common.ReadElement(Common.o.getObject("eleDiscardButton"), x);
			status = common.CompareStringResult("document Actions - Discard", sValue[4], ActValue);
			break;
		case "VERIFY_DOC_ACTIONS_DISABLED":
			logger.info("Validating the '" + sValue[0] + "'");
			status = common.ElementDisplayed(common.o.getObject("eleCheckOut_Disabled"));
			ActValue = common.ReadElement(Common.o.getObject("eleCheckOut_Disabled"), x);
			status = common.CompareStringResult("document Actions - ChekOut Disabled", sValue[1], ActValue);
			/*
			 * status=common.ElementExist(common.o.getObject("eleCheckIn"));
			 * status=common.ElementDisplayed(common.o.getObject("eleCheckIn_diabled"));
			 * ActValue=common.ReadElement(Common.o.getObject("eleCheckIn_diabled"),x);
			 * status=common.CompareStringResult("document Actions - CheckIn Disabled",
			 * sValue[1], ActValue);
			 * status=common.ElementDisplayed(common.o.getObject("eleDiscard_Disabled"));
			 * ActValue=common.ReadElement(Common.o.getObject("eleDiscard_Disabled"),x);
			 * status=common.CompareStringResult("document Actions - Discard Disabled",
			 * sValue[2], ActValue);
			 */
			break;
		case "VERIFY_FILE_FORMAT":
			logger.info("Validating the '" + sValue[0] + "'");
			String strFileType = common.ReadElementGetAttribute(Common.o.getObject("eleDocImg"), "src", x);
			if (sValue[1].equalsIgnoreCase("WORD")) {
				if (strFileType.contains("word.png")) {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Verfith Format of document present", "Expected -" + sValue[1] + "Actual -" + strFileType, "PASS");
					status = true;

				} else {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Verfith Format of document present", "Expected -" + sValue[1] + "Actual -" + strFileType, "FAIL");
					status = false;

				}
			}
			break;
		case "Table":
			status = common.ActionOnTable(Common.o.getObject("elePolDocumentTble"), 5, 4, "UnderwritingTools", "a");
			break;
		case "VERIFY_DOC_ACTIONS_DOESNT_EXIST":
			logger.info("Validating the '" + sValue[0] + "'");
			status = common.ElementExistOrNotFalse(sValue[1], "Button/element should not be available in Screen", "As expected Button/element is not displayed", Common.o.getObject("ele" + sValue[1]));
			status = common.ElementExistOrNotFalse(sValue[2], "Button/element should not be available in Screen", "As expected Button/element is not displayed", Common.o.getObject("ele" + sValue[2]));
			break;

		case "VERIFYDOCUMENT":
			logger.info(sValue[0]);
			status = common.ActionOnTable(Common.o.getObject("eleDocumentTable"), 2, 2, sValue[1], "a");
			break;

		case "VERIFYDOCUMENTISSUED":
			logger.info(sValue[0]);
			status = common.ActionOnTable(Common.o.getObject("eleDocumentTableIssued"), 2, 2, sValue[1], "a");
			break;
		case "CHECKPOLICYSTATUS":
			logger.info(sValue[0]);
			value = common.ReadElement(Common.o.getObject("elePolicyInfo_PolicyStatus"), Integer.valueOf(HTML.properties.getProperty

("NORMALWAIT")));
			status = common.CompareStringResult("Policy Status", sValue[1], value);
			break;
		}
		return status;
	}

	/**
	 * @function Method used to upload the file into UI
	 * @return true/false
	 */
	public static boolean uploadFile() {
		boolean status = true;
		try {
			Runtime.getRuntime().exec("C:\\Selenium\\AutoIT\\FileUpload.exe");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return status;
	}

	public boolean VerifyPolicyDocument() throws Exception {
		Boolean status = false;
		By obj = Common.o.getObject("elePolicyNumberSummary");
		WebElement ele = ManagerDriver.getInstance().getWebDriver().findElement(obj);
		String value = ele.getText();
		String doc = "Policy - " + value;
		common.WaitForPageToBeReady();
		common.SafeAction(common.o.getObject("eleDocuments"), "YES", "ele");
		status = common.VerifyTextFromTable(common.o.getObject("eleDocumentTable"), 1, 2, doc);
		if (status) {

			logger.info("Search Policy Number " + value + " in the Policy Document Table");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "System should search the Policy number '" + value + "' in the Policy Document Table", "System searched the Policy number '" + 

value + "' in the Policy Document Table", "PASS");
			status = true;
		} else {
			logger.info("Search Policy Number " + value + " in the Policy Document Table");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "System should search the Policy number '" + value + "' in the Policy Document Table", "System did not search the Policy number '" + value + "' in the Policy Document Table", "FAIL");
			status = false;
			;
		}
		return status;
	}

}