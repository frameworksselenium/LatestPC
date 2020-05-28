/**
 * @ClassPurpose This Class used for the CustomerForms 
 * @Scriptor Jaya priyanka
 * @ReviewedBy
 * @ModifiedBy
 * @LastDateModified 01/04/2018
 */
package com.pc.screen;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.PCThreadCache;

public class CustomerForms {

	public static String sheetname = "CustomerForms";
	Common common = CommonManager.getInstance().getCommon();
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();

	public Boolean SCRCustomerForms() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);

		if (!status) {
			status = false;
		}
		return status;
	}

}