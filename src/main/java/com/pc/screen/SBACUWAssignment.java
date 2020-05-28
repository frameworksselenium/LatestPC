
package com.pc.screen;

import org.apache.log4j.Logger;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.PCThreadCache;

public class SBACUWAssignment {

	public static String sheetname = "SBACUWAssignment";
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();

	public Boolean SCRSBACUWAssignment() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			return status;
		}
		return status;
	}

	public boolean SelectAssignment(String sFuncValue) throws Exception {
		boolean status = false;
		status = common.ActionOnTable(Common.o.getObject("eleSBACUWAssignmentTable"), 3, 0, sFuncValue, "", "div");
		return status;
	}

	public boolean SBACUWAssignmentTable(String sFuncValue) throws Exception {
		boolean status = false;
		String sValue[] = sFuncValue.split(":::");
		int sRowCount = SCRCommon.TableRowCount(Common.o.getObject("eleSBACUWAssignmentTable"));
		try {
			switch (sValue[0].toUpperCase()) {
			case "NEW":
				status = SCRCommon.DataWebTable(Common.o.getObject("eleSBACUWAssignmentTable"), sRowCount, 1, "", "single", 

"div");
				status = common.SafeAction(Common.o.getObject("edtSBACUWAgentMail"), sValue[1], "edt");
				status = SCRCommon.DataWebTable(Common.o.getObject("eleSBACUWAssignmentTable"), sRowCount, 3, "", "single", 

"div");
				status = common.SafeAction(Common.o.getObject("edtSBACUWAgentAgentFirstName"), sValue[2], "edt");
				// status =
				// SCRCommon.DataWebTable(Common.o.getObject("eleSBACUWAssignmentTable"),
				// sRowCount, 4, "", "single", "div");
				status = common.SafeAction(Common.o.getObject("edtSBACUWAgentAgentLastName"), sValue[3], "edt");
				// status =
				// SCRCommon.DataWebTable(Common.o.getObject("eleSBACUWAssignmentTable"),
				// sRowCount, 5, "", "single", "div");
				status = common.SafeAction(Common.o.getObject("eleAAAssignedUserIcon"), "ele", "ele");
				status = common.SafeAction(Common.o.getObject("edtAAUserName"), sValue[4], "edt");
				status = common.SafeAction(Common.o.getObject("eleAAProducerCodeSearchButton"), "eleAAProducerCodeSearchButton", 

"ele");
				status = common.SafeAction(Common.o.getObject("eleAAProducerCodeSelect"), "eleAAProducerCodeSelect", "ele");
				break;
			case "UPDATE":
				status = SCRCommon.DataWebTable(Common.o.getObject("eleSBACUWAssignmentTable"), sRowCount, 1, "", "single", 

"div");
				status = common.SafeAction(Common.o.getObject("edtSBACUWAgentMail"), sValue[1], "edt");
				status = SCRCommon.DataWebTable(Common.o.getObject("eleSBACUWAssignmentTable"), sRowCount, 3, "", "single", 

"div");
				status = common.SafeAction(Common.o.getObject("edtSBACUWAgentAgentFirstName"), sValue[2], "edt");
				// status =
				// SCRCommon.DataWebTable(Common.o.getObject("eleSBACUWAssignmentTable"),
				// sRowCount, 4, "", "single", "div");
				status = common.SafeAction(Common.o.getObject("edtSBACUWAgentAgentLastName"), sValue[3], "edt");
				break;
			}
		} catch (Throwable e) {
			e.printStackTrace();
			if (status == false) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should complete performing the add class", "System not completed performing the add class", "FAIL");
			}
		}
		return status;
	}

	public boolean VerifyResults(String sFuncValue) throws Exception {
		boolean status = false;
		String[] sValue = sFuncValue.split(":::");
		logger.info("Verifying the Results");
		String Value = null;
		switch (sValue[0].toUpperCase()) {
		case "VERIFYSBACUWCREATEDASSIGNMENT":
			logger.info(sValue[0]);
			Value = common.GetTextFromTable(Common.o.getObject("eleSBACUWAssignmentTable"), 1, sValue[1]);
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			Value = common.GetTextFromTable(Common.o.getObject("eleSBACUWAssignmentTable"), 3, sValue[2]);
			status = common.CompareStringResult(sValue[0], sValue[2], Value);
			Value = common.GetTextFromTable(Common.o.getObject("eleSBACUWAssignmentTable"), 4, sValue[3]);
			status = common.CompareStringResult(sValue[0], sValue[3], Value);
			Value = common.GetTextFromTable(Common.o.getObject("eleSBACUWAssignmentTable"), 5, sValue[4]);
			status = common.CompareStringResult(sValue[0], sValue[4], Value);
			break;
		case "VERIFYASSIGNEDUSER":
			logger.info(sValue[0]);
			Value = common.GetTextFromTable(Common.o.getObject("eleAASelectUser"), 1, sValue[1]);
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
	 * @function use to delete the SBAC administration prerequest
	 * @param sFuncValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean preRequestCheck(String sFuncValue) throws Exception {
		boolean status = false;
		String[] sValue = sFuncValue.split(":::");
		String value = null;
		int elementSize = 0;
		switch (sValue[0].toUpperCase()) {
		case "SBACADMINISTRATION":
			elementSize = common.ElementSize(Common.o.getObject("eleZeroResultMsg"));
			if (elementSize == 0) {
				logger.info("SBAC Administration User is available hence deleteing it");
				//status = common.SafeAction(Common.o.getObject("eleSBACEdit"), "", "eleSBACEdit");
				value = common.GetTextFromTable(Common.o.getObject("eleSBACUWAssignmentTable"), 5, sValue[1]);
				if (value.equalsIgnoreCase(sValue[1])) {
					status = common.ActionOnTable(Common.o.getObject("eleSBACUWAssignmentTable"), 5, 0, sValue[1], "", 

"div");
					status = common.SafeAction(Common.o.getObject("eleSBACDelete"), "", "eleSBACDelete");
					status = common.SafeAction(Common.o.getObject("eleAlertPopUp"), "", "eleAlertPopUp");
				}
			} else {
				status = true;
				logger.info("SBAC Administration PreUser is not available");
			}
			break;
		}

		return status;
	}
}