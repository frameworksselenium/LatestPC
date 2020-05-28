
package com.pc.screen;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.PCThreadCache;

public class OutPut {

	public static String sheetname = "OutPut";
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();

	public Boolean SCROutPut() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			return false;
		}
		return status;
	}

	public Boolean OutPutValidation() throws Exception {
		boolean status = false;
		String sPrimaryWindow = driver.getWindowHandle();
		java.util.Set<String> allWindowHandles = driver.getWindowHandles();
		for (String handle : allWindowHandles) {
			if (driver.switchTo().window(handle).getTitle().contains("IDARS Access")) {
				driver.switchTo().window(handle).manage().window().maximize();
				// Common.dr().manage().window().maximize();
				boolean sPopUpExist = common.ElementSize(Common.o.getObject("eleNoRecords")) != 0;
				if (sPopUpExist == true) {
					// Common.dr().findElement(Common.o.getObject("eleNoRecords")).click();
					common.SafeAction(Common.o.getObject("eleNoRecords"), "eleNoRecords", "ele");
					status = false;
				}
				common.SafeAction(Common.o.getObject("drpoptCopyType"), "All Copies", "lst");
				common.SafeAction(Common.o.getObject("eleSearch"), "eleSearch", "ele");
				// Thread.sleep(5000);
				boolean view = common.ElementSize(Common.o.getObject("eleView")) != 0;
				if (view == true) {
					status = true;
				}
			}
		}
		// close the child window
		driver.close();
		// Switch to the parent window
		driver.switchTo().window(sPrimaryWindow);
		/*
		 * java.util.Set<String> allWindowHandles1 = Common.dr().getWindowHandles();
		 * for(String handle1 : allWindowHandles1) { if
		 * (Common.dr().switchTo().window(handle1).getTitle().
		 * contains("Guidewire PolicyCenter")) { Common.dr().switchTo().window(handle1);
		 * } }
		 */
		return status;
	}
}