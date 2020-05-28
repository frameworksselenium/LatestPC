

/**
 * @ClassPurpose This Class used for the QPBPolicySelection usecase
 * @Scriptor Sojan David
 * @ReviewedBy
 * @ModifiedBy Sojan David
 * @LastDateModified 3/27/2017
 */
package com.pc.screen;

import org.apache.log4j.Logger;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.PCThreadCache;

public class QPBDocumentOptions {

	public static String sheetname = "QPBDocumentOptions";
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();

	/**
	 * @function Use to perform all the objects/elements/functions from the
	 *           QPBDocumentOption excel sheet
	 * @return true/false
	 * @throws Exception
	 */
	public Boolean SCRQPBDocumentOptions() throws Exception {

		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

	/**
	 * @function use to verify the results of the test case
	 * @param sFuncValue
	 * @return true/false
	 * @throws Exception
	 * @throws Exception
	 */
	public boolean verifyResults(String sFuncValue) throws Exception, Exception {
		boolean status = false;
		String value = null;
		String[] sValue = sFuncValue.split(":::");
		logger.info("Verifying the results");
		switch (sValue[0].toUpperCase()) {
		case "QPBSAVECHANGES":
			logger.info(sValue[0]);
			// Value =
			// Common.dr().findElement(Common.o.getObject("lstQPBDODeliveryType")).getAttribute("value");
			value = common.ReadElementGetAttribute(Common.o.getObject("lstQPBDODeliveryType"), "value", Integer.valueOf

(HTML.properties.getProperty("LONGWAIT")));
			status = common.CompareStringResult("CheckingSaveChanges", sValue[1], value);
			break;
		case "QPBSCPROPOSALRESULTS":
			logger.info(sValue[0]);
			common.WaitUntilClickable(Common.o.getObject("eleQPBDOFinalize"), Integer.valueOf(HTML.properties.getProperty

("LONGWAIT")));
			value = common.ReadElement(Common.o.getObject("txtQPBDOAgentorInsured"), Integer.valueOf(HTML.properties.getProperty

("SHORTWAIT")));
			status = common.CompareStringResult("AgentorInsured", sValue[1], value);
			value = common.ReadElement(Common.o.getObject("lstQPBDODeliveryType"), Integer.valueOf(HTML.properties.getProperty

("SHORTWAIT")));
			status = common.CompareStringResult("DeliveryType", sValue[2], value);
			value = common.ReadElement(Common.o.getObject("lstQPBDOName"), Integer.valueOf(HTML.properties.getProperty

("SHORTWAIT")));
			status = common.CompareStringResult("ProducerCodeName", sValue[3], value);
			value = common.ReadElement(Common.o.getObject("txtQPBDOEmail"), Integer.valueOf(HTML.properties.getProperty

("SHORTWAIT")));
			status = common.CompareStringResult("Email", sValue[4], value);
			break;
		case "SCFINALIZE":
			logger.info(sValue[0]);
			value = common.GetTextFromTable(Common.o.getObject("eleDocumentFinalizeTable"), 2, 5, sValue[1], sValue[2]);
			status = common.CompareStringResult(sValue[0], sValue[2], value);
			status = common.ElementExistOrNotTrue(sValue[1], "There should be PDF document generated", "PDF genreated", 

Common.o.getObject("eleQPBPDFFinalize"));
			break;
		case "LEAVETHEFLOW":
			logger.info(sValue[0]);
			status = common.ElementExistOrNotTrue(sValue[0], "QPB should not leave the flow", "QPB not leave the flow", 

Common.o.getObject("eleCommonFinalize"));
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
	 * @function use to select the saved document for QPBSC in document screen
	 * @throws Exception
	 * @return true/false
	 */
	public boolean selectSaveDocument(String sFuncValue) throws Exception {
		boolean status = false;
		String sValue[] = sFuncValue.split(":::");
		// status = common.ActionOnTable(Common.o.getObject("eleDocumentFinalizeTable"),
		// 2, 2, sFuncValue, "a");
		status = common.ActionOnTable(Common.o.getObject("eleDocumentFinalizeTable"), 5, 2, sValue[1], sValue[0], "a");
		return status;
	}
}