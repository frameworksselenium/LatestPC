
/**
 * @ClassPurpose This Class used for the PolicyTransactions 
 * @Scriptor Sudha
 * @ReviewedBy
 * @ModifiedBy
 * @LastDateModified 
 */
package com.pc.screen;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.ManagerDriver;

public class PolicyTransactions {
	public static String sheetname = "PolicyTransactions";
	Common common = CommonManager.getInstance().getCommon();
	static Logger logger = Logger.getLogger(sheetname);
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();

	public Boolean SCRPolicyTransactions() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}
	

}
