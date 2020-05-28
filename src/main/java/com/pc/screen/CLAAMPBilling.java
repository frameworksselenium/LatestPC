package com.pc.screen;

import org.apache.log4j.Logger;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.PCThreadCache;

public class CLAAMPBilling {
	public static String sheetName = "CLAAMPBilling";
	Common common = CommonManager.getInstance().getCommon();
	//static Logger logger = Logger.getLogger(sheetName);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));

	public Boolean SCRCLAAMPBilling() throws Exception {
		Boolean status = false;
		status = common.ClassComponent(sheetName, Common.o);
		if (!status) {
			return false;
		}

		if (common.WaitForElementExist(Common.o.getObject("eleCLAAMPTitle"), Integer.valueOf(HTML.properties.getProperty("LONGWAIT")))) {
			logger.info("System displayed AMP Billing Page");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "System should display AMP Billing Page", "System displayed AMP Billing Page", "PASS");
			status = true;
		} else {
			logger.info("System not displayed AMP Billing Page");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "System should display AMP Billing Page", "System not displayed AMP Billing Page", "FAIL");
			status = false;
		}

		return status;
	}

}