package com.pc.screen;

import org.apache.log4j.Logger;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.PCThreadCache;

public class ShellContacts {

	public static String sheetName = "ShellContacts";
	Common common = CommonManager.getInstance().getCommon();
	//static Logger logger = Logger.getLogger(sheetName);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));

	public Boolean SCRShellContacts() throws Exception {
		Boolean status = false;
		status = common.ClassComponent(sheetName, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

	/*
	 * public boolean VerifyResults(String strValue) throws Exception{
	 * 
	 * boolean status = false; String value =
	 * common.GetTextFromTable(Common.o.getObject("eleShellRoleTable"), 1,
	 * strValue); if(value != null){ status=true; } return status; }
	 */

	public boolean VerifyResults(String strValue) throws Exception {

		boolean status = false;
		logger.info("Verify the Results");
		String[] sValue = strValue.split(":::");
		String Value = null;
		switch (sValue[0].toUpperCase()) {
		case "ROLE_TABLE":
			String value = common.GetTextFromTable(Common.o.getObject("eleShellRoleTable"), 1, sValue[1]);
			if (value != null) {
				status = true;
			}
			break;
		case "VALIDATE_BILLING_CONTACT_TABLE":
			value = common.GetTextFromTableTagName(Common.o.getObject("eleShell_ContactTable"), 0, 1, "a");
			status = common.CompareStringResult("Billing contact Name", sValue[1], value);
			value = common.GetTextFromTableTagName(Common.o.getObject("eleShell_ContactTable"), 0, 2, "div");
			status = common.CompareStringResult("Billing contact Name", sValue[2], value);
			break;
		case "VALIDATE_BILLING_CONTACT_DETAILS":
			value = common.ReadElement(Common.o.getObject("ele" + sValue[1]), Integer.valueOf(HTML.properties.getProperty

("NORMALWAIT")));
			status = common.CompareStringResult("Billing contact First Name", sValue[2], value);
			value = common.ReadElement(Common.o.getObject("ele" + sValue[3]), Integer.valueOf(HTML.properties.getProperty

("NORMALWAIT")));
			status = common.CompareStringResult("Billing contact Last Name", sValue[4], value);
			break;

		}
		return status;
	}

}