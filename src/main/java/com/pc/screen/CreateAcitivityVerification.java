package com.pc.screen;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.PCThreadCache;

public class CreateAcitivityVerification {

	public static String sheetname = "CreateAcitivityVerification";
	Common common = CommonManager.getInstance().getCommon();
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();
	//static Logger logger = Logger.getLogger(sheetname);
	//private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));

	public Boolean SCRCreateAcitivityVerification() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

	public boolean VerifyActivityResults(String funValue) throws Exception {
		boolean status = false;
		String[] sValue = funValue.split(":::");
		String wait = HTML.properties.getProperty("SHORTWAIT");
		Integer x = Integer.valueOf(wait);
		String sActualValue = null;
		switch (sValue[0].toUpperCase()) {
		case "VERIFYACTIVITYSTATUS":
			logger.info("Validating the '" + sValue[0] + "'");
			sActualValue = driver.findElement(Common.o.getObject("txtStatusCheck")).getAttribute("value");
			status = common.CompareStringResult(sValue[0], sValue[1], sActualValue);
			break;

		case "VERIFYNOTESWARNING":
			logger.info("Validating the '" + sValue[0] + "'");
			String sExpectedViewNotesWarning = sValue[1];
			String sAcutualViewNotesWarning = common.ReadElement(Common.o.getObject("txtViewNoteWarningMsg"), x);
			status = common.CompareStringResult("" + sValue[0] + "", sExpectedViewNotesWarning, sAcutualViewNotesWarning);
			break;

		/**
		 * case "VERIFYVIEWNOTESFORTHEACTIVITY": logger.info("Validating the
		 * '"+sValue[0]+"'"); String sExpectedValues = sValue[1]; String
		 * sAcutualViewNotes = Common.ReadElement(o.getObject("txtViewNotesMessage"),
		 * x); status = Common.TestStepResults(""+sValue[0]+"", sExpectedValues,
		 * sAcutualViewNotes); break;
		 * 
		 * case "VERIFYVIEWNOTEDOESNOTDISPLAYANYWARNING": logger.info("Validating the
		 * '"+sValue[0]+"'"); status = Common.ElementExistOrNotTrue(""+sValue[0]+"",
		 * "Account File Screen for view notes warning message should not present",
		 * "Account File Screen for view notes warning message is not present",
		 * o.getObject("eleAccountFileNotesScreen")); break;
		 * 
		 * case "VERIFYDUEDATEORESCALATIONDATEWARNINGMSG": logger.info("Validating the
		 * '"+sValue[0]+"'"); String sExpectedValues1 = sValue[1]; String
		 * sAcutualViewNotes1 = Common.ReadElement(o.getObject("txtViewNoteWarningMsg"),
		 * x); status = Common.TestStepResults(""+sValue[0]+"", sExpectedValues1,
		 * sAcutualViewNotes1); break;
		 * 
		 * case "VERIFYSTATUS1": logger.info("Validating the '"+sValue[0]+"'"); String
		 * sExpectedValues2 = sValue[1]; String sAcutualViewNotes2 =
		 * Common.ReadElement(o.getObject("txtActivityStatus"), x); status =
		 * Common.TestStepResults(""+sValue[0]+"", sExpectedValues2,
		 * sAcutualViewNotes2); break;
		 * 
		 * case "VERIFYACTIVITYSTATUS": logger.info("Validating the '"+sValue[0]+"'");
		 * String sExpectedValues3 = sValue[1]; String sAcutualViewNotes3 =
		 * driver.findElement(o.getObject("eleReplaceActivityCheck")).getAttribute("value");
		 * status = Common.TestStepResults(""+sValue[0]+"", sExpectedValues3,
		 * sAcutualViewNotes3); break;
		 **/
		}
		/*
		 * if(status == false) { status = true; }
		 */
		return status;
	}
}