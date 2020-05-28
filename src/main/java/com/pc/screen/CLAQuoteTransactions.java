package com.pc.screen;

import org.apache.log4j.Logger;

import com.pc.constants.PCConstants;
import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.PCThreadCache;

public class CLAQuoteTransactions {
	
	public static String sheetName = "CLAQuoteTransactions";
	//static Logger logger = Logger.getLogger(sheetName);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();

	public Boolean SCRCLAQuoteTransactions() throws Exception {
		Boolean status = false;
		status = common.ClassComponent(sheetName, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

	public boolean QuoteTransactionTable(String sFunValue) throws Exception {
		boolean status = false;
		String[] sValue = sFunValue.split(":::");
		String sEffectiveDate = SCRCommon.ReturnCurrentDate();
		String sOneYearDate = SCRCommon.ReturnOneYearFromDate();
		status = common.WaitForElementExist(Common.o.getObject("eleCLAQTTable"), Integer.valueOf(HTML.properties.getProperty

("LONGWAIT")));
		int sRowCount = SCRCommon.TableRowCount(Common.o.getObject("eleCLAQTTable"));
		try {
			switch (sValue[0].toUpperCase()) {
			case "NEWW":
				status = SCRCommon.DataWebTable(Common.o.getObject("eleCLAQTTable"), sRowCount, 1, "", "double", "div");
				status = common.SafeAction(Common.o.getObject("lstCLACompleteDate"), sEffectiveDate, "lstCLACompleteDate");
				status = common.SafeAction(Common.o.getObject("lstCLASystemAddress"), sValue[1], "lstCLASystemAddress");
				status = common.SafeAction(Common.o.getObject("lstCLAQuoteSeq"), sValue[2], "lstCLAQuoteSeq");
				status = common.SafeAction(Common.o.getObject("lstCLAQuoteDesc"), sValue[3], "lst");
				status = SCRCommon.DataWebTable(Common.o.getObject("eleCLAQTTable"), sRowCount, 6, "", "double", "div");
				status = common.SafeAction(Common.o.getObject("lstCLATotalPrem"), sValue[4], "lstCLATotalPrem");
				status = common.SafeAction(Common.o.getObject("lstCLAEffectiveDate"), sEffectiveDate, "lstCLAEffectiveDate");
				status = common.SafeAction(Common.o.getObject("lstCLAExpirationDate"), sOneYearDate, "lstCLAExpirationDate");
				break;
			case "USECASE":
				status = common.ActionOnTable(Common.o.getObject("eleCLAQTTable"), 0, 0, "", "img");
				break;
			case "NEW":
				status = SCRCommon.DataWebTable(Common.o.getObject("eleCLAQTTable"), sRowCount, 4, "", "double", "div");
				status = common.SafeAction(Common.o.getObject("lstCLAQuoteDesc"), sValue[1], "lstCLAQuoteDesc");
				status = common.SafeAction(Common.o.getObject("lstCLAQuotePolnum"), PCThreadCache.getInstance().getProperty

(PCConstants.CACHE_POLICY_NUMBER), "lstCLAQuotePolnum");
				status = common.SafeAction(Common.o.getObject("lstCLATotalPrem"), sValue[2], "lstCLATotalPrem");
				status = common.SafeAction(Common.o.getObject("lstCLAEffectiveDate"), sEffectiveDate, "lstCLAEffectiveDate");
				status = common.SafeAction(Common.o.getObject("lstCLAExpirationDate"), sOneYearDate, "lstCLAExpirationDate");
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/*
	 * public boolean QuoteDetailsTable(String sFunValue) {
	 * Common.WaitForPageToBeReady(); boolean status = false; String[] sValue =
	 * sFunValue.split(":::"); int sRowCount =
	 * SCRCommon.TableRowCount(Common.o.getObject("eleCLAQTQDTable")); try { status
	 * = SCRCommon.DataWebTable(Common.o.getObject("eleCLAQTQDTable"), sRowCount, 1,
	 * "","single"); status =Common.SafeAction(Common.o.getObject("lstCLAQTQDLOB"),
	 * sValue[0], "lst"); status
	 * =Common.SafeAction(Common.o.getObject("lstCLAQTQDTotalCost"), sValue[1],
	 * "lst"); status
	 * =Common.SafeAction(Common.o.getObject("lstCLAQTQDExposureState"), sValue[2],
	 * "lst"); status =Common.SafeAction(Common.o.getObject("lstCLAQTQDCompany"),
	 * sValue[3], "lst"); status
	 * =Common.SafeAction(Common.o.getObject("lstCLAQTQDLevelOfSupport"), sValue[4],
	 * "lst"); } catch(Exception e) { e.printStackTrace(); } return status; }
	 */

	public boolean QuoteDetailsTable(String sFunValue) {
		common.WaitForPageToBeReady();
		boolean status = false;
		String[] sValue = sFunValue.split(":::");
		int sRowCount = SCRCommon.TableRowCount(Common.o.getObject("eleCLAQTQDTable"));
		try {
			status = SCRCommon.DataWebTable(Common.o.getObject("eleCLAQTQDTable"), sRowCount, 1, "", "single", "div");
			status = common.SafeAction(Common.o.getObject("lstCLAQTQDLOB"), sValue[0], "lst");
			status = common.SafeAction(Common.o.getObject("lstCLAQTQDTotalCost"), sValue[1], "lst");
			status = SCRCommon.DataWebTable(Common.o.getObject("eleCLAQTQDTable"), sRowCount, 3, "<none>", "single", "div");
			status = common.SafeAction(Common.o.getObject("eleExposureStateColumn"), "ele", "ele");
			// status =Common.SafeAction(Common.o.getObject("eleExposureState"), "ele",
			// "ele");
			status = common.SafeAction(Common.o.getObject("dblExposureState"), "dbl", "dbl");
			status = common.SafeAction(Common.o.getObject("eleCommonUpdate"), "ele", "ele");
			// status =Common.SafeAction(Common.o.getObject("lstCLAQTQDExposureState"),
			// sValue[2], "lst");
			status = SCRCommon.DataWebTable(Common.o.getObject("eleCLAQTQDTable"), sRowCount, 4, "<none>", "single", "div");
			status = common.SafeAction(Common.o.getObject("lstCLAQTQDCompany"), sValue[3], "lst");
			status = common.SafeAction(Common.o.getObject("lstCLAQTQDLevelOfSupport"), sValue[4], "lst");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	public boolean SelectQuoteTransaction(String sFunValue) {
		common.WaitForPageToBeReady();
		boolean status = false;

		try {

			status = common.SelectCheckBoxOnTable(Common.o.getObject("eleCLAQTTable"), 2, 0, sFunValue, sFunValue, "img");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	public boolean QuoteTransactionTabledata(String sFunValue) throws Exception {
		boolean status = false;
		String[] sValue = sFunValue.split(":::");
		String sEffectiveDate = SCRCommon.ReturnCurrentDate();
		String sOneYearDate = SCRCommon.ReturnOneYearFromDate();
		status = common.WaitForElementExist(Common.o.getObject("eleCLAQTTable"), Integer.valueOf(HTML.properties.getProperty

("LONGWAIT")));
		int sRowCount = SCRCommon.TableRowCount(Common.o.getObject("eleCLAQTTable"));
		try {

			status = SCRCommon.DataWebTable(Common.o.getObject("eleCLAQTTable"), sRowCount, 3, "", "double", "div");
			status = common.SafeAction(Common.o.getObject("lstCLAQuoteSeq"), sValue[0], "lstCLAQuoteSeq");
			status = common.SafeAction(Common.o.getObject("lstCLAQuoteDesc"), sValue[1], "lst");
			status = common.SafeAction(Common.o.getObject("lstCLAQuotePolnum"), sValue[3], "lst");
			status = SCRCommon.DataWebTable(Common.o.getObject("eleCLAQTTable"), sRowCount, 6, "", "double", "div");
			status = common.SafeAction(Common.o.getObject("lstCLATotalPrem"), sValue[2], "lstCLATotalPrem");
			status = common.SafeAction(Common.o.getObject("lstCLAEffectiveDate"), sEffectiveDate, "lstCLAEffectiveDate");
			status = common.SafeAction(Common.o.getObject("lstCLAExpirationDate"), sOneYearDate, "lstCLAExpirationDate");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	public boolean selectTotalCost(String sFunctionValue) throws Throwable {
		boolean status = false;
		String[] sValue = sFunctionValue.split(":::");
		int sRowCount = SCRCommon.TableRowCount(Common.o.getObject("eleQuoteTransactionstbl"));
		switch (sValue[0].toUpperCase()) {

		case "TOTALCOST":
			try {

				status = SCRCommon.DataWebTable(Common.o.getObject("eleQuoteTransactionstbl"), sRowCount, 6, "", "double", 

"div");
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

	public boolean QuoteDetailsTableData(String sFunValue) {
		common.WaitForPageToBeReady();
		boolean status = false;
		String[] sValue = sFunValue.split(":::");
		int sRowCount = SCRCommon.TableRowCount(Common.o.getObject("eleCLAQTQDTable"));
		try {
			status = SCRCommon.DataWebTable(Common.o.getObject("eleCLAQTQDTable"), sRowCount, 1, "", "single", "div");
			status = common.SafeAction(Common.o.getObject("lstCLAQTQDLOB"), sValue[0], "lst");
			status = common.SafeAction(Common.o.getObject("lstCLAQTQDTotalCost"), sValue[1], "lst");
			/*
			 * status = SCRCommon.DataWebTable(Common.o.getObject("eleCLAQTQDTable"),
			 * sRowCount, 3, "<none>","single"); status
			 * =Common.SafeAction(Common.o.getObject("eleExposureStateColumn"), "ele",
			 * "ele");
			 */
			// status =Common.SafeAction(Common.o.getObject("eleExposureState"), "ele",
			// "ele");
			// status =Common.SafeAction(Common.o.getObject("dblExposureState"), "dbl",
			// "dbl");
			// status =Common.SafeAction(Common.o.getObject("eleCommonSave"), "ele", "ele");
			// status =Common.SafeAction(Common.o.getObject("lstCLAQTQDExposureState"),
			// sValue[2], "lst");
			// status = SCRCommon.DataWebTable(Common.o.getObject("eleCLAQTQDTable"),
			// sRowCount, 4, "<none>","single");
			status = SCRCommon.DataWebTable(Common.o.getObject("eleCLAQTQDTable"), sRowCount, 6, "", "single", "div");
			// status =Common.SafeAction(Common.o.getObject("lstCLAQTQDCompany"), sValue[3],
			// "lst");
			status = common.SafeAction(Common.o.getObject("lstCLAQTQDLevelOfSupport"), sValue[2], "lst");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

}