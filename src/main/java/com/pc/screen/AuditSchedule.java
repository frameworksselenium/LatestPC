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

public class AuditSchedule {
	public static String sheetname = "AuditSchedule";
	//static Logger logger = Logger.getLogger(sheetname);
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();

	public Boolean SCRAuditSchedule() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

	public boolean VerifyResults(String funValue) throws Exception {
		boolean status = false;
		logger.info("Verify the Results");
		String[] sValue = funValue.split(":::");
		String Value = null;
		switch (sValue[0].toUpperCase()) {
		case "VERIFY_AUDITSTATUS":
			logger.info(sValue[0]);
			status = common.ActionOnTable(common.o.getObject("eleAuditSchedule_Tbl"), 4, 8, "In Progress", "div");
			// status =
			// common.ActionOnTable(common.o.getObject("eleAccountSummary_PolicyTermsTbl"),
			// 8, 0, "In Force","img");
			// Value=common.GetTextFromTable(Common.o.getObject("eleAccountSummary_PolicyTermsTbl"),
			// 8, 1, "In Force");
			// status =
			// common.ActionOnTable(Common.o.getObject("eleAccountSummary_PolicyTermsTbl"),
			// 1, 0, Value,"img");
			break;
		}
		return status;
	}

	public boolean CoveredEmployees(String sFunctionValue) throws Throwable {
		boolean status = false;
		String[] sValue = sFunctionValue.split(":::"); // Audited Actual Basis:::10000
		int sRowCount = SCRCommon.TableRowCount(Common.o.getObject("eleAuditDetailstableRow"));
		List<WebElement> abcd = ManagerDriver.getInstance().getWebDriver().findElements(By.xpath("//div[contains(@id,'wcCovEmpLV-body')]"));
		for (WebElement sEle : abcd) {
			String sId = sEle.getAttribute("id");
			status = SCRCommon.DataWebTable(By.id(sId), sRowCount, 6, "", "single", "div");
			status = common.SafeAction(By.tagName("auditedActualBasis_Ext"), sValue[1], "edt" + "AuditedActualBasis");

		}
		return status;

	}

	public boolean AddEffectiveDate(String strValue) throws Exception {
		boolean status = false;
		switch (strValue) {
		case "Date Received": {
			status = common.SafeAction(Common.o.getObject("edt_DateReceived"), SCRCommon.ReturnCurrentDate(), "lst");
		}
		}
		return status;
	}

}