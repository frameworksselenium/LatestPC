	package com.pc.screen;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.PCThreadCache;

public class VehicleIDCard {

	public static String sheetname = "VehicleIDCard";
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();

	public Boolean SCRVehicleIDCard() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			return false;
		}

		String sScreentoNavi = common.getSpecifiedExcelValue(sheetname, "NaviToScreen");
		if (sScreentoNavi.contains("VehicleInfo")) {
			if (common.WaitForElementExist(Common.o.getObject("eleVINewVehicleTitle"), Integer.valueOf(HTML.properties.getProperty

("LONGWAIT")))) {
				logger.info("System displayed Vehicle Information Page");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should display  Vehicle Information Page", "System displayed  Vehicle Information Page", "PASS");
				status = true;
			} else {
				logger.info("System not displayed  Vehicle Information Page");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should display  Vehicle Information Page", "System not displayed  Vehicle Information Page", "FAIL");
				status = false;
			}

		} else if (sScreentoNavi.contains("PolicyInfo")) {
			if (common.WaitForElementExist(Common.o.getObject("eleCLATitlePolicyInfo"), Integer.valueOf(HTML.properties.getProperty

("LONGWAIT")))) {
				logger.info("System displayed CLA Policy Info Page");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should display CLA Policy Info Page", "System displayed CLA Policy Info Page", "PASS");
				status = true;
			} else {
				logger.info("System not displayed CLA Policy Info Page");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should display CLA Policy Info Page", "System not displayed CLA Policy Info Page", "FAIL");
				status = false;
			}
		}
		return status;
	}

	// Function to Set effective date
	public boolean DefaultEffectiveDate() throws Exception {
		boolean status = false;
		String sCurrentDate = SCRCommon.ReturnCurrentDate();
		status = common.SafeAction(Common.o.getObject("edtAutoIdCardEndorsementEffDate"), sCurrentDate, "edt");
		return status;
	}

	// Function to Select Name from the list
	/*
	 * public boolean SelectName() throws Exception { boolean status = false; status
	 * = common.SafeAction(Common.o.getObject("lstAutoIdCardContactName"),
	 * CreateAccount.AccountName, "lst"); return status; }
	 */

	// Function to verify and compare expected error message with actual
	public boolean VerifyVehicleErrorMsg(String strValue) throws Exception {
		boolean status = false;
		String[] sValue = strValue.split(":::");
		String strErrorMsg = null;
		status = driver.findElements(Common.o.getObject("eleAutoIdCardErrorMsg")).size() != 0;
		strErrorMsg = common.ReadElement(Common.o.getObject("eleAutoIdCardErrorMsg"), Integer.valueOf(HTML.properties.getProperty

("LONGWAIT")));
		status = common.CompareStringResult(sValue[0], sValue[1], strErrorMsg);
		return status;
	}
}