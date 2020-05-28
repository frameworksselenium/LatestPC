
/**
 * @ClassPurpose Access all the data's for Edit Account Page
 * @Scriptor Kumar
 * @ReviewedBy
 * @ModifiedBy Sojan
 * @LastDateModified 3/21/2017
 */
package com.pc.screen;

import org.apache.log4j.Logger;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.PCThreadCache;

public class AccountManage {

	public static String sheetname = "AccountManage";
	//static Logger logger = Logger.getLogger(sheetname);
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));
	public static String AccountNumber;
	public static String AccountName;
	Common common = CommonManager.getInstance().getCommon();

	public Boolean SCRAccountManage() throws Exception {

		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			return false;
		}
		String sScreenToNavigate = common.getSpecifiedExcelValue(sheetname, "NaviToScreen");

		if (sScreenToNavigate.equals("AccountFileSummary")) {
			if (common.WaitForElementExist(Common.o.getObject("eleAccountFileSummary"), Integer.valueOf(HTML.properties.getProperty

("LONGWAIT")))) {
				logger.info("System displayed Account File Summary Page");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should display Account File Summary Page", "System displayed Account File Summary Page", "PASS");
				status = true;
			} else {
				logger.info("System not displayed Account File Summary Page");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should display Account File Summary Page", "System not displayed Account File Summary Page", "FAIL");
				status = false;
			}
		}

		return status;
	}

	/**
	 * @function This function verify policy transaction
	 * @return true/false
	 * @throws Exception
	 */
	public boolean VerifyDateCustomerVerified() throws Exception {
		boolean status = false;
		String sDate = SCRCommon.ReturnCurrentDate();
		try {
			String sApplnValue = common.ReadElement(Common.o.getObject("eleAMDateCustVerified"), Integer.valueOf

(HTML.properties.getProperty("LONGWAIT")));

			if (sApplnValue.equals(sDate)) {
				status = true;
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should display Cusotmer Verified Date", "System date is displayed and matching with current date" + 

sApplnValue, "PASS");
			} else {
				status = false;
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should display Cusotmer Verified Date", "System date is not  matching with current date" + sApplnValue, 

"FAIL");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * @function This function verify producer Code
	 * @param
	 * @return status
	 * @throws Exception
	 */
	public boolean VerifyProducerCode() throws Exception {
		boolean status = false;
		String sApplnValue = "";
		try {

			sApplnValue = common.GetTextFromTable(Common.o.getObject("eleAMProducerCode"), 0, 0);

			if (!sApplnValue.equals("")) {
				status = true;
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should display Producer Code", "Producer Code is displayed" + sApplnValue, "PASS");
			} else {
				status = false;
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should display Producer Code", "Producer Code not displayed" + sApplnValue, "FAIL");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
}
