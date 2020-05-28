
package com.pc.screen;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.pc.constants.PCConstants;
import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.PCThreadCache;

public class Forms {

	public static String sheetname = "Forms";
	//static Logger logger =Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();
	public static Map<String, String> formNames = new HashMap<String, String>();

	public Boolean SCRForms() throws Exception
	{

		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if(!status)
		{
			return false;
		}

		return status;
	}

	public boolean selectFormToDownload(String sReadString) throws IOException
	{
		boolean blnStatus = false;
		boolean blnSearchString = false;
		String strPageCount = null;
		String PolicyNumber = PCThreadCache.getInstance().getProperty(PCConstants.CACHE_POLICY_NUMBER);
		String[] strSplitValue = sReadString.split(":::");
		formsWait();
		try
		{
			//				blnSearchString = ActionOnTable(Common.o.getObject("eleDocumentFinalizeTable"), 8, 3, strSplitValue[0], "a");
			switch (strSplitValue[0].toUpperCase())
			{
			case "TRANSACTIONTYPE":
				blnSearchString = ActionOnTable(Common.o.getObject("eleDocumentFinalizeTable"), 6, 2, 3, strSplitValue[1], "Policy - "+PolicyNumber, "View", "a");
				break;
			default:
				blnSearchString = ActionOnTable(Common.o.getObject("eleDocumentFinalizeTable"), 6, 2, 3, strSplitValue[1], "Policy - "+PolicyNumber, "View", "a");
				break;
			}
			if(blnSearchString){
				blnStatus = true;
			}
			Outer:
				if(!blnSearchString)
				{
					int icheckNextPageButton = common.ElementSize(Common.o.getObject("eleNextPage"));
					if(icheckNextPageButton > 0)
					{
						strPageCount = common.ReadElement(Common.o.getObject("eleCommonPageNumber"), Integer.valueOf

(HTML.properties.getProperty("LONGWAIT")));
						strPageCount = SCRCommon.getIntFromString(strPageCount);
						for(int i =0; i <Integer.parseInt(strPageCount); i++)
						{
							blnStatus = common.SafeAction(Common.o.getObject("eleNextPage"), "", "eleNextPage");
							//		   					blnStatus = common.SafeAction(Common.o.getObject("eleMyQueuesTitle"), "", "eleMyQueuesTitle");
							//		   					blnSearchString = ActionOnTable(Common.o.getObject("eleDocumentFinalizeTable"), 8, 3, strSplitValue[0], "a");
							switch (strSplitValue[0].toUpperCase())
							{
							case "TRANSACTIONTYPE":
								blnSearchString = ActionOnTable(Common.o.getObject("eleDocumentFinalizeTable"), 6, 2, 3, strSplitValue[1], "Policy - "+PolicyNumber, "View", "a");		   	   			
								break;
							default:
								blnSearchString = ActionOnTable(Common.o.getObject("eleDocumentFinalizeTable"), 6, 2, 3, strSplitValue[1], "Policy - "+PolicyNumber, "View", "a");		   							
								break;
							}
							if(blnSearchString)
							{
								HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search object in the table cell and click on object. Object name is '" +strSplitValue[0] + "'","System searched object in the table and clicked on object. object name is '" + strSplitValue[0] + "'", "PASS");
								break Outer;
							}
						}
					}else{
						logger.info(""+sReadString+" does not exist in Document Table");
					}
				}
			if(!blnSearchString){
				logger.info(""+sReadString+" does not exist in Document Table");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), ""+sReadString+" should available in the my Document Table",""+sReadString+" is not available in the my Document Table", "FAIL");
			}else{
				blnStatus = true;
			}
		}
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), ""+sReadString+" should available in the my Document Table",""+sReadString+" is not available in the my Document Table", "FAIL");
			e.printStackTrace();
		}  
		return blnStatus;  	
	}

	public boolean selectFormforTransaction(String sReadString) throws IOException
	{
		boolean blnStatus = false;
		boolean blnSearchString = false;
		String strPageCount = null;
		String[] strSplitValue = sReadString.split(":::");
		try
		{
			blnSearchString = ActionOnTable(Common.o.getObject("eleDocumentFinalizeTable"), 6, 2, strSplitValue[1], strSplitValue[2], 

"a");
			//				blnSearchString = ActionOnTable(Common.o.getObject("eleDocumentFinalizeTable"), 8, 2, 3, strSplitValue[0], strSplitValue[1], "View", "a");
			if(blnSearchString){
				blnStatus = true;
			}
			Outer:
				//		   		while(!blnSearchString)
				if(!blnSearchString)
				{
					//		   			blnStatus = common.ElementExist(Common.o.getObject("eleNextPage"));
					int icheckNextPageButton = common.ElementSize(Common.o.getObject("eleNextPage"));
					if(icheckNextPageButton > 0)
					{
						strPageCount = common.ReadElement(Common.o.getObject("eleCommonPageNumber"), Integer.valueOf(HTML.properties.getProperty("LONGWAIT")));
						strPageCount = SCRCommon.getIntFromString(strPageCount);
						for(int i =0; i <Integer.parseInt(strPageCount); i++)
						{
							blnStatus = common.SafeAction(Common.o.getObject("eleNextPage"), "", "eleNextPage");
							//		   					blnStatus = common.SafeAction(Common.o.getObject("eleMyQueuesTitle"), "", "eleMyQueuesTitle");
							blnSearchString = ActionOnTable(Common.o.getObject("eleDocumentFinalizeTable"), 6, 2, strSplitValue[1],strSplitValue[2], "a");
							//		   					blnSearchString = ActionOnTable(Common.o.getObject("eleDocumentFinalizeTable"), 8, 2, 3, strSplitValue[0], strSplitValue[1], "View", "a");
							if(blnSearchString)
							{
								HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search object in the table cell and click on object. Object name is '" + strSplitValue[0] + "'","System searched object in the table and clicked on object. object name is '" + strSplitValue[0] + "'", "PASS");
								break Outer;
							}
						}
					}else{
						logger.info(""+sReadString+" does not exist in Document Table");
					}
				}
			if(!blnSearchString){
				logger.info(""+sReadString+" does not exist in Document Table");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), ""+sReadString+" should available in the my Document Table",""+sReadString+" is not available in the my Document Table", "FAIL");
			}else{
				blnStatus = true;
			}
		}
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), ""+sReadString+" should available in the my Document Table",""+sReadString+" is not available in the my Document Table", "FAIL");
			e.printStackTrace();
		}  
		return blnStatus;  	
	}



	public static File WaitForNewFile(Path folder, String extension, int timeout_sec, WebElement element) throws InterruptedException, IOException, Exception {
		/* loggers  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
		JavascriptExecutor js = (JavascriptExecutor) ManagerDriver.getInstance().getWebDriver();
		WebDriverWait wait = new WebDriverWait(ManagerDriver.getInstance().getWebDriver(),Integer.parseInt(HTML.properties.getProperty("VERYLONGWAIT")));
		long end_time = System.currentTimeMillis() + timeout_sec * 1000;
		try (WatchService watcher = FileSystems.getDefault().newWatchService()) {
			folder.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);
			formsWait();
			//		        Thread.sleep(Integer.parseInt(HTML.properties.getProperty("FORMSWAIT")));
			element.click();
			wait.until(ExpectedConditions.stalenessOf(element));
			SCRCommon.JavaScript(js);
			for (WatchKey key; null != (key = watcher.poll(end_time - System.currentTimeMillis(), TimeUnit.MILLISECONDS)); key.reset()) {
				for (WatchEvent<?> event : key.pollEvents()) {
					File file = folder.resolve(((WatchEvent<Path>)event).context()).toFile();
					if (file.toString().toLowerCase().endsWith(extension.toLowerCase()))
						return file;
					loggers.info("Latest downloaded file name is "+file.getName()+"");
				}
			}	
		}*/
		loggers.info("No forms are downloaded recently");
		return null;
	}

	/**
	 * @function This function use to Select the data from the table and click the element accordingly
	 * @param obj,readCol,actionCol,strReadString,actionObjetName
	 * @return status
	 * @throws Exception
	 */
	public Boolean ActionOnTable(By obj, int readCol, int actionCol, String strReadString, String actionObjetName, String sTagName) throws 

Exception
	{	
		boolean Status=false;
		boolean SearchString=false;
		boolean ActionObject=false;
		JavascriptExecutor js = (JavascriptExecutor) ManagerDriver.getInstance().getWebDriver();      
		WebElement mytable = ManagerDriver.getInstance().getWebDriver().findElement(obj);
		List<WebElement> allrows = mytable.findElements(By.tagName("tr"));
		for(int i = 0; i <= allrows.size()-1; i++)
		{
			List<WebElement> Cells = allrows.get(i).findElements(By.tagName("td"));
			String readText = Cells.get(readCol).getText();
			if (readText.contains(strReadString))
			{
				SearchString = true;
				List<WebElement> CellElements = Cells.get(actionCol).findElements(By.tagName(sTagName));
				for(WebElement element: CellElements)
				{
					String objName = element.getText();
					if(objName.contains(actionObjetName))
					{
						//		        		  	Status = true;
						ActionObject = true;
						logger.info(""+actionObjetName+" form found with the "+strReadString+" transaction");
						Path downloadFile = Paths.get(HTML.properties.getProperty("FormsDownloadPath"));
						formsWait();
						File file = WaitForNewFile(downloadFile, ".pdf", 30, element);
						String tcID = PCThreadCache.getInstance().getProperty("TCID");
						String formName = PCThreadCache.getInstance().getProperty

(PCConstants.CACHE_POLICY_NUMBER_SEARCH)+"_"+strReadString+".pdf";
						String actualFormName = PCThreadCache.getInstance().getProperty

(PCConstants.CACHE_POLICY_NUMBER_SEARCH)+"_"+strReadString;
						if(!(file == null)){
							file.renameTo(downloadFile.resolve(formName).toFile());
							logger.info("Form name is renamed to '"+formName+"'");
							if(!formNames.containsKey(tcID))
								formNames.put(tcID, actualFormName);
							else{
								formNames.put(tcID, formNames.get(tcID)+","+actualFormName);
							}
						}else{
							logger.info("Unable to download"+actionObjetName+" form with the "+strReadString+" transaction");
							int downloadForms = common.ElementSize(Common.o.getObject("DownloadLater"));
							if(downloadForms > 0)
							{
								//		                    			 downloadFile = Paths.get(HTML.properties.getProperty("FormsDownloadPath"));
								//		                    			 file = WaitForNewFile(downloadFile, ".pdf", 60, element);
								logger.info("Unable to download"+actionObjetName+" form with the "+strReadString+" transaction");
							}
							//		                    		else{
							//		                    			 logger.info("Unable to download the form "+strReadString1+" whose recpeient name is "+strReadString+"");
							//		                    		}
						}
						Status = SCRCommon.JavaScript(js);
						break;
					}
				}
			}
			if(ActionObject == true)
			{
				break;
			}
		}
		return Status;
	}

	public boolean ActionOnTable(By obj, int readCol, int readCol1, int actionCol, String strReadString, String strReadString1, String 

actionObjetName, String sTagName) throws Exception
	{	
		boolean Status=false;
		boolean ActionObject=false;
		//	 	  createFormDirectory(HTML.properties.getProperty("FormsDownloadPath"));
		JavascriptExecutor js = (JavascriptExecutor) ManagerDriver.getInstance().getWebDriver();      
		WebElement mytable = ManagerDriver.getInstance().getWebDriver().findElement(obj);
		List<WebElement> allrows = mytable.findElements(By.tagName("tr"));
		for(int i = 0; i <= allrows.size()-1; i++)
		{
			List<WebElement> Cells = allrows.get(i).findElements(By.tagName("td"));
			String readText = Cells.get(readCol).getText();
			if(readText.contains(strReadString))
			{
				String readText1 = Cells.get(readCol1).getText();
				if (readText1.contains(strReadString1))
				{
					List<WebElement> CellElements = Cells.get(actionCol).findElements(By.tagName(sTagName));
					for(WebElement element: CellElements)
					{
						String objName = element.getText();
						if(objName.contains(actionObjetName))
						{
							ActionObject = true;
							//	 	             		element.click();
							logger.info("Recepient Name "+strReadString+"found with the Form Name of "+strReadString1);
							//		                        Path downloadFile = Paths.get(HTML.properties.getProperty("FormsDownloadPath"));
							Path downloadFile = Paths.get(PCThreadCache.getInstance().getProperty(PCConstants.FormsFolderPath));
							//		                        formsWait();
							File file = WaitForNewFile(downloadFile, ".pdf", 60, element);
							String tcID = PCThreadCache.getInstance().getProperty("TCID");
							String formName = PCThreadCache.getInstance().getProperty(PCConstants.CACHE_POLICY_NUMBER_SEARCH)+"_"+strReadString+".pdf";
							String actualFormName = PCThreadCache.getInstance().getProperty(PCConstants.CACHE_POLICY_NUMBER_SEARCH)+"_"+strReadString;
							if(!(file == null)){
								file.renameTo(downloadFile.resolve(formName).toFile());
								logger.info("Form name is renamed to '"+formName+"'");
								if(!formNames.containsKey(tcID))
									formNames.put(tcID, actualFormName);
								else{
									formNames.put(tcID, formNames.get(tcID)+","+actualFormName);
								}
							}else{
								logger.info("Unable to download the form "+strReadString1+" whose recpeient name is "+strReadString+" so going to check again");
								int downloadForms = common.ElementSize(Common.o.getObject("DownloadLater"));
								if(downloadForms > 0)
								{
									//		                    			 downloadFile = Paths.get(HTML.properties.getProperty("FormsDownloadPath"));
									//		                    			 file = WaitForNewFile(downloadFile, ".pdf", 60, element);
									logger.info("Unable to download the form "+strReadString1+" whose recpeient name is "+strReadString+"");
								}
								//		                    		else{
								//		                    			 logger.info("Unable to download the form "+strReadString1+" whose recpeient name is "+strReadString+"");
								//		                    		}
							}
							Status = SCRCommon.JavaScript(js);
							break;
						}
					}
				}
			}
			if(ActionObject == true)
			{
				break;
			}
		}
		return Status;
	}

	public static Boolean createFormDirectory()
	{
		Date d = new Date();          
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy");
		String date = dateFormat.format(d);	
		String dirAddress = HTML.properties.getProperty("FormsDownloadPath")+"\\"+PCThreadCache.getInstance().getProperty

("testcasename")+"_"+ date+"_"+ d.getHours() +d.getMinutes()+d.getSeconds() + Thread.currentThread().getId();
		PCThreadCache.getInstance().setProperty(PCConstants.FormsFolderPath, dirAddress);
		File dir = new File(dirAddress);
		if(!dir.exists()) {
			dir.mkdir();
		}
		return true;
	}

	public static Boolean formsWait()
	{
		try {
			Thread.sleep(Integer.parseInt(HTML.properties.getProperty("FORMSWAIT")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public Boolean updateFormsinE2E()
	{
		return SCRCommon.updateE2ETrans("FormNames", formNames.get(PCThreadCache.getInstance().getProperty("TCID")));
	}
	public Boolean updateFormsPathinE2E()
	{
		return SCRCommon.updateE2ETrans("FormPath",  PCThreadCache.getInstance().getProperty(PCConstants.FormsFolderPath));
	}

	public static void setBrowserPathDownload() throws InterruptedException{	

		//		 	System.out.println("Launching chrome browser");
		//			System.setProperty("webdriver.chrome.driver", driverPath);	
		ChromeOptions options = new ChromeOptions();
		String downloadFilepath =  PCThreadCache.getInstance().getProperty(PCConstants.FormsFolderPath);;
		// String FolderPath1 = "C:\\Documents\\DownloadPDF\\20180305_114633\\";
		// CreateFileRenameExisting( FolderPath1);

		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", downloadFilepath);
		options.setExperimentalOption("prefs", chromePrefs);
		// options.setExperimentalOption("useAutomationExtension", false);

		DesiredCapabilities cap = DesiredCapabilities.chrome();
		cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		cap.setCapability(ChromeOptions.CAPABILITY, options);
		// WebDriver driver = new ChromeDriver(cap);
	}


	/*public static void Download() throws InterruptedException{	
			System.out.println("Download Started");
			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
			.format(Calendar.getInstance().getTime());
			OutputFile = new File("C:/Users/CO25077/Documents/DownloadPDF/" + timeStamp);

			OutputFile.mkdir();
			System.out.println(OutputFile);
			// Launch PC
			System.out.println("Launching chrome browser");
			System.setProperty("webdriver.chrome.driver", driverPath);

			ChromeOptions options = new ChromeOptions();
			FolderPath = "C:\\Users\\CO25077\\Documents\\DownloadPDF\\" + timeStamp + "\\";
			String downloadFilepath = FolderPath;
			// String FolderPath1 = "C:\\Documents\\DownloadPDF\\20180305_114633\\";
			// CreateFileRenameExisting( FolderPath1);

			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("profile.default_content_settings.popups", 0);
			chromePrefs.put("download.default_directory", downloadFilepath);
			options.setExperimentalOption("prefs", chromePrefs);
			// options.setExperimentalOption("useAutomationExtension", false);

			DesiredCapabilities cap = DesiredCapabilities.chrome();
			cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			cap.setCapability(ChromeOptions.CAPABILITY, options);
			// WebDriver driver = new ChromeDriver(cap);

			TryDownload(driver, FolderPath);

		}*/
}