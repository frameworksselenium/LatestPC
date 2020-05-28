
package com.pc.screen;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.PCThreadCache;

public class Modifiers {

	public static String sheetname = "Modifiers";
	Common common = CommonManager.getInstance().getCommon();
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));

	public Boolean SCRModifiers() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

	/**
	 * @function This function is to fill the NGS Coverage from the coverage sheet
	 * @return
	 */
	public Boolean fillRateModifier(String sheetName) {
		Boolean status = false;
		try {
			status = SCRCommon.Modifiers(sheetName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}

	public boolean ClickIfexists(String ele) {
		By element = Common.o.getObject(ele);
		// WebElement elemnt =
		// ManagerDriver.getInstance().getWebDriver().findElement(element);

		try {
			WebDriverWait wait = new WebDriverWait(ManagerDriver.getInstance().getWebDriver(), Integer.valueOf

(HTML.properties.getProperty("SHORTWAIT")));
			wait.until(ExpectedConditions.visibilityOf(ManagerDriver.getInstance().getWebDriver().findElement(element)));
			if (ManagerDriver.getInstance().getWebDriver().findElement(element).isDisplayed()) {
				ManagerDriver.getInstance().getWebDriver().findElement(element).click();
				logger.info("Clicked on element: " + ele);

			} else
				logger.info("Element: " + ele + "not available");
		}
		//
		catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}

		return true;
	}

}
