package com.pc.dao;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.testng.log4testng.Logger;

import com.pc.driver.LoggerClass;
import com.pc.screen.SCRCommon;
import com.pc.utilities.DBConnectionManager;
import com.pc.utilities.PCThreadCache;

public class ReportUtilityDAO {

	private Connection ucaConn;
	private Statement st;
	private ResultSet rs;
	//private static Logger log = Logger.getLogger(ReportUtilityDAO.class);
	private org.apache.log4j.Logger log;

	public ReportUtilityDAO() {
		ucaConn = DBConnectionManager.getInstance().getConnectionFromPool();
	}

	public String retrieveTransKeyValue(String e2eTestCaseId, String keyName) {
		log = LoggerClass.getThreadLogger("Thread" + Thread
				.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
		String policyNumber = null;
		try {
			rs = st.executeQuery("Select TRANS_KEY_VALUE from E2E_TRANS where E2E_TCASE_ID='" + e2eTestCaseId + "' and TRANS_KEY_NM = '" + keyName + "' order by LAST_UPDT_TMSP desc;");
			if (rs.next()) {
				policyNumber = rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			/*
			 * try { st.close(); ucaConn.close(); } catch (SQLException e) { // TODO
			 * Auto-generated catch block e.printStackTrace(); }
			 */
		}

		return policyNumber;
	}

	public boolean updateE2ETrans(String e2eTestCaseId, String keyName, String keyValue) {
		log = LoggerClass.getThreadLogger("Thread" + Thread
				.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
		boolean status = false;
		Date curDate = new Date();
		SimpleDateFormat format = new SimpleDateFormat("M/d/yyyy h:m:s aaa");
		String dateToStr = format.format(curDate);
		try {
			// String query = "MERGE INTO TRANS bi USING (Select '"+
			// e2eTestCaseId
			// +"' as E2E_TCASE_ID from dual) bo ON bi.E2E_TCASE_ID = bo.E2E_TCASE_ID"
			// +
			// " WHEN MATCHED THEN Update set POLICYNO = '"+ policyNumber
			// +"', APPLICATION = 'Selenium', LAST_UPDATE_TIMESTAMP ='"+dateToStr
			// +"', E2E_TCASE_ID = '"+e2eTestCaseId
			// +"' WHEN NOT MATCHED THEN insert(POLICYNO, E2E_TCASE_ID, APPLICATION,
			// LAST_UPDATE_TIMESTAMP) values ( '"+
			// policyNumber +"','"+e2eTestCaseId +"','Selenium','"+dateToStr+
			// "') ";
			String query = "insert into E2E_TRANS(TRANS_KEY_NM, TRANS_KEY_VALUE,E2E_TEST_CASE_ID,LAST_UPDT_TMSP,LAST_UPDT_APPL_NM) VALUES('" + keyName + "','" + keyValue + "','" + e2eTestCaseId + "','" + dateToStr + "','PC')";
			log.info("executing query = " + query);
			status = st.execute(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			/*
			 * try { st.close(); ucaConn.close(); } catch (SQLException e) { // TODO
			 * Auto-generated catch block e.printStackTrace(); }
			 */
		}
		return status;

	}

	public boolean updateE2EMasterStatus(String Id, String statusValue) {
		log = LoggerClass.getThreadLogger("Thread" + Thread
				.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
		boolean status = false;
		Date curDate = new Date();
		SimpleDateFormat format = new SimpleDateFormat("M/d/yyyy h:m:s aaa");
		String dateToStr = format.format(curDate);
		String computerName = null;
		String ipAddress = null;
		String lastUpdateTimeStamp = null;
		String duration = "";

		try {
			InetAddress addr = InetAddress.getLocalHost();
			ipAddress = addr.getHostAddress(); // gets the ip address of current
												// machine
			computerName = addr.getHostName(); // gets computer name of current
												// machine
		} catch (UnknownHostException e) {
		}

		try {
			if (statusValue != null && statusValue.length() > 0 && (statusValue.equalsIgnoreCase("Passed") || statusValue.equalsIgnoreCase("Failed"))) {
				// String getLastUpdateTimeStamp =
				// "Select LAST_UPDT_TMSP from E2E_TESTCASES where SEQ_NUM = '"+Id+"'";
				String getLastUpdateTimeStamp = "Select LAST_UPDT_TMSP from E2E_PC_INIT_TESTCASES where SEQ_NUM = '" + Id + "'";
				Statement st1 = ucaConn.createStatement();
				rs = st1.executeQuery(getLastUpdateTimeStamp);
				if (rs.next()) {
					lastUpdateTimeStamp = rs.getString(1);
				}

				if (lastUpdateTimeStamp != null && lastUpdateTimeStamp.length() > 0) {
					Date date1;
					try {
						date1 = format.parse(lastUpdateTimeStamp);
						long difference = curDate.getTime() - date1.getTime(); // milliseconds
						long diffMinutes = difference / (60 * 1000) % 60;
						duration = String.valueOf(diffMinutes);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

				st1.close();
			}
			// setting the computername as grid
			computerName = "GRID";
			// String query = "MERGE INTO E2E_TESTCASES bi USING (Select '"+ Id
			// +"' as SEQ_NUM from dual) bo ON bi.SEQ_NUM = bo.SEQ_NUM" +
			// " WHEN MATCHED THEN Update set TEST_CASE_STAT_DESC = '"+
			// statusValue +"', EXEC_ELAPS_MIN='"+duration
			// +"',EXEC_MACH_NM ='"+computerName+"',LAST_UPDT_TMSP = '"+dateToStr
			// +"' WHEN NOT MATCHED THEN insert(TEST_CASE_STAT_DESC,EXEC_ELAPS_MIN,
			// EXEC_MACH_NM, LAST_UPDT_TMSP) values ( '"+
			// statusValue +"','"+duration+"','"+computerName +"','"+dateToStr
			// +"' ) ";
			String query = "MERGE INTO E2E_PC_INIT_TESTCASES bi USING (Select '" + Id + "' as SEQ_NUM from dual) bo ON bi.SEQ_NUM = bo.SEQ_NUM" + " WHEN MATCHED THEN Update set TEST_CASE_STAT_DESC = '" + statusValue + "', EXEC_ELAPS_MIN='" + duration + "',EXEC_MACH_NM ='" + computerName + "',LAST_UPDT_TMSP = '" + dateToStr + "' WHEN NOT MATCHED THEN insert(TEST_CASE_STAT_DESC,EXEC_ELAPS_MIN, EXEC_MACH_NM, LAST_UPDT_TMSP) values ( '" + statusValue + "','" + duration + "','" + computerName + "','"
					+ dateToStr + "' ) ";

			log.info("executing query = " + query);
			status = st.execute(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			/*
			 * try { st.close(); ucaConn.close(); } catch (SQLException e) { // TODO
			 * Auto-generated catch block e.printStackTrace(); }
			 */
		}
		return status;

	}

	public void populateTestExecStatus(ArrayList<HashMap<String, Object>> resultListRows) {
		log = LoggerClass.getThreadLogger("Thread" + Thread
				.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));

		try {

			String query = "INSERT INTO TEST_EXEC_STATUS (TEST_CASE_ID,TEST_CASE_NM,TEST_CASE_STAT_DESC,MODULE,EXEC_MACH_NM ) " + "VALUES (?,?,'Initialized',?,?)";
			String machineName = SCRCommon.getMachineName();
			PreparedStatement statement = ucaConn.prepareStatement(query);
			if (!resultListRows.isEmpty()) {

				for (HashMap<String, Object> processRow : resultListRows) {
					// query = ""+(String)processRow.get("ID")+"','"
					// +(String)processRow.get("TestCaseName")+"','"
					// +"Initialized"+"','"
					// +(String)processRow.get("Use Case")+"'";
					statement.setString(1, (String) processRow.get("ID"));
					statement.setString(2, (String) processRow.get("TestCaseName"));
					statement.setString(3, (String) processRow.get("Use Case"));
					statement.setString(4, machineName);
					statement.executeUpdate();
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public boolean updateTestExecMachine(String testCaseID, String execMachineName) {
		log = LoggerClass.getThreadLogger("Thread" + Thread
				.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
		boolean status = false;
		try {

			String threadId = String.valueOf(Thread.currentThread().getId());
			// String query = "MERGE INTO TRANS bi USING (Select '"+
			// e2eTestCaseId
			// +"' as E2E_TCASE_ID from dual) bo ON bi.E2E_TCASE_ID = bo.E2E_TCASE_ID"
			// +
			// " WHEN MATCHED THEN Update set POLICYNO = '"+ policyNumber
			// +"', APPLICATION = 'Selenium', LAST_UPDATE_TIMESTAMP ='"+dateToStr
			// +"', E2E_TCASE_ID = '"+e2eTestCaseId
			// +"' WHEN NOT MATCHED THEN insert(POLICYNO, E2E_TCASE_ID, APPLICATION,
			// LAST_UPDATE_TIMESTAMP) values ( '"+
			// policyNumber +"','"+e2eTestCaseId +"','Selenium','"+dateToStr+
			// "') ";
			String query = "update TEST_EXEC_STATUS set EXEC_START_TMSP = sysdate, EXEC_THREAD_ID ='" + threadId + "', EXEC_MACH_NM = '" + execMachineName + "', TEST_CASE_STAT_DESC ='In Progress' where TEST_CASE_ID = '" + testCaseID + "'";
			st = ucaConn.createStatement();
			status = true;
			st.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnections();
			/*
			 * try { st.close(); ucaConn.close(); } catch (SQLException e) { // TODO
			 * Auto-generated catch block e.printStackTrace(); }
			 */
		}
		return status;

	}

	public boolean updateTestExecStatus(String testCaseID, String status) {
		log = LoggerClass.getThreadLogger("Thread" + Thread
				.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
		boolean isSucess = false;
		try {
			// String query = "MERGE INTO TRANS bi USING (Select '"+
			// e2eTestCaseId
			// +"' as E2E_TCASE_ID from dual) bo ON bi.E2E_TCASE_ID = bo.E2E_TCASE_ID"
			// +
			// " WHEN MATCHED THEN Update set POLICYNO = '"+ policyNumber
			// +"', APPLICATION = 'Selenium', LAST_UPDATE_TIMESTAMP ='"+dateToStr
			// +"', E2E_TCASE_ID = '"+e2eTestCaseId
			// +"' WHEN NOT MATCHED THEN insert(POLICYNO, E2E_TCASE_ID, APPLICATION,
			// LAST_UPDATE_TIMESTAMP) values ( '"+
			// policyNumber +"','"+e2eTestCaseId +"','Selenium','"+dateToStr+
			// "') ";
			String query = "update TEST_EXEC_STATUS set EXEC_END_TMSP = sysdate, TEST_CASE_STAT_DESC = '" + status + "' where TEST_CASE_ID = '" + testCaseID + "'";
			st = ucaConn.createStatement();
			isSucess = true;
			st.executeUpdate(query);
			log.info("updated : " + query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// closeConnections();
			/*
			 * try { st.close(); ucaConn.close(); } catch (SQLException e) { // TODO
			 * Auto-generated catch block e.printStackTrace(); }
			 */
		}
		return isSucess;

	}

	public boolean populateDataFeed(String testCaseID, String valueToUpdate) {
		log = LoggerClass.getThreadLogger("Thread" + Thread
				.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
		boolean isSucess = false;

		try {

			// String query = "MERGE INTO TRANS bi USING (Select '"+
			// e2eTestCaseId
			// +"' as E2E_TCASE_ID from dual) bo ON bi.E2E_TCASE_ID = bo.E2E_TCASE_ID"
			// +
			// " WHEN MATCHED THEN Update set POLICYNO = '"+ policyNumber
			// +"', APPLICATION = 'Selenium', LAST_UPDATE_TIMESTAMP ='"+dateToStr
			// +"', E2E_TCASE_ID = '"+e2eTestCaseId
			// +"' WHEN NOT MATCHED THEN insert(POLICYNO, E2E_TCASE_ID, APPLICATION,
			// LAST_UPDATE_TIMESTAMP) values ( '"+
			// policyNumber +"','"+e2eTestCaseId +"','Selenium','"+dateToStr+
			// "') ";
			String query = null;
			st = ucaConn.createStatement();
			if (valueToUpdate != null && !valueToUpdate.isEmpty()) {
				Map<String, String> statusMap = new HashMap<String, String>();
				statusMap.put("IN_PROGRESS", "In Progress");
				statusMap.put("PASS", "Pass");
				statusMap.put("FAIL", "Fail");
				query = "select ID from DATA_FEED where TEST_CASE_ID='" + testCaseID + "'";
				String machineName = SCRCommon.getMachineName();
				rs = st.executeQuery(query);
				String id = null;
				if (rs.next()) {
					id = rs.getString(1);
				}
				if (id == null) {
					query = "insert into DATA_FEED(TEST_CASE_ID,status,LAST_UPDT_TMSP,EXEC_MACH_NM) values('" + testCaseID + "','" + statusMap.get(valueToUpdate) + "',sysdate,'" + machineName + "')";
				} else {
					query = "update DATA_FEED set status = '" + statusMap.get(valueToUpdate) + "', LAST_UPDT_TMSP=sysdate where id = '" + id + "'";
				}
			}
			log.info("Regreession :: query ::" + valueToUpdate + query);

			if (query != null) {

				st.executeUpdate(query);
				isSucess = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnections();
			/*
			 * try { st.close(); ucaConn.close(); } catch (SQLException e) {
			 * e.printStackTrace(); }
			 */
		}
		return isSucess;

	}

	public void closeConnections() {
		try {
			if (st != null)
				st.close();
			// ucaConn.close();
			DBConnectionManager.getInstance().returnConnectionToPool(ucaConn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * public void removeExistingData() { try { st = ucaConn.createStatement();
	 * 
	 * String query = "insert into TEST_EXEC_STat_LOG" +
	 * " (ID,TEST_CASE_ID,TEST_CASE_NM,TEST_CASE_STAT_DESC,EXEC_START_TMSP,EXEC_END_TMSP,EXEC_THREAD_ID,EXEC_MACH_NM,MODULE,COMPONENT_SUMMARY) "
	 * + " (select * from TEST_EXEC_STATUS)"; st.executeUpdate(query);
	 * 
	 * query = "delete from TEST_EXEC_STATUS"; st.executeUpdate(query);
	 * 
	 * query = "delete from data_feed"; st.executeUpdate(query); } catch (Exception
	 * e) { e.printStackTrace(); }finally{ closeConnections(); } }
	 */

	public void removeExistingData() {
		log = LoggerClass.getThreadLogger("Thread" + Thread
				.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
		try {
			st = ucaConn.createStatement();

			String query = "insert into TEST_EXEC_STAT_LOG" + " (ID, TEST_CASE_ID, TEST_CASE_NM, TEST_CASE_STAT_DESC, EXEC_START_TMSP, EXEC_END_TMSP, EXEC_THREAD_ID, EXEC_MACH_NM, MODULE, COMPONENT_SUMMARY, FAILURE_REASON) " + " (select ID, TEST_CASE_ID, TEST_CASE_NM, TEST_CASE_STAT_DESC, EXEC_START_TMSP, EXEC_END_TMSP, EXEC_THREAD_ID, EXEC_MACH_NM, MODULE, COMPONENT_SUMMARY, FAILURE_REASON from TEST_EXEC_STATUS)";
			st.executeUpdate(query);

			query = "UPDATE TEST_EXEC_STAT_LOG set batch_ID=SYSDATE where batch_id is null";
			st.executeUpdate(query);

			String machineName = SCRCommon.getMachineName();
			String statuss = "completed";

			/*
			 * query = "delete from TEST_EXEC_STATUS where EXEC_MACH_NM = '"+machineName
			 * +"' "; st.executeUpdate(query); query =
			 * "delete from data_feed where EXEC_MACH_NM = '"+machineName+"' ";
			 * st.executeUpdate(query);
			 */

			query = "delete from TEST_EXEC_STATUS where EXEC_MACH_NM = '" + machineName + "'  and EXECUTION = '" + statuss + "' ";
			st.executeUpdate(query);

			query = "delete from data_feed where EXEC_MACH_NM = '" + machineName + "'  and EXECUTION = '" + statuss + "' ";
			st.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnections();
		}
	}

	public void updateTotalExecutionStatus() {
		log = LoggerClass.getThreadLogger("Thread" + Thread
				.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
		String executionstatus = "completed";

		try {
			st = ucaConn.createStatement();
			String queryTest = "update Test_Exec_Status set Execution = '" + executionstatus + "' ";
			st.executeUpdate(queryTest);
			String querydata = "update Data_feed set Execution = '" + executionstatus + "' ";
			st.executeUpdate(querydata);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
