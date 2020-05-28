
package com.pc.screen;

import java.io.IOException;
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

public class WCOptions {

	public static String sheetname = "WCOptions";
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();

	public Boolean SCRWCOptions() throws Exception {

		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			return status;
		}
		return status;
	}

	/**
	 * Function to fill the Plan Details
	 * 
	 * @param sFuncValue
	 * @return boolean
	 * @throws Exception
	 */
	public boolean FillplanDetails(String sFuncValue) throws Exception {
		boolean status = false;
		String[] sValue = sFuncValue.split(":::");
		Integer x = Integer.valueOf(HTML.properties.getProperty("NORMALWAIT"));
		WebElement mytable = ManagerDriver.getInstance().getWebDriver().findElement(Common.o.getObject("eleWCoption_StateTable"));
		List<WebElement> rows_table = mytable.findElements(By.tagName("tr"));
		for (int i = 0; i <= rows_table.size() - 1; i++) {

			status = SCRCommon.DataWebTable(Common.o.getObject("eleWCoption_StateTable"), i + 1, 0, "", "single", "div");
			Boolean eleExist = common.WaitForElementExist(Common.o.getObject("lsjLineCov_PlanId"), x);
			if (eleExist) {
				status = common.WaitUntilClickable(Common.o.getObject("lsjLineCov_PlanId"), x);
				status = common.SafeAction(Common.o.getObject("lsjLineCov_PlanId"), sValue[0], "lst");
				/*
				 * if(!(sValue[1].equals("NULL"))) {
				 * status=common.SafeAction(Common.o.getObject("lstLineCov_DividendVar"),
				 * sValue[1], "lst"); }
				 */
				if (sValue.length == 2) // 2nd field value availabel in excel
				{
					status = common.SafeAction(Common.o.getObject("lstLineCov_DividendVar"), sValue[1], "lst");
				}

			}

		}
		if (rows_table.size() == 0) {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "Validate the selection of  Plan Id & dividend code in WC option screen", "No State is available in Table", "FAIL");
		}
		return status;
	}

	/**
	 * Function used to Click the avaialble state in wc participation path table so
	 * to fill the details accordingly
	 * 
	 * @param strStateValue
	 * @return
	 * @throws Throwable
	 */
	public boolean selectState1(String strStateValue) throws Throwable {
		boolean status = false;
		int sRowCount = SCRCommon.TableRowCount(Common.o.getObject("eleWCParticipationPlanStateTable"));
		for (int i = 0; i < sRowCount; i++) {
			status = common.ActionOnTable_JS(Common.o.getObject("eleWCParticipationPlanStateTable"), 0, 0, strStateValue, "div");
			if (status) {
				break;
			}
		}
		if (!status) {
			logger.info("'" + strStateValue + "' state is not present in the Participation Plan Table");
		}
		return status;
	}

	/**
	 * Function used to Click the avaialble state in wc participation path table so
	 * to fill the details accordingly
	 * 
	 * @param strStateValue
	 * @return
	 * @throws Throwable
	 */
	public boolean selectState(String strFuncValue) throws Throwable {
		boolean blnStatus = false;
		boolean blnSearchString = false;
		String[] strSplitStateValue = strFuncValue.split("###");
		String[] strEachStateValue = null;
		int intRowCount = SCRCommon.TableRowCount(Common.o.getObject("eleWCParticipationPlanStateTable"));
		for (int i = 0; i <= intRowCount - 1; i++) {
			String strStateValue = common.GetTextFromTable(Common.o.getObject("eleWCParticipationPlanStateTable"), i, 0);
			inner: for (int j = 0; j < strSplitStateValue.length; j++) {
				blnSearchString = false;
				strEachStateValue = strSplitStateValue[j].split(":::");
				if (strEachStateValue[0].equals(strStateValue)) {
					blnSearchString = true;
					// blnStatus=common.ActionOnTable_JS(Common.o.getObject("eleWCParticipationPlanStateTable"),
					// 0, 0, strEachStateValue[0], "div");
					blnStatus = SCRCommon.ActionOnTable(Common.o.getObject("eleWCParticipationPlanStateTable"), 0, 0, 

strEachStateValue[0], "div");
					if (strEachStateValue.length == 2) {
						blnStatus = common.SafeAction(Common.o.getObject("lsjWCPlanID"), strEachStateValue[1], 

"lsjWCPlanID");
					} else if (strEachStateValue.length == 3) {
						blnStatus = common.SafeAction(Common.o.getObject("lsjWCPlanID"), strEachStateValue[1], 

"lsjWCPlanID");
						blnStatus = common.SafeAction(Common.o.getObject("lsjWCDividendVariation"), strEachStateValue[2], 

"lsjWCDividendVariation");
					}
				}
				if (blnSearchString) {
					break inner;
				}
			}
			if (!blnSearchString) {
				logger.info("State is not avilable in the ");
			}
		}
		if (!blnStatus) {
			logger.info("'" + strFuncValue + "' state is not present in the Participation Plan Table");
		}
		return blnStatus;
	}

	public boolean Addmanuscript(String sFunValue) {

		boolean status = false;

		int sRowCount = SCRCommon.TableRowCount(Common.o.getObject("eleAddManuscripttbl"));
		try {
			status = SCRCommon.DataWebTable(Common.o.getObject("eleAddManuscripttbl"), sRowCount, 1, "", "single", "div");
			status = common.SafeAction(Common.o.getObject("edtManualDescription"), sFunValue, "edt");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	public boolean selectManuscript(String sReadString) throws Exception {
		boolean status = false;
		status = common.ActionOnTable(Common.o.getObject("eleAddManuscripttbl"), 1, 0, sReadString, "div");
		return status;
	}

	public static boolean uploadFile() {
		boolean status = true;
		try {
			Runtime.getRuntime().exec("C:\\Selenium\\AutoIT\\FileUpload.exe");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return status;
	}

}