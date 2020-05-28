package com.pc.screen;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.PCThreadCache;

public class BulkMailing {

	public static String sheetname = "BulkMailing";
	Common common = CommonManager.getInstance().getCommon();
	//tatic Logger logger = Logger.getLogger(sheetname);
	//private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));

	public Boolean SCRBulkMailing() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

	/**
	 * @function to select the administration
	 * @return true/false
	 */
	public boolean selectBulkmailing() {
		boolean status = false;
		status = common.MouseHoverAction(Common.o.getObject("eleBulkemailing"), Common.o.getObject("eleHomeofficetorts"));
		return status;
	}

	/**
	 * @function to uploadfile in the
	 * @return
	 * @throws IOException
	 */
	public boolean UploadFile() throws IOException {
		boolean status = true;
		Runtime.getRuntime().exec("C:\\Selenium\\AutoIT\\BulkMailingUpload.exe");
		return status;
	}

	public boolean UploadCsvFile() throws IOException {
		boolean status = true;
		Runtime.getRuntime().exec("C:\\Selenium\\AutoIT\\CsvFileUpload.exe");
		return status;
	}

	public boolean SelectBulkMailingTable(String sReadString) throws Exception {
		boolean status = false;
		status = common.ActionOnTable(Common.o.getObject("eleBulkMailingTable"), 0, 7, "", "a");
		return status;
	}

	public boolean VerifyResults(String funValue) throws Exception {
		boolean status = false;
		String[] sValue = funValue.split(":::");
		String Value = null;
		switch (sValue[0].toUpperCase()) {
		case "CHECKSTATUS":
			Value = common.GetTextFromTable(Common.o.getObject("eleBulktbl"), 1, sValue[1]);
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			break;
		case "CHECKBATCHID":
			Value = common.GetTextFromTable(Common.o.getObject("eleBulkMailingTable"), 0, sValue[1]);
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			break;
		case "VERIFYPRINTOK":
			// logger.info(sValue[0]);
			status = common.ElementExistOrNotTrue("VerifyPrintOKselectable", "OK button should be selectable", "OK button should be selectable", (Common.o.getObject("elePrintOkButton")));
			break;
		case "VERIFYSTATUS":
			Value = common.GetTextFromTable(Common.o.getObject("eleBulktbl"), 1, sValue[1]);
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			break;
		}

		if (!status) {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "" + sValue[0] + " case should PASS", "" + sValue[0] + " case should not fail", "FAIL");
			status = true;
		}
		return status;
	}
}