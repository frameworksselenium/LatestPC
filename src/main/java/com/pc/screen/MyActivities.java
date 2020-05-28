
/**
 * @ClassPurpose This Class used for the Desktop usecase
 * @Scriptor Nishantni
 * @ReviewedBy
 * @ModifiedBy
 * @LastDateModified 3/22/2017
 */
package com.pc.screen;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.PCThreadCache;

public class MyActivities {

	public static String sheetname = "MyActivities";
	Common common = CommonManager.getInstance().getCommon();
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));

	/**
	 * @function Use to perform all the objects/elements/functions from the
	 *           DocumentInfo sheet
	 * @return true/false
	 * @throws Exception
	 */
	public Boolean SCRMyActivities() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			return status;
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
	 * @function This function is used to select data from table in My Activities
	 *           screen
	 * @param sReadString
	 * @return true/false
	 * @throws Exception
	 */
	public boolean SelectActivityFromActivityTable(String sReadString) throws Exception {
		boolean status = false;
		status = common.ActionOnTable(Common.o.getObject("eleMyActivitiesTbl"), 8, 0, sReadString, "div");
		return status;
	}

	public boolean openActivityFromActivityTable(String sReadString) throws Exception {
		boolean blnStatus = false;
		boolean blnSearchString = false;
		String strPageCount = null;
		String[] strReadStrings = sReadString.split(":::");
		blnSearchString = SCRCommon.ActionOnTable(Common.o.getObject("eleMyActivitiesTbl"), 22, 8, strReadStrings[1], strReadStrings[0], "a");
		if (blnSearchString) {
			blnStatus = true;
		}
		Outer:
		// while(!blnSearchString)
		if (!blnSearchString) {
			// blnStatus = common.ElementExist(Common.o.getObject("eleNextPage"));
			int icheckNextPageButton = common.ElementSize(Common.o.getObject("eleNextPage"));
			if (icheckNextPageButton > 0) {
				// if(blnStatus)
				// {
				strPageCount = common.ReadElement(Common.o.getObject("eleCommonPageNumber"), Integer.valueOf(HTML.properties.getProperty("LONGWAIT")));
				// strPageCount = strPageCount.substring(strPageCount.length()-2);
				strPageCount = SCRCommon.getIntFromString(strPageCount);
				for (int i = 0; i < Integer.parseInt(strPageCount); i++) {
					blnStatus = common.SafeAction(Common.o.getObject("eleNextPage"), "", "eleNextPage");
					blnStatus = common.SafeAction(Common.o.getObject("eleVerifyActivityPage"), "", "eleVerifyActivityPage");
					// blnSearchString =
					// common.ActionOnTable(Common.o.getObject("eleMyActivitiesTbl"), 22, 8,
					// strReadStrings[1], strReadStrings[0], "a");
					blnSearchString = SCRCommon.ActionOnTable(Common.o.getObject("eleMyActivitiesTbl"), 23, 8, 

strReadStrings[1], strReadStrings[0], "a");
					if (blnSearchString) {
						HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), 

PCThreadCache.getInstance().getProperty("methodName"), "System should search object in the table cell and click on object. Object name is '" + strReadStrings[0] + "'", "System searched object in the table and clicked on object. object name is '" + strReadStrings[0] + "'", "PASS");
						break Outer;
					}
				}
			} else {
				logger.info("" + sReadString + " does not exist in My Queues Table");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "" + sReadString + " should available in the my Queues Table", "" + sReadString + " is not available in the my Queues Table", "FAIL");
			}
		}
		if (!blnSearchString) {
			logger.info("" + sReadString + " does not exist in My Queues Table");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search string in table and Search string is '" + strReadStrings[0] + "'", "System searched string in table and search string '" + strReadStrings[0] + "' is  not present", "FAIL");
		} else {
			blnStatus = true;
		}
		return blnStatus;
	}

	/**
	 * @function This function is used to select data from table in My Activities
	 *           screen
	 * @param sReadString
	 * @return true/false
	 * @throws Exception
	 */

	public boolean VerifyResults(String funValue) throws Exception {
		boolean status = false;
		String[] sValue = funValue.split(":::");
		String Value = null;
		switch (sValue[0].toUpperCase()) {
		case "VERIFYMYACTIVITYACTIVITYTODAY":
			Value = common.GetTextFromTable(Common.o.getObject("eleMyActivitiesTbl"), 7, sValue[1]);
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			break;

		case "VERIFYMYACTIVITYSTATUS":
			Value = common.GetTextFromTable(Common.o.getObject("eleMyActivitiesTbl"), 7, sValue[1]);
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			break;
		case "VERIFYBUSINESSSEGEMT":
			Value = common.GetTextFromTable(Common.o.getObject("eleMyActivitiesTbl"), 16, sValue[1]);
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			logger.info("Verifying " + sValue[0] + ": " + sValue[1] + " is matching with " + Value);
			break;

		}
		if (!status) {
			HTML.fnInsertResult(HTML.properties.getProperty("testcasename"), HTML.properties.getProperty("methodName"), "" + sValue[0] + " case should PASS", "" + sValue[0] + " case should not fail", "FAIL");
			status = true;
		}
		return status;
	}

	/**
	 * @function Use to select the activity from queue
	 * @param sFuncValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean selectActivityToAssignOrClose(String sReadString) throws Exception {
		boolean blnStatus = false;
		boolean blnSearchString = false;
		String strPageCount = null;
		blnSearchString = common.ActionOnTableTrueFalse(Common.o.getObject("eleMyActivitiesTbl"), 22, 0, sReadString, "img");
		if (blnSearchString) {
			blnStatus = true;
		}
		Outer:
		// while(!blnSearchString)
		if (!blnSearchString) {
			int icheckNextPageButton = common.ElementSize(Common.o.getObject("eleNextPage"));
			if (icheckNextPageButton > 0) {
				blnStatus = common.ElementExist(Common.o.getObject("eleNextPage"));
				if (blnStatus) {
					strPageCount = common.ReadElement(Common.o.getObject("eleCommonPageNumber"), Integer.valueOf(HTML.properties.getProperty("LONGWAIT")));
					// strPageCount = strPageCount.substring(strPageCount.length()-2);
					strPageCount = SCRCommon.getIntFromString(strPageCount);
					for (int i = 0; i <= Integer.parseInt(strPageCount); i++) {
						blnStatus = common.SafeAction(Common.o.getObject("eleNextPage"), "", "eleNextPage");
						blnSearchString = common.ActionOnTableTrueFalse(Common.o.getObject("eleMyActivitiesTbl"), 22, 0, sReadString, "img");
						if (blnSearchString) {
							HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), 

PCThreadCache.getInstance().getProperty("methodName"), "System should search object in the table cell and click on object. Object name is '" + sReadString + "'", "System searched object in the table and clicked on object. object name is '" + sReadString + "'", "PASS");
							break Outer;
						}
					}
				} else {
					logger.info("" + sReadString + " does not exist in My Queues Table");
				}
			} else {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "" + sReadString + " should available in the my Queues Table", "" + sReadString + " is not available in the my Queues Table", "FAIL");
			}
		}
		if (!blnSearchString) {
			logger.info("" + sReadString + " does not exist in My Queues Table");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "" + sReadString + " should available in the my Queues Table", "" + sReadString + " is not available in the my Queues Table", "FAIL");
		} else {
			blnStatus = true;
		}
		return blnStatus;
	}
}