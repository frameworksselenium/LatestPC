
/**
 * @ClassPurpose Access all the data's for driver inforamtion sheet
 * @Scriptor Sojan David
 * @ReviewedBy
 * @ModifiedBy Ratish Kuppusamy
 * @LastDateModified 3/21/2017
 */
package com.pc.screen;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.PCThreadCache;

public class DriverInformation {

	public static String sheetname = "DriverInformation";
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();

	public Boolean SCRDriverInformation() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

	/**
	 * @function Use to verify the results for the driver information usecase
	 * @param funValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean VerifyResults(String funValue) throws Exception {
		boolean status = false;
		String[] sValue = funValue.split(":::");
		String Value = null;
		switch (sValue[0].toUpperCase()) {
		case "MANDATORYWARNING":
			Value = common.ReadElement(Common.o.getObject("txtFirstNameWarningandDOB"), Integer.valueOf(HTML.properties.getProperty

("NORMALWAIT")));
			status = common.CompareStringResult("FirstNameWarning", sValue[1], Value);
			Value = common.ReadElement(Common.o.getObject("txtLastNameWarning"), Integer.valueOf(HTML.properties.getProperty

("NORMALWAIT")));
			status = common.CompareStringResult("LastNameWarning", sValue[2], Value);
			Value = common.ReadElement(Common.o.getObject("txtLicenceNumber"), Integer.valueOf(HTML.properties.getProperty

("NORMALWAIT")));
			status = common.CompareStringResult("LicenceNumberWarning", sValue[3], Value);
			Value = common.ReadElement(Common.o.getObject("txtLicenceState"), Integer.valueOf(HTML.properties.getProperty

("NORMALWAIT")));
			status = common.CompareStringResult("LicenceStateWarning", sValue[4], Value);
			break;
		case "MANDATORYWARNINGDOB":
			Value = common.ReadElement(Common.o.getObject("txtFirstNameWarningandDOB"), Integer.valueOf(HTML.properties.getProperty

("NORMALWAIT")));
			status = common.CompareStringResult("DOBWarning", sValue[1], Value);
			break;
		case "LICENCENUMEBRWARNING":
			Value = common.ReadElement(Common.o.getObject("txtFirstNameWarningandDOB"), Integer.valueOf(HTML.properties.getProperty

("NORMALWAIT")));
			status = common.CompareStringResult("LicenceNumberApropriateWarning", sValue[1], Value);
			break;
		case "CLICKDRIVERINFORECORD":
			status = DriverDataTable(4, sValue[1], "a");
			break;
		case "READINGTABLEVALIDATION":
			Value = DriverDataTable(sValue[1], 4);
			status = common.CompareStringResult("UpdateShouldNotHappen", sValue[1], Value);
			break;
		case "LICENCENUMEBRWARNINGUPDATE":
			Value = common.ReadElement(Common.o.getObject("eleCLADvrAddDriverFirstRecord"), Integer.valueOf

(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("UpdateLicenceNumberApropriateWarning", sValue[1], Value);
			break;
		case "CHECKDEFAULTVALUESACTIVESTATUSANDDATEADDED":
			Value = DriverDataTable(sValue[1], 1);
			status = common.CompareStringResult("CompareActiveStatus", sValue[1], Value);
			Value = DriverDataTable(SCRCommon.ReturnCurrentDate(), 15);
			status = common.CompareStringResult("DateAdded", SCRCommon.ReturnCurrentDate(), Value);
			Value = DriverDataTable(sValue[2], 10);
			status = common.CompareStringResult("OrderStatus", sValue[2], Value);
			Value = DriverDataTable("", 13);
			status = common.CompareStringResult("Description", " ", Value);
			break;
		case "FIRSTNAMELASTNAMEWARNING":
			Value = common.ReadElement(Common.o.getObject("eleCLADvrAddDriverFirstRecord"), Integer.valueOf

(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("FirstNameLastNameWarning", sValue[1], Value);
			break;
		case "DOBWARNING":
			Value = common.ReadElement(Common.o.getObject("eleCLADvrAddDriverFirstRecord"), Integer.valueOf

(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("DOBWarnings", sValue[1], Value);
			break;
		case "REMOVEDRIVERWARNING":
			Value = common.ReadElement(Common.o.getObject("txtPopUpWindowMsg"), Integer.valueOf(HTML.properties.getProperty

("NORMALWAIT")));
			status = common.CompareStringResult("RemoveDriver", sValue[1], Value);
			break;
		case "REMOVEDRIVER":
			status = common.ElementExistOrNotFalse("DriverInformationCheck", "The Driver Information should be removed", "The Driver information remvoed", Common.o.getObject("eleCheckRemvoedDriverPresent"));
			// Value = DriverDataTable(sValue[1], 4);
			// status = Common.CompareStringResult("RemoveDriver", "", Value);
			break;
		case "DUPLICATEDRIVERDETAILS":
			Value = common.ReadElement(Common.o.getObject("txtFirstNameWarningandDOB"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("DuplicateDriverInfoCheck", sValue[1], Value);
			break;
		case "CLICKDRIVERINFO":
			status = DriverDataTable(1, sValue[1], "div");
			break;
		case "DATEFIRSTLICENCEDWARNING":
			Value = common.ReadElement(Common.o.getObject("eleCLADvrAddDriverFirstRecord"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("DateFirstLicencedWarning", sValue[1], Value);
			break;
		case "ACTIVETOINACTIVE":
			Value = common.ReadElement(Common.o.getObject("txtInactiveEffectiveDate"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("ActiveToInactiveEffectiveDate", SCRCommon.ReturnCurrentDate(), Value);
			break;
		case "INACTIVETOACTIVE":
			Value = common.ReadElementforODS(Common.o.getObject("txtInactiveEffectiveDate"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("ActiveToInactiveEffectiveDate", "", Value);
			break;
		case "VERIFYSUPRESS":
			Value = common.GetTextFromTable(Common.o.getObject("eleDriverInformationRecordTable"), 4, 2, sValue[1]);
			status = common.CompareStringResult(sValue[0], sValue[2], Value);
			break;
		case "VERIFYDRIVERALERT":
			Value = common.ReadElement(Common.o.getObject("eleCommonZeroResults"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("DriverInactiveStatus", sValue[1], Value);
			break;
		// case "VerifyLessthan12Months":
		case "VERIFYLESSTHAN12MONTHS":
			Value = common.ReadElement(Common.o.getObject("eleCommonZeroResults"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("Lessthan12Months", sValue[1], Value);
			break;
		case "VERIFYERRORMESSAGE":
			Value = common.ReadElement(Common.o.getObject("eleCommonZeroResults"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
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
	 * @function Use to click the element in the table
	 * @param sReadActionCol
	 * @param sReadString
	 * @return true/false
	 * @throws Exception
	 */
	public boolean DriverDataTable(int sReadActionCol, String sReadString, String sTagname) throws Exception {
		boolean status = false;
		status = common.ActionOnTable(Common.o.getObject("eleDriverInformationRecordTable"), 4, sReadActionCol, sReadString, sReadString, 

sTagname);
		return status;
	}

	/**
	 * @function Use to read the string from the table
	 * @param sReadString
	 * @param sReadCol
	 * @return String
	 * @throws Exception
	 */
	public String DriverDataTable(String sReadString, int sReadCol) throws Exception {
		String sValue = null;
		sValue = common.GetTextFromTable(Common.o.getObject("eleDriverInformationRecordTable"), sReadCol, sReadString);
		return sValue;
	}

	/**
	 * @function Use to select the drivers in the table list
	 * @param sReadString
	 * @return true/false
	 * @throws Exception
	 */
	public boolean SelectDriverFromTable(String sReadString) throws Exception {
		boolean status = false;
		status = common.ActionOnTable(Common.o.getObject("eleDriverInformationRecordTable"), 4, 0, sReadString, "", "div");
		return status;
	}

	/**
	 * @function use to upload the file using the AUTOIT
	 * @return true/false
	 * @throws Exception
	 */
	public boolean UploadSpecifiedFile() throws Exception {
		boolean status = false;
		// String[] sValue = value.split(":::");

		try {
			WebElement ele1 = driver.findElement(Common.o.getObject("eleBrowseButton"));
			ele1.click();

			String Value = "C:/Users/CPG2215/Documents/driver.exe";
			Thread.sleep(2000);
			Runtime.getRuntime().exec(Value);

		} catch (Exception e) {//
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * @function This function is used to select a driver from table using Last Name
	 * @param sReadString
	 * @return true/false
	 * @throws Exception
	 */
	public boolean SelectLastName(String sReadString) throws Exception {
		boolean status = false;
		status = common.ActionOnTable(Common.o.getObject("eleDriverInformationRecordTable"), 3, 0, sReadString, "", "div");
		return status;
	}

}