
/**
 * @ClassPurpose Access all the data's for driver inforamtion sheet
 * @Scriptor Sojan David
 * @ReviewedBy
 * @ModifiedBy Sojan David
 * @LastDateModified 3/21/2017
 */
package com.pc.screen;

import org.apache.log4j.Logger;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.PCThreadCache;

public class SearchActivites {

	public static String sheetname = "SearchActivites";
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();

	public Boolean SCRSearchActivites() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

	/**
	 * @function Verify the results for all the conditions
	 * @param funValue
	 * @return status
	 * @throws Exception
	 **/
	public boolean VerifySearchResults(String funValue) throws Exception {
		boolean status = false;
		String[] sValue = funValue.split(":::");
		String Value = null;
		switch (sValue[0].toUpperCase()) {
		case "PRODUCERCODERESULTS":
			logger.info("Validating the '" + sValue[0] + "'");
			// Need to write the code since it is not showing the results
			Value = common.GetTextFromTable(Common.o.getObject("SearchTable"), 9, sValue[1]);
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			break;

		case "OOTBTCON15":
			logger.info("Validating the '" + sValue[0] + "'");
			Value = common.ReadElement(Common.o.getObject("txtOOTBTCON15"), Integer.valueOf(HTML.properties.getProperty

("NORMALWAIT")));
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			break;

		case "ASSIGNTOACTIVITYRESULTS":
			logger.info("Validating the '" + sValue[0] + "'");
			Value = common.GetTextFromTable(Common.o.getObject("SearchTable"), 9, sValue[1]);
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			break;

		case "REVIEWACTIVITY":
			logger.info("Validating the '" + sValue[0] + "'");
			status = common.ElementExistOrNotTrue(sValue[0], "The Activity should Exist", "The Activity Exist", Common.o.getObject

("eleActivityFollowUpList"));
			break;

		case "ASSIGNACTIVITYSTATUS":
			logger.info("Validating the '" + sValue[0] + "'");
			// status = common.ElementExistOrNotFalse(sValue[0], "The Activity should be
			// cleared in the results", "The Activity results is cleared",
			// Common.o.getObject("SearchTable"));
			Value = common.GetTextFromTable(Common.o.getObject("SearchTable"), Integer.valueOf(sValue[2]), sValue[1]);
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			break;

		case "ACTIVITYCLOSE":
			logger.info("Validating the '" + sValue[0] + "'");
			Value = common.GetTextFromTable(Common.o.getObject("SearchTable"), 6, sValue[1]);
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			break;

		case "OOTBMESSSAGE":
			logger.info("Validating the '" + sValue[0] + "'");
			status = common.ElementExistOrNotTrue(sValue[0], "'The search returned zero results' should exist", "'The search returned zero results' exist", Common.o.getObject("eleZeroResultMsg"));
			break;

		case "OOTBWARNINGMESSAGE":
			logger.info("Validating the '" + sValue[0] + "'");
			Value = common.ReadElement(Common.o.getObject("eleSARtoSAMsg"), Integer.valueOf(HTML.properties.getProperty

("NORMALWAIT")));
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			break;

		case "ASSIGNTOQUEUEFIELD":
			logger.info("Validating the '" + sValue[0] + "'");
			Value = common.GetTextFromTable(Common.o.getObject("SearchTable"), 9, sValue[1]);
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			Value = common.GetTextFromTable(Common.o.getObject("SearchTable"), 17, sValue[2]);
			status = common.CompareStringResult(sValue[0], sValue[2], Value);
			break;

		case "ASSIGNTOFIELD":
			logger.info("Validating the '" + sValue[0] + "'");
			Value = common.GetTextFromTable(Common.o.getObject("SearchTable"), 17, sValue[1]);
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			break;

		case "DISABLEASSIGNTOQUEUEFIELD":
			logger.info("Validating the '" + sValue[0] + "'");
			status = common.ElementExistOrNotTrue(sValue[0], "Assign to queue filed should diabled", "Assign to queue filed diabled", 

Common.o.getObject("eleDisableAssigntoQueue"));
			break;

		case "DISABLEASSIGNTOUSERFIELD":
			logger.info("Validating the '" + sValue[0] + "'");
			status = common.ElementExistOrNotTrue(sValue[0], "Assign to user filed should diabled", "Assign to user filed diabled", 

Common.o.getObject("eleDisableAssigntoUser"));
			break;

		case "FULLWILDCARDSEARCH":
			logger.info("Validating the '" + sValue[0] + "'");
			Value = common.GetTextFromTable(Common.o.getObject("eleSearchQueueSearchResultsTable"), 1, sValue[1]);
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			Value = common.GetTextFromTable(Common.o.getObject("eleSearchQueueSearchResultsTable"), 1, sValue[2]);
			status = common.CompareStringResult(sValue[0], sValue[2], Value);
			break;

		case "ASSIGNTOQUEUEWARNINGMSG":
			logger.info("Validating the '" + sValue[0] + "'");
			Value = common.ReadElement(Common.o.getObject("txtAssigntoQueueOOTB"), Integer.valueOf(HTML.properties.getProperty

("NORMALWAIT")));
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
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
	 * @function use to select the activity search table
	 * @return true/false
	 * @throws Exception
	 */
	public boolean SelectResultsToAssign() throws Exception {
		boolean status = false;
		status = SCRCommon.DataWebTable(Common.o.getObject("SearchTable"), 1, 1, "", "single", "div");
		return status;
	}
}