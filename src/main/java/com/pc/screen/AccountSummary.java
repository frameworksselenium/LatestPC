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

public class AccountSummary {

	public static String sheetname = "AccountSummary";
	//static Logger logger = Logger.getLogger(sheetname);
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();

	public Boolean SCRAccountSummary() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

	public boolean VerifyResults(String funValue) throws Exception {
		boolean status = false;
		logger.info("Verify the Results");
		String[] sValue = funValue.split(":::");
		String Value = null;
		switch (sValue[0].toUpperCase()) {
		case "VERIFYEXPERIAN":
			logger.info(sValue[0]);
			Value = common.ReadElement(Common.o.getObject("eleAccount_ExperianKey"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("PolicyExperian", sValue[1], Value);
			break;
		case "STATUSCHECK":
			logger.info(sValue[0]);
			Value = common.ReadElement(Common.o.getObject("eleNewSubmission"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("VerifyStatus", sValue[1], Value);
			break;
		case "VERIFY_POLICY_FLAG":
			logger.info(sValue[0]);
			// status=common.ActionOnTable(common.o.getObject("eleAccountSummary_PolicyTermsTbl"),
			// 8, 0, "In Force", "img");
			// status =
			// common.ActionOnTable(common.o.getObject("eleAccountSummary_PolicyTermsTbl"),
			// 8, 0, "In Force","img");
			Value = common.GetTextFromTable(Common.o.getObject("eleAccountSummary_PolicyTermsTbl"), 8, 1, "In Force");
			status = common.ActionOnTable(Common.o.getObject("eleAccountSummary_PolicyTermsTbl"), 1, 0, Value, "img");
			break;
		case "VERIFY_POLICY_FLAG_DOESNT_EXIST":
			logger.info(sValue[0]);
			// status=common.ActionOnTable(common.o.getObject("eleAccountSummary_PolicyTermsTbl"),
			// 8, 0, "In Force", "img");
			// status =
			// common.ActionOnTable(common.o.getObject("eleAccountSummary_PolicyTermsTbl"),
			// 8, 0, "In Force","img");
			Value = common.GetTextFromTable(Common.o.getObject("eleAccountSummary_PolicyTermsTbl"), 8, 1, "In Force");
			status = ElementExistonTable(Common.o.getObject("eleAccountSummary_PolicyTermsTbl"), 1, 0, Value, "img");
			if (!status) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Verify the FLAG icon on Policy Term Section", "Flag Icon is NOT Displayed on Policy Term Section", "PASS");
				status = true;
			} else {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Verify the FLAG icon on Policy Term Section", "Flag Icon is Displayed on Policy Term Section", "FAIL");
				status = false;
			}
			break;
		case "VERIFYRESULT":
			logger.info(sValue[0]);
			Value = common.ReadElement(Common.o.getObject(sValue[1]), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			if (Value != null) {
				status = true;

			}
			break;
		case "VERIFYPOLICYTERMS":
			logger.info(sValue[0]);
			// value =
			// common.GetTextFromTable1(common.o.getObject("eleAccountSummary_PolicyTermsTbl"),
			// 7,12,sValue[1]);
			Value = common.GetTextFromTable(Common.o.getObject("eleAccountSummary_PolicyTermsTbl"), 8, 12, sValue[1]);
			status = common.CompareStringResult("verify display of biling Number", sValue[2], Value);
			if (sValue[3].toUpperCase().equalsIgnoreCase("SPAN") || sValue[3].toUpperCase().equalsIgnoreCase("DIV")) {
				status = common.ActionOnTable(Common.o.getObject("eleAccountSummary_PolicyTermsTbl"), 12, 12, sValue[2], sValue

[3].toLowerCase());
				if (status) {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Billing Number '" + sValue[2] + "' should display without hyperlink", "'" + sValue[0] + "' displayed without hyperlink", "PASS");
				} else {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Billing Number '" + sValue[2] + "' should display without hyperlink", "'" + sValue[0] + "' NOT displayed without hyperlink", "FAIL");
				}
			} else if (sValue[3].toUpperCase().equalsIgnoreCase("LINK")) {
				status = common.ActionOnTable(Common.o.getObject("eleAccountSummary_PolicyTermsTbl"), 12, 12, sValue[2], "a");
				if (status) {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Billing Number '" + sValue[2] + "' should display with hyperlink", "'" + sValue[0] + "' displayed with hyperlink", 

"PASS");
				} else {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Billing Number '" + sValue[2] + "' should display with hyperlink", "'" + sValue[0] + "' NOT displayed with hyperlink", "FAIL");
				}
			}
			break;

		case "VERIFYPOLICYTRANSACTION":
			logger.info(sValue[0]);
			// Value =
			// common.GetTextFromTable(Common.o.getObject("eleAccountSummary_PolicyTranTbl"),
			// 5,8,sValue[1]);
			// modified as per LTI changes
			Value = common.GetTextFromTable(Common.o.getObject("eleAccountSummary_PolicyTranTbl"), 6, 9, sValue[1]);
			status = common.CompareStringResult("verify display of biling Number", sValue[2], Value);
			if (sValue[3].toUpperCase().equalsIgnoreCase("SPAN") || sValue[3].toUpperCase().equalsIgnoreCase("DIV")) {
				status = common.ActionOnTable(Common.o.getObject("eleAccountSummary_PolicyTranTbl"), 9, 9, sValue[2], sValue

[3].toLowerCase());
				if (status) {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Billing method '" + sValue[2] + "' should display without hyperlink", "'" + sValue[0] + "' displayed without hyperlink", "PASS");
				} else {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Billing method '" + sValue[2] + "' should display without hyperlink", "'" + sValue[0] + "' NOT displayed without hyperlink", "FAIL");
				}
			} else if (sValue[3].toUpperCase().equalsIgnoreCase("LINK")) {
				status = common.ActionOnTable(Common.o.getObject("eleAccountSummary_PolicyTranTbl"), 9, 9, sValue[2], "a");
				if (status) {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Billing method '" + sValue[2] + "' should display with hyperlink", "'" + sValue[0] + "' displayed with hyperlink", 

"PASS");
				} else {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Billing method '" + sValue[2] + "' should display with hyperlink", "'" + sValue[0] + "' NOT displayed with hyperlink", "FAIL");
				}
			} else {
				status = false;
			}
			break;
		case "VERIFY_XACTPAY_ERR": // changed to manage sheel policy
			logger.info("Verification started for  '" + sValue[0] + "'");
			// status=common.ElementExistOrNotTrue("Element exist", "XAct Pay Launch
			// screen", "XAct Pay Launch screen dispalyed as expected",
			// Common.o.getObject("eleXactPayLaunchTitle"));
			Value = common.ReadElement(Common.o.getObject("elePCVerifyErrorMessages"), Integer.valueOf(HTML.properties.getProperty

("NORMALWAIT")));
			status = common.CompareStringResult("Account Summary- XAct Pay Error message", sValue[1], Value);
			break;
		case "VERIFYPOLICYADDEDACTIVITY":
			logger.info(sValue[0]);
			status = common.ActionOnTable(Common.o.getObject("eleActivityTable"), 2, 2, sValue[1], "a");
			Value = common.ReadElement(Common.o.getObject("eleActivity_AssignTo"), Integer.valueOf(HTML.properties.getProperty

("NORMALWAIT")));
			status = common.CompareStringResult("Activity - Assign to", sValue[2], Value);
			Value = common.ReadElement(Common.o.getObject("eleActivity_ActivityDesc"), Integer.valueOf(HTML.properties.getProperty

("NORMALWAIT")));
			status = common.CompareStringResult("Activity - Description", sValue[3], Value);
			Value = common.ReadElement(Common.o.getObject("eleActivity_ActivitySubject"), Integer.valueOf

(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("Activity - Subject", sValue[4], Value);

			break;
		case "CHECKACTIVITY":
			logger.info("Validating the '" + sValue[0] + "'");
			// Value =
			// common.GetTextFromTable(Common.o.getObject("eleShellSummaryActivityTable"),
			// 2, sValue[1]);
			// status = common.CompareStringResult(sValue[0], sValue[1], Value);
			// By sActivityElement = By.xpath(".//a[contains(text(),'"+sValue[1]+"')]");
			By sActivityElement = By.id("AccountFile_Summary:AccountFile_SummaryScreen:AccountFile_Summary_ActivitiesLV-body");
			status = common.ElementExistOrNotTrue("ActivityCheck", "'" + sValue[1] + "' actvity should be created", "'" + sValue[1] + "' actvity is creatred", sActivityElement);
			break;
		case "CHECKACTIVITYCLA":
			logger.info("Validating the '" + sValue[0] + "'");
			Value = common.GetTextFromTable(Common.o.getObject("eleActivityTable"), 2, sValue[1]);
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			// By sActivityElement = By.xpath(".//a[contains(text(),'"+sValue[1]+"')]");
			// By sActivityElement = By.xpath("//a[@class='g-actionable' and
			// contains(text(),'"+sValue[1]+"')]");
			// status = common.ElementExistOrNotTrue("ActivityCheck", "'"+sValue[1]+"'
			// actvity should be created", "'"+sValue[1]+"' actvity is creatred",
			// sActivityElement);
			break;

		}
		if (!status) {
			logger.info("" + sValue[0] + " Fails");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "" + sValue[0] + " case should PASS", "" + sValue[0] + " case should not fail", "FAIL");
			status = true;
		}
		return status;
	}

	/**
	 * @function This function use to verify element in Table
	 * @param obj,readCol,actionCol,strReadString,actionObjetName
	 * @return status
	 * @throws Exception
	 */
	public static Boolean ElementExistonTable(By obj, int readCol, int actionCol, String strReadString, String sTagName) throws Exception {
		boolean Status = false;
		boolean SearchString = false;
		// boolean ActionObject=false;
		WebElement mytable = ManagerDriver.getInstance().getWebDriver().findElement(obj);
		List<WebElement> allrows = mytable.findElements(By.tagName("tr"));
		for (int i = 0; i <= allrows.size() - 1; i++) {
			List<WebElement> Cells = allrows.get(i).findElements(By.tagName("td"));
			String readText = Cells.get(readCol).getText();
			if (readText.contains(strReadString)) {
				SearchString = true;
				List<WebElement> CellElements = Cells.get(actionCol).findElements(By.tagName(sTagName));
				for (WebElement element : CellElements) {
					// String objName = element.getText();
					// if(objName.contains(actionObjetName))
					// {
					// Status = true;
					// ActionObject = true;
					Status = element.isDisplayed();
					break;
					// }
				}
			}
			if (Status == true) {
				break;
			}
		}
		if (SearchString) {
			//logger.info("Search String available in the table. '" + strReadString + "'");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "System should search string in table and search string is '" + strReadString + "'", "System searched string in table and searched string is  '" + strReadString + "'", "PASS");
		} else {
			//logger.info("Search String not available in the table. '" + strReadString + "'");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "System should search string in table and search string is '" + strReadString + "'", "System searched string in table and searched string is  '" + strReadString + "'", "FAIL");
			Status = false;
		}
		return Status;
	}

	/**
	 * @function use to select policy transaction from the account file summary
	 *           screen
	 * @param sReadString
	 * @return true/false
	 * @throws Exception
	 */
	public boolean SelectPolicyTable(String sReadString) throws Exception {
		boolean status = false;
		status = common.ActionOnTable(Common.o.getObject("PolicyTermTable"), 7, 1, sReadString, "a");
		return status;
	}

	/**
	 * @function use to select policy term from the account file summary screen
	 * @param sReadString
	 * @return true/false
	 * @throws Exception
	 */
	public boolean SelectPolicyTab(String sReadString) throws Exception {
		boolean status = false;
		status = common.ActionOnTable(Common.o.getObject("eleAccountSummary_PolicyTermsTbl"), 5, 1, "", "div");
		return status;
	}

	/*
	 * public boolean openActivity (String activityName) throws Exception { boolean
	 * activityExist = false; boolean status = false; String readText = null;
	 * WebElement mytable =
	 * ManagerDriver.getInstance().getWebDriver().findElement(Common.o.getObject(
	 * "eleActivityTable")); List<WebElement> allrows =
	 * mytable.findElements(By.tagName("tr")); for(int i = 0; i < allrows.size();
	 * i++) { List<WebElement> Cells =
	 * allrows.get(i).findElements(By.tagName("td")); readText =
	 * Cells.get(2).getText(); if(readText.contains(activityName)) {
	 * logger.info("Activity '"+ activityName+"' found in summary page");
	 * activityExist = true; WebElement actionEle =
	 * Cells.get(2).findElement(By.tagName("a")); String
	 * sAttributeId=actionEle.getAttribute("id"); status =
	 * common.SafeAction(By.id(sAttributeId),"ele","ele"+ activityName); break; }
	 * 
	 * } if (activityExist) {
	 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
	 * PCThreadCache.getInstance().getProperty("methodName"), "Activity '" +
	 * activityName + "' should avilable in Activitty table", "Activity string '" +
	 * activityName + "' is avilable in the Activitytable", "PASS"); } else {
	 * //logger.info("Search String not available in the table. '" + strReadString +
	 * "'");
	 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
	 * PCThreadCache.getInstance().getProperty("methodName"), "Activity '" +
	 * activityName + "' should avilable in Activitty table", "Activity string '" +
	 * activityName + "' is NOT avilable in the Activitytable", "FAIL");
	 * activityExist = false; }
	 * 
	 * return activityExist; }
	 */
	/**
	 * @function used to shift browser
	 **/

	public Boolean ProducerWindow(String funValue) throws Exception {
		boolean status = false;
		String sPrimaryWindow = driver.getWindowHandle();
		java.util.Set<String> allWindowHandles = driver.getWindowHandles();
		for (int i = 0; i <= 20; i++) {
			if (i == 20)
				status = true;
		}
		for (String handle : allWindowHandles) {
			if (status == true && driver.switchTo().window(handle).getTitle().contains(funValue)) {
				logger.info("New window is displayed and swithced to that window");
				break;
			}
		}
		if (status) {
			// close the child window
			driver.close();
			// Switch to the parent window
			driver.switchTo().window(sPrimaryWindow);
		} else {
			status = false;
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Switch to New Window", "Could not switch to New Window", "FAIL");
		}
		return status;
	}

	public boolean openActivity(String activityName) throws Exception {
		Boolean blnStatus = false;
		blnStatus = common.ActionOnTable(Common.o.getObject("eleActivityTable"), 2, 2, activityName, "a");
		return blnStatus;
	}

}