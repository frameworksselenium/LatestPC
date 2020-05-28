package com.pc.screen;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.pc.constants.PCConstants;
import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.PCThreadCache;
import com.pc.utilities.XlsxReader;

public class SearchNewAccount {

	public static String sheetname = "SearchNewAccount";
	Common common = CommonManager.getInstance().getCommon();
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));

	public Boolean SCRSearchNewAccount() throws Exception {

		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			return false;
		}
		if (common.WaitUntilClickable(Common.o.getObject("edtAddress1"), Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")))) {
			logger.info("System displayed Create New Account Page");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "System should display Create New Account Page", "System displayed Create New Account Page", "PASS");
			status = true;
		} else {
			logger.info("System not displayed Create New Account Page");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "System should display Create New Account Page", "System not displayed Create New Account Page", "FAIL");
			status = false;
		}
		return status;
	}

	/**
	 * Function used to update dynamic company name and update in sheet
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean DynamicCompanyName() throws Exception {
		boolean status = false;
		String Name = "";
		HashMap<String, Object> updateColumnNameValues = new HashMap<String, Object>();
		HashMap<String, Object> whereConstraint = new HashMap<String, Object>();
		XlsxReader sXL = XlsxReader.getInstance();
		Date d = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy");
		String date = dateFormat.format(d);
		Name = "SampleNGS" + "_" + date + "_" + d.getHours() + d.getMinutes() + d.getSeconds() + Thread.currentThread().getId();
		System.out.print("Dynamic Company name : " + Name);
		status = common.SafeAction(Common.o.getObject("edtCompanyName"), Name, "edtCompanyName");
		// ManagerDriver.getInstance().getWebDriver().findElement(Common.o.getObject("edtCompanyName")).sendKeys(Name);

		whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
		updateColumnNameValues.put("DynamicAccountName", Name);
		PCThreadCache.getInstance().setProperty("DynamicAccountName", Name);

		/*status = sXL.executeUpdateQuery(PCConstants.SHEET_SEARCHNEWACCOUNT, updateColumnNameValues, whereConstraint);
		status = sXL.executeUpdateQuery(PCConstants.SHEET_XMLVALIDATION, updateColumnNameValues, whereConstraint);
		status = sXL.executeUpdateQuery(PCConstants.SHEET_InsuranceScoreLog, updateColumnNameValues, whereConstraint);*/

		return status;

	}

}
