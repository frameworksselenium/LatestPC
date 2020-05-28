package com.pc.screen;

import org.apache.log4j.Logger;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.PCThreadCache;

public class Reinsurance {

	public static String sheetname = "Reinsurance";
	Common common = CommonManager.getInstance().getCommon();
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));

	public Boolean SCRReinsurance() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

	public boolean VerifyResults(String funValue) throws Exception {
		boolean status = false;
		String[] sValue = funValue.split(":::");
		String Value = null;
		switch (sValue[0].toUpperCase()) {
		case "CHECKTranscationType":
			Value = common.GetTextFromTable(Common.o.getObject("eleBulkMailingTable"), 1, sValue[1]);
			status = common.CompareExactStringResult(sValue[0], sValue[1], Value);
			break;

		}

		if (!status) {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "" + sValue[0] + " case should PASS", "" + sValue[0] + " case should not fail", "FAIL");
			status = true;
		}
		return status;
	}

	public boolean ReinsuranceCertification(String activityName) throws Exception {
		Boolean blnStatus = false;
		blnStatus = common.ActionOnTable(Common.o.getObject("eleReinsuranceCertification_tbl"), 0, 1, activityName, "div");
		return blnStatus;
	}

	public boolean RsikTransferCheck(String activityName) throws Exception {
		Boolean blnStatus = false;
		blnStatus = common.ActionOnTable(Common.o.getObject("eleRiskTransferCheck_tbl"), 0, 1, activityName, "div");
		return blnStatus;
	}

}