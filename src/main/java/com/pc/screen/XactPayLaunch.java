
/**
 * @ClassPurpose This Class used to create activites for account and polcies
 * @Scriptor Rajeshwari
 * @ReviewedBy
 * @ModifiedBy Rajeshwari
 * @LastDateModified 4/12/2017
 */
package com.pc.screen;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.PCThreadCache;

public class XactPayLaunch {

	public static String sheetname = "XactPayLaunch";
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();

	public Boolean SCRXactPayLaunch() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);

		if (!status) {
			status = false;
		}
		return status;
	}

	/**
	 * @method to add multiple FIEN number
	 * @param funValue
	 * @return status
	 * @throws Exception
	 */
	public boolean addMultipleFEINNumber(String funValue) throws Exception {
		boolean status = false;
		logger.info("Adding multiple FEIN numbers");
		String[] sValue = funValue.split(":::");

		// status = common.ActionOnTable(Common.o.getObject("eleXactpayTable"), 1, 1,
		// "", "div");
		status = SCRCommon.DataWebTable(Common.o.getObject("eleXactpayTable"), 1, 1, "", "single", "div");
		status = common.SafeAction(Common.o.getObject("edtFIEN_Xact"), sValue[0], "edtFIEN_Xact");
		status = SCRCommon.DataWebTable(Common.o.getObject("eleXactpayTable"), 2, 1, "", "single", "div");
		// status = common.ActionOnTable(Common.o.getObject("eleXactpayTable"), 1, 1,
		// "", "div");
		status = common.SafeAction(Common.o.getObject("edtFIEN_Xact"), sValue[1], "edtFIEN_Xact");

		return status;
	}

	/**
	 * @method to validate results as per criteria
	 * @param funValue
	 * @return boolean
	 * @throws Exception
	 */
	public boolean ValidateResults(String funValue) throws Exception {
		boolean status = true;
		String[] sValue = funValue.split(":::");
		String wait = HTML.properties.getProperty("SHORTWAIT");
		Integer x = Integer.valueOf(wait);
		String ActValue;
		switch (sValue[0].toUpperCase()) {

		case "FEIN_RADIO_SELECTION":
			status = common.ActionOnTable(Common.o.getObject("eleXactpayTable"), 1, 0, sValue[1], "div");
			status = common.SafeAction(Common.o.getObject("eleXactPayTitle"), "ele", "eleXactPayTitle");
			status = common.ActionOnTable(Common.o.getObject("eleXactpayTable"), 1, 0, sValue[2], "div");
			status = VerifyRadioSelection(sValue[3]);
			break;
		case "VALIDATE_XACT_PAY_APP_ENABLED":
			ActValue = common.GetTextFromTableTagName(Common.o.getObject("eleXactpayTable"), 0, 1, "div");
			status = common.CompareExactStringResult("verify the FEIN value displayed matches", sValue[1], ActValue);
			status = common.ActionOnTable(Common.o.getObject("eleXactpayTable"), 1, 0, sValue[1], "div");
			status = common.ElementExistOrNotTrue("XactPay Application -Enabled", "Verify whether XactPay Application button is enabled only selecting the FIEN number in screen", "XactPay Application button is enabled on selecting the FIEN", Common.o.getObject

("eleXactPay_App_Enabled"));
			//
			break;
		}
		return status;
	}

	/**
	 * @method
	 * @param funValue
	 * @return
	 * @throws Exception
	 */

	public boolean VerifyResults(String funValue) throws Exception {
		boolean status = false;
		logger.info("Verify the Results");
		String[] sValue = funValue.split(":::");
		String sPrimaryWindow = driver.getWindowHandle();
		java.util.Set<String> allWindowHandles = driver.getWindowHandles();
		for (String handle : allWindowHandles) {
			if (driver.switchTo().window(handle).getTitle().contains(sValue[1])) {
				driver.switchTo().window(handle);
				// driver.switchTo().window(handle).manage().window().maximize();
				status = common.handleUnknownAlert();
				switch (sValue[0].toUpperCase()) {
				case "XACTAPP":

					for (int i = 2; i < sValue.length; i++) {
						status = common.ElementExistOrNotTrue("verify the display of field", "'" + sValue[i] + "'", "'" + 

sValue[i] + "'", Common.o.getObject(sValue[i]));
					}
					break;
				case "XACTADMIN":

					for (int i = 2; i < sValue.length; i++) {
						status = common.ElementExistOrNotTrue("verify the display of field", "'" + sValue[i] + "'", "'" + 

sValue[i] + "'", Common.o.getObject(sValue[i]));
					}
					break;

				}
				if (sValue[1].toUpperCase() == "XACTPAY") {
					status = common.SafeAction(Common.o.getObject("eleXactPayClose"), "eleXactPayClose", "eleXactPayClose");
				}
				driver.close();
				driver.switchTo().window(sPrimaryWindow);

			}
		}

		return status;

	}

	/**
	 * 
	 * @param sCount
	 * @return
	 * @throws IOException
	 */
	public boolean VerifyRadioSelection(String sCount) throws IOException {
		boolean status = false;
		List<WebElement> allrows = ManagerDriver.getInstance().getWebDriver().findElements(Common.o.getObject("edtFIEN_radio"));

		if (allrows.size() == Integer.parseInt(sCount)) {
			logger.info("Validate the Radio selection of FEIN in xactPay Screen '" + allrows.size() + "'");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "Validate the Radio selection of FEIN field ", "Only one FEIN Radio button should be selected at a time", "PASS");
			status = true;
		} else {
			logger.info("Validate the Radio selection of FEIN in xactPay Screen '" + allrows.size() + "'");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "Validate the Radio selection of FEIN field ", "More than one FEIN Radio button should be selected at a time .No Of radio button selected -" + allrows.size(), "FAIL");
			status = false;
		}
		return status;

	}

	/**
	 * @method to select the FEIN radio button
	 * @param strValue
	 * @return boolean
	 * @throws Exception
	 */
	public boolean SelectFEINRadio(String strValue) throws Exception {
		boolean status = false;
		status = common.ActionOnTable(Common.o.getObject("eleXactpayTable"), 1, 0, strValue, "", "div");
		return status;
	}

}
