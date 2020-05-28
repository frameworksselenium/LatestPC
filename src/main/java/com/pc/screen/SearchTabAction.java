/**
 * @ClassPurpose This Class used for the SearchTabAction usecase
 * @Scriptor Kumar
 * @ReviewedBy
 * @ModifiedBy Sojan David
 * @LastDateModified 3/29/2017
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

public class SearchTabAction {

	public static String sheetname = "SearchTabAction";
	Common common = CommonManager.getInstance().getCommon();
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName
(),PCThreadCache.getInstance().getProperty("TCID"));

	/**
	 * @function Use to perform all the objects/elements/functions from the
	 *           SearchTabAction excel sheet
	 * @return true/false
	 * @throws Exception
	 */
	public Boolean SCRSearchTabAction() throws Exception {

		Boolean status = true;
		String strCriteria = "";
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			return false;
		}
		strCriteria = common.getSpecifiedExcelValue(sheetname, "ActionPage");
		if (strCriteria.equals("Policy")) {
			if (common.WaitForElementExist(Common.o.getObject("eleSearchTabPolicyTitle"), Integer.valueOf

(HTML.properties.getProperty("LONGWAIT")))) {
				logger.info("System displayed Search Policy Page");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should display Search Policy Page", "System displayed Search Policy Page", "PASS");
				status = true;
			} else {
				logger.info("System not displayed Search Policy Page");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should display Search Policy Page", "System not displayed Search Policy Page", "FAIL");
				status = false;
			}
		}
		return status;
	}

	/**
	 * @function Use to compare the error alert
	 * @return true/false
	 * @throws Exception
	 */
	public boolean verifyErrorMsgDisplayedOrNot() throws Exception {
		boolean status = driver.findElements(Common.o.getObject("eleSearchTabErrorMsgs")).size() != 0;
		String strErrorMsg = common.ReadElement(Common.o.getObject("eleSearchTabErrorMsgs"), Integer.valueOf(HTML.properties.getProperty

("LONGWAIT")));
		if (status == true) {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "Verify whether error message is displayed", "Error message is displayed : '" + strErrorMsg + "'", "FAIL");
			status = false;
		} else {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "Verify whether error message is displayed or not", "Error message is not displayed : '" + strErrorMsg + "'", "PASS");
			status = true;
		}

		return status;
	}

	/**
	 * @function Use to compare the error alert
	 * @return true/false
	 * @throws Exception
	 */
	public boolean VerifyCompareErrorMsg(String strValue) throws Exception {
		SCRCommon.PageVerify("SearchTabErrorMsgs");
		boolean status = driver.findElements(Common.o.getObject("eleSearchTabErrorMsgs")).size() != 0;
		String strErrorMsg = common.ReadElement(Common.o.getObject("eleSearchTabErrorMsgs"), Integer.valueOf(HTML.properties.getProperty

("LONGWAIT")));

		if (status == true) {

			if (strErrorMsg.toUpperCase().equalsIgnoreCase(strValue)) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Verify whether error message is displayed as expected", "Expected Error message '" + strValue + "'  is matching with Actual : '" + strErrorMsg + "'", "PASS");
				status = true;
			} else {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Verify whether error message is displayed as expected", "Expected Error message '" + strValue + "' is not matching with Actual : '" + strErrorMsg + "'", "FAIL");
				status = false;
			}

		} else {

			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Verify whether error message is not displayed", "Error message is not displayed : '" + strErrorMsg + "'", "WARNING");
		}

		return status;
	}

	/**
	 * @function Use to verify the results
	 * @return true/false
	 * @throws Exception
	 */
	/*
	 * public boolean VerifySearchResults(String sValue) throws Exception { String
	 * strValue = ""; boolean status = false; String strCriteria = ""; try {
	 * strCriteria = common.getSpecifiedExcelValue(sheetname, "SearchCriteria");
	 * switch (strCriteria.toUpperCase()) { case "COMPANYNAME": strValue =
	 * common.GetTextFromTable(Common.o.getObject("eleSearchTabSearchResults"), 4,4,
	 * sValue); break; case "POLICYNUMBER": strValue =
	 * common.GetTextFromTable(Common.o.getObject("eleSearchTabSearchResults"), 2,2,
	 * sValue); break; }
	 * 
	 * if (strValue.equals(sValue)) {
	 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
	 * PCThreadCache.getInstance().getProperty("methodName"),
	 * "Verify whether specified searched item is displayed"
	 * ,"Searched item is displayed : '"+strValue+"'", "PASS"); status = true; }
	 * else {
	 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
	 * PCThreadCache.getInstance().getProperty("methodName"),
	 * "Verify whether specified searched item is not displayed"
	 * ,"Searched item is not displayed : '"+strValue+"'", "FAIL"); } }
	 * catch(Exception e) { e.printStackTrace(); } return status; }
	 */

	/**
	 * @function Uses this function to split the last six digit policy number
	 * @return true/false
	 * @throws Exception
	 */
	public boolean PolicyNumberSearch(String sValue) throws Exception {
		boolean status = true;
		String sLastSixADigit = sValue.substring(sValue.length() - 6);
		status = common.SafeAction(Common.o.getObject("edtSearchTabPolicyNum"), sLastSixADigit, "edt");
		return status;
	}

	/**
	 * @function
	 * @param sFuncValue
	 * @return
	 * @throws Exception
	 */
	public boolean lastArrowCheck() throws Exception {
		boolean status = false;
		int sResultsCount = common.ElementSize(Common.o.getObject("elePreviousLastArrow"));
		if (sResultsCount == 1) {
			status = common.SafeAction(Common.o.getObject("elePreviousLastArrow"), "elePreviousLastArrow", "elePreviousLastArrow");
		} else {
			status = true;
			logger.info("Last Arrow is not there");
		}
		return status;
	}

	/**
	 * @function Use to verify the results
	 * @return true/false
	 * @throws Exception
	 */
	public boolean VerifySearchResults(String sFuncValue) throws Exception {
		boolean status = true;
		boolean sPatternCheck = false;
		String strCriteria = common.getSpecifiedExcelValue(sheetname, "SearchCriteria");
		String compareValue = null;
		int sResultsCount = common.ElementSize(Common.o.getObject("eleSearchPoliciesResults"));
		if (sResultsCount == 1) {
			String sCountObject = common.ReadElement(Common.o.getObject("eleSearchPoliciesResults"), Integer.valueOf

(HTML.properties.getProperty("LONGWAIT")));
			String sCount = sCountObject.substring(sCountObject.length() - 1);
			SCRCommon.PageVerify("SearchTabSearchResults");
			for (int i = 0; i < Integer.valueOf(sCount); i++) {
				// sPatternCheck = ActionOnTable(Common.o.getObject("eleActivityPatterntbl"), 1,
				// 1, sValue[1], sValue[1], "a");
				switch (strCriteria.toUpperCase()) {
				case "COMPANYNAME":
					compareValue = GetTextFromTable(Common.o.getObject("eleSearchTabSearchResults"), 4, 4, sFuncValue);
					if (compareValue == null) {
						compareValue = "";
						sPatternCheck = false;
					} else {
						if (compareValue.equals(sFuncValue)) {
							sPatternCheck = true;
							logger.info("" + sFuncValue + " available in the account number column");
							HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), 

PCThreadCache.getInstance().getProperty("methodName"), "System should search string in account table and Search string is '" + sFuncValue + "'", 

"System searched string in account table and search string is  '" + sFuncValue + "'", "PASS");
						}
					}
					break;

				/*
				 * case "POLICYNUMBER": compareValue =
				 * GetTextFromTable(Common.o.getObject("eleSearchTabSearchResults"), 2,2,
				 * sFuncValue); if(compareValue == null) { compareValue = ""; sPatternCheck =
				 * false; } else{ if(compareValue.equals(sFuncValue)) { sPatternCheck = true;
				 * logger.info(""+sFuncValue+" available in the policy number column");
				 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
				 * PCThreadCache.getInstance().getProperty("methodName"),
				 * "System should search string in policy table and Search string is '" +
				 * sFuncValue +
				 * "'","System searched string in policy table and search string is  '" +
				 * sFuncValue + "'", "PASS"); } } break;
				 */
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
		} else {
			switch (strCriteria.toUpperCase()) {
			case "COMPANYNAME":
				compareValue = GetTextFromTable(Common.o.getObject("eleSearchTabSearchResults"), 4, 4, sFuncValue);
				if (compareValue == null) {
					compareValue = "";
					sPatternCheck = false;
				} else {
					if (compareValue.equals(sFuncValue)) {
						sPatternCheck = true;
						logger.info("" + sFuncValue + " available in the account number column");
						HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), 

PCThreadCache.getInstance().getProperty("methodName"), "System should search string in account table and Search string is '" + sFuncValue + "'", 

"System searched string in account table and search string is  '" + sFuncValue + "'", "PASS");
					}
				}
				break;
			}
		}

		return status;
	}

	/**
	 * @function function use to
	 * @return true/false
	 */
	public boolean VerifyPolicyNumber(String sFuncValue) {
		boolean status = false;
		String value = null;
		try {
			SCRCommon.PageVerify("SearchTabSearchResults");
			value = GetTextFromTable(Common.o.getObject("eleSearchTabSearchResults"), 2, 2, sFuncValue);
			status = common.CompareStringResult("PolicyNumberSearch", sFuncValue, value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
}