
/**
 * @ClassPurpose This Class used for the PolicyShellParticipants usecase
 * @Scriptor Krishna
 * @ReviewedBy
 * @ModifiedBy Sojan
 * @LastDateModified 4/12/2017
 */
package com.pc.screen;

import org.apache.log4j.Logger;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.PCThreadCache;

public class PolicyShellParticipants {

	public static String sheetname = "PolicyShellParticipants";
	Common common = CommonManager.getInstance().getCommon();
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));

	/**
	 * @function Use to perform all the objects/elements/functions from the
	 *           PolicyShellParticipants excel sheet
	 * @return true/false
	 * @throws Exception
	 */
	public Boolean SCRPolicyShellParticipants() throws Exception {

		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			return status;
		}
		if (common.WaitUntilClickable(Common.o.getObject("eleParticipentsTitle"), Integer.valueOf(HTML.properties.getProperty

("VERYLONGWAIT")))) {
			logger.info("System displayed Participents Page");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "System should display Participents Page", "System displayed Participents Page", "PASS");
			status = true;
		} else {
			logger.info("System not displayed Participents Page");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "System should display Participents Page", "System not displayed Participents Page", "FAIL");
			status = false;
		}
		return status;
	}

	/**
	 * @function use to add the data in the participant table
	 * @param sFunctionValue
	 * @return true/false
	 * @throws Throwable
	 */
	public boolean AddParticipents(String sFunctionValue) throws Throwable {
		boolean status = false;
		String[] sValue = sFunctionValue.split(":::");
		// int sRowCount =
		// SCRCommon.TableRowCount(Common.o.getObject("funParticipants"));
		try {
			status = SCRCommon.DataWebTable(Common.o.getObject("tblParticipants"), 2, 1, "<none>", "single", "div");
			status = common.SafeAction(Common.o.getObject("lstAssignmentRole"), sValue[0], "lstAssignmentRole");
			status = SCRCommon.DataWebTable(Common.o.getObject("tblParticipants"), 2, 4, "", "single", "div");
			status = common.SafeAction(Common.o.getObject("eleSelectUser"), "eleSelectUser", "ele");
			status = common.SafeAction(Common.o.getObject("edtEnterUserID"), sValue[1], "edt");
			status = common.SafeAction(Common.o.getObject("eleSearchPartUser"), "eleSearchPartUser", "ele");
			status = common.SafeAction(Common.o.getObject("eleSearchPartUserSelect"), "eleSearchPartUserSelect", "ele");
			status = SCRCommon.DataWebTable(Common.o.getObject("tblParticipants"), 2, 3, "<none>", "single", "div");
			status = common.SafeAction(Common.o.getObject("lstAssignedGroup"), sValue[2], "lstAssignedGroup");
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
	 * @function Use to remove the participant according to the input
	 * @param sFunctionValue
	 * @return true/false
	 * @throws Throwable
	 */
	public boolean RemoveParticipents(String sFunctionValue) throws Throwable {
		boolean status = false;

		try {
			status = common.ActionOnTable(Common.o.getObject("tblParticipants"), 1, 0, sFunctionValue, "", "img");
		} catch (Throwable e) {
			e.printStackTrace();
			if (status == false) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should complete performing the add class", "System not completed performing the add class", "FAIL");
			}
		}
		return status;
	}

}