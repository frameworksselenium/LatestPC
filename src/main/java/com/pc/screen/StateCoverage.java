package com.pc.screen;

import java.io.IOException;
import java.util.ArrayList;
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

public class StateCoverage {

	public static String sheetname = "StateCoverage";
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();

	public Boolean SCRStateCoverage() throws Exception {

		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

	/**
	 * Function to Add class/Multiple classes in StateCoverage
	 * 
	 * @param sFunctionValue
	 * @return boolean
	 * @throws Throwable
	 */
	public boolean AddClass(String sFunctionValue) throws Throwable {
		boolean status = false;
		String[] sValue = sFunctionValue.split(":::");
		int sRowCount = SCRCommon.TableRowCount(Common.o.getObject("eletableRowXpath"));
		try {
			if (sValue[0].contains("State Act") || sValue[0].contains("Voluntary Compensation and Employers Liability") || sValue

[0].contains("Longshore And Harbor Workers Compensation Act"))

			{
				status = SCRCommon.DataWebTable(Common.o.getObject("eletableRowXpath"), sRowCount, 1, "State Act", "single", 

"div");
				status = common.SafeAction(Common.o.getObject("edtStateAct"), sValue[0], "lst");
				status = common.SafeAction(Common.o.getObject("selAddressCode"), sValue[1], "sel");
				status = SCRCommon.DataWebTable(Common.o.getObject("eletableRowXpath"), sRowCount, 3, "", "single", "div");
				status = common.SafeAction(Common.o.getObject("eleStateCoverageLookUp"), "eleStateCoverageLookUp", "ele");
				status = common.SafeAction(Common.o.getObject("edtEnterClassCode"), sValue[2], "edt");
				int pos = sValue[2].indexOf("##");
				if (pos > 0) {
					String[] arrClsCode = sValue[2].split("##");
					status = common.SafeAction(Common.o.getObject("edtEnterClassCode"), arrClsCode[0], "edt");
					status = common.SafeAction(Common.o.getObject("edtClassification"), arrClsCode[1], "edt");
				} else if (pos == 0) {
					status = common.SafeAction(Common.o.getObject("edtEnterClassCode"), sValue[2], "edt");
				}
				status = common.SafeAction(Common.o.getObject("eleClassCodeButton"), "eleClassCodeButton", "ele");
				status = common.SafeAction(Common.o.getObject("eleSelectInput"), "eleSelectInput", "ele");
				status = SCRCommon.DataWebTable(Common.o.getObject("eletableRowXpath"), sRowCount, 7, "", "single", "div");
				status = common.SafeAction(Common.o.getObject("lstAnualPremiumEditClick"), sValue[3], 

"lstAnualPremiumEditClick");

			}
			/*
			 * else if (sValue[0].contains("Exposure Rated Stop Gap")) { status =
			 * SCRCommon.DataWebTable(Common.o.getObject("eletableRowXpath"), sRowCount, 1,
			 * sValue[0], "single", "div"); status =
			 * common.SafeAction(Common.o.getObject("edtStateAct"), sValue[0], "lst");
			 * status=common.SafeAction(Common.o.getObject("selAddressCode"), sValue[1],
			 * "sel"); status =
			 * SCRCommon.DataWebTable(Common.o.getObject("eletableRowXpath"), sRowCount, 3,
			 * "", "single", "div"); status =
			 * common.SafeAction(Common.o.getObject("eleStateCoverageLookUp"),
			 * "eleStateCoverageLookUp", "ele"); status =
			 * common.SafeAction(Common.o.getObject("edtEnterClassCode"), sValue[2], "edt");
			 * int pos=sValue[2].indexOf("##"); if(pos>0) { String[]
			 * arrClsCode=sValue[2].split("##"); status =
			 * common.SafeAction(Common.o.getObject("edtEnterClassCode"), arrClsCode[0],
			 * "edt"); status = common.SafeAction(Common.o.getObject("edtClassification"),
			 * arrClsCode[1], "edt"); } else if(pos==0) { status =
			 * common.SafeAction(Common.o.getObject("edtEnterClassCode"), sValue[2], "edt");
			 * } status = common.SafeAction(Common.o.getObject("eleClassCodeButton"),
			 * "eleClassCodeButton", "ele"); status =
			 * common.SafeAction(Common.o.getObject("eleSelectInput"), "eleSelectInput",
			 * "ele"); status =
			 * SCRCommon.DataWebTable(Common.o.getObject("eletableRowXpath"), sRowCount, 7,
			 * "", "single", "div"); status =
			 * common.SafeAction(Common.o.getObject("edtAnualPremiumEditClick"), sValue[3],
			 * "edtAnualPremiumEditClick");
			 * 
			 * }
			 */

			else {

				status = SCRCommon.DataWebTable(Common.o.getObject("eletableRowXpath"), sRowCount, 1, sValue[0], "single", 

"div");
				status = common.SafeAction(Common.o.getObject("edtStateAct"), sValue[0], "lst");
				status = common.SafeAction(Common.o.getObject("selAddressCode"), sValue[1], "sel");
				status = SCRCommon.DataWebTable(Common.o.getObject("eletableRowXpath"), sRowCount, 7, "", "single", "div");
				status = common.SafeAction(Common.o.getObject("edtAnualPremiumEditClick"), sValue[2], 

"edtAnualPremiumEditClick");

			}

		} catch (Throwable e) {
			e.printStackTrace();
			if (status == false) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should complete performing the add class", "System not completed performing the add class", "FAIL");
			}
		}
		return status;
	}

	public boolean VerifyResults(String sFuncValue) throws Exception {
		boolean status = false;
		String[] sValue = sFuncValue.split(":::");
		logger.info("Verifying the Results of QPB SHOULD NOT AFFECT THE POLICY DATA");
		String Value = null;
		switch (sValue[0].toUpperCase()) {
		case "VERIFYQPBSAVING":
			status = common.SafeAction(Common.o.getObject("eleStateCoverages"), "ele", "ele");
			Value = common.ReadElement(Common.o.getObject("eleUWCompany"), Integer.valueOf(HTML.properties.getProperty

("VERYLONGWAIT")));
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			break;
		}
		if (!status) {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "" + sValue[0] + " case should PASS", "" + sValue[0] + " case fail", "FAIL");
			status = true;
		}
		return status;
	}

	/**
	 * Function used to Click the avaialble state in table so to fill the details
	 * accordingly
	 * 
	 * @param strStateValue
	 * @return
	 * @throws Throwable
	 */
	public boolean SelectAvailableState(String strStateValue) throws Throwable {
		boolean status = false;
		status = SCRCommon.ActionOnTable(Common.o.getObject("eleStateCov_StateTbl"), 1, 1, strStateValue, "div");
		/*
		 * int sRowCount =
		 * SCRCommon.TableRowCount(Common.o.getObject("eleStateCov_StateTbl")); for(int
		 * i=0;i<sRowCount;i++) { //
		 * status=common.ActionOnTable_JS(Common.o.getObject("eleStateCov_StateTbl"), 1,
		 * 1, strStateValue, "div"); //String
		 * Actvalue=common.GetTextFromTable(Common.o.getObject("eleStateCov_StateTbl"),
		 * 1, 1, strStateValue); status =
		 * SCRCommon.ActionOnTable(Common.o.getObject("eleStateCov_StateTbl"), 1, 1,
		 * strStateValue, "div");
		 * 
		 * if(status) { break; } }
		 */

		return status;
	}

	/**
	 * Function used to Select the check box type modifiers
	 * 
	 * @param sFuncValue
	 * @return boolean
	 */
	public Boolean SelectModifier(String sFuncValue) {
		boolean status = false;
		List<WebElement> abcd = ManagerDriver.getInstance().getWebDriver().findElements(By.xpath("//legend[contains(@id,'WC7JurisdictionModifierInputSet')]//span//div[contains(@id,'legendTitle')]"));
		for (WebElement sText : abcd) // looping into legend
		{
			String sElementText = sText.getText();
			if (sElementText.equals(sFuncValue)) {
				String sID = sText.getAttribute("id");
				String[] sSplit = sID.split("-");
				By sDivId = By.id(sSplit[0] + ":_checkbox"); // select the modifier
				try {
					status = common.SafeAction(sDivId, "ele", "ele");

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return status;

	}

	/**
	 * Function used to select/check the Modifer and fill the data for fields init
	 * 
	 * @param sFuncValue
	 * @return boolean
	 * @throws IOException
	 */
	public Boolean SelectModiferAndFillDetails(String sFuncValue) throws IOException {
		boolean status = false;
		boolean QuesFound = false;
		String[] sValue = sFuncValue.split(":::");
		List<WebElement> abcd = ManagerDriver.getInstance().getWebDriver().findElements(By.xpath(".//div[@class='x-component x-fieldset-header-text x-component-default']"));
		for (WebElement sText : abcd) // looping into legend
		{
			String sElementText = sText.getText();
			if (sElementText.equals(sValue[0])) {
				String sID = sText.getAttribute("id");
				String[] sSplit = sID.split("-");
				By sDivId = By.id(sSplit[0] + ":_checkbox"); // select the modifier
				try {
					status = common.SafeAction(sDivId, "ele", "ele");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				String[] arrQues = sValue[1].split("&&"); // Q1::A1&&Q2::A2 Extended Broad Form::: &&

				for (int j = 0; j < arrQues.length; j++) {
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
								status = common.SafeAction(By.id(sLabelConcatListID), arrOptionSet[1], "lst");
								QuesFound = true;

							} catch (Exception e) {
								e.printStackTrace();
							}
						} else if (arrOptionSet[0].equalsIgnoreCase("NONE")) {
							HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), 

PCThreadCache.getInstance().getProperty("methodName"), "Verify the Coverage is Selected/removed in StateCoverage", "Select/Deselect the Coverage", "PASS");
						}
						if (QuesFound) {
							QuesFound = false;
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
	 * Function used to select the coverage and fill all fields in the coverage
	 * 
	 * @param sFuncValue
	 * @return boolean
	 * @throws IOException
	 */
	public Boolean scheduleRating_sheetref(String sFuncValue) {
		Boolean status = false;
		try {
			status = SCRCommon.scheduleRating(sFuncValue);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;

	}

	/**
	 * Function used to fill modifier details (not Check box type)
	 * 
	 * @param sFuncValue
	 * @return boolean
	 * @throws Exception
	 */
	public Boolean FillModiferQuestionAnswers(String sFuncValue) throws Exception {
		boolean status = false;
		String[] sSetOption = sFuncValue.split("@@"); // a:::B:::C&&a2:::b2:::c2
		for (int i = 0; i < sSetOption.length; i++) {
			String[] sValue = sSetOption[i].split(":::");
			status = false;
			List<WebElement> abcd = ManagerDriver.getInstance().getWebDriver().findElements(Common.o.getObject("eleModiferTable"));
			for (WebElement sText : abcd) // looping into legend
			{
				String sElementText = sText.getAttribute("id");
				By sTargetID = By.id(sElementText);
				WebElement sTargetLegend = common.returnObject(sTargetID);
				List<WebElement> sFieldLabel = sTargetLegend.findElements(By.tagName("label"));
				for (WebElement sLabel : sFieldLabel) {
					String sLabelValue = sLabel.getText();
					if (sLabelValue.equalsIgnoreCase(sValue[0])) {
						String QuestId = sLabel.getAttribute("id");
						String[] arrQuestId = QuestId.split("-");
						switch (sValue[2].toUpperCase()) {
						case "TXT":
							By sAnswerId = By.id(arrQuestId[0] + "-inputEl");
							try {
								if (sValue[0].equalsIgnoreCase("Schedule Rating Factor")) {
									status = common.SafeAction(sAnswerId, "ele", "ele");
									status = common.SafeAction(Common.o.getObject("edtStateCov_RatingFac"), 

sValue[1], "edt");
									status = common.SafeAction(Common.o.getObject

("eleStateCov_RatingFacOKbtn"), "ele", "ele");

								} else {
									status = common.SafeAction(sAnswerId, sValue[1], "lst");
								}

							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							break;
						case "RADIO":
							if (sValue[1].toUpperCase().equals("YES")) {
								By sAnswerId1 = By.id(arrQuestId[0] + "_true-inputEl");
								status = common.SafeAction(sAnswerId1, "ele", "ele");
							} else if (sValue[1].toUpperCase().equals("NO")) {
								By sAnswerId1 = By.id(arrQuestId[0] + "_false-inputEl");
								status = common.SafeAction(sAnswerId1, "ele", "ele");
							}
							break;
						}
						if (status) {
							break; // for loop label
						}
					}
				}
				if (status) {
					break; // for loop legen
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
		// status=common.SafeAction(Common.o.getObject("eleNext"),"ele","ele");
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
	 * @param strFuncValue
	 * @return
	 * @throws Exception
	 */
	public boolean FillStateIDS(String strFuncValue) throws Exception {

		String[] arrFunVal = strFuncValue.split(":::");
		boolean status = false;
		boolean blnLabelFound = false;

		// List<WebElement> CellElements =
		// ManagerDriver.getInstance().getWebDriver().findElements(By.xpath("//table[contains(@id,'SubmissionWizard:LOBWizardStepGroup:LineWizardStepSet:WC7StateCoverageScreen:WC7StateCoverageCV')]"));
		List<WebElement> CellElements = ManagerDriver.getInstance().getWebDriver().findElements(By.xpath("//table[contains(@id,'ANI_OfficialIDEntry')]"));
		// List<WebElement> CellElements =
		// ManagerDriver.getInstance().getWebDriver().findElements(By.xpath("//table[contains(@id,'SubmissionWizard:LOBWizardStepGroup:LineWizardStepSet:WC7StateCoverageScreen:WC7StateCoverageCV:WC7PolicyLinePerStateConfigDV:officialIDs')]"));

		for (int i = 0; i < CellElements.size(); i++) {
			List<WebElement> Columns_row = CellElements.get(i).findElements(By.tagName("td"));
			String strAttr = Columns_row.get(0).getAttribute("id");
			String[] arrLabel = strAttr.split("-");

			// Label id
			By sAnswerId1 = By.id(arrLabel[0] + "-labelEl");
			WebElement strLblValue = CellElements.get(i).findElement(sAnswerId1);
			String LabelHeading = strLblValue.getText();

			if (LabelHeading.contains(arrFunVal[0]))

			{
				By strAns = By.id(arrLabel[0] + "-inputEl");
				status = common.SafeAction(strAns, arrFunVal[1], "lst");
				blnLabelFound = true;
			}
			if (blnLabelFound) {
				break;
			}
			// -labelEl

		}
		return status;
	}

	public boolean AddVoluntaryScheduledItems(String sFunctionValue) throws Throwable {
		boolean status = false;
		String[] sValue = sFunctionValue.split(":::");
		int sRowCount = SCRCommon.TableRowCount(Common.o.getObject("eleVoluntarySchedule_tbl"));
		try {
			status = SCRCommon.DataWebTable(Common.o.getObject("eleVoluntarySchedule_tbl"), sRowCount, 1, "<none>", "single", "div");
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
			status = common.SafeAction(Common.o.getObject("edjEmployees"), sValue[2], "edjEmployees");

		} catch (Throwable e) {
			e.printStackTrace();
			if (status == false) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should complete performing the add class", "System not completed performing the add class", "FAIL");
			}
		}
		return status;
	}

	public boolean selectAnnualBasis(String sFunctionValue) throws Throwable {
		boolean status = false;
		String[] sValue = sFunctionValue.split(":::");
		int sRowCount = SCRCommon.TableRowCount(Common.o.getObject("elePolicyChangeAddClassTbl"));
		switch (sValue[0].toUpperCase()) {

		case "ANNUALBASIS":
			try {

				status = SCRCommon.DataWebTable(Common.o.getObject("elePolicyChangeAddClassTbl"), sRowCount, 7, "", "single", 

"div");

				status = common.SafeAction(Common.o.getObject("edtAnualPremiumEditClick"), sValue[1], 

"edtAnualPremiumEditClick");
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}

		return status;
	}

	/**
	 * @function use to select the check box of the particular class from
	 *           State_Coverage screen
	 * @param sReadString
	 * @return true/false
	 * @throws Exception
	 */

	public boolean SelectClass(String sReadString) throws Exception {
		boolean status = false;
		status = common.ActionOnTable(Common.o.getObject("elePolicyChangeAddClassTbl"), 1, 0, sReadString, "div");
		return status;
	}

	public boolean addClass_sheetref(String strFuncValue) {
		Boolean blnStatus = false;
		blnStatus = SCRCommon.tableHandling(strFuncValue, "eletableRowXpath", "eleAddClassColumnSearch", 

"eleSTAddClassCoveredEmployees");
		return blnStatus;
	}
}