
package com.pc.screen;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.PCThreadCache;

public class AuditDetails {
	public static String sheetname = "AuditDetails";
	//static Logger logger = Logger.getLogger(sheetname);
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();

	public Boolean SCRAuditDetails() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;

	}

	public Boolean coveredEmployees(String strFunValue) {
		Boolean blnStatus = true;
		Boolean blnCheckStateExist = false;
		JavascriptExecutor js = (JavascriptExecutor) ManagerDriver.getInstance().getWebDriver();
		String[] splitValues = strFunValue.split(":::");
		String strGetStateID = ".//span[contains(text(),'" + splitValues[1] + "')]";
		By strGetStateExistID = By.xpath(strGetStateID);
		try {
			blnCheckStateExist = common.ElementExist(strGetStateExistID);
			if (blnCheckStateExist) {
				String getStateID = common.ReadElementGetAttribute(strGetStateExistID, "id", Integer.valueOf

(HTML.properties.getProperty("SHORTWAIT")));
				String getStateTableID[] = getStateID.split("StateTitle");
				String getConcatToGetStateID = getStateTableID[0].concat("0:wcCovEmpLV-body");
				By strTableID = By.id(getConcatToGetStateID);
				blnStatus = common.ActionOnTable(strTableID, 1, 6, splitValues[0], "", "div");
				if (blnStatus) {
					ManagerDriver.getInstance().getWebDriver().switchTo().activeElement().sendKeys(splitValues[2]);
					ManagerDriver.getInstance().getWebDriver().switchTo().activeElement().sendKeys(Keys.TAB);
					blnStatus = SCRCommon.JavaScript(js);
					// blnStatus =
					// common.SafeAction(Common.o.getObject("edtAuditAuditedActualBasis"),
					// splitValues[2], "edtAuditAuditedActualBasis");
					blnStatus = common.SafeAction(Common.o.getObject("eleDetailsScreen"), "", "eleDetailsScreen");
				} else {
					logger.info("Problem in getting the state table ID");
				}
			} else {
				logger.info("State is not available");
				blnStatus = false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return blnStatus;
	}
}