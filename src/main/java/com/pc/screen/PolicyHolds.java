
package com.pc.screen;

import org.apache.log4j.Logger;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.PCThreadCache;

public class PolicyHolds {

	public static String sheetname = "PolicyHolds";
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();

	public Boolean SCRPolicyHolds() throws Exception {

		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

	public boolean holdrules(String sFunctionValue) throws Throwable {
		boolean status = false;
		String[] sValue = sFunctionValue.split(":::");
		int sRowCount = SCRCommon.TableRowCount(Common.o.getObject("eletblLineOfBusiness"));

		logger.info(sValue[0]);
		try {
			status = SCRCommon.DataWebTable(Common.o.getObject("eletblLineOfBusiness"), sRowCount, 1, "<none>", "single", "div");
			status = common.SafeAction(Common.o.getObject("lstPolLOBval"), sValue[1], "lst");

			status = common.SafeAction(Common.o.getObject("lstPolPTTval"), sValue[2], "lst");
			status = common.SafeAction(Common.o.getObject("lstPolTDTval"), sValue[2], "lst");

			status = SCRCommon.DataWebTable(Common.o.getObject("elePolLOBtbl"), sRowCount, 3, "<none>", "single", "a");

			status = common.SafeAction(Common.o.getObject("eleCommonUpdate"), "", "eleCommonUpdate");
			status = SCRCommon.PageVerify("titleNewPolicyHold");
		} catch (Throwable e) {
			e.printStackTrace();
			if (status == false) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should complete performing the add class", "System not completed performing the Hold Rules table", "FAIL");
			}
		}

		return status;
	}

	public boolean DefaultHoldStartDate(String strFuncValue) throws Exception {
		boolean status = false;
		String[] strSplitValue = strFuncValue.split(":::");
		String strDate = null;
		switch (strSplitValue[0].toUpperCase()) {
		case "CURRENTDATE":
			strDate = SCRCommon.ReturnCurrentDate();
			status = common.SafeAction(Common.o.getObject("edtHoldStartDate"), strDate, "edt");
			break;
		case "INCREASEDECREASEDATE":
			strDate = SCRCommon.returnDate(Integer.parseInt(strSplitValue[1]));
			status = common.SafeAction(Common.o.getObject("edtHoldStartDate"), strDate, "edt");
			break;
		}
		return status;
	}

	public boolean DefaultHoldEndDate(String strFuncValue) throws Exception {
		boolean status = false;
		String[] strSplitValue = strFuncValue.split(":::");
		String strDate = null;
		switch (strSplitValue[0].toUpperCase()) {
		case "CURRENTDATE":
			strDate = SCRCommon.ReturnCurrentDate();
			status = common.SafeAction(Common.o.getObject("edtHoldEndDate"), strDate, "edt");
			break;
		case "INCREASEDECREASEDATE":
			strDate = SCRCommon.returnDate(Integer.parseInt(strSplitValue[1]));
			status = common.SafeAction(Common.o.getObject("edtHoldEndDate"), strDate, "edt");
			break;
		}
		return status;
	}

}