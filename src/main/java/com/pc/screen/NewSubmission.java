
package com.pc.screen;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.PCThreadCache;
import com.pc.utilities.XlsxReader;

import org.openqa.selenium.JavascriptExecutor;

public class NewSubmission {

	public static String sheetname = "NewSubmission";
	Common common = CommonManager.getInstance().getCommon();
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
	public static XlsxReader sXL;

	public Boolean SCRNewSubmission() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			return false;
		}
		// String sProductSelect = common.getSpecifiedExcelValue(sheetname,
		// "ProductSelect");
		/*
		 * if (sProductSelect.equals("SOR")) {
		 * if(common.WaitUntilClickable(Common.o.getObject("assertforPolicyINFOPage"),
		 * Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")))) {
		 * logger.info("System displayed Organisation Page");
		 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
		 * PCThreadCache.getInstance().getProperty("methodName"),
		 * "System should display Organisation Page"
		 * ,"System displayed Organisation Page", "PASS"); status = true; } else {
		 * logger.info("System not displayed Organisation Page");
		 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
		 * PCThreadCache.getInstance().getProperty("methodName"),
		 * "System should display Organisation Page"
		 * ,"System not displayed Organisation Page", "FAIL"); status = false; } } else
		 * if (sProductSelect.equals("Shell")) {
		 * if(common.WaitForElementExist(Common.o.getObject("eleCLATitlePolicyInfo"),
		 * Integer.valueOf(HTML.properties.getProperty("LONGWAIT")))) {
		 * logger.info("System displayed Policy Info Page");
		 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
		 * PCThreadCache.getInstance().getProperty("methodName"),
		 * "System should display Policy Info Page","System displayed Policy Info Page",
		 * "PASS"); status = true; } else {
		 * logger.info("System not displayed Policy Info Page");
		 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
		 * PCThreadCache.getInstance().getProperty("methodName"),
		 * "System should display Policy Info Page"
		 * ,"System not displayed Policy Info Page", "FAIL"); status = false; } } else
		 * if (sProductSelect.equals("Multiple")) {
		 * if(common.WaitForElementExist(Common.o.getObject("eleAccountFileSummary"),
		 * Integer.valueOf(HTML.properties.getProperty("LONGWAIT")))) {
		 * logger.info("System displayed Account File Summary Page");
		 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
		 * PCThreadCache.getInstance().getProperty("methodName"),
		 * "System should display Account File Summary Page"
		 * ,"System displayed Account File Summary Page", "PASS"); status = true; } else
		 * { logger.info("System not displayed Account File Summary Page");
		 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
		 * PCThreadCache.getInstance().getProperty("methodName"),
		 * "System should display Account File Summary Page"
		 * ,"System not displayed Account File Summary Page", "FAIL"); status = false; }
		 * }
		 */
		return status;
	}

	public boolean DefaultEffectiveDate() throws Exception {
		boolean status = false;
		String sCurrentDate = SCRCommon.ReturnCurrentDate();
		status = common.SafeAction(Common.o.getObject("edtEffDate"), sCurrentDate, "edt");
		return status;
	}

	public boolean DefaultEffectiveDate(String strFuncValue) throws Exception {
		boolean status = false;
		String[] strSplitValue = strFuncValue.split(":::");
		String strDate = null;
		switch (strSplitValue[0].toUpperCase()) {
		case "CURRENTDATE":
			strDate = SCRCommon.ReturnCurrentDate();
			status = common.SafeAction(Common.o.getObject("edtEffDate"), strDate, "edt");
			break;
		case "INCREASEDECREASEDATE":
			strDate = SCRCommon.returnDate(Integer.parseInt(strSplitValue[1]));
			status = common.SafeAction(Common.o.getObject("edtEffDate"), strDate, "edt");
			break;
		}
		return status;
	}

	public boolean InCaseEffectiveDate(String strFuncValue) throws Exception {
		boolean status = false;
		String[] strSplitValue = strFuncValue.split(":::");
		String strDate = null;
		switch (strSplitValue[0].toUpperCase()) {
		case "CURRENTDATE":
			strDate = SCRCommon.ReturnCurrentDate();
			status = common.SafeAction(Common.o.getObject("edtEffDate"), strDate, "edt");
			break;
		case "INCREASEDECREASEDATE":
			strDate = SCRCommon.ReturnDate(Integer.parseInt(strSplitValue[1]));
			status = common.SafeAction(Common.o.getObject("edtEffDate"), strDate, "edt");
			break;
		}
		return status;
	}

	public boolean DefaultExpiryDate(String strFuncValue) throws Exception {
		boolean status = false;
		String[] strSplitValue = strFuncValue.split(":::");
		String strDate = null;
		switch (strSplitValue[0].toUpperCase()) {
		case "CURRENTDATE":
			strDate = SCRCommon.ReturnCurrentDate();
			status = common.SafeAction(Common.o.getObject("edtExpDate"), strDate, "edt");
			break;
		case "INCREASEDECREASEDATE":
			strDate = SCRCommon.returnDate(Integer.parseInt(strSplitValue[1]));
			status = common.SafeAction(Common.o.getObject("edtExpDate"), strDate, "edt");
			break;
		}
		return status;
	}

	/**
	 * @function This function use to select the LOB dynamically
	 * @param value(LOB
	 *            & System)
	 * @return status
	 * @throws Exception
	 */
	public boolean SelectLOB(String value) throws Exception {
		boolean status = false;
		String[] sValue = value.split(":::");
		try {
			status = common.ActionOnTable(Common.o.getObject("funSelectLOB"), 1, 0, sValue[0], sValue[1], "a");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public boolean VerifyMultipleSORForNGS(String proName) throws Exception {
		boolean status = false;
		try {
			
		/*	status = common.ActionOnTable(Common.o.getObject("eleProductNameTable"),2,0,proName,"div");
			status = common.ElementExist(Common.o.getObject("lstNewSubmissionSORSHELLList"));
			if(status)
			{
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "For NGS, multiple product option, SHELL list should NOT be displayed", "SHELL List is displayed for NGS product 

while selecting Multiple option", "FAIL");
				logger.info("Element lstNewSubmissionSORSHELLList is displayed. validation fails");
			}
			else
			{
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "For NGS, multiple product option, SHELL list should Not be displayed", "SHELL List is NOT displayed for NGS 

product while selecting Multiple option", "PASS");
				logger.info("Element lstNewSubmissionSORSHELLList is NOT displayed");
			}
			*/
			status = common.ActionOnTable(Common.o.getObject("eleProductNameTable"),2,1,proName,"div");
			//status = common.ElementExist(Common.o.getObject("lstNewSubmissionSORSHELLList"));
			int size = common.ElementSize(Common.o.getObject("lstNewSubmissionSORSHELLList"));
			if(size > 0)
			{
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "For NGS, multiple product option, SOR list should be displayed", "SOR List is displayed for NGS product while selecting Multiple option", "PASS");
				logger.info("Element lstNewSubmissionSORSHELLList is displayed");
			}
			else
			{
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "For NGS, multiple product option, SOR list should be displayed", "SOR List is NOT displayed for NGS product while selecting Multiple option", "FAIL");
				logger.info("Element lstNewSubmissionSORSHELLList is NOT displayed");
			}
			status = common.ActionOnTable(Common.o.getObject("eleProductNameTable"),2,0,proName,"div");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * @function This function use to select multiple LOB based on param
	 * @param value(LOB
	 *            & System)
	 * @return status
	 * @throws Exception
	 */
	public boolean SelectMultipleLOB(String value) throws Exception {
		boolean status = false;
		String[] sValue = value.split(":::");
		try {
			if (sValue[0].equalsIgnoreCase("Next Gen Spectrum"))
			{
				status = common.ActionOnTableSelect(Common.o.getObject("eleProductNameTable"), 2, 1, sValue[0], sValue[1], sValue[2], "div");
			}
			else
			{
				status = common.ActionOnTableSelect(Common.o.getObject("eleProductNameTable"), 2, 0, sValue[0], sValue[1], sValue[2], "div");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	public boolean VerifyResults(String sFuncValue) throws Exception {
		boolean status = false;
		String[] sValue = sFuncValue.split(":::");
		logger.info("Verifying the Results");
		String Value = null;
		switch (sValue[0].toUpperCase()) {
		case "VERIFYNONAMPLOB":
			logger.info(sValue[0]);
			Value = common.GetTextFromTable(Common.o.getObject("eleProductNameTable"), 1, sValue[1]);
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			Value = common.GetTextFromTable(Common.o.getObject("eleProductNameTable"), 1, sValue[2]);
			status = common.CompareStringResult(sValue[0], sValue[2], Value);
			Value = common.GetTextFromTable(Common.o.getObject("eleProductNameTable"), 1, sValue[3]);
			status = common.CompareStringResult(sValue[0], sValue[3], Value);
			Value = common.GetTextFromTable(Common.o.getObject("eleProductNameTable"), 1, sValue[4]);
			status = common.CompareStringResult(sValue[0], sValue[4], Value);
			Value = common.GetTextFromTable(Common.o.getObject("eleProductNameTable"), 1, sValue[5]);
			status = common.CompareStringResult(sValue[0], sValue[5], Value);
			Value = common.GetTextFromTable(Common.o.getObject("eleProductNameTable"), 1, sValue[6]);
			status = common.CompareStringResult(sValue[0], sValue[6], Value);
			Value = common.GetTextFromTable(Common.o.getObject("eleProductNameTable"), 1, sValue[7]);
			status = common.CompareStringResult(sValue[0], sValue[7], Value);
			Value = common.GetTextFromTable(Common.o.getObject("eleProductNameTable"), 1, sValue[8]);
			status = common.CompareStringResult(sValue[0], sValue[8], Value);
			Value = common.GetTextFromTable(Common.o.getObject("eleProductNameTable"), 1, sValue[9]);
			status = common.CompareStringResult(sValue[0], sValue[9], Value);
			Value = common.GetTextFromTable(Common.o.getObject("eleProductNameTable"), 1, sValue[10]);
			status = common.CompareStringResult(sValue[0], sValue[10], Value);
			Value = common.GetTextFromTable(Common.o.getObject("eleProductNameTable"), 1, sValue[11]);
			status = common.CompareStringResult(sValue[0], sValue[11], Value);
			Value = common.GetTextFromTable(Common.o.getObject("eleProductNameTable"), 1, sValue[12]);
			status = common.CompareStringResult(sValue[0], sValue[12], Value);
			Value = common.GetTextFromTable(Common.o.getObject("eleProductNameTable"), 1, sValue[13]);
			status = common.CompareStringResult(sValue[0], sValue[13], Value);
			Value = common.GetTextFromTable(Common.o.getObject("eleProductNameTable"), 1, sValue[14]);
			status = common.CompareStringResult(sValue[0], sValue[14], Value);
			Value = common.GetTextFromTable(Common.o.getObject("eleProductNameTable"), 1, sValue[15]);
			status = common.CompareStringResult(sValue[0], sValue[15], Value);
			Value = common.GetTextFromTable(Common.o.getObject("eleProductNameTable"), 1, sValue[16]);
			status = common.CompareStringResult(sValue[0], sValue[16], Value);
			Value = common.GetTextFromTable(Common.o.getObject("eleProductNameTable"), 1, sValue[17]);
			status = common.CompareStringResult(sValue[0], sValue[17], Value);
			Value = common.GetTextFromTable(Common.o.getObject("eleProductNameTable"), 1, sValue[18]);
			status = common.CompareStringResult(sValue[0], sValue[18], Value);
			Value = common.GetTextFromTable(Common.o.getObject("eleProductNameTable"), 1, sValue[19]);
			status = common.CompareStringResult(sValue[0], sValue[19], Value);
			break;
		case "VERIFYAMPLOB":
			logger.info(sValue[0]);
			Value = common.GetTextFromTable(Common.o.getObject("eleProductNameTable"), 1, sValue[1]);
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			Value = common.GetTextFromTable(Common.o.getObject("eleProductNameTable"), 1, sValue[2]);
			status = common.CompareStringResult(sValue[0], sValue[2], Value);
			Value = common.GetTextFromTable(Common.o.getObject("eleProductNameTable"), 1, sValue[3]);
			status = common.CompareStringResult(sValue[0], sValue[3], Value);
			Value = common.GetTextFromTable(Common.o.getObject("eleProductNameTable"), 1, sValue[4]);
			status = common.CompareStringResult(sValue[0], sValue[4], Value);
			Value = common.GetTextFromTable(Common.o.getObject("eleProductNameTable"), 1, sValue[5]);
			status = common.CompareStringResult(sValue[0], sValue[5], Value);
			Value = common.GetTextFromTable(Common.o.getObject("eleProductNameTable"), 1, sValue[6]);
			status = common.CompareStringResult(sValue[0], sValue[6], Value);
			Value = common.GetTextFromTable(Common.o.getObject("eleProductNameTable"), 1, sValue[7]);
			status = common.CompareStringResult(sValue[0], sValue[7], Value);
			Value = common.GetTextFromTable(Common.o.getObject("eleProductNameTable"), 1, sValue[8]);
			status = common.CompareStringResult(sValue[0], sValue[8], Value);
			Value = common.GetTextFromTable(Common.o.getObject("eleProductNameTable"), 1, sValue[9]);
			status = common.CompareStringResult(sValue[0], sValue[9], Value);
			Value = common.GetTextFromTable(Common.o.getObject("eleProductNameTable"), 1, sValue[10]);
			status = common.CompareStringResult(sValue[0], sValue[10], Value);
			Value = common.GetTextFromTable(Common.o.getObject("eleProductNameTable"), 1, sValue[11]);
			status = common.CompareStringResult(sValue[0], sValue[11], Value);
			break;

		}
		if (!status) {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "" + sValue[0] + " case should PASS", "" + sValue[0] + " case should not fail", "FAIL");
			status = true;
		}
		return status;
	}

	/**
	 * Set date for eff date field with one year old date. so that policy will be
	 * expired while issuing on current date
	 * 
	 * @return true/false
	 * @throws Exception
	 */
	public boolean Date(String strValue) throws Exception {
		boolean status = false;
		String strDate;
		Calendar Date = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		try {
			switch (strValue.toUpperCase()) {
			case "EFF_13_MON_OLD":
				/*
				 * Calendar Date = Calendar.getInstance(); Date.add(Calendar.MONTH,-1);
				 * Date.add(Calendar.YEAR,-1); String strDate =
				 * (Date.get(Calendar.MONTH)+1)+"/"+(Date.get(Calendar.DATE)+"/"+(Date.get(
				 * Calendar.YEAR))); status =
				 * common.SafeAction(Common.o.getObject("edtEffDate"), strDate, "edt");
				 */
				Date.add(Calendar.MONTH, -1);
				Date.add(Calendar.YEAR, -1);
				strDate = (Date.get(Calendar.MONTH) + 1) + "/" + (Date.get(Calendar.DATE) + "/" + (Date.get(Calendar.YEAR)));
				status = common.SafeAction(Common.o.getObject("edtEffDate"), strDate, "edt");
				break;
			case "EXPDT_LESSTHAN_A_YEAR":
				logger.info(strValue);
				Date.add(Calendar.MONTH, 7);
				String strMonth = String.valueOf(Date.get(Calendar.MONTH));
				if (strMonth.length() == 1) {
					strMonth = "0" + strMonth;
				}
				strDate = strMonth + "/" + (Date.get(Calendar.DATE) + "/" + (Date.get(Calendar.YEAR)));
				status = common.SafeAction(Common.o.getObject("edtExpDate"), strDate, "edt");
				break;
			case "EFF_DT_GT_THN_CRRDT":
				String strDt = SCRCommon.ReturnSixtyDaysFromDate();
				status = common.SafeAction(Common.o.getObject("edtEffDate"), strDt, "edt");
				break;

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * This method checks whether Shell type of Policies are not available for NGS
	 **/

	public boolean actionOnSubmissionsTable() throws Exception {
		String ele = "eleProductNameTable";
		By obj = Common.o.getObject(ele);
		WebElement mytable = ManagerDriver.getInstance().getWebDriver().findElement(obj);
		List<WebElement> allrows = mytable.findElements(By.tagName("tr"));
		for (int i = 0; i < allrows.size(); i++) {
			List<WebElement> Cells = allrows.get(i).findElements(By.tagName("td"));
			if (Cells.get(1).getText().contains("Next Gen Spectrum") && (!Cells.get(0).getText().contains("Shell"))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This method verifies whether the product type NGS is available or not in the
	 * Product Table returns true if NGS is not available returns false if NGS is
	 * available
	 **/

	public boolean verifyProductName() throws Exception {
		String ele = "eleProductNameTable";
		By obj = Common.o.getObject(ele);
		WebElement mytable = ManagerDriver.getInstance().getWebDriver().findElement(obj);
		List<WebElement> Cols = mytable.findElements(By.tagName("td"));

		for (int i = 0; i < Cols.size(); i++) {
			if (!Cols.get(1).getText().contains("Next Gen Spectrum")) {
				return true;
			}
		}
		return false;
	}
	
	public boolean VerifyProductNotExist(String strProName)throws Exception
	{
		String ele="eleProductNameTable";
		boolean blnStatus = true;
		By obj = Common.o.getObject(ele);
		JavascriptExecutor js = (JavascriptExecutor) ManagerDriver.getInstance().getWebDriver();
		WebElement mytable = ManagerDriver.getInstance().getWebDriver().findElement(obj);
		List<WebElement> allrows = mytable.findElements(By.tagName("tr"));
	
		for(int i=0;i<allrows.size();i++)
		{
			List<WebElement> Cells = allrows.get(i).findElements(By.tagName("td"));
			if(Cells.get(1).getText().equalsIgnoreCase(strProName))
			{
				blnStatus = false;
				break;
			}
		}
		if (blnStatus)
		{
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "Verify product '"+strProName+"' is NOT displayed in product selection table", "Product '"+strProName+"' is not displayed", 

"PASS");
		}
		else
		{
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "Verify product '"+strProName+"' is NOT displayed in product selection table", "Product '"+strProName+"' is displayed", "FAIL");
		}
		return blnStatus;
	}
}
