package com.pc.driver;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.pc.utilities.FlatFile;
import com.pc.utilities.HTML;
import com.pc.utilities.XlsxReader;

public class ParallelE2EDriver {
	
	static Logger log = null;
	//private org.apache.log4j.Logger log;

	private Socket socket = null;
	// private DataInputStream console = null;
	// private DataOutputStream streamOut = null;
	private DataInputStream serverStreamIn = null;

	private static ConcurrentLinkedQueue<String> testCaseNames = new ConcurrentLinkedQueue<String>();

	@BeforeSuite
	public void loadConfig() throws Exception {

		PropertyConfigurator.configure("log4j.properties");
		log = Logger.getLogger(ParallelE2EDriver.class);
		//log = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),strTestCaseName);
		try {
			HTML.fnSummaryInitialization("Execution Summary Report");
			System.out.println("end2endTestCaseName = executeTestCase=");
			// commented below line for end to end framework
			// XlsxReader.getInstance().addTestCasesFromDataSheetName(testCaseNames);
			log.info("Inside BeforeSuite");
			for (int i = 0; i < Integer.parseInt(HTML.properties.getProperty("VERYLONGWAIT")); i++) {
				try {
					Thread.sleep(5000);
					socket = new Socket("localhost", 9900);
					serverStreamIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
					break;
				} catch (InterruptedException e) {

				} catch (java.net.ConnectException e) {
					log.info("waiting for E2E server to open connection on port 9900. Exception details below");
					e.printStackTrace();
				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Parameters({ "DataSheetName", "Region", "end2endTestCaseName", "executeTestCase" })
	@Test(priority = 1, enabled = true)
	public void testParallele2e3(String DataSheetName, String Region, String end2endTestCaseName, String executeTestCase) throws Exception {

		log.info("Test NG Thread Started....");

		boolean exitLoop = false;
		boolean isTestCasePass = false;
		String testCaseName = null;
		int nThreads = Integer.parseInt((String) HTML.properties.getProperty("THREAD_COUNT"));
		log.info("Total Number of Threads = " + HTML.properties.getProperty("THREAD_COUNT"));
		ExecutorService multiThreadExecutor = Executors.newFixedThreadPool(nThreads);
		try {
			testCaseName = testCaseNames.remove();
		} catch (java.util.NoSuchElementException e) {
			exitLoop = true;
		}
		if (testCaseName == null) {
			exitLoop = true;
		}
		// while(!exitLoop){
		while (true) {
			if (testCaseName != null) { // added this if condition for end to end testing
				log.info("Running test case in testParallele2e3 = " + testCaseName);
				ParallelExecutor parallelExecutor = new ParallelExecutor("RunModeNo", testCaseName, "", Region);
				multiThreadExecutor.execute(parallelExecutor);
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
		}

	}

	// added this method for end to end testing
	@Parameters({ "DataSheetName", "Region", "end2endTestCaseName", "executeTestCase" })
	@Test(priority = 1, enabled = true)
	public void populateConcurrentLinkedQueue(String DataSheetName, String Region, String end2endTestCaseName, String executeTestCase) throws Exception {
		String testCaseName = null;
		while (testCaseName == null || (testCaseName != null && !testCaseName.equalsIgnoreCase("shutdown"))) {
			testCaseName = null;
			testCaseName = serverStreamIn.readUTF();// reading from socket
			log.info("Message Received From Server =" + testCaseName);
			if (testCaseName != null && !testCaseName.equalsIgnoreCase("shutdown")) {
				testCaseNames.add(testCaseName); // populating into concurrentlinkedqueue
				System.out.println("Added TestCase Into concurrent linked queue. TestCaseName Received from Server =" + testCaseName);
			}
			System.out.println("Waiting for Server to send TestCaseName to execute....");
			if (testCaseName.equalsIgnoreCase("shutdown")) {
				System.exit(0);
			}

			/*
			 * System.out.println("Enter Message to send to Server =" + textFromServer);
			 * testCaseName = console.readLine();//DataInputStream.readUTF(console);
			 * streamOut.writeUTF(line); streamOut.flush();
			 */
		}
	}

	@AfterSuite
	public void exitConfig() {

		try {
			XlsxReader.getInstance().closeConnections();
			FlatFile.getInstance().flatFileClose();
			HTML.fnSummaryCloseHtml(HTML.properties.getProperty("Release"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Inside AfterSuite");
	}

}
