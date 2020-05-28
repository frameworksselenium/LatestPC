package com.pc.screen;

import org.apache.log4j.Logger;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.PCThreadCache;

public class CLAQuotedForm {

	public static String sheetName = "CLAQuotedForm";
	//static Logger logger = Logger.getLogger(sheetName);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();

	public Boolean SCRCLAQuotedForm() throws Exception {
		Boolean status = false;
		status = common.ClassComponent(sheetName, Common.o);
		if (!status) {
			return false;
		}

		if (common.WaitForElementExist(Common.o.getObject("eleCLAQFTitle"), Integer.valueOf(HTML.properties.getProperty("LONGWAIT")))) {
			logger.info("System displayed Quoted Form Page");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "System should display  Quoted Form Page", "System displayed Quoted Form Page", "PASS");
			status = true;
		} else {
			logger.info("System not displayed Quoted Form Page");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "System should display Quoted Form Page", "System not displayed Quoted Form Page", "FAIL");
			status = false;
		}

		return status;
	}
}