package com.pc.driver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;

import com.pc.constants.PCConstants;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.E2ETestCaseUtil;
import com.pc.utilities.HTML;
import com.pc.utilities.LocalDriverFactory;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.ManagerPhantomJS;
import com.pc.utilities.PhantomJSDriverFactory;
import com.pc.utilities.RemoteDriverFactory;
import com.pc.utilities.ThreadCache;
import com.pc.utilities.ThreadCacheManager;

public class ParallelExecutor implements Runnable {

	private String strRunMode = null;
	private String strTestCaseName = null;
	private String workbook = null;
	private String region = null;
	//static Logger log = Logger.getLogger(ParallelExecutor.class);
	private org.apache.log4j.Logger log;
	// added E2E Framework integration start
	private boolean isE2EExecution = false;
	// added E2E Framework integration end


	public ParallelExecutor(String strRunMode, String strTestCaseName, String workbook, String region) {
		this.strRunMode = strRunMode;
		this.strTestCaseName = strTestCaseName;
		this.workbook = workbook;
		this.region = region;
	}

	//@Override
	public void run() {
		List<String> acList = new ArrayList<String>();
		File f= new File("C:/Selenium/WorkSpace/LatestPC/Reports/Log/"+strTestCaseName+".log");
		System.out.println("f is in parallelexecutor is:::"+f);
		if(f.exists()){
			f.delete();
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("In run:::");
		 log = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),strTestCaseName);

		log.info("Starting Thread Id =" + Thread.currentThread().getId() + "Executing testcase = " + strTestCaseName);
		// added E2E Framework integration start
		boolean isTestCasePass = false;
		E2ETestCaseUtil e2eTestCaseUtil = null;
		if (strTestCaseName != null && strTestCaseName.length() > 0 && strTestCaseName.contains("|")) {

			isE2EExecution = true;
			e2eTestCaseUtil = new E2ETestCaseUtil();
			strTestCaseName = e2eTestCaseUtil.e2eInitE2ETestCaseExecution(strTestCaseName);
		}
		// added E2E Framework integration end

		WebDriver driver = null;
		String execMode = HTML.properties.getProperty("EXECUTIONMODE");
		System.out.println("execMode is :::"+execMode);
		if (HTML.properties.getProperty("TypeOfAutomation").equalsIgnoreCase("HEADLESS")) {
			System.out.println("execMode is  in if:::"+execMode);
			PhantomJSDriverService service = PhantomJSDriverFactory.getInstance().createPhantomJSDriver();
			ManagerPhantomJS.getInstance().setPhantomJSDriverService(service);
		}
		if (execMode.equalsIgnoreCase(PCConstants.executionModeLocal)) {
			System.out.println("::::");
			driver = LocalDriverFactory.getInstance().createNewDriver();
		} else {
			System.out.println("execMode is in else :::"+execMode);
			driver = RemoteDriverFactory.getInstance().createNewDriver();
		}
		Common common = new Common();
		CommonManager.getInstance().setCommon(common);
		ManagerDriver.getInstance().setWebDriver(driver);
		
		ThreadCacheManager tm = new ThreadCacheManager();
        //ThreadCacheManager tm =  ThreadCache.getInstance();
        ThreadCache.getInstance().setThreadCacheManager(tm);
		log.info("Thread ID = " + Thread.currentThread().getId() + " common = " + common);
		try {
			System.out.println("iT IS IN isTestCasePass");
			isTestCasePass = common.RunTest("RunModeNo", strTestCaseName, workbook, region);
			System.out.println("iT IS IN isTestCasePass:::" + isTestCasePass);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("Error while executing test case = " + strTestCaseName, e);
		}

		// added E2E Framework integration start
		if (isE2EExecution && isTestCasePass) {
			e2eTestCaseUtil.updateTestResultsintoDB();
			e2eTestCaseUtil.upDateE2EStatus("Passed");
			e2eTestCaseUtil = null;// Mark for Garbage collection
		} else if (isE2EExecution && !isTestCasePass) {
			e2eTestCaseUtil.upDateE2EStatus("Failed");
			e2eTestCaseUtil = null;// Mark for Garbage collection
		}
		// added E2E Framework integration end

		common = null; // Mark for garbage collection

	}
}
