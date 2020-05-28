package com.pc.screen;

import java.io.File;
import java.io.FileFilter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.filefilter.WildcardFileFilter;
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

import org.apache.commons.io.comparator.LastModifiedFileComparator;

public class DownloadLogFile {

	public static String sheetname = "DownloadLogFile";
	Common common = CommonManager.getInstance().getCommon();
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();

	public Boolean SCRDownloadLogFile() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);

		if (!status) {
			status = false;
		}
		return status;
	}

	/**
	 * Function used to sleect the env form list box
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean SelectEnvironment() throws Exception {
		boolean status = false;
		status = common.SafeAction(Common.o.getObject("drpWEISS_EnvName"), HTML.properties.getProperty("Region"), "drpWEISS_EnvName");
		status = common.SafeAction(Common.o.getObject("btnWEISS_Continue"), "btnWEISS_Continue", "btnWEISS_Continue");
		return status;

	}

	/**
	 * Function used to navigate to tha particluar port
	 * 
	 * @param funValue
	 * @return
	 * @throws Exception
	 */
	public boolean Select_LogPort(String funValue) throws Exception {
		boolean status = false;
		boolean bFlag = false;
		List<WebElement> gPortList = ManagerDriver.getInstance().getWebDriver().findElements(Common.o.getObject("btnWEISS_PortList"));
		for (int i = 0; i < gPortList.size(); i++) {
			String strListValue = gPortList.get(i).getText();
			try {
				if (strListValue.equalsIgnoreCase(funValue)) {
					System.out.println(gPortList.get(i).getText());
					gPortList.get(i).click();
					bFlag = true;
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Thread ID = " + Thread.currentThread().getId() + " Error Occured =" + e.getMessage(), e);
				bFlag = false;
			}
		}
		if (bFlag) {
			logger.info("Port specified in Datasheet" + funValue);
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "Port specified in Datasheet should be displayed and clicked", "Port specified dispalyed -" + funValue, "PASS");
			status = true;
		} else {
			logger.info("Port specified in Datasheet" + funValue);
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "Port specified in Datasheet should be displayed and clicked", "Port specified not dispalyed -" + funValue, "FAIL");
			status = false;
		}
		return status;

	}

	/**
	 * function used to navigate to particular path and download a file and rename.
	 * 
	 * @param funValue
	 * @return
	 * @throws Exception
	 */
	public Boolean downloadLogfile(String funValue) throws Exception {
		boolean status = false;
		String strPort = null;
		String strLogFileName = null;
		// funValue=funValue.concat(":::"+funValue);
		// String[] sPair = funValue.split(":::");
		String[] sValue = funValue.split(":::");

		// String LOGGED_SERVERNAME="-compasltq2pccluster-srv2"; //To test
		if (sValue[4].equalsIgnoreCase("PORT1")) {
			strPort = HTML.properties.getProperty("WEISS_PORT1");
		} else if (sValue[4].equalsIgnoreCase("PORT2")) {
			strPort = HTML.properties.getProperty("WEISS_PORT2");
		}
		String sPrimaryWindow = driver.getWindowHandle();
		String strexp = "user_projects/" + HTML.properties.getProperty("Region").toLowerCase() + "-compas";
		java.util.Set<String> allWindowHandles = driver.getWindowHandles();
		Path downloadFile = Paths.get(PCThreadCache.getInstance().getProperty(PCConstants.FormsFolderPath));
		for (String handle : allWindowHandles) {

			if (driver.switchTo().window(handle).getTitle().contains(strexp)) {
				driver.switchTo().window(handle);
				logger.info(driver.getCurrentUrl() + " Current window url ");
				logger.info(driver.switchTo().window(handle).getTitle() + " window is displayed and swithced to that window");
				status = true;
				break;
			}
		}
		if (status) {
			// Switch to the parent window
			strLogFileName = sValue[3] + "-" + PCThreadCache.getInstance().getProperty("LOGGED_SERVERNAME") + ".log"; // ddressStandardizationLog-compasltq2pccluster-srv1.log;
			// strLogFileName=sValue[3]+LOGGED_SERVERNAME+".log"; //
			// AddressStandardizationLog-compasltq2pccluster-srv1.log;

			status = ActiononTable_withoutTr(Common.o.getObject("eleWEISS_FielList"), 1, 1, sValue[0]);
			status = ActiononTable_withoutTr(Common.o.getObject("eleWEISS_FielList"), 1, 1, sValue[1]);
			status = ActiononTable_withoutTr(Common.o.getObject("eleWEISS_FielList"), 1, 1, sValue[2]);
			status = common.SafeAction(Common.o.getObject("btnWEISS_Date"), "YES", "btn");
			status = common.SafeAction(Common.o.getObject("btnWEISS_Date"), "YES", "btn");
			// specific log
			status = ActiononTable_withoutTr(Common.o.getObject("eleWEISS_FielList"), 1, 5, strLogFileName);
			if (status) {
				File file = getTheNewestFile(PCThreadCache.getInstance().getProperty(PCConstants.FormsFolderPath), "log");
				file.renameTo(downloadFile.resolve("WEISS_Log_" + strPort + ".txt").toFile());
				String RenamedPath = "WEISS_Log_" + strPort + ".txt";
				PCThreadCache.getInstance().setProperty(PCConstants.RENAMEDPATH, RenamedPath);
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Log file should be downloaded form WEISS lad1wlahq2024 (lad1wlahq2024) Portal and renamed", " Fiel downloaded successfully adn renamed ", "PASS");
				driver.switchTo().window(sPrimaryWindow);
			} else {

				driver.switchTo().window(sPrimaryWindow);
				status = Select_LogPort(HTML.properties.getProperty("WEISS_PORT2"));

				for (String handle : allWindowHandles) {

					if (driver.switchTo().window(handle).getTitle().contains(strexp)) {
						driver.switchTo().window(handle);
						logger.info(driver.getCurrentUrl() + " Current window url ");
						logger.info(driver.switchTo().window(handle).getTitle() + " window is displayed and swithced to that window");
						status = true;
						break;
					}
				}

				status = ActiononTable_withoutTr(Common.o.getObject("eleWEISS_FielList"), 1, 1, sValue[0]);
				status = ActiononTable_withoutTr(Common.o.getObject("eleWEISS_FielList"), 1, 1, sValue[1]);
				status = ActiononTable_withoutTr(Common.o.getObject("eleWEISS_FielList"), 1, 1, sValue[2]);
				status = common.SafeAction(Common.o.getObject("btnWEISS_Date"), "YES", "btn");
				status = common.SafeAction(Common.o.getObject("btnWEISS_Date"), "YES", "btn");
				status = ActiononTable_withoutTr(Common.o.getObject("eleWEISS_FielList"), 1, 5, strLogFileName);
				File file = getTheNewestFile(PCThreadCache.getInstance().getProperty(PCConstants.FormsFolderPath), "log");
				file.renameTo(downloadFile.resolve("WEISS_Log_" + HTML.properties.getProperty("WEISS_PORT2") + ".txt").toFile());
				String RenamedPath = "WEISS_Log_" + HTML.properties.getProperty("WEISS_PORT2") + ".txt";
				PCThreadCache.getInstance().setProperty(PCConstants.RENAMEDPATH, RenamedPath);
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Log fiel should be downloaded form WEISS lad1wlahq2025 (lad1wlahq2025) Portal and renamed", " Fiel downloaded successfully adn renamed ", "PASS");
				driver.switchTo().window(sPrimaryWindow);

			}

		} else {
			status = false;
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "Switch to Classify window", " Could not swithc to classify window", "FAIL");
		}

		return status;
	}

	/**
	 * Function used to get the Newest file form the location to rename
	 * 
	 * @param filePath
	 * @param ext
	 * @return
	 */
	public File getTheNewestFile(String filePath, String ext) {
		File theNewestFile = null;
		File dir = new File(filePath);
		FileFilter fileFilter = new WildcardFileFilter("*." + ext);

		try {
			Thread.sleep(Integer.parseInt(HTML.properties.getProperty("FORMSWAIT")));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			System.out.println("Exception");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("Exception");
		}
		File[] files = dir.listFiles(fileFilter);

		if (files.length > 0) {
			try {
				/** The newest file comes first **/
				Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
				theNewestFile = files[0];
			} catch (Exception e) {
				System.out.println("Exception - in Finding the Files in download path");
			}

		} else {
			System.out.println("No files found with extension in download path");
		}
		return theNewestFile;
	}

	/**
	 * Function used to click on table without using tr tag.
	 * 
	 * @param obj
	 * @param readCol
	 * @param actionCol
	 * @param sValue
	 * @return
	 * @throws Exception
	 */
	public static boolean ActiononTable_withoutTr(By obj, int readCol, int actionCol, String sValue) throws Exception {
		 loggers  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty

("TCID"));
		List<WebElement> allrows = ManagerDriver.getInstance().getWebDriver().findElements(obj);
		boolean SearchString = false;
		for (int i = 0; i <= allrows.size() - 1; i++) {
			List<WebElement> Cells = allrows.get(i).findElements(By.tagName("td"));
			String readText = Cells.get(readCol).getText();
			if (readText.contains(sValue)) {
				SearchString = true;
				Cells.get(1).click();
				WebElement objAntor = Cells.get(actionCol).findElement(By.tagName("a"));
				objAntor.click();
				break;
			}
		}
		if (SearchString) {
			loggers.info("Search String available in the table. '" + sValue + "'");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "System should search string in table and Search string is '" + sValue + "'", "System searched string in table and search string is  '" + sValue + "'", "PASS");
		} else {
			loggers.info("Search String not available in the table. '" + sValue + "'");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search string in table and Search string is '" + sValue + "'", "System searched string in table and search string is  '" + sValue + "'", "PASS");
			SearchString = false;
		}
		return SearchString;

	}

}