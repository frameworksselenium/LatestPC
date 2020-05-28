
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

public class LineCoverage {

	public static String sheetname = "LineCoverage";
	Common common = CommonManager.getInstance().getCommon();
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));

	public Boolean SCRLineCoverage() throws Exception {

		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

	/**
	 * Function to Fill the Covergae details
	 * 
	 * @param sFuncValue
	 * @return boolean
	 */
	public Boolean FillCovergeDetails(String sFuncValue) {
		boolean status = false;
		String[] sValue = sFuncValue.split(":::");
		List<WebElement> abcd = ManagerDriver.getInstance().getWebDriver().findElements(By.xpath(".//div[@class='x-component x-fieldset-header-text x-component-default']"));
		for (WebElement sText : abcd) // looping into legend
		{
			String sElementText = sText.getText();
			if (sElementText.equals(sValue[0])) {
				String sID = sText.getAttribute("id");
				String[] sSplit = sID.split("-");
				// for some cases select
				/*
				 * 
				 * By sDivId = By.id(sSplit[0]+":_checkbox"); //select the modifier try { status
				 * = common.SafeAction(sDivId, "ele", "ele"); } catch (Exception e) { // TODO
				 * Auto-generated catch block e.printStackTrace(); }
				 */

				String[] arrQues = sValue[1].split("&&"); // Q1::A1||Q2::A2
				for (int j = 0; j < arrQues.length; j++) {
					status = false;
					String[] arrOptionSet = arrQues[j].split("::");
					By sTargetID = By.id(sSplit[0] + "-body");
					WebElement sTargetLegend = common.returnObject(sTargetID);
					List<WebElement> sFieldLabel = sTargetLegend.findElements(By.tagName("label"));
					for (WebElement sLabel : sFieldLabel) {
						String sLabelValue = sLabel.getText();
						if (arrOptionSet[0].equals(sLabelValue)) // Interstate Experience Rating Modification
						{
							String sLabelID = sLabel.getAttribute("id");
							String[] sLabelIDSplit = sLabelID.split("-");
							String sLabelConcatListID = sLabelIDSplit[0].concat("-inputEl");
							try {
								// status = common.SafeAction(By.id(sLabelConcatListID), arrOptionSet[1],
								// "lst");
								status = common.SafeAction(By.id(sLabelConcatListID), arrOptionSet[1], "lsj");
								status = common.SafeAction(By.id(sLabelConcatListID), arrOptionSet[1], "lsj");
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						if (status) {
							break;
						}
					}
				}
			}
			if (status == true) {
				break;
			}
		}
		return status;

	}

	/**
	 * Function to Add Blanket/Multiple BlanketWaiver in LineCoverage
	 * 
	 * @param sFunctionValue
	 * @return boolean
	 * @throws Throwable
	 */
	public boolean AddBlanketWaiver(String sFunctionValue) throws Throwable {
		boolean status = false;
		String[] sValue = sFunctionValue.split(":::");
		int sRowCount = SCRCommon.TableRowCount(Common.o.getObject("eleBanket_tbl"));
		try {
			status = SCRCommon.DataWebTable(Common.o.getObject("eleBanket_tbl"), sRowCount, 1, "<none>", "single", "div");
			status = common.SafeAction(Common.o.getObject("edtBlanketStateAct"), sValue[0], "lst");
			// status = SCRCommon.DataWebTable(Common.o.getObject("eleBanket_tbl"),
			// sRowCount, 2, "<none>", "single", "div");
			// status = common.SafeAction(Common.o.getObject("edtPercentageCharge"),
			// sValue[1], "lst");
			status = SCRCommon.DataWebTable(Common.o.getObject("eleBanket_tbl"), sRowCount, 3, "<none>", "single", "div");
			status = common.SafeAction(Common.o.getObject("edtWCLaw"), sValue[1], "lst");

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
	 * Function to Add Scheduled items for Longshore/Multiple Scheduled items in
	 * LineCoverage
	 * 
	 * @param sFunctionValue
	 * @return boolean
	 * @throws Throwable
	 */
	public boolean AddScheduledItems(String sFunctionValue) throws Throwable {
		boolean status = false;
		String[] sValue = sFunctionValue.split(":::");
		String sCurrentDate = SCRCommon.ReturnCurrentDate();
		int sRowCount = SCRCommon.TableRowCount(Common.o.getObject("eleScheduledItemsTbl"));
		try {
			status = SCRCommon.DataWebTable(Common.o.getObject("eleScheduledItemsTbl"), sRowCount, 1, "<none>", "single", "div");
			status = common.SafeAction(Common.o.getObject("edtLongShore_State"), sValue[0], "lst");
			// status = SCRCommon.DataWebTable(Common.o.getObject("eleScheduledItemsTbl"),
			// sRowCount, 2, "", "single", "div");
			status = common.SafeAction(Common.o.getObject("edtDateOfChange"), sCurrentDate, "edt");
			status = SCRCommon.DataWebTable(Common.o.getObject("eleScheduledItemsTbl"), sRowCount, 3, "", "single", "div");
			status = common.SafeAction(Common.o.getObject("edtPercentageChange"), sValue[1], "lst");

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
	 * Function to Add Scheduled items for Voluntary Coverage/Multiple Scheduled
	 * items in LineCoverage
	 * 
	 * @param sFunctionValue
	 * @return boolean
	 * @throws Throwable
	 *//*
		 * 
		 * /*public boolean AddVoluntaryScheduledItems(String sFunctionValue) throws
		 * Throwable { boolean status = false; String[] sValue =
		 * sFunctionValue.split(":::"); int sRowCount =
		 * SCRCommon.TableRowCount(Common.o.getObject("eleVoluntaryScheduledItemsTbl"));
		 * try { status =
		 * SCRCommon.DataWebTable(Common.o.getObject("eleVoluntaryScheduledItemsTbl"),
		 * sRowCount, 1, "<none>", "single", "div"); status =
		 * common.SafeAction(Common.o.getObject("edtForm_State"), sValue[0], "lst");
		 * status =
		 * SCRCommon.DataWebTable(Common.o.getObject("eleVoluntaryScheduledItemsTbl"),
		 * sRowCount, 2, "", "single", "div"); String sCurrentDate =
		 * SCRCommon.ReturnCurrentDate(); status =
		 * common.SafeAction(Common.o.getObject("edtState"), sCurrentDate, "edt");
		 * status =
		 * SCRCommon.DataWebTable(Common.o.getObject("eleVoluntaryScheduledItemsTbl"),
		 * sRowCount, 3, "<none>", "single", "div"); status =
		 * common.SafeAction(Common.o.getObject("edtEmployees"), sValue[1], "edt");
		 * 
		 * }
		 */

	public boolean AddVoluntaryScheduledItems(String sFunctionValue) throws Throwable {
		boolean status = false;
		String[] sValue = sFunctionValue.split(":::");
		int sRowCount = SCRCommon.TableRowCount(Common.o.getObject("eleVoluntaryScheduledItemsTbl"));
		try {
			status = SCRCommon.DataWebTable(Common.o.getObject("eleVoluntaryScheduledItemsTbl"), sRowCount, 1, "<none>", "single", 

"div");
			// status = common.SafeAction(Common.o.getObject("lstAddFormState"), "", "lst");
			status = common.SafeAction(Common.o.getObject("lstAddFormState"), sValue[0], "lstAddFormState");
			// status = common.SafeAction(Common.o.getObject("lstAddFormState"), sValue[0],
			// "lst");
			// status =
			// SCRCommon.DataWebTable(Common.o.getObject("eleVoluntarySchedule_tbl"),
			// sRowCount, 2, "", "single", "div");
			status = common.SafeAction(Common.o.getObject("lstStateOfEmployment"), sValue[1], "lstStateOfEmployment");
			// status =
			// SCRCommon.DataWebTable(Common.o.getObject("eleVoluntarySchedule_tbl"),
			// sRowCount, 3, "", "single", "div");
			// status = common.SafeAction(Common.o.getObject("edjEmployees"), sValue[2],
			// "edjEmployees");
			status = common.SafeAction(Common.o.getObject("edtEmployees"), sValue[2], "edtEmployees");

			// status =
			// SCRCommon.DataWebTable(Common.o.getObject("eleVoluntaryScheduledItemsTbl"),
			// sRowCount, 1, "<none>", "single", "div");

		}

		catch (Throwable e) {
			e.printStackTrace();
			if (status == false) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should complete performing the add class", "System not completed performing the add class", "FAIL");
			}
		}
		return status;
	}

	/**
	 * Funtion to check or uncheck the coverage in Condition screen
	 * 
	 * @param strFuncValue
	 * @return boolean
	 */

	public boolean CoverageSelection(String strFuncValue) {
		boolean status = false;
		String[] sValue = strFuncValue.split(":::");
		List<WebElement> abcd = ManagerDriver.getInstance().getWebDriver().findElements(Common.o.getObject

("eleLineCov_CoverageHeading"));
		for (WebElement sText : abcd) // looping into legend
		{
			String sElementText = sText.getText();
			if (sElementText.equals(sValue[0])) {
				String sID = sText.getAttribute("id");
				String[] sSplit = sID.split("-");
				By sTargetID = By.id(sSplit[0] + ":_checkbox");
				try {
					status = common.SafeAction(sTargetID, "ele", "ele");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return status;
	}

	/**
	 * @function Verify the warning and error messages on screen
	 * @param sValue
	 * @return boolean
	 * @throws Exception
	 */
	public boolean VerifyErrWarning_ClickNext(String sValue) throws Exception {
		boolean status = true;
		boolean blnErrWarnfound = false;
		String expectedText = null;
		String refClassName = null;
		String[] arrMessgage = sValue.split("::");

		Integer x = Integer.valueOf(HTML.properties.getProperty("NORMALWAIT"));
		// status=common.WaitUntilClickable(Common.o.getObject("eleCommonErrorObject"),
		// x);

		for (int j = 0; j < arrMessgage.length; j++) {
			List<String> a = new ArrayList<String>();

			List<WebElement> CellElements = ManagerDriver.getInstance().getWebDriver().findElements(Common.o.getObject

("eleCommonErrorObject"));
			if (arrMessgage[j].toUpperCase().contentEquals("ERROR")) {
				refClassName = "error_icon";

			} else if (arrMessgage[j].toUpperCase().contentEquals("WARNING")) {
				refClassName = "warning_icon";
			}
			if (CellElements.size() == 0) {
				logger.info("No Error/Warning messages in screen to validate");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Expected error text should matching with actual text '" + expectedText + "'", "No " + arrMessgage[j] + " messgae in the screen to validate", "PASS");
				status = true;
				// status=common.SafeAction(Common.o.getObject("eleNext"),"ele","ele");
			}
			for (int i = 0; i <= CellElements.size() - 1; i++) {

				List<WebElement> Cells = CellElements.get(i).findElements(By.className(refClassName));
				if (Cells.size() != 0) {
					String actWarningMsg = CellElements.get(i).getText();
					a.add(actWarningMsg);
					logger.info(actWarningMsg);
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Validate for any Error/Warning Dispalyed", "Error/Warning displayed -" + actWarningMsg, "PASS");
					blnErrWarnfound = true;
				}
			}

		}
		if (blnErrWarnfound) {
			status = common.SafeAction(Common.o.getObject("eleNext"), "ele", "ele");
		}

		return status;

	}

	/**
	 * 
	 * @param sFunctionValue
	 * @return
	 * @throws Throwable
	 */
	/*
	 * public boolean AddBlanketWaiver(String sFunctionValue) throws Throwable {
	 * boolean status = false; String[] sValue = sFunctionValue.split(":::"); int
	 * sRowCount = SCRCommon.TableRowCount(Common.o.getObject("eleBanket_tbl")); try
	 * { status = SCRCommon.DataWebTable(Common.o.getObject("eleBanket_tbl"),
	 * sRowCount, 1, "<none>", "single", "div"); status =
	 * common.SafeAction(Common.o.getObject("edtBlanketStateAct"), sValue[0],
	 * "lst"); status = SCRCommon.DataWebTable(Common.o.getObject("eleBanket_tbl"),
	 * sRowCount, 2, "2", "single", "div"); status =
	 * common.SafeAction(Common.o.getObject("edtPercentageCharge"), sValue[2],
	 * "lst"); status = SCRCommon.DataWebTable(Common.o.getObject("eleBanket_tbl"),
	 * sRowCount, 3, "<none>", "single", "div"); status =
	 * common.SafeAction(Common.o.getObject("edtPercentageCharge"), sValue[3],
	 * "lst");
	 * 
	 * } catch(Throwable e) { e.printStackTrace(); if(status == false) {
	 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
	 * PCThreadCache.getInstance().getProperty("methodName"),
	 * "System should complete performing the add class"
	 * ,"System not completed performing the add class", "FAIL"); } } return status;
	 * }
	 */

	/**
	 * Function used to select the coverage and fill all fields in the coverage
	 * 
	 * @param sFuncValue
	 * @return boolean
	 * @throws IOException
	 */
	public Boolean selectCoverageandFill_sheetref(String sFuncValue) {
		Boolean status = false;
		try {
			status = SCRCommon.coverageSheet(sFuncValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;

	}

	/**
	 * @function use to select the check box of the particular Blanket table
	 * @param sReadString
	 * @return true/false
	 * @throws Exception
	 */
	public boolean SelectBlanket(String sReadString) throws Exception {
		boolean status = false;
		status = common.ActionOnTable(Common.o.getObject("elePolicyChangeBlanketTbl"), 1, 0, sReadString, "div");
		return status;
	}

	/**
	 * Function used to select the coverage and fill all fields in the coverage
	 * 
	 * @param sFuncValue
	 * @return boolean
	 * @throws IOException
	 */
	public Boolean lineCoverageConditions_sheetref(String sFuncValue) {
		Boolean status = false;
		try {
			status = SCRCommon.coverageSheet(sFuncValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;

	}

	/**
	 * 
	 * @param sFunctionValue
	 * @return
	 * @throws Throwable
	 */
	public boolean AddEmployeeLeasingExclusionEndorsement(String sFunctionValue) throws Throwable {
		boolean status = false;
		String[] sValue = sFunctionValue.split(":::");
		int sRowCount = SCRCommon.TableRowCount(Common.o.getObject("eleLC_EmployeeLeasingExclusionCovBody"));
		try {
			status = SCRCommon.DataWebTable(Common.o.getObject("eleLC_EmployeeLeasingExclusionCovBody"), sRowCount, 2, "<none>", 

"single", "div");
			status = common.SafeAction(Common.o.getObject("edtBlanketStateAct"), sValue[0], "lst");

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
	 * 
	 * @param sFunctionValue
	 * @return
	 * @throws Throwable
	 */
	public boolean AddEmployeeLeasingClientEndorsement(String sFunctionValue) throws Throwable {
		boolean status = false;
		String[] sValue = sFunctionValue.split(":::");
		int sRowCount = SCRCommon.TableRowCount(Common.o.getObject("eleLC_EmployeeLeasingClientCovBody"));
		try {
			status = SCRCommon.DataWebTable(Common.o.getObject("eleLC_EmployeeLeasingClientCovBody"), sRowCount, 2, "<none>", 

"single", "div");
			status = common.SafeAction(Common.o.getObject("edtBlanketStateAct"), sValue[0], "lst");

		} catch (Throwable e) {
			e.printStackTrace();
			if (status == false) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should complete performing the add class", "System not completed performing the add class", "FAIL");
			}
		}
		return status;
	}

}