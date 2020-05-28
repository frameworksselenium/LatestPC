
package com.pc.screen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.pc.constants.PCConstants;
import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.FlatFile;
import com.pc.utilities.HTML;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.PCThreadCache;
import com.pc.utilities.XlsxReader;

public class PolicyReview {

	public static String sheetname = "PolicyReview";
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();

	public Boolean SCRPolicyReview() throws Exception {

		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

	public boolean ApproveCountLast() throws NumberFormatException, Exception {
		boolean status = false;
		status = common.WaitUntilClickable(Common.o.getObject("eleBindOptionAssert"), Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
		if (driver.findElements(Common.o.getObject("eleSPLApprvoalAssert")).isEmpty()) {
			logger.info("System does not any special approval");
			status = common.SafeAction(Common.o.getObject("eleRiskAnalysisTab"), "eleRiskAnalysisTab", "ele");
			status = true;
		} else {
			status = common.SafeAction(Common.o.getObject("eleRiskAnalysispage"), "", "ele");
			status = common.WaitUntilClickable(Common.o.getObject("eleRiskAnalysis"), Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
			if (driver.findElements(Common.o.getObject("eleAuthorityAckowledge")).isEmpty() && driver.findElements

(Common.o.getObject("eleSpecialApprove")).isEmpty()) {
				logger.info("Heading to Approve");
				// *****************This Code is for Approve Button
				int sApprovalCount = common.ElementSize(Common.o.getObject("eleApprove"));
				for (int i = 0; i < sApprovalCount; i++) {
					status = common.SafeAction(Common.o.getObject("eleApproveButton"), "", "ele");
					status = common.SafeAction(Common.o.getObject("eleRiskOkButton"), "", "ele");
				}
			} else if (driver.findElements(Common.o.getObject("eleApprove")).isEmpty() && driver.findElements(Common.o.getObject

("eleSpecialApprove")).isEmpty()) {
				logger.info("Heading to Authority Acknowledgement");
				// *****************This Code is for Authority Acknowledgement Approve button
				int sApprovalAuthorityCount = common.ElementSize(Common.o.getObject("eleAuthorityAckowledge"));
				for (int i = 0; i < sApprovalAuthorityCount; i++) {
					status = common.SafeAction(Common.o.getObject("eleAuthorityApprove"), "", "ele");
					status = common.SafeAction(Common.o.getObject("eleAlertPopUp"), "", "ele");
					status = common.SafeAction(Common.o.getObject("eleRiskOkButton"), "", "ele");
				}
			} else {
				logger.info("Heading to Special Approval");
				// *****************This Code is for Special Approval
				int sApprovalAuthorityCount = common.ElementSize(Common.o.getObject("eleSpecialApprove"));
				for (int i = 0; i < sApprovalAuthorityCount; i++) {
					status = common.SafeAction(Common.o.getObject("eleSpecialApproveButton"), "", "ele");
					status = common.SafeAction(Common.o.getObject("eleAlertPopUp"), "", "ele");
					status = common.SafeAction(Common.o.getObject("eleRiskOkButton"), "", "ele");
				}
			}
		}
		if (!status) {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "System should approve all the request", "System not approved all the request", "FAIL");
		}
		return status;
	}

	/*
	 * public boolean ApproveCount() throws NumberFormatException, Exception {
	 * boolean status = false; String sApproveType = null; int sApprovalCount;
	 * status = common.WaitUntilClickable(Common.o.getObject("eleBindOptionAssert"),
	 * Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT"))); sApproveType =
	 * common.ReadElement(Common.o.getObject("eleSPLApprvoalAssertTable"),
	 * Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT"))); //
	 * sApproveType =
	 * GetTextFromTableApproval(Common.o.getObject("eleSPLApprvoalAssertTable"), 0,
	 * 3, "This quote will require:::underwriting approval:::prior to issuance." ,
	 * "label"); if(sApproveType.
	 * contains("This quote will require underwriting approval prior to issuance."))
	 * { status = common.SafeAction(Common.o.getObject("eleRiskAnalysispage"), "",
	 * "ele"); status =
	 * common.WaitUntilClickable(Common.o.getObject("eleRiskAnalysis"),
	 * Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT"))); sApproveType =
	 * GetTextFromTableApproval(Common.o.getObject("eleApproveButtonType"), 4, 1,
	 * "Approve:::Special Approve:::Authority Acknoledge", "a"); switch
	 * (sApproveType) { case "Approve": logger.info("Heading to Approve");
	 * //*****************This Code is for Approve Button sApprovalCount =
	 * common.ElementSize(Common.o.getObject("eleApprove")); for(int i = 0;
	 * i<sApprovalCount; i++ ) { status =
	 * common.SafeAction(Common.o.getObject("eleApproveButton"), "", "ele"); status
	 * = common.SafeAction(Common.o.getObject("eleRiskOkButton"), "", "ele"); }
	 * break; case "Special Approve": logger.info("Heading to Special Approval");
	 * //*****************This Code is for Special Approval sApprovalCount =
	 * common.ElementSize(Common.o.getObject("eleSpecialApprove")); for(int i = 0;
	 * i<sApprovalCount; i++ ) { status =
	 * common.SafeAction(Common.o.getObject("eleSpecialApproveButton"), "", "ele");
	 * status = common.SafeAction(Common.o.getObject("eleAlertPopUp"), "", "ele");
	 * status = common.SafeAction(Common.o.getObject("eleRiskOkButton"), "", "ele");
	 * } break; case "Authority Acknowledge":
	 * logger.info("Heading to Authority Acknowledgement"); //*****************This
	 * Code is for Authority Acknowledgement Approve button sApprovalCount =
	 * common.ElementSize(Common.o.getObject("eleAuthorityAckowledge")); for(int i =
	 * 0; i<sApprovalCount; i++ ) { status =
	 * common.SafeAction(Common.o.getObject("eleAuthorityApprove"), "", "ele");
	 * status = common.SafeAction(Common.o.getObject("eleAlertPopUp"), "", "ele");
	 * status = common.SafeAction(Common.o.getObject("eleRiskOkButton"), "", "ele");
	 * } break; } } else { logger.info("System does not any special approval");
	 * status = common.SafeAction(Common.o.getObject("eleRiskAnalysisTab"),
	 * "eleRiskAnalysisTab", "ele"); } if(!status) {
	 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
	 * PCThreadCache.getInstance().getProperty("methodName"),
	 * "System should approve all the request","System not approved all the request"
	 * , "FAIL"); } return status; }
	 */

	public boolean ApproveCount() {
		int iUnderWriterReferalExist = 0;
		boolean status = false;
		boolean SearchString = false;
		String sApproveType = null;
		// status =
		// common.WaitUntilClickable(Common.o.getObject("eleSPLApprvoalAssertTable"),
		// Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
		iUnderWriterReferalExist = common.ElementSize(Common.o.getObject("eleSPLApprvoalAssertTable"));
		try {
			if (iUnderWriterReferalExist == 1) {
				status = common.SafeAction(Common.o.getObject("eleRiskAnalysisPage"), "", "ele");
				status = common.WaitUntilClickable(Common.o.getObject("eleRiskAnalysis"), Integer.valueOf

(HTML.properties.getProperty("VERYLONGWAIT")));
				int rowCount = SCRCommon.TableRowCount(Common.o.getObject("eleApproveButtonType"));
				for (int i = 1; i <= rowCount - 1; i++) {
					WebElement mytable = driver.findElement(Common.o.getObject("eleApproveButtonType"));
					List<WebElement> allrows = mytable.findElements(By.tagName("tr"));
					List<WebElement> Cells = allrows.get(1).findElements(By.tagName("td"));
					List<WebElement> CellElements = Cells.get(4).findElements(By.tagName("a"));
					inner: for (WebElement element : CellElements) {
						SearchString = false;
						sApproveType = element.getText();
						if (sApproveType.contains("Approve") || sApproveType.contains("Special Approve") || 

sApproveType.contains("Authority Acknowledge")) {
							SearchString = true;
							switch (sApproveType) {
							case "Approve":
								logger.info("Heading to Approve");
								status = common.SafeAction(Common.o.getObject("eleApproveButton"), "", "ele");
								status = common.SafeAction(Common.o.getObject("eljCommonOk"), "", "ele");
								break;
							case "Special Approve":
								logger.info("Heading to Special Approval");
								status = common.SafeAction(Common.o.getObject("eleSpecialApproveButton"), "", 

"ele");
								status = common.SafeAction(Common.o.getObject("eleAlertPopUp"), "", "ele");
								status = common.SafeAction(Common.o.getObject("eljCommonOk"), "", "ele");
								break;
							case "Authority Acknowledge":
								logger.info("Heading to Authority Acknowledgement");
								status = common.SafeAction(Common.o.getObject("eleAuthorityApprove"), "", "ele");
								status = common.SafeAction(Common.o.getObject("eleAlertPopUp"), "", "ele");
								status = common.SafeAction(Common.o.getObject("eleRiskOkButton"), "", "ele");
								break;
							}
						}
						if (SearchString) {
							break inner;
						}
					}
					if (!SearchString) {
						logger.info("Unable to find the special approval");
						SearchString = true;
						break;
					}
				}
			} else {
				logger.info("System does not any special approval");
				status = common.SafeAction(Common.o.getObject("eleRiskAnalysisTab"), "eleRiskAnalysisTab", "ele");
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (!status) {
				try {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should approve all the request", "System not approved all the request", "FAIL");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} finally {
			status = true;
			SearchString = true;
		}
		return status;
	}

	/**
	 * @function This function use to get the text from the approve button type to
	 *           approve the reques
	 * @param obj
	 * @param readTextCol
	 * @param strReadString
	 * @return readText
	 * @throws Exception
	 */
	public String GetTextFromTableApproval(By obj, int readTextCol, int sRow, String strReadString, String sTagname) throws Exception {
		boolean SearchString = false;
		String[] sReadString = strReadString.split(":::");
		String readText = null;
		WebElement mytable = driver.findElement(obj);
		List<WebElement> allrows = mytable.findElements(By.tagName("tr"));
		for (int i = 0; i <= allrows.size() - 1; i++) {
			List<WebElement> Cells = allrows.get(sRow).findElements(By.tagName("td"));
			List<WebElement> CellElements = Cells.get(readTextCol).findElements(By.tagName(sTagname));
			for (WebElement element : CellElements) {
				readText = element.getText();
				if (readText.contains(sReadString[0]) || readText.contains(sReadString[1]) || readText.contains(sReadString[2])) 

{
					SearchString = true;
					break;
				}
			}
			if (SearchString == true) {
				break;
			}
		}
		if (SearchString) {
			logger.info("Search String available in the table. '" + strReadString + "'");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "System should search string in table and Search string is '" + strReadString + "'", "System searched string in table and search string is  '" + strReadString + "'", "PASS");
		} else {
			logger.info("Search String not available in the table. '" + strReadString + "'");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search string in table and Search string is '" + strReadString + "'", "System searched string in table and search string is  '" + strReadString + "'", "FAIL");
			SearchString = true;
		}
		return readText;
	}

	/**
	 * @function This function use to get the text from the approve button type to
	 *           approve the reques
	 * @param obj
	 * @param readTextCol
	 * @param strReadString
	 * @return readText
	 * @throws Exception
	 */
	public String approvalTable(By obj, int readTextCol, int sRow, String strReadString, String sTagname) throws Exception {
		boolean SearchString = false;
		String[] sReadString = strReadString.split(":::");
		String readText = null;
		WebElement mytable = driver.findElement(obj);
		List<WebElement> allrows = mytable.findElements(By.tagName("tr"));
		for (int i = 0; i <= allrows.size() - 1; i++) {
			List<WebElement> Cells = allrows.get(sRow).findElements(By.tagName("td"));
			List<WebElement> CellElements = Cells.get(readTextCol).findElements(By.tagName(sTagname));
			for (WebElement element : CellElements) {
				readText = element.getText();
				if (readText.contains(sReadString[0]) || readText.contains(sReadString[1]) || readText.contains(sReadString[2])) 

{
					SearchString = true;
					break;
				}
			}
			if (SearchString == true) {
				break;
			}
		}
		if (SearchString) {
			logger.info("Search String available in the table. '" + strReadString + "'");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "System should search string in table and Search string is '" + strReadString + "'", "System searched string in table and search string is  '" + strReadString + "'", "PASS");
		} else {
			logger.info("Search String not available in the table. '" + strReadString + "'");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "System should search string in table and Search string is '" + strReadString + "'", "System searched string in table and search string is  '" + strReadString + "'", "FAIL");
			SearchString = true;
		}
		return readText;
	}

	public boolean PolicyNumber() throws Exception {
		boolean status = false;
		Payment payment = new Payment();
		status = common.WaitForElementExist(Common.o.getObject("eleQuotePolicyNumber"), Integer.valueOf(HTML.properties.getProperty

("LONGWAIT")));
		if (status) {
			payment.policyNumber = common.ReadElement(Common.o.getObject("eleQuotePolicyNumber"), Integer.valueOf

(HTML.properties.getProperty("LONGWAIT")));
			PCThreadCache.getInstance().setProperty(PCConstants.CACHE_POLICY_NUMBER, payment.policyNumber);
			payment.policyNumberSearch = payment.policyNumber.substring(payment.policyNumber.length() - 6);
			PCThreadCache.getInstance().setProperty(PCConstants.CACHE_POLICY_NUMBER_SEARCH, payment.policyNumberSearch);
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "Policy Number shold be generated", "Policy Number generated: Policy Number is '" + payment.policyNumber + "'", "PASS");
			if (HTML.properties.getProperty("EXECUTIONAPP").contains("ODS")) {
				FlatFile sReadWrite = FlatFile.getInstance();
				SCRCommon SCRCommon = new SCRCommon();
				String FlatFileName = SCRCommon.FlatFileName();
				String policyNumebrSearch = "%" + payment.policyNumberSearch;
				status = sReadWrite.write(PCThreadCache.getInstance().getProperty("TCID"), PCThreadCache.getInstance

().getProperty("methodName"), "PolicyNumber", policyNumebrSearch, "OUTPUT", FlatFileName);
			}
		}
		return status;
	}

	public boolean specificApproval1(String strFuncValue) throws NumberFormatException, Exception {
		boolean status = false;
		boolean SearchString = false;
		String sApproveType = null;
		status = common.WaitUntilClickable(Common.o.getObject("eleSPLApprvoalAssertTable"), Integer.valueOf(HTML.properties.getProperty

("VERYLONGWAIT")));
		try {
			if (status) {
				status = common.SafeAction(Common.o.getObject("eleRiskAnalysispage"), "", "ele");
				status = common.WaitUntilClickable(Common.o.getObject("eleRiskAnalysis"), Integer.valueOf

(HTML.properties.getProperty("VERYLONGWAIT")));
				int rowCount = SCRCommon.TableRowCount(Common.o.getObject("eleApproveButtonType"));
				for (int i = 1; i <= rowCount - 1; i++) {
					WebElement mytable = driver.findElement(Common.o.getObject("eleApproveButtonType"));
					List<WebElement> allrows = mytable.findElements(By.tagName("tr"));
					List<WebElement> Cells = allrows.get(i).findElements(By.tagName("td"));
					List<WebElement> CellElements = Cells.get(4).findElements(By.tagName("a"));
					inner: for (WebElement element : CellElements) {
						SearchString = false;
						sApproveType = element.getText();
						if (sApproveType.contains(strFuncValue) || sApproveType.contains(strFuncValue) || 

sApproveType.contains(strFuncValue)) {
							SearchString = true;
							switch (sApproveType) {
							case "Approve":
								logger.info("Heading to Approve");
								status = common.SafeAction(Common.o.getObject("eleApproveButton"), "", "ele");
								status = common.SafeAction(Common.o.getObject("eljCommonOk"), "", "ele");
								break;
							case "Special Approve":
								logger.info("Heading to Special Approval");
								status = common.SafeAction(Common.o.getObject("eleSpecialApproveButton"), "", 

"ele");
								status = common.SafeAction(Common.o.getObject("eleAlertPopUp"), "", "ele");
								status = common.SafeAction(Common.o.getObject("eljCommonOk"), "", "ele");
								break;
							case "Authority Acknowledge":
								logger.info("Heading to Authority Acknowledgement");
								status = common.SafeAction(Common.o.getObject("eleAuthorityApprove"), "", "ele");
								status = common.SafeAction(Common.o.getObject("eleAlertPopUp"), "", "ele");
								status = common.SafeAction(Common.o.getObject("eleRiskOkButton"), "", "ele");
								break;
							}
						}
						if (SearchString) {
							break inner;
						}
					}
					/*
					 * if(!SearchString){ logger.info("Unable to find the special approval");
					 * SearchString = true; break; }
					 */
				}
				status = common.SafeAction(Common.o.getObject("eleViewQuote"), "", "eleViewQuote");
			} else {
				logger.info("System does not any special approval");
				status = common.SafeAction(Common.o.getObject("eleRiskAnalysisTab"), "eleRiskAnalysisTab", "ele");
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (!status) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should approve all the request", "System not approved all the request", "FAIL");
			}
		} finally {
			status = true;
			SearchString = true;
		}
		return status;
	}

	public boolean specificApproval(String strFuncValue) throws NumberFormatException, Exception {
		boolean status = false;
		JavascriptExecutor js = (JavascriptExecutor) ManagerDriver.getInstance().getWebDriver();
		status = common.WaitUntilClickable(Common.o.getObject("eleSPLApprvoalAssertTable"), Integer.valueOf(HTML.properties.getProperty

("VERYLONGWAIT")));
		try {
			if (status) {
				status = common.SafeAction(Common.o.getObject("eleRiskAnalysispage"), "", "ele");
				status = common.WaitUntilClickable(Common.o.getObject("eleRiskAnalysis"), Integer.valueOf

(HTML.properties.getProperty("VERYLONGWAIT")));
				List<WebElement> approveCount = driver.findElements(Common.o.getObject("ele" + strFuncValue + ""));
				if (approveCount.size() > 0) {
					for (WebElement webElement : approveCount) {
						// status = common.SafeAction(Common.o.getObject("ele"+strFuncValue+""), "",
						// "ele"+strFuncValue+"");
						switch (strFuncValue) {
						case "UWApprove":
							logger.info("Heading to Approve");
							// status = common.SafeAction(Common.o.getObject("eleApproveButton"), "",
							// "ele");
							webElement.click();
							status = SCRCommon.JavaScript(js);
							status = common.SafeAction(Common.o.getObject("eljCommonOk"), "", "ele");
							break;
						case "UWSpecialApprove":
							logger.info("Heading to Special Approval");
							// status = common.SafeAction(Common.o.getObject("eleSpecialApproveButton"), "",
							// "ele");
							webElement.click();
							status = SCRCommon.JavaScript(js);
							status = common.SafeAction(Common.o.getObject("eleAlertPopUp"), "", "ele");
							status = common.SafeAction(Common.o.getObject("eljCommonOk"), "", "ele");
							break;
						case "UWAuthorityAcknowledgement":
							logger.info("Heading to Authority Acknowledgement");
							// status = common.SafeAction(Common.o.getObject("eleAuthorityApprove"), "",
							// "ele");
							webElement.click();
							status = SCRCommon.JavaScript(js);
							status = common.SafeAction(Common.o.getObject("eleAlertPopUp"), "", "ele");
							status = common.SafeAction(Common.o.getObject("eleRiskOkButton"), "", "ele");
							break;
						}
					}
					status = common.SafeAction(Common.o.getObject("eleViewQuote"), "", "eleViewQuote");
				} else {
					logger.info("No " + strFuncValue + " UW buttons are available");
				}
			} else {
				logger.info("There is no UW issue to be done");
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (!status) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should approve all the request", "System not approved all the request", "FAIL");
			}
		} finally {
			status = true;
		}
		return status;
	}

	/**
	 * @function Verify the warning and error messages on screen
	 * @param sValue
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean captureErrorMsg(String sValue) throws Exception {
	 loggers  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
		boolean status = false;
		String blnPrintStatus = null;
		String refClassName = "ERROR::WARNING::INFO";
		String strXpath = null;
		Common common = CommonManager.getInstance().getCommon();
		/*
		 * String expectedText = null; String refClassName=null; String[]
		 * arrMessgage=sValue.split("::"); String[]
		 * expWarnignMsg=arrMessgage[1].split(":::");
		 */
		List<String> a = new ArrayList<String>();
		// ArrayList actWarningMsg=new ArrayList();
		List<WebElement> CellElements = ManagerDriver.getInstance().getWebDriver().findElements(Common.o.getObject("eleValidationMsg"));
		String[] arvalidate = refClassName.split("::");

		if (CellElements.size() == 0) {
			loggers.info("No Error/Warning messages in screen to validate");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "Valdiate any error/Warning is displayed on screen", "No warning message is displayed on screen", "PASS");
			status = true;
			return status;
		}
		for (int i = 0; i < arvalidate.length; i++) {
			switch (arvalidate[i].toUpperCase()) {
			case "ERROR":
				refClassName = "error_icon";
				blnPrintStatus = "PASS";
				strXpath = "//div[img[@class='error_icon']]";
				break;
			case "WARNING":
				refClassName = "warning_icon";
				blnPrintStatus = "WARNING";
				strXpath = "//div[img[@class='warning_icon']]";
				break;
			case "INFO":
				refClassName = "info_icon";
				blnPrintStatus = "PASS";
				strXpath = "//div[img[@class='info_icon']]";
				break;

			}
			for (WebElement obj : CellElements) {

				List<WebElement> Cells = obj.findElements(By.xpath(strXpath));
				if (Cells.size() == 0) {
					loggers.info("NO " + arvalidate[i] + "displayed on screen");

				} else {
					for (int j = 0; j < Cells.size(); j++) {

						String actWarningMsg = Cells.get(j).getText();
						loggers.info(actWarningMsg);
						HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), 

PCThreadCache.getInstance().getProperty("methodName"), "Valdiate any " + arvalidate[i] + " is displayed on screen", "Error/Warning message ----- " + actWarningMsg + " ------is displayed on screen", blnPrintStatus);
					}

				}

			}

		}
		if (common.ElementEmpty(Common.o.getObject("eleValidation_ClearBtn"))) {
			status = common.SafeAction(Common.o.getObject("eleValidation_ClearBtn"), "eleValidation_ClearBtn", 

"eleValidation_ClearBtn");
		}
		return status;

	}
}