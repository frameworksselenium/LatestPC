package com.pc.screen;

import java.io.IOException;
import java.util.ArrayList;
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

public class BBTGuidedWorkFlow {

	public static String sheetname = "BBTGuidedWorkFlow";
	Common common = CommonManager.getInstance().getCommon();
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();
	public static String JobNumber;
	public static String BBTStatus;

	public Boolean SCRBBTGuidedWorkFlow() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);

		if (!status) {
			status = false;
		}
		return status;
	}

	/**
	 * @function this is used to navigate to the maintainence transaction
	 *           screen->guided workflow
	 * @param strActions
	 * @return boolean
	 * @throws IOException
	 */

	public boolean NavigatetoGuidedWrkFlw_MT(String strActions) throws IOException {
		boolean status = false;
		String[] arrAction = strActions.split(":::");
		try {
			status = common.SafeAction(common.o.getObject(arrAction[0]), "", "ele");

			status = common.SafeAction(common.o.getObject(arrAction[1]), "", "ele");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return status;

	}

	/**
	 * @function Verify the warning and error messages on screen
	 * @param sValue
	 * @return boolean
	 * @throws IOException
	 */
	public static boolean VerifyWarning_ErrorMessages(String sValue) throws IOException {
		loggers  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
		boolean status = true;
		String expectedText = null;
		String refClassName = null;
		String[] arrMessgage = sValue.split("::");
		String[] expWarnignMsg = arrMessgage[1].split(":::");
		List<String> a = new ArrayList<String>();
		// ArrayList actWarningMsg=new ArrayList();
		List<WebElement> CellElements = ManagerDriver.getInstance().getWebDriver().findElements(Common.o.getObject("eleErrorMsgs"));
		if (arrMessgage[0].toUpperCase().contentEquals("ERROR")) {
			refClassName = "error_icon";

		} else if (arrMessgage[0].toUpperCase().contentEquals("WARNING")) {
			refClassName = "warning_icon";
		}
		if (CellElements.size() == 0) {
			loggers.info("No Error/Warning messages in screen to validate");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Expected error text should matching with actual text '" + expectedText + "'", "No Error/Warning messgae in the screen to validate", "FAIL");

		}
		for (int i = 0; i <= CellElements.size() - 1; i++) {

			List<WebElement> Cells = CellElements.get(i).findElements(By.className(refClassName));
			if (Cells.size() != 0) {
				String actWarningMsg = CellElements.get(i).getText();
				a.add(actWarningMsg);
				loggers.info(actWarningMsg);

			}
		}

		// check for Warnging in UI list

		for (String expText : expWarnignMsg) {
			boolean matchStatus = false;
			expectedText = expText.toString();
			for (String actText : a) {
				if (actText.equals(expText)) {
					matchStatus = true;
					loggers.info("Expected Warning Message  is matching with actual message '" + expectedText + "'");
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Expected error text should matching with actual text '" + expectedText + "'", "Expected error text is matching with actual text '" + expectedText + "'", "PASS");
					break;
				}
			}

			if (matchStatus == false) {
				loggers.info("Expected Warning Message is not matching with actual message '" + expectedText + "'");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Expected error text should matching with actual text '" + expectedText + "'", "Expected error text is not matching with actual text '" + expectedText + "'", "FAIL");
				// Status = false;
			}
		}

		return status;

	}

	/**
	 * @function verify the expected behaviour
	 * @param funValue
	 * @return boolean
	 * @throws Exception
	 */
	public boolean VerifyResults(String funValue) throws Exception {
		boolean status = true;
		String[] sValue = funValue.split(":::");
		String wait = HTML.properties.getProperty("SHORTWAIT");
		Integer x = Integer.valueOf(wait);
		String ActValue;
		switch (sValue[0].toUpperCase()) {

		case "VALIDATEERRMSG":
			logger.info("Validating the '" + sValue[0] + "'");

			// handled for Email adn fax field

			ActValue = common.ReadElement(Common.o.getObject("eleBBPTErrorMsg"), x);
			status = common.CompareStringResult("Error Message", sValue[1], ActValue);
			break;
		case "VALIDATEPAGEEXISTENCE":
			logger.info("Validating the '" + sValue[0] + "'");
			status = common.ElementExistOrNotTrue("Page- Guided BBT Workflow", "verify whether the BBT Guided workflow screen is displayed", "As expected,BBT guided workflow screen dispalyed", Common.o.getObject(sValue[1]));
			break;
		case "VALIDATEPAGEMODE":
			logger.info("Validating the '" + sValue[0] + "'");
			ActValue = common.ReadElement(Common.o.getObject(sValue[1]), x);
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Validate the BBT guided Info Bar title", "BBT Guided workflow title Bar is '" + ActValue + "'", "PASS");
			break;
		case "VALIDATESAVEDDATA":
			logger.info("Validating the '" + sValue[0] + "'");
			status = common.ActionOnTable(Common.o.getObject("eleGuidedWrkFlwTable"), 0, 1, SCRCommon.ReturnCurrentDate(), "a");

			ActValue = common.ReadElement(Common.o.getObject("eleNewProducerName"), x);
			status = common.CompareStringResult("effective Date", sValue[2], ActValue);
			ActValue = common.ReadElementFromListEditBox(Common.o.getObject("edtEffectiveDate"), x);
			status = common.CompareStringResult("New agency Name", sValue[1], ActValue);
			break;
		case "VALIDATEWITHDRAW_TRANSACTION":
			logger.info("Validating the '" + sValue[0] + "'");

			JobNumber = common.ReadElement(Common.o.getObject("ele" + sValue[1]), x);
			BBTStatus = common.ReadElement(Common.o.getObject("ele" + sValue[2]), x);
			if (sValue[3].equalsIgnoreCase("TransactionScreen")) {
				status = common.SafeAction(Common.o.getObject("eljPolicyNo"), "eljPolicyNo", "eljPolicyNo");
				NavigatetoGuidedWrkFlw_MT("eleMaintainanceTransactions:::eleGuidedWrkFlw_MT");
			}
			ActValue = common.GetTextFromTable(Common.o.getObject("eleGuidedWrkFlwTable"), 1, 2, JobNumber);
			status = common.CompareStringResult("Transaction status", BBTStatus, ActValue);
			break;

		case "VALIDATE_LEFTPANEL":
			logger.info("Validating the '" + sValue[0] + "'");
			ActValue = common.ReadElement(Common.o.getObject(sValue[1]), x);
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Validate the BBT guided Info Bar title", "BBT Guided workflow title Bar is '" + ActValue + "'", "PASS");
			break;
		}
		return status;
	}

	/**
	 * Method used to set to Date
	 * 
	 * @param sFuncValue
	 * @return boolean
	 * @throws Exception
	 */
	public boolean Date(String sFuncValue) throws Exception {
		boolean status = false;
		switch (sFuncValue.toUpperCase()) {
		case "EFFDATE_CURDATE":
			status = common.SafeAction(Common.o.getObject("edtEffectiveDate"), SCRCommon.ReturnCurrentDate(), "edt");
			break;
		}
		return status;
	}

}