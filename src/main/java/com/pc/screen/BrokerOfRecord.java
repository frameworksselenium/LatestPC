/**
 * @ClassPurpose Access all the data's from BrokerOfRecord sheet
 * @Scriptor Rajeshwari
 * @ReviewedBy
 * @ModifiedBy Rajeshwari
 * @LastDateModified 4/14/2017
 */
package com.pc.screen;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.PCThreadCache;

public class BrokerOfRecord {

	public static String sheetname = "BrokerOfRecord";
	//static Logger logger = Logger.getLogger(sheetname);
	Common common = CommonManager.getInstance().getCommon();
	//private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));

	public Boolean SCRBrokerOfRecord() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);

		if (!status) {
			status = false;
		}
		return status;
	}

	/**
	 * @function This use to verify the results of the usecase
	 * @param funValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean VerifyResults(String funValue) throws Exception {
		boolean status = false;
		logger.info("Verify the Results");
		String[] sValue = funValue.split(":::");
		String value = null;

		switch (sValue[0].toUpperCase()) {
		case "VERIFYERRORMESSAGE":
			logger.info("Validation started for the case '" + sValue[0]);
			value = common.ReadElement(Common.o.getObject(sValue[1]), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("Verify Error message", sValue[2], value);
			break;

		case "SET_BOR_EFFECTIVE":
			logger.info("Validation started for the case '" + sValue[0]);
			if (sValue[1].equalsIgnoreCase("POLICY_EFF")) {
				status = common.SafeAction(common.o.getObject("elePolicyInfoTab"), "ele", "ele");
				value = common.ReadElement(Common.o.getObject("edtCLAEffDate"), Integer.valueOf(HTML.properties.getProperty

("NORMALWAIT")));
			}

			status = common.CompareStringResult("Verify Error message", sValue[2], value);
			break;
		}
		if (!status) {
			logger.info("" + sValue[0] + " Fails");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "" + sValue[0] + " case should PASS", "" + sValue[0] + " case should not fail", "FAIL");
			status = true;
		}
		return status;
	}

	/**
	 * @function - This function used to get the Date for different scenario
	 * @param -
	 *            value
	 * @return - true/false
	 * @throws Exception
	 */
	public boolean Date(String sFuncValue) throws Exception {
		boolean status = false;
		String[] sValue = sFuncValue.split(":::");
		switch (sValue[0].toUpperCase()) {
		/*
		 * case "BOREFF_POLICYEXP_LESSTHAN30": status =
		 * common.SafeAction(Common.o.getObject("edtEffectiveDate"), NewDate, "edt");
		 * break;
		 */
		case "BOR_BEFORE_EFF_DATE":
			// String
			// NewDate=SCRCommon.ReturnDate_LessthanDays_FromGivenDate(sValue[1],-30);
			// status = common.SafeAction(Common.o.getObject("edtEffectiveDate"), NewDate,
			// "edt");
			status = false;
			break;
		}
		return status;
	}

	// @SuppressWarnings("static-access")
	public boolean SelectBORQstn(String strValue) throws Exception {
		boolean status = false;
		logger.info("Select value for BOR question table");
		String[] sValue = strValue.split(":::");
		try {
			if (sValue[0].equalsIgnoreCase("DROPDOWN")) {
				status = ActioonOnTable_Select(common.o.getObject("eleBORQstnTable"), 0, 1, sValue[1], "div", "LIST");
				status = common.SafeAction(common.o.getObject("lstBORQstnlist"), sValue[2], "lst");
			} else if (sValue[0].equalsIgnoreCase("RADIOBUTTON")) {
				status = ActioonOnTable_Select(common.o.getObject("eleBORQstnTable"), 0, 2, sValue[1], "input", sValue[2]);
				// status = common.SafeAction(common.o.getObject(sValue[1]),"ele","ele");
			}
		} catch (Throwable e) {
			e.printStackTrace();
			if (status == false) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should select a value for BPR question", "System not able to select value for the BPR question", "FAIL");
			}
		}
		return status;
	}

	/**
	 * @name SetDate
	 * @param sFuncValue
	 * @return boolean
	 * @throws Exception
	 */
	public boolean SetDate(String sFuncValue) throws Exception {
		boolean status = false;
		switch (sFuncValue.toUpperCase()) {
		case "EFFDATE":
			status = common.SafeAction(Common.o.getObject("lstCPEffectiveDate"), SCRCommon.ReturnCurrentDate(), "edt");
			break;
		case "RECEIEVEDDATE":
			status = common.SafeAction(Common.o.getObject("lstCPDateReceived"), SCRCommon.ReturnCurrentDate(), "edt");
			break;
		}
		return status;
	}

	/**
	 * @function This function use to Select the data from the table and click the
	 *           element accordingly
	 * @param obj,readCol,actionCol,strReadString,actionObjetName
	 * @return status
	 * @throws Exception
	 */
	public Boolean ActioonOnTable_Select(By obj, int readCol, int actionCol, String strReadString, String TagName, String objectType) throws 

Exception {
		boolean Status = false;
		boolean SearchString = false;
		boolean ActionObject = false;
		JavascriptExecutor js = (JavascriptExecutor) ManagerDriver.getInstance().getWebDriver();
		WebElement mytable = ManagerDriver.getInstance().getWebDriver().findElement(obj);
		// By byXpath = By.xpath("//tr[contains(@class,'x-grid-row')]");

		List<WebElement> allrows = mytable.findElements(By.xpath("//tr[contains(@class,'x-grid-data-row')]"));

		for (int i = 0; i <= allrows.size() - 1; i++) {
			List<WebElement> Cells = allrows.get(i).findElements(By.tagName("td"));
			List<WebElement> divCells = Cells.get(readCol).findElements(By.tagName("div"));
			if (divCells.size() > 0) {
				String readText = divCells.get(0).getText();

				if (readText.contains(strReadString)) {
					SearchString = true;
					if (objectType.equalsIgnoreCase("YES")) {
						List<WebElement> CellElements = allrows.get(i).findElements(By.tagName(TagName));
						CellElements.get(0).click();

						SCRCommon.JavaScript(js);
						Status = true;
						ActionObject = true;
					} else if (objectType.equalsIgnoreCase("NO")) {
						List<WebElement> CellElements = allrows.get(i).findElements(By.tagName(TagName));
						CellElements.get(1).click(); // NO
						SCRCommon.JavaScript(js);
						Status = true;
						ActionObject = true;
					} else {
						Status = true;
						ActionObject = true;
						Cells.get(actionCol).click();
						SCRCommon.JavaScript(js);
						break;
					}

				}
				if (ActionObject == true) {
					break;
				}
			}
		}
		return Status;
	}
}