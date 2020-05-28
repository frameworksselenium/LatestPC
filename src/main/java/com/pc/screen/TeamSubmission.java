/**
 * @ClassPurpose Access all the data's for driver information sheet
 * @Scriptor Rajeshwari
 * @ReviewedBy
 * @ModifiedBy Rajeshwari
 * @LastDateModified 5/12/2017
 */
package com.pc.screen;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.PCThreadCache;

public class TeamSubmission {

	public static String sheetname = "TeamSubmission";
	Common common = CommonManager.getInstance().getCommon();
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName
(),PCThreadCache.getInstance().getProperty("TCID"));
	public static String AccountNumber;

	public Boolean SCRTeamSubmission() throws Exception {

		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;

	}

	/**
	 * @function to selectAccount from Submission tab
	 * @param sReadString
	 * @return
	 * @throws Exception
	 */
	public boolean SelectAccountFromSubmission(String sReadString) throws Exception {
		boolean status = false;
		status = common.ActionOnTable(Common.o.getObject("eleSelectTransacFromTable"), 1, 3, "", "a");
		return status;
	}

	/**
	 * @function used to select activity from MyGroup Team
	 * @param sReadString
	 * @return boolean
	 * @throws Exception
	 */
	public boolean SelectActivityFromMyGroupTeam(String sReadString) throws Exception {
		boolean status = false;
		status = common.ActionOnTable(Common.o.getObject("eleSelectActivityFromTable"), 7, 0, sReadString, "", "div");
		return status;
	}

	/**
	 * @function used to select any activity from table
	 * @param sValue
	 * @return boolean
	 * @throws Exception
	 */
	public boolean selectAnyActivity(String sValue) throws Exception {
		boolean status = false;
		String[] arrAction = sValue.split(":::");
		if (arrAction[0].equalsIgnoreCase("YES")) {
			status = common.ActionOnTable(Common.o.getObject("eleSelectActivityFromTable"), 5, 0, arrAction[1], "img");
		}
		return status;
	}

	/**
	 * @function used to expand the Group node from left pane
	 * @param GrpName
	 * @return boolean
	 * @throws IOException
	 */
	public boolean ExpandGroupNode(String GrpName) throws IOException {
		boolean status = false;
		String strXpath = "//div[@id='Team:TeamMenuTree:TeamTreeView-body']//tr//span[contains(@class,'x-tree-node-text') and text()='<GRPNAME>']/../img[contains(@class,'x-tree-expander')]";
		strXpath = strXpath.replace("<GRPNAME>", GrpName);
		logger.error("xpath for SubGrp" + strXpath);

		try {
			status = CommonManager.getInstance().getCommon().SafeAction(By.xpath(strXpath), "ele", "ele");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Thread ID = " + Thread.currentThread().getId() + " Error Occured =" + e.getMessage(), e);
		}
		return status;

	}

	/**
	 * @function used to verify the results as per expected cases mentioned.
	 * @param vValue
	 * @return boolean
	 * @throws Exception
	 */
	public boolean VerifyResults(String vValue) throws Exception {
		boolean status = false;
		String[] arrValue = vValue.split(":::");
		int RwCnt;
		String value;
		switch (arrValue[0].toUpperCase()) {
		case "VERIFY_TABLE_ROWCNT":
			status = common.ElementExist(Common.o.getObject(arrValue[1]));
			RwCnt = SCRCommon.TableRowCount(Common.o.getObject(arrValue[1]));

			if (RwCnt > 0) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Table RowCount in Team Tab -" + arrValue[1], "Total no of Rows displayed on screen is " + RwCnt, "PASS");
			} else {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Table RowCount in Team Tab -" + arrValue[1], "NO Rows displayed on screen is " + RwCnt, "WARNING");
			}
			break;
		case "VERIFY_ROWCNT":
			status = common.ElementExist(Common.o.getObject(arrValue[1]));
			RwCnt = SCRCommon.TableRowCount(Common.o.getObject(arrValue[1]));
			value = common.ReadElementGetAttribute(Common.o.getObject("edtTeam_Filter"), "value", Integer.valueOf

(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("Filter option ", arrValue[2], value);
			if (RwCnt > 0) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Table RowCount in Team Tab -" + arrValue[1], "Total no of Rows displayed on screen is " + RwCnt, "PASS");
			} else {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Table RowCount in Team Tab -" + arrValue[1], "NO Rows displayed on screen is " + RwCnt, "WARNING");
			}
			break;
		case "VERIFY_TOTAL_ROW_PERPAGE":
			status = common.ElementExist(Common.o.getObject(arrValue[1]));
			RwCnt = SCRCommon.TableRowCount(Common.o.getObject(arrValue[1]));

			status = common.CompareStringResult("Total record sper page", arrValue[2], Integer.toString(RwCnt));
			break;
		case "VALIDATE_ACTIVITY_DETAIL_SCREEN":
			status = common.SafeAction(common.o.getObject("eljInQ_Actvty_SubjectLnk"), "elj", "eljInQ_Actvty_SubjectLnk");
			status = common.ElementExist(Common.o.getObject(arrValue[1]));
			value = common.ReadElement(Common.o.getObject(arrValue[2]), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));

			status = common.CompareStringResult("Activity Status in Activity detail screen", arrValue[3], value);
			break;

		}

		return status;

	}
}