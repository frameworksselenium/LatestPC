
package com.pc.screen;

import org.apache.log4j.Logger;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.PCThreadCache;

public class Group {

	public static String sheetname = "Group";
	Common common = CommonManager.getInstance().getCommon();
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
	public static String AccountNumber;
	public static String AccountName;

	public Boolean SCRGroup() throws Exception {

		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			return false;
		}
		/*
		 * if(Common.WaitUntilClickable(Common.o.getObject("eleCreateNewAccountNumber"),
		 * Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")))) { sXL = new
		 * XlsxReader(HTML.properties.getProperty("DataSheetName")); AccountNumber =
		 * Common.ReadElement(Common.o.getObject("eleCreateNewAccountNumber"),
		 * Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT"))); AccountName =
		 * Common.ReadElement(Common.o.getObject("eleAccountName"),
		 * Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT"))); // String row
		 * = HTML.properties.getProperty("Row"); // int inte = Integer.parseInt(row)-1;
		 * // sXL.setCellData(sheetname,AccountNumber, inte, 2); status =
		 * Common.UpdateQuery(HTML.properties.getProperty("DataSheetName"),
		 * "Update CreateAccount Set AccountNumber = '"+AccountNumber+"' where ID='"
		 * +PCThreadCache.getInstance().getProperty("TCID")+"'");
		 * logger.info("System displayed Account Summary Page with Account Number: " +
		 * AccountNumber);
		 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
		 * PCThreadCache.getInstance().getProperty("methodName"),
		 * "System should display Account Summary Page with Account Number"
		 * ,"System displayed Account Summary Page with Account Number: '" +
		 * AccountNumber + "'", "PASS"); status = true; } else {
		 * logger.info("System not displayed Account Summary Page");
		 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
		 * PCThreadCache.getInstance().getProperty("methodName"),
		 * "System should display Account Summary Page with Account Number"
		 * ,"System not displayed Account Summary Page", "FAIL"); status = false; }
		 */
		return status;
	}

	public boolean VerifyResults(String funValue) throws Exception {
		boolean status = false;
		String[] sValue = funValue.split(":::");
		String Value = null;
		switch (sValue[0].toUpperCase()) {
		case "VERIFYERRORMESSAGE":
			Value = common.ReadElement(Common.o.getObject("eleCommonZeroResults"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("VerifyMessage", sValue[1], Value);
			break;
		case "VERIFYGROUPINFOSCREEN":
			logger.info(sValue[0]);
			status = common.ElementExistOrNotTrue("VerifyGroupInfoScreen", "Group Info Screen should not be editable", "Group Info Screen is not editable", (Common.o.getObject("eleGroupName")));
			status = common.ElementExistOrNotTrue("VerifyGroupInfoScreen", "Group Info Screen should not be editable", "Group Info Screen is not editable", (Common.o.getObject("eleType")));
			status = common.ElementExistOrNotTrue("VerifyGroupInfoScreen", "Group Info Screen should not be editable", "Group Info Screen is not editable", (Common.o.getObject("eleSecZone")));
			break;
		case "CHECKREGION":
			Value = common.GetTextFromTable(Common.o.getObject("eleSelectRegionTable"), 1, sValue[1]);
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
	 * @function use to select Group from Groups screen
	 * @param sReadString
	 * @return true/false
	 * @throws Exception
	 */
	public boolean SelectActivityTable(String sReadString) throws Exception {
		boolean status = false;
		status = common.ActionOnTable(Common.o.getObject("eleSelectGroupFromTable"), 2, 0, "", "a");
		return status;
	}

	/**
	 * @function use to select the new region from the region screen
	 * @param sReadString
	 * @return true/false
	 * @throws Exception
	 */
	public boolean SelectActivityRegionTable(String sReadString) throws Exception {
		boolean status = false;
		status = common.ActionOnTable(Common.o.getObject("eleSelectRegionTable"), 1, 0, "", "div");
		return status;
	}

	/**
	 * @function use to select the region from the region screen
	 * @param sReadString
	 * @return true/false
	 * @throws Exception
	 */
	public boolean SelectActivityRegion(String sReadString) throws Exception {
		boolean status = false;
		status = common.ActionOnTable(Common.o.getObject("eleRegionsTable"), 1, 0, "", "div");
		return status;
	}

	/**
	 * @function use to select the region from the browse group region screen
	 * @param sReadString
	 * @return true/false
	 * @throws Exception
	 */
	public boolean SelectGroup(String sReadString) throws Exception {
		boolean status = false;
		status = common.ActionOnTable(Common.o.getObject("eleGrpTableAdd"), 1, 0, "", "div");
		return status;
	}

}