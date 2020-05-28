package com.pc.driver;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.*;
import com.pc.constants.PCConstants;
import com.pc.utilities.CommonManager;
import com.pc.utilities.FlatFile;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.E2ETestCaseUtil;
import com.pc.utilities.HTML;
import com.pc.utilities.LocalDriverFactory;
import com.pc.utilities.RemoteDriverFactory;
import com.pc.utilities.Common;
import com.pc.utilities.XlsxReader;

public class Driver {
	static Logger log = Logger.getLogger(Common.class);
	private Socket socket = null;
	private boolean isShutdownReceied = false;
	public static BufferedWriter writer;
	// static boolean isFlag =false;
	// private DataInputStream console = null;
	// private DataOutputStream streamOut = null;
	private DataInputStream serverStreamIn = null;
	private static ConcurrentLinkedQueue<String> testCaseNames = new ConcurrentLinkedQueue<String>();

	@BeforeSuite
	public void loadConfig() throws Exception {

		PropertyConfigurator.configure("log4j.properties");
		try {
			HTML.fnSummaryInitialization("Execution Summary Report");
			System.out.println("end2endTestCaseName = executeTestCase=");
			// commented below line for end to end framework
			// XlsxReader.getInstance().addTestCasesFromDataSheetName(testCaseNames);
			System.out.println("Inside BeforeSuite");
			// console = new DataInputStream(System.in);
			// streamOut = new DataOutputStream(socket.getOutputStream());
			FlatFile ff = FlatFile.getInstance();
			writer = ff.CreateTextFile();
			if (HTML.properties.getProperty("E2E").equalsIgnoreCase("YES")) {
				for (int i = 0; i < Integer.parseInt(HTML.properties.getProperty("VERYLONGWAIT")); i++) {
					try {
						Thread.sleep(5000);
						socket = new Socket("10.205.83.65", 8001);
						DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
						outputStream.writeUTF("Selenium_PC_CLA");
						serverStreamIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
						break;
					} catch (InterruptedException e) {

					} catch (java.net.ConnectException e) {
						System.out.println("waiting for E2E server to open connection on port 8001. Exception details below");
						e.printStackTrace();
					}
				}
			} else {
				boolean status = false;
				PropertyConfigurator.configure("log4j.properties");
				try {
					HTML.fnSummaryInitialization("Execution Summary Report");
					status = XlsxReader.getInstance().addTestCasesFromDataSheetName(testCaseNames);
					System.out.println("Inside BeforeSuite");
					if (!status) {
						log.info("None of the testcase selected as 'YES' to execute");
						System.out.println("None of the testcase selected as 'YES' to execute");
						System.exit(0);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log.error("Thread ID = " + Thread.currentThread().getId() + " Error Occured =" + e.getMessage(), e);
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Parameters({ "DataSheetName", "Region", "end2endTestCaseName", "executeTestCase" })
	// @Test(priority=1,enabled=true, invocationCount = 1,threadPoolSize=1)
	@Test(priority = 1, enabled = true)
	public void testParallele2e3(String DataSheetName, String Region, String end2endTestCaseName, String executeTestCase) throws Exception {
		// System.out.println("end2endTestCaseName =" + end2endTestCaseName +"
		// executeTestCase="+executeTestCase);
		System.out.println("end2endTestCaseName =" + end2endTestCaseName + " executeTestCase=" + executeTestCase);
		// testCaseNames.add(executeTestCase);
		// check the status the tc is piked the thread
		boolean exitLoop = false;
		boolean isTestCasePass = false;
		String testCaseName = null;
		// System.out.println("waiting ........");
		// Thread.sleep(10000);
		// System.out.println("execution started.....");
		try {
			testCaseName = testCaseNames.remove();
		} catch (java.util.NoSuchElementException e) {
			exitLoop = true;
		}
		if (testCaseName == null) {
			exitLoop = true;
		}

		if (!isShutdownReceied)
			exitLoop = false;
		else
			exitLoop = true;
		System.out.println("e2e3 before loop : testcaseName:" + testCaseName + " , exitLoop" + exitLoop);
		while (!exitLoop) {
			// while(true){
			if (testCaseName != null) { // added this if condition for end to end testing
				System.out.println("Running test case in testParallele2e3 = " + testCaseName);
				boolean e2eTesting = false;
				String executorMachine = null;
				// Logic to Handle E2E Test Case execution Start
				E2ETestCaseUtil e2eTestCaseUtil = null;
				if (testCaseName.contains("|")) {
					e2eTesting = true;
					e2eTestCaseUtil = new E2ETestCaseUtil();

					System.out.println("Before test " + testCaseName);
					testCaseName = e2eTestCaseUtil.e2eInitE2ETestCaseExecution(testCaseName);
					System.out.println("After test " + testCaseName);
					executorMachine = e2eTestCaseUtil.getExecutorMachine();
				}
				// Logic to Handle E2E Test Case execution End

				String execMode = HTML.properties.getProperty("EXECUTIONMODE");
				// Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				WebDriver driver = null;
				System.out.println("TestParallel3  Started = " + Thread.currentThread().getId());
				System.out.println(" Mode =" + execMode);
				if (execMode.equalsIgnoreCase(PCConstants.executionModeLocal)) {
					driver = LocalDriverFactory.getInstance().createNewDriver();
				} else {
					driver = RemoteDriverFactory.getInstance().createNewDriver();
				}

				System.out.println("TestParallel3  Started = " + Thread.currentThread().getId());
				System.out.println("Test PArallel3 driver = " + driver);
				Common common = new Common();
				CommonManager.getInstance().setCommon(common);
				ManagerDriver.getInstance().setWebDriver(driver);
				// common.setDriver(driver);
				// common.setCommon(common);
				System.out.println("Thread ID = " + Thread.currentThread().getId() + " common = " + common);
				System.out.println("Checking E2e runtest " + testCaseName + " " + DataSheetName + " " + Region);
				isTestCasePass = common.RunTest("RunModeNo", testCaseName, DataSheetName, Region);
				System.out.println("isTEscasePass " + isTestCasePass + " " + e2eTesting);
				// Logic to Handle E2E Test Case execution Start
				if (e2eTesting && isTestCasePass) {
					e2eTestCaseUtil.updateTestResultsintoDB();
					e2eTestCaseUtil.upDateE2EStatus("Passed");
					e2eTestCaseUtil = null;// Mark for Garbage collection
				} else if (e2eTesting && !isTestCasePass) {
					e2eTestCaseUtil.upDateE2EStatus("Failed");
					e2eTestCaseUtil = null;// Mark for Garbage collection
				}

				// Logic to Handle E2E Test Case execution End

				testCaseName = null;
			}
			try {
				testCaseName = testCaseNames.remove();
			} catch (java.util.NoSuchElementException e) {
				exitLoop = true;
			}
			if (testCaseName == null) {
				exitLoop = true;
			}
			if (!isShutdownReceied)
				exitLoop = false;
			Thread.sleep(5000);
			// System.out.println("in e2e3 loop : testcaseName:"+testCaseName +" ,
			// exitLoop"+exitLoop);
		}
	}

	@Parameters({ "DataSheetName", "Region", "end2endTestCaseName", "executeTestCase" })
	// @Test(priority=1,enabled=true, invocationCount = 1,threadPoolSize=1)
	@Test(priority = 1, enabled = false)
	public void testParallele2e4(String DataSheetName, String Region, String end2endTestCaseName, String executeTestCase) throws Exception {
		// System.out.println("end2endTestCaseName =" + end2endTestCaseName +"
		// executeTestCase="+executeTestCase);
		System.out.println("end2endTestCaseName =" + end2endTestCaseName + " executeTestCase=" + executeTestCase);
		// testCaseNames.add(executeTestCase);

		boolean exitLoop = false;
		boolean isTestCasePass = false;
		String testCaseName = null;
		// System.out.println("waiting ........");
		// Thread.sleep(10000);
		// System.out.println("execution started.....");
		try {
			testCaseName = testCaseNames.remove();
			// update the status as "In Progress" in E2E_Testcase
		} catch (java.util.NoSuchElementException e) {
			exitLoop = true;
		}
		if (testCaseName == null) {
			exitLoop = true;
		}
		if (!isShutdownReceied)
			exitLoop = false;
		else
			exitLoop = true;
		System.out.println("e2e4 before loop : testcaseName:" + testCaseName + " , exitLoop" + exitLoop);
		while (!exitLoop) {
			// while(true){
			if (testCaseName != null) { // added this if condition for end to end testing
				System.out.println("Running test case in testParallele2e3 = " + testCaseName);
				boolean e2eTesting = false;
				String executorMachine = null;
				// Logic to Handle E2E Test Case execution Start
				E2ETestCaseUtil e2eTestCaseUtil = null;
				if (testCaseName.contains("|")) {
					e2eTesting = true;
					e2eTestCaseUtil = new E2ETestCaseUtil();

					System.out.println("Before test " + testCaseName);
					testCaseName = e2eTestCaseUtil.e2eInitE2ETestCaseExecution(testCaseName);
					System.out.println("After test " + testCaseName);
					executorMachine = e2eTestCaseUtil.getExecutorMachine();
				}
				// Logic to Handle E2E Test Case execution End

				String execMode = HTML.properties.getProperty("EXECUTIONMODE");
				// Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				WebDriver driver = null;
				System.out.println("TestParallel3  Started = " + Thread.currentThread().getId());
				System.out.println(" Mode =" + execMode);
				if (execMode.equalsIgnoreCase(PCConstants.executionModeLocal)) {
					driver = LocalDriverFactory.getInstance().createNewDriver();
				} else {
					driver = RemoteDriverFactory.getInstance().createNewDriver();
				}

				System.out.println("TestParallel3  Started = " + Thread.currentThread().getId());
				System.out.println("Test PArallel3 driver = " + driver);
				Common common = new Common();
				CommonManager.getInstance().setCommon(common);
				ManagerDriver.getInstance().setWebDriver(driver);
				// common.setDriver(driver);
				// common.setCommon(common);
				System.out.println("Thread ID = " + Thread.currentThread().getId() + " common = " + common);
				System.out.println("Checking E2e runtest " + testCaseName + " " + DataSheetName + " " + Region);
				isTestCasePass = common.RunTest("RunModeNo", testCaseName, DataSheetName, Region);
				System.out.println("isTEscasePass " + isTestCasePass + " " + e2eTesting);
				// Logic to Handle E2E Test Case execution Start
				if (e2eTesting && isTestCasePass) {
					e2eTestCaseUtil.updateTestResultsintoDB();
					e2eTestCaseUtil.upDateE2EStatus("Passed");
					e2eTestCaseUtil = null;// Mark for Garbage collection
				} else if (e2eTesting && !isTestCasePass) {
					e2eTestCaseUtil.upDateE2EStatus("Failed");
					e2eTestCaseUtil = null;// Mark for Garbage collection
				}
				// Logic to Handle E2E Test Case execution End
				testCaseName = null;
			}
			try {
				testCaseName = testCaseNames.remove();
				// update the status as "In Progress" in E2E_Testcase
			} catch (java.util.NoSuchElementException e) {
				exitLoop = true;
			}
			if (testCaseName == null) {
				exitLoop = true;
			}
			if (!isShutdownReceied)
				exitLoop = false;
			Thread.sleep(5000);
			// System.out.println("in e2e4 loop : testcaseName:"+testCaseName +" ,
			// exitLoop"+exitLoop);
		}
	}

	// added this method for end to end testing
	@Parameters({ "DataSheetName", "Region", "end2endTestCaseName", "executeTestCase" })
	@Test(priority = 1, enabled = true)
	public void populateConcurrentLinkedQueue(String DataSheetName, String Region, String end2endTestCaseName, String executeTestCase) throws Exception {
		String testCaseName = null;
		while (testCaseName == null || (!"shutdown".equalsIgnoreCase(testCaseName))) {
			testCaseName = null;
			testCaseName = serverStreamIn.readUTF();// reading from socket //get the record which ready to pick up is "Y"
			System.out.println("Message Received From Server =" + testCaseName);
			if (testCaseName != null && !testCaseName.equalsIgnoreCase("shutdown")) {
				// update the status as "Waiting" in E2E_Testcase
				testCaseNames.add(testCaseName); // populating into concurrentlinkedqueue
				// isFlag = true;
				System.out.println("Added TestCase Into concurrent linked queue. TestCaseName Received from Server =" + testCaseName);
			} else if (testCaseName.equalsIgnoreCase("shutdown")) {
				isShutdownReceied = true;
			}
			System.out.println("Waiting for Server to send TestCaseName to execute....");
			if (testCaseName.equalsIgnoreCase("shutdown")) {
				break;
			}

			/*
			 * System.out.println("Enter Message to send to Server =" + textFromServer);
			 * testCaseName = console.readLine();//DataInputStream.readUTF(console);
			 * streamOut.writeUTF(line); streamOut.flush();
			 */
		}
		// System.exit(0);
	}

	@AfterSuite(alwaysRun = true, enabled = true)
	public void exitConfig() {

		try {
			XlsxReader.getInstance().closeConnections();
			HTML.fnSummaryCloseHtml(HTML.properties.getProperty("Release"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Inside AfterSuite");
	}

}
