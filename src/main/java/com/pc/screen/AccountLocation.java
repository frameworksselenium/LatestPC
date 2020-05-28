package com.pc.screen;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.PCThreadCache;

public class AccountLocation {

	public static String sheetname = "AccountLocation";
	//static Logger logger = Logger.getLogger(sheetname);
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();

	public Boolean SCRAccountLocation() throws Exception {

		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}

		return status;
	}

	public boolean actionOnLocationTable(String strValue) throws Exception {
		boolean status = false;

		status = common.ActionOnTable(Common.o.getObject("eleAccLocationTable"), 6, 0, strValue, "", "img");

		return status;
	}

	public boolean clickOnTableValue() throws Exception {
		boolean status = false;
		String ele = "eleAccLocationTable";

		By obj = Common.o.getObject(ele);
		List<WebElement> Cells = null;
		WebElement mytable = ManagerDriver.getInstance().getWebDriver().findElement(obj);
		List<WebElement> allrows = mytable.findElements(By.tagName("tr"));
		String id = mytable.getAttribute("id");
		for (int i = 0; i <= allrows.size() - 1; i++) {
			String one = ":" + i + ":Loc";
			String two = ":" + i + ":LocationCode";
			String three = ":" + i + ":LocationName";
			String Location_Num = id.replace("-body", one);
			String Location_Code = id.replace("-body", two);
			String Location_Name = id.replace("-body", three);
			status = CommonManager.getInstance().getCommon().SafeAction(By.id(Location_Num), "YES", "BtnLocation");
			CommonManager.getInstance().getCommon().SafeAction(Common.o.getObject("eleLocationCancelBtn"), "YES", 

"eleLocationCancelBtn");
			// System.out.println(ManagerDriver.getInstance().getWebDriver().getPageSource());
			String p1 = ManagerDriver.getInstance().getWebDriver().getTitle();
			if (i != 0) {
				String expect = CommonManager.getInstance().getCommon().returnObject(By.id(Location_Code)).getText();
				status = CommonManager.getInstance().getCommon().SafeAction(By.id(Location_Code), "YES", "BtnLocation");
				String p2 = ManagerDriver.getInstance().getWebDriver().getTitle();
				By obj1 = common.o.getObject("edtLocationCode");
				WebElement ele1 = CommonManager.getInstance().getCommon().returnObject(obj1);
				String actual = ele1.getAttribute("value");
				System.out.println("1: " + actual + " " + expect);
				if (actual.equalsIgnoreCase(expect))
					status = true;
				else
					return false;
				CommonManager.getInstance().getCommon().SafeAction(Common.o.getObject("eleLocationCancelBtn"), "YES", 

"eleLocationCancelBtn");

				expect = CommonManager.getInstance().getCommon().returnObject(By.id(Location_Name)).getText();
				status = CommonManager.getInstance().getCommon().SafeAction(By.id(Location_Name), "YES", "BtnLocation");
				obj1 = common.o.getObject("edtLocationName");
				ele1 = CommonManager.getInstance().getCommon().returnObject(obj1);
				actual = ele1.getAttribute("value");
				System.out.println("2: " + actual + " " + expect);
				if (actual.equalsIgnoreCase(expect))
					status = true;
				else
					return false;
				String p3 = ManagerDriver.getInstance().getWebDriver().getTitle();
				CommonManager.getInstance().getCommon().SafeAction(Common.o.getObject("eleLocationCancelBtn"), "YES", 

"eleLocationCancelBtn");
				if (p1.equals(p2) && p2.equals(p3))
					status = true;
				else
					return false;
			}

		}

		return status;
	}

}
