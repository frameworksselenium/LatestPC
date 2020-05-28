
package com.pc.screen;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.PCThreadCache;

public class PolicySummary {

	public static String sheetname = "PolicySummary";
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();

	public Boolean SCRPolicySummary() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

	public Boolean IsIssuedPolicy() throws IOException {
		Boolean status = false;
		String id = "PolicyFile_Summary:Policy_SummaryScreen:Policy_Summary_PolicyDV:Issued-inputEl";
		WebElement element = ManagerDriver.getInstance().getWebDriver().findElement(By.id(id));
		if (element.getText().equalsIgnoreCase("YES")) {

			logger.info("Policy is Issued ");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "System should Issue the Policy ", "System Issued the Policy", "PASS");
			status = true;
		} else {

			logger.info("Policy is NOT Issued ");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "System should Issue the Policy ", "System did not issue the Policy", "FAIL");
			status = false;
		}
		return status;
	}


}