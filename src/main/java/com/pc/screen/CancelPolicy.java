
/**
 * @ClassPurpose This Class used for the Cancel Policy
 * @Scriptor Raj
 * @ReviewedBy
 * @ModifiedBy
 * @LastDateModified 3/23/2017
 */
package com.pc.screen;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.PCThreadCache;

public class CancelPolicy {

	public static String sheetname = "CancelPolicy";
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();

	/**
	 * @function Use to perform all the action in the CancelPolicy sheet
	 * @return true/false
	 * @throws Exception
	 */
	public Boolean SCRCancelPolicy() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			return false;
		}
		return status;
	}

	/**
	 * @function This method used to return Effective Date
	 * @return True/False
	 * @throws Exception
	 **/
	public boolean AddEffectiveDate(String strValue) throws Exception {
		boolean status = false;
		String[] strSplitValue = strValue.split(":::");
		String strDate=null;
		switch (strSplitValue[0]) {
		case "Date Received": {
			status = common.SafeAction(Common.o.getObject("edtCan_DateReceived"), SCRCommon.ReturnCurrentDate(), "lst");
			break;
		}
		
		case "INCREASEDECREASEDATE":{
			strDate = SCRCommon.returnDate(Integer.parseInt(strSplitValue[1]));
			status = common.SafeAction(Common.o.getObject("edtCan_DateReceived"), strDate, "lst");
			break;
		}
		}
		return status;
	}

	/**
	 * @function This use to select the cancel policy screen radio button
	 * @param sFuncValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean CancelationQuestions(String sFuncValue) throws Exception {
		boolean status = false;
		String[] sValue = sFuncValue.split(":::");
		status = ActionOnTable(Common.o.getObject("eleCan_ODENQues"), 0, 1, sValue[0], sValue[1], "input");
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
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "System should search string in table and Search string is '" + strReadString + "'", "System searched string in table and srarch string is  '" + actionObjetName + "'", "PASS");
			if (ActionObject) {
				logger.info("Search and click on object in the table cell and object name is '" + actionObjetName + "'");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search object in the table cell and click on object. Object name is '" + actionObjetName + "'", 

"System searched object in the table and clicked on object. object name is '" + actionObjetName + "'", "PASS");
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

	public boolean DefaultEffectiveDate(String strFuncValue) throws Exception {
		boolean status = false;
		String[] strSplitValue = strFuncValue.split(":::");
		String strDate = null;
		switch (strSplitValue[0].toUpperCase()) {
		case "CURRENTDATE":
			strDate = SCRCommon.ReturnCurrentDate();
			status = common.SafeAction(Common.o.getObject("edjCan_CanEffDate"), strDate, "edj");
			break;
		case "INCREASEDECREASEDATE":
			strDate = SCRCommon.returnDate(Integer.parseInt(strSplitValue[1]));
			status = common.SafeAction(Common.o.getObject("edjCan_CanEffDate"), strDate, "edj");
			break;
		}
		return status;
	}

}