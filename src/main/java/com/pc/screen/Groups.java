package com.pc.screen;

import java.util.Random;

import org.apache.log4j.Logger;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.PCThreadCache;

public class Groups {
	public static String sheetname = "Groups";
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();

	/**
	 * @function Use to perform all the objects/elements/functions from the Groups
	 *           excel sheet
	 * @return true/false
	 * @throws Exception
	 */
	public Boolean SCRGroups() throws Exception {

		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

	/**
	 * @function Use to select the check box
	 * @param sFuncValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean selectManagerCheckBox(String sFuncValue) throws Exception {
		boolean status = false;
		/*
		 * int sRowCount = SCRCommon.TableRowCount(Common.o.getObject("")); String[]
		 * sValue = sFuncValue.split(":::"); status =
		 * SCRCommon.DataWebTable(Common.o.getObject("eleGroupUsertbl"), sRowCount, 4,
		 * "","single","input"); status
		 * =common.SafeAction(Common.o.getObject("edtLoadFactor"), sValue[0], "edt");
		 */
		status = common.ActionOnTable(Common.o.getObject("eleGroupUsertbl"), 4, 3, sFuncValue, "div");
		return status;
	}

	public boolean addLoadFactorPermissions(String sFuncValue) {
		boolean status = false;

		int sRowCount = SCRCommon.TableRowCount(Common.o.getObject("eleGroupUsertbl"));
		try {
			status = SCRCommon.DataWebTable(Common.o.getObject("eleGroupUsertbl"), sRowCount, 4, "", "single", "div");
			status = common.SafeAction(Common.o.getObject("edtLoadFactor"), sFuncValue, "edt");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	public boolean addQueues(String sFunValue) {

		boolean status = false;
		String[] sValue = sFunValue.split(":::");
		int sRowCount = SCRCommon.TableRowCount(Common.o.getObject("eleGroupQueuestbl"));
		try {
			status = SCRCommon.DataWebTable(Common.o.getObject("eleGroupQueuestbl"), sRowCount, 1, "", "single", "div");
			status = common.SafeAction(Common.o.getObject("edtGroupQueueName"), sValue[0], "edt");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * @function used to set the value in Group name
	 * @param strValue
	 * @return
	 * @throws Exception
	 */
	public boolean setGroupName(String strValue) throws Exception {
		boolean status = false;
		String val;
		Random r = new Random();
		int numbers = 100000 + (int) (r.nextFloat() * 899900);
		val = strValue + String.valueOf(numbers);
		status = common.SafeAction(Common.o.getObject("edtGroupName"), val, "edt");

		return status;
	}

}