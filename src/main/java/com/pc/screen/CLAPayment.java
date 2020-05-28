
package com.pc.screen;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.PCThreadCache;

public class CLAPayment {
	public static String sheetName = "CLAPayment";
	//static Logger logger = Logger.getLogger(sheetName);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();

	public Boolean SCRCLAPayment() throws Exception {
		Boolean status = false;
		status = common.ClassComponent(sheetName, Common.o);
		if (!status) {
			return false;
		}
		return status;
	}

	/**
	 * @function This function use to Select the existign contacts info in Payment
	 *           screen
	 * @return String
	 * @throws IOException
	 * @throws Exception
	 */
	public boolean SelectExistingContact(String strValue) throws IOException {
		boolean Status = false;
		// By option = By.xpath("//span[starts-with(text(),'"+strValue+"')]");
		By option = By.xpath("//*[contains(@id,'HIGPolicyFile_Payment_Ext:psBillingAdjustment:PSBillingAdjustment_ExtDV')]//span[contains(text(), '" + strValue + "')]");
		try {

			Status = CommonManager.getInstance().getCommon().SafeAction(option, "scl", "scl");
			Status = CommonManager.getInstance().getCommon().SafeAction(option, "ele", "ele");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Thread ID = " + Thread.currentThread().getId() + " Error Occured =" + e.getMessage(), e);
		}
		if (Status) {
			// logger.info("Clicked on '" + option + "' element or button or link and
			// element '"+ option + "'");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should click on '" + option + "' element or button or link", "Clicked on '" + option + "' element or button or link", 

"PASS");
		} else {
			// logger.info("Object not enabled or displayed or not clickable '"+ option +
			// "'");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should display '" + option + "' in screen", "'" + option + "' not displayed in screen", "FAIL");
		}
		return Status;
	}

	/**
	 * function used to get the dropdown values and check for sorted order
	 * 
	 * @param strValue
	 * @return
	 * @throws Exception
	 */
	public boolean verifyListBoxValuesSortedOrder(String strValue) throws Exception {
		String[] sValue = strValue.split(":::");
		logger.info("Verifying the Results");
		common.WaitUntilClickable(Common.o.getObject(sValue[0]), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
		WebElement element = ManagerDriver.getInstance().getWebDriver().findElement(Common.o.getObject(sValue[0]));
		element.click();
		Thread.sleep(1000);
		ManagerDriver.getInstance().getWebDriver().findElement(Common.o.getObject(sValue[0])).sendKeys(Keys.ARROW_DOWN);
		Thread.sleep(1500);
		boolean bFlag = false;
		common.WaitForElementExist(Common.o.getObject(sValue[0]), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
		List<WebElement> gwListBox = ManagerDriver.getInstance().getWebDriver().findElements(By.tagName("LI"));
		for (int i = 0; i < gwListBox.size(); i++) {
			String strListValue = gwListBox.get(i).getText();
			try {
				if (strListValue.contentEquals(sValue[i + 1]))

				{
					System.out.println(gwListBox.get(i).getText());
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should display Billing method '" + sValue[i + 1] + "'", "System displayed Billing method '" + sValue[i + 1] 

+ "' in the drop down", "PASS");
					// gwListBox.get(i).click();
					bFlag = true;
					// break;
				} else {
					bFlag = false;
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should display Billing method '" + sValue[i + 1] + "'", "System NOT displayed Billing method '" + sValue[i 

+ 1] + "' in the drop down", "FAIL");
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Thread ID = " + Thread.currentThread().getId() + " Error Occured =" + e.getMessage(), e);
				bFlag = false;
			}
		}
		return bFlag;
	}

	public boolean selectTotalCost(String sFunctionValue) throws Throwable {
		boolean status = false;
		String[] sValue = sFunctionValue.split(":::");
		int sRowCount = SCRCommon.TableRowCount(Common.o.getObject("eleCLAQTTable"));
		switch (sValue[0].toUpperCase()) {

		case "TOTALCOST":
			try {

				status = SCRCommon.DataWebTable(Common.o.getObject("eleCLAQTTable"), sRowCount, 6, "", "double", "div");
				// status=SCRCommon.FillCellValue(Common.o.getObject("eleQuoteTransactionstbl"),
				// sRowCount, 6,"", "single", sValue[1]);
				// driver.findElement(Common.o.getObject("lstTotalAnnualCost")).sendKeys(sValue[1]);

				status = common.SafeAction(Common.o.getObject("lstTotalAnnualCost"), sValue[1], "lstTotalAnnualCost");
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}

		return status;
	}

	/**
	 * @function used to verify the expected message with actual/expected results
	 * @param funValue
	 * @return boolean
	 * @throws Exception
	 */
	public boolean VerifyResults(String funValue) throws Exception {
		boolean status = false;
		String[] sValue = funValue.split(":::");
		logger.info("Verifying the Results");
		String Value = null;
		switch (sValue[0].toUpperCase()) {
		/*
		 * case "VERIFYFIELDS": logger.info(sValue[0]);
		 * //status=ActionOnFieldExistence(Common.o.getObject("eleTypeofCuisineLbl")),
		 * "Input"); status=ActionOnFieldExistence("Type of Cuisine","Input");
		 * status=ActionOnFieldExistence("Risk Management Solutions","YES");
		 * status=ActionOnFieldExistence("Motor Carrier Filings","NO");
		 * status=ActionOnFieldExistence("Individual Financial Responsibility (SR22)"
		 * ,"YES");
		 * status=ActionOnFieldExistence("TX Wind/Hail Exclusion Received","NO"); break;
		 * case "VERIFY_CANADIAN_POLICY_FIELDS": logger.info(sValue[0]); //String
		 * exlValue=Common.getSpecifiedExcelValue(sheetName, "edtOtherPolicyNo");
		 * Value=common.ReadElementGetAttribute(Common.o.getObject("edtOtherPolicyNo"),
		 * "value", Integer.valueOf(HTML.properties.getProperty("NORMALWAIT"))); status
		 * = common.CompareStringResult("OtherPolicy number - Canadian Policy",
		 * sValue[1], Value); Value =
		 * common.ReadElementGetAttribute(common.o.getObject("edtExchangeRate"),
		 * "value",Integer.valueOf(HTML.properties.getProperty("NORMALWAIT"))); status =
		 * common.CompareStringResult("Exchange Rate - Canadian Policy", sValue[2],
		 * Value); Value =
		 * common.ReadElementGetAttribute(common.o.getObject("edtTotalCanadianCost"),
		 * "value", Integer.valueOf(HTML.properties.getProperty("NORMALWAIT"))); status
		 * = common.CompareStringResult("Total Canadian cost  - Canadian Policy",
		 * sValue[3], Value); Value =
		 * common.ReadElementGetAttribute(common.o.getObject("edtMailDate"),"value",
		 * Integer.valueOf(HTML.properties.getProperty("NORMALWAIT"))); status =
		 * common.CompareStringResult("Mail Date  - Canadian Policy", sValue[4], Value);
		 * //total cost field exitence
		 * status=common.SafeAction(common.o.getObject("edtTotalCost"), sValue[5],
		 * "edt"); String
		 * ActValue=common.ReadElementFromListEditBox(common.o.getObject("edtTotalCost")
		 * , Integer.valueOf(HTML.properties.getProperty("NORMALWAIT"))); status =
		 * common.CompareStringResult("Total cost  - Canadian Policy", sValue[5],
		 * ActValue); break;
		 */
		case "VALIDATE_NOTE_MSG":
			logger.info(sValue[0]);
			Value = common.ReadElement(Common.o.getObject("ele" + sValue[1]), Integer.valueOf(HTML.properties.getProperty

("NORMALWAIT")));
			status = common.CompareStringResult("Payment Screen - Note messgae", sValue[2], Value);
			break;
		case "VERIFY_FIELDS_EDITABLE":
			logger.info(sValue[0]);
			status = common.SafeAction(Common.o.getObject(sValue[1]), sValue[2], sValue[1]);
			Value = common.ReadElementGetAttribute(Common.o.getObject(sValue[1]), "value", Integer.valueOf

(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("Payment Screen - Billing Method", sValue[2], Value);
			status = common.SafeAction(Common.o.getObject(sValue[3]), sValue[4], sValue[3]);
			Value = common.ReadElementGetAttribute(Common.o.getObject(sValue[3]), "value", Integer.valueOf

(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("Payment Screen -Installment plan", sValue[4], Value);
			break;
		case "VERFIY_FIELD_DISABLED":
			logger.info(sValue[0]);
			status = common.ElementExistOrNotTrue("Verify the Premium Financing Field is diabled", "Disabled -Premium Financing field should be displayed", "Premium Financing field is disabled as expected", Common.o.getObject(sValue[1]));
			break;
		case "VERIFY_FIELD_NOT_DISPLAYED":
			logger.info(sValue[0]);
			status = common.ElementExistOrNotFalse("Verify the Installment Plan Field id not displayed for Billing Method = Payroll", "Field - Installment Plan should not be displayed ", "Field -Installment Plan is not displayed", Common.o.getObject(sValue[1]));
			break;
		case "VERIFYTABNUM":
			logger.info("validation started for '" + sValue[0] + "'");
			Value = common.ReadElement(Common.o.getObject("eleNewBillingNum"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			status = common.SafeAction(Common.o.getObject("lsjAMPBillingNumber"), "Enter", "lsjAMPBillingNumber");
			status = common.SafeAction(Common.o.getObject("lsjAMPBillingNumber"), "New", "lsjAMPBillingNumber");
			String value1 = common.ReadElement(Common.o.getObject("eleNewBillingNum"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			if (Value.equals(value1)) {
				status = true;
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Tab account number should not get chnaged after reselecting Billing", "TAB number is not changed. First value =" + Value + ", second value =" + value1, "PASS");
			} else {
				status = false;
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Tab account number should not get chnaged after reselecting Billing", "TAB number is  changed. First value =" + Value + ", second value =" + value1, "FAIL");
			}
			break;
		}
		if (!status) {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "" + sValue[0] + " case should PASS", "" + sValue[0] + " case should not fail", "FAIL");
			status = true;
		}
		return status;
	}

	/* *//***
			 * Function to verify billing/ installment plan method drop down values for
			 * different conditions
			 * 
			 * @param sFuncValue
			 * @return
			 * @throws Exception
			 *             First value in the data sheet should be the element name
			 *//*
				 * public boolean VerifyPaymentDropDownList(String funValue) throws Exception {
				 * boolean status = false; String[] sValue =funValue.split(":::"); String
				 * strActualValues=""; String strExpValues="";
				 * logger.info("Entering into Verify Billing MethodList function"); status =
				 * common.SafeAction(Common.o.getObject(sValue[0]),"ele","ele"); WebElement
				 * sBillingMethodList = common.returnObject(Common.o.getObject("eleClassName"));
				 * List<WebElement> sGetList =
				 * sBillingMethodList.findElements(By.tagName("li"));
				 * 
				 * Integer eleActualCount = sGetList.size(); Integer eleExpectedCount
				 * =sValue.length;
				 * 
				 * for(int i=1; i<eleExpectedCount;i++) { strExpValues =
				 * strExpValues+", "+sValue[i]; }
				 * 
				 * for(WebElement element: sGetList) { String objName = element.getText();
				 * strActualValues =strActualValues+", "+objName; } if(eleActualCount ==
				 * eleExpectedCount) { for (int i=1; i<eleExpectedCount; i++) { status = false;
				 * for(WebElement element: sGetList) { String objName = element.getText(); if
				 * (objName.contains(sValue[i])) { status = true;
				 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
				 * PCThreadCache.getInstance().getProperty("methodName"),
				 * "Drop down '"+sValue[0]+"' should be having value '"+sValue[i]+
				 * "'","Drop down '"+sValue[0]+"' is having option '"+sValue[i]+"'", "PASS");
				 * break; }
				 * 
				 * } if (!status) {
				 * 
				 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
				 * PCThreadCache.getInstance().getProperty("methodName"),
				 * "Drop down '"+sValue[0]+"' should be having value '"+sValue[i]+
				 * "'","Drop down '"+sValue[0]+"' is NOT having option '"+sValue[i]+"'",
				 * "FAIL"); } } } else { status = false; funValue = funValue.replace(":::",
				 * ", ");
				 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
				 * PCThreadCache.getInstance().getProperty("methodName"),
				 * "Number of options in the drop down is not matching with expected values. "
				 * ,"Expected options are :"+strExpValues+". Actual options are : "
				 * +strActualValues+"'", "FAIL"); }
				 * 
				 * return status; }
				 */

	/***
	 * Function to verify billing/ installment plan method drop down values for
	 * different conditions
	 * 
	 * @param sFuncValue
	 * @return
	 * @throws Exception
	 *             First value in the data sheet should be the element name
	 */
	public boolean VerifyPaymentDropDownList(String funValue) throws Exception {
		boolean status = false;
		String[] sValue = funValue.split(":::");
		String strActualValues = "";
		String strExpValues = "";
		logger.info("Entering into Verify Billing MethodList function");
		status = common.SafeAction(Common.o.getObject(sValue[0]), "ele", "ele");
		WebElement sBillingMethodList = common.returnObject(Common.o.getObject("eleClassName"));
		List<WebElement> sGetList = sBillingMethodList.findElements(By.tagName("li"));

		Integer eleActualCount = sGetList.size();
		Integer eleExpectedCount = sValue.length - 1;

		for (int i = 0; i < eleExpectedCount; i++) {
			strExpValues = strExpValues + ", " + sValue[i];
		}

		for (WebElement element : sGetList) {
			String objName = element.getText();
			strActualValues = strActualValues + ", " + objName;
		}
		if (eleActualCount == eleExpectedCount) {
			for (int i = 1; i <= eleExpectedCount; i++) {
				status = false;
				for (WebElement element : sGetList) {
					String objName = element.getText();
					if (objName.contains(sValue[i])) {
						status = true;
						HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Drop down '" + sValue[0] + "' should be having value '" + sValue[i] + "'", "Drop down '" + sValue[0] + "' is having option '" + sValue[i] + "'", "PASS");
						break;
					}

				}
				if (!status) {

					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Drop down '" + sValue[0] + "' should be having value '" + sValue[i] + "'", "Drop down '" + sValue[0] + "' is NOT having option '" + sValue[i] + "'", "FAIL");
				}
			}
		} else {
			status = false;
			funValue = funValue.replace(":::", ", ");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Number of options in the drop down is not matching with expected values. ", "Expected options are :" + strExpValues + ". Actual options are : " + strActualValues + "'", "FAIL");
		}
		return status;
	}

	/***
	 * @function To verify that given options are not displayed in Installment Plan
	 *           drop down
	 * @param funValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean VerifyInstmantPlanNonExist(String funValue) throws Exception {
		boolean status = false;
		String[] sValue = funValue.split(":::");
		funValue = funValue.replace(":::", ", ");
		String actualValue = "";
		logger.info("Entering into Verify Billing method non existance ");
		status = common.SafeAction(Common.o.getObject("lstAMPInstallmentPlan"), "ele", "ele");
		WebElement sBillingMethodList = common.returnObject(Common.o.getObject("eleClassName"));
		List<WebElement> sGetList = sBillingMethodList.findElements(By.tagName("li"));
		for (WebElement element : sGetList) {
			String objName = element.getText();
			actualValue = actualValue + ", " + objName;
		}
		Integer eleExpectedCount = sValue.length;
		for (int i = 0; i < eleExpectedCount; i++) {
			status = true;
			for (WebElement element : sGetList) {
				String objName = element.getText().trim();
				if (objName.contains(sValue[i])) {
					status = false;
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should Not display Billing method '" + sValue[i] + "'", "System displays Billing method '" + sValue[i] + "' in the drop down", "FAIL");
					break;
				}

			}
			if (status) {

				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should NOT display Billing method '" + sValue[i] + "'", "System NOT displayed Billing method '" + sValue[i] 

+ "' in the drop down", "PASS");
			}

		}
		return status;
	}

	/***
	 * To verify that given options are not displayed in Billing method drop down
	 * 
	 * @param funValue
	 * @return Boolean
	 * @throws Exception
	 */
	public boolean VerifyBillingMethodNonExist(String funValue) throws Exception {
		boolean status = false;
		String[] sValue = funValue.split(":::");
		funValue = funValue.replace(":::", ", ");
		String actualValue = "";
		logger.info("Entering into Verify Billing method non existance ");
		status = common.SafeAction(common.o.getObject("lsjAMPBillingMethod"), "ele", "ele");
		WebElement sBillingMethodList = common.returnObject(Common.o.getObject("eleClassName"));
		List<WebElement> sGetList = sBillingMethodList.findElements(By.tagName("li"));

		for (WebElement element : sGetList) {
			String objName = element.getText();
			actualValue = actualValue + ", " + objName;
		}

		Integer eleExpectedCount = sValue.length;

		for (int i = 0; i < eleExpectedCount; i++) {
			status = true;
			for (WebElement element : sGetList) {
				String objName = element.getText();
				if (objName.contains(sValue[i])) {
					status = false;
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should Not display Billing method '" + sValue[i] + "'", "System displays Billing method '" + sValue[i] + "' in the drop down", "FAIL");
					break;
				}

			}
			if (status) {

				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should NOT display Billing method '" + sValue[i] + "'", "System NOT displayed Billing method '" + sValue[i] 

+ "' in the drop down", "PASS");
			}

		}

		return status;
	}

}