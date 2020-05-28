package com.pc.screen;

import java.io.IOException;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import com.pc.constants.PCConstants;
import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.PCThreadCache;
import com.pc.utilities.XlsxReader;

public class Contact {

	public static String sheetname = "Contact";
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();

	public Boolean SCRContact() throws Exception {

		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			return false;
		}
		/*
		 * if(common.WaitUntilClickable(Common.o.getObject("eleAssertContact"),
		 * Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")))) {
		 * logger.info("System displayed Locations Page");
		 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
		 * PCThreadCache.getInstance().getProperty("methodName"),
		 * "System should Complete Contact Page","System displayed Locations Page",
		 * "PASS"); status = true; } else {
		 * logger.info("System not displayed Locations Page");
		 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
		 * PCThreadCache.getInstance().getProperty("methodName"),
		 * "System should Complete Contact Page","System not Completed Contact Page",
		 * "FAIL"); status = false; }
		 */
		return status;
	}

	public boolean ContactType(String strValue) throws IOException {
		boolean status = false;
		By option = By.xpath("//span[starts-with(text(),'" + strValue + "')]");
		try {
			status = common.SafeAction(option, "YES", "ele");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}

	public boolean DataFill(String sValue) throws Exception {
		boolean status = false;
		HashMap<String, Object> updateColumnNameValues = new HashMap<String, Object>();
		HashMap<String, Object> whereConstraint = new HashMap<String, Object>();
		XlsxReader sXL = XlsxReader.getInstance();
		int rowcount = sXL.getRowCount(sheetname);
		for (int i = 1; i < rowcount; i++) {
			String value = sXL.getCellData(sheetname, 0, i);
			if (!value.isEmpty()) {
				if (PCThreadCache.getInstance().getProperty("TCID").equals(value)) {
					if (sValue.equals("AccountNumber")) {
						updateColumnNameValues.clear();
						whereConstraint.clear();
						updateColumnNameValues.put(PCConstants.AccountNumber, PCThreadCache.getInstance().getProperty(PCConstants.CACHE_ACCOUNT_NUMBER));
						whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
						PCThreadCache.getInstance().setProperty("AccountNumber", PCThreadCache.getInstance().getProperty(PCConstants.CACHE_ACCOUNT_NUMBER));
						//status = sXL.executeUpdateQuery(PCConstants.SHEET_CONTACT, updateColumnNameValues, whereConstraint);

						updateColumnNameValues.clear();
						whereConstraint.clear();
						updateColumnNameValues.put(PCConstants.edtaccountNumber, PCThreadCache.getInstance().getProperty(PCConstants.CACHE_ACCOUNT_NUMBER));
						whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
						PCThreadCache.getInstance().setProperty("edtaccountNumber", PCThreadCache.getInstance

().getProperty(PCConstants.CACHE_ACCOUNT_NUMBER));
						//status = sXL.executeUpdateQuery(PCConstants.SHEET_SEARCHACCOUNT, updateColumnNameValues, whereConstraint);

						// status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"),
						// "Update Contact Set AccountNumber = '"+CreateAccount.AccountNumber+"' where
						// ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
						// status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"),
						// "Update SearchAccount Set edtaccountNumber =
						// '"+CreateAccount.AccountNumber+"' where
						// ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
						status = true;
						break;
					} else {
						updateColumnNameValues.clear();
						whereConstraint.clear();
						updateColumnNameValues.put(PCConstants.AccountNumber, PCThreadCache.getInstance().getProperty(PCConstants.CACHE_ACCOUNT_NUMBER));
						whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
						PCThreadCache.getInstance().setProperty("AccountNumber", PCThreadCache.getInstance().getProperty(PCConstants.CACHE_ACCOUNT_NUMBER));
						//status = sXL.executeUpdateQuery(PCConstants.SHEET_CONTACT, updateColumnNameValues, whereConstraint);

						updateColumnNameValues.clear();
						whereConstraint.clear();
						updateColumnNameValues.put(PCConstants.edtaccountNumber, PCThreadCache.getInstance().getProperty(PCConstants.CACHE_ACCOUNT_NUMBER));
						whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
						PCThreadCache.getInstance().setProperty("edtaccountNumber", PCThreadCache.getInstance().getProperty(PCConstants.CACHE_ACCOUNT_NUMBER));
						//status = sXL.executeUpdateQuery(PCConstants.SHEET_SEARCHACCOUNT, updateColumnNameValues,hereConstraint);

						updateColumnNameValues.clear();
						whereConstraint.clear();
						updateColumnNameValues.put(PCConstants.PolicyNumber, PCThreadCache.getInstance().getProperty(PCConstants.CACHE_POLICY_NUMBER));
						whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
						PCThreadCache.getInstance().setProperty("PolicyNumber", PCThreadCache.getInstance().getProperty(PCConstants.CACHE_POLICY_NUMBER));
						//status = sXL.executeUpdateQuery(PCConstants.SHEET_CONTACT, updateColumnNameValues, whereConstraint);

						// status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"),
						// "Update Contact Set AccountNumber = '"+CreateAccount.AccountNumber+"' where
						// ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
						// status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"),
						// "Update SearchAccount Set edtaccountNumber =
						// '"+CreateAccount.AccountNumber+"' where
						// ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
						// status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"),
						// "Update Contact Set PolicyNumber = '"+Payment.PolicyNumber+"' where
						// ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
						status = true;
						break;
					}
				}
			}
		}
		return status;
	}
}