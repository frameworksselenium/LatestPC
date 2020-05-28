
/**
 * @ClassPurpose Access all the data's from AssignActivity Screen
 * @Scriptor Rathish Kuppusamy
 * @ReviewedBy
 * @ModifiedBy Rathish Kuppusamy
 * @LastDateModified 3/27/2017
 */
package com.pc.screen;

import org.apache.log4j.Logger;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.PCThreadCache;

public class NameAddressChange {

	public static String sheetname = "NameAddressChange";
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();

	/**
	 * @function Use to perform all the objects/elements/functions from the
	 *           AssignActivity excel sheet
	 * @return true/false
	 * @throws Exception
	 */
	public Boolean SCRNameAddressChange() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			return false;
		}
		return status;
	}

	/**
	 * @function This function is used to set the current date
	 * @return true/false
	 * @throws Exception
	 */
	public boolean DefaultEffectiveDate() throws Exception {
		boolean status = false;
		String sCurrentDate = SCRCommon.ReturnCurrentDate();
		status = common.SafeAction(Common.o.getObject("edtChangeEffDate"), sCurrentDate, "edt");
		return status;
	}

	/**
	 * @function This function is used to select policy from table in Name Address
	 *           Change screen
	 * @return true/false
	 * @throws Exception
	 */
	public boolean SelectPolicyTable(String sReadString) throws Exception {
		boolean status = false;
		status = common.ActionOnTable(Common.o.getObject("elePolicyTable"), 3, 0, sReadString, "", "div");
		return status;
	}

	/**
	 * @function Use to verify the case result
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

		case "VERIFYSUBMITUNSELECTABLE":
			logger.info(sValue[0]);
			status = common.ElementExistOrNotTrue("VerifySubmitUnselectable", "Submit button should not be selectable", "Submit button should not be selectable", (Common.o.getObject("eleNACSubmit")));
			break;
		case "VERIFYPOLICYNUMFROMTABLE":
			Value = common.GetTextFromTable(Common.o.getObject("eleSummaryOfChangestbl"), 4, 3, sValue[1]);
			String sPolicyNumber[] = Value.split(",");
			status = common.CompareStringResult(sValue[0], sPolicyNumber[0], sValue[2]);
			status = common.CompareStringResult(sValue[0], sPolicyNumber[1], sValue[3]);
			break;
		case "VERIFYCHARACTERS":
			logger.info(sValue[0]);
			Value = common.ReadElementGetAttribute(Common.o.getObject("edtPolicyInfoName2"), "value", Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("VerifyCharacters", sValue[1], Value);
			break;
		}
		if (!status) {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "" + sValue[0] + " case should PASS", "" + sValue[0] + " case should not fail", "FAIL");
			status = true;
		}
		return status;
	}
}