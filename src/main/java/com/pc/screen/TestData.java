package com.pc.screen;

import org.apache.log4j.Logger;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.PCThreadCache;

public class TestData {

	public static String sheetname = "TestData";
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();

	public Boolean SCRTestData() throws Exception {

		// System.out.println(Runtime.getRuntime().totalMemory());
		// System.out.println(Runtime.getRuntime().freeMemory());
		//
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			logger.info("Test Data update was UnSuccessful");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "Test Data update should be Successful", "Test Data update was UnSuccessful", "FAIL");
			status = false;
		} else {
			logger.info("Test Data update was Successful");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "Test Data update should be Successful", "Test Data update was Successful", "PASS");
			status = true;
		}
		return status;
	}
}