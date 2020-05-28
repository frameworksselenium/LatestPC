
package com.pc.screen;

import org.apache.log4j.Logger;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.PCThreadCache;

public class QPBContactInformation {

	public static String sheetname = "QPBContactInformation";
	Common common = CommonManager.getInstance().getCommon();
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));

	public Boolean SCRQPBContactInformation() throws Exception {

		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

	public boolean VerifyResults(String sFuncValue) throws Exception {
		boolean status = false;
		String Value = null;
		String[] sValue = sFuncValue.split(":::");
		logger.info("Verifying the Results Contact Information Page");
		switch (sValue[0].toUpperCase()) {
		case "VERIFYCONTACTINFORMATION":
			logger.info(sValue[0]);
			Value = common.ReadElementGetAttribute(Common.o.getObject("edtQPBContactName"), "value", Integer.valueOf

(HTML.properties.getProperty("LONGWAIT")));
			status = common.CompareStringResult("VerifyUnderWriter", sValue[1], Value);
			break;
		case "VERIFYQUOTEVERSIONCHANGE":
			logger.info(sValue[0]);
			Value = common.ReadElementGetAttribute(Common.o.getObject("edtContactName"), "value", Integer.valueOf

(HTML.properties.getProperty("LONGWAIT")));
			status = common.CompareStringResult("UserName", sValue[1], Value);
			Value = common.ReadElementGetAttribute(Common.o.getObject("edtAddressQPB"), "value", Integer.valueOf

(HTML.properties.getProperty("LONGWAIT")));
			status = common.CompareStringResult("Address", sValue[2], Value);
			Value = common.ReadElementGetAttribute(Common.o.getObject("edtCityAPB"), "value", Integer.valueOf

(HTML.properties.getProperty("LONGWAIT")));
			status = common.CompareStringResult("City", sValue[3], Value);
			Value = common.ReadElementGetAttribute(Common.o.getObject("edtZipCodeQPB"), "value", Integer.valueOf

(HTML.properties.getProperty("LONGWAIT")));
			status = common.CompareStringResult("Zipcode", sValue[4], Value);
			break;
		}
		if (!status) {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "" + sValue[0] + " case should PASS", "" + sValue[0] + " case fail", "FAIL");
			status = true;
		}
		return status;
	}
}