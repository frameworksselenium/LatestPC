
/**
 * @ClassPurpose Access all the data's for Edit Account Page
 * @Scriptor Kumar
 * @ReviewedBy
 * @ModifiedBy Sojan
 * @LastDateModified 3/21/2017
 */
package com.pc.screen;

import org.apache.log4j.Logger;

import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.PCThreadCache;

public class Offerings {

	public static String sheetname = "Offerings";
	static Logger logger = Logger.getLogger(sheetname);
	Common common = CommonManager.getInstance().getCommon();

	public Boolean SCROfferings() throws Exception {

		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}
}