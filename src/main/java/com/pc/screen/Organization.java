
package com.pc.screen;

import org.apache.log4j.Logger;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;

public class Organization {
	public static String sheetname = "Organization";
	static Logger logger = Logger.getLogger(sheetname);
	Common common = CommonManager.getInstance().getCommon();

	/**
	 * @function Use to perform all the objects/elements/functions from the
	 *           Organization excel sheet
	 * @return true/false
	 * @throws Exception
	 */
	public Boolean SCROrganization() throws Exception {

		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

}