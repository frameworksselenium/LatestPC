/**
 * @ClassPurpose This Class used for the CPIDAssignment Screen
 * @Scriptor Sojan David
 * @ReviewedBy
 * @ModifiedBy Sojan David
 * @LastDateModified 3/31/2017
 */
package com.pc.screen;

import org.apache.log4j.Logger;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.PCThreadCache;

public class CPIDAssignment {

	public static String sheetname = "CPIDAssignment";
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();

	/**
	 * @function Use to perform all the objects/elements/functions from the
	 *           CPIDAssignment excel sheet
	 * @return true/false
	 * @throws Exception
	 */
	public Boolean SCRCPIDAssignment() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

	/**
	 * @function use to select the cpid activity table
	 * @param sFuncValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean SelectAssignment(String sFuncValue) throws Exception {
		boolean status = false;
		status = common.ActionOnTable(Common.o.getObject("eleCPIDParticipantAssignmentTable"), 1, 0, sFuncValue, "", "div");
		return status;
	}

	/**
	 * @function use to fill the table fo the CPIDparticipant assignment table
	 * @param sFuncValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean CPIDParticipantAssignmentTable(String sFuncValue) throws Exception {
		boolean status = false;
		String sValue[] = sFuncValue.split(":::");
		int sRowCount = SCRCommon.TableRowCount(Common.o.getObject("eleCPIDParticipantAssignmentTable"));
		try {
			switch (sValue[0].toUpperCase()) {
			case "NEW":
				status = SCRCommon.DataWebTable(Common.o.getObject("eleCPIDParticipantAssignmentTable"), sRowCount, 1, "", 

"single", "div");
				// status = common.SafeAction(Common.o.getObject("lstCPID"), sValue[1], "lst");
				common.SafeAction(Common.o.getObject("eleSelectCPID"), "YES", "ele");
				common.SafeAction(Common.o.getObject("edtCPID"), sValue[1], "edt");
				common.SafeAction(Common.o.getObject("eleTerritory_SearchBtn"), "YES", "ele");
				common.SafeAction(Common.o.getObject("eleCPIDChoose"), "YES", "ele");
				status = SCRCommon.DataWebTable(Common.o.getObject("eleCPIDParticipantAssignmentTable"), sRowCount, 2, "", 

"single", "div");
				status = common.SafeAction(Common.o.getObject("lstRO"), sValue[2], "lst");
				// status =
				// SCRCommon.DataWebTable(Common.o.getObject("eleCPIDParticipantAssignmentTable"),
				// sRowCount, 3, "", "single");
				status = common.SafeAction(Common.o.getObject("eleAAAssignedUserIcon"), "ele", "ele");
				status = common.SafeAction(Common.o.getObject("edtAAUserName"), sValue[3], "edt");
				status = common.SafeAction(Common.o.getObject("eleAAProducerCodeSearchButton"), "eleAAProducerCodeSearchButton", 

"ele");
				status = common.SafeAction(Common.o.getObject("eleAAProducerCodeSelect"), "eleAAProducerCodeSelect", "ele");
				status = SCRCommon.DataWebTable(Common.o.getObject("eleCPIDParticipantAssignmentTable"), sRowCount, 4, "", 

"single", "div");
				status = common.SafeAction(Common.o.getObject("lstCPIDAssigneeRole"), sValue[4], "lst");
				break;
			case "UPDATE":
				status = SCRCommon.DataWebTable(Common.o.getObject("eleCPIDParticipantAssignmentTable"), sRowCount, 1, "", 

"single", "div");
				// status = common.SafeAction(Common.o.getObject("lstCPID"), sValue[1], "lst");
				common.SafeAction(Common.o.getObject("eleSelectCPID"), "YES", "ele");
				common.SafeAction(Common.o.getObject("edtCPID"), sValue[1], "edt");
				common.SafeAction(Common.o.getObject("eleTerritory_SearchBtn"), "YES", "ele");
				common.SafeAction(Common.o.getObject("eleCPIDChoose"), "YES", "ele");
				status = SCRCommon.DataWebTable(Common.o.getObject("eleCPIDParticipantAssignmentTable"), sRowCount, 2, "", 

"single", "div");
				status = common.SafeAction(Common.o.getObject("lstRO"), sValue[2], "lst");
				// status =
				// SCRCommon.DataWebTable(Common.o.getObject("eleCPIDParticipantAssignmentTable"),
				// sRowCount, 3, "", "single");
				status = SCRCommon.DataWebTable(Common.o.getObject("eleCPIDParticipantAssignmentTable"), sRowCount, 4, "", 

"single", "div");
				status = common.SafeAction(Common.o.getObject("lstCPIDAssigneeRole"), sValue[4], "lst");
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

	/**
	 * @function Use to verify the function results
	 * @param sFuncValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean VerifyResults(String sFuncValue) throws Exception {
		boolean status = false;
		String[] sValue = sFuncValue.split(":::");
		logger.info("Verifying the Results");
		String Value = null;
		switch (sValue[0].toUpperCase()) {
		case "VERIFYCPIDCREATEDASSIGNMENT":
			logger.info(sValue[0]);
			SCRCommon.PageVerify("CPIDParticipantAssignmentTable");
			Value = common.GetTextFromTable(Common.o.getObject("eleCPIDParticipantAssignmentTable"), 1, sValue[1]);
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			Value = common.GetTextFromTable(Common.o.getObject("eleCPIDParticipantAssignmentTable"), 2, sValue[2]);
			status = common.CompareStringResult(sValue[0], sValue[2], Value);
			Value = common.GetTextFromTable(Common.o.getObject("eleCPIDParticipantAssignmentTable"), 3, sValue[3]);
			status = common.CompareStringResult(sValue[0], sValue[3], Value);
			Value = common.GetTextFromTable(Common.o.getObject("eleCPIDParticipantAssignmentTable"), 4, sValue[4]);
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
	 * @function use to delete the CPID administration pre request
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
		case "CPIDADMINISTRATION":
			elementSize = common.ElementSize(Common.o.getObject("eleZeroResultMsg"));
			if (elementSize == 0) {
				logger.info("CPID Administration User is available hence deleteing it");
				status = common.SafeAction(Common.o.getObject("eleSBACEdit"), "", "eleSBACEdit");
				value = common.GetTextFromTable(Common.o.getObject("eleCPIDParticipantAssignmentTable"), 3, sValue[1]);
				if (value.equalsIgnoreCase(sValue[1])) {
					status = common.ActionOnTable(Common.o.getObject("eleCPIDParticipantAssignmentTable"), 3, 0, sValue[1], 

"", "div");
					status = common.SafeAction(Common.o.getObject("eleSBACDelete"), "", "eleSBACDelete");
					status = common.SafeAction(Common.o.getObject("eleAlertPopUp"), "", "eleAlertPopUp");
				}
			} else {
				status = true;
				logger.info("CPID Administration PreUser is not available");
			}
			break;
		}

		return status;
	}

}