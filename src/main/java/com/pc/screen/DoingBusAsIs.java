package com.pc.screen;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.PCThreadCache;

public class DoingBusAsIs {

	public static String sheetname = "DoingBusAsIs";
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();

	public Boolean SCRDoingBusAsIs() throws Exception {

		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

	public boolean SelectDBA(String sFunctionValue) throws Throwable {
		boolean status = false;
		String[] sValue = sFunctionValue.split(":::");
		try {
			int TableCol = 0;
			int SearchTableCol = 0;
			String strclass = null;
			switch (sValue[0]) {
			case "elePriNamedInsDBAAddedTable":
				TableCol = 1;
				strclass = "img";
				SearchTableCol = 2;
				break;
			case "eleAccAddDBATable":
				TableCol = 1;
				strclass = "img";
				SearchTableCol = 2;
				break;
			case "elePolInfoDBAAddedTable":
				TableCol = 0;
				strclass = "img";
				SearchTableCol = 2;
				break;
			case "eleAddInsDBAAddedTable":
				TableCol = 0;
				strclass = "img";
				SearchTableCol = 2;
				break;
			case "eleAccNamedInsTable":
				TableCol = 2;
				strclass = "a";
				SearchTableCol = 2;
				break;
			case "eleAccNamedInsDBATable":
				TableCol = 1;
				strclass = "img";
				SearchTableCol = 2;
				break;
			case "eleAccNewAddDBARoleTable":
				TableCol = 0;
				strclass = "img";
				SearchTableCol = 1;
				break;
			}
			status = true;
			status = common.ActionOnTable(Common.o.getObject(sValue[0]), SearchTableCol, TableCol, sValue[1], "", strclass);
		} catch (Throwable e) {
			e.printStackTrace();
			if (status == false) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should complete performing the add class", "System not completed performing the add class", "FAIL");
			}
		}
		return status;
	}

	public boolean PolPrimInsAddressesValidation(String sFunctionValue) throws Throwable {
		boolean status = true;
		Integer x = Integer.valueOf(HTML.properties.getProperty("NORMALWAIT"));
		/*
		 * String PolDBARoleAddrAdd1 =
		 * common.ReadElementGetAttribute(Common.o.getObject("elePolDBARoleAddrAdd1"),
		 * x); String PolDBARoleAddrCity =
		 * common.ReadElementFromListEditBox(Common.o.getObject("elePolDBARoleAddrCity")
		 * , x); String PolDBARoleAddrCounty =
		 * common.ReadElementFromListEditBox(Common.o.getObject(
		 * "elePolDBARoleAddrCounty"), x); String PolDBARoleAddrState =
		 * common.ReadElementFromListEditBox(Common.o.getObject("elePolDBARoleAddrState"
		 * ), x); String PolDBARoleAddrZip =
		 * common.ReadElementFromListEditBox(Common.o.getObject("elePolDBARoleAddrZip"),
		 * x); String PolDBARoleAddrCountey =
		 * common.ReadElementFromListEditBox(Common.o.getObject(
		 * "elePolDBARoleAddrCountey"), x);
		 */

		String PolDBARoleAddrAdd1 = common.ReadElement(Common.o.getObject("elePolDBARoleAddrAdd1"), x);
		String PolDBARoleAddrCity = common.ReadElement(Common.o.getObject("elePolDBARoleAddrCity"), x);
		String PolDBARoleAddrCounty = common.ReadElement(Common.o.getObject("elePolDBARoleAddrCounty"), x);
		String PolDBARoleAddrState = common.ReadElement(Common.o.getObject("elePolDBARoleAddrState"), x);
		String PolDBARoleAddrZip = common.ReadElement(Common.o.getObject("elePolDBARoleAddrZip"), x);
		String PolDBARoleAddrCountey = common.ReadElement(Common.o.getObject("elePolDBARoleAddrCountey"), x);

		status = common.SafeAction(Common.o.getObject("edtPolDBARoleContDetTab"), "edtPolDBARoleContDetTab", "ele");

		String PolDBARoleContDetAdd1 = common.ReadElement(Common.o.getObject("elePolDBARoleContDetAdd1"), x);
		String PolDBARoleContDetCity = common.ReadElement(Common.o.getObject("elePolDBARoleContDetCity"), x);
		String PolDBARoleContDetCounty = common.ReadElement(Common.o.getObject("elePolDBARoleContDetCounty"), x);
		String PolDBARoleContDetState = common.ReadElement(Common.o.getObject("elePolDBARoleContDetState"), x);
		String PolDBARoleContDetZip = common.ReadElement(Common.o.getObject("elePolDBARoleContDetZip"), x);
		String PolDBARoleContDetCountey = common.ReadElement(Common.o.getObject("elePolDBARoleContDetCountey"), x);
		if (PolDBARoleAddrAdd1.contains(PolDBARoleContDetAdd1) && PolDBARoleAddrCity.contains(PolDBARoleContDetCity) && 

PolDBARoleAddrCounty.contains(PolDBARoleContDetCounty) && PolDBARoleAddrState.contains(PolDBARoleContDetState) && PolDBARoleAddrZip.contains

(PolDBARoleContDetZip) && PolDBARoleAddrCountey.contains(PolDBARoleContDetCountey)) {
			logger.info("System should display Policy DBA Role page Addresses Tab Addresses and ContactTab Adrresses as Same");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "System should display Policy DBA Role page Addresses Tab Addresses and ContactTab Adrresses as Same", "System displayed Policy DBA Role page Addresses Tab Addresses and ContactTab Adrresses as Same", "PASS");
		} else {
			logger.info("System should display Policy DBA Role page Addresses Tab Addresses and ContactTab Adrresses as Same");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should display Policy DBA Role page Addresses Tab Addresses and ContactTab Adrresses as Same", "System not displayed Policy DBA Role page Addresses Tab Addresses and ContactTab Adrresses as Same", "FAIL");
		}
		return status;
	}

	public Boolean CheckDBAAddRemove(String Values) throws Exception {

		Boolean Status = true;

		String[] sValue = Values.split(":::");
		// Integer x = Integer.valueOf(HTML.properties.getProperty("NORMALWAIT"));
		String strid = null;
		int readTextCol = 0;
		switch (sValue[1]) {
		case "elePriNamedInsDBAAddedTable":
			readTextCol = 2;
			strid = "EditPolicyContactRolePopup:ContactDetailScreen:PolicyContactRoleDetailsCV:ContactDBALV-body";
			break;
		case "elePolInfoDBAAddedTable":
			readTextCol = 2;
			strid = 

"SubmissionWizard:LOBWizardStepGroup:SubmissionWizard_PolicyInfoScreen:listOfDBAs:PolicyDBAsDV:NamedInsuredInputSet:DBAListLV-body";
			break;
		case "eleAddInsDBAAddedTable":
			readTextCol = 2;
			strid = 

"SubmissionWizard:LOBWizardStepGroup:SubmissionWizard_PolicyInfoScreen:AdditionalNamedInsuredsDV:NamedInsuredInputSet:NamedInsuredsLV-body";
			break;
		case "eleAccNamedInsDBATable":
			readTextCol = 2;
			strid = "EditAccountContactPopup:ContactDetailScreen:AccountContactCV:ContactDBALV-body";
			break;
		case "eleAccNewAddDBARoleTable":
			readTextCol = 1;
			strid = "NewDBAContactPopup:ContactDetailScreen:AccountDBAContactCV:ContactRolesDV:ContactRolesLV-body";
			break;

		}
		if (sValue[0].contains("AddDBA")) {
			Boolean dbaAddStatus = false;
			List<WebElement> allrows = driver.findElements(By.tagName("div"));
			for (WebElement ele : allrows) {
				String readText = ele.getAttribute("id");
				if (readText.contains(strid)) {
					String strDBANAme = common.GetTextFromTable(Common.o.getObject(sValue[1]), readTextCol, sValue[2]);
					if (sValue[2].contains(strDBANAme)) {
						dbaAddStatus = true;
					}
				}
			}
			if (dbaAddStatus) {
				logger.info("System should display Added DBA in table");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System displayed Added DBA in table", "System displayed Added DBA in table", "PASS");
			} else {
				logger.info("System should display Added DBA in table");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System not displayed Added DBA in table", "System displayed Added DBA in table", "FAIL");
			}
		}
		if (sValue[0].contains("DeleteDBA")) {
			Boolean dbaDeleteStatus = true;
			List<WebElement> allrows = driver.findElements(By.tagName("div"));
			for (WebElement ele : allrows) {
				String readText = ele.getAttribute("id");
				if (readText.contains(sValue[1])) {
					String strDBANAme = common.GetTextFromTable(Common.o.getObject(sValue[1]), 2, sValue[2]);
					if (sValue[2].contains(strDBANAme)) {
						dbaDeleteStatus = false;
					}
				}
			}
			if (dbaDeleteStatus) {
				logger.info("System should not display Deleted DBA in table");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should not display Deleted DBA in table", "System not displayed Deleted DBA in table", "PASS");
			} else {
				logger.info("System should not display Deleted DBA in table");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should not display Deleted DBA in table", "System displayed Deleted DBA in table", "FAIL");
			}
		}
		return Status;
	}

}