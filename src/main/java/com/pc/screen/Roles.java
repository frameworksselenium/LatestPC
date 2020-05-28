package com.pc.screen;

import org.apache.log4j.Logger;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.PCThreadCache;

public class Roles {
	public static String sheetname = "Roles";
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();

	/**
	 * @function Use to perform all the objects/elements/functions from the Roles
	 *           excel sheet
	 * @return true/false
	 * @throws Exception
	 */
	public Boolean SCRRoles() throws Exception {

		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

	/**
	 * @function Use to perform open the selected Role From RolesTable
	 * @return true/false
	 * @throws Exception
	 */
	public boolean openRoleFromRolesTable(String sReadString) throws Exception {
		boolean blnStatus = false;
		boolean blnSearchString = false;
		String strPageCount = null;
		String[] strReadStrings = sReadString.split(":::");
		blnSearchString = SCRCommon.ActionOnTable(Common.o.getObject("eleMyRolesTb"), 1, 0, strReadStrings[0], "", "img");
		if (blnSearchString) {
			blnStatus = true;
		}
		Outer: if (!blnSearchString) {
			int icheckNextPageButton = common.ElementSize(Common.o.getObject("eleNextPageArrow"));
			if (icheckNextPageButton > 0) {
				strPageCount = common.ReadElement(Common.o.getObject("eleCommonPageNumber"), Integer.valueOf

(HTML.properties.getProperty("LONGWAIT")));
				strPageCount = SCRCommon.getIntFromString(strPageCount);
				for (int i = 0; i < Integer.parseInt(strPageCount); i++) {
					blnStatus = common.SafeAction(Common.o.getObject("eleNextPageArrow"), "", "eleNextPageArrow");
					blnStatus = common.SafeAction(Common.o.getObject("eleVerifyRoleName"), "", "eleVerifyRoleName");
					blnSearchString = SCRCommon.ActionOnTable(Common.o.getObject("eleMyRolesTb"), 1, 0, strReadStrings[0], 

"", "img");
					if (blnSearchString) {
						HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), 

PCThreadCache.getInstance().getProperty("methodName"), "System should search object in the table cell and click on object. Object name is '" + 

strReadStrings[0] + "'", "System searched object in the table and clicked on object. object name is '" + strReadStrings[0] + "'", "PASS");
						break Outer;
					}
				}
			} else {
				logger.info("" + sReadString + " does not exist in Roles Table");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "" + sReadString + " should available in the my Queues Table", "" + sReadString + " is not available in the my Queues Table", "FAIL");
			}
		}
		if (!blnSearchString) {
			logger.info("" + sReadString + " does not exist in Roles Table");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "System should search string in table and Search string is '" + strReadStrings[0] + "'", "System searched string in table and search string '" + strReadStrings[0] + "' is  not present", "FAIL");
		} else {
			blnStatus = true;
		}
		return blnStatus;
	}

}