
/**
 * @ClassPurpose This Class used for the Activity Pattern Screen
 * @Scriptor Sojan
 * @ReviewedBy
 * @ModifiedBy Sojan
 * @LastDateModified 3/24/2017
 */
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

public class ActivityPatternVerification {

	public static String sheetname = "ActivityPatternVerification";
	Common common = CommonManager.getInstance().getCommon();
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();
	//static Logger logger = Logger.getLogger(sheetname);
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));

	/**
	 * @function Use to perform all the objects/elements/functions from the
	 *           ActivityPatternVerification excel sheet
	 * @return true/false
	 * @throws Exception
	 */
	public Boolean SCRActivityPatternVerification() throws Exception {
		String sheetname = "ActivityPatternVerification";
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

	/**
	 * @function Verify the results of Activity Pattern
	 * @param funValue
	 * @return status
	 * @throws Exception
	 **/
	public boolean ActivityPatternResults(String funValue) throws Exception {
		boolean status = false;
		String[] sValue = funValue.split(":::");
		String wait = HTML.properties.getProperty("SHORTWAIT");
		Integer x = Integer.valueOf(wait);
		String sActualValue, sActualValue1, sActualValue2, sActualValue3, sActualValue4 = null;
		switch (sValue[0].toUpperCase()) {
		case "VERIFYCREATEDACTIVITYPATTERN":
			logger.info("Validating the '" + sValue[0] + "'");
			status = CheckingCreatedActivityPattern(sValue[0], sValue[1], sValue[2]);
			break;

		case "VERIFYCANCEL":
			logger.info("Validating the '" + sValue[0] + "'");
			status = common.ElementExistOrNotTrue(sValue[0], "Cancel should work fine", "Cancel is working fine", Common.o.getObject("eleNewActivityPattern"));
			break;

		case "VERIFYACTIVITYDELETED":
			logger.info("Validating the '" + sValue[0] + "'");
			status = CheckingCreatedActivityPattern(sValue[0], sValue[1], sValue[2]);
			break;

		case "VERIFYMANDATORYWARNIGNALERT":
			logger.info("Validating the '" + sValue[0] + "'");
			sActualValue = common.ReadElement(Common.o.getObject("txtSubjectMandatoryWarningMessage"), x);
			sActualValue1 = common.ReadElement(Common.o.getObject("txtCodeMandatoryWarningMessage"), x);
			sActualValue2 = common.ReadElement(Common.o.getObject("txtPatternLevelMandatoryWarningMessage"), x);
			status = common.CompareStringResult(sValue[0], sValue[1], sActualValue);
			status = common.CompareStringResult(sValue[0], sValue[2], sActualValue1);
			status = common.CompareStringResult(sValue[0], sValue[3], sActualValue2);
			break;

		case "VERIFYRECURRINGPRIORITYTYPECATEGORYBUSINESSGROUPING":
			logger.info("Validating the '" + sValue[0] + "'");
			sActualValue = common.ReadElement(Common.o.getObject("txtRecurring"), x);
			sActualValue1 = common.ReadElement(Common.o.getObject("txtPriority"), x);
			sActualValue2 = common.ReadElement(Common.o.getObject("txtCategory"), x);
			sActualValue3 = common.ReadElement(Common.o.getObject("txtType"), x);
			// sActualValue4 = common.ReadElement(Common.o.getObject("txtBusinessGrouping"),
			// x);
			status = common.CompareStringResult(sValue[0], sValue[1], sActualValue);
			status = common.CompareStringResult(sValue[0], sValue[2], sActualValue1);
			status = common.CompareStringResult(sValue[0], sValue[3], sActualValue2);
			status = common.CompareStringResult(sValue[0], sValue[4], sActualValue3);
			// status = common.CompareStringResult(sValue[0], sValue[5], sActualValue4);
			break;

		case "VERIFYBISNESSSEGMENTTRACKLEVELTRACK":
			logger.info("Validating the '" + sValue[0] + "'");
			sActualValue = common.ReadElement(Common.o.getObject("txtBusinessSegment"), x);
			sActualValue1 = common.ReadElement(Common.o.getObject("txtSkillLevel"), x);
			sActualValue2 = common.ReadElement(Common.o.getObject("txtTrack"), x);
			status = common.CompareStringResult(sValue[0], sValue[1], sActualValue);
			status = common.CompareStringResult(sValue[0], sValue[2], sActualValue1);
			status = common.CompareStringResult(sValue[0], sValue[3], sActualValue2);
			break;

		case "VERIFYBUSINESSSEGMENTREMOVED":
			logger.info("Validating the '" + sValue[0] + "'");
			status = common.ElementExistOrNotFalse(sValue[0], "Buiness Segment Table should be removed", "Buiness Segment Table is removed", Common.o.getObject("eleBSTableExist"));
			break;
		}
		/*
		 * if(status == false) { status = true; }
		 */
		return status;
	}

	/**
	 * @function Ability to search newly created activity in the list
	 * @param fValue
	 *            is for Subject
	 * @param sCase
	 *            is for select case name
	 * @return status
	 * @throws Exception
	 **/
	public boolean CheckingCreatedActivityPattern(String fCase, String fOption, String fValue) throws Exception {
		boolean status = false;
		String sSubject = null;
		WebElement tableRowXpathRole = driver.findElement(Common.o.getObject("eleNewActivityPatternListTable"));
		List<WebElement> rows_table1 = tableRowXpathRole.findElements(By.tagName("tr"));
		int rows_count1 = rows_table1.size();
		switch (fOption.toUpperCase()) {
		case "CREATEACTIVITYPATTERN":
			for (int i = 1; i <= rows_count1; i++) {
				sSubject = driver.findElement(By.xpath(".//*[@id='ActivityPatterns:ActivityPatternsScreen:ActivityPatternsLV-body']/div/table/tbody/tr[" + i + "]/td[2]")).getText();
				if (sSubject.contains(fValue)) {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "" + fCase + ": '" + fValue + "' should match", "" + fCase + ": '" + sSubject + "' is matching", "PASS");
					status = true;
					break;
				}
			}
			break;

		case "DELETEACTIVITYPATTERN":
			for (int i = 1; i <= rows_count1; i++) {
				sSubject = driver.findElement(By.xpath(".//*[@id='ActivityPatterns:ActivityPatternsScreen:ActivityPatternsLV-body']/div/table/tbody/tr[" + i + "]/td[2]")).getText();
				if (sSubject.contains(fValue)) {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "" + fCase + ": '" + fValue + "' should not match", "" + fCase + ": '" + sSubject + "' is matching", "FAIL");
					status = false;
				}
			}
			if (status == false) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "" + fCase + ": '" + fValue + "' should not match", "" + fCase + ": It is not matching", "PASS");
				status = true;
			}
			break;
		}
		if (status == false) {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "" + fCase + ": '" + fValue + "' should match", "" + fCase + ": '" + sSubject + "' is not matching", "FAIL");
		}
		return status;
	}
}