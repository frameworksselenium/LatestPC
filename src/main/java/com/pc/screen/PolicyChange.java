
/**
 * @ClassPurpose This Class used for the Cancel Policy
 * @Scriptor Raj
 * @ReviewedBy
 * @ModifiedBy
 * @LastDateModified 3/23/2017
 */
package com.pc.screen;

import org.apache.log4j.Logger;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.PCThreadCache;

public class PolicyChange {

	public static String sheetname = "PolicyChange";
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();

	/**
	 * @function Use to perform all the action in the PolicyChange sheet
	 * @return true/false
	 * @throws Exception
	 */
	public Boolean SCRPolicyChange() throws Exception {

		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			return false;
		}
		return status;
	}

	/**
	 * @function This function use to fill the current date of policy change screen
	 * @return true/false
	 * @throws Exception
	 */
	public boolean EffectiveDate() throws Exception {
		boolean status = false;
		status = common.SafeAction(Common.o.getObject("lstCPEffectiveDate"), SCRCommon.ReturnCurrentDate(), "lst");
		status = common.SafeAction(Common.o.getObject("lstCPDateReceived"), SCRCommon.ReturnCurrentDate(), "lst");
		return status;
	}

	public boolean DefaultEffectiveDate(String strFuncValue) throws Exception {
		boolean status = false;
		String[] strSplitValue = strFuncValue.split(":::");
		String strDate = null;
		switch (strSplitValue[0].toUpperCase()) {
		case "CURRENTDATE":
			strDate = SCRCommon.ReturnCurrentDate();
			status = common.SafeAction(Common.o.getObject("edtCPEffectiveDate"), strDate, "edt");
			break;
		case "INCREASEDECREASEDATE":
			strDate = SCRCommon.returnDate(Integer.parseInt(strSplitValue[1]));
			status = common.SafeAction(Common.o.getObject("edtCPEffectiveDate"), strDate, "edt");
			break;
		}
		return status;
	}

	public boolean DefaultReceivedDate(String strFuncValue) throws Exception {
		boolean status = false;
		String[] strSplitValue = strFuncValue.split(":::");
		String strDate = null;
		switch (strSplitValue[0].toUpperCase()) {
		case "CURRENTDATE":
			strDate = SCRCommon.ReturnCurrentDate();
			status = common.SafeAction(Common.o.getObject("edjCPDateReceived"), strDate, "edt");
			break;
		case "INCREASEDECREASEDATE":
			strDate = SCRCommon.returnDate(Integer.parseInt(strSplitValue[1]));
			status = common.SafeAction(Common.o.getObject("edjCPDateReceived"), strDate, "edt");
			break;
		}
		return status;
	}

}