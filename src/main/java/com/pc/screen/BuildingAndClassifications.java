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
import com.pc.utilities.XlsxReader;

public class BuildingAndClassifications {

	public static String sheetname = "BuildingAndClassifications";
	Common common = CommonManager.getInstance().getCommon();
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();

	public Boolean SCRBuildingAndClassifications() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);

		if (!status) {
			status = false;
		}
		return status;
	} 

	/**
	 * Function used to Select the lcoation specific - Add building
	 * 
	 * @param funValue
	 * @return boolean
	 * @throws Exception
	 */

	public boolean AddBuildingSpecificLoc(String funValue) throws Exception {
		boolean status = false;
		boolean blnStatefnd = false;
		int MulListSize = 0;
		List<WebElement> ObjMulLocList = null;
		String sAddress = null;
		try {
			// status=common.SafeAction(Common.o.getObject("eljBldClas_AddBuilding"), "ele",
			// "eljBldClas_AddBuilding");
			// check for the element exists
			MulListSize = common.ElementSize(Common.o.getObject("eleMulitpleLocList"));
			if (MulListSize > 0) {
				logger.info("Multiple Location exist to Add Building details. Please select the specific Location to add Building.");
				ObjMulLocList = ManagerDriver.getInstance().getWebDriver().findElements(Common.o.getObject("eleMulitpleLocList"));
				lblFor: for (WebElement eList : ObjMulLocList) {
					sAddress = eList.getText();
					if (funValue.length() > 0 && funValue.length() == 2) // passing State code
					{
						if (sAddress.toUpperCase().endsWith(funValue.toUpperCase())) {
							blnStatefnd = true;
							logger.info("State -" + funValue.toUpperCase() + " available in AddBuilding List.Hence selecting the state to add building");

							status = common.SafeAction(By.id(eList.getAttribute("id")), "elj", "eljLocationDropdown" + funValue.toUpperCase() + "AddBuilding");
							break lblFor;
						}
					} else // address given in excel
					{
						if (sAddress.toUpperCase().contains(funValue.toUpperCase())) {
							blnStatefnd = true;
							logger.info("Address -" + funValue.toUpperCase() + " available in AddBuilding List.Hence selecting the state to add building");
							status = common.SafeAction(By.id(eList.getAttribute("id")), "ele", "LocationDropdown " + funValue.toUpperCase() + " - AddBuilding");
							break lblFor;
						}

					}

				}
				if (!blnStatefnd) {
					logger.info("State/Address  -" + funValue.toUpperCase() + " is NOT available in AddBuilding List.");
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "State/Address " + funValue.toUpperCase() + "should be available in AddBuildingList", "State/Address  is not available in AddBuilding List value", "PASS");
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * 
	 * @param funValue
	 * @return
	 * @throws Exception
	 */
	public static boolean verifyErrorMsgUp(String sValue) throws IOException {
		loggers  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
		int c = 0;
		boolean status = false;
		Common common = CommonManager.getInstance().getCommon();
		List<String> a = new ArrayList<String>();
		String[] expMsg = null;
		boolean matchStatus = false;
		// String expectedText=null;
		if (common.ElementSize(Common.o.getObject("eleErrorBanner")) == 0) {
			loggers.info("No Error/Warning messages in screen to validate");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Valdiate any error/Warning is displayed on screen", "No warning message is displayed on screen", "PASS");
			// status=true;
			return status;
		} else {
			WebElement eleErrtble = ManagerDriver.getInstance().getWebDriver().findElement(Common.o.getObject("eleErrorBanner"));
			List<WebElement> eleDiv = eleErrtble.findElements(By.className("message")); // all div
			List<WebElement> eleErrIcon = eleErrtble.findElements(By.tagName("img")); // all div
			expMsg = sValue.split("::");
			String actWarningMsg = null;
			for (String expectedText : expMsg) {
				loggers.info("Expected Message - " + expectedText);
				String type = null;
				for (int i = 0; i < eleDiv.size(); i++) {
					matchStatus = false;
					actWarningMsg = eleDiv.get(i).getText();
					String TypeMsg = eleErrIcon.get(i).getAttribute("class");
					String[] Msgtype = TypeMsg.split("_");
					type = Msgtype[0];
					if (actWarningMsg.contains(expectedText)) {
						c++;
						matchStatus = true;
						loggers.info("Expected " + Msgtype[0] + " Message is matching with actual message '" + actWarningMsg + "'");
						HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Expected error text should matching with actual text '" + expectedText + "'", "Expected error text is matching with actual text '" + actWarningMsg + "'", "PASS");
						break;
					}

				}
				if (!matchStatus) {
					loggers.info("Expected " + type + " Message is NOT matching with actual message '" + actWarningMsg + "'");
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Expected error text should matching with actual text '" + expectedText + "'", "Expected error text is not matching with actual text '" + actWarningMsg + "'", "FAIL");
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

	public boolean FillBuildingDetails(String funValue) throws Exception {
		boolean status = false;
		logger.info("Entering into Filling Building Details");
		Common common = CommonManager.getInstance().getCommon();
		// String[] arrSheetTabName=null;
		Boolean tcAvailable = false;
		String TCID = null;
		String TCIDAdd = null;

		// String currentCompoentName =
		// PCThreadCache.getInstance().getProperty("methodName");

		int rowcount;

		XlsxReader sXL = XlsxReader.getInstance();
		rowcount = sXL.getRowCount(funValue);
		for (int i = 2; i <= rowcount; i++) {
			TCID = PCThreadCache.getInstance().getProperty("TCID");
			TCIDAdd = sXL.getCellData(funValue, "ID", i);
			String Iteration = PCThreadCache.getInstance().getProperty("Iteration");
			String TCIDIteration = sXL.getCellData(funValue, "Iteration", i);
			if (TCIDAdd.equals(TCID) && Iteration.equals(TCIDIteration)) {
				tcAvailable = true;
				int colcount = sXL.getColumnCount(funValue);
				for (int j = 2; j <= colcount; j++) {
					String sCellValue = sXL.getCellData(funValue, j, i);
					if (!sCellValue.isEmpty()) {
						String sColunmName = sXL.getCellData(funValue, j, 1);
						String sElementType = sColunmName.substring(0, 3);
						switch (sElementType.toUpperCase()) {
						case "FUN":
							break;
						default:
							status = common.SafeAction(Common.o.getObject(sColunmName), sCellValue, sColunmName);
							break;
						}
					}
				}
			}
		}
		if (!tcAvailable) {
			logger.info("Testcase ID not available in Data sheet - " + funValue);
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Testcase ID " + TCID + "should be available in Datasheet -" + funValue.toUpperCase(), "Testcase Id not avaialble in Datasheet", "FAIL");
		}
		return status;
	}

	/**
	 * @function This function is to fill the NGS Coverage from the coverage sheet
	 * @return
	 */
	public Boolean fillCoverages(String sheetName) {
		Boolean status = false;
		try {
			status = SCRCommon.coverageSheet(sheetName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * @function This function is to fill the NGS Coverage from the coverage sheet
	 * @return
	 */

	public Boolean addCoverage(String sheetName) {
		Boolean status = false;
		try {
			status = SCRCommon.addCoverages(sheetName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * Function created to add the Exclusion/Conditions in Location screen
	 * 
	 * @param sFuncValue
	 * @return boolean
	 * @throws Exception
	 */
	public Boolean AddCoverages_Exclusions(String sFuncValue) throws Exception {
		Boolean status = false;
		try {

			status = SCRCommon.addCoverages(sFuncValue);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * @function This function is to fill the NGS Coverage from the coverage sheet
	 * @return
	 */
	public Boolean CoverageValidation(String sheetName) // CoverageValidation
	{
		Boolean status = false;
		try {
			status = SCRCommon.coverageValidation(sheetName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * This method is used to retrieve table in the buildings and classification
	 * Page
	 **/

	public boolean actionOnBuildingsAndClassificationsTable() throws Exception {
		boolean status = false;
		// String strReadString="Primary Building";
		// boolean SearchString=false;
		String ele = "eleBuildingsAndClassificationsTable";
		// status =
		// common.ActionOnTable(Common.o.getObject("eleBuildingsAndClassificationsTable"),
		// 4, 0, strValue, "", "img");
		By obj = Common.o.getObject(ele);
		WebElement mytable = ManagerDriver.getInstance().getWebDriver().findElement(obj);
		List<WebElement> allrows = mytable.findElements(By.tagName("tr"));
		List<WebElement> Cells = allrows.get(0).findElements(By.tagName("td"));

		String readText = Cells.get(2).getText();
		if (readText.equals("X")) 
		{
			logger.info("Validating the Primary Building is available");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should display the added Building as 'PRIMARY'","Added Building is displayed as 'PRIMARY' in table", "PASS");
			status = true;
		}
		else
		{
			logger.info("Validating the Primary Building is available");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should display the added Building as 'PRIMARY'","Added Building is NOT displayed as 'PRIMARY' in table", "FAIL");
			status = false;
		}
		return status;
	}

	/** This method is used to select checkbox for a building location **/

	public boolean selectPrimaryBuilding() throws Exception {

		String ele = "eleBuildingsAndClassificationsTable";
		By obj = Common.o.getObject(ele);
		WebElement mytable = ManagerDriver.getInstance().getWebDriver().findElement(obj);
		try {

			List<WebElement> allrows = mytable.findElements(By.tagName("tr"));
			for (int i = 0; i < allrows.size(); i++) {
				List<WebElement> Cells = allrows.get(i).findElements(By.tagName("td"));
				if (Cells.get(4).getText().contains("1:") && (Cells.get(2).getText().contains("X"))) {
					Cells.get(1).click();
				}

			}

		} catch (Exception e) {
			System.out.println(e);
		}
		return true;
	}

	public boolean removeBuilding(String LocationName) throws Exception {

		//String ele = "eleBuildingsAndClassificationsTable";
		By obj = Common.o.getObject("eleBuilding_Classification_table");
		boolean status = false;
		WebElement mytable = ManagerDriver.getInstance().getWebDriver().findElement(obj);
		try {

			List<WebElement> allrows = mytable.findElements(By.tagName("tr"));
			for (int i = 0; i < allrows.size(); i++) {
				List<WebElement> Cells = allrows.get(i).findElements(By.tagName("td"));
				if (Cells.get(5).getText().toUpperCase().contains(LocationName.toUpperCase()))// &&(Cells.get(4).getText().contains(LocationName)))
				{
					String buildingName = Cells.get(5).getText();
					Cells.get(1).click();
					common.SafeAction(Common.o.getObject("eleRemoveBuildings"), "YES", "ele");
					common.SafeAction(Common.o.getObject("eleRemove_OK"), "YES", "ele");
					if (SCRCommon.PageVerifyNotPresent(buildingName)) {
						logger.info("The building at location '" + LocationName + "' has been removed");
						HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should display '" + LocationName + "'", "System displayed '" + LocationName + "'", "PASS");
						return true;
					} else {
						logger.info("The building at location '" + LocationName + "' has not been removed");
						HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should display '" + LocationName + "'", "System not displayed '" + LocationName + "'", "FAIL");
						return false;
					}

				}

			}

		} catch (Exception e) {
			System.out.println(e);

		}
		return true;

	}

	public boolean selectCoverage(String CoverageName) throws Exception {

		Boolean status = false;
		String ele = "eleSearch_CovResultTbl";
		By obj = Common.o.getObject(ele);

		logger.info("Searching for coverage");
		WebElement mytable = ManagerDriver.getInstance().getWebDriver().findElement(obj);
		try {

			List<WebElement> allrows = mytable.findElements(By.tagName("tr"));
			for (int i = 0; i < allrows.size(); i++) {
				List<WebElement> Cells = allrows.get(i).findElements(By.tagName("td"));
				if (Cells.get(1).getText().equals(CoverageName)) {
					Cells.get(0).click();
				}
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		// if(CoverageName.equals("Earthquake Coverage"))
		// {
		// common.SafeAction(Common.o.getObject("eleEarthquakeCovFullLimitYes"), "YES",
		// "ele");
		// }
		// //if(SCRCommon.PageVerify("Validation_ClearBtn"))
		// //common.SafeAction(Common.o.getObject("eleValidation_ClearBtn"), "YES",
		// "ele");
		return true;
	}

	public Boolean addCoverage1(String sheetName) {
		Boolean status = false;
		try {
			status = SCRCommon.addCoverages(sheetName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}

	public Boolean FillCoverageDetails(String sFuncValue) throws Exception {
		Boolean status = false;

		logger.info("Entering into Add NEW Location Function");
		XlsxReader sXL = XlsxReader.getInstance();// new XlsxReader( PCThreadCache.getInstance().getProperty("DataSheetName)
		String TCID = null;
		String TCIteration = null;
		// Common common = CommonManager.getInstance().getCommon();
		Boolean tcAvailable = false;
		int rowcount = sXL.getRowCount(sFuncValue);
		for (int i = 2; i <= rowcount; i++) {
			TCID = PCThreadCache.getInstance().getProperty("TCID");
			TCIteration = PCThreadCache.getInstance().getProperty("Iteration");
			String TCIDAdd = sXL.getCellData(sFuncValue, "ID", i);
			String TCIDIteration = sXL.getCellData(sFuncValue, "Iteration", i);

			if (TCIDAdd.equals(TCID) && TCIDIteration.equals(TCIteration)) {
				tcAvailable = true;
				int colcount = sXL.getColumnCount(sFuncValue);
				for (int j = 2; j <= colcount; j++) {
					String sColName = sXL.getCellData(sFuncValue, j, 1);
					String sCellValue = sXL.getCellData(sFuncValue, j, i);
					if (!sCellValue.isEmpty()) {
						if (!sColName.equalsIgnoreCase("funOverrideclick")) {
							status = common.SafeAction(Common.o.getObject(sColName), sCellValue, sColName);
						} else if (sColName.equalsIgnoreCase("funOverrideclick")) {
							int sElementSize = common.ElementSize(Common.o.getObject("eleOverRide"));
							if (sElementSize == 1) {
								try {
									status = common.SafeAction(Common.o.getObject("eleOverRide"), "eleOverRide", "eleOverRide");
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} else {
								status = common.SafeAction(Common.o.getObject("eleLocationOK"), "eleLocationOK","eleLocationOK");
							}
						}

					}

				}

			}
		}
		if (!tcAvailable) {
			logger.info("" + TCID + " is not available in the LocationData Sheet");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Validate the Testcase availability in datasheet - " + sFuncValue, "Testcass not available in Datasheet", "FAIL");

		} else if (!status) {
			logger.info("" + TCID + " is not available in the LocationData Sheet");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Validate the Testcase availability in datasheet - " + sFuncValue, "Testcass not available in Datasheet", "FAIL");
		}
		return status;
	}

	/** This method is used to validate the error messages **/

	public static boolean verifyErrorMsgText(String sValue) throws IOException {
		loggers  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
		int c = 0;
		boolean status = false;
		Common common = CommonManager.getInstance().getCommon();
		List<String> a = new ArrayList<String>();
		String[] expMsg = null;
		boolean matchStatus = false;
		// String expectedText=null;
		if (common.ElementSize(Common.o.getObject("eleValidationMsg")) == 0) {
			loggers.info("No Error/Warning messages in screen to validate");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Valdiate any error/Warning is displayed on screen", "No warning message is displayed on screen", "PASS");

		} else {
			WebElement eleErrtble = ManagerDriver.getInstance().getWebDriver().findElement(Common.o.getObject("eleValidationMsg"));
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
					if (actWarningMsg.contains(expectedText) && Msgtype[0].equals("error")) {
						c++;
						matchStatus = true;
						// logger.info("Expected " + Msgtype[0] +" Message is matching with actual
						// message '" + expectedText + "'");
						break;
					}

				}
				loggers.info("Visibility of error message " + expectedText + " :" + matchStatus);
			}
			if (c == expMsg.length) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Expected Error Messags should be displayed '" + "'", "Expected Error message(s) displayed", "PASS");
				status = true;
			} else
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Expected Error Messags should be displayed '" + "'", "Expected Error message(s) not displayed", "FAIL");
		}
		return true;
	}

	public boolean fillClassification(String sFuncValue) throws Exception {
		Boolean status = false;
		Common common = CommonManager.getInstance().getCommon();

		String[] sSetValue = sFuncValue.split(":::");
		logger.info("Verifying the Results");
		String Value = null;
		// set values for code
		
		status = common.SafeAction(Common.o.getObject("eleClassification"), "YES", "ele");
		status = common.SafeAction(Common.o.getObject("eleClassificationSearch"), "YES", "ele");
		status = common.SafeAction(Common.o.getObject("edtPolicyInfo_PreClassCode_Popup"), sSetValue[0], "edtPolicyInfo_PreClassCode_Popup");
		status = common.SafeAction(Common.o.getObject("eljCommonSearch"), "elj", "elj");
		

		// Verify result table and set the code
		status = common.ElementExistOrNotTrue("Table existence", "Table should be displayed for Validating & selecting the Value", "Predominant CLass code search Result table dispalyed as expected", Common.o.getObject("elePredominateSearchRes"));
		if (!(sSetValue[0].isEmpty())) {
			status = common.ActionOnTable_JS(Common.o.getObject("elePredominateSearchRes"), 1, 0, sSetValue[0], "a");
			status = common.SafeAction(Common.o.getObject("eleExposureField"), "YES", "ele");
			//String readonly=null;
			//readonly = ManagerDriver.getInstance().getWebDriver().findElement(Common.o.getObject("edtExposure")).getText();//.getAttribute("disabled");
			if(sSetValue.length>2 && sSetValue[2]!="")
			{
				status = common.SafeAction(Common.o.getObject("edtExposure"), sSetValue[2], "edtExposure");
			}
			if(status)
			{
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Verify whether class code can be Added successfully in B&C screen", "As expected, class code had been selected", "PASS");
			}
			else
			{
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Verify whether class code can be Added successfully in B&C screen", "Class code had not been selected", "FAIL");
			}
		}
 		return status;
	}

	public boolean fillExposure(String sFuncValue) throws Exception {
		Boolean status = false;
		Common common = CommonManager.getInstance().getCommon();

		String[] sSetValue = sFuncValue.split(":::");
		logger.info("Verifying the Results");
		String Value = null;
		// set values for code
		status = common.SafeAction(Common.o.getObject("eleExposureField"), "YES", "ele");
		status = common.SafeAction(Common.o.getObject("edtExposure"), sSetValue[0], "edtExposure");
	//	status = common.ElementExistOrNotTrue("Table existence", "Table should be displayed for Validating & selecting the Value", "Predominant CLass code search Result table dispalyed as expected", Common.o.getObject("elePredominateSearchRes"));

		return status;
		
	}

	/**
	 * This method is used to verify whether particular dropdown values are present
	 * in the dropdown or not
	 **/

	public boolean VerifyNotDropDown(String val) throws Exception {
		Boolean status = false;
		By ele = Common.o.getObject("lstConstructionDetail");
		String id = ManagerDriver.getInstance().getWebDriver().findElement(ele).getAttribute("id");
		status = SCRCommon.VerifyDropDownFalse(id, val);
		return status;
	}

	/**
	 * This method is used to verify the list of Class codes displayed for given
	 * Policy Type
	 **/

	public boolean VerifyClassCodes(String sFuncValue) throws Exception {
		Boolean status = false;
		Common common = CommonManager.getInstance().getCommon();

		String[] sSetValue = sFuncValue.split(":::");
		logger.info("Verifying the Results");
		String strClassCode = null;
		// set values for code
		status = common.SafeAction(Common.o.getObject("eleClassification"), "YES", "ele");
		status = common.SafeAction(Common.o.getObject("eleClassificationSearch"), "YES", "ele");
		status = common.SafeAction(Common.o.getObject("edtPolicyInfo_PreClassCode_Popup"), sSetValue[0], "edtPolicyInfo_PreClassCode_Popup");
		status = common.SafeAction(Common.o.getObject("eljCommonSearch"), "elj", "elj");

		for (int i = 0; i < sSetValue.length - 1; i++) {
			status = false;
			strClassCode = common.GetTextFromTableTagName(Common.o.getObject("elePredominateSearchRes"), i, 1, "div");
			if (strClassCode.equals(sSetValue[i + 1])) {
				status = true;
				logger.info("Display the status of the class code-'" + strClassCode + ": " + status);
			}

		}
		if (status == true) {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "All the expected Class codes are displayed ", "All the expected Class codes are displayed  ", "PASS");
		} else {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Expected Class codes are not displayed ", "Expected Class codes are not displayed ", "FAIL");
		}
		return status;
	}

}