package com.pc.screen;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.PCThreadCache;

public class AuthorityProfiles {

	public static String sheetname = "AuthorityProfiles";
	//static Logger logger = Logger.getLogger(sheetname);
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();

	public Boolean SCRAuthorityProfiles() throws Exception {

		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			return false;
		}

		return status;
	}

	public boolean addAuthorityProfile(String sFuncValue) throws Exception {
		boolean status = false;
		String sValue[] = sFuncValue.split(":::");
		int sRowCount = SCRCommon.TableRowCount(Common.o.getObject("eleAuthorityprofiletbl"));
		try {
			switch (sValue[0].toUpperCase()) {
			case "NEW":
				status = SCRCommon.DataWebTable(Common.o.getObject("eleAuthorityprofiletbl"), sRowCount, 1, "", "single", "div");
				status = common.SafeAction(Common.o.getObject("eleSearchAuthorityType"), "eleSearchAuthorityType", "eleSearchAuthorityType");
				status = common.SafeAction(Common.o.getObject("edtAuthorityIssueName"), sValue[1], "edt");
				status = common.SafeAction(Common.o.getObject("eleCommonSearch"), "eleCommonSearch", "eleCommonSearch");
				status = common.SafeAction(Common.o.getObject("eleCommonSelect"), "eleCommonSelect", "eleCommonSelect");

				break;
			}
		} catch (Throwable e) {
			e.printStackTrace();
			if (status == false) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should complete performing the add class", "System not completed performing the add class", "FAIL");
			}
		}
		return status;
	}

	/**
	 * @function Use to select the created authority profile
	 * @param sFuncValue
	 * @return true/false
	 * @throws Exception
	 */

	public boolean SelectAuthorityCreated(String sReadString) throws Exception {
		boolean blnStatus = false;
		boolean blnSearchString = false;
		String strPageCount = null;
		// String[] strReadStrings = sReadString.split(":::");
		blnSearchString = SCRCommon.ActionOnTable(Common.o.getObject("eleAuthorityProfilebody"), 1, 1, sReadString, "a");
		if (blnSearchString) {
			blnStatus = true;
		}
		Outer:
		// while(!blnSearchString)
		if (!blnSearchString) {
			blnStatus = common.ElementExist(Common.o.getObject("eleNextPage"));
			int icheckNextPageButton = common.ElementSize(Common.o.getObject("eleNextPage"));
			if (icheckNextPageButton > 0) {
				// if(blnStatus)
				// {
				strPageCount = common.ReadElement(Common.o.getObject("eleCommonPageNumber"), Integer.valueOf(HTML.properties.getProperty("LONGWAIT")));
				// strPageCount = strPageCount.substring(strPageCount.length()-2);
				strPageCount = SCRCommon.getIntFromString(strPageCount);
				for (int i = 0; i < Integer.parseInt(strPageCount); i++) {
					blnStatus = common.SafeAction(Common.o.getObject("eleNextPage"), "", "eleNextPage");
					blnStatus = common.SafeAction(Common.o.getObject("eleAuthorityProfilesttl"), "", "eleAuthorityProfilesttl");
					// blnSearchString =
					// common.ActionOnTable(Common.o.getObject("eleMyActivitiesTbl"), 22, 8,
					// strReadStrings[1], strReadStrings[0], "a");
					blnSearchString = SCRCommon.ActionOnTable(Common.o.getObject("eleAuthorityProfilebody"), 1, 1, sReadString, "a");
					if (blnSearchString) {
						HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search object in the table cell and click on object. Object name is '" + sReadString + "'", "System searched object in the table and clicked on object. object name is '" + sReadString + "'", "PASS");
						break Outer;
					}
				}
			} else {
				logger.info("" + sReadString + " does not exist in Authorities Table");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "" + sReadString + " should available in the Authority Profile Table", "" + sReadString + " is not available in the uthority Profile Table", "FAIL");
			}
		}
		if (!blnSearchString) {
			logger.info("" + sReadString + " does not exist in Authorities Table");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search string in table and Search string is '" + sReadString + "'", "System searched string in table and search string '" + sReadString + "' is  not present", "FAIL");
		} else {
			blnStatus = true;
		}
		return blnStatus;
	}

	public boolean SelectAuthorityCheckBox(String sReadString) throws Exception {
		boolean blnStatus = false;
		boolean blnSearchString = false;
		String strPageCount = null;
		// String[] strReadStrings = sReadString.split(":::");
		blnSearchString = SCRCommon.ActionOnTable(Common.o.getObject("eleAuthorityProfilebody"), 1, 0, sReadString, "div");
		if (blnSearchString) {
			blnStatus = true;
		}
		Outer:
		// while(!blnSearchString)
		if (!blnSearchString) {
			blnStatus = common.ElementExist(Common.o.getObject("eleNextPage"));
			int icheckNextPageButton = common.ElementSize(Common.o.getObject("eleNextPage"));
			if (icheckNextPageButton > 0) {
				// if(blnStatus)
				// {
				strPageCount = common.ReadElement(Common.o.getObject("eleCommonPageNumber"), Integer.valueOf(HTML.properties.getProperty("LONGWAIT")));
				// strPageCount = strPageCount.substring(strPageCount.length()-2);
				strPageCount = SCRCommon.getIntFromString(strPageCount);
				for (int i = 0; i < Integer.parseInt(strPageCount); i++) {
					blnStatus = common.SafeAction(Common.o.getObject("eleNextPage"), "", "eleNextPage");
					blnStatus = common.SafeAction(Common.o.getObject("eleAuthorityProfilesttl"), "", "eleAuthorityProfilesttl");
					// blnSearchString =
					// common.ActionOnTable(Common.o.getObject("eleMyActivitiesTbl"), 22, 8,
					// strReadStrings[1], strReadStrings[0], "a");
					blnSearchString = SCRCommon.ActionOnTable(Common.o.getObject("eleAuthorityProfilebody"), 1, 0, sReadString, "div");
					if (blnSearchString) {
						HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search object in the table cell and click on object. Object name is '" + sReadString + "'", "System searched object in the table and clicked on object. object name is '" + sReadString + "'", "PASS");
						break Outer;
					}
				}
			} else {
				logger.info("" + sReadString + " does not exist in Authorities Table");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "" + sReadString + " should available in the Authority Profile Table", "" + sReadString + " is not available in the Authority Profile Table", "FAIL");
			}
		}
		if (!blnSearchString) {
			logger.info("" + sReadString + " does not exist in Authorities Table");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search string in table and Search string is '" + sReadString + "'", "System searched string in table and search string '" + sReadString + "' is  not present", "FAIL");
		} else {
			blnStatus = true;
		}
		return blnStatus;
	}

}