package com.pc.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.testng.log4testng.Logger;

import com.pc.driver.LoggerClass;
import com.pc.screen.SCRCommon;
import com.pc.utilities.DBConnectionManager;
import com.pc.utilities.PCThreadCache;

public class E2EUtilityDAO {

	private Connection ucaConn;
	private Statement st;
	private ResultSet rs;
	//private static Logger log = Logger.getLogger(E2EUtilityDAO.class);
	private org.apache.log4j.Logger log;
	private static E2EUtilityDAO e2EUtilDAO = new E2EUtilityDAO();

	public static E2EUtilityDAO getInstance() {
		return e2EUtilDAO;
	}

	private E2EUtilityDAO() {

	}

	public String retrieveTransKeyValue(String e2eTestCaseId, String keyName) {
		log = LoggerClass.getThreadLogger("Thread" + Thread
				.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
		String transKeyValue = "";
		String query = "";
		ucaConn = DBConnectionManager.getInstance().getConnectionFromPool();
		try {

			st = ucaConn.createStatement();

			query = "SELECT trans_key_value FROM E2E_TRANS	WHERE trans_id =(SELECT MAX (trans_id) FROM E2E_TRANS WHERE  E2E_TEST_CASE_ID ='" + e2eTestCaseId + "' AND UPPER (TRANS_KEY_NM) =	(SELECT UPPER (field_nm) FROM field_name_master WHERE field_id = (SELECT master_field_id FROM app_field_name_mapping WHERE app_nm = 'PC' AND UPPER (app_field_nm) =	UPPER ('" + keyName + "'))))";
			System.out.println("retrieveTransKeyValue():::Query::" + query);
			rs = st.executeQuery(query);

			/*
			 * rs = st.executeQuery(
			 * "SELECT trans_key_value FROM (SELECT trans_key_value, trans_id, E2E_Test_case_id, MAX (trans_id) OVER (PARTITION BY E2E_Test_case_id) max_trans_id FROM e2e_trans WHERE UPPER (trans_key_nm) =(SELECT UPPER (mas.field_nm) FROM field_name_master mas, app_field_name_mapping map WHERE     mas.field_id = map.master_field_id AND UPPER (map.app_field_nm) =UPPER('"
			 * + keyName + "') )) WHERE trans_id = max_trans_id AND E2E_Test_case_id = '" +
			 * e2eTestCaseId + "'");
			 */

			if (rs.next()) {
				transKeyValue = rs.getString(1);
				log.info("Trans Key details -> " + keyName + " : " + transKeyValue);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionManager.getInstance().closeResultSet(rs);
			DBConnectionManager.getInstance().closeStatement(st);
			DBConnectionManager.getInstance().returnConnectionToPool(ucaConn);
		}

		return transKeyValue;
	}

	public boolean updateE2ETrans(String e2eTestCaseId, String keyName, String keyValue) {
		log = LoggerClass.getThreadLogger("Thread" + Thread
				.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
		boolean status = false;
		ucaConn = DBConnectionManager.getInstance().getConnectionFromPool();
		try {

			st = ucaConn.createStatement();
			String query = "INSERT into E2E_TRANS(TRANS_KEY_NM, TRANS_KEY_VALUE,E2E_TEST_CASE_ID,LAST_UPDT_TMSP,LAST_UPDT_APPL_NM) VALUES('" + keyName + "','" + keyValue + "','" + e2eTestCaseId + "',SYSDATE ,'PC')";
			log.info("executing query = " + query);
			status = st.execute(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBConnectionManager.getInstance().closeResultSet(rs);
			DBConnectionManager.getInstance().closeStatement(st);
			DBConnectionManager.getInstance().returnConnectionToPool(ucaConn);
		}
		return status;

	}

	public boolean updateE2EMasterStatus(String Id, String statusValue) {
		log = LoggerClass.getThreadLogger("Thread" + Thread
				.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
		boolean status = false;
		String computerName = null;
		ucaConn = DBConnectionManager.getInstance().getConnectionFromPool();
		try {
			// setting the computername as grid
			computerName = SCRCommon.getMachineName();
			st = ucaConn.createStatement();
			String query = "UPDATE E2E_TESTCASES set  TEST_CASE_STAT_DESC = '" + statusValue + "',  EXEC_MACH_NM ='" + computerName;
			if ("In Progress".equalsIgnoreCase(statusValue)) {
				query = query + "', EXEC_START_TIME = SYSDATE";
			} else {
				query = query + "', EXEC_END_TIME = SYSDATE";
			}
			query = query + " where seq_num = " + Id;
			log.info("executing query = " + query);
			status = st.execute(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBConnectionManager.getInstance().closeResultSet(rs);
			DBConnectionManager.getInstance().closeStatement(st);
			DBConnectionManager.getInstance().returnConnectionToPool(ucaConn);
		}
		return status;

	}

	public boolean updateE2EValidationStatus(String Id, String statusValue) {
		log = LoggerClass.getThreadLogger("Thread" + Thread
				.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
		boolean status = false;
		ucaConn = DBConnectionManager.getInstance().getConnectionFromPool();
		try {
			st = ucaConn.createStatement();
			String query = "UPDATE E2E_TESTCASES set  TC_VALIDATION_STATUS = '" + statusValue + "' where seq_num = " + Id;
			log.info("executing query = " + query);
			status = st.execute(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBConnectionManager.getInstance().closeResultSet(rs);
			DBConnectionManager.getInstance().closeStatement(st);
			DBConnectionManager.getInstance().returnConnectionToPool(ucaConn);
		}
		return status;

	}

}
