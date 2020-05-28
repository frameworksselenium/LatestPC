
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

public class CLACancellationGuidedWorkflow {

	public static String sheetname = "CLACancellationGuidedWorkflow";
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();

	public Boolean SCRCLACancellationGuidedWorkflow() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

	public boolean VerifyResults(String sFuncValue) throws Exception {
		boolean status = false;
		String[] sValue = sFuncValue.split(":::");
		logger.info("Verifying the Results Guided WorkFlow Transactions");
		String Value = null;
		switch (sValue[0].toUpperCase()) {
		case "VERIFYALLREADYCANCELMSGWARNING":
			// Value = Common.ReadElement(Common.o.getObject(""),
			// Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
			// status = Common.CompareStringResult(sValue[0], sValue[1], Value);
			status = false;
			break;
		case "VERIFYODENWARNING":
			Value = common.ReadElement(Common.o.getObject("eleCommonZeroResults"), Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			break;
		case "VERIFYBSHORTRATEWARNING":
			Value = common.ReadElement(Common.o.getObject("eleGWShortRateWarning"), Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
			// status = Common.VerifyErrorMessages()
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			break;
		case "VERIFYEMAIL":
			Value = common.ReadElement(Common.o.getObject("eleGWShortRateWarning"), Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			break;
		case "VERIFYALT-4":
			Value = common.ReadElement(Common.o.getObject("eleGWWarning1"), Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			Value = common.ReadElement(Common.o.getObject("eleGWWarning2"), Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
			status = common.CompareStringResult(sValue[0], sValue[2], Value);
			Value = common.ReadElement(Common.o.getObject("eleGWWarning3"), Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
			status = common.CompareStringResult(sValue[0], sValue[3], Value);
			break;
		case "VERIFYPOLICYREWRITTTEN":
			Value = common.ReadElement(Common.o.getObject("eleGWRewrite"), Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			break;
		case "VERIFYCANCELEFFECTIVEDATE":
			Value = common.ReadElement(Common.o.getObject("eleCommonZeroResults"), Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			break;
		case "VERIFYEVIDENCETYPENO":
			// Value = Common.ReadElement(Common.o.getObject("eleCommonZeroResults"),
			// Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
			// status = Common.CompareStringResult(sValue[0], sValue[1], Value);
			status = false;
			break;
		}
		if (!status) {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "" + sValue[0] + " case should PASS", "" + sValue[0] + " case fail", "FAIL");
			status = true;
		}
		return status;
	}

	public boolean CancelationQuestions(String sFuncValue) throws Exception {
		boolean status = false;
		String[] sValue = sFuncValue.split(":::");
		status = ActionOnTable(Common.o.getObject("eleGWCancellationQuestions"), 0, 1, sValue[0], sValue[1], "input");
		// Common.WaitForPageToBeReady();
		return status;
	}

	/**
	 * @function
	 * @param obj
	 * @param readCol
	 * @param actionCol
	 * @param strReadString
	 * @param actionObjetName(true/false)
	 * @param sTagName
	 * @return
	 * @throws Exception
	 */
	public Boolean ActionOnTable(By obj, int readCol, int actionCol, String strReadString, String actionObjetName, String sTagName) throws 

Exception {
		boolean Status = false;
		boolean SearchString = false;
		boolean ActionObject = false;
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
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search object in the table cell and click on object. Object name is '" + actionObjetName + "'", 

"System searched object in the table and clicked on object. object name is '" + actionObjetName + "'", "FAIL");
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
			status = common.SafeAction(Common.o.getObject("edtGWCancellationEffectiveDate"), strDate, "edj");
			break;
		case "INCREASEDECREASEDATE":
			strDate = SCRCommon.returnDate(Integer.parseInt(strSplitValue[1]));
			status = common.SafeAction(Common.o.getObject("edtGWCancellationEffectiveDate"), strDate, "edj");
			break;
		}
		return status;
	}
}