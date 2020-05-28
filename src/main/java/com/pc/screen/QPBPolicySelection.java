
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

public class QPBPolicySelection {

	public static String sheetname = "QPBPolicySelection";
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();

	/**
	 * @function Use to perform all the objects/elements/functions from the
	 *           QPBPolicySelection excel sheet
	 * @return true/false
	 * @throws Exception
	 */
	public Boolean SCRQPBPolicySelection() throws Exception {

		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

	/**
	 * @function use to verify the results of the test case
	 * @param funValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean VerifyResults(String funValue) throws Exception {
		boolean status = false;
		String[] sValue = funValue.split(":::");
		logger.info("Verifying the Results");
		String Value = null;
		switch (sValue[0].toUpperCase()) {

		case "VERIFYPOLICYSELECTION":
			logger.info(sValue[0]);
			Value = common.ReadElement(Common.o.getObject("eleAvailablePolciesLabel0"), Integer.valueOf(HTML.properties.getProperty

("NORMALWAIT")));
			status = common.CompareStringResult("CheckProduct", sValue[2], Value);
			Value = common.ReadElement(Common.o.getObject("eleQPBPolicy0"), Integer.valueOf(HTML.properties.getProperty

("NORMALWAIT")));
			status = common.CompareStringResult("CheckPolicyNumber", sValue[4], Value);
			Value = common.ReadElement(Common.o.getObject("eleAvailablePolciesLabel1"), Integer.valueOf(HTML.properties.getProperty

("NORMALWAIT")));
			status = common.CompareStringResult("CheckProduct", sValue[1], Value);
			Value = common.ReadElement(Common.o.getObject("eleQPBPolicy1"), Integer.valueOf(HTML.properties.getProperty

("NORMALWAIT")));
			status = common.CompareStringResult("CheckPolicyNumber", sValue[3], Value);
			break;
		case "VERIFYPOLICYNUMFROMTABLE":
			logger.info(sValue[0]);
			break;
		case "VERIFYCHARACTERS":
			logger.info(sValue[0]);

			break;
		}
		if (!status) {
			logger.info("" + sValue[0] + " FAILED");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "" + sValue[0] + " case should PASS", "" + sValue[0] + " case should not fail", "FAIL");
			status = true;
		}
		return status;
	}

}
