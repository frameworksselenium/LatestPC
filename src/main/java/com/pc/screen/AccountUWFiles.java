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

public class AccountUWFiles {

	public static String sheetname = "AccountUWFiles";
	Common common = CommonManager.getInstance().getCommon();
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();
	//static Logger logger = Logger.getLogger(sheetname);
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));

	public Boolean SCRAccountUWFiles() throws Exception {

		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			return false;
		}
		String sScreenToNavigate = common.getSpecifiedExcelValue(sheetname, "NaviToScreen");

		if (sScreenToNavigate.equals("New Submission")) {
			if (common.WaitForElementExist(Common.o.getObject("eleNewSubmissionTitle"), Integer.valueOf(HTML.properties.getProperty

("VERYLONGWAIT")))) {
				logger.info("System displayed New Submission Page");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should display New Submission Page", "System displayed New Submission Page", "PASS");
				status = true;
			} else {
				logger.info("System not displayed New Submission Page");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should display New Submission Page", "System not displayed New Submission Page", "FAIL");
				status = false;
			}
		} else if (sScreenToNavigate.equals("Submission Manager")) {
			if (common.WaitForElementExist(Common.o.getObject("eleASMSubmissionManagerTitle"), Integer.valueOf

(HTML.properties.getProperty("LONGWAIT")))) {
				logger.info("System displayed Submission Manager");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should display Submission Manager Page", "System displayed Submission Manager Page", "PASS");
				status = true;
			} else {
				logger.info("System not displayed Submission Manager Page");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should display Submission Manager Page", "System not displayed Submission Manager Page", "FAIL");
				status = false;
			}
		}
		return status;
	}

	/**
	 * @function This function to Verify UW Files Results
	 * @param sValue(SUBMISSION
	 *            ONLY,RENEWAL ONLY)
	 * @return status
	 * @throws Exception
	 */
	public boolean VerifyUWFileStartsWithText(String sValue) throws Exception {
		boolean status = false;
		try {
			WebElement mytable = driver.findElement(Common.o.getObject("eleUWFile"));
			if (driver.findElement(Common.o.getObject("eleUWFile")).getText().startsWith(sValue)) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should display text starting with ", "It is starting with the Expected the text", "PASS");
				status = true;
			}

			else {

				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should display text starting with ", "It is not starting with the Expected the text", "FAIL");
				status = true;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	public boolean VerifyUWFileResults(String sValue) throws Exception {
		boolean status = false;
		try {
			WebElement mytable = driver.findElement(Common.o.getObject("eleUWFilesSearchResults"));
			List<WebElement> allrows = mytable.findElements(By.tagName("tr"));
			if (allrows.size() == 0) {
				if (sValue.equalsIgnoreCase("SUBMISSION ONLY")) {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should display Submission Details", "Submission is not available for this account", "FAIL");

				} else if (sValue.equalsIgnoreCase("RENEWAL ONLY")) {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should display Renewal Details", "Renewal is not available for this account", "FAIL");
				}

			} else {
				if (sValue.equalsIgnoreCase("SUBMISSION ONLY")) {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should display Submission Details", "Submissions are available for this account", "PASS");
					status = true;
				} else if (sValue.equalsIgnoreCase("RENEWAL ONLY")) {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should display Renewal Details", "Renewals are available for this account", "PASS");
					status = true;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * @function Verifying the results
	 * @param funValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean VerifyResults(String funcValue) throws Exception {
		boolean status = false;
		String Value = null;
		String[] sValue = funcValue.split(":::");
		logger.info("Validating the Results");
		switch (sValue[0].toUpperCase()) {
		case "VERIFYUWACTIVITY":
			logger.info("Validating the '" + sValue[0] + "'");
			Value = common.GetTextFromTable(Common.o.getObject("eleUWActivityTable"), 8, 8, sValue[1]);
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			break;
		}
		if (status == false) {
			HTML.fnInsertResult(HTML.properties.getProperty("testcasename"), HTML.properties.getProperty("methodName"), "" + sValue

[0] + " case should PASS", "" + sValue[0] + " case fail", "FAIL");
			status = true;
		}
		return status;
	}
}