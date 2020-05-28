package com.pc.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.testng.log4testng.Logger;

import com.pc.dao.E2EUtilityDAO;
import com.pc.driver.LoggerClass;

public class E2ETestCaseUtil {
	
	//static Logger log = Logger.getLogger(E2ETestCaseUtil.class);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger log  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));

	private String e2eTestCaseId = null;
	private String seqNumber = null;
	private String processTestCase = null;
	private String executorMachine = null;
	public static String E2E_TestCaseID = null;
	public static Map<String, String> keyMap = new HashMap<String, String>();
	private static E2ETestCaseUtil e2eTestCaseUtil = new E2ETestCaseUtil();

	public String getExecutorMachine() {
		return executorMachine;
	}

	public void setExecutorMachine(String executorMachine) {
		this.executorMachine = executorMachine;
	}

	public static E2ETestCaseUtil getInstance() {
		return e2eTestCaseUtil;
	}

	public String e2eInitE2ETestCaseExecution(String testCaseName) {
		// if test case name contains | pipe symbol then it means we are executing a E2E
		// testing

		// TestCaseName :: PC_CUE_SC1_Quote|E2E_CUE_PC_SC1|140|GRID
		System.out.println("testCaseName::::::" + testCaseName);
		StringTokenizer st = new StringTokenizer(testCaseName, "|");
		// TC1|E2E_e2e_TEST_caSE_ID_Tc4|52|GRID
		if (st.hasMoreTokens()) {
			testCaseName = st.nextToken();
			processTestCase = testCaseName;
		}
		if (st.hasMoreTokens()) {
			e2eTestCaseId = st.nextToken();
			E2E_TestCaseID = e2eTestCaseId;
		}
		if (st.hasMoreTokens()) {
			seqNumber = st.nextToken();
		}
		if (st.hasMoreTokens()) {
			executorMachine = st.nextToken();
		}
		if (!keyMap.containsKey(processTestCase)) {
			keyMap.put(processTestCase, e2eTestCaseId);
			keyMap.put(e2eTestCaseId, seqNumber);
		}
		testCaseName = testCaseName.trim();
		System.out.println("testCaseName::::::" + testCaseName);
		// Check if any value in excel sheet needs to be updated with values from Access
		// DB. for instance UFT updates account number in access db and
		// that account number may be required by selenium testcase. If an entry in
		// E2EMAPPING tab is present then
		// it means we have to process it. please refer E2EMapping tab for more details.
		ArrayList<HashMap<String, Object>> e2eMappingRows = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> whereClause = new HashMap<String, Object>();
		whereClause.put("TestCaseName", testCaseName);
		e2eMappingRows = XlsxReader.getInstance().executeSelectQuery("E2EDBREAD", whereClause);
		System.out.println("Bread Rows ****************** " + e2eMappingRows.size() + " " + whereClause);
		if (!e2eMappingRows.isEmpty()) {

			// Populate Excel sheet with values
			for (HashMap<String, Object> processRow : e2eMappingRows) {
				String sheetName = (String) processRow.get("SheetName");
				String columnName = (String) processRow.get("ColumnName");
				String valueToRead = (String) processRow.get("ValueToRead");
				String testCaseID = (String) processRow.get("ID");
				log.info("sheetName = " + sheetName);
				System.out.println("sheetName = " + sheetName);
				log.info("columnName = " + columnName);
				System.out.println("columnName = " + columnName);
				log.info("valueToUpdate = " + valueToRead);
				System.out.println("valueToUpdate = " + valueToRead);
				log.info("testCaseID = " + testCaseID);
				System.out.println("testCaseID = " + testCaseID);
				if (sheetName != null && sheetName.length() > 0 && columnName != null && columnName.length() > 0 && valueToRead != null && valueToRead.length() > 0) {

					HashMap<String, Object> updateWhereClause = new HashMap<String, Object>();
					HashMap<String, Object> updateColumnValue = new HashMap<String, Object>();
					String value = null;
					if (valueToRead != null && valueToRead.length() > 0) {
						value = E2EUtilityDAO.getInstance().retrieveTransKeyValue(e2eTestCaseId, valueToRead);
					}
					updateColumnValue.put(columnName, value);
					updateWhereClause.put("ID", testCaseID);
					PCThreadCache.getInstance().setProperty("columnName", value);
					/*XlsxReader.getInstance().executeUpdateQuery(sheetName, updateColumnValue, updateWhereClause);*/
				}
			}
		}
		E2EUtilityDAO.getInstance().updateE2EMasterStatus(seqNumber, "In Progress");
		return processTestCase;
	}

	public void upDateE2EStatus(String status) {
		log.info("seqNumber " + seqNumber);
		System.out.println("seqNumber " + seqNumber);
		if (seqNumber != null && seqNumber.length() > 0) {
			E2EUtilityDAO.getInstance().updateE2EMasterStatus(seqNumber, status);
		}
	}

	public void updateE2EValidationStatus(String status) {
		String seqNum = keyMap.get(keyMap.get(PCThreadCache.getInstance().getProperty("TCID")));
		System.out.println("seqNum " + seqNum);
		//if (seqNum != null && !seqNum.isEmpty()) {
		//	E2EUtilityDAO.getInstance().updateE2EValidationStatus(seqNum, status);
		//}
	}

	public void updateTestResultsintoDB() {
		ArrayList<HashMap<String, Object>> e2eExcelRows = new ArrayList<HashMap<String, Object>>();
		try {
			ArrayList<HashMap<String, Object>> e2eDBUpdateRows = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> whereClause = new HashMap<String, Object>();
			whereClause.put("TestCaseName", processTestCase);
			e2eDBUpdateRows = XlsxReader.getInstance().executeSelectQuery("E2EDBUPDATE", whereClause);
			if (!e2eDBUpdateRows.isEmpty()) {
				for (HashMap<String, Object> processRow : e2eDBUpdateRows) {
					String sheetName = (String) processRow.get("FromSheetName");
					String columnName = (String) processRow.get("FromColumnName");
					String valueToUpdate = (String) processRow.get("End2EndDBKeyName");
					String testCaseID = (String) processRow.get("ID");
					log.info("sheetName = " + sheetName);
					log.info("columnName = " + columnName);
					log.info("valueToUpdate = " + valueToUpdate);
					log.info("testCaseID = " + testCaseID);
					if (sheetName != null && sheetName.length() > 0 && columnName != null && columnName.length() > 0 && valueToUpdate != null && valueToUpdate.length() > 0) {
						String value = null;
						whereClause.clear();
						whereClause.put("ID", testCaseID);
						e2eExcelRows.clear();
						e2eExcelRows = XlsxReader.getInstance().executeSelectQuery(sheetName, whereClause);
						if (!e2eExcelRows.isEmpty()) {
							for (HashMap<String, Object> currentRow : e2eExcelRows) {
								value = (String) currentRow.get(columnName);
							}
						}
						if (valueToUpdate != null && valueToUpdate.length() > 0) {
							E2EUtilityDAO.getInstance().updateE2ETrans(e2eTestCaseId, valueToUpdate, value);
						}
					}
				}

			}
		} finally {
			e2eExcelRows = null;
		}
	}

}
