
/**
 * @ClassPurpose This Class used for the AssignmentAdministration usecase
 * @Scriptor Kumar
 * @ReviewedBy
 * @ModifiedBy Sojan David
 * @LastDateModified 3/24/2017
 */
package com.pc.screen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.PCThreadCache;

public class AssignmentAdministration {

	public static String sheetname = "AssignmentAdministration";
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();

	/**
	 * @function Use to perform all the objects/elements/functions from the
	 *           AssignmentAdministration excel sheet
	 * @return true/false
	 * @throws Exception
	 */
	public Boolean SCRAssignmentAdministration() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

	/**
	 * @function use to put the assignment adminsitration table in the table
	 * @param sFuncValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean AssignmentAdministrationTable(String sFuncValue) throws Exception {
		boolean status = false;
		String sValue[] = sFuncValue.split(":::");
		int sRowCount = SCRCommon.TableRowCount(Common.o.getObject("eleAssignmentAdministrationTable"));
		try {
			switch (sValue[0].toUpperCase()) {
			case "NEW":
				status = SCRCommon.DataWebTable(Common.o.getObject("eleAssignmentAdministrationTable"), sRowCount, 1, "", "single", "div");
				status = common.SafeAction(Common.o.getObject("eleAAProducerCodeSearchIcon"), "ele", "ele");
				status = common.SafeAction(Common.o.getObject("edtAAAgencyName"), sValue[1], "edt");
				status = common.SafeAction(Common.o.getObject("eleAAProducerCodeSearchButton"), "eleAAProducerCodeSearchButton", "ele");
				status = common.SafeAction(Common.o.getObject("eleAAProducerCodeSelect"), "eleAAProducerCodeSelect", "ele");
				status = SCRCommon.DataWebTable(Common.o.getObject("eleAssignmentAdministrationTable"), sRowCount, 3, "<none>", "single", "div");
				status = common.SafeAction(Common.o.getObject("lstAssigneeRole"), sValue[2], "lst");
				status = common.SafeAction(Common.o.getObject("eleAAAssignedUserIcon"), "ele", "ele");
				status = common.SafeAction(Common.o.getObject("edtAAUserName"), sValue[3], "edt");
				status = common.SafeAction(Common.o.getObject("eleAAProducerCodeSearchButton"), "eleAAProducerCodeSearchButton", "ele");
				status = common.SafeAction(Common.o.getObject("eleAAProducerCodeSelect"), "eleAAProducerCodeSelect", "ele");
				status = SCRCommon.DataWebTable(Common.o.getObject("eleAssignmentAdministrationTable"), sRowCount, 5, "", 

"single", "div");
				status = common.SafeAction(Common.o.getObject("lstAASegment"), sValue[4], "lst");
				status = SCRCommon.DataWebTable(Common.o.getObject("eleAssignmentAdministrationTable"), sRowCount, 8, "", "single", "div");
				status = common.SafeAction(Common.o.getObject("lstEffectiveDate"), SCRCommon.ReturnCurrentDate(), "lst");
				break;
			case "CONCAT":
				status = SCRCommon.DataWebTable(Common.o.getObject("eleAssignmentAdministrationTable"), sRowCount, 1, "", "single", "div");
				status = common.SafeAction(Common.o.getObject("eleAAProducerCodeSearchIcon"), "ele", "ele");
				status = common.SafeAction(Common.o.getObject("edtAAAgencyName"), sValue[1], "edt");
				status = common.SafeAction(Common.o.getObject("eleAAProducerCodeSearchButton"), "eleAAProducerCodeSearchButton", "ele");
				status = common.SafeAction(Common.o.getObject("eleAAProducerCodeSelect"), "eleAAProducerCodeSelect", "ele");
				status = SCRCommon.DataWebTable(Common.o.getObject("eleAssignmentAdministrationTable"), sRowCount, 3, "", 

"single", "div");
				status = common.SafeAction(Common.o.getObject("lstAssigneeRole"), sValue[2], "lst");
				status = common.SafeAction(Common.o.getObject("eleAAAssignedUserIcon"), "ele", "ele");
				status = common.SafeAction(Common.o.getObject("edtAAUserName"), sValue[3], "edt");
				status = common.SafeAction(Common.o.getObject("eleAAProducerCodeSearchButton"), "eleAAProducerCodeSearchButton", "ele");
				status = common.SafeAction(Common.o.getObject("eleAAProducerCodeSelect"), "eleAAProducerCodeSelect", "ele");
				status = SCRCommon.DataWebTable(Common.o.getObject("eleAssignmentAdministrationTable"), sRowCount, 5, "", 

"single", "div");
				status = common.SafeAction(Common.o.getObject("lstAASegment"), sValue[4], "lst");
				// status =
				// SCRCommon.DataWebTable(Common.o.getObject("eleAssignmentAdministrationTable"),
				// sRowCount, 5, "", "single");
				status = common.SafeAction(Common.o.getObject("lstSIC"), sValue[5], "lst");
				// status =
				// SCRCommon.DataWebTable(Common.o.getObject("eleAssignmentAdministrationTable"),
				// sRowCount, 6, "", "single");
				// status = common.SafeAction(Common.o.getObject("lstRenewalReviewType"),
				// sValue[6], "lst");
				status = SCRCommon.DataWebTable(Common.o.getObject("eleAssignmentAdministrationTable"), sRowCount, 8, "", "single", "div");
				status = common.SafeAction(Common.o.getObject("lstEffectiveDate"), SCRCommon.ReturnCurrentDate(), "lst");
				status = SCRCommon.DataWebTable(Common.o.getObject("eleAssignmentAdministrationTable"), sRowCount, 13, "", "single", "div");
				status = common.SafeAction(Common.o.getObject("lstJobType"), sValue[6], "lst");
				break;
			}
		} catch (Throwable e) {
			e.printStackTrace();
			if (status == false) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should complete performing the add class", "System not completed performing the add class", "FAIL");
			}
		}
		return status;
	}

	/**
	 * @function edit the assignment administration table
	 * @param sFuncValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean EditAssignmentAdministrationTable(String sFuncValue) throws Exception {
		boolean status = false;
		String sValue[] = sFuncValue.split(":::");
		int sRowCount = SCRCommon.TableRowCount(Common.o.getObject("eleAssignmentAdministrationTable"));
		try {
			// status =
			// SCRCommon.DataWebTable(Common.o.getObject("eleAssignmentAdministrationTable"),
			// sRowCount, 1, "", "single");
			// status = Common.SafeAction(Common.o.getObject("eleAAProducerCodeSearchIcon"),
			// "ele", "ele");
			// status = Common.SafeAction(Common.o.getObject("edtAAAgencyName"), sValue[0],
			// "edt");
			// status =
			// Common.SafeAction(Common.o.getObject("eleAAProducerCodeSearchButton"),
			// "eleAAProducerCodeSearchButton", "ele");
			// status = Common.SafeAction(Common.o.getObject("eleAAProducerCodeSelect"),
			// "eleAAProducerCodeSelect", "ele");
			status = SCRCommon.DataWebTable(Common.o.getObject("eleAssignmentAdministrationTable"), sRowCount, 3, sValue[0], 

"single", "div");
			status = common.SafeAction(Common.o.getObject("lstAssigneeRole"), sValue[1], "lst");
			// status = Common.SafeAction(Common.o.getObject("eleAAAssignedUserIcon"),
			// "ele", "ele");
			// status = Common.SafeAction(Common.o.getObject("edtAAUserName"), sValue[2],
			// "edt");
			// status =
			// Common.SafeAction(Common.o.getObject("eleAAProducerCodeSearchButton"),
			// "eleAAProducerCodeSearchButton", "ele");
			// status = Common.SafeAction(Common.o.getObject("eleAAProducerCodeSelect"),
			// "eleAAProducerCodeSelect", "ele");
			// status =
			// SCRCommon.DataWebTable(Common.o.getObject("eleAssignmentAdministrationTable"),
			// sRowCount, 4, sValue[2], "single");
			// status = Common.SafeAction(Common.o.getObject("lstAASegment"), sValue[3],
			// "lst");
			// status =
			// SCRCommon.DataWebTable(Common.o.getObject("eleAssignmentAdministrationTable"),
			// sRowCount, 7, "", "single");
			// status = Common.SafeAction(Common.o.getObject("lstEffectiveDate"),
			// SCRCommon.ReturnCurrentDate(), "lst");
		} catch (Throwable e) {
			e.printStackTrace();
			if (status == false) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should complete performing the add class", "System not completed performing the add class", "FAIL");
			}
		}
		return status;
	}

	/**
	 * @function use to select the assignment
	 * @param sFuncValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean SelectAssignment(String sFuncValue) throws Exception {
		boolean status = false;
		/*
		 * String value[]=sFuncValue.split(":::"); if(value.length==1) { status =
		 * common.ActionOnTable(Common.o.getObject("eleAssignmentAdministrationTable"),
		 * 1, 0, sFuncValue, "", "div"); } boolean status = false;
		 */
		String[] arrCovName = sFuncValue.split(":::");
		int iRowCnt = 0;
		try {
			// status=common.SafeAction(Common.o.getObject("eljBlanketPopup_AddBlanket"),
			// "eljBlanketPopup_AddBlanket", "eljBlanketPopup_AddBlanket");
			iRowCnt = SCRCommon.TableRowCount(Common.o.getObject("eleAssignmentAdministrationTable"));
			if (iRowCnt > 0) {
				if (!(arrCovName[0].toUpperCase().equals("ALL"))) {
					for (int i = 0; i < arrCovName.length; i++) {
						status = ActionOnTable1(Common.o.getObject("eleAssignmentAdministrationTable"), 1, 0, arrCovName

[i], "img");
					}

				} else if (arrCovName[0].toUpperCase().equals("ALL")) {
					status = common.SafeAction(Common.o.getObject("eleAssignmentAll"), "ele", "eleAssignmentAll");
				}

			} else {
				logger.info("No records available in Assignment Table!!");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Select the users and Role in Assignment Admin Screen", "No users displayed in Assignment Table", "WARNING");
				status = false;
				// status=common.SafeAction(Common.o.getObject("eljBlanketPopup_ReturnLink"),
				// "elj", "eljBlanketPopup_ReturnLink");

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return status;
	}

	public static boolean verifyErrorMsgUp(String sValue) throws IOException {
		loggers  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty

("TCID"));
		int c = 0;
		boolean status = false;
		Common common = CommonManager.getInstance().getCommon();
		List<String> a = new ArrayList<String>();
		String[] expMsg = null;
		boolean matchStatus = false;
		// String expectedText=null;
		if (common.ElementSize(Common.o.getObject("eleAssigmentErrorBanner")) == 0) {
			loggers.info("No Error/Warning messages in screen to validate");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "Valdiate any error/Warning is displayed on screen", "No warning message is displayed on screen", "PASS");
			// status=true;
			return status;
		} else {
			WebElement eleErrtble = ManagerDriver.getInstance().getWebDriver().findElement(Common.o.getObject

("eleAssigmentErrorBanner"));
			List<WebElement> eleDiv = eleErrtble.findElements(By.className("message")); // all div
			List<WebElement> eleErrIcon = eleErrtble.findElements(By.tagName("img")); // all div
			expMsg = sValue.split("::");

			for (String expectedText : expMsg) {
				loggers.info("Expected Message - " + expectedText);
				String type = null;
				for (int i = 0; i < eleDiv.size(); i++) {
					matchStatus = false;
					String actWarningMsg = eleDiv.get(i).getText();
					String TypeMsg = eleErrIcon.get(i).getAttribute("class");
					String[] Msgtype = TypeMsg.split("_");
					type = Msgtype[0];
					if (expectedText.contains(actWarningMsg) && Msgtype[0].equals("error")) {
						c++;
						matchStatus = true;
						loggers.info("Expected " + Msgtype[0] + " Message is matching with actual message '" + 

expectedText + "'");
						HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), 

PCThreadCache.getInstance().getProperty("methodName"), "Expected error text should matching with actual text '" + expectedText + "'", "Expected error text is matching with actual text '" + expectedText + "'", "PASS");
						break;
					}

				}
				if (!matchStatus) {
					loggers.info("Expected " + type + " Message is NOT matching with actual message '" + expectedText + "'");
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Expected error text should matching with actual text '" + expectedText + "'", "Expected error text is not matching with actual text '" + expectedText + "'", "FAIL");
					break;
				}
				if (c == expMsg.length) {
					// HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
					// PCThreadCache.getInstance().getProperty("methodName"), "Expected error text
					// should matching with actual text '" + expectedText + "'","Expected error text
					// is matching with actual text '" + expectedText + "'", "PASS");
					status = true;
					break;
				}

			}
			if (!status) {
				loggers.info("Number of failed Expected Message: " + (expMsg.length - c) + " , In out of Total " + expMsg.length + " Expected Message ");
			}

		}

		return status;

	}

	public Boolean ActionOnTable1(By obj, int readCol, int actionCol, String strReadString, String sTagName) throws Exception {
		boolean Status = false;
		boolean SearchString = false;
		boolean ActionObject = false;
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
					// WebElement sElement = returnObject(By.id(readAttriID1));
					Status = true;
					ActionObject = true;
					element.click();
					break;
					// }
				}
			}
			// if(ActionObject == true)
			// {
			// break;
			// }

		}
		if (SearchString) {
			logger.info("Search String available in the table. '" + strReadString + "'");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "System should search string in table and Search string is '" + strReadString + "'", "System searched string in table and srarch string is  '" + strReadString + "'", "PASS");
			if (ActionObject) {
				logger.info("Search and click on object in the table cell and object name is '" + strReadString + "'");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should search object in the table cell and click on object. Object name is '" + strReadString + "'", 

"System searched object in the table and clicked on object. object name is '" + strReadString + "'", "PASS");
				Status = true;
			} else {
				logger.info("Search and click on object in the table cell and object name is '" + strReadString + "'");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should search object in the table cell and click on object. Object name is '" + strReadString + "'", 

"System searched object in the table and clicked on object. object name is '" + strReadString + "'", "FAIL");
				Status = false;
			}
		} else {
			logger.info("Search String not available in the table. '" + strReadString + "'");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "System should search string in table and Search string is '" + strReadString + "'", "System searched string in table and srarch string is  '" + strReadString + "'", "FAIL");
			Status = false;
		}
		return Status;
	}

	/**
	 * 
	 * @param sFuncValue
	 * @return
	 * @throws Exception
	 */
	public boolean SBACUWAssignmentTable(String sFuncValue) throws Exception {
		boolean status = false;
		String sValue[] = sFuncValue.split(":::");
		int sRowCount = SCRCommon.TableRowCount(Common.o.getObject("eleSBACUWAssignmentTable"));
		try {
			switch (sValue[0].toUpperCase()) {
			case "NEW":
				status = SCRCommon.DataWebTable(Common.o.getObject("eleSBACUWAssignmentTable"), sRowCount, 1, "", "single", "div");
				status = common.SafeAction(Common.o.getObject("edtSBACUWAgentMail"), sValue[1], "edt");
				status = SCRCommon.DataWebTable(Common.o.getObject("eleSBACUWAssignmentTable"), sRowCount, 3, "", "single", "div");
				status = common.SafeAction(Common.o.getObject("edtSBACUWAgentAgentFirstName"), sValue[2], "edt");
				status = SCRCommon.DataWebTable(Common.o.getObject("eleSBACUWAssignmentTable"), sRowCount, 4, "", "single", "div");
				status = common.SafeAction(Common.o.getObject("edtSBACUWAgentAgentLastName"), sValue[3], "edt");
				status = SCRCommon.DataWebTable(Common.o.getObject("eleSBACUWAssignmentTable"), sRowCount, 5, "", "single", "div");
				status = common.SafeAction(Common.o.getObject("eleAAAssignedUserIcon"), "ele", "ele");
				status = common.SafeAction(Common.o.getObject("edtAAUserName"), sValue[4], "edt");
				status = common.SafeAction(Common.o.getObject("eleAAProducerCodeSearchButton"), "eleAAProducerCodeSearchButton", "ele");
				status = common.SafeAction(Common.o.getObject("eleAAProducerCodeSelect"), "eleAAProducerCodeSelect", "ele");
				break;
			case "UPDATE":
				status = SCRCommon.DataWebTable(Common.o.getObject("eleSBACUWAssignmentTable"), sRowCount, 1, "", "single", "div");
				status = common.SafeAction(Common.o.getObject("edtSBACUWAgentMail"), sValue[0], "edt");
				status = SCRCommon.DataWebTable(Common.o.getObject("eleSBACUWAssignmentTable"), sRowCount, 3, "", "single", "div");
				status = common.SafeAction(Common.o.getObject("edtSBACUWAgentAgentFirstName"), sValue[1], "edt");
				status = SCRCommon.DataWebTable(Common.o.getObject("eleSBACUWAssignmentTable"), sRowCount, 4, "", "single", "div");
				status = common.SafeAction(Common.o.getObject("edtSBACUWAgentAgentLastName"), sValue[2], "edt");
				break;
			}
		} catch (Throwable e) {
			e.printStackTrace();
			if (status == false) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should complete performing the add class", "System not completed performing the add class", "FAIL");
			}
		}
		return status;
	}

	/**
	 * @function Verifying the results of the cases
	 * @param sFuncValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean VerifyResults(String sFuncValue) throws Exception {
		boolean status = false;
		String[] sValue = sFuncValue.split(":::");
		logger.info("Verifying the Results");
		String Value = null;
		switch (sValue[0].toUpperCase()) {
		case "VERIFYCREATEDASSIGNMENT":
			logger.info(sValue[0]);
			Value = common.GetTextFromTable(Common.o.getObject("eleAssignmentAdministrationTable"), 1, sValue[1]);
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			Value = common.GetTextFromTable(Common.o.getObject("eleAssignmentAdministrationTable"), 3, sValue[2]);
			status = common.CompareStringResult(sValue[0], sValue[2], Value);
			Value = common.GetTextFromTable(Common.o.getObject("eleAssignmentAdministrationTable"), 4, sValue[3]);
			status = common.CompareStringResult(sValue[0], sValue[3], Value);
			Value = common.GetTextFromTable(Common.o.getObject("eleAssignmentAdministrationTable"), 5, sValue[4]);
			status = common.CompareStringResult(sValue[0], sValue[4], Value);
			break;
		case "VERIFYEDITEDASSIGNMENT":
			logger.info(sValue[0]);
			Value = common.GetTextFromTable(Common.o.getObject("eleAssignmentAdministrationTable"), 3, sValue[1]);
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			// Value =
			// Common.GetTextFromTable(Common.o.getObject("eleAssignmentAdministrationTable"),
			// 4, sValue[2]);
			// status = Common.CompareStringResult(sValue[0], sValue[2], Value);
			break;
		case "VERIFYDELETERESULTS":
			logger.info(sValue[0]);
			Value = common.ReadElement(Common.o.getObject("eleZeroResultsErrorMessage"), Integer.valueOf(HTML.properties.getProperty

("VERYLONGWAIT")));
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			break;
		case "VERIFYNEWASSIGNEE":
			logger.info(sValue[0]);
			Value = common.GetTextFromTable(Common.o.getObject("eleAssignmentAdministrationTable"), 4, sValue[1]);
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			break;
		/*
		 * case "VERIFYSBACUWCREATEDASSIGNMENT": Value =
		 * Common.GetTextFromTable(Common.o.getObject("eleSBACUWAssignmentTable"), 1,
		 * sValue[1]); status = Common.CompareStringResult(sValue[0], sValue[1], Value);
		 * Value =
		 * Common.GetTextFromTable(Common.o.getObject("eleSBACUWAssignmentTable"), 3,
		 * sValue[2]); status = Common.CompareStringResult(sValue[0], sValue[2], Value);
		 * Value =
		 * Common.GetTextFromTable(Common.o.getObject("eleSBACUWAssignmentTable"), 4,
		 * sValue[3]); status = Common.CompareStringResult(sValue[0], sValue[3], Value);
		 * Value =
		 * Common.GetTextFromTable(Common.o.getObject("eleSBACUWAssignmentTable"), 5,
		 * sValue[4]); status = Common.CompareStringResult(sValue[0], sValue[4], Value);
		 * break;
		 */
		case "VERIFYCONCATRESULTS":
			logger.info(sValue[0]);
			Value = common.GetTextFromTable(Common.o.getObject("eleAssignmentAdministrationTable"), 10, sValue[1]);
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			break;
		case "VERIFYASSIGNEDUSER":
			logger.info(sValue[0]);
			Value = common.GetTextFromTable(Common.o.getObject("eleAASelectUser"), 1, sValue[1]);
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			break;
		}
		if (!status) {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "" + sValue[0] + " case should PASS", "" + sValue[0] + " case should not fail", "FAIL");
			status = true;
		}
		return status;
	}

	/**
	 * @function use to delete the assignment adminstration prequest
	 * @param sFuncValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean preRequestCheck(String sFuncValue) throws Exception {
		boolean status = false;
		String[] sValue = sFuncValue.split(":::");
		String value = null;
		int elementSize = 0;
		switch (sValue[0].toUpperCase()) {
		case "ASSIGNMENTADMINISTRATION":
			elementSize = common.ElementSize(Common.o.getObject("eleZeroResultMsg"));
			if (elementSize == 0) {
				logger.info("Assignment Administration User is available hence deleteing it");
				status = common.SafeAction(Common.o.getObject("eleAAEdit"), "", "eleAAEdit");
				value = common.GetTextFromTable(Common.o.getObject("eleAssignmentAdministrationTable"), 4, sValue[1]);
				if (value.equalsIgnoreCase(sValue[1])) {
					status = common.ActionOnTable(Common.o.getObject("eleAssignmentAdministrationTable"), 4, 0, sValue[1], "", "div");
					status = common.SafeAction(Common.o.getObject("eleAADelete"), "", "eleAADelete");
					status = common.SafeAction(Common.o.getObject("eleAlertPopUp"), "", "eleAlertPopUp");
				}
			} else {
				status = true;
				logger.info("Assignment Administration PreUser is not available");
			}
			break;
		}

		return status;
	}

	
	public boolean openForm(String FormName) throws Exception {
		Boolean blnStatus = false;
		blnStatus = common.ActionOnTable(Common.o.getObject("eleFormTable"), 1, 1, FormName, "a");
		return blnStatus;
		}
}