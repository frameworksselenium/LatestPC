/**
 * @ClassPurpose Access all the data's from AssignActivity sheet
 * @Scriptor Rathish Kuppusamy
 * @ReviewedBy
 * @ModifiedBy Rathish Kuppusamy
 * @LastDateModified 3/21/2017
 */
package com.pc.screen;

import java.io.IOException;
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

public class AssignActivity {

	public static String sheetname = "AssignActivity";
	//static Logger logger = Logger.getLogger(sheetname);
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();

	/**
	 * @function Use to perform all the objects/elements/functions from the Assign
	 *           Activity excel sheet
	 * @return true/false
	 * @throws Exception
	 */
	public Boolean SCRAssignActivity() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			return false;
		}
		return status;
	}

	/**
	 * @function use to select the activity from the summary screen
	 * @param strValue
	 * @return true/false
	 * @throws IOException
	 */
	public boolean SelectActivity(String strValue) throws IOException {
		boolean status = false;
		status = common.SelectActivity(strValue);
		return status;
	}

	/**
	 * @function use to select the activity table from the summary screen
	 * @param strValue
	 * @return true/false
	 * @throws IOException
	 */
	public boolean SelectActivityFromTable(String sReadString) throws Exception {
		boolean status = false;
		status = common.ActionOnTable(Common.o.getObject("eleActivityTblBody"), 1, 0, sReadString, "Assign", "a");
		return status;
	}

	/**
	 * @function This function is used to select data from table in My Groups screen
	 * @param sReadString
	 * @return true/false
	 * @throws Exception
	 */
	public boolean SelectActivityFromGroupTable(String sReadString) throws Exception {
		boolean status = false;
		status = common.ActionOnTable(Common.o.getObject("eleMyGroupsTbl"), 6, 0, sReadString, "", "div");
		return status;
	}

	/**
	 * @function This function is used to select data from table in My Queues screen
	 * @param sReadString
	 * @return true/false
	 * @throws Exception
	 */
	public boolean SelectActivityFromQueueTable(String sReadString) throws Exception {
		boolean status = false;
		status = common.ActionOnTable(Common.o.getObject("eleMyQueuesTbl"), 6, 0, sReadString, "", "div");
		return status;
	}

	/**
	 * @function This function is used to select data from table in My Activities
	 *           screen
	 * @param sReadString
	 * @return true/false
	 * @throws Exception
	 */
	public boolean SelectActivityFromActivityTableTable(String sReadString) throws Exception {
		boolean status = false;

		status = common.ActionOnTable(Common.o.getObject("eleMyActivitiesTbl"), 7, 0, sReadString, "", "div");
		return status;
	}

	public boolean SelectActivityFromActivityAssignTable(String sReadString) throws Exception {
		boolean status = false;

		status = common.ActionOnTable(Common.o.getObject("eleMyActivitiesAssign"), 1, 0, sReadString, "", "div");
		return status;
	}

	/**
	 * @function This function is used to select data from table having current date
	 * @param sReadActionCol
	 * @param sReadString
	 * @param sTagname
	 * @return true/false
	 * @throws Exception
	 */
	public boolean TodayActivity(int sReadActionCol, String sReadString, String sTagname) throws Exception {
		boolean status = false;
		status = common.ActionOnTable(Common.o.getObject("eleMyGroupsTbl"), 3, 9, SCRCommon.ReturnCurrentDate(), "", "a");
		return status;
	}

	/**
	 * @function This function is used to verify the actual result with expected
	 *           result
	 * @param funValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean VerifyResults(String funValue) throws Exception {
		boolean status = false;
		String[] sValue = funValue.split(":::");
		String Value = null;
		switch (sValue[0].toUpperCase()) {
		case "VERIFYASSIGNTO":
			// Value = common.ReadElement(Common.o.getObject("eleAssignTo"),
			// Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			Value = common.ReadElementGetAttribute(Common.o.getObject("eleAssignTo"), "value", Integer.valueOf

(HTML.properties.getProperty("NORMALWAIT")));
			// status = common.CompareExactStringResult("VerifyUser", sValue[1], Value);
			status = common.CompareStringResult("VerifyUser", sValue[1], Value);
			break;
		case "VERIFYACTIVITYASSIGNEDTO":
			Value = common.GetTextFromTable(Common.o.getObject("eleAccountSummary_Activitytbl"), 2, sValue[1]);
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			break;
		case "VERIFYRELATEDTO":
			Value = common.ReadElementGetAttribute(Common.o.getObject("eleRelatedTo"), "value", Integer.valueOf

(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("VerifyUser", sValue[1], Value);
			break;
		case "VERIFYPRIORITY":
			Value = common.ReadElementGetAttribute(Common.o.getObject("elePriority"), "value", Integer.valueOf

(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("VerifyUser", sValue[1], Value);
			break;
		case "VERIFYSECURITYLEVEL":
			Value = common.ReadElementGetAttribute(Common.o.getObject("eleSecurityLevel"), "value", Integer.valueOf

(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("VerifyUser", sValue[1], Value);
			break;
		case "VERIFYEXCEPTIONREASON":
			Value = common.ReadElementGetAttribute(Common.o.getObject("elePartnerExceptionResason"), "value", Integer.valueOf

(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("VerifyUser", sValue[1], Value);
			break;
		case "VERIFYACTIVITYTODAY":
			Value = common.GetTextFromTable(Common.o.getObject("eleMyGroupsTbl"), 8, 7, sValue[1]);
			status = common.CompareStringResult(sValue[0], sValue[2], Value);
			break;
		}
		if (!status) {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "" + sValue[0] + " case should PASS", "" + sValue[0] + " case should not fail", "FAIL");
			status = true;
		}
		return status;
	}

	/**
	 * @function Use to select the activity dynamically if there is a scroll also
	 * @param sFuncValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean scrollActivity(String sFuncValue) throws Exception {
		boolean status = false;
		logger.info("Entering into the scrollActivity function");
		for (int i = 0; i <= 50; i++) {
			List<WebElement> sGetList = driver.findElements(By.className("x-menu-item-text"));
			for (WebElement element : sGetList) {
				String objName = element.getText();
				if (objName.equals(sFuncValue)) {
					String readAttriID1 = element.getAttribute("id");
					common.highlightElement(By.id(readAttriID1));
					status = common.SafeAction(By.id(readAttriID1), "ele", "ele");
					logger.info("Activity Found");
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should click the " + sFuncValue + " Activity", "System clicked " + sFuncValue + " Activity", "PASS");
					status = true;
					break;
				}
			}
			if (status == false) {
				common.SafeAction(Common.o.getObject("eleActvityScrollBottom"), "eleActvityScrollBottom", 

"eleActvityScrollBottom");
			}
		}
		if (status == false) {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "" + sFuncValue + "Activity should present in the list", "" + sFuncValue + "Activity is not present in the list", "FAIL");
		}
		return status;
	}

	/**
	 * @function Use to select the activity from queue
	 * @param sFuncValue
	 * @return true/false
	 * @throws Exception
	 */

	public boolean SelectActivityFromQueue(String funValue) throws Exception {
		boolean status = false;
		String[] sValue = funValue.split(":::");
		String Value = null;
		Integer x = Integer.valueOf(HTML.properties.getProperty("NORMALWAIT"));
		Integer intRowCnt = 0;
		// Loop for pages
		// status=common.WaitUntilClickable(Common.o.getObject("eljMyQueue_NextDisabled"),
		// x);
		while (!(common.WaitUntilClickable(Common.o.getObject("eljMyQueue_NextDisabled"), x))) {
			intRowCnt = SCRCommon.TableRowCount(Common.o.getObject("eleMyQueuesTbl"));
			for (int i = 0; i < intRowCnt; i++) {
				// status = common.ActionOnTable(Common.o.getObject("eleMyQueuesTbl"), 9, 9,
				// sReadString, "", "div");
				String strAccNo = common.GetTextFromTable(Common.o.getObject("eleMyQueuesTbl"), i, 21);
				// String expAccno="534594330728128"; //yet to get acc no from sheetname

				if (strAccNo.equals(sValue[0])) {
					String strActivtyName = common.GetTextFromTable(Common.o.getObject("eleMyQueuesTbl"), i, 9);
					status = common.CompareStringResult("Activty Name in myQueue Table", sValue[1], strActivtyName);
				}

			}
			status = common.SafeAction(Common.o.getObject("eljMyQueue_Next"), "elj", "elj");
		}
		return status;

	}

}