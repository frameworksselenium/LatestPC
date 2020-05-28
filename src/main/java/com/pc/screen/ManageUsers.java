
package com.pc.screen;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.codoid.products.exception.FilloException;
import com.pc.constants.PCConstants;
import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.PCThreadCache;
import com.pc.utilities.XlsxReader;

public class ManageUsers {

	public static String sheetname = "ManageUsers";
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();

	public static String strFileName = HTML.properties.getProperty("suitepath") + "\\Data\\Data.xlsm";

	public Boolean SCRManageUsers() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "Manage User Component Should be executed Successfully", "Manage User Component executed Successfully", "FAIL");
			return false;
		} else {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "Manage User Component Should be executed Successfully", "Manage User Component was not executed Successfully", "PASS");
		}

		return status;
	}

	public Boolean User_AddRole(String svalue) throws Exception {
		Boolean status = true;
		String[] sValue = svalue.split(":::");
		WebElement table = common.returnObject(common.o.getObject("eleUser_Roletbl"));
		List<WebElement> rows_table = table.findElements(By.tagName("tr"));
		int rowCount = rows_table.size();
		SCRCommon.DataWebTable(common.o.getObject("eleUser_Roletbl"), rowCount, 1, sValue[0], "single", "div");
		status = common.SafeAction(common.o.getObject("eleUser_RoleName"), sValue[1], "lst");

		if (!status) {
			logger.info("Role'" + sValue[1] + "' was not Succesfully Added");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "User should be able to select User Value", "User not able to Click User table Column value", "FAIL");
			return false;

		} else {
			logger.info("Role'" + sValue[1] + "' was Succesfully Added");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "User should be able to select User Value", "User able to Select User table Column value", "PASS");
		}

		return status;

	}

	public Boolean User_Skill(String svalue) throws Exception {
		Boolean status = true;
		String[] sValue = svalue.split(":::");
		WebElement table = common.returnObject(Common.o.getObject("eleUser_Skilltbl"));
		List<WebElement> rows_table = table.findElements(By.tagName("tr"));
		int rowCount = rows_table.size();
		SCRCommon.DataWebTable(Common.o.getObject("eleUser_Skilltbl"), rowCount, 1, sValue[0], "single", "div");
		status = common.SafeAction(Common.o.getObject("eleUser_SkillSegment"), sValue[1], "lst");
		status = common.SafeAction(Common.o.getObject("eleUser_Skill"), sValue[3], "lst");

		if (!status) {
			logger.info("Skill'" + sValue[1] + "' was not Succesfully Added");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "User should be able to select Skill Value", "User not able to Select Skill table Column value", "FAIL");
			return false;

		} else {
			logger.info("Skill'" + sValue[1] + "' was Succesfully Added");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "User should be able to select Skill Value", "User able to Select Skill table Column value", "PASS");
		}

		return status;

	}

	public Boolean User_Track(String svalue) throws Exception {
		Boolean status = true;
		String[] sValue = svalue.split(":::");
		WebElement table = common.returnObject(Common.o.getObject("eleUser_Tracktbl"));
		List<WebElement> rows_table = table.findElements(By.tagName("tr"));
		int rowCount = rows_table.size();
		SCRCommon.DataWebTable(Common.o.getObject("eleUser_Tracktbl"), rowCount, 1, sValue[0], "single", "div");
		status = common.SafeAction(Common.o.getObject("eleUser_SkillSegment"), sValue[1], "lst");
		// SCRCommon.DataWebTable(Common.o.getObject("eleUser_Skilltbl"), rowCount, 2,
		// sValue[2], "single");
		status = common.SafeAction(Common.o.getObject("eleUser_Track"), sValue[3], "lst");

		if (!status) {
			logger.info("Track'" + sValue[1] + "' was not Succesfully Added");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "User should be able to select Track Value", "User not able to Select Track table Column value", "FAIL");
			return false;

		} else {
			logger.info("Track'" + sValue[1] + "' was Succesfully Added");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "User should be able to select Track Value", "User able to Select Track table Column value", "PASS");
		}

		return status;

	}

	public Boolean User_AddUWAuthority(String svalue) throws Exception {
		Boolean status = true;
		String[] sValue = svalue.split(":::");
		WebElement table = common.returnObject(Common.o.getObject("eleUser_AddUWAuthoritytbl"));
		List<WebElement> rows_table = table.findElements(By.tagName("tr"));
		int rowCount = rows_table.size();
		SCRCommon.DataWebTable(Common.o.getObject("eleUser_AddUWAuthoritytbl"), rowCount, 1, sValue[0], "single", "div");
		status = common.SafeAction(Common.o.getObject("eleUser_UWAuthorityName"), sValue[1], "lst");

		if (!status) {
			logger.info("UW Authority'" + sValue[1] + "' was not Succesfully Added");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "User should be able to select UW Authority Value", "User not able to Click UW Authority table Column value", "FAIL");
			return false;

		} else {
			logger.info("UW Authority'" + sValue[1] + "' was Succesfully Added");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "User should be able to select UW Authority Value", "User able to Select UW Authority table Column value", "PASS");
		}

		return status;

	}

	public Boolean User_SelectTable(String svalue) throws Exception {
		Boolean status = false;
		String[] sValue = svalue.split(":::");
		switch (sValue[0]) {
		case "Skill": {
			status = common.ActionOnTable(Common.o.getObject("eleUser_Skilltbl"), 1, 0, sValue[1], "", "div");
			break;
		}
		case "Track": {
			status = common.ActionOnTable(Common.o.getObject("eleUser_Tracktbl"), 1, 0, sValue[1], "", "div");
			break;
		}
		case "Roles": {
			status = common.ActionOnTable(Common.o.getObject("eleUser_Roletbl"), 1, 0, sValue[1], "", "div");
			break;
		}
		case "UWAuthority": {
			status = common.ActionOnTable(Common.o.getObject("eleUser_AddUWAuthoritytbl"), 1, 0, sValue[1], "", "div");
			break;
		}
		case "Access": {
			status = common.ActionOnTable(Common.o.getObject("eleUser_Accesstbl"), 1, 0, sValue[1], "", "div");
			break;
		}
		case "Attribute": {
			status = common.ActionOnTable(Common.o.getObject("eleUser_AttributeTbl"), 0, 0, "", "", "div");
			break;
		}
		}
		return status;
	}

	public Boolean User_UserName(String strValue) throws FilloException {
		HashMap<String, Object> updateColumnNameValues = new HashMap<String, Object>();
		HashMap<String, Object> whereConstraint = new HashMap<String, Object>();
		String tcID = PCThreadCache.getInstance().getProperty("TCID");
		XlsxReader sXL = XlsxReader.getInstance();
		updateColumnNameValues.put(PCConstants.User_UserName, strValue);
		whereConstraint.put(PCConstants.ID, tcID);
		PCThreadCache.getInstance().setProperty("User_UserName", strValue);
		//sXL.executeUpdateQuery(PCConstants.SHEET_MANAGEUSERS, updateColumnNameValues, whereConstraint);

		// common.UpdateDataInExcel(strFileName, "Update ManageUsers Set User_UserName
		// ='" + strValue + "' where ID = '" +
		// PCThreadCache.getInstance().getProperty("TCID") + "'");
		String value3[] = PCThreadCache.getInstance().getProperty("TCID").split("TC");
		int value4 = Integer.parseInt(value3[1]) + 1;
		String value5 = "TC" + value4;

		updateColumnNameValues.clear();
		whereConstraint.clear();
		updateColumnNameValues.put(PCConstants.User_UserName, strValue);
		whereConstraint.put(PCConstants.ID, value5);
		PCThreadCache.getInstance().setProperty("User_UserName", strValue);
		//sXL.executeUpdateQuery(PCConstants.SHEET_SEARCHUSER, updateColumnNameValues, whereConstraint);

		// common.UpdateDataInExcel(strFileName, "Update SearchUser Set User_UserName
		// ='" + strValue + "' where ID = '" + value5 + "'");
		return true;
	}

}