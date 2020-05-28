
package com.pc.screen;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.PCThreadCache;
import com.pc.utilities.XlsxReader;

public class Locations {

	public static String sheetname = "Locations";
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();
	

	public Boolean SCRLocations() throws Exception {

		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

	/**
	 * @function to check whether we get update or standardize button for account
	 *           creation
	 * @return true/false
	 */
	public boolean UpdateLocation() {
		boolean status = false;
		int sElementSize = common.ElementSize(Common.o.getObject("eleLocationOK"));
		if (sElementSize == 1) {
			try {
				status = common.SafeAction(Common.o.getObject("eleLocationOK"), "eleLocationOK", "eleLocationOK");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				status = common.SafeAction(Common.o.getObject("eleOverRide"), "eleOverRide", "eleOverRide");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return status;
	}

	/**
	 * @function use to select the check box of the particular location from
	 *           Locations screen
	 * @param sReadString
	 * @return true/false
	 * @throws Exception
	 */

	public boolean SelectLocations(String sReadString) throws Exception {
		boolean status = false;
		status = common.ActionOnTable(Common.o.getObject("eleLocationsTbl"), 6, 0, sReadString, "div");
		return status;
	}
	public boolean SelectLocationsR3(String sReadString) throws Exception {
		boolean status = false;
		status = common.ActionOnTable(Common.o.getObject("eleLocationsTbl"), 3, 1, sReadString, "div");
		return status;
	}

	/**
	 * function used to fill the Location screen details while adding /editing the
	 * location
	 * 
	 * @param sFuncValue
	 * @return boolean
	 * @throws Exception
	 */

	public Boolean StandardiseLoc() throws Exception {
		boolean status = false;
		String ele = "eleNewLoc_LocDetailTbl";
		By obj = Common.o.getObject(ele);
		WebElement mytable = ManagerDriver.getInstance().getWebDriver().findElement(obj);
		String id = mytable.getAttribute("id");
		String one = ":0:Loc";
		String Location_Num = id.replace("-body", one);
		status = CommonManager.getInstance().getCommon().SafeAction(By.id(Location_Num), "YES", "BtnLocation");
		CommonManager.getInstance().getCommon().SafeAction(Common.o.getObject("eleStandardise"), "YES", "eleStandardise");
		CommonManager.getInstance().getCommon().SafeAction(Common.o.getObject("eleLocationOK"), "YES", "eleLocationOK");
		if (status)
			logger.info("Standardise Location num in Location Screen is completed");
		return status;
	}

	public Boolean FillLocationDetails(String sFuncValue) throws Exception {
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
						switch (sColName.toUpperCase()) {
						case "FUNOVERRIDECLICK":
							int sElementSize = common.ElementSize(Common.o.getObject("eleOverRide"));
							if (sElementSize == 1) {
								try {
									status = common.SafeAction(Common.o.getObject("eleOverRide"), 

"eleOverRide", "eleOverRide");
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} else {
								status = common.SafeAction(Common.o.getObject("eleLocationOK"), "eleLocationOK", 

"eleLocationOK");
							}
							break;
						case "CFUPAGESCREENSHOT":
							status = SCRCommon.PageScreenShot(sCellValue);
							break;
						default:
							status = common.SafeAction(Common.o.getObject(sColName), sCellValue, sColName);
							break;

						}

					}

				}

			}
		}
		if (!tcAvailable) {
			logger.info("" + TCID + " is not available in the LocationData Sheet");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "Validate the Testcase availability in datasheet - " + sFuncValue, "Testcass not available in Datasheet", "FAIL");

		} else if (!status) {
			logger.info("" + TCID + " is not available in the LocationData Sheet");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "Validate the Testcase availability in datasheet - " + sFuncValue, "Testcass not available in Datasheet", "FAIL");
		}
		return status;
	}

	/**
	 * Function created to edit the location details submitted
	 * 
	 * @param sFuncValue
	 * @return boolean
	 * @throws Exception
	 */
	public Boolean EditLocation(String sFuncValue) throws Exception {
		Boolean status = false;
		status = common.ActionOnTable(Common.o.getObject("eleNewLoc_LocDetailTbl"), 5, 0, sFuncValue, "img");
		status = common.WaitUntilClickable(Common.o.getObject("eleLoc_EditBtn"), Integer.valueOf(HTML.properties.getProperty

("SHORTWAIT")));
		status = common.SafeAction(Common.o.getObject("eleLoc_EditBtn"), "eleLoc_EditBtn", "eleLoc_EditBtn");
		return status;

	}

	/**
	 * Function created to set the territory values in Location screen
	 * 
	 * @param funValue
	 * @return boolean
	 * @throws Exception
	 */
	public boolean SelectTerritoryCode(String funValue) throws Exception {
		boolean status = true;
		String wait = HTML.properties.getProperty("SHORTWAIT");
		Integer x = Integer.valueOf(wait);

		// search button clikc
		status = common.WaitUntilClickable(Common.o.getObject("eleLoc_SearchTerritory"), Integer.valueOf(HTML.properties.getProperty

("SHORTWAIT")));
		status = common.SafeAction(Common.o.getObject("eleLoc_SearchTerritory"), "eleLoc_TerritorySearchBtn", 

"eleLoc_TerritorySearchBtn");
		// InputCode if provided
		status = common.WaitUntilClickable(Common.o.getObject("edtLoc_TerritoryCode"), Integer.valueOf(HTML.properties.getProperty

("SHORTWAIT")));
		if (!(funValue.equalsIgnoreCase("ANY"))) {
			status = common.SafeAction(Common.o.getObject("edtLoc_TerritoryCode"), funValue, "edtLoc_TerritoryCode");
			status = common.SafeAction(Common.o.getObject("eleTerritory_SearchBtn"), "eleLoc_TerritorySearchBtn", 

"eleLoc_TerritorySearchBtn");
			status = common.ActionOnTable_JS(Common.o.getObject("eleTerritoryTbl"), 1, 0, funValue, "a");
		} else {
			status = common.SafeAction(Common.o.getObject("eleTerritory_SearchBtn"), "eleLoc_TerritorySearchBtn", 

"eleLoc_TerritorySearchBtn");
			status = common.ActionOnTable_JS(Common.o.getObject("eleTerritoryTbl"), 1, 0, "", "a");
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
	 * @function This use to select the questions radio button
	 * @param sFuncValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean LocationsQuestions(String sFuncValue) throws Exception {
		boolean status = false;
		String[] sValue = sFuncValue.split(":::");
		status = ActionOnTable(Common.o.getObject("eleLocationQuestionstbl"), 0, 1, sValue[0], sValue[1], "input");
		return status;
	}
	

	/**
	 * @function use to perform the radio click based on the input
	 * @param obj
	 * @param readCol
	 * @param actionCol
	 * @param strReadString
	 * @param actionObjetName(true/false)
	 * @param sTagName
	 * @return true/false
	 * @throws Exception
	 */
	public Boolean ActionOnTable(By obj, int readCol, int actionCol, String strReadString, String actionObjetName, String sTagName) throws 

Exception {
		boolean Status = false;
		boolean SearchString = false;
		boolean ActionObject = false;
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement mytable = driver.findElement(obj);
		List<WebElement> allrows = mytable.findElements(By.tagName("tr"));
		for (int i = 0; i <= allrows.size() - 1; i++) {
			List<WebElement> Cells = allrows.get(i).findElements(By.tagName("td"));
			String readText = Cells.get(readCol).getText();
			if (readText.contains(strReadString)) {
				SearchString = true;
				List<WebElement> CellElements = Cells.get(actionCol).findElements(By.tagName(sTagName));
				for (WebElement element : CellElements) {
					String objName = element.getAttribute("inputvalue");
					if (objName.contains(actionObjetName)) {
						Status = true;
						ActionObject = true;
						element.click();
						Status = SCRCommon.JavaScriptDynamicWait(element, js);
						break;
					}
				}
			}
			if (ActionObject == true) {
				break;
			}
		}
		if (SearchString) {
			logger.info("Search String available in the table. '" + strReadString + "'");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search string in table and Search string is '" + strReadString + "'", "System searched string in table and srarch string is  '" + actionObjetName + "'", "PASS");
			if (ActionObject) {
				logger.info("Search and click on object in the table cell and object name is '" + actionObjetName + "'");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search object in the table cell and click on object. Object name is '" + actionObjetName + "'", "System searched object in the table and clicked on object. object name is '" + actionObjetName + "'", "PASS");
				Status = true;
			} else {
				logger.info("Search and click on object in the table cell and object name is '" + actionObjetName + "'");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search object in the table cell and click on object. Object name is '" + actionObjetName + "'", "System searched object in the table and clicked on object. object name is '" + actionObjetName + "'", "FAIL");
				Status = false;
			}
		} else {
			logger.info("Search String not available in the table. '" + strReadString + "'");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search string in table and Search string is '" + strReadString + "'", "System searched string in table and srarch string is  '" + actionObjetName + "'", "FAIL");
			Status = false;
		}
		return Status;
	}

}