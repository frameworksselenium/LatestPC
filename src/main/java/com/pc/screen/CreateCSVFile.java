
//@Component - This Component used to verify functionalities of Cancel Policy for SOR Policy
package com.pc.screen;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.PCThreadCache;

public class CreateCSVFile {
	
	public static String sheetname = "CreateCSVFile";
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();

	public Boolean SCRCreateCSVFile() throws Exception {

		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			return false;
		}

		return status;
	}

	public boolean CreateFlatFile() throws Exception {

		boolean status = true;
		status = common.CSVFile();
		return status;
	}
	/**
	 * To create CSV for Policy compare
	 * @return
	 * @throws Exception
	 */
	public boolean CreateE2ECSVFile() throws Exception{

		boolean status = true;
		status=SCRCommon.E2ECSVFile();
		return true;		
	}

}