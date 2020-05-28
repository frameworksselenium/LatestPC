
/**
 * @ClassPurpose This Class used for the CustomerCompletion letter in SOR Policy
 * @Scriptor Raj
 * @ReviewedBy
 * @ModifiedBy
 * @LastDateModified 3/23/2017
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

public class MyQueues {

	public static String sheetname = "MyQueues";
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();

	/**
	 * @function Use to perform all the action in the SORCustomerCompltr sheet
	 * @return true/false
	 * @throws Exception
	 */
	public Boolean SCRMyQueues() throws Exception {

		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			return false;
		}

		return status;
	}

	/**
	 * @function Use to select the activity from queue
	 * @param sFuncValue
	 * @return true/false
	 * @throws IOException
	 * @throws Exception
	 */
	public boolean SelectActivityFromQueue(String sReadString) throws IOException {
		boolean blnStatus = false;
		boolean blnSearchString = false;
		String strPageCount = null;
		String[] strSplitValue = sReadString.split(":::");
		// blnSearchString =
		// common.ActionOnTableTrueFalse(Common.o.getObject("eleMyQueuesTbl"), 22, 0,
		// sReadString, "img");
		// blnSearchString =
		// SCRCommon.ActionOnTable(Common.o.getObject("eleMyQueuesTbl"), 22, 0,
		// strSplitValue[0], "img");
		try {
			blnSearchString = SCRCommon.ActionOnTable(Common.o.getObject("eleMyQueuesTbl"), 22, 9, 0, strSplitValue[0], strSplitValue[1], "", "img");
			if (blnSearchString) {
				blnStatus = true;
			}
			Outer:
			// while(!blnSearchString)
			if (!blnSearchString) {
				// blnStatus = common.ElementExist(Common.o.getObject("eleNextPage"));
				int icheckNextPageButton = common.ElementSize(Common.o.getObject("eleNextPage"));
				if (icheckNextPageButton > 0) {
					strPageCount = common.ReadElement(Common.o.getObject("eleCommonPageNumber"), Integer.valueOf(HTML.properties.getProperty("LONGWAIT")));
					strPageCount = SCRCommon.getIntFromString(strPageCount);
					for (int i = 0; i < Integer.parseInt(strPageCount); i++) {
						blnStatus = common.SafeAction(Common.o.getObject("eleNextPage"), "", "eleNextPage");
						blnStatus = common.SafeAction(Common.o.getObject("eleMyQueuesTitle"), "", "eleMyQueuesTitle");
						blnSearchString = SCRCommon.ActionOnTable(Common.o.getObject("eleMyQueuesTbl"), 22, 9, 0, strSplitValue[0], strSplitValue[1], "", "img");
						if (blnSearchString) {
							HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), 

PCThreadCache.getInstance().getProperty("methodName"), "System should search object in the table cell and click on object. Object name is '" + strSplitValue[0] + "'", "System searched object in the table and clicked on object. object name is '" + strSplitValue[0] + "'", "PASS");
							break Outer;
						}
					}
				} else {
					logger.info("" + sReadString + " does not exist in My Queues Table");
				}
			}
			if (!blnSearchString) {
				logger.info("" + sReadString + " does not exist in My Queues Table");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "" + sReadString + " should available in the my Queues Table", "" + sReadString + " is not available in the my Queues Table", "FAIL");
			} else {
				blnStatus = true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "" + sReadString + " should available in the my Queues Table", "" + sReadString + " is not available in the my Queues Table", 

"FAIL");
			e.printStackTrace();
		}
		return blnStatus;
	}

	/**
	 * @function Use to select the activity from queue
	 * @param sFuncValue
	 * @return true/false
	 * @throws IOException
	 * @throws Exception
	 */
	public boolean verifyActivityInMyQueue(String sReadString) throws IOException {
		boolean blnStatus = false;
		boolean blnSearchString = false;
		String strPageCount = null;
		String[] strSplitValue = sReadString.split(":::");
		String strCreator = null;
		strCreator = PCThreadCache.getInstance().getProperty("LOGGED_USERNAME");
		// blnSearchString =
		// common.ActionOnTableTrueFalse(Common.o.getObject("eleMyQueuesTbl"), 22, 0,
		// sReadString, "img");
		// blnSearchString =
		// SCRCommon.ActionOnTable(Common.o.getObject("eleMyQueuesTbl"), 22, 0,
		// strSplitValue[0], "img");
		try {
			// blnSearchString =
			// SCRCommon.ActionOnTable(Common.o.getObject("eleMyQueuesTbl"), 22, 9, 0,
			// strSplitValue[0], strSplitValue[1], "", "img");
			// blnSearchString =
			// SCRCommon.ActionOnTable(Common.o.getObject("eleMyQueuesTbl"), 22, 9, 11, 0,
			// strSplitValue[0], strSplitValue[1], strCreator,"", "img");
			blnSearchString = SCRCommon.ActionOnTable(Common.o.getObject("eleMyQueuesTbl"), 22, 9, 11, 9, strSplitValue[0], 

strSplitValue[1], strCreator, strSplitValue[1], "a");
			if (blnSearchString) {
				blnStatus = true;
			}
			Outer:
			// while(!blnSearchString)
			if (!blnSearchString) {
				// blnStatus = common.ElementExist(Common.o.getObject("eleNextPage"));
				int icheckNextPageButton = common.ElementSize(Common.o.getObject("eleNextPage"));
				if (icheckNextPageButton > 0) {
					strPageCount = common.ReadElement(Common.o.getObject("eleCommonPageNumber"), Integer.valueOf

(HTML.properties.getProperty("LONGWAIT")));
					strPageCount = SCRCommon.getIntFromString(strPageCount);
					for (int i = 0; i < Integer.parseInt(strPageCount); i++) {
						blnStatus = common.SafeAction(Common.o.getObject("eleNextPage"), "", "eleNextPage");
						blnStatus = common.SafeAction(Common.o.getObject("eleMyQueuesTitle"), "", "eleMyQueuesTitle");
						blnSearchString = SCRCommon.ActionOnTable(Common.o.getObject("eleMyQueuesTbl"), 22, 9, 11, 9, 

strSplitValue[0], strSplitValue[1], strCreator, strSplitValue[1], "a");
						if (blnSearchString) {
							HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), 

PCThreadCache.getInstance().getProperty("methodName"), "System should search object in the table cell and click on object. Object name is '" + 

strSplitValue[0] + "'", "System searched object in the table and clicked on object. object name is '" + strSplitValue[0] + "'", "PASS");
							break Outer;
						}
					}
				} else {
					logger.info("" + sReadString + " does not exist in My Queues Table");
				}
			}
			if (!blnSearchString) {
				logger.info("" + sReadString + " does not exist in My Queues Table");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "" + sReadString + " should available in the my Queues Table", "" + sReadString + " is not available in the my Queues Table", "FAIL");
			} else {
				blnStatus = true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "" + sReadString + " should available in the my Queues Table", "" + sReadString + " is not available in the my Queues Table", 

"FAIL");
			e.printStackTrace();
		}
		return blnStatus;
	}

	public boolean verifyActivityInMyQueueByAccNum(String sReadString) throws IOException {
		boolean blnStatus = false;
		boolean blnSearchString = false;
		String strPageCount = null;
		String[] strSplitValue = sReadString.split(":::");
		String strCreator = null;
		strCreator = PCThreadCache.getInstance().getProperty("LOGGED_USERNAME");
		try {
			blnSearchString = SCRCommon.ActionOnTable(Common.o.getObject("eleMyQueuesTbl"), 13, 9, 11, 9, strSplitValue[0], 

strSplitValue[1], strCreator, strSplitValue[1], "a");
			if (blnSearchString) {
				blnStatus = true;
			}
			Outer: if (!blnSearchString) {
				int icheckNextPageButton = common.ElementSize(Common.o.getObject("eleNextPage"));
				if (icheckNextPageButton > 0) {
					strPageCount = common.ReadElement(Common.o.getObject("eleCommonPageNumber"), Integer.valueOf(HTML.properties.getProperty("LONGWAIT")));
					strPageCount = SCRCommon.getIntFromString(strPageCount);
					for (int i = 0; i < Integer.parseInt(strPageCount); i++) {
						blnStatus = common.SafeAction(Common.o.getObject("eleNextPage"), "", "eleNextPage");
						blnStatus = common.SafeAction(Common.o.getObject("eleMyQueuesTitle"), "", "eleMyQueuesTitle");
						blnSearchString = SCRCommon.ActionOnTable(Common.o.getObject("eleMyQueuesTbl"), 13, 9, 11, 9, strSplitValue[0], strSplitValue[1], strCreator, strSplitValue[1], "a");
						if (blnSearchString) {
							HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search object in the table cell and click on object. Object name is '" + strSplitValue[0] + "'", "System searched object in the table and clicked on object. object name is '" + strSplitValue[0] + "'", "PASS");
							break Outer;
						}
					}
				} else {
					logger.info("" + sReadString + " does not exist in My Queues Table");
				}
			}
			if (!blnSearchString) {
				logger.info("" + sReadString + " does not exist in My Queues Table");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "" + sReadString + " should available in the my Queues Table", "" + sReadString + " is not available in the my Queues Table", "FAIL");
			} else {
				blnStatus = true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "" + sReadString + " should available in the my Queues Table", "" + sReadString + " is not available in the my Queues Table", 

"FAIL");
			e.printStackTrace();
		}
		return blnStatus;
	}
}