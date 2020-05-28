/**
 * @ClassPurpose Access all the data's from RiskAnalysis sheet
 * @Scriptor Krishna
 * @ReviewedBy
 * @ModifiedBy Rajeshwari & Siva
 * @LastDateModified 4/3/2017
 */
package com.pc.screen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.PCThreadCache;

public class ClassGuide {

	public static String sheetname = "ClassGuide";
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();
	public Boolean SCRClassGuide() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

	public boolean SwitchtoChildWindow(String windowname) throws Exception {
		boolean status=false;
		String parentwindow=driver.getWindowHandle();
		PCThreadCache.getInstance().setProperty("ParentWindow", parentwindow);
		
		ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
		
		for(int i=0;i<newTab.size();i++)
		{
			String Childwindowname;
		    Childwindowname=driver.switchTo().window(newTab.get(i)).getTitle();
		    if(Childwindowname.toLowerCase().contains(windowname.toLowerCase()))
		    {
		    	PCThreadCache.getInstance().setProperty("ChildWindow", newTab.get(i));
		    	driver.switchTo().window(newTab.get(i));
		    	driver.switchTo().frame("classDataSearchHeaderFrame");
		    	//status = common.SafeAction(Common.o.getObject("eleClassGuideSelectCustomerRadioBtn"), "","eleClassGuideSelectCustomerRadioBtn");
		    	status= true;
		    }
		}
		if (status) {
			
		}
		
		/*Set<String>s1=driver.getWindowHandles();
		Iterator<String> I1= s1.iterator();

		while(I1.hasNext())
		{

		   String child_window=I1.next();
		   String Childwindowname;
		   Childwindowname=driver.switchTo().window(child_window).getTitle();
		   // Here we will compare if parent window is not equal to child window then we            will close

		//if(!parentwindow.equals(child_window))
		   if(Childwindowname.toLowerCase().contains(windowname.toLowerCase()))
		{	
			   PCThreadCache.getInstance().setProperty("ChildWindow", child_window);
			   //ManagerDriver.getInstance().setWebDriver(child_window);
			   driver.switchTo().window(child_window);
			   System.out.println(driver.switchTo().window(child_window).getTitle());
			   status= true;
			   //setWebDriver
			   //ManagerDriver.getInstance().getWebDriver().close();
		}

		}*/
		
	return status;
	}
	
	public Boolean AddClassificationClasses(String funValue) throws Exception {
		boolean status = false;
		// String[] sPair = funValue.split(":::");
		String[] sValue = funValue.split(":::");
		String sPrimaryWindow = driver.getWindowHandle();
		PCThreadCache.getInstance().setProperty("ParentWindow", sPrimaryWindow);
		java.util.Set<String> allWindowHandles = driver.getWindowHandles();
		for (String handle : allWindowHandles) {
			if (driver.switchTo().window(handle).getTitle().contains("Class Guide")) {
				PCThreadCache.getInstance().setProperty("ChildWindow", handle);
				driver.switchTo().window(handle);
				driver.switchTo().frame("classDataSearchFrame");
				//status = common.SafeAction(Common.o.getObject("elewhatsnew"), "", "elewhatsnew");
				//status = common.SafeAction(Common.o.getObject("btnClassGuideSelectCustomerRadioBtn"), "", "btnClassGuideSelectCustomerRadioBtn");
				logger.info("Classification window is displayed and swithced to that window");
				status = true;
				break;
			}
		}
		if (status) {
			status = common.SafeAction(Common.o.getObject("btnClassGuideSelectCustomerRadioBtn"), "", 

"btnClassGuideSelectCustomerRadioBtn");
			common.WaitForPageToBeReady();
			status = common.SafeAction(Common.o.getObject("drpClassGuideRiskState"), sValue[0], "drpClassGuideRiskState");
			status = common.SafeAction(Common.o.getObject("btnCGSearchByClassDesc"), "", "btnCGSearchByClassDesc");
			status = common.SafeAction(Common.o.getObject("pwdCGClassDescText"), sValue[1], "pwdCGClassDescText");
			status = common.SafeAction(Common.o.getObject("btnCGSearchBtn1"), "", "btnCGSearchBtn1");
			
			//if(common.WaitForElementExist(Common.o.getObject("btnSelectClassCode1"), Integer.valueOf(HTML.properties.getProperty("LONGWAIT"))))
			//{
				common.WaitForPageToBeReady();
				Thread.sleep(4000);
				status = common.SafeAction(Common.o.getObject("btnSelectClassCode1"), "", "btnSelectClassCode1");
				common.WaitForPageToBeReady();
				Thread.sleep(4000);
			//}
			status = common.SafeAction(Common.o.getObject("btnsavetoPC"), "", "btnsavetoPC");
			//common.WaitForPageToBeReady();
			Thread.sleep(5000);
		} else {
			status = false;
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Switch to Classify window", " Could not swithc to classify window", "FAIL");
		}

		return status;
	}
	
	public boolean SwitchtoParentWindow() throws InterruptedException {
		//PCThreadCache.getInstance().getProperty("ChildWindow");
		//driver.switchTo().window(PCThreadCache.getInstance().getProperty("ChildWindow"));
		//driver.close();
		driver.switchTo().window(PCThreadCache.getInstance().getProperty("ParentWindow"));
		return true;
	}
	public boolean VerifyWindowName(String windowname) throws InterruptedException, Exception {
		boolean status = false;
		
		String sPrimaryWindow = driver.getWindowHandle();
		PCThreadCache.getInstance().setProperty("ParentWindow", sPrimaryWindow);
		
		Set<String>s1=driver.getWindowHandles();
		Iterator<String> I1= s1.iterator();

		while(I1.hasNext())
		{

		   String child_window=I1.next();
		   String Childwindowname;
		   Childwindowname=driver.switchTo().window(child_window).getTitle();

		   if(Childwindowname.toLowerCase().contains(windowname.toLowerCase()))
		   {	
			   HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "New window with title Class Guide should be opened", "The child window name is "+Childwindowname, "PASS");
			   System.out.println("Expected window by name " + driver.switchTo().window(child_window).getTitle()+" opened successfully");
			   status= true;
		   }

		}
		
		return status;
	}
	
	
}