
package com.pc.screen;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.codoid.products.exception.FilloException;
import com.pc.constants.PCConstants;
import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.PCThreadCache;
import com.pc.utilities.XlsxReader;

public class NewAffinityGroup {

	public static String sheetname = "NewAffinityGroup";
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();
	public static String strFileName = HTML.properties.getProperty("suitepath") + "\\Data\\Data.xlsm";

	public Boolean SCRNewAffinityGroup() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "New or Update Affinity Group Should be Created", "New Affinity Group was not successfully Created", "FAIL");
			return false;
		} else {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "New or Update Affinity Group Should be Created", "New Affinity Group was successfully Created", "PASS");
		}

		return status;
	}

	/**
	 * @function
	 * @param svalue
	 * @return
	 * @throws Exception
	 */
	public Boolean AddJurisdictions(String svalue) throws Exception {
		Boolean status = true;
		String[] sValue = svalue.split(":::");
		WebElement table = common.returnObject(Common.o.getObject("eleAffinityJuristictiontable"));
		List<WebElement> rows_table = table.findElements(By.tagName("tr"));
		int rowCount = rows_table.size();
		SCRCommon.DataWebTable(Common.o.getObject("eleAffinityJuristictiontable"), rowCount, 1, sValue[0], "single", "div");
		status = common.SafeAction(Common.o.getObject("eleAffinityJurisdictionVal"), sValue[1], "lst");

		if (!status) {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "User should be able to select Jurisdiction Value", "User not able to Click Jurisdiction table Column value", "FAIL");
			return false;

		} else {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "User should be able to select Jurisdiction Value", "User able to Select Jurisdiction table Column value", "PASS");
		}
		return status;
	}

	/**
	 * @function to get CurrentEffectiveDate and Enter in Appropriate field
	 * @param funValue
	 * @return status
	 * @throws Exception
	 **/
	public Boolean AddEffectiveDate(String svalue) throws Exception {
		Boolean status = true;
		String[] sValue = svalue.split(":::");
		String EffectivetDate = null;
		String ExpirationDate = null;
		switch (sValue[1]) {
		case "EffDateinProducerCodes": {
			WebElement table = common.returnObject(Common.o.getObject("eleAffinityproducerCodetbl"));
			List<WebElement> rows_table = table.findElements(By.tagName("tr"));
			int rowCount = rows_table.size();
			SCRCommon.DataWebTable(Common.o.getObject("eleAffinityproducerCodetbl"), rowCount, 2, sValue[0], "single", "div");
			EffectivetDate = SCRCommon.ReturnCurrentDate();
			status = common.SafeAction(Common.o.getObject("eleAffinityproducerCodeVal"), EffectivetDate, "lst");
			break;
		}
		case "ExpDateinProducerCodes": {
			WebElement table = common.returnObject(Common.o.getObject("eleAffinityproducerCodetbl"));
			List<WebElement> rows_table = table.findElements(By.tagName("tr"));
			// int rowCount = rows_table.size();
			// SCRCommon.DataWebTable(Common.o.getObject("eleAffinityproducerCodetbl"),
			// rowCount, 3, sValue[0], "single");
			ExpirationDate = SCRCommon.ReturnOneYearFromDate();
			status = common.SafeAction(Common.o.getObject("eleAffinityprodExpDt"), ExpirationDate, "edt");
			break;
		}
		case "AffinityGroupStartDate": {
			EffectivetDate = SCRCommon.ReturnCurrentDate();
			status = common.SafeAction(Common.o.getObject("edtAffinityGrpStartDt"), EffectivetDate, "edt");
			break;
		}
		case "AffinityGroupEndDate": {
			String End_Date = SCRCommon.ReturnOneYearFromDate();
			status = common.SafeAction(Common.o.getObject("edtAffinityGrpEndDt"), End_Date, "edt");
			break;
		}
		}
		if (!status) {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "User should be able to Enter Date Value", "User not able to Enter Date Value", "FAIL");
			return false;

		} else {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "User should be able to Enter Date Value", "User Entered Date Value", "PASS");
		}
		return status;
	}

	/**
	 * @function
	 * @param svalue
	 * @return
	 * @throws Exception
	 */
	public Boolean AffinityProductsSelect(String svalue) throws Exception {
		Boolean status = false;
		String[] sValue = svalue.split(":::");
		switch (sValue[0]) {
		case "Products": {
			status = common.ActionOnTable(Common.o.getObject("eleAffinityGroup_ProductsSt"), 1, 0, sValue[1], "", "div");
			break;
		}
		case "ProducerCodes": {
			// status =
			// common.SafeAction(Common.o.getObject("eleAffinityGroup_ProducerCodeFieldClick"),
			// "ele", "ele");
			// status =
			// common.ActionOnTable(Common.o.getObject("eleAffinityGroup_ProducerCodesSt"),
			// 1,0, sValue[1], "","div");
			break;
		}
		}

		return status;
	}

	/**
	 * @function
	 * @param strValue
	 * @return
	 * @throws FilloException
	 */
	public Boolean CommertialProgramName(String strValue) throws FilloException {
		HashMap<String, Object> updateColumnNameValues = new HashMap<String, Object>();
		HashMap<String, Object> whereConstraint = new HashMap<String, Object>();
		String tcID = PCThreadCache.getInstance().getProperty("TCID");
		XlsxReader sXL = XlsxReader.getInstance();

		String value1[] = strValue.split("_");
		int value2 = Integer.parseInt(value1[1]) + 1;
		String value = value1[0].concat("_") + value2;

		updateColumnNameValues.clear();
		whereConstraint.clear();
		updateColumnNameValues.put(PCConstants.edtCommertialProgramName, value);
		whereConstraint.put(PCConstants.ID, tcID);
		PCThreadCache.getInstance().setProperty("edtCommertialProgramName", value);
		//sXL.executeUpdateQuery(PCConstants.SHEET_NEWAFFINITYGROUP, updateColumnNameValues, whereConstraint);

		// common.UpdateDataInExcel(strFileName, "Update NewAffinityGroup Set
		// edtCommertialProgramName ='" + value + "' where ID = '" +
		// PCThreadCache.getInstance().getProperty("TCID") + "'");
		String value3[] = PCThreadCache.getInstance().getProperty("TCID").split("TC");
		int value4 = Integer.parseInt(value3[1]) + 1;
		String value5 = "TC" + value4;
		String value6 = strValue + "_upd";

		updateColumnNameValues.clear();
		whereConstraint.clear();
		updateColumnNameValues.put(PCConstants.AffinityGrp_CommPgmName, strValue);
		whereConstraint.put(PCConstants.ID, value5);
		PCThreadCache.getInstance().setProperty("AffinityGrp_CommPgmName", strValue);
		//sXL.executeUpdateQuery(PCConstants.SHEET_SEARCHAFFINITYGROUP, updateColumnNameValues, whereConstraint);

		updateColumnNameValues.clear();
		whereConstraint.clear();
		updateColumnNameValues.put(PCConstants.CommertialProgramName, value6);
		whereConstraint.put(PCConstants.ID, tcID);
		PCThreadCache.getInstance().setProperty("CommertialProgramName", value6);
		//sXL.executeUpdateQuery(PCConstants.SHEET_NEWAFFINITYGROUP, updateColumnNameValues, whereConstraint);

		// common.UpdateDataInExcel(strFileName, "Update SearchAffinityGroup Set
		// AffinityGrp_CommPgmName ='" + strValue + "' where ID = '" + value5 + "'");
		// common.UpdateDataInExcel(strFileName, "Update NewAffinityGroup Set
		// CommertialProgramName ='" + value6 + "' where ID = '" +
		// PCThreadCache.getInstance().getProperty("TCID") + "'");
		return true;
	}

	/**
	 * @function
	 * @param strValue
	 * @return
	 * @throws FilloException
	 */
	public Boolean AffGroupName(String strValue) throws FilloException {
		HashMap<String, Object> updateColumnNameValues = new HashMap<String, Object>();
		HashMap<String, Object> whereConstraint = new HashMap<String, Object>();
		String tcID = PCThreadCache.getInstance().getProperty("TCID");
		XlsxReader sXL = XlsxReader.getInstance();

		String value1[] = strValue.split("_");
		int value2 = Integer.parseInt(value1[1]) + 1;
		String value = value1[0].concat("_") + value2;

		updateColumnNameValues.clear();
		whereConstraint.clear();
		updateColumnNameValues.put(PCConstants.edtAffGroupName, value);
		whereConstraint.put(PCConstants.ID, tcID);
		PCThreadCache.getInstance().setProperty("edtAffGroupName", value);
	//	sXL.executeUpdateQuery(PCConstants.SHEET_NEWAFFINITYGROUP, updateColumnNameValues, whereConstraint);

		// common.UpdateDataInExcel(strFileName, "Update NewAffinityGroup Set
		// edtAffGroupName ='" + value + "' where ID = '" +
		// PCThreadCache.getInstance().getProperty("TCID") + "'");
		String value6 = strValue + "_upd";

		updateColumnNameValues.clear();
		whereConstraint.clear();
		updateColumnNameValues.put(PCConstants.AffGroupName, value6);
		whereConstraint.put(PCConstants.ID, tcID);
		PCThreadCache.getInstance().setProperty("AffGroupName", value6);
		//sXL.executeUpdateQuery(PCConstants.SHEET_NEWAFFINITYGROUP, updateColumnNameValues, whereConstraint);

		// common.UpdateDataInExcel(strFileName, "Update NewAffinityGroup Set
		// AffGroupName ='" + value6 + "' where ID = '" +
		// PCThreadCache.getInstance().getProperty("TCID") + "'");
		return true;
	}

	/**
	 * @function
	 * @param strValue
	 * @return
	 * @throws FilloException
	 */
	public Boolean CPID(String strValue) throws FilloException {
		HashMap<String, Object> updateColumnNameValues = new HashMap<String, Object>();
		HashMap<String, Object> whereConstraint = new HashMap<String, Object>();
		String tcID = PCThreadCache.getInstance().getProperty("TCID");
		XlsxReader sXL = XlsxReader.getInstance();

		String Testval = strValue.substring(0, 1);
		String value1[] = strValue.split(Testval);
		int value2 = Integer.parseInt(value1[1]) + 1;
		String value = Testval + value2;

		updateColumnNameValues.clear();
		whereConstraint.clear();
		updateColumnNameValues.put(PCConstants.AthorityCPID, value);
		whereConstraint.put(PCConstants.ID, tcID);
		PCThreadCache.getInstance().setProperty("AthorityCPID", value);
		//sXL.executeUpdateQuery(PCConstants.SHEET_NEWAFFINITYGROUP, updateColumnNameValues, whereConstraint);

		updateColumnNameValues.clear();
		whereConstraint.clear();
		updateColumnNameValues.put(PCConstants.edtAthorityCPID, value);
		whereConstraint.put(PCConstants.ID, tcID);
		PCThreadCache.getInstance().setProperty("edtAthorityCPID", value);
		//sXL.executeUpdateQuery(PCConstants.SHEET_NEWAFFINITYGROUP, updateColumnNameValues, whereConstraint);

		// common.UpdateDataInExcel(strFileName, "Update NewAffinityGroup Set
		// AthorityCPID ='" + value + "' where ID = '" +
		// PCThreadCache.getInstance().getProperty("TCID") + "'");
		// common.UpdateDataInExcel(strFileName, "Update NewAffinityGroup Set
		// edtAthorityCPID ='" + value + "' where ID = '" +
		// PCThreadCache.getInstance().getProperty("TCID") + "'");
		return true;
	}

	/**
	 * @function
	 * @param strValue
	 * @return
	 */
	public boolean VerifyAffinityGrp(String value) {
		boolean status = false;
		// String value1[] = strValue.split("_");
		// int value2 = Integer.parseInt(value1[1])-1;
		// String value = value1[0].concat("_")+value2;
		By CommPgmName = Common.o.getObject("eleAffinityGroup_CommPgmIden");
		WebElement element = driver.findElement(CommPgmName);
		if (element.getAttribute("value").equals(value)) {
			status = true;
		}
		return status;
	}

	/**
	 * @function use to verify the case results
	 * @param funValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean VerifyResults(String funValue) throws Exception {
		boolean status = false;
		String[] sValue = funValue.split(":::");
		String Value = null;
		switch (sValue[0].toUpperCase()) {
		case "VERIFYCOMPANYNAME":
			logger.info(sValue[0]);
			status = common.ElementExistOrNotTrue("VerifyCompanyName", "Company Name should not be editable", "Company Name should not be editable", (Common.o.getObject("eleVerifyCompany")));
			break;

		case "VERIFYNEWAFFINITYGROUPSCREEN":
			logger.info(sValue[0]);
			status = common.ElementExistOrNotTrue("VerifyCommercialProgramName", "Commercial Program Name should not be editable", "Commercial Program Name should not be editable", (Common.o.getObject("eleVerifyCommmercialProgram")));
			status = common.ElementExistOrNotTrue("VerifyCommercialProgramName", "Affinity Group Name should not be editable", "Affinity Group Name should not be editable", (Common.o.getObject("eleVerifyAffinityGroupName")));
			break;
		}
		if (!status) {
			HTML.fnInsertResult(HTML.properties.getProperty("testcasename"), HTML.properties.getProperty("methodName"), "" + sValue[0] + " case should PASS", "" + sValue[0] + " case should not fail", "FAIL");
			status = true;
		}
		return status;
	}

	public boolean SetCommercialProgramName(String strValue) throws Exception {
		boolean status = false;
		String val;
		Random r = new Random();
		int numbers = 100000 + (int) (r.nextFloat() * 899900);
		val = strValue + String.valueOf(numbers);
		status = common.SafeAction(Common.o.getObject("edtCommertialProgramName"), val, "edt");

		return status;
	}

}