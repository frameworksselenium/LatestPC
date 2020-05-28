
package com.pc.screen;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.pc.constants.PCConstants;
import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.PCThreadCache;
import com.pc.utilities.XlsxReader;

public class GetDataFromPCForODS {

	public static String sheetname = "GetDataFromPCForODS";
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
	public static String strFileName = HTML.properties.getProperty("suitepath") + "\\Data\\ODSData.xlsm";
	Common common = CommonManager.getInstance().getCommon();
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();

	public Boolean SCRGetDataFromPCForODS() throws Exception {

		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			logger.info("Get Data from PC was not Successful");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Get data From PC Component", "Get Data from PC was not Successful", "FAIL");
			return false;
		}

		else {
			logger.info("Get Data from PC was Successful");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Get data From PC Component", "Get Data from PC was Successful", "PASS");
		}
		return status;
	}

	public Boolean ODSCfun(String strText) throws Exception {
		String elementfield = null;
		String[] sValue = strText.split(":::");
		String[] svalueQueryfield = sValue[0].split(":");
		String[] svaluePCfield = sValue[1].split(":");
		String wait = HTML.properties.getProperty("SHORTWAIT");
		String strPCfieldValue = null;
		String strQueryfieldValue = null;
		Boolean status = true;
		XlsxReader sXL = XlsxReader.getInstance();
		HashMap<String, Object> whereConstraint = new HashMap<String, Object>();
		HashMap<String, Object> updateColumnNameValues = new HashMap<String, Object>();
		for (int i = 0; i < (svalueQueryfield.length); i++) {

			if (svalueQueryfield[i].isEmpty()) {
				break;
			} else {
				elementfield = svalueQueryfield[i].substring(0, 3);
				String methodName = svalueQueryfield[i].substring(3);
				switch (elementfield) {
				case "fun": {
					Class[] paramString = new Class[1];
					paramString[0] = String.class;
					Class cls = Class.forName("com.pc.screen." + sheetname);
					Object obj = cls.newInstance();
					Method method = cls.getDeclaredMethod("GetValuefromTable", paramString);
					status = (Boolean) method.invoke(obj, new String(methodName));
					break;
				}
				case "clk": {
					String ColName = "ele" + methodName;
					status = common.SafeAction(Common.o.getObject(ColName), "YES", ColName);
					break;
				}
				case "lst": {
					String[] slstelementnameandval = svalueQueryfield[i].split("-");
					String ColName = slstelementnameandval[0];
					status = common.SafeAction(Common.o.getObject(ColName), slstelementnameandval[1], ColName);
					break;
				}
				case "lsj": {
					String[] slstelementnameandval = svalueQueryfield[i].split("-");
					String ColName = slstelementnameandval[0];
					status = common.SafeAction(Common.o.getObject(ColName), slstelementnameandval[1], ColName);
					break;
				}

				default: {
					strQueryfieldValue = common.ReadElement(Common.o.getObject(svalueQueryfield[i]), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
					if (svalueQueryfield[i].contains("AccountNumber")) {
						updateColumnNameValues.clear();
						whereConstraint.clear();
						updateColumnNameValues.put(PCConstants.AccountNumber, strQueryfieldValue);
						whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
						sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);

						// common.UpdateDataInExcel(strFileName, "Update Validation Set AccountNumber='"
						// + strQueryfieldValue + "' where ID = '" +
						// PCThreadCache.getInstance().getProperty("TCID") + "'");
					} else if (svalueQueryfield[i].contains("PolicyNumber")) {
						updateColumnNameValues.clear();
						whereConstraint.clear();
						updateColumnNameValues.put(PCConstants.PolicyNumber, strQueryfieldValue);
						whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
						sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);

						// common.UpdateDataInExcel(strFileName, "Update Validation Set PolicyNumber='"
						// + strQueryfieldValue + "' where ID = '" +
						// PCThreadCache.getInstance().getProperty("TCID") + "'");
					} else if (svalueQueryfield[i].contains("User_UserName")) {
						updateColumnNameValues.clear();
						whereConstraint.clear();
						updateColumnNameValues.put(PCConstants.User_UserName, strQueryfieldValue);
						whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
						sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);

						// common.UpdateDataInExcel(strFileName, "Update Validation Set PolicyNumber='"
						// + strQueryfieldValue + "' where ID = '" +
						// PCThreadCache.getInstance().getProperty("TCID") + "'");
					} else {
						updateColumnNameValues.clear();
						whereConstraint.clear();
						updateColumnNameValues.put(methodName, strQueryfieldValue);
						whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
						sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);
						// common.UpdateDataInExcel(strFileName, "Update Validation Set "+methodName+" =
						// '" + strQueryfieldValue + "' where ID = '" +
						// PCThreadCache.getInstance().getProperty("TCID") + "'");
					}

				}
				}

			}
		}

		for (int i = 0; i < (svaluePCfield.length); i++) {
			if (svaluePCfield[i].isEmpty()) {
				break;
			} else {
				elementfield = svaluePCfield[i].substring(0, 3);
				String methodName1 = svaluePCfield[i].substring(3);
				switch (elementfield) {
				case "fun": {
					Class[] paramString = new Class[1];
					paramString[0] = String.class;
					Class cls = Class.forName("com.pc.screen." + sheetname);
					Object obj = cls.newInstance();
					Method method = cls.getDeclaredMethod("GetValuefromTable", paramString);
					status = (Boolean) method.invoke(obj, new String(methodName1));
					break;
				}
				case "cfu": {
					String[] svaluePCfieldDate = methodName1.split("-");
					String methodName2 = svaluePCfieldDate[0];
					String strValue = null;
					Class noparams[] = {};
					Class cls = Class.forName("com.pc.screen." + "SCRCommon");
					Object obj = cls.newInstance();
					Method method = cls.getDeclaredMethod(methodName2, noparams);
					strValue = (String) method.invoke(obj, null);

					updateColumnNameValues.clear();
					whereConstraint.clear();
					updateColumnNameValues.put(PCConstants.PCFieldValue, strValue);
					whereConstraint.put(PCConstants.PCFieldName, svaluePCfieldDate[1]);
					whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
					sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);

					// common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue='"
					// + strValue + "' where PCFieldName ='"+ svaluePCfieldDate[1] +"'and ID = '" +
					// PCThreadCache.getInstance().getProperty("TCID") + "'");
					break;
				}
				case "clk": {
					String ColName = "ele" + methodName1;
					status = common.SafeAction(Common.o.getObject(ColName), "YES", ColName);
					break;
				}
				case "mel": {
					status = common.SafeAction(Common.o.getObject(svaluePCfield[i]), "YES", svaluePCfield[i]);
					break;
				}
				case "lst": {
					String[] slstelementnameandval = svaluePCfield[i].split("-");
					String ColName = slstelementnameandval[0];
					status = common.SafeAction(Common.o.getObject(ColName), slstelementnameandval[1], ColName);
					break;
				}
				case "lsj": {
					String[] slstelementnameandval = svaluePCfield[i].split("-");
					String ColName = slstelementnameandval[0];
					status = common.SafeAction(Common.o.getObject(ColName), slstelementnameandval[1], ColName);
					break;
				}
				case "edt": {
					strPCfieldValue = common.ReadElementGetAttribute(Common.o.getObject(svaluePCfield[i]), "value", 

Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));

					updateColumnNameValues.clear();
					whereConstraint.clear();
					updateColumnNameValues.put(PCConstants.PCFieldValue, strPCfieldValue);
					whereConstraint.put(PCConstants.PCFieldName, svaluePCfield[i].substring(3));
					whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
					sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);

					// common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
					// + strPCfieldValue + "' where PCFieldName ='"+ svaluePCfield[i].substring(3) +
					// "'and ID = '" + PCThreadCache.getInstance().getProperty("TCID") + "'");
					break;
				}
				default: {
					strPCfieldValue = common.ReadElementforODS(Common.o.getObject(svaluePCfield[i]), Integer.valueOf

(HTML.properties.getProperty("NORMALWAIT")));

					updateColumnNameValues.clear();
					whereConstraint.clear();
					updateColumnNameValues.put(PCConstants.PCFieldValue, strPCfieldValue);
					whereConstraint.put(PCConstants.PCFieldName, svaluePCfield[i].substring(3));
					whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
					sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);

					// common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
					// + strPCfieldValue + "' where PCFieldName ='"+ svaluePCfield[i].substring(3) +
					// "'and ID = '" + PCThreadCache.getInstance().getProperty("TCID") + "'");
				}
				}
			}
			// whereConstraint = null;
			// updateColumnNameValues = null;
			// strPCfieldValue = null;
		}
		return true;
	}

	/**
	 * @function to get value from function
	 * @param funValue
	 * @return status
	 * @throws Exception
	 **/
	public boolean GetValuefromTable(String funValue) throws Exception {
		boolean status = false;
		String value = null;
		String[] svalue = funValue.split("-");
		XlsxReader sXL = XlsxReader.getInstance();
		HashMap<String, Object> whereConstraint = new HashMap<String, Object>();
		HashMap<String, Object> updateColumnNameValues = new HashMap<String, Object>();
		String tcID = PCThreadCache.getInstance().getProperty("TCID");
		switch (svalue[0].toUpperCase()) {
		case "AFFINITYGROUP_JURISDICTIONS":
			value = common.GetTextFromTable(Common.o.getObject("eleAffinityGroup_JurisdictionSt"), 0, 1);
			updateColumnNameValues.clear();
			whereConstraint.clear();
			updateColumnNameValues.put(PCConstants.PCFieldValue, value);
			whereConstraint.put(PCConstants.PCFieldName, "AffinityGroup_JurisdictionState");
			whereConstraint.put(PCConstants.ID, tcID);
			sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);
			// Common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
			// + value + "' where PCFieldName = 'AffinityGroup_JurisdictionState' and ID =
			// '" + HTML.properties.getProperty("TCID") + "'");
			status = true;
			break;
		case "AFFINITYGROUP_PRODUCTS":
			value = common.GetTextFromTable(Common.o.getObject("eleAffinityGroup_ProductsSt"), 0, 1);
			if (value.contains("'")) {
				value = value.replace("'", "");
			}
			// Common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
			// + value + "' where PCFieldName = 'AffinityGroup_Products' and ID = '" +
			// HTML.properties.getProperty("TCID") + "'");
			updateColumnNameValues.clear();
			whereConstraint.clear();
			updateColumnNameValues.put(PCConstants.PCFieldValue, value);
			whereConstraint.put(PCConstants.PCFieldName, "AffinityGroup_Products");
			whereConstraint.put(PCConstants.ID, tcID);
			sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);
			status = true;
			break;
		case "AFFINITYGROUP_PRODUCERCODES":
			value = common.GetTextFromTable(Common.o.getObject("eleAffinityGroup_ProducerCodesSt"), 0, 1);
			updateColumnNameValues.clear();
			whereConstraint.clear();
			updateColumnNameValues.put(PCConstants.PCFieldValue, value);
			whereConstraint.put(PCConstants.PCFieldName, "AffinityGroup_ProducerCode");
			whereConstraint.put(PCConstants.ID, tcID);
			sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);
			// Common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
			// + value + "' where PCFieldName = 'AffinityGroup_ProducerCode' and ID = '" +
			// HTML.properties.getProperty("TCID") + "'");

			String Prod_Eff_Date = common.GetTextFromTable(Common.o.getObject("eleAffinityGroup_ProducerCodesSt"), 0, 2);
			updateColumnNameValues.clear();
			whereConstraint.clear();
			updateColumnNameValues.put(PCConstants.PCFieldValue, Prod_Eff_Date);
			whereConstraint.put(PCConstants.PCFieldName, "AffinityGroup_ProdEffDate");
			whereConstraint.put(PCConstants.ID, tcID);
			sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);
			// Common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
			// + Prod_Eff_Date + "' where PCFieldName = 'AffinityGroup_ProdEffDate' and ID =
			// '" + HTML.properties.getProperty("TCID") + "'");

			String Prod_Exp_Date = common.GetTextFromTable(Common.o.getObject("eleAffinityGroup_ProducerCodesSt"), 0, 3);
			updateColumnNameValues.clear();
			whereConstraint.clear();
			updateColumnNameValues.put(PCConstants.PCFieldValue, Prod_Exp_Date);
			whereConstraint.put(PCConstants.PCFieldName, "AffinityGroup_ProdExpDate");
			whereConstraint.put(PCConstants.ID, tcID);
			sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);
			// Common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
			// + Prod_Exp_Date + "' where PCFieldName = 'AffinityGroup_ProdExpDate' and ID =
			// '" + HTML.properties.getProperty("TCID") + "'");
			status = true;
			break;
		case "ACCOUNTSUMMARY_SUBPOLICYNUM":
			value = GetTextFromTableMultipleCondition(Common.o.getObject("eleAccountSummary_PolicyTranTbl"), 2, 7, "Bound", 

"Submission", "POLICYTRANSACTIONTBL");
			updateColumnNameValues.clear();
			whereConstraint.clear();
			updateColumnNameValues.put(PCConstants.PolicyNumber, value);
			whereConstraint.put(PCConstants.ID, tcID);
			sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);
			// Common.UpdateDataInExcel(strFileName, "Update Validation Set PolicyNumber='"
			// + value + "' where ID = '" + HTML.properties.getProperty("TCID") + "'");
			status = true;
			break;
		case "ACCOUNTSUMMARY_POLICYTRANSTAT":
			value = GetTextFromTableMultipleCondition(Common.o.getObject("eleAccountSummary_PolicyTranTbl"), 2, 0, svalue[1], "", 

"POLICYTRANSACTIONTBL");
			status = true;
			break;
		case "ACCOUNTSUMMARY_ACTIVITY":
			value = GetTextFromTableMultipleCondition(Common.o.getObject("eleAccountSummary_Activitytbl"), 0, 0, "Open", "Open", 

"ACTIVITIESTBL");
			status = true;
			break;
		case "POLICYSUMMARY_ACTIVITY":
			value = GetTextFromTableMultipleCondition(Common.o.getObject("elepolicySummary_Activitytbl"), 0, 0, "Open", "Open", 

"POLACTIVITIESTBL");
			// value =
			// Common.GetTextFromTable(Common.o.getObject("elepolicySummary_Activitytbl1"),2,"Follow
			// up");
			status = true;
			break;
		case "CONTACTTBL":
			status = common.ActionOnTable(Common.o.getObject("eleAccountSummary_Contacttbl"), 3, 3, "Named Insured", "Named Insured", 

"div");
			break;
		case "ADDRESS":
			value = common.ReadElementforODS(Common.o.getObject("eleContacts_Address"), Integer.valueOf(HTML.properties.getProperty

("NORMALWAIT")));
			String[] sValue1 = value.split("\n");
			String[] sValue2 = sValue1[1].split(",");

			updateColumnNameValues.clear();
			whereConstraint.clear();
			updateColumnNameValues.put(PCConstants.PCFieldValue, sValue1[0]);
			whereConstraint.put(PCConstants.PCFieldName, "Contacts_AddressLine1");
			whereConstraint.put(PCConstants.ID, tcID);
			sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);
			// Common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
			// + sValue1[0] + "' where PCFieldName = 'Contacts_AddressLine1' and ID = '" +
			// HTML.properties.getProperty("TCID") + "'");

			updateColumnNameValues.clear();
			whereConstraint.clear();
			updateColumnNameValues.put(PCConstants.PCFieldValue, sValue2[0]);
			whereConstraint.put(PCConstants.PCFieldName, "Contacts_City");
			whereConstraint.put(PCConstants.ID, tcID);
			sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);
			// Common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
			// + sValue2[0] + "' where PCFieldName = 'Contacts_City' and ID = '" +
			// HTML.properties.getProperty("TCID") + "'");
			break;
		case "POLICYSUMMARY_SHELLPOLICYNUMBER":
			value = common.ReadElementforODS(Common.o.getObject("eleShell_PolicyNumber"), Integer.valueOf

(HTML.properties.getProperty("NORMALWAIT")));
			// String PolicyNumber = value.substring(value.length()-6);

			updateColumnNameValues.clear();
			whereConstraint.clear();
			PCThreadCache.getInstance().setProperty(PCConstants.CACHE_POLICY_NUMBER, value);
			updateColumnNameValues.put(PCConstants.PolicyNumber, value);
			whereConstraint.put(PCConstants.ID, tcID);
			sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);
			// Common.UpdateDataInExcel(strFileName, "Update Validation Set PolicyNumber='"
			// + value + "' where ID = '" + HTML.properties.getProperty("TCID") + "'");

			updateColumnNameValues.clear();
			whereConstraint.clear();
			PCThreadCache.getInstance().setProperty(PCConstants.CACHE_POLICY_NUMBER, value);
			updateColumnNameValues.put(PCConstants.PCFieldValue, value);
			whereConstraint.put(PCConstants.PCFieldName, "PolicySummary_ShellPolicyNumber");
			whereConstraint.put(PCConstants.ID, tcID);
			sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);
			// Common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
			// + value + "' where PCFieldName ='PolicySummary_ShellPolicyNumber' and ID = '"
			// + HTML.properties.getProperty("TCID") + "'");
			break;
		case "POLICYSUMMARY_ACTIVITYTBL":
			status = common.ActionOnTable(Common.o.getObject("elepolicySummary_Activitytbl"), 2, 2, svalue[1], svalue[1], "a");
			String ActivityDesc = common.ReadElementforODS(Common.o.getObject("elePolicySummary_ActivityDesc"), Integer.valueOf

(HTML.properties.getProperty("NORMALWAIT")));
			String ActivityStatus = common.ReadElementforODS(Common.o.getObject("elePolicySummary_ActivityStatus"), Integer.valueOf

(HTML.properties.getProperty("NORMALWAIT")));
			String ActivityCurrentStatus = common.ReadElementforODS(Common.o.getObject("eleActivity_Status"), Integer.valueOf

(HTML.properties.getProperty("NORMALWAIT")));
			String ActivityEscalationDt = common.ReadElementGetAttribute(Common.o.getObject("eleActivity_EscalationDate"), "value", 

Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));

			updateColumnNameValues.clear();
			whereConstraint.clear();
			updateColumnNameValues.put(PCConstants.PCFieldValue, ActivityDesc);
			whereConstraint.put(PCConstants.PCFieldName, "PolicySummary_ActivityDesc");
			whereConstraint.put(PCConstants.ID, tcID);
			sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);
			// Common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
			// + ActivityDesc + "' where PCFieldName = 'PolicySummary_ActivityDesc' and ID =
			// '" + HTML.properties.getProperty("TCID") + "'");

			updateColumnNameValues.clear();
			whereConstraint.clear();
			updateColumnNameValues.put(PCConstants.PCFieldValue, ActivityStatus);
			whereConstraint.put(PCConstants.PCFieldName, "PolicySummary_ActivityStatus");
			whereConstraint.put(PCConstants.ID, tcID);
			sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);
			// Common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
			// + ActivityStatus + "' where PCFieldName = 'PolicySummary_ActivityStatus' and
			// ID = '" + HTML.properties.getProperty("TCID") + "'");

			updateColumnNameValues.clear();
			whereConstraint.clear();
			updateColumnNameValues.put(PCConstants.PCFieldValue, ActivityCurrentStatus);
			whereConstraint.put(PCConstants.PCFieldName, "Acticity_Status");
			whereConstraint.put(PCConstants.ID, tcID);
			sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);
			// Common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
			// + ActivityCurrentStatus + "' where PCFieldName = 'Acticity_Status' and ID =
			// '" + HTML.properties.getProperty("TCID") + "'");

			updateColumnNameValues.clear();
			whereConstraint.clear();
			updateColumnNameValues.put(PCConstants.PCFieldValue, ActivityEscalationDt);
			whereConstraint.put(PCConstants.PCFieldName, "Activity_EsacaltionDate");
			whereConstraint.put(PCConstants.ID, tcID);
			sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);
			// Common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
			// + ActivityEscalationDt + "' where PCFieldName = 'Activity_EsacaltionDate' and
			// ID = '" + HTML.properties.getProperty("TCID") + "'");

			if (common.ElementSize(Common.o.getObject("elePolicySummary_CloseWS")) != 0) {
				status = common.SafeAction(Common.o.getObject("elePolicySummary_CloseWS"), "YES", "ele");
			} else {
				status = common.SafeAction(Common.o.getObject("elePolicySummary_CancelWS"), "YES", "ele");
			}
			break;
		case "ACCOUNTSUMMARY_POLICYTERM":
			value = GetTextFromTableMultipleCondition(Common.o.getObject("eleAccountSummary_PolicyTermsTbl"), 1, 1, "PolicyCenter", 

"", "POLTERMSTBL");
			status = true;
			break;
		case "ACTIVITYPATTERNCLICK":
			status = common.ActionOnTable(Common.o.getObject("eleActivityPatterntbl"), 1, 1, "Automation_Activity", 

"Automation_Activity", "div");
			status = true;
			break;
		case "ACCOUNTSUMMARY_DRAFTSUBPOLICYNUM":
			value = GetTextFromTableMultipleCondition(Common.o.getObject("eleAccountSummary_PolicyTranTbl"), 2, 7, svalue[1], 

"Submission", "POLICYTRANSACTIONTBL");
			updateColumnNameValues.clear();
			whereConstraint.clear();
			updateColumnNameValues.put(PCConstants.PolicyNumber, value);
			whereConstraint.put(PCConstants.ID, tcID);
			sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);
			// Common.UpdateDataInExcel(strFileName, "Update Validation Set PolicyNumber='"
			// + value + "' where ID = '" + HTML.properties.getProperty("TCID") + "'");
			status = true;
			break;
		case "ACCOUNTSUMMARY_SYSTEM":
			value = "policycenter";
			updateColumnNameValues.clear();
			whereConstraint.clear();
			updateColumnNameValues.put(PCConstants.PCFieldValue, value);
			whereConstraint.put(PCConstants.PCFieldName, "AccountSummary_System");
			whereConstraint.put(PCConstants.ID, tcID);
			sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);
			// Common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
			// + value + "' where PCFieldName = 'AccountSummary_System' and ID = '" +
			// HTML.properties.getProperty("TCID") + "'");
			status = true;
			break;
		case "AP_BUSINESSSEGMENT":
			value = common.GetTextFromTable(Common.o.getObject("eleAP_BusinessSegment"), 0, 1);
			String Skilllevel = common.GetTextFromTable(Common.o.getObject("eleAP_BusinessSegment"), 0, 2);
			String Track = common.GetTextFromTable(Common.o.getObject("eleAP_BusinessSegment"), 0, 3);

			updateColumnNameValues.clear();
			whereConstraint.clear();
			updateColumnNameValues.put(PCConstants.PCFieldValue, value);
			whereConstraint.put(PCConstants.PCFieldName, "AP_BusinessSegment");
			whereConstraint.put(PCConstants.ID, tcID);
			sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);
			// Common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
			// + value + "' where PCFieldName = 'AP_BusinessSegment' and ID = '" +
			// HTML.properties.getProperty("TCID") + "'");

			updateColumnNameValues.clear();
			whereConstraint.clear();
			updateColumnNameValues.put(PCConstants.PCFieldValue, Skilllevel);
			whereConstraint.put(PCConstants.PCFieldName, "AP_SkillLevel");
			whereConstraint.put(PCConstants.ID, tcID);
			sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);
			// Common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
			// + Skilllevel + "' where PCFieldName = 'AP_SkillLevel' and ID = '" +
			// HTML.properties.getProperty("TCID") + "'");

			updateColumnNameValues.clear();
			whereConstraint.clear();
			updateColumnNameValues.put(PCConstants.PCFieldValue, Track);
			whereConstraint.put(PCConstants.PCFieldName, "AP_Track");
			whereConstraint.put(PCConstants.ID, tcID);
			sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);
			// Common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
			// + Track + "' where PCFieldName = 'AP_Track' and ID = '" +
			// HTML.properties.getProperty("TCID") + "'");
			status = true;
			break;
		case "ACCOUNT_CONTACTSTAB":
			status = AccountFileEditTable("Alternate Payer");
			break;
		case "ACCOUNT_CONTACTSDBATAB":
			status = AccountFileEditTable("Account Holder");
			break;
		case "ACCOUNT_MERGEDCONTACT":
			status = AccountFileEditTable("Merged Account Holder");
			break;
		case "PAGESCREENSHOT":
			// String path = SCRCommon.PageScreenShot();
			status = SCRCommon.PageScreenShot();
			// String path =
			// PCThreadCache.getInstance().getProperty(PCConstants.ALMScreenshotPath);
			status = true;
			break;
		case "ACCOUNTPOLICYNUMBERCLK":
			status = common.ActionOnTable(Common.o.getObject("eleAccountSummary_PolicyTermsTbl"), 6, 0, svalue[1], "a");
			break;
		case "POLICY_SELECTCONTACT":
			status = common.ActionOnTable(Common.o.getObject("elePolicy_Contacttbl"), 1, 0, svalue[1], "div");
			break;
		case "ACCOUNT_PRIMARYNAMEDINSURED":
			status = AccountFileEditTable("Named Insured");
			break;
		case "ACCOUNT_PRIMARYCONTACT":
			status = AccountFileEditTable("Primary Contact");
			break;
		case "AP_SEGMENTCODE":
			value = common.GetTextFromTable(Common.o.getObject("tblAP_SegmentCode"), 0, 1);
			updateColumnNameValues.clear();
			whereConstraint.clear();
			updateColumnNameValues.put(PCConstants.PCFieldValue, value);
			whereConstraint.put(PCConstants.PCFieldName, "AP_SegmentCode");
			whereConstraint.put(PCConstants.ID, tcID);
			sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);
			// Common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
			// + value + "' where PCFieldName = 'AP_SegmentCode' and ID = '" +
			// HTML.properties.getProperty("TCID") + "'");
			status = true;
			break;
		case "TRANTYPE_SELECT":
			status = common.ActionOnTable(Common.o.getObject("elePolTrantbl"), 1, 1, svalue[1], "div");
			break;
		case "QUOTE_SEQ_NUMBER":
			status = UpdateGivenPCStandardValue("QUOTE_SEQ_NUMBER");
			break;
		case "POLICYSUMMARY_TRANSACTION":
			value = GetTextFromTableMultipleCondition(Common.o.getObject("eleCompPolTrantbl"), 3, 3, svalue[1], svalue[1], 

"POLICYTRANSACTION");
			status = true;
			break;
		case "ACCOUNTSUMMARY_POLICYTRANDET":
			value = GetTextFromTableMultipleCondition(Common.o.getObject("eleAccountSummary_PolicyTranTbl"), 2, 7, svalue[1], svalue

[2], "POLICYTRANSACTIONTBL");
			status = true;
			break;
		case "USER_BUSINESSSEGMENT":
			String TrackBusinessSegment = common.GetTextFromTable(Common.o.getObject("eleUser_Tracktbl"), 0, 1);
			String TrackLevel = common.GetTextFromTable(Common.o.getObject("eleUser_Tracktbl"), 0, 2);
			String SkillBusinessSegment = common.GetTextFromTable(Common.o.getObject("eleUser_Skilltbl"), 0, 1);
			String SkillLevel = common.GetTextFromTable(Common.o.getObject("eleUser_Skilltbl"), 0, 2);

			updateColumnNameValues.clear();
			whereConstraint.clear();
			updateColumnNameValues.put(PCConstants.PCFieldValue, TrackBusinessSegment);
			whereConstraint.put(PCConstants.PCFieldName, "User_TrackBusinessSegment");
			whereConstraint.put(PCConstants.ID, tcID);
			sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);
			// Common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
			// + value + "' where PCFieldName = 'AP_BusinessSegment' and ID = '" +
			// HTML.properties.getProperty("TCID") + "'");

			updateColumnNameValues.clear();
			whereConstraint.clear();
			updateColumnNameValues.put(PCConstants.PCFieldValue, TrackLevel);
			whereConstraint.put(PCConstants.PCFieldName, "User_TrackLevel");
			whereConstraint.put(PCConstants.ID, tcID);
			sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);
			// Common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
			// + Skilllevel + "' where PCFieldName = 'AP_SkillLevel' and ID = '" +
			// HTML.properties.getProperty("TCID") + "'");

			updateColumnNameValues.clear();
			whereConstraint.clear();
			updateColumnNameValues.put(PCConstants.PCFieldValue, SkillBusinessSegment);
			whereConstraint.put(PCConstants.PCFieldName, "User_SkillBusinessSegmentt");
			whereConstraint.put(PCConstants.ID, tcID);
			sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);
			// Common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
			// + value + "' where PCFieldName = 'AP_BusinessSegment' and ID = '" +
			// HTML.properties.getProperty("TCID") + "'");

			updateColumnNameValues.clear();
			whereConstraint.clear();
			updateColumnNameValues.put(PCConstants.PCFieldValue, SkillLevel);
			whereConstraint.put(PCConstants.PCFieldName, "User_SkillLevel");
			whereConstraint.put(PCConstants.ID, tcID);
			sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);
			// Common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
			// + Skilllevel + "' where PCFieldName = 'AP_SkillLevel' and ID = '" +
			// HTML.properties.getProperty("TCID") + "'");
			status = true;
			break;

		case "USER_ATTRIBUTE":
			String AttributeName = common.GetTextFromTable(Common.o.getObject("eleUser_AttributeTblSummary"), 0, 1);
			String AttributeType = common.GetTextFromTable(Common.o.getObject("eleUser_AttributeTblSummary"), 0, 3);
			updateColumnNameValues.clear();
			whereConstraint.clear();
			updateColumnNameValues.put(PCConstants.PCFieldValue, AttributeName);
			whereConstraint.put(PCConstants.PCFieldName, "User_AttributeName");
			whereConstraint.put(PCConstants.ID, tcID);
			sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);
			// Common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
			// + value + "' where PCFieldName = 'AP_BusinessSegment' and ID = '" +
			// HTML.properties.getProperty("TCID") + "'");

			updateColumnNameValues.clear();
			whereConstraint.clear();
			updateColumnNameValues.put(PCConstants.PCFieldValue, AttributeType);
			whereConstraint.put(PCConstants.PCFieldName, "User_AttributeType");
			whereConstraint.put(PCConstants.ID, tcID);
			sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);
			// Common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
			// + Skilllevel + "' where PCFieldName = 'AP_SkillLevel' and ID = '" +
			// HTML.properties.getProperty("TCID") + "'");
			status = true;
			break;

		case "CURRENTDATETIME":
			status = UpdateGivenPCStandardValue("CURRENTDATETIME");
			break;

		case "POLICYCOUNT":
			int iPolicyCnt = SCRCommon.TableRowCount(Common.o.getObject("eleAccountSummary_PolicyTranTbl"));
			updateColumnNameValues.clear();
			whereConstraint.clear();
			updateColumnNameValues.put(PCConstants.PCFieldValue, String.valueOf(iPolicyCnt));
			whereConstraint.put(PCConstants.PCFieldName, "NumberOfPolicies");
			whereConstraint.put(PCConstants.ID, tcID);
			sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);
			status = true;
			break;

		}
		// whereConstraint = null;
		// updateColumnNameValues = null;
		return status;
	}

	/**
	 * @function This function use to retrieve the data from Account Summary Tables
	 * @param obj
	 * @param readTextCol,readTextCol1,readTextCol2
	 * @param strReadString,strReadString1
	 * @return Policy #
	 * @throws Exception
	 */
	public String GetTextFromTableMultipleCondition(By obj, int readTextCol, int readTextCol1, String strReadString, String strReadString1, 

String strinput) throws Exception {
		String policynum = null;
		String policyStatus = null;
		String policyType = null;
		String value = null;
		XlsxReader sXL = XlsxReader.getInstance();
		HashMap<String, Object> whereConstraint = new HashMap<String, Object>();
		HashMap<String, Object> updateColumnNameValues = new HashMap<String, Object>();
		String tcID = PCThreadCache.getInstance().getProperty("TCID");
		WebElement mytable = common.returnObject(obj);
		List<WebElement> allrows = mytable.findElements(By.tagName("tr"));
		switch (strinput) {
		case "POLICYTRANSACTIONTBL": {
			for (int i = 0; i <= allrows.size() - 1; i++) {
				List<WebElement> Cells = allrows.get(i).findElements(By.tagName("td"));
				if (Cells.get(readTextCol).getText().contains(strReadString) && Cells.get(readTextCol1).getText().contains

(strReadString1)) {
					logger.info("Search String'" + strReadString + "' and '" + strReadString1 + "' available in the table.");
					// HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
					// PCThreadCache.getInstance().getProperty("methodName"), "System should search
					// string in table and Search string is '" + strReadString + "' and '" +
					// strReadString1 + "'" ,"System searched string in table and search string is
					// '" + strReadString + "' and '" + strReadString1 + "'", "PASS");
					policynum = Cells.get(4).getText();
					// policynum = PolicyNumber.substring(PolicyNumber.length()-6);
					policyStatus = Cells.get(2).getText();
					policyType = Cells.get(7).getText();
					String policyJobNumber = Cells.get(1).getText();
					;

					updateColumnNameValues.clear();
					whereConstraint.clear();
					updateColumnNameValues.put(PCConstants.PCFieldValue, policynum);
					whereConstraint.put(PCConstants.PCFieldName, "AccountSummary_PolicyNumber");
					whereConstraint.put(PCConstants.ID, tcID);
					sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);

					updateColumnNameValues.clear();
					whereConstraint.clear();
					updateColumnNameValues.put(PCConstants.PCFieldValue, policyStatus);
					whereConstraint.put(PCConstants.PCFieldName, "AccountSummary_PolicyStatus");
					whereConstraint.put(PCConstants.ID, tcID);
					sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);

					updateColumnNameValues.clear();
					whereConstraint.clear();
					updateColumnNameValues.put(PCConstants.PCFieldValue, policyType);
					whereConstraint.put(PCConstants.PCFieldName, "AccountSummary_PolicyType");
					whereConstraint.put(PCConstants.ID, tcID);
					sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);

					updateColumnNameValues.clear();
					whereConstraint.clear();
					updateColumnNameValues.put(PCConstants.PCFieldValue, policyJobNumber);
					whereConstraint.put(PCConstants.PCFieldName, "AccountSummary_PolicyJobNumber");
					whereConstraint.put(PCConstants.ID, tcID);
					sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);

					// common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
					// + policynum + "' where PCFieldName = 'AccountSummary_PolicyNumber' and ID =
					// '" + PCThreadCache.getInstance().getProperty("TCID") + "'");
					// common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
					// + policyStatus + "' where PCFieldName = 'AccountSummary_PolicyStatus' and ID
					// = '" + PCThreadCache.getInstance().getProperty("TCID") + "'");
					// common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
					// + policyType + "' where PCFieldName = 'AccountSummary_PolicyType' and ID = '"
					// + PCThreadCache.getInstance().getProperty("TCID") + "'");
					// common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
					// + policyJobNumber + "' where PCFieldName = 'AccountSummary_PolicyJobNumber'
					// and ID = '" + PCThreadCache.getInstance().getProperty("TCID") + "'");
				}

			}
			break;
		}
		case "ACTIVITIESTBL": {
			if (allrows.size() > 0) {
				for (int i = 0; i <= allrows.size() - 1; i++) {
					List<WebElement> Cells = allrows.get(i).findElements(By.tagName("td"));

					logger.info("Activity table is available");
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Activity table should be available", "Activity table is available", "PASS");
					String ActivityDesc = Cells.get(2).getText();
					updateColumnNameValues.clear();
					whereConstraint.clear();
					updateColumnNameValues.put(PCConstants.PCFieldValue, ActivityDesc);
					whereConstraint.put(PCConstants.PCFieldName, "AccountSummary_ActivityDesc");
					whereConstraint.put(PCConstants.ID, tcID);
					sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);

					// common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
					// + ActivityDesc + "' where PCFieldName = 'AccountSummary_ActivityDesc' and ID
					// = '" + PCThreadCache.getInstance().getProperty("TCID") + "'");
					value = common.ReadElementGetAttribute(Common.o.getObject("lstActivity_Status"), "value", 

Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
					if (value.toUpperCase().contains("OPEN")) {
						updateColumnNameValues.clear();
						whereConstraint.clear();
						updateColumnNameValues.put(PCConstants.PCFieldValue, "Open");
						whereConstraint.put(PCConstants.PCFieldName, "AccountSummary_ActivityStatus");
						whereConstraint.put(PCConstants.ID, tcID);
						sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);
						// common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue
						// ='Open' where PCFieldName = 'AccountSummary_ActivityStatus' and ID = '" +
						// PCThreadCache.getInstance().getProperty("TCID") + "'");
					} else if (value.toUpperCase().contains("CLOSED")) {
						updateColumnNameValues.clear();
						whereConstraint.clear();
						updateColumnNameValues.put(PCConstants.PCFieldValue, "Closed");
						whereConstraint.put(PCConstants.PCFieldName, "AccountSummary_ActivityStatus");
						whereConstraint.put(PCConstants.ID, tcID);
						sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);
						// common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue
						// ='Closed' where PCFieldName = 'AccountSummary_ActivityStatus' and ID = '" +
						// PCThreadCache.getInstance().getProperty("TCID") + "'");
					}

					policynum = "123456";
				}
			} else {
				logger.info("Search String not available in the table. '" + strinput + "'");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should search string in table and Search string is '" + strinput + "'", "System searched string in table and search string is  '" + strinput + "'", "FAIL");
			}
			break;
		}
		case "POLACTIVITIESTBL": {
			if (allrows.size() > 0) {
				for (int i = 0; i <= allrows.size() - 1; i++) {
					List<WebElement> Cells = allrows.get(i).findElements(By.tagName("td"));

					logger.info("Activity table is available");
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Activity table should be available", "Activity table is available", "PASS");
					String ActivityDesc = Cells.get(2).getText();
					updateColumnNameValues.clear();
					whereConstraint.clear();
					updateColumnNameValues.put(PCConstants.PCFieldValue, ActivityDesc);
					whereConstraint.put(PCConstants.PCFieldName, "PolicySummary_ActivityDesc");
					whereConstraint.put(PCConstants.ID, tcID);
					sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);

					updateColumnNameValues.clear();
					whereConstraint.clear();
					updateColumnNameValues.put(PCConstants.PCFieldValue, "Open");
					whereConstraint.put(PCConstants.PCFieldName, "PolicySummary_ActivityStatus");
					whereConstraint.put(PCConstants.ID, tcID);
					sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);

					// common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
					// + ActivityDesc + "' where PCFieldName = 'PolicySummary_ActivityDesc' and ID =
					// '" + PCThreadCache.getInstance().getProperty("TCID") + "'");
					// common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue
					// ='Open' where PCFieldName = 'PolicySummary_ActivityStatus' and ID = '" +
					// PCThreadCache.getInstance().getProperty("TCID") + "'");
					policynum = "123456";
				}
				break;
			}
		}
		case "POLTERMSTBL": {
			if (allrows.size() > 0) {
				for (int i = 0; i <= allrows.size() - 1; i++) {
					List<WebElement> Cells = allrows.get(i).findElements(By.tagName("td"));

					logger.info("Policy Terms table is available");
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Policy Terms table should be available", "Policy Terms table is available", "PASS");
					String strPolicySystem = Cells.get(1).getText();
					String strEffDate = Cells.get(7).getText();
					String strExpDate = Cells.get(8).getText();

					updateColumnNameValues.clear();
					whereConstraint.clear();
					updateColumnNameValues.put(PCConstants.PCFieldValue, strPolicySystem);
					whereConstraint.put(PCConstants.PCFieldName, "AccountSummary_PolicySystem");
					whereConstraint.put(PCConstants.ID, tcID);
					sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);

					updateColumnNameValues.clear();
					whereConstraint.clear();
					updateColumnNameValues.put(PCConstants.PCFieldValue, strEffDate);
					whereConstraint.put(PCConstants.PCFieldName, "AccountSummary_PolicyEffectiveDate");
					whereConstraint.put(PCConstants.ID, tcID);
					sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);

					updateColumnNameValues.clear();
					whereConstraint.clear();
					updateColumnNameValues.put(PCConstants.PCFieldValue, strExpDate);
					whereConstraint.put(PCConstants.PCFieldName, "AccountSummary_PolicyExpirationDate");
					whereConstraint.put(PCConstants.ID, tcID);
					sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);

					// common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
					// + strPolicySystem + "' where PCFieldName = 'AccountSummary_PolicySystem' and
					// ID = '" + PCThreadCache.getInstance().getProperty("TCID") + "'");
					// common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
					// + strEffDate + "' where PCFieldName = 'AccountSummary_PolicyEffectiveDate'
					// and ID = '" + PCThreadCache.getInstance().getProperty("TCID") + "'");
					// common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
					// + strExpDate + "' where PCFieldName = 'AccountSummary_PolicyExpirationDate'
					// and ID = '" + PCThreadCache.getInstance().getProperty("TCID") + "'");
					policynum = "123456";
					break;
				}
			}
			break;
		}

		case "POLICYTRANSACTION": {
			if (allrows.size() > 0) {
				for (int i = 0; i <= allrows.size() - 1; i++) {
					List<WebElement> Cells = allrows.get(i).findElements(By.tagName("td"));

					logger.info("Policy Transaction table is available");
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Policy Terms table should be available", "Policy Terms table is available", "PASS");
					String strPolicyType = Cells.get(3).getText();
					String strTransaction = Cells.get(4).getText();

					updateColumnNameValues.clear();
					whereConstraint.clear();
					updateColumnNameValues.put(PCConstants.PCFieldValue, strPolicyType);
					whereConstraint.put(PCConstants.PCFieldName, "PolicySummary_TranType");
					whereConstraint.put(PCConstants.ID, tcID);
					sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);

					updateColumnNameValues.clear();
					whereConstraint.clear();
					updateColumnNameValues.put(PCConstants.PCFieldValue, strTransaction);
					whereConstraint.put(PCConstants.PCFieldName, "PolicySummary_TranNum");
					whereConstraint.put(PCConstants.ID, tcID);
					sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);

					// common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
					// + strPolicySystem + "' where PCFieldName = 'AccountSummary_PolicySystem' and
					// ID = '" + PCThreadCache.getInstance().getProperty("TCID") + "'");
					// common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
					// + strEffDate + "' where PCFieldName = 'AccountSummary_PolicyEffectiveDate'
					// and ID = '" + PCThreadCache.getInstance().getProperty("TCID") + "'");
					// common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
					// + strExpDate + "' where PCFieldName = 'AccountSummary_PolicyExpirationDate'
					// and ID = '" + PCThreadCache.getInstance().getProperty("TCID") + "'");
					policynum = strTransaction;
					break;
				}
			}
			break;
		}
		}
		// whereConstraint = null;
		// updateColumnNameValues = null;
		return policynum;
	}

	public boolean AccountFileEditTable(String iprolename) throws Exception {
		boolean status = true;
		XlsxReader sXL = XlsxReader.getInstance();
		HashMap<String, Object> whereConstraint = new HashMap<String, Object>();
		HashMap<String, Object> updateColumnNameValues = new HashMap<String, Object>();
		String tcID = PCThreadCache.getInstance().getProperty("TCID");
		common.SafeAction(Common.o.getObject("accFileContactRoleTable"), "", "ele");
		WebElement tableRowXpathRole = driver.findElement(Common.o.getObject("accFileContactRoleTable"));
		List<WebElement> rows_table1 = tableRowXpathRole.findElements(By.tagName("tr"));
		int rows_count1 = rows_table1.size();
		for (int i = 1; i <= rows_count1; i++) {
			String roleName = driver.findElement(By.xpath(".//*[@id='AccountFile_Contacts:AccountFile_ContactsScreen:AccountContactsLV-body']/div/table/tbody/tr[" + i + "]/td[4]")).getText();
			switch (iprolename) {
			case "Account Holder": {
				if (roleName.contains(iprolename)) {
					driver.findElement(By.xpath(".//*[@id='AccountFile_Contacts:AccountFile_ContactsScreen:AccountContactsLV-body']/div/table/tbody/tr[" + i + "]/td[4]")).click();
					status = common.SafeAction(Common.o.getObject("eleContactDBATab"), "YES", "ele");
					String value = common.GetTextFromTable(Common.o.getObject("eleContacts_DBATable"), 0, 2);

					updateColumnNameValues.clear();
					whereConstraint.clear();
					updateColumnNameValues.put(PCConstants.PCFieldValue, value);
					whereConstraint.put(PCConstants.PCFieldName, "Contact_DBAName");
					whereConstraint.put(PCConstants.ID, tcID);
					sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);

					updateColumnNameValues.clear();
					whereConstraint.clear();
					updateColumnNameValues.put(PCConstants.PCFieldValue, "Account Contact DBA Name");
					whereConstraint.put(PCConstants.PCFieldName, "Contact_DBACode");
					whereConstraint.put(PCConstants.ID, tcID);
					sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);

					// common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
					// + value + "' where PCFieldName = 'Contact_DBAName' and ID = '" +
					// PCThreadCache.getInstance().getProperty("TCID") + "'");
					// common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue
					// ='DBA' where PCFieldName = 'Contact_DBACode' and ID = '" +
					// PCThreadCache.getInstance().getProperty("TCID") + "'");
					status = true;
					break;
				}
			}
			case "Alternate Payer": {
				if (roleName.contains(iprolename)) {
					driver.findElement(By.xpath(".//*[@id='AccountFile_Contacts:AccountFile_ContactsScreen:AccountContactsLV-body']/div/table/tbody/tr[" + i + "]/td[3]")).click();
					String strAddressLine = driver.findElement(Common.o.getObject("eleAccountContactAddress")).getText();
					String strCity = driver.findElement(Common.o.getObject("eleAccountContactCity")).getText();
					String strState = driver.findElement(Common.o.getObject("eleAccountContactState")).getText();
					String strZip = driver.findElement(Common.o.getObject("eleAccountContactZip")).getText();

					updateColumnNameValues.clear();
					whereConstraint.clear();
					updateColumnNameValues.put(PCConstants.PCFieldValue, strAddressLine);
					whereConstraint.put(PCConstants.PCFieldName, "AlternatePayer_AddressLine");
					whereConstraint.put(PCConstants.ID, tcID);
					sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);

					updateColumnNameValues.clear();
					whereConstraint.clear();
					updateColumnNameValues.put(PCConstants.PCFieldValue, strCity);
					whereConstraint.put(PCConstants.PCFieldName, "AlternatePayer_City");
					whereConstraint.put(PCConstants.ID, tcID);
					sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);

					updateColumnNameValues.clear();
					whereConstraint.clear();
					updateColumnNameValues.put(PCConstants.PCFieldValue, strState);
					whereConstraint.put(PCConstants.PCFieldName, "AlternatePayer_State");
					whereConstraint.put(PCConstants.ID, tcID);
					sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);

					updateColumnNameValues.clear();
					whereConstraint.clear();
					updateColumnNameValues.put(PCConstants.PCFieldValue, strZip);
					whereConstraint.put(PCConstants.PCFieldName, "AlternatePayer_Zip");
					whereConstraint.put(PCConstants.ID, tcID);
					sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);
					// common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
					// + strAddressLine + "' where PCFieldName = 'AlternatePayer_AddressLine' and ID
					// = '" + PCThreadCache.getInstance().getProperty("TCID") + "'");
					// common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
					// + strCity + "' where PCFieldName = 'AlternatePayer_City' and ID = '" +
					// PCThreadCache.getInstance().getProperty("TCID") + "'");
					// common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
					// + strState + "' where PCFieldName = 'AlternatePayer_State' and ID = '" +
					// PCThreadCache.getInstance().getProperty("TCID") + "'");
					// common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
					// + strZip + "' where PCFieldName = 'AlternatePayer_Zip' and ID = '" +
					// PCThreadCache.getInstance().getProperty("TCID") + "'");
					status = common.SafeAction(Common.o.getObject("eleCancel"), "YES", "ele");
					status = true;
					break;
				}
			}
			case "Named Insured": {
				if (roleName.contains(iprolename)) {
					driver.findElement(By.xpath(".//*[@id='AccountFile_Contacts:AccountFile_ContactsScreen:AccountContactsLV-body']/div/table/tbody/tr[" + i + "]/td[3]")).click();
					String strPhone = driver.findElement(Common.o.getObject("eleAccountContactPhone")).getAttribute("value");
					String strFax = driver.findElement(Common.o.getObject("eleAccountContactFax")).getAttribute("value");
					String strPrimaryEmail = driver.findElement(Common.o.getObject("eleAccountContactPrimaryEmail")).getAttribute("value");
					String strSecondaryEmail = driver.findElement(Common.o.getObject("eleAccountContactSecondaryEmail")).getAttribute("value");
					String strCountry = driver.findElement(Common.o.getObject("eleAccountContactCountry")).getText();
					String strCounty = driver.findElement(Common.o.getObject("eleAccountContactCounty")).getText();
					String strAddressLine = driver.findElement(Common.o.getObject("eleAccountContactAddress")).getText();
					String strCity = driver.findElement(Common.o.getObject("eleAccountContactCity")).getText();
					String strState = driver.findElement(Common.o.getObject("eleAccountContactState")).getText();
					String strZip = driver.findElement(Common.o.getObject("eleAccountContactZip")).getText();

					updateColumnNameValues.clear();
					whereConstraint.clear();
					updateColumnNameValues.put(PCConstants.PCFieldValue, strPhone);
					whereConstraint.put(PCConstants.PCFieldName, "PrimaryContact_PhoneNumber");
					whereConstraint.put(PCConstants.ID, tcID);
					sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);

					updateColumnNameValues.clear();
					whereConstraint.clear();
					updateColumnNameValues.put(PCConstants.PCFieldValue, strFax);
					whereConstraint.put(PCConstants.PCFieldName, "PrimaryContact_FaxNumber");
					whereConstraint.put(PCConstants.ID, tcID);
					sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);

					updateColumnNameValues.clear();
					whereConstraint.clear();
					updateColumnNameValues.put(PCConstants.PCFieldValue, strPrimaryEmail);
					whereConstraint.put(PCConstants.PCFieldName, "PrimaryContact_PrimaryEmailAddress");
					whereConstraint.put(PCConstants.ID, tcID);
					sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);

					updateColumnNameValues.clear();
					whereConstraint.clear();
					updateColumnNameValues.put(PCConstants.PCFieldValue, strSecondaryEmail);
					whereConstraint.put(PCConstants.PCFieldName, "PrimaryContact_SecondaryEmailAddress");
					whereConstraint.put(PCConstants.ID, tcID);
					sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);

					updateColumnNameValues.clear();
					whereConstraint.clear();
					updateColumnNameValues.put(PCConstants.PCFieldValue, strCountry);
					whereConstraint.put(PCConstants.PCFieldName, "PrimaryContact_Country");
					whereConstraint.put(PCConstants.ID, tcID);
					sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);

					updateColumnNameValues.clear();
					whereConstraint.clear();
					updateColumnNameValues.put(PCConstants.PCFieldValue, strCounty);
					whereConstraint.put(PCConstants.PCFieldName, "PrimaryContact_County");
					whereConstraint.put(PCConstants.ID, tcID);
					sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);

					updateColumnNameValues.clear();
					whereConstraint.clear();
					updateColumnNameValues.put(PCConstants.PCFieldValue, strAddressLine);
					whereConstraint.put(PCConstants.PCFieldName, "PrimaryContact_AddressLine");
					whereConstraint.put(PCConstants.ID, tcID);
					sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);

					updateColumnNameValues.clear();
					whereConstraint.clear();
					updateColumnNameValues.put(PCConstants.PCFieldValue, strCity);
					whereConstraint.put(PCConstants.PCFieldName, "PrimaryContact_City");
					whereConstraint.put(PCConstants.ID, tcID);
					sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);

					updateColumnNameValues.clear();
					whereConstraint.clear();
					updateColumnNameValues.put(PCConstants.PCFieldValue, strState);
					whereConstraint.put(PCConstants.PCFieldName, "PrimaryContact_State");
					whereConstraint.put(PCConstants.ID, tcID);
					sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);

					updateColumnNameValues.clear();
					whereConstraint.clear();
					updateColumnNameValues.put(PCConstants.PCFieldValue, strZip);
					whereConstraint.put(PCConstants.PCFieldName, "PrimaryContact_Zip");
					whereConstraint.put(PCConstants.ID, tcID);
					sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);
					// common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
					// + strPhone + "' where PCFieldName = 'PrimaryContact_PhoneNumber' and ID = '"
					// + PCThreadCache.getInstance().getProperty("TCID") + "'");
					// common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
					// + strFax + "' where PCFieldName = 'PrimaryContact_FaxNumber' and ID = '" +
					// PCThreadCache.getInstance().getProperty("TCID") + "'");
					// common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
					// + strPrimaryEmail + "' where PCFieldName =
					// 'PrimaryContact_PrimaryEmailAddress' and ID = '" +
					// PCThreadCache.getInstance().getProperty("TCID") + "'");
					// common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
					// + strSecondaryEmail + "' where PCFieldName =
					// 'PrimaryContact_SecondaryEmailAddress' and ID = '" +
					// PCThreadCache.getInstance().getProperty("TCID") + "'");
					// common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
					// + strCountry + "' where PCFieldName = 'PrimaryContact_Country' and ID = '" +
					// PCThreadCache.getInstance().getProperty("TCID") + "'");
					// common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
					// + strCounty + "' where PCFieldName = 'PrimaryContact_County' and ID = '" +
					// PCThreadCache.getInstance().getProperty("TCID") + "'");
					// common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
					// + strAddressLine + "' where PCFieldName = 'PrimaryContact_AddressLine' and ID
					// = '" + PCThreadCache.getInstance().getProperty("TCID") + "'");
					// common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
					// + strCity + "' where PCFieldName = 'PrimaryContact_City' and ID = '" +
					// PCThreadCache.getInstance().getProperty("TCID") + "'");
					// common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
					// + strState + "' where PCFieldName = 'PrimaryContact_State' and ID = '" +
					// PCThreadCache.getInstance().getProperty("TCID") + "'");
					// common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
					// + strZip + "' where PCFieldName = 'PrimaryContact_Zip' and ID = '" +
					// PCThreadCache.getInstance().getProperty("TCID") + "'");
					status = common.SafeAction(Common.o.getObject("eleCancel"), "YES", "ele");
					status = true;
					break;
				}

			}

			case "Merged Account Holder": {
				if (roleName.contains(iprolename)) {
					String Name = driver.findElement(By.xpath(".//*[@id='AccountFile_Contacts:AccountFile_ContactsScreen:AccountContactsLV-body']/div/table/tbody/tr[" + i + "]/td[3]")).getText();

					updateColumnNameValues.clear();
					whereConstraint.clear();
					updateColumnNameValues.put(PCConstants.PCFieldValue, Name);
					whereConstraint.put(PCConstants.PCFieldName, "Contact_Name");
					whereConstraint.put(PCConstants.ID, tcID);
					sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);

					// common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
					// + Name + "' where PCFieldName = 'Contact_Name' and ID = '" +
					// PCThreadCache.getInstance().getProperty("TCID") + "'");
					status = true;
					break;
				}

			}
			case "Primary Contact": {
				if (roleName.contains(iprolename)) {
					driver.findElement(By.xpath(".//*[@id='AccountFile_Contacts:AccountFile_ContactsScreen:AccountContactsLV-body']/div/table/tbody/tr[" + i + "]/td[3]")).click();

					String strConFirstName = common.ReadElementGetAttribute(Common.o.getObject("edtContactFirstName"), "value", Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
					String strConLastName = common.ReadElementGetAttribute(Common.o.getObject("edtContactLasttName"),"value", Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));

					updateColumnNameValues.clear();
					whereConstraint.clear();
					updateColumnNameValues.put(PCConstants.PCFieldValue, strConFirstName);
					whereConstraint.put(PCConstants.PCFieldName, "Contact_FirstName");
					whereConstraint.put(PCConstants.ID, tcID);
					sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);

					updateColumnNameValues.clear();
					whereConstraint.clear();
					updateColumnNameValues.put(PCConstants.PCFieldValue, strConLastName);
					whereConstraint.put(PCConstants.PCFieldName, "Contact_LastName");
					whereConstraint.put(PCConstants.ID, tcID);
					sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);

					updateColumnNameValues.clear();
					whereConstraint.clear();
					updateColumnNameValues.put(PCConstants.PCFieldValue, "YES");
					whereConstraint.put(PCConstants.PCFieldName, "Contact_Activation_Ind");
					whereConstraint.put(PCConstants.ID, tcID);
					sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);

					updateColumnNameValues.clear();
					whereConstraint.clear();
					updateColumnNameValues.put(PCConstants.PCFieldValue, "Work Phone");
					whereConstraint.put(PCConstants.PCFieldName, "Contact_Prim_Telephone_Desc");
					whereConstraint.put(PCConstants.ID, tcID);
					sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);

					status = true;
					break;
				}
			}
			/*
			 * for(int i=1;i<=rows_count1; i++) { String roleName =
			 * driver.findElement(By.xpath(
			 * ".//*[@id='AccountFile_Contacts:AccountFile_ContactsScreen:AccountContactsLV-body']/div/table/tbody/tr["
			 * +i+"]/td[4]")).getText(); if(roleName.contains(iprolename)) { String Name =
			 * driver.findElement(By.xpath(
			 * ".//*[@id='AccountFile_Contacts:AccountFile_ContactsScreen:AccountContactsLV-body']/div/table/tbody/tr["
			 * +i+"]/td[3]")).getText(); String[] SplitName = Name.split(" ");
			 * Common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
			 * + SplitName[0] + "' where PCFieldName = 'Contact_FirstName' and ID = '" +
			 * PCThreadCache.getInstance().getProperty("TCID") + "'");
			 * Common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
			 * + SplitName[1] + "' where PCFieldName = 'Contact_LastName' and ID = '" +
			 * PCThreadCache.getInstance().getProperty("TCID") + "'");
			 * Common.UpdateDataInExcel(strFileName,
			 * "Update Validation Set PCFieldValue ='Work Phone' where PCFieldName = 'Contact_Prim_Telephone_Desc' and ID = '"
			 * + PCThreadCache.getInstance().getProperty("TCID") + "'");
			 * Common.UpdateDataInExcel(strFileName,
			 * "Update Validation Set PCFieldValue ='YES' where PCFieldName = 'Contact_Activation_Ind' and ID = '"
			 * + PCThreadCache.getInstance().getProperty("TCID") + "'"); status = true;
			 * break; } else if(roleName.contains(iprolename)) {
			 * driver.findElement(By.xpath(
			 * ".//*[@id='AccountFile_Contacts:AccountFile_ContactsScreen:AccountContactsLV-body']/div/table/tbody/tr["
			 * +i+"]/td[4]")).click(); status =
			 * Common.SafeAction(Common.o.getObject("eleContactDBATab"), "YES","ele");
			 * String value =
			 * Common.GetTextFromTable(Common.o.getObject("eleContacts_DBATable"),0,3);
			 * Common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue ='"
			 * + value + "' where PCFieldName = 'Contact_DBAName' and ID = '" +
			 * PCThreadCache.getInstance().getProperty("TCID") + "'");
			 * Common.UpdateDataInExcel(strFileName,
			 * "Update Validation Set PCFieldValue ='DBA' where PCFieldName = 'Contact_DBACode' and ID = '"
			 * + PCThreadCache.getInstance().getProperty("TCID") + "'"); status = true;
			 * break; } else if(roleName.contains(iprolename)) { String Name =
			 * driver.findElement(By.xpath(
			 * ".//*[@id='AccountFile_Contacts:AccountFile_ContactsScreen:AccountContactsLV-body']/div/table/tbody/tr["
			 * +i+"]/td[3]")).getText(); Common.UpdateDataInExcel(strFileName,
			 * "Update Validation Set PCFieldValue ='" + Name +
			 * "' where PCFieldName = 'Contact_Name' and ID = '" +
			 * PCThreadCache.getInstance().getProperty("TCID") + "'"); status = true; break;
			 * }
			 * 
			 * }
			 */
			// whereConstraint = null;
			// updateColumnNameValues = null;

			}
		}
		return status;
	}

	/**
	 * @function to update standard value in excel
	 * @param funValue
	 * @return status
	 * @throws Exception
	 **/
	public boolean UpdateGivenPCStandardValue(String funValue) throws Exception {
		boolean status = false;
		String value = null;
		XlsxReader sXL = XlsxReader.getInstance();
		HashMap<String, Object> whereConstraint = new HashMap<String, Object>();
		HashMap<String, Object> updateColumnNameValues = new HashMap<String, Object>();
		String tcID = PCThreadCache.getInstance().getProperty("TCID");
		String[] svalue = funValue.split("-");
		switch (svalue[0].toUpperCase()) {
		case "QUOTE_SEQ_NUMBER":
			value = common.ReadElement(Common.o.getObject("eleCLAPolicyStatus"), 2000);
			if (value.equalsIgnoreCase("Draft")) {
				updateColumnNameValues.clear();
				whereConstraint.clear();
				updateColumnNameValues.put(PCConstants.PCFieldValue, "");
				whereConstraint.put(PCConstants.PCFieldName, "Quote_Sequence_Number");
				whereConstraint.put(PCConstants.ID, tcID);
				sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);
				// Common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue =''
				// where PCFieldName = 'Quote_Sequence_Number' and ID = '" +
				// HTML.properties.getProperty("TCID") + "'");
				status = true;
				break;
			} else if (value.equalsIgnoreCase("Quoted")) {
				updateColumnNameValues.clear();
				whereConstraint.clear();
				updateColumnNameValues.put(PCConstants.PCFieldValue, "001");
				whereConstraint.put(PCConstants.PCFieldName, "Quote_Sequence_Number");
				whereConstraint.put(PCConstants.ID, tcID);
				sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);
				// Common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue
				// ='001' where PCFieldName = 'Quote_Sequence_Number' and ID = '" +
				// HTML.properties.getProperty("TCID") + "'");
				status = true;
				break;
			}
		case "CURRENTDATETIME":
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			System.out.println(dateFormat.format(date));
			updateColumnNameValues.clear();
			whereConstraint.clear();
			updateColumnNameValues.put(PCConstants.PCFieldValue, dateFormat.format(date));
			whereConstraint.put(PCConstants.PCFieldName, "UpdateTimeStamp");
			whereConstraint.put(PCConstants.ID, tcID);
			sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION, updateColumnNameValues, whereConstraint);
			// Common.UpdateDataInExcel(strFileName, "Update Validation Set PCFieldValue =''
			// where PCFieldName = 'Quote_Sequence_Number' and ID = '" +
			// HTML.properties.getProperty("TCID") + "'");
			status = true;
			break;

		}
		// whereConstraint = null;
		// updateColumnNameValues = null;
		return status;
	}
}