/**
 * @ClassPurpose This Class used for the Desktop usecase
 * @Scriptor Rathish Kuppusamy
 * @ReviewedBy
 * @ModifiedBy
 * @LastDateModified 04/06/2017
 */
package com.pc.screen;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.PCThreadCache;

public class Desktop {

	public static String sheetname = "Desktop";
	Common common = CommonManager.getInstance().getCommon();
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();

	public Boolean SCRDesktop() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);

		if (!status) {
			status = false;
		}
		return status;
	}

	public boolean VerifyElements(String strValue) throws Exception {
		boolean status1 = false, status2 = false;
		String[] sfuncVal = strValue.split(":::");
		if (sfuncVal[0].equalsIgnoreCase("AccountNumber")) {
			status1 = SCRCommon.PageVerify("DesktopAccountNumber");
		}
		if (sfuncVal[1].equalsIgnoreCase("ProducerCode"))
			status2 = SCRCommon.PageVerify("DesktopProducerCode");

		if (status1 && status2)
			return true;
		return false;
	}

}