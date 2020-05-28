

package com.pc.screen;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

public class ODS {

	public static String sheetname = "ODS";
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();

	public Boolean SCRODS() throws Exception {
		Boolean status = true;
		if (HTML.properties.getProperty("EXECUTIONAPP").contains("ODS")) {
			status = common.ClassComponent(sheetname, Common.o);
			if (!status) {
				logger.info("ODS Component was not Successfully Executed");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Execute ODS Component", "ODS Component was not Successfully Executed", "FAIL");
				status = true;
			} else {
				logger.info("ODS Component was Successfully executed");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Execute ODS Component", "ODS Component was Successfully Executed", "PASS");
			}
		}
		return status;
	}

	public Boolean DBValidation() throws Exception {
		Boolean Status = false;
		Status = GetDataFromDB();
		return Status;
	}

	public Boolean GetDataFromDB() throws Exception {
		Boolean Status = false;
		Boolean sMappingFound = false;
		Boolean sTranformationLogicFound = false;
		Boolean sDBAliasName = false;
		String Schema = null;
		String URL = null;
		String UserName = null;
		String Password = null;
		String sPCCompareValue = null;
		ResultSet resultSet = null;
		String strAccountNumber = null;
		String strPolicyNumber = null;
		String strGroupName = null;
		String strQueueName = null;
		String strUserFirstName = null;
		String strUserLastName = null;
		String strOrganizationName = null;
		String strActivityID = null;
		String strRewritepolicy = null;
		String strRoleName = null;
		XlsxReader sXL = XlsxReader.getInstance();
		SCRCommon scrCommon = new SCRCommon();
		HashMap<String, Object> whereConstraint = new HashMap<String, Object>();
		// HashMap<String,Object> updateColumnNameValues = new HashMap<String,Object>();
		ArrayList<HashMap<String, Object>> resultListRows = new ArrayList<HashMap<String, Object>>();
		ArrayList<HashMap<String, Object>> resultListRows1 = new ArrayList<HashMap<String, Object>>();
		ArrayList<HashMap<String, Object>> resultListRows2 = new ArrayList<HashMap<String, Object>>();
		switch (HTML.properties.getProperty("Region").toUpperCase()) {
		case "QA":
			Schema = HTML.properties.getProperty("QAODSSchema");
			URL = HTML.properties.getProperty("QAURL");
			UserName = HTML.properties.getProperty("QAMDMODSUserID");
			Password = HTML.properties.getProperty("QAMDMODSPassword");
			break;
		case "INT":
			Schema = HTML.properties.getProperty("INTODSSchema");
			URL = HTML.properties.getProperty("INTURL");

			UserName = HTML.properties.getProperty("INTMDMODSUserID");
			Password = HTML.properties.getProperty("INTMDMODSPassword");
			break;
		case "LTQ":
			Schema = HTML.properties.getProperty("LTQODSSchema");
			URL = HTML.properties.getProperty("INTURL");

			UserName = HTML.properties.getProperty("INTMDMODSUserID");
			Password = HTML.properties.getProperty("INTMDMODSPassword");
			break;
		case "LTI":
			Schema = HTML.properties.getProperty("LTIODSSchema");
			URL = HTML.properties.getProperty("INTURL");

			UserName = HTML.properties.getProperty("INTMDMODSUserID");
			Password = HTML.properties.getProperty("INTMDMODSPassword");
			break;
		case "LTA":
			Schema = HTML.properties.getProperty("LTAODSSchema");
			URL = HTML.properties.getProperty("INTURL");

			UserName = HTML.properties.getProperty("INTMDMODSUserID");
			Password = HTML.properties.getProperty("INTMDMODSPassword");
			break;
		case "QA02":
			Schema = HTML.properties.getProperty("QA02ODSSchema");
			URL = HTML.properties.getProperty("QAURL");

			UserName = HTML.properties.getProperty("QAMDMODSUserID");
			Password = HTML.properties.getProperty("QAMDMODSPassword");
			break;
		case "DLTQ2":
			Schema = HTML.properties.getProperty("LTQ02ODSSchema");
			URL = HTML.properties.getProperty("INTURL");

			UserName = HTML.properties.getProperty("INTMDMODSUserID");
			Password = HTML.properties.getProperty("INTMDMODSPassword");
			break;
		case "DLTI2":
			Schema = HTML.properties.getProperty("LTI02ODSSchema");
			URL = HTML.properties.getProperty("INTURL");

			UserName = HTML.properties.getProperty("INTMDMODSUserID");
			Password = HTML.properties.getProperty("INTMDMODSPassword");
			break;
		}
		whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
		resultListRows = sXL.executeSelectQuery(PCConstants.SHEET_Queries, whereConstraint);
		FlatFile sReadWrite = FlatFile.getInstance();
		for (HashMap<String, Object> ProcessRow : resultListRows) {
			String sQueries = (String) ProcessRow.get("Queries");
			String sDBField = (String) ProcessRow.get("DBField");
			String[] sDBFields = sDBField.split(",");
			if (sQueries.contains("$AccountNumber$") && sQueries.contains("$PolicyNumber$")) {
				strAccountNumber = sReadWrite.Read("AccountNumber", scrCommon.FlatFileName());
				strPolicyNumber = sReadWrite.Read("PolicyNumber", FlatFileName());
				sQueries = sQueries.replace("$AccountNumber$", strAccountNumber);
				sQueries = sQueries.replace("$PolicyNumber$", strPolicyNumber);
				sQueries = sQueries.replace("$SchemaName$", Schema);
			} else if (sQueries.contains("$AccountNumber$")) {
				strAccountNumber = sReadWrite.Read("AccountNumber", scrCommon.FlatFileName());
				sQueries = sQueries.replace("$AccountNumber$", strAccountNumber);
				sQueries = sQueries.replace("$SchemaName$", Schema);
			} else if (sQueries.contains("$PolicyNumber$")) {
				strPolicyNumber = sReadWrite.Read("PolicyNumber", PCThreadCache.getInstance().getProperty("FlatFileName"));
				sQueries = sQueries.replace("$PolicyNumber$", strPolicyNumber);
				sQueries = sQueries.replace("$SchemaName$", Schema);
			} else if (sQueries.contains("$FPolicyNumber$")) {
				strPolicyNumber = sReadWrite.Read("FPolicyNumber", PCThreadCache.getInstance().getProperty("FlatFileName"));
				sQueries = sQueries.replace("$FPolicyNumber$", strPolicyNumber);
				sQueries = sQueries.replace("$SchemaName$", Schema);
			} else if (sQueries.contains("$GroupName$")) {
				strGroupName = sReadWrite.Read("GroupName", FlatFileName());
				sQueries = sQueries.replace("$GroupName$", strGroupName);
				sQueries = sQueries.replace("$SchemaName$", Schema);
			} else if (sQueries.contains("$QueueName$")) {
				strQueueName = sReadWrite.Read("QueueName", FlatFileName());
				sQueries = sQueries.replace("$QueueName$", strQueueName);
				sQueries = sQueries.replace("$SchemaName$", Schema);
			} else if (sQueries.contains("$RewritePolicyNumber$")) {
				strRewritepolicy = sReadWrite.Read("RewritePolicyNumber", FlatFileName());
				sQueries = sQueries.replace("$RewritePolicyNumber$", strRewritepolicy);
				sQueries = sQueries.replace("$SchemaName$", Schema);
			} else if (sQueries.contains("$OrganizationName$")) {
				strOrganizationName = sReadWrite.Read("OrganizationName", scrCommon.FlatFileName());
				sQueries = sQueries.replace("$OrganizationName$", strOrganizationName);
				sQueries = sQueries.replace("$SchemaName$", Schema);
			} else if (sQueries.contains("$UserFirstName$") && sQueries.contains("$UserLastName$")) {
				strUserFirstName = sReadWrite.Read("UserFirstName", FlatFileName());
				strUserLastName = sReadWrite.Read("UserLastName", FlatFileName());
				sQueries = sQueries.replace("$UserFirstName$", strUserFirstName);
				sQueries = sQueries.replace("$UserLastName$", strUserLastName);
				sQueries = sQueries.replace("$SchemaName$", Schema);
			} else if (sQueries.contains("$ActivityId$")) {
				strActivityID = sReadWrite.Read("ActivityId", scrCommon.FlatFileName());
				sQueries = sQueries.replace("$ActivityId$", strActivityID);
				sQueries = sQueries.replace("$SchemaName$", Schema);
			} else if (sQueries.contains("$RoleName$")) {
				strRoleName = sReadWrite.Read("RoleName", scrCommon.FlatFileName());
				sQueries = sQueries.replace("$RoleName$", strRoleName);
				sQueries = sQueries.replace("$SchemaName$", Schema);
			}
			System.out.println(sQueries);
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), sQueries, sQueries, "PASS");
			
			int odsWait = Integer.valueOf(HTML.properties.getProperty("ODSWAIT"));
			Boolean sQueryFound = false;
			int Counter = 0;
			for (int k = 1; k <= odsWait / 10000; k++) {
				resultSet = ConnectDB(sQueries, URL, UserName, Password);
				while (resultSet.next()) {
					sQueryFound = true;
					ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
					int queryColumnCount = resultSetMetaData.getColumnCount();
					for (String fieldValue : sDBFields) {
						sDBAliasName = false;
						for (int i = 1; i <= queryColumnCount; i++) {
							String queryColumnName = resultSetMetaData.getColumnName(i);
							// if(fieldValue.toUpperCase().contains(queryColumnName.toUpperCase()))
							if (fieldValue.toUpperCase().equalsIgnoreCase(queryColumnName.toUpperCase())) {
								sDBAliasName = true;
								String queryDBValue = resultSet.getString(i);
								if (!((queryDBValue) == null)) {
									sMappingFound = false;
									whereConstraint.clear();
									whereConstraint.put(PCConstants.DBAliasName, queryColumnName);
									resultListRows1 = sXL.executeSelectQuery(PCConstants.SHEET_ODSMapping, whereConstraint);
									for (HashMap<String, Object> ProcessRow1 : resultListRows1) {
										sMappingFound = true;
										String sPCFieldName = (String) ProcessRow1.get("PCFieldName");
										System.out.println("sPCFieldName " + sPCFieldName);
										sPCCompareValue = sReadWrite.Read(sPCFieldName, PCThreadCache.getInstance().getProperty("FlatFileName"));
										if (!((sPCCompareValue) == null)) {
											sTranformationLogicFound = false;
											whereConstraint.clear();
											// whereConstraint.put(PCConstants.PCFieldName, sPCFieldName);
											whereConstraint.put(PCConstants.DBAliasName, queryColumnName);
											resultListRows2 = sXL.executeSelectQuery(PCConstants.SHEET_ODSTransformLogic, whereConstraint);
											for (HashMap<String, Object> ProcessRow2 : resultListRows2) {
												sTranformationLogicFound = true;
												String sTranformationLogic = (String) ProcessRow2.get("ODSTransformationLogic");
												Status = PCDBCompare(sPCCompareValue, queryDBValue, sTranformationLogic, sPCFieldName, queryColumnName);
												break;
											}
											if (!sTranformationLogicFound) {
												logger.info("ODS Tranformation Logic(" + sPCFieldName + ") is not found in the " + PCConstants.SHEET_ODSTransformLogic + " Sheet");
											}
										}
										break;
									}
									if (!sMappingFound) {
										logger.info("DB Mapping field('" + queryColumnName + "') is not found in the " + PCConstants.SHEET_ODSMapping + " Sheet");
									}
								} else {
									queryDBValue = "";
								}
							}
							if (sDBAliasName) {
								break;
							}
						}
					}
					if (sQueryFound) {
						logger.info("Query returned the value and validated");
						break;
					}
				}
				if (!sQueryFound) {
					Counter++;
					logger.info("Query did not found so iterating " + Counter + " for the Query " + sDBFields[0] + "");
					SCRCommon.ODSWait(Integer.valueOf(HTML.properties.getProperty("WAIT")));
				} else {
					break;
				}
			}
			if (!sQueryFound) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "ODS should retrieve the value for the query " + sDBFields[0] + "", "ODS did not retrieve the value for the query " 

+ sDBFields[0] + "", "FAIL");
			}
		}
		if (!Status) {
			Status = true;
			logger.info("ODS did not executed properly");
		}
		return Status;
	}

	private boolean PCDBCompare(String sPCValue, String sDBValue, String CompareFormat, String sPCFieldName, String sDBAliasName) throws 

Exception {
		// TODO Auto-generated method stub
		boolean Status = false;
		String sConvertedFormatPC = null;
		String sConvertedFormatDB = null;
		String splitValue[] = null;
		switch (CompareFormat.toUpperCase()) {
		case "TEXT":
			Status = CompareResults(sPCValue, sDBValue, sPCFieldName, sDBAliasName);
			break;
		case "REMOVESPACE":
			Status = RemoveSpaceCompareResults(sPCValue, sDBValue, sPCFieldName, sDBAliasName);
			break;
		case "SEGMENT":
			switch (sDBValue) {
			case "SC":
				sDBValue = "Small Commercial";
				break;
			case "captiveService":
				sDBValue = "Captive Services";
				break;
			case "keyAccount":
				sDBValue = "Key Account";
				break;
			case "selectCustomer":
				sDBValue = "Select Customer";
				break;
			case "commercialProgramSmall":
				sDBValue = "Commercial Program Small";
				break;
			case "commercialProgramMedium":
				sDBValue = "Commercial Program Medium";
				break;
			}
			Status = CompareResults(sPCValue, sDBValue, sPCFieldName, sDBAliasName);
			break;
		case "SPACECONTAINS":
			sPCValue = sPCValue.replaceAll(" ", "");
			Status = CompareResults(sPCValue, sDBValue, sPCFieldName, sDBAliasName);
			break;
		case "DATETIME":
			try {
				SimpleDateFormat defFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				Date date = defFormat.parse(sDBValue);
				SimpleDateFormat sdfDestination = new SimpleDateFormat("MM/dd/yyyy");
				sConvertedFormatDB = sdfDestination.format(date);
				SimpleDateFormat defFormat1 = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
				Date date1 = defFormat1.parse(sPCValue);
				SimpleDateFormat sdfDestination1 = new SimpleDateFormat("MM/dd/yyyy");
				sConvertedFormatPC = sdfDestination1.format(date1);
				Status = CompareResults(sConvertedFormatPC, sConvertedFormatDB, sPCFieldName, sDBAliasName);
			} catch (ParseException exp) {
				exp.printStackTrace();
			}
			break;
		case "DATE":
			try {
				SimpleDateFormat defFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				Date date = defFormat.parse(sDBValue);
				SimpleDateFormat sdfDestination = new SimpleDateFormat("MM/dd/yyyy");
				sConvertedFormatDB = sdfDestination.format(date);
				Status = CompareResults(sPCValue, sConvertedFormatDB, sPCFieldName, sDBAliasName);
			} catch (ParseException exp) {
				exp.printStackTrace();
			}
			break;
		case "DATE1":
			try {
				SimpleDateFormat defFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				SimpleDateFormat defFormat1 = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
				Date date = defFormat.parse(sDBValue);
				Date date1 = defFormat1.parse(sPCValue);
				SimpleDateFormat sdfDestination = new SimpleDateFormat("MM/dd/yyyy");
				sConvertedFormatDB = sdfDestination.format(date);
				sConvertedFormatPC = sdfDestination.format(date1);
				Status = CompareResults(sConvertedFormatPC, sConvertedFormatDB, sPCFieldName, sDBAliasName);
			} catch (ParseException exp) {
				exp.printStackTrace();
			}
			break;

		case "TIMESTAMP":
			Status = true;
			break;
		case "DATETIMEAMPM":
			try {
				SimpleDateFormat defFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				Date date = defFormat.parse(sDBValue);
				SimpleDateFormat sdfDestination = new SimpleDateFormat("MM/dd/yyyy");
				sConvertedFormatDB = sdfDestination.format(date);

				SimpleDateFormat defFormat1 = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
				Date date1 = defFormat1.parse(sPCValue);
				SimpleDateFormat sdfDestination1 = new SimpleDateFormat("MM/dd/yyyy");
				sConvertedFormatPC = sdfDestination1.format(date1);
				Status = CompareResults(sConvertedFormatPC, sConvertedFormatDB, sPCFieldName, sDBAliasName);
			} catch (ParseException exp) {
				exp.printStackTrace();
			}
			break;
		case "STATE":
			if (sDBValue == null && sPCValue == null) {
				Status = true;
			}
			switch (sDBValue) {
			case "CT":
				sConvertedFormatDB = "Connecticut";
				break;
			case "OH":
				sConvertedFormatDB = "Ohio";
				break;
			case "US":
				sConvertedFormatDB = "United States";
				break;
			case "CA":
				sConvertedFormatDB = "California";
				break;
			case "TX":
				sConvertedFormatDB = "Texas";
				break;
			case "AR":
				sConvertedFormatDB = "Arkansas";
				break;
			case "AZ":
				sConvertedFormatDB = "Arizona";
				break;
			case "MN":
				sConvertedFormatDB = "Minnesota";
				break;
			case "WI":
				sConvertedFormatDB = "Wisconsin";
				break;
			case "MD":
				sConvertedFormatDB = "Maryland";
				break;
			case "KY":
				sConvertedFormatDB = "Kentucky";
				break;
			case "NE":
				sConvertedFormatDB = "Nebraska";
				break;
			case "SC":
				sConvertedFormatDB = "South Carolina";
				break;
			case "AL":
				sConvertedFormatDB = "Alabama";
				break;
			case "CO":
				sConvertedFormatDB = "Colorado";
				break;
			case "FL":
				sConvertedFormatDB = "Florida";
				break;
			case "NH":
				sConvertedFormatDB = "New Hampshire";
				break;

			}
			Status = CompareResults(sPCValue, sConvertedFormatDB, sPCFieldName, sDBAliasName);
			break;
		case "HYPHEN":
			sConvertedFormatPC = sPCValue.replaceAll("[\\s\\-()]", "");
			Status = CompareResults(sConvertedFormatPC, sDBValue, sPCFieldName, sDBAliasName);
			break;
		/*
		 * case "ADDSPACEBRACKET": sConvertedFormatPC = sPCValue.replaceAll("-","");
		 * sConvertedFormatDB = sDBValue.replaceAll("-",""); Status =
		 * CompareResults(sConvertedFormatPC, sConvertedFormatDB, sPCFieldName,
		 * sDBAliasName); break;
		 */
		case "MAPPCODS":
			if (sDBValue == null && sPCValue == null) {
				Status = true;
			}
			switch (sDBValue) {
			case "WC7WorkersComp":
				sConvertedFormatDB = "Workers Compensation";
				break;
			case "HIGCOMPROP":
				sConvertedFormatDB = "Commercial Property";
				break;
			case "relNonRenewal":
				sConvertedFormatDB = "Release Non Renewal";
				break;
			case "PSRenewal_Ext":
				sConvertedFormatDB = "Renewal";
				break;
			case "custCareTeam":
				sConvertedFormatDB = "Customer Care Team";
				break;
			case "NonRenewing":
				sConvertedFormatDB = "Non-renewing";
				break;
			case "NotTaking":
				sConvertedFormatDB = "Not Taking";
				break;
			case "appetiteChange_Ext":
				sConvertedFormatDB = "Non-Renew - Appetite Change";
				break;
			case "userview":
				sConvertedFormatDB = "View user";
				break;
			case "FinalAudit":
				sConvertedFormatDB = "Final Audit";
				break;
			case "Administrative Fund Assessment Surcharge":
				sConvertedFormatDB = "VT administrative fund Surcharge";
				break;
			case "Debt Reduction Surcharge":
				sConvertedFormatDB = "WV Debt Reduction Surcharge";
				break;
			}
			Status = CompareResults(sPCValue, sConvertedFormatDB, sPCFieldName, sDBAliasName);
			break;
		case "Contains":
			Status = CompareResults(sPCValue, sDBValue, sPCFieldName, sDBAliasName);
			break;
		case "YesNo":
			if (sPCValue.equalsIgnoreCase("Yes"))
				sConvertedFormatPC = "Y";
			else if (sPCValue.equalsIgnoreCase("No"))
				sConvertedFormatPC = "N";
			Status = CompareResults(sConvertedFormatPC, sDBValue, sPCFieldName, sDBAliasName);
			break;
		case "SpaceHyphen":
			sPCValue = sPCValue.replaceAll(" ", "");
			sPCValue = sPCValue.replaceAll("-", "");
			Status = CompareResults(sPCValue, sDBValue, sPCFieldName, sDBAliasName);
			break;
		case "ZIPCODE":
			// strExcelPCFieldValue1= strExcelPCFieldValue.replaceAll(".","");
			splitValue = sDBValue.split("-");
			sDBValue = splitValue[0];
			System.out.println("SplitValue of " + sDBValue + "");
			Status = CompareResults(sPCValue, sDBValue, sPCFieldName, sDBAliasName);
			break;
		case "PRODUCERCODE":
			sPCValue = sPCValue.substring(0, 2);
			Status = CompareResults(sPCValue, sDBValue, sPCFieldName, sDBAliasName);
			break;
		case "COMPANYCODE":
			splitValue = sPCValue.split("Company Code ");
			sPCValue = splitValue[1].trim();
			Status = CompareResults(sPCValue, sDBValue, sPCFieldName, sDBAliasName);
			break;
		case "SUBMISSIONCODE":
			switch (sDBValue) {
			case "eMail":
				sDBValue = "E-mail";
				break;
			}
			Status = CompareResults(sPCValue, sDBValue, sPCFieldName, sDBAliasName);
			break;
		case "DOLLAR":
			sDBValue = "$" + sDBValue + ".00";
			Status = CompareResults(sPCValue, sDBValue, sPCFieldName, sDBAliasName);
			break;
		case "DECIMAL":
			String value = "00";
			sDBValue = sDBValue + value;
			Status = CompareResults(sPCValue, sDBValue, sPCFieldName, sDBAliasName);
			break;
		case "CITY":
			switch (sDBValue) {
			case "NORTH LITTLE ROCK":
				sDBValue = "NORTH LITTLE RO";
				break;
			}
			Status = CompareResults(sPCValue, sDBValue, sPCFieldName, sDBAliasName);
		}
		if (!Status) {
			Status = true;
		}
		return Status;
	}

	private boolean CompareResults(String sPCValue, String sDBValue, String sPCFieldName, String sDBAliasName) throws IOException {
		Boolean Status = false;
		if (sPCValue.toUpperCase().equals(sDBValue.toUpperCase()) || sDBValue.toUpperCase().equals(sPCValue.toUpperCase())) {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "PC " + sPCFieldName + " Value('" + sPCValue + "') should match with DB " + sDBAliasName + " Value('" + sDBValue + "')", "PC " + 

sPCFieldName + " Value('" + sPCValue + "') is matching with DB " + sDBAliasName + " Value('" + sDBValue + "')", "PASS");
			Status = true;
		} else {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "PC " + sPCFieldName + " Value('" + sPCValue + "') should match with DB " + sDBAliasName + " Value('" + sDBValue + "')", "PC " + 

sPCFieldName + " Value('" + sPCValue + "') is not matching with DB " + sDBAliasName + " Value('" + sDBValue + "')", "FAIL");
		}
		return Status;
	}

	private boolean RemoveSpaceCompareResults(String sPCValue, String sDBValue, String sPCFieldName, String sDBAliasName) throws IOException 

{
		Boolean Status = false;
		sPCValue = sPCValue.replaceAll("//s+", "");
		sDBValue = sDBValue.replaceAll("//s+", "");
		if (sPCValue.toUpperCase().equals(sDBValue.toUpperCase()) || sDBValue.toUpperCase().equals(sPCValue.toUpperCase())) {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "PC " + sPCFieldName + " Value('" + sPCValue + "') should match with DB " + sDBAliasName + " Value('" + sDBValue + "')", "PC " + 

sPCFieldName + " Value('" + sPCValue + "') is matching with DB " + sDBAliasName + " Value('" + sDBValue + "')", "PASS");
			Status = true;
		} else {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "PC " + sPCFieldName + " Value('" + sPCValue + "') should match with DB " + sDBAliasName + " Value('" + sDBValue + "')", "PC " + 

sPCFieldName + " Value('" + sPCValue + "') is not matching with DB " + sDBAliasName + " Value('" + sDBValue + "')", "FAIL");
		}
		return Status;
	}

	public ResultSet ConnectDB(String strSQLStatement, String strURL, String strUserName, String strPassword) throws ClassNotFoundException, 

SQLException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection conn = DriverManager.getConnection(strURL, strUserName, strPassword);
		Statement stment = conn.createStatement();
		ResultSet rs = stment.executeQuery(strSQLStatement);
		return rs;
	}

	/*
	 * private String FlatFileName() { XlsxReader xls = XlsxReader.getInstance();
	 * String tcID = PCThreadCache.getInstance().getProperty("TCID"); String
	 * sFileName = null; HashMap<String,Object> whereConstraint = new
	 * HashMap<String,Object>(); ArrayList<HashMap<String,Object>> resultListRows=
	 * new ArrayList<HashMap<String,Object>>(); whereConstraint.clear();
	 * whereConstraint.put(PCConstants.ID, tcID); resultListRows =
	 * xls.executeSelectQuery(PCConstants.SHEET_FLATFILE, whereConstraint); for
	 * (HashMap<String,Object> processRow: resultListRows) { sFileName =
	 * (String)processRow.get("FlatFileName");
	 * 
	 * } return sFileName;
	 * 
	 * }
	 */

	private String FlatFileName() {
		XlsxReader xls = XlsxReader.getInstance();
		String tcID = PCThreadCache.getInstance().getProperty("TCID");
		String[] splittcID = tcID.split("_");
		String sFileName = null;
		HashMap<String, Object> whereConstraint = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> resultListRows = new ArrayList<HashMap<String, Object>>();
		whereConstraint.clear();
		whereConstraint.put(PCConstants.ID, splittcID[0]);
		resultListRows = xls.executeSelectQuery(PCConstants.SHEET_FLATFILE, whereConstraint);
		for (HashMap<String, Object> processRow : resultListRows) {
			sFileName = (String) processRow.get("FlatFileName");
			break;
		}
		return sFileName;

	}

	public Boolean getDBValueForQueryInput(String strFuncValue) throws Exception {
		Boolean Status = false;
		Boolean sDBAliasName = false;
		String Schema = null;
		String URL = null;
		String UserName = null;
		String Password = null;
		ResultSet resultSet = null;
		String strAccountNumber = null;
		String strPolicyNumber = null;
		String strGroupName = null;
		String strQueueName = null;
		String strUserFirstName = null;
		String strUserLastName = null;
		XlsxReader sXL = XlsxReader.getInstance();
		SCRCommon SCRCommon = new SCRCommon();
		String sFileName = SCRCommon.FlatFileName();
		HashMap<String, Object> whereConstraint = new HashMap<String, Object>();
		// HashMap<String,Object> updateColumnNameValues = new HashMap<String,Object>();
		ArrayList<HashMap<String, Object>> resultListRows = new ArrayList<HashMap<String, Object>>();
		whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
		switch (HTML.properties.getProperty("Region").toUpperCase()) {
		case "QA":
			Schema = HTML.properties.getProperty("QAODSSchema");
			URL = HTML.properties.getProperty("QAURL");
			UserName = HTML.properties.getProperty("QAMDMODSUserID");
			Password = HTML.properties.getProperty("QAMDMODSPassword");
			break;
		case "INT":
			Schema = HTML.properties.getProperty("INTODSSchema");
			URL = HTML.properties.getProperty("INTURL");

			UserName = HTML.properties.getProperty("INTMDMODSUserID");
			Password = HTML.properties.getProperty("INTMDMODSPassword");
			break;
		case "LTQ":
			Schema = HTML.properties.getProperty("LTQODSSchema");
			URL = HTML.properties.getProperty("INTURL");

			UserName = HTML.properties.getProperty("INTMDMODSUserID");
			Password = HTML.properties.getProperty("INTMDMODSPassword");
			break;
		case "LTI":
			Schema = HTML.properties.getProperty("LTIODSSchema");
			URL = HTML.properties.getProperty("INTURL");

			UserName = HTML.properties.getProperty("INTMDMODSUserID");
			Password = HTML.properties.getProperty("INTMDMODSPassword");
			break;
		case "LTA":
			Schema = HTML.properties.getProperty("LTAODSSchema");
			URL = HTML.properties.getProperty("INTURL");

			UserName = HTML.properties.getProperty("INTMDMODSUserID");
			Password = HTML.properties.getProperty("INTMDMODSPassword");
			break;
		case "QA02":
			Schema = HTML.properties.getProperty("QA02ODSSchema");
			URL = HTML.properties.getProperty("QAURL");

			UserName = HTML.properties.getProperty("QAMDMODSUserID");
			Password = HTML.properties.getProperty("QAMDMODSPassword");
			break;
		case "LTQ2":
			Schema = HTML.properties.getProperty("LTQ2ODSSchema");
			URL = HTML.properties.getProperty("INTURL");

			UserName = HTML.properties.getProperty("INTMDMODSUserID");
			Password = HTML.properties.getProperty("INTMDMODSPassword");
			break;
		case "DLTI2":
			Schema = HTML.properties.getProperty("LTI2ODSSchema");
			URL = HTML.properties.getProperty("INTURL");

			UserName = HTML.properties.getProperty("INTMDMODSUserID");
			Password = HTML.properties.getProperty("INTMDMODSPassword");
			break;
		}
		resultListRows = sXL.executeSelectQuery(PCConstants.SHEET_Queries, whereConstraint);
		FlatFile sReadWrite = FlatFile.getInstance();
		for (HashMap<String, Object> ProcessRow : resultListRows) {
			String sQueries = (String) ProcessRow.get("Queries");
			String sDBField = (String) ProcessRow.get("DBField");
			String[] sDBFields = sDBField.split(",");
			if (sQueries.contains("$AccountNumber$") && sQueries.contains("$PolicyNumber$")) {
				strAccountNumber = sReadWrite.Read("AccountNumber", SCRCommon.FlatFileName());
				strPolicyNumber = sReadWrite.Read("PolicyNumber", FlatFileName());
				sQueries = sQueries.replace("$AccountNumber$", strAccountNumber);
				sQueries = sQueries.replace("$PolicyNumber$", strPolicyNumber);
				sQueries = sQueries.replace("$SchemaName$", Schema);
			} else if (sQueries.contains("$AccountNumber$")) {
				strAccountNumber = sReadWrite.Read("AccountNumber", SCRCommon.FlatFileName());
				sQueries = sQueries.replace("$AccountNumber$", strAccountNumber);
				sQueries = sQueries.replace("$SchemaName$", Schema);
			} else if (sQueries.contains("$PolicyNumber$")) {
				strPolicyNumber = sReadWrite.Read("PolicyNumber", SCRCommon.FlatFileName());
				sQueries = sQueries.replace("$PolicyNumber$", strPolicyNumber);
				sQueries = sQueries.replace("$SchemaName$", Schema);
			} else if (sQueries.contains("$GroupName$")) {
				strGroupName = sReadWrite.Read("GroupName", FlatFileName());
				sQueries = sQueries.replace("$GroupName$", strGroupName);
				sQueries = sQueries.replace("$SchemaName$", Schema);
			} else if (sQueries.contains("$QueueName$")) {
				strQueueName = sReadWrite.Read("QueueName", FlatFileName());
				sQueries = sQueries.replace("$QueueName$", strQueueName);
				sQueries = sQueries.replace("$SchemaName$", Schema);
			} else if (sQueries.contains("$UserFirstName$") && sQueries.contains("$UserLastName$")) {
				strUserFirstName = sReadWrite.Read("UserFirstName", FlatFileName());
				strUserLastName = sReadWrite.Read("UserLastName", FlatFileName());
				sQueries = sQueries.replace("$UserFirstName$", strUserFirstName);
				sQueries = sQueries.replace("$UserLastName$", strUserLastName);
				sQueries = sQueries.replace("$SchemaName$", Schema);
			} else if (sQueries.contains("$ActivityId$")) {
				strAccountNumber = sReadWrite.Read("ActivityId", SCRCommon.FlatFileName());
				sQueries = sQueries.replace("$ActivityId$", strAccountNumber);
				sQueries = sQueries.replace("$SchemaName$", Schema);
			}
			int odsWait = Integer.valueOf(HTML.properties.getProperty("ODSWAIT"));
			Boolean sQueryFound = false;
			int Counter = 0;
			for (int k = 1; k <= odsWait / 10000; k++) {
				resultSet = ConnectDB(sQueries, URL, UserName, Password);
				while (resultSet.next()) {
					sQueryFound = true;
					ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
					int queryColumnCount = resultSetMetaData.getColumnCount();
					for (String fieldValue : sDBFields) {
						sDBAliasName = false;
						for (int i = 1; i <= queryColumnCount; i++) {
							String queryColumnName = resultSetMetaData.getColumnName(i);
							if (fieldValue.toUpperCase().equalsIgnoreCase(queryColumnName.toUpperCase())) {
								sDBAliasName = true;
								String queryDBValue = resultSet.getString(i);
								Status = sReadWrite.write(PCThreadCache.getInstance().getProperty("TCID"), PCThreadCache.getInstance().getProperty("methodName"), strFuncValue, queryDBValue, "OUTPUT", sFileName);
							}
							if (sDBAliasName) {
								break;
							}
						}
					}
					if (sQueryFound) {
						logger.info("Query returned the value for input");
						break;
					}
				}
				if (!sQueryFound) {
					Counter++;
					logger.info("ODS did not executed properly for getting DB input hence iterating " + Counter + " for the Query " + sDBFields[0] + "");
					SCRCommon.ODSWait(Integer.valueOf(HTML.properties.getProperty("WAIT")));
				} else {
					break;
				}
			}
			if (!sQueryFound) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "ODS should retrieve the value for the query " + sDBFields[0] + "", "ODS did not retrieve the value for the query " + sDBFields[0] + "", "FAIL");
			}
		}
		if (!Status) {
			Status = true;
			logger.info("ODS did not executed properly for getting DB input");
		}
		return Status;
	}

	public Boolean getODSInput(String strFuncValue) {
		Boolean blnStatus = false;
		FlatFile sReadWrite = FlatFile.getInstance();
		String strLoginUserName = System.getProperty("user.name");
		if (!(strLoginUserName == null)) {
			blnStatus = sReadWrite.write(PCThreadCache.getInstance().getProperty("TCID"), PCThreadCache.getInstance().getProperty("methodName"), strFuncValue, strLoginUserName, "OUTPUT", FlatFileName());
		}
		return blnStatus;
	}
}