package com.pc.screen;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.pc.constants.PCConstants;
import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.FlatFile;
import com.pc.utilities.HTML;
import com.pc.utilities.PCThreadCache;
import com.pc.utilities.XlsxReader;

public class SearchPolicy {

	public static String sheetname = "SearchPolicy";
	Common common = CommonManager.getInstance().getCommon();
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));

	public Boolean SCRSearchPolicy() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

	public boolean PolicyNumber() throws Exception {
		boolean status = false;
		Payment payment = new Payment();
		String accountNumber = null;
		status = common.WaitForElementExist(Common.o.getObject("eleQuotePolicyNumber"), Integer.valueOf(HTML.properties.getProperty

("LONGWAIT")));
		if (status) {
			accountNumber = common.ReadElement(Common.o.getObject("elePolicySummary_AccountNumber"), 30);
			PCThreadCache.getInstance().setProperty(PCConstants.CACHE_ACCOUNT_NUMBER, accountNumber);
			payment.policyNumber = common.ReadElement(Common.o.getObject("eleQuotePolicyNumber"), Integer.valueOf

(HTML.properties.getProperty("LONGWAIT")));
			PCThreadCache.getInstance().setProperty(PCConstants.CACHE_POLICY_NUMBER, payment.policyNumber);
			payment.policyNumberSearch = payment.policyNumber.substring(payment.policyNumber.length() - 6);
			PCThreadCache.getInstance().setProperty(PCConstants.CACHE_POLICY_NUMBER_SEARCH, payment.policyNumberSearch);
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "Policy Number shold be generated", "Policy Number generated: Policy Number is '" + payment.policyNumber + "'", "PASS");
			if (HTML.properties.getProperty("EXECUTIONAPP").contains("ODS")) {
				FlatFile sReadWrite = FlatFile.getInstance();
				SCRCommon SCRCommon = new SCRCommon();
				String FlatFileName = SCRCommon.FlatFileName();
				String policyNumebrSearch = "%" + payment.policyNumberSearch;
				status = sReadWrite.write(PCThreadCache.getInstance().getProperty("TCID"), PCThreadCache.getInstance

().getProperty("methodName"), "PolicyNumber", policyNumebrSearch, "OUTPUT", FlatFileName);
				status = sReadWrite.write(PCThreadCache.getInstance().getProperty("TCID"), PCThreadCache.getInstance

().getProperty("methodName"), "AccountNumber", accountNumber, "OUTPUT", FlatFileName);
			}
		}
		return status;
	}

	public Boolean trimPolicyNumber(String strFuncValue) {
		Boolean blnStatus = false;
		HashMap<String, Object> updateColumnNameValues = new HashMap<String, Object>();
		HashMap<String, Object> whereConstraint = new HashMap<String, Object>();
		XlsxReader sXL = XlsxReader.getInstance();
		String policyNum = strFuncValue.substring(strFuncValue.length() - 6);
		whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
		updateColumnNameValues.put("edtPolicyNumber", policyNum);
		PCThreadCache.getInstance().setProperty("edtPolicyNumber", policyNum);
		//blnStatus = sXL.executeUpdateQuery(PCConstants.SHEET_SEARCHPOLICY, updateColumnNameValues, whereConstraint);
		return blnStatus;
	}

	public boolean OpenBound(String activityName) throws Exception {
		Boolean blnStatus = false;
		blnStatus = common.ActionOnTable(Common.o.getObject("PolicyTransactionTable"), 2, 1, activityName, "a");
		return blnStatus;
	}

}