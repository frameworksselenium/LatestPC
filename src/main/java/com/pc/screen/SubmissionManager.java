package com.pc.screen;

import java.util.HashMap;

import org.apache.log4j.Logger;
import com.pc.constants.PCConstants;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.FlatFile;
import com.pc.utilities.HTML;
import com.pc.utilities.PCThreadCache;
import com.pc.utilities.XlsxReader;

public class SubmissionManager {

	public static String sheetname = "SubmissionManager";
	Common common = CommonManager.getInstance().getCommon();
	static Logger logger = Logger.getLogger(sheetname);

	public Boolean SCRSubmissionManager() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

}