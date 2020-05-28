/**
 * @ClassPurpose This Class used for the account merge usecase verification
 * @Scriptor Sojan David
 * @ReviewedBy
 * @ModifiedBy
 * @LastDateModified 3/17/2017
 */
package com.pc.screen;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.pc.constants.PCConstants;
import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.PCThreadCache;
import com.pc.utilities.XlsxReader;

public class AccountMergeVerification {

	public static String sheetname = "AccountMergeVerification";
	Common common = CommonManager.getInstance().getCommon();
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();
	//static Logger logger = Logger.getLogger(sheetname);
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));

	/**
	 * @function Use to perform all the objects/elements/functions from the
	 *           accountmergeverification excel sheet
	 * @return true/false
	 * @throws Exception
	 */
	public Boolean SCRAccountMergeVerification() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}

		return status;
	}

	/**
	 * @function Used to write the testdata for the dependency testcase
	 * @param sValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean DataFill(String sValue) throws Exception {
		HashMap<String, Object> updateColumnNameValues = new HashMap<String, Object>();
		HashMap<String, Object> whereConstraint = new HashMap<String, Object>();
		boolean status = false;
		try {
			XlsxReader sXL = XlsxReader.getInstance();
			int rowcount = sXL.getRowCount(sheetname);
			for (int i = 1; i < rowcount; i++) {
				String value = sXL.getCellData(sheetname, 0, i);
				if (!value.isEmpty()) {
					if (PCThreadCache.getInstance().getProperty("TCID").equals(value)) {
						if (sValue.equals("AccountNumber")) {
							updateColumnNameValues.clear();
							whereConstraint.clear();
							updateColumnNameValues.put(PCConstants.AccountNumber, PCThreadCache.getInstance().getProperty(PCConstants.CACHE_ACCOUNT_NUMBER));
							whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
							PCThreadCache.getInstance().setProperty("AccountNumber", PCThreadCache.getInstance().getProperty(PCConstants.CACHE_ACCOUNT_NUMBER));
							//status = sXL.executeUpdateQuery(PCConstants.SHEET_ACCOUNTMERGEVERIFICATION, updateColumnNameValues, whereConstraint);

							// status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"),
							// "Update AccountMergeVerification Set AccountNumber =
							// '"+CreateAccount.AccountNumber+"' where
							// ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
							status = true;
							break;
						}
					}
				}
			}
		} finally {
			updateColumnNameValues = null;
			whereConstraint = null;
		}
		return status;
	}

	/**
	 * @function function used to verify results of the testcase
	 * @param funValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean VerifySearchResults(String funValue) throws Exception {
		// Common c = new Common();
		boolean fvalue = true;
		String[] sValue = funValue.split(":::");
		System.out.println(sValue[0]);
		String wait = HTML.properties.getProperty("SHORTWAIT");
		Integer x = Integer.valueOf(wait);
		// WaitUntilClickable(driver, bylocator, iWaitTime);
		// WebElement element = driver.findElement(bylocator);
		// string r = element.getText();
		if (fvalue) {
			switch (sValue[0].toUpperCase()) {
			case "CHECKINGRESET":
				boolean checkSelectisResetted = driver.findElements(Common.o.getObject("eleSelect")).size() != 0;
				logger.info("Validating the '" + sValue[0] + "'");
				if (!checkSelectisResetted == true) {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should clear the field after clicking the reset", "All the fileds are resetted", "PASS");
				} else {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should clear the field after clicking the reset", "Fileds are not resetted", "FAIL");
				}
				break;

			case "CHECKINGMINFIELDVALIDATION":
				String expectedValue = sValue[1];
				String actualValue = common.ReadElement(Common.o.getObject("eleSameTgtasSrc"), x);
				logger.info("Validating the '" + sValue[0] + "'");
				if (expectedValue.equals(actualValue)) {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "'" + expectedValue + "'", "'" + actualValue + "'", "PASS");
				} else {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "'" + expectedValue + "'", "'" + actualValue + "'", "FAIL");
				}
				break;

			case "CHECKINGATTHREECHAR":
				String expectedValue1 = sValue[1];
				String actualValue1 = common.ReadElement(Common.o.getObject("eleSameTgtasSrc"), x);
				logger.info("Validating the '" + sValue[0] + "'");
				if (expectedValue1.equals(actualValue1)) {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "'" + expectedValue1 + "'", "'" + actualValue1 + "'", "PASS");
				} else {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "'" + expectedValue1 + "'", "'" + actualValue1 + "'", "FAIL");
				}
				break;

			case "CHECKINGEXACTMATCHWITHCHECK":
				String expectedValue2 = sValue[1];
				String actualValue2 = common.ReadElement(Common.o.getObject("eleSameTgtasSrc"), x);
				logger.info("Validating the '" + sValue[0] + "'");
				if (expectedValue2.equals(actualValue2)) {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "'" + expectedValue2 + "'", "'" + actualValue2 + "'", "PASS");
				} else {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "'" + expectedValue2 + "'", "'" + actualValue2 + "'", "FAIL");
				}
				break;

			case "CHECKINGALLINUM":
				String expectedValue3 = sValue[1];
				String actualValue3 = common.ReadElement(Common.o.getObject("eleSameTgtasSrc"), x);
				logger.info("Validating the '" + sValue[0] + "'");
				if (expectedValue3.equals(actualValue3)) {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "'" + expectedValue3 + "'", "'" + actualValue3 + "'", "PASS");
				} else {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "'" + expectedValue3 + "'", "'" + actualValue3 + "'", "FAIL");
				}
				break;

			case "CHECKINGAIFNUM":
				String expectedValue4 = sValue[1];
				String actualValue4 = common.ReadElement(Common.o.getObject("eleSameTgtasSrc"), x);
				logger.info("Validating the '" + sValue[0] + "'");
				if (expectedValue4.equals(actualValue4)) {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "'" + expectedValue4 + "'", "'" + actualValue4 + "'", "PASS");
				} else {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "'" + expectedValue4 + "'", "'" + actualValue4 + "'", "FAIL");
				}
				break;

			case "CHECKINGACCOUNTNUM":
				String expectedValue5 = sValue[1];
				String actualValue5 = common.ReadElement(Common.o.getObject("eleSameTgtasSrc"), x);
				logger.info("Validating the '" + sValue[0] + "'");
				if (expectedValue5.equals(actualValue5)) {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "'" + expectedValue5 + "'", "'" + actualValue5 + "'", "PASS");
				} else {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "'" + expectedValue5 + "'", "'" + actualValue5 + "'", "FAIL");
				}
				break;

			case "CHECKINGFEINNUM":
				String expectedValue6 = sValue[1];
				String actualValue6 = common.ReadElement(Common.o.getObject("eleSameTgtasSrc"), x);
				logger.info("Validating the '" + sValue[0] + "'");
				if (expectedValue6.equals(actualValue6)) {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "'" + expectedValue6 + "'", "'" + actualValue6 + "'", "PASS");
				} else {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "'" + expectedValue6 + "'", "'" + actualValue6 + "'", "FAIL");
				}
				break;

			case "CHECKINGEXPERIANKEY":
				String expectedValue7 = sValue[1];
				String actualValue7 = common.ReadElement(Common.o.getObject("eleSameTgtasSrc"), x);
				logger.info("Validating the '" + sValue[0] + "'");
				if (expectedValue7.equals(actualValue7)) {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "'" + expectedValue7 + "'", "'" + actualValue7 + "'", "PASS");
				} else {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "'" + expectedValue7 + "'", "'" + actualValue7 + "'", "FAIL");
				}
				break;

			case "VERIFYMAHNEWROLE":
				String expectedValue8 = sValue[1];
				boolean verifyNewRole = driver.findElements(Common.o.getObject("eleMAHTable2")).size() != 0;
				if (verifyNewRole == true) {
					String actualValue8 = common.ReadElement(Common.o.getObject("eleMAHTable2"), x);
					if (expectedValue8.equals(actualValue8)) {
						HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), 

PCThreadCache.getInstance().getProperty("methodName"), "'" + expectedValue8 + "'", "'" + actualValue8 + "'", "PASS");
					} else {
						HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), 

PCThreadCache.getInstance().getProperty("methodName"), "'" + expectedValue8 + "'", "'" + actualValue8 + "'", "FAIL");
					}
				}
				break;

			case "VERIFYNEWROLEACTIVE":
				String expectedValue9 = sValue[1];
				String actualValue9 = common.ReadElement(Common.o.getObject("txtCheckNewRoleActiveStatus"), x);
				if (expectedValue9.equals(actualValue9)) {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "'" + expectedValue9 + "'", "'" + actualValue9 + "'", "PASS");
				} else {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "'" + expectedValue9 + "'", "'" + actualValue9 + "'", "FAIL");
				}
				break;

			case "VERIFYAIFMSG":
				String expectedValue10 = sValue[1];
				String actualValue10 = common.ReadElement(Common.o.getObject("txtMergeTranMsg"), x);
				logger.info("Validating the '" + sValue[0] + "'");
				if (expectedValue10.equals(actualValue10)) {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "'" + expectedValue10 + "'", "'" + actualValue10 + "'", "PASS");
				} else {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "'" + expectedValue10 + "'", "'" + actualValue10 + "'", "FAIL");
				}
				break;

			case "VERIFYAIFFEINALLIACENUM":
				String expectedValue11 = sValue[1];
				String expectedValue12 = sValue[2];
				String expectedValue13 = sValue[3];
				String actualValue11 = common.ReadElement(Common.o.getObject("txtAIFNum"), x);
				String actualValue12 = common.ReadElement(Common.o.getObject("txtAllianceNum"), x);
				String actualValue13 = common.ReadElement(Common.o.getObject("txtFEINNum"), x);
				logger.info("Validating the '" + sValue[0] + "'");
				if (expectedValue11.equals(actualValue11)) {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "AIF Number'" + expectedValue11 + "'should match", "AIF Number'" + actualValue11 + "' matches", "PASS");
				} else {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "AIF Number'" + expectedValue11 + "'should match", "AIF Number'" + actualValue11 + "' does not match", "FAIL");
				}
				if (expectedValue12.equals(actualValue12)) {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Alliance Number'" + expectedValue12 + "'should match", "Alliance Number'" + actualValue12 + "' matches", "PASS");
				} else {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Alliance Number'" + expectedValue12 + "'should match", "Alliance Number'" + actualValue12 + "' does not match", 

"FAIL");
				}
				if (expectedValue13.equals(actualValue13)) {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "FEIN Number'" + expectedValue13 + "'should match", "FEIN Number'" + actualValue13 + "' matches", "PASS");
				} else {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "FEIN Number'" + expectedValue13 + "'should match", "FEIN Number'" + actualValue13 + "' does not match", "FAIL");
				}
				break;

			case "VERIFYSOURCEACCEXISTORNOT":
				String expectedValue14 = sValue[1];
				// String actualValue14 =
				// common.ReadElement(Common.o.getObject("eleZeroResultMsg"), x);
				String actualValue14 = common.ReadElementforODS(Common.o.getObject("eleZeroResultMsg"), x);
				logger.info("Validating the '" + sValue[0] + "'");
				if (expectedValue14.equals(actualValue14)) {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "'" + expectedValue14 + "'", "'" + actualValue14 + "'", "PASS");
				} else {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "'" + expectedValue14 + "'", "'" + actualValue14 + "'", "FAIL");
				}
				break;

			// case "VERIFYROLEANDPRIMARYCONTACT":
			// boolean checkExpectedValue = true;
			// WebElement tableRowXpath =
			// driver.findElement(Common.o.getObject("accFileContactRoleTable"));
			// List<WebElement> rows_table = tableRowXpath.findElements(By.tagName("tr"));
			// int rows_count = rows_table.size();
			// for(int j=1;j<=rows_count;j++)
			// {
			// String sourceAccName =
			// driver.findElement(By.xpath(".//*[@id='AccountFile_Contacts:AccountFile_ContactsScreen:AccountContactsLV-body']/div/table/tbody/tr["+j+"]/td[3]")).getText();
			//// boolean checkExpectedValue;
			// if(sValue[1].equals(sourceAccName))
			// {
			// HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
			// PCThreadCache.getInstance().getProperty("methodName"), "'"+sValue[1]+"'",
			// "'"+sourceAccName+"'", "PASS");
			// checkExpectedValue = true;
			// String sourceAccRole =
			// driver.findElement(By.xpath(".//*[@id='AccountFile_Contacts:AccountFile_ContactsScreen:AccountContactsLV-body']/div/table/tbody/tr["+j+"]/td[4]")).getText();
			// if(sValue[2].equals(sourceAccRole))
			// {
			// HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
			// PCThreadCache.getInstance().getProperty("methodName"), "'"+sValue[2]+"'",
			// "'"+sourceAccRole+"'", "PASS");
			// String sourceAccActiveCheck =
			// driver.findElement(By.xpath(".//*[@id='AccountFile_Contacts:AccountFile_ContactsScreen:AccountContactsLV-body']/div/table/tbody/tr["+j+"]/td[2]")).getText();
			// if(sValue[3].equals(sourceAccActiveCheck)){
			// HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
			// PCThreadCache.getInstance().getProperty("methodName"), "'"+sValue[3]+"'",
			// "'"+sourceAccActiveCheck+"'", "PASS");
			// checkExpectedValue = true;
			// }
			// else
			// {
			// HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
			// PCThreadCache.getInstance().getProperty("methodName"), "'"+sValue[3]+"'",
			// "'"+sourceAccActiveCheck+"'", "FAIL");
			// checkExpectedValue = false;
			// }
			// }
			// else
			// {
			// HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
			// PCThreadCache.getInstance().getProperty("methodName"), "'"+sValue[2]+"'",
			// "'"+sourceAccRole+"'", "FAIL");
			// checkExpectedValue = false;
			// }
			// }
			// if(sValue[4].equals(sourceAccName))
			// {
			// HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
			// PCThreadCache.getInstance().getProperty("methodName"), "'"+sValue[4]+"'",
			// "'"+sourceAccName+"'", "PASS");
			// String sourcePrimaryContactAccRole =
			// driver.findElement(By.xpath(".//*[@id='AccountFile_Contacts:AccountFile_ContactsScreen:AccountContactsLV-body']/div/table/tbody/tr["+j+"]/td[4]")).getText();
			// if(sourcePrimaryContactAccRole.equals(sValue[5]))
			// {
			// HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
			// PCThreadCache.getInstance().getProperty("methodName"), "'"+sValue[5]+"'",
			// "'"+sourcePrimaryContactAccRole+"'", "PASS");
			// checkExpectedValue = true;
			// }
			// else
			// {
			// HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
			// PCThreadCache.getInstance().getProperty("methodName"), "'"+sValue[5]+"'",
			// "'"+sourcePrimaryContactAccRole+"'", "FAIL");
			// checkExpectedValue = false;
			// }
			// }
			// if(sourceAccName.equals(sValue[6]))
			// {
			// HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
			// PCThreadCache.getInstance().getProperty("methodName"), "'"+sValue[6]+"'Source
			// Acc. Primary Acc should not be available", "'"+sourceAccName+"' is
			// available", "FAIL");
			// }
			// if(sourceAccName.endsWith(sValue[7]))
			// {
			// HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
			// PCThreadCache.getInstance().getProperty("methodName"), "'"+sValue[6]+"'",
			// "'"+sourceAccName+"'", "PASS");
			// String srcAdditionalInsureeName =
			// driver.findElement(By.xpath(".//*[@id='AccountFile_Contacts:AccountFile_ContactsScreen:AccountContactsLV-body']/div/table/tbody/tr["+j+"]/td[4]")).getText();
			// checkExpectedValue = true;
			// if(srcAdditionalInsureeName.equals(sValue[8]))
			// {
			// HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
			// PCThreadCache.getInstance().getProperty("methodName"), "'"+sValue[8]+"'",
			// "'"+srcAdditionalInsureeName+"'", "PASS");
			// checkExpectedValue = true;
			// String srcAdditionalInsureeActive =
			// driver.findElement(By.xpath(".//*[@id='AccountFile_Contacts:AccountFile_ContactsScreen:AccountContactsLV-body']/div/table/tbody/tr["+j+"]/td[2]")).getText();
			// if(srcAdditionalInsureeActive.equals(sValue[9]))
			// {
			// HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
			// PCThreadCache.getInstance().getProperty("methodName"), "'"+sValue[9]+"'",
			// "'"+srcAdditionalInsureeActive+"'", "PASS");
			// checkExpectedValue = true;
			// }
			// else
			// {
			// HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
			// PCThreadCache.getInstance().getProperty("methodName"), "'"+sValue[9]+"'",
			// "'"+srcAdditionalInsureeActive+"'", "FAIL");
			// checkExpectedValue = false;
			// }
			// }
			// else
			// {
			// HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
			// PCThreadCache.getInstance().getProperty("methodName"), "'"+sValue[8]+"'",
			// "'"+srcAdditionalInsureeName+"'", "FAIL");
			// checkExpectedValue = false;
			// }
			// }
			// }
			// break;
			//
			case "VERIFYROLEANDPRIMARYCONTACT":
				boolean status = true;
				String Value = null;
				Value = common.GetTextFromTable(Common.o.getObject("accFileContactRoleTable"), 2, 3, sValue[1], sValue[2]);
				status = common.CompareStringResult("VerifyContactRole", sValue[2], Value);
				Value = common.GetTextFromTable(Common.o.getObject("accFileContactRoleTable"), 2, 1, sValue[1], sValue[3]);
				status = common.CompareStringResult("VerifyContactRole", sValue[3], Value);
				Value = common.GetTextFromTable(Common.o.getObject("accFileContactRoleTable"), 2, 3, sValue[4], sValue[5]);
				status = common.CompareStringResult("VerifyContactRole", sValue[5], Value);
				Value = common.GetTextFromTable(Common.o.getObject("accFileContactRoleTable"), 2, 3, sValue[6], sValue[7]);
				status = common.CompareStringResult("VerifyContactRole", sValue[7], Value);
				Value = common.GetTextFromTable(Common.o.getObject("accFileContactRoleTable"), 2, 1, sValue[6], sValue[8]);
				status = common.CompareStringResult("VerifyContactRole", sValue[8], Value);
				break;
			}
		}
		return true;
	}
}
