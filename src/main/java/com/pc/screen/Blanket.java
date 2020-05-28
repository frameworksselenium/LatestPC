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

public class Blanket {

	public static String sheetname = "Blanket";
	Common common = CommonManager.getInstance().getCommon();
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();

	public Boolean SCRBlanket() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);

		if (!status) {
			status = false;
		}
		return status;
	}

	/**
	 * 
	 * @param funValue
	 * @return
	 */
	public boolean addBlanketCoverages(String funValue) {
		boolean status = false;
		String[] arrCovName = funValue.split(":::");
		int iRowCnt = 0;
		try {
			status = common.SafeAction(Common.o.getObject("eljBlanketPopup_AddBlanket"), "eljBlanketPopup_AddBlanket", "eljBlanketPopup_AddBlanket");
			iRowCnt = SCRCommon.TableRowCount(Common.o.getObject("eleBlanketTbl"));
			if (iRowCnt > 0) {
				if (!(arrCovName[0].toUpperCase().equals("ALL"))) {
					for (int i = 0; i < arrCovName.length; i++) {
						status = ActionOnTable1(Common.o.getObject("eleBlanketTbl"), 3, 0, arrCovName[i], "img");
					}

				} else if (arrCovName[0].toUpperCase().equals("ALL")) {
					status = common.SafeAction(Common.o.getObject("eleAllBlanketCov_Chk"), "ele", "eleAllBlanketCov_Chk");
				}
				status = common.SafeAction(Common.o.getObject("eleBlanketAddCoverages"), "ele", "eleBlanketAddCoverages");

			} else {
				logger.info("No records available in Blanket Table to select the coverages!!");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Add the Coverages dispalyed in blanket screen", "No Coverages displayed to Add to Blanket in Search Results", 

"WARNING");
				status = common.SafeAction(Common.o.getObject("eljBlanketPopup_ReturnLink"), "elj", "eljBlanketPopup_ReturnLink");

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return status;
	}

	public static boolean verifyErrorMsgText(String sValue) throws IOException {
		int c = 0;
		loggers  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
		boolean status = false;
		Common common = CommonManager.getInstance().getCommon();
		List<String> a = new ArrayList<String>();
		String[] expMsg = null;
		boolean matchStatus = false;
		// String expectedText=null;
		if (common.ElementSize(Common.o.getObject("eleValidationMsg")) == 0) {
			loggers.info("No Error/Warning messages in screen to validate");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Valdiate any error/Warning is displayed on screen", "No warning message is displayed on screen", "PASS");
			// status=true;
			return status;
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
					if (expectedText.equals(actWarningMsg) && Msgtype[0].equals("error")) {
						c++;
						matchStatus = true;
						loggers.info("Expected " + Msgtype[0] + " Message is matching with actual message '" + expectedText + "'");
						break;
					}

				}
				if (!matchStatus) {
					loggers.info("Expected " + type + " Message is NOT matching with actual message '" + expectedText + "'");
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Expected error text should matching with actual text '" + expectedText + "'", "Expected error text is not matching with actual text '" + expectedText + "'", "FAIL");
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
				loggers.info("Number of failed Expected Message: " + (expMsg.length - c) + " , In out of Total " + expMsg.length 

+ " Expected Message ");
			}

		}

		return status;

	}

	public boolean removeBlanketCoverages(String funValue) {
		boolean status = false;
		String[] arrCovName = funValue.split(":::");
		int iRowCnt = 0;
		try {
			// status=common.SafeAction(Common.o.getObject("eljBlanketPopup_AddBlanket"),
			// "eljBlanketPopup_AddBlanket", "eljBlanketPopup_AddBlanket");
			iRowCnt = SCRCommon.TableRowCount(Common.o.getObject("eleBlanketRemoveTbl"));
			if (iRowCnt > 0) {
				if (!(arrCovName[0].toUpperCase().equals("ALL"))) {
					for (int i = 0; i < arrCovName.length; i++) {
						status = ActionOnTable1(Common.o.getObject("eleBlanketRemoveTbl"), 3, 0, arrCovName[i], "img");
					}

				} else if (arrCovName[0].toUpperCase().equals("ALL")) {
					status = common.SafeAction(Common.o.getObject("eleBlanketAllRemove"), "ele", "eleBlanketAllRemove");
				}
				status = common.SafeAction(Common.o.getObject("eleRemoveBlanket"), "ele", "eleRemoveBlanket");

			} else {
				logger.info("No records available in Blanket Table to Remove the coverages!!");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Remove the Coverages dispalyed in blanket screen", "No Coverages displayed to Add to Blanket in Search Results", 

"WARNING");
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
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search object in the table cell and click on object. Object name is '" + strReadString + "'", 

"System searched object in the table and clicked on object. object name is '" + strReadString + "'", "PASS");
				Status = true;
			} else {
				logger.info("Search and click on object in the table cell and object name is '" + strReadString + "'");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search object in the table cell and click on object. Object name is '" + strReadString + "'", 

"System searched object in the table and clicked on object. object name is '" + strReadString + "'", "FAIL");
				Status = false;
			}
		} else {
			logger.info("Search String not available in the table. '" + strReadString + "'");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search string in table and Search string is '" + strReadString + "'", "System searched string in table and srarch string is  '" + strReadString + "'", "FAIL");
			Status = false;
		}
		return Status;
	}
}