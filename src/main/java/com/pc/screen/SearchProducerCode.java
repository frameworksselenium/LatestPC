package com.pc.screen;

import org.apache.log4j.Logger;

import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;

public class SearchProducerCode {

	public static String sheetname = "SearchProducerCode";
	Common common = CommonManager.getInstance().getCommon();
	static Logger logger = Logger.getLogger(sheetname);

	public Boolean SCRSearchProducerCode() throws Exception {

		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}
}