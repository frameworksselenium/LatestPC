
//@Component - This Component used to verify functionalities of Cancel Policy for SOR Policy
package com.pc.screen;

import java.io.IOException;
import java.util.ArrayList;
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

public class AccountHistory {
	
	public static String sheetname = "AccountHistory";
	//static Logger logger = Logger.getLogger(sheetname);
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();

	public Boolean SCRAccountHistory() throws Exception {

		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			return false;
		}

		return status;
	}

	public boolean VerifyHistoryTable(String sValue) throws NumberFormatException, Exception {

		boolean status = true;
		List<String> eleText = new ArrayList<String>();
		List<WebElement> lstDetails = driver.findElements(Common.o.getObject("eleHistoryTable"));
		for (WebElement ele : lstDetails) {
			eleText.add(ele.getText());
		}
		for (String s : sValue.split("::")) {
			if (eleText.contains(s)) {
				logger.info("Search String available in the table. '" + s + "'");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), s + "column should be displayed in History Table", s + "column is displayed in History Table", "PASS");
			} else {
				logger.info("Search String not available in the table. '" + s + "'");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), s + "column should be displayed in History Table", s + "column is not displayed in History Table", "FAIL");

			}
		}

		return status;
	}

	public boolean GetUserName(String sValue) throws Exception {
		boolean status = true;
		XlsxReader sXL = XlsxReader.getInstance();
		HashMap<String, Object> updateColumnNameValues = new HashMap<String, Object>();
		HashMap<String, Object> whereConstraint = new HashMap<String, Object>();
		String strUser = common.GetTextFromTable(Common.o.getObject("ele" + sValue), 0, 1);
		updateColumnNameValues.put(PCConstants.UserName, strUser);
		whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
		PCThreadCache.getInstance().setProperty("UserName", strUser);
		//status = sXL.executeUpdateQuery(PCConstants.SHEET_ACCOUNTHISTORY, updateColumnNameValues, whereConstraint);
		// status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"),
		// "Update AccountHistory Set UserName = '"+strUser+"' where
		// ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
		return status;

	}

	public boolean VerifySelectCriteriaResult(String sValue) throws Exception {
		boolean status = false;
		String[] strText = sValue.split("::");
		WebElement mytable = driver.findElement(Common.o.getObject("eleHistory_UserText"));
		List<WebElement> allrows = mytable.findElements(By.tagName("tr"));
		for (int i = 0; i <= allrows.size() - 1; i++) {
			status = common.VerifyTextFromTable(Common.o.getObject("eleHistory_UserText"), i, Integer.parseInt(strText[1]), strText

[0]);
		}
		return status;

	}

	public boolean VerifyRelatedToList(String sValue) throws IOException {
		boolean status = true;
		boolean blnflag = false;
		List<String> eleText = new ArrayList<String>();
		List<WebElement> lstDetails = driver.findElements(Common.o.getObject("eleHistory_RelatedToList"));
		for (String s : sValue.split(":::")) {
			for (WebElement ele : lstDetails) {
				if (ele.getText().contains(s)) {
					blnflag = true;
				}
			}
			if (blnflag) {
				logger.info("Search String available in the List. '" + s + "'");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), s + "should be displayed in Related To List", s + "is displayed in Related To List", "PASS");
			} else {
				logger.info("Search String not available in the table. '" + s + "'");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), s + "should be displayed in Related To List", s + "is not displayed in Related To List", "FAIL");

			}
			blnflag = false;

		}
		return status;
	}

}
