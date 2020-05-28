
/**
 * @ClassPurpose This Class used for the EditAccount Screen
 * @Scriptor Sojan David
 * @ReviewedBy
 * @ModifiedBy
 * @LastDateModified 3/17/2017
 */
package com.pc.screen;

import org.apache.log4j.Logger;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.PCThreadCache;

public class EditAccount {

	public static String sheetname = "EditAccount";
	Common common = CommonManager.getInstance().getCommon();
	//static Logger logger = Logger.getLogger(sheetname);
	//private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));

	/**
	 * @function Use to perform all the objects/elements/functions from the
	 *           EditAccount excel sheet
	 * @return true/false
	 * @throws Exception
	 */
	public Boolean SCREditAccount() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

	/**
	 * @function to check whether we get update or standardize button for account
	 *           creation
	 * @return true/false
	 */
	public boolean update() {
		boolean status = false;
		int sElementSize = common.ElementSize(Common.o.getObject("eleEditUpdate"));
		if (sElementSize == 1) {
			try {
				status = common.SafeAction(Common.o.getObject("eleEditUpdate"), "eleEditUpdate", "eleEditUpdate");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				status = common.SafeAction(Common.o.getObject("eleOverRide"), "eleOverRide", "eleOverRide");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return status;
	}
}