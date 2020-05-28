
/**
 * @ClassPurpose This Class used for the Activity Pattern Screen
 * @Scriptor Sojan
 * @ReviewedBy
 * @ModifiedBy Sojan
 * @LastDateModified 3/24/2017
 */
package com.pc.screen;

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

public class ActivityPattern {

	public static String sheetname = "ActivityPattern";
	Common common = CommonManager.getInstance().getCommon();
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();
	//static Logger logger = Logger.getLogger(sheetname);
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));

	/**
	 * @function Use to perform all the objects/elements/functions from the
	 *           ActivityPattern excel sheet
	 * @return true/false
	 * @throws Exception
	 */
	public Boolean SCRActivityPattern() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

	/**
	 * @Function Ability to choose the activity patterns in the administration menu
	 *           through house over action
	 * @return status
	 * @throws Exception
	 **/
	public boolean AcitivityPattern() throws Exception {
		boolean status = false;
		status = common.MouseHoverAction(Common.o.getObject("eleBusinessSettings"), Common.o.getObject("eleActivityPatterns"));
		return status;
	}

	/**
	 * @function Verify the results for all the conditions
	 * @param funValue
	 * @return status
	 * @throws Exception
	 **/
	public boolean ActivityPatternResults(String funValue) throws Exception {
		boolean status = false;
		String[] sValue = funValue.split(":::");
		// String wait = HTML.properties.getProperty("SHORTWAIT");
		// Integer x = Integer.valueOf(wait);
		switch (sValue[0].toUpperCase()) {
		case "SELECTINGTHEACTIVITYTODELETE":
			logger.info("Validating the '" + sValue[0] + "'");
			status = SelectActivity(sValue[0], sValue[1], sValue[2]);
			Thread.sleep(5000);
			break;

		case "SELECTINGTHEACTIVITYTOEDIT":
			logger.info("Validating the '" + sValue[0] + "'");
			status = SelectActivity(sValue[0], sValue[1], sValue[2]);
			break;
		}
		return status;
	}

	/**
	 * @function Ability to fill the Business segment table
	 * @param fValue
	 * @return status
	 * @throws Exception
	 **/
	public boolean BussinessSegment(String fValue) throws Exception {
		boolean status = false;
		String[] sValue = fValue.split(":::");
		String wait = HTML.properties.getProperty("SHORTWAIT");
		Integer x = Integer.valueOf(wait);
		WebElement tableRowXpathRole = driver.findElement(Common.o.getObject("eleBSTable"));
		List<WebElement> rows_table1 = tableRowXpathRole.findElements(By.tagName("tr"));
		int rows_count1 = rows_table1.size();
		for (int i = 1; i <= rows_count1; i++) {
			String exist1 = driver.findElement(By.xpath(".//*[contains(@id, 'ActivityPatternDetailScreen:skillAndTrackLV-body')]/div/table/tbody/tr[" + i + "]/td[2]/div")).getText();
			if (exist1.contains("<none>")) {
				driver.findElement(By.xpath(".//*[contains(@id, 'ActivityPatternDetailScreen:skillAndTrackLV-body')]/div/table/tbody/tr[" + i + "]/td[2]")).click();
				status = common.SafeAction(Common.o.getObject("lstBusinessSegement"), sValue[0], "lstBusinessSegement");
				common.WaitUntilClickable(By.xpath(".//*[contains(@id, 'ActivityPatternDetailScreen:skillAndTrackLV-body')]/div/table/tbody/tr[" + i + "]/td[3]"), x);
				status = common.SafeAction(Common.o.getObject("lstSkill"), sValue[1], "lstSkill");
				common.WaitUntilClickable(By.xpath(".//*[contains(@id, 'ActivityPatternDetailScreen:skillAndTrackLV-body')]/div/table/tbody/tr[" + i + "]/td[4]"), x);
				status = common.SafeAction(Common.o.getObject("lstTrack"), sValue[2], "lstTrack");
			} else {
				System.out.println("The row" + i + " is filed");
			}
		}
		return status;
	}

	/**
	 * @function Ability to search newly created activity in the list to delete the
	 *           record
	 * @param fValue
	 *            is for Subject
	 * @param sCase
	 *            is for select case name
	 * @return status
	 * @throws Exception
	 **/
	public boolean SelectActivity(String sCase, String fOption, String fValue) throws Exception {
		boolean status = false;
		String sSubject = null;
		WebElement tableRowXpathRole = driver.findElement(Common.o.getObject("eleNewActivityPatternListTable"));
		List<WebElement> rows_table1 = tableRowXpathRole.findElements(By.tagName("tr"));
		int rows_count1 = rows_table1.size();
		switch (fOption.toUpperCase()) {
		case "SELECTACTIVITYPATTERNTODELETE":
			for (int i = 1; i <= rows_count1; i++) {
				sSubject = driver.findElement(By.xpath(".//*[@id='ActivityPatterns:ActivityPatternsScreen:ActivityPatternsLV-body']/div/table/tbody/tr[" + i + "]/td[2]")).getText();
				if (sSubject.contains(fValue)) {
					driver.findElement(By.xpath(".//*[@id='ActivityPatterns:ActivityPatternsScreen:ActivityPatternsLV-body']/div/table/tbody/tr[" + i + "]/td[1]/div/img")).click();
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "" + sCase + ": '" + fValue + "' should select", "" + sCase + ": '" + sSubject + "' is selected", "PASS");
					status = true;
					break;
				}
			}
			break;

		case "SELECTACTIVITYACTION":
			for (int i = 1; i <= rows_count1; i++) {
				sSubject = driver.findElement(By.xpath(".//*[@id='ActivityPatterns:ActivityPatternsScreen:ActivityPatternsLV-body']/div/table/tbody/tr[" + i + "]/td[2]")).getText();
				if (sSubject.contains(fValue)) {
					driver.findElement(By.xpath(".//*[@id='ActivityPatterns:ActivityPatternsScreen:ActivityPatternsLV-body']/div/table/tbody/tr[" + i + "]/td[2]/div/a")).click();
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "" + sCase + ": '" + fValue + "' should select", "" + sCase + ": '" + sSubject + "' is selected", "PASS");
					status = true;
					break;
				}
			}
			break;
		}
		if (status == false) {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "" + sCase + ": '" + fValue + "' should select", "" + sCase + ": '" + sSubject + "' is not selected", "FAIL");
			status = false;
		}
		return status;
	}

	/**
	 * @function To select the activity pattern according to the input
	 * @param sFuncValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean selectActivtyPatterns(String sFuncValue) throws Exception {
		boolean status = true;
		boolean sPatternCheck = false;
		String[] sValue = sFuncValue.split(":::");
		String sCountObject = common.ReadElement(Common.o.getObject("eleActivityPatternPageCount"), Integer.valueOf(HTML.properties.getProperty("LONGWAIT")));
		String sCount = sCountObject.substring(sCountObject.length() - 2);
		for (int i = 0; i < Integer.valueOf(sCount); i++) {
			// sPatternCheck = ActionOnTable(Common.o.getObject("eleActivityPatterntbl"), 1,// 1, sValue[0], "a");
			switch (sValue[0].toUpperCase()) {
			case "SELECT":
				sPatternCheck = ActionOnTable(Common.o.getObject("eleActivityPatterntbl"), 1, 1, sValue[1], sValue[1], "a");
				break;

			case "DELETE":
				sPatternCheck = ActionOnTable(Common.o.getObject("eleActivityPatterntbl"), 1, 0, sValue[1], "", "img");
				break;
			}
			if (sPatternCheck == false) {
				common.SafeAction(Common.o.getObject("eleNextPageArrow"), "eleNextPageArrow", "eleNextPageArrow");
				status = false;
			}
			if (sPatternCheck == true) {
				status = true;
				break;
			}
		}
		return status;
	}

	/**
	 * @function Precondition to check whether the activity is avialble in the table
	 *           before creating the activity
	 * @param sFuncValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean selectActivtyPatternAvailablity(String sFuncValue) throws Exception {
		boolean status = true;
		boolean sPatternCheck = false;
		String compareValue = null;
		// String[] sValue = sFuncValue.split(":::");
		String sCountObject = common.ReadElement(Common.o.getObject("eleActivityPatternPageCount"), Integer.valueOf(HTML.properties.getProperty("LONGWAIT")));
		String sCount = sCountObject.substring(sCountObject.length() - 2);
		for (int i = 0; i < Integer.valueOf(sCount); i++) {
			// sPatternCheck = ActionOnTable(Common.o.getObject("eleActivityPatterntbl"), 1,
			// 1, sValue[0], "a");
			compareValue = GetTextFromTable(Common.o.getObject("eleActivityPatterntbl"), 1, 1, sFuncValue);
			if (compareValue == null) {
				compareValue = "";
				sPatternCheck = false;
			} else {
				if (compareValue.equals(sFuncValue)) {
					sPatternCheck = true;
					logger.info("" + sFuncValue + " available in the account number column");
					sPatternCheck = ActionOnTable(Common.o.getObject("eleActivityPatterntbl"), 1, 0, sFuncValue, "", "img");
					status = SCRCommon.PageVerify("DeleteAP");
					status = common.SafeAction(Common.o.getObject("eljDeleteAP"), "", "eljDeleteAP");
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should search string in account table and Search string is '" + sFuncValue + "'", "System searched string in account table and search string is  '" + sFuncValue + "'", "PASS");
				}
			}
			if (sPatternCheck == false) {
				common.SafeAction(Common.o.getObject("eleNextPageArrow"), "eleNextPageArrow", "eleNextPageArrow");
				status = false;
			}
			if (sPatternCheck == true) {
				status = true;
				break;
			}
		}
		sPatternCheck = true;
		status = true;
		return status;
	}

	/**
	 * @function this function use to get the text from the table according to the
	 *           input and the column
	 * @param obj,readTextCol,getTextCol,strReadString
	 * @return String
	 * @throws Exception
	 */
	public String GetTextFromTable(By obj, int readTextCol, int getTextCol, String strReadString) throws Exception {
		String text = null;
		// boolean SearchString = false;
		WebElement mytable = ManagerDriver.getInstance().getWebDriver().findElement(obj);
		List<WebElement> allrows = mytable.findElements(By.tagName("tr"));
		for (int i = 0; i <= allrows.size() - 1; i++) {
			List<WebElement> Cells = allrows.get(i).findElements(By.tagName("td"));
			String readText = Cells.get(readTextCol).getText();
			if (readText.contains(strReadString)) {
				// SearchString = true;
				text = Cells.get(getTextCol).getText();
			}
		}
		/*
		 * if(SearchString) { logger.info("Search String available in the table. '" +
		 * strReadString + "'");
		 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
		 * PCThreadCache.getInstance().getProperty("methodName"),
		 * "System should search string in table and Search string is '" + strReadString
		 * + "'","System searched string in table and search string is  '" +
		 * strReadString + "'", "PASS"); } else {
		 * logger.info("Search String not available in the table. '" + strReadString +
		 * "'");
		 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
		 * PCThreadCache.getInstance().getProperty("methodName"),
		 * "System should search string in table and Search string is '" + strReadString
		 * + "'","System searched string in table and search string is  '" +
		 * strReadString + "'", "FAIL"); SearchString = false; }
		 */
		return text;
	}

	/**
	 * @function This function use to Select the data from the table and click the
	 *           element accordingly
	 * @param obj,readCol,actionCol,strReadString,actionObjetName
	 * @return status
	 * @throws Exception
	 */
	public Boolean ActionOnTable(By obj, int readCol, int actionCol, String strReadString, String actionObjetName, String sTagName) throws 

Exception {
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
					String objName = element.getText();
					if (objName.contains(actionObjetName)) {
						Status = true;
						ActionObject = true;
						element.click();
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
			// HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
			// PCThreadCache.getInstance().getProperty("methodName"), "System should search
			// string in table and Search string is '" + strReadString + "'","System
			// searched string in table and srarch string is '" + actionObjetName + "'",
			// "PASS");
			if (ActionObject) {
				logger.info("Search and click on object in the table cell and object name is '" + actionObjetName + "'");
				// HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
				// PCThreadCache.getInstance().getProperty("methodName"), "System should search
				// object in the table cell and click on object. Object name is '" +
				// actionObjetName + "'","System searched object in the table and clicked on
				// object. object name is '" + actionObjetName + "'", "PASS");
				Status = true;
			} else {
				logger.info("Search and click on object in the table cell and object name is '" + actionObjetName + "'");
				// HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
				// PCThreadCache.getInstance().getProperty("methodName"), "System should search
				// object in the table cell and click on object. Object name is '" +
				// actionObjetName + "'","System searched object in the table and clicked on
				// object. object name is '" + actionObjetName + "'", "FAIL");
				Status = false;
			}
		} else {
			logger.info("Search String not available in the table. '" + strReadString + "'");
			// HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
			// PCThreadCache.getInstance().getProperty("methodName"), "System should search
			// string in table and Search string is '" + strReadString + "'","System
			// searched string in table and srarch string is '" + actionObjetName + "'",
			// "FAIL");
			Status = false;
		}
		return Status;
	}
}