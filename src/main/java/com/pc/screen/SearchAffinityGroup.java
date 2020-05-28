package com.pc.screen;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.PCThreadCache;

public class SearchAffinityGroup {

	public static String sheetname = "SearchAffinityGroup";
	Common common = CommonManager.getInstance().getCommon();
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));

	public Boolean SCRSearchAffinityGroup() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "Search Affinity Group Should be Successful", "Search Affinity Group was not successfully executed", "FAIL");
			return false;
		} else {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "Search Affinity Group Should be Successful", "Search Affinity Group was successfully executed", "PASS");
		}

		return status;
	}

	public boolean VerifyReset() {
		boolean status = false;
		By AffinityCommPgmName = Common.o.getObject("edtAffinityGrp_CommPgmName");
		WebElement element = driver.findElement(AffinityCommPgmName);
		if (element.getAttribute("value").isEmpty()) {
			status = true;
		}
		return status;
	}

}