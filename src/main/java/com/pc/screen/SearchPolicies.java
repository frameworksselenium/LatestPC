
package com.pc.screen;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.PCThreadCache;

public class SearchPolicies {

	public static String sheetname = "SearchPolicies";
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();

	public Boolean SCRSearchPolicies() throws Exception {

		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

	public boolean VerifyTableCell(String strValue) throws Exception {
		boolean status = false;
		String[] sFuncValue = strValue.split(":::");
		By table = Common.o.getObject(sFuncValue[0]);
		String policySystem = sFuncValue[1];
		int row = Integer.parseInt(sFuncValue[2]) - 1;
		status = common.VerifyTextFromTable(table, row, 6, policySystem);
		return status;
	}

}