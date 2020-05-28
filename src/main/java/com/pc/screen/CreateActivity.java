package com.pc.screen;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.PCThreadCache;

public class CreateActivity {

	public static String sheetname = "CreateActivity";
	Common common = CommonManager.getInstance().getCommon();
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));

	public Boolean SCRCreateActivity() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

	/**
	 * @function Use to verify the case results
	 * @param funValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean ActivityResults(String funValue) throws Exception {

		boolean status = false;
		String[] sValue = funValue.split(":::");
		String wait = HTML.properties.getProperty("SHORTWAIT");
		Integer x = Integer.valueOf(wait);
		switch (sValue[0].toUpperCase()) {
		case "VERIFYACTIVITYLIST":
			logger.info("Validating the '" + sValue[0] + "'");
			status = ActivityTable(sValue[1]);
			break;

		case "VERIFYACTIVITYCLOSED":
			logger.info("Validating the '" + sValue[0] + "'");
			status = common.ElementExistOrNotFalse("" + sValue[0] + "", "Activity", "Activity", Common.o.getObject

("eleActivityFollowUpList"));
			break;

		case "VERIFYNOTESWARNING":
			logger.info("Validating the '" + sValue[0] + "'");
			String sExpectedViewNotesWarning = sValue[1];
			String sAcutualViewNotesWarning = common.ReadElement(Common.o.getObject("txtViewNoteWarningMsg"), x);
			status = common.CompareStringResult("" + sValue[0] + "", sExpectedViewNotesWarning, sAcutualViewNotesWarning);
			break;

		case "VERIFYCANCELCHECK":
			logger.info("Validating the '" + sValue[0] + "'");
			status = common.ElementExistOrNotFalse("", "Activity Cancel Button should not exist", "Activity Cancel Button is not exist", Common.o.getObject("eleActivityCancel"));
			break;

		case "VERIFYORIGINALACTIVITYDATA":
			logger.info("Validating the '" + sValue[0] + "'");
			break;
		}
		if (status == false) {
			status = true;
		}
		return status;
	}

	/**
	 * @function Use to select the activity from the activity table
	 * @param sFActivitySubject
	 * @return true/false
	 * @throws Exception
	 */
	public boolean ActivityTable(String sFActivitySubject) throws Exception {
		boolean status = false;
		String wait = HTML.properties.getProperty("SHORTWAIT");
		Integer x = Integer.valueOf(wait);
		WebElement tableRowXpathRole = driver.findElement(Common.o.getObject("eleActivityTable"));
		List<WebElement> rows_table1 = tableRowXpathRole.findElements(By.tagName("tr"));
		int rows_count1 = rows_table1.size();
		for (int i = 1; i <= rows_count1; i++) {
			String sActvitySubject = common.ReadElement(By.xpath(".//*[@id='AccountFile_Summary:AccountFile_SummaryScreen:AccountFile_Summary_ActivitiesLV-body']/div/table/tbody/tr['+i+']/td[3]"), x);
			if (sActvitySubject.equals(sFActivitySubject)) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Activity should Exist", "Activity Exist", "PASS");
				status = true;
				break;
			}
		}
		if (status == false) {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Activity should Exist", "Activity Not Exist", "FAIL");
			status = false;
		}
		return status;
	}

	// public boolean OriginalActivity(String sFClose)
	// {
	// return true;
	// }
}