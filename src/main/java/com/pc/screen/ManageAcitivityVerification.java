
package com.pc.screen;

import org.apache.log4j.Logger;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.PCThreadCache;

public class ManageAcitivityVerification {

	public static String sheetname = "ManageAcitivityVerification";
	Common common = CommonManager.getInstance().getCommon();
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));

	public Boolean SCRManageAcitivityVerification() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

	/**
	 * @function use to verify the results
	 * @param funValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean VerifyActivityResults(String funValue) throws Exception {
		boolean status = false;
		String[] sValue = funValue.split(":::");
		String wait = HTML.properties.getProperty("SHORTWAIT");
		Integer x = Integer.valueOf(wait);
		switch (sValue[0].toUpperCase()) {
		case "VERIFYACTIVITYCLOSEDUINCLOSEDSTATUS":
			logger.info("Validating the '" + sValue[0] + "'");
			status = common.ElementExistOrNotTrue("" + sValue[0] + "", "Activity should present", "Activity is present", Common.o.getObject("eleActivityFollowUpList"));
			break;

		case "VERIFYVIEWNOTESFORTHEACTIVITY":
			logger.info("Validating the '" + sValue[0] + "'");
			String sExpectedValues = sValue[1];
			String sAcutualViewNotes = common.ReadElement(Common.o.getObject("txtViewNotesMessage"), x);
			status = common.CompareStringResult("" + sValue[0] + "", sExpectedValues, sAcutualViewNotes);
			break;

		case "VERIFYVIEWNOTEDOESNOTDISPLAYANYWARNING":
			logger.info("Validating the '" + sValue[0] + "'");
			status = common.ElementExistOrNotTrue("" + sValue[0] + "", "Account File Screen for view notes warning message should not present", "Account File Screen for view notes warning message is not present", Common.o.getObject("eleAccountFileNotesScreen"));
			break;

		case "VERIFYDUEDATEORESCALATIONDATEWARNINGMSG":
			logger.info("Validating the '" + sValue[0] + "'");
			String sExpectedValues1 = sValue[1];
			String sAcutualViewNotes1 = common.ReadElement(Common.o.getObject("txtViewNoteWarningMsg"), x);
			status = common.CompareStringResult("" + sValue[0] + "", sExpectedValues1, sAcutualViewNotes1);
			break;

		case "VERIFYSTATUS":
			logger.info("Validating the '" + sValue[0] + "'");
			String sExpectedValues2 = sValue[1];
			String sAcutualViewNotes2 = common.ReadElement(Common.o.getObject("txtActivityStatus"), x);
			status = common.CompareStringResult("" + sValue[0] + "", sExpectedValues2, sAcutualViewNotes2);
			break;

		case "VERIFYACTIVITYSTATUS":
			logger.info("Validating the '" + sValue[0] + "'");
			String sExpectedValues3 = sValue[1];
			// String sAcutualViewNotes3 =
			// driver.findElement(Common.o.getObject("eleActivityStatus")).getAttribute("value");
			String sAcutualViewNotes3 = common.ReadElement(Common.o.getObject("eleReplaceActivityCheck"), 30);
			status = common.CompareStringResult("" + sValue[0] + "", sExpectedValues3, sAcutualViewNotes3);
			break;
		}
		if (status == false) {
			status = true;
		}
		return status;
	}

	/**
	 * @function Use to select the created activity
	 * @param sFuncValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean selectCreatedActivity(String sFuncValue) throws Exception {
		boolean status = false;
		// status = common.ActionOnTable(Common.o.getObject("eleActivityTable"), 2, 2,
		// sFuncValue, "div");
		status = common.ActionOnTable(Common.o.getObject("eleActivityTable"), 2, 2, sFuncValue, sFuncValue, "a");
		return status;
	}
}