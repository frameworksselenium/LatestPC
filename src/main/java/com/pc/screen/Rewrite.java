package com.pc.screen;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.pc.constants.PCConstants;
import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.PCThreadCache;
import com.pc.utilities.XlsxReader;

public class Rewrite {

	public static String sheetname = "Rewrite";
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();

	public Boolean SCRRewrite() throws Exception {

		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

	public boolean PolicyNumber() throws Exception {
		boolean status = false;
		HashMap<String, Object> updateColumnNameValues = new HashMap<String, Object>();
		HashMap<String, Object> whereConstraint = new HashMap<String, Object>();
		XlsxReader sXL = XlsxReader.getInstance();
		// Common.WaitForElementExist(Common.o.getObject("eleCLAAccountNumberExist"),
		// Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
		String sPolicyNumber = common.ReadElement(Common.o.getObject("eleCLAPolicyNumber"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
		String sPolicyNumberTrim[] = sPolicyNumber.split("\\# ");
		String policyNumber = sPolicyNumberTrim[1].toString();
		PCThreadCache.getInstance().setProperty(PCConstants.CACHE_POLICY_NUMBER, policyNumber);

		String policyNumberSearch = policyNumber.substring(policyNumber.length() - 6);
		PCThreadCache.getInstance().setProperty(PCConstants.CACHE_POLICY_NUMBER_SEARCH, policyNumberSearch);
		logger.info("Policy Created::Policy Number '" + policyNumber + "'");
		logger.info("Policy Created::Last Six Degit Policy Number '" + policyNumberSearch + "'");

		updateColumnNameValues.put(PCConstants.PolicyNumber, policyNumber);
		whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
		PCThreadCache.getInstance().setProperty("PolicyNumber", policyNumber);
		//status = sXL.executeUpdateQuery(sheetname, updateColumnNameValues, whereConstraint);

		// status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"),
		// "Update "+sheetname+" Set PolicyNumber = '"+Payment.PolicyNumber+"' where
		// ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
		HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "CLA Policy Number shold be generated", "CLA Policy Number generated: Policy Number is '" + policyNumber + "'", "PASS");
		return status;
	}

	public boolean VerifyResults(String sFuncValue) throws Exception {
		boolean status = false;
		String[] sValue = sFuncValue.split(":::");
		logger.info("Verifying the Results");
		String Value = null;
		switch (sValue[0].toUpperCase()) {
		case "VERIFYREWRITESTATUS":
			logger.info(sValue[0]);
			status = PolicyTermCompare(Common.o.getObject("eleAccountSummary_PolicyTermsTbl"), 0, 6, sValue[1], "Bound", "div");
			break;
		case "VERIFYPOLICYNUMBER":
			logger.info(sValue[0]);
			String value1 = common.ReadElement(Common.o.getObject("eleRewritePolicy"), Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
			String value2 = common.ReadElementGetAttribute(Common.o.getObject("eleRewritePolicyNumber"), "value", Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
			status = common.CompareStringResult(sValue[0], value1, value2);
		
	/*case "VERIFYPOLICYNUMBERREWRITE":
		logger.info(sValue[0]);
		String policy1 = HTML.fnInsertResult(PCThreadCache.getInstance().getProperty(POLICY NUMBER), sDesc, sExpected, sActual, sResult);
		String policy2 = common.ReadElementGetAttribute(Common.o.getObject("eleRewritePolicyNumber"), "value", Integer.valueOf

(HTML.properties.getProperty("VERYLONGWAIT")));
		status = common.CompareStringResult(sValue[0], policy1, policy2);*/
	}
		
		
		if (!status) {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "" + sValue[0] + " case should PASS", "" + sValue[0] + " case should not fail", "FAIL");
			status = true;
		}
		return status;
	}

	/**
	 * @function This function use to Select the data from the table and performs
	 *           the action accordingly
	 * @param obj,readCol,actionCol,strReadString,actionObjetName
	 * @return status
	 * @throws Exception
	 */
	public Boolean PolicyTermCompare(By obj, int readCol, int actionCol, String strReadString, String actionObjetName, String sTagName) 

throws Exception {
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
					String objName = element.getText();
					if (objName.contains(actionObjetName)) {
						Status = true;
						ActionObject = true;
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
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should search object in the table cell and click on object. Object name is '" + actionObjetName + "'", "System searched object in the table and clicked on object. object name is '" + actionObjetName + "'", "PASS");
				Status = true;
			} else {
				logger.info("Search and click on object in the table cell and object name is '" + actionObjetName + "'");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should search object in the table cell and click on object. Object name is '" + actionObjetName + "'", "System searched object in the table and clicked on object. object name is '" + actionObjetName + "'", "FAIL");
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