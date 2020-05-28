
package com.pc.screen;

import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;

public class CLACustomerCompltr {

	public static String sheetname = "CLACustomerCompltr";
	Common common = CommonManager.getInstance().getCommon();

	public boolean SCRCLACustomerCompltr() {
		boolean status = false;
		status = common.ClassComponent(sheetname, Common.o);
		if (status) {
			status = true;
		}
		return status;
	}

}
