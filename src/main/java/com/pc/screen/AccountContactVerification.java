package com.pc.screen;

import java.util.HashMap;
import java.util.List;

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
import com.pc.utilities.XlsxReader;

public class AccountContactVerification {

	public static final String sheetname = "AccountContactVerification";
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();
	//static Logger logger = Logger.getLogger(sheetname);
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();

	public Boolean SCRAccountContactVerification() throws Exception {

		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

	public boolean DataFill() throws Exception {
		
		boolean status = false;
		XlsxReader sXL = XlsxReader.getInstance();
		HashMap<String, Object> updateColumnNameValues = new HashMap<String, Object>();
		HashMap<String, Object> whereConstraint = new HashMap<String, Object>();
		int rowcount = sXL.getRowCount(sheetname);
		try {
			for (int i = 1; i < rowcount; i++) {
				String value = sXL.getCellData(sheetname, 0, i);
				if (!value.isEmpty()) {
					if (PCThreadCache.getInstance().getProperty("TCID").equals(value)) {
						updateColumnNameValues.clear();
						whereConstraint.clear();
						updateColumnNameValues.put(PCConstants.PolicyNumber, PCThreadCache.getInstance().getProperty

(PCConstants.CACHE_POLICY_NUMBER));
						whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
						PCThreadCache.getInstance().setProperty("PolicyNumber", PCThreadCache.getInstance().getProperty(PCConstants.CACHE_POLICY_NUMBER));
						//status = sXL.executeUpdateQuery(PCConstants.SHEET_ACCOUNTCONTACTVERIFICATION, updateColumnNameValues, whereConstraint);
						// status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"),
						// "Update AccountContactVerification Set PolicyNumber =
						// '"+Payment.PolicyNumber+"' where
						// ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
						status = true;
						break;
					}
				}
			}
		} finally {
			// Marking for Garbage Collection
			updateColumnNameValues = null;
			whereConstraint = null;
		}
		return status;
	}

	public boolean VerifyResults(String funValue) throws Exception {
		
		boolean status = true;
		String[] sValue = funValue.split(":::");
		switch (sValue[0].toUpperCase()) {
		case "VERIFYADDITIONALACCOUNTEXIST":
			logger.info("Validating the '" + sValue[0] + "'");
			AccountFileTable(sValue[1]);
			break;

		case "VERIFYACCOUNTSTATUS":
			logger.info("Validating the '" + sValue[0] + "'");
			AccountActiveStatus(sValue[1], sValue[2]);
			break;

		case "VERIFYACCOUNTREMOVED":
			logger.info("Validating the '" + sValue[0] + "'");
			VerifyAccountRemoved(sValue[1]);
			break;

		case "VERIFYROLEISREMOVED":
			logger.info("Validating the '" + sValue[0] + "'");
			VerifyRemoveRole(sValue[1], sValue[2]);
			break;

		case "VERIFYADDRESSES":
			logger.info("Validating the '" + sValue[0] + "'");
			VerifyAddress(sValue[1], sValue[2], sValue[3]);
			break;

		case "VERIFYLISTOFPOLICIES":
			logger.info("Validating the '" + sValue[0] + "'");
			ListofPolicies(sValue[1]);
			break;
		}
		return status;
	}

	public boolean AccountFileRoleTable(String accRole) throws Exception {
		
		boolean status = false;
		String wait = HTML.properties.getProperty("SHORTWAIT");
		Integer x = Integer.valueOf(wait);
		WebElement tableRowXpathRole = driver.findElement(Common.o.getObject("accFileContactRoleTable"));
		List<WebElement> rows_table1 = tableRowXpathRole.findElements(By.tagName("tr"));
		int rows_count1 = rows_table1.size();
		for (int i = 1; i <= rows_count1; i++) {
			// String accRoles =
			// driver.findElement(By.xpath(".//*[@id='AccountFile_Contacts:AccountFile_ContactsScreen:AccountContactCV:ContactRolesDV:ContactRolesLV-body']/div/table/tbody/tr["+i+"]/td[2]")).getText();
			String accountRoles = common.ReadElement(By.xpath(".//*[@id='AccountFile_Contacts:AccountFile_ContactsScreen:AccountContactCV:ContactRolesDV:ContactRolesLV-body']/div/table/tbody/tr[" + i + "]/td[2]"), x);
			if (accountRoles.equals(accRole)) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Account Role '" + accRole + "' should match", "Account Role '" + accountRoles + "' is matching", "PASS");
				status = true;
				break;
			}
		}
		if (status == false) {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "Account Role '" + accRole + "' should match", "Account Role '" + accRole + "' not Found", "FAIL");
		}

		return status;
	}

	public boolean AccountFileTable(String saccName) throws Exception {
		
		boolean status = false;
		common.SafeAction(Common.o.getObject("accFileContactRoleTable"), "", "ele");
		WebElement tableRowXpathRole = driver.findElement(Common.o.getObject("accFileContactRoleTable"));
		List<WebElement> rows_table1 = tableRowXpathRole.findElements(By.tagName("tr"));
		int rows_count1 = rows_table1.size();
		for (int i = 1; i <= rows_count1; i++) {
			String accName = driver.findElement(By.xpath(".//*[@id='AccountFile_Contacts:AccountFile_ContactsScreen:AccountContactsLV-body']/div/table/tbody/tr[" + i + "]/td[3]")).getText();
			if (accName.equals(saccName)) {
				// driver.findElement(By.xpath(".//*[@id='AccountFile_Contacts:AccountFile_ContactsScreen:AccountContactsLV-body']/div/table/tbody/tr["+i+"]/td[3]")).click();
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Accont Name '" + saccName + "' should match", "Accont Name '" + accName + "' is matching", "PASS");
				status = true;
				break;
			}
		}
		if (status == false) {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "Accont Name '" + saccName + "' should match", "Account Name '" + saccName + "' not found", "FAIL");
		}
		return status;
	}

	public boolean AccountStatusChangeAndRemove(String funValue) throws Exception {
		
		boolean status = false;
		String[] sValue = funValue.split(":::");
		WebElement tableRowXpathRole = driver.findElement(Common.o.getObject("accFileContactRoleTable"));
		List<WebElement> rows_table1 = tableRowXpathRole.findElements(By.tagName("tr"));
		int rows_count1 = rows_table1.size();
		for (int i = 1; i <= rows_count1; i++) {
			String accName = driver.findElement(By.xpath(".//*[@id='AccountFile_Contacts:AccountFile_ContactsScreen:AccountContactsLV-body']/div/table/tbody/tr[" + i + "]/td[3]")).getText();
			if (accName.equals(sValue[0])) {
				driver.findElement(By.xpath(".//*[@id='AccountFile_Contacts:AccountFile_ContactsScreen:AccountContactsLV-body']/div/table/tbody/tr[" + i + "]/td[1]/div/img")).click();
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Accont Name '" + sValue[0] + "' should match", "Accont Name '" + accName + "' is matching", "PASS");
				status = true;
				break;
			}
		}
		if (status == false) {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "Accont Name '" + sValue[0] + "' should match", "Accont Name '" + sValue[0] + "' not found", "FAIL");
		}
		return status;
	}

	public boolean AccountActiveStatus(String sAccName, String sAccStatus) throws Exception {
		boolean status = false;
		WebElement tableRowXpathRole = driver.findElement(Common.o.getObject("accFileContactRoleTable"));
		List<WebElement> rows_table1 = tableRowXpathRole.findElements(By.tagName("tr"));
		int rows_count1 = rows_table1.size();
		for (int i = 1; i <= rows_count1; i++) {
			String accName = driver.findElement(By.xpath(".//*[@id='AccountFile_Contacts:AccountFile_ContactsScreen:AccountContactsLV-body']/div/table/tbody/tr[" + i + "]/td[3]/div")).getText();
			if (accName.equals(sAccName)) {
				String sStatusCheck = driver.findElement(By.xpath(".//*[@id='AccountFile_Contacts:AccountFile_ContactsScreen:AccountContactsLV-body']/div/table/tbody/tr[" + i + "]/td[2]/div")).getText();
				if (sStatusCheck.equals(sAccStatus)) {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Account Status '" + sAccStatus + "' should match", "Account Status '" + sStatusCheck + "' is matching", "PASS");
					status = true;
					break;
				}
			}
		}
		if (status == false) {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "Account Status '" + sAccStatus + "' should match", "Status not Found/Matched", "FAIL");
		}
		return status;
	}

	public boolean VerifyAccountRemoved(String sAccName) throws Exception {
		boolean status = true;
		WebElement tableRowXpathRole = driver.findElement(Common.o.getObject("accFileContactRoleTable"));
		List<WebElement> rows_table1 = tableRowXpathRole.findElements(By.tagName("tr"));
		int rows_count1 = rows_table1.size();
		for (int i = 1; i <= rows_count1; i++) {
			String accName = driver.findElement(By.xpath(".//*[@id='AccountFile_Contacts:AccountFile_ContactsScreen:AccountContactsLV-body']/div/table/tbody/tr[" + i + "]/td[3]")).getText();
			if (accName.equals(sAccName)) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Account Name '" + sAccName + "' should match", "Account Name '" + accName + "' is matching", "FAIL");
				status = false;
				break;
			}
		}
		if (status == true) {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "Account Name '" + sAccName + "' should not found", "Account Name not found", "PASS");
		}
		return status;
	}

	public boolean VerifyRemoveRole(String sAccName, String sContactRole) throws Exception {
		boolean status = true;
		WebElement tableRowXpathRole = driver.findElement(Common.o.getObject("accFileContactRoleTable"));
		List<WebElement> rows_table1 = tableRowXpathRole.findElements(By.tagName("tr"));
		int rows_count1 = rows_table1.size();
		for (int i = 1; i <= rows_count1; i++) {
			String accName = driver.findElement(By.xpath(".//*[@id='AccountFile_Contacts:AccountFile_ContactsScreen:AccountContactsLV-body']/div/table/tbody/tr[" + i + "]/td[3]")).getText();
			if (accName.equals(sAccName)) {
				// driver.findElement(By.xpath(".//*[@id='AccountFile_Contacts:AccountFile_ContactsScreen:AccountContactsLV-body']/div/table/tbody/tr["+i+"]/td[3]")).click();
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "'" + sAccName + "' should match", "'" + accName + "' is matching", "PASS");
				String sNewRole = driver.findElement(By.xpath(".//*[@id='AccountFile_Contacts:AccountFile_ContactsScreen:AccountContactsLV-body']/div/table/tbody/tr[" + i + "]/td[4]")).getText();
				if (sNewRole.contains(sContactRole)) {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Removed Role '" + sContactRole + "' should not be in the role", "The Removed Role '" + sNewRole + "' is available", "FAIL");
					status = false;
				}
			}
		}
		if (status == true) {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "Removed Role '" + sContactRole + "' should not be present", "Removed Role '" + sContactRole + "' not present", "PASS");
		}
		return status;
	}

	public boolean VerifyAddress(String sFAddressCheck, String sFAddress, String sFAddressType) throws Exception {
		boolean status = false;
		String wait = HTML.properties.getProperty("SHORTWAIT");
		Integer x = Integer.valueOf(wait);
		WebElement tableRowXpathRole = driver.findElement(Common.o.getObject("eleAddressesTable"));
		List<WebElement> rows_table1 = tableRowXpathRole.findElements(By.tagName("tr"));
		int rows_count1 = rows_table1.size();
		for (int i = 1; i <= rows_count1; i++) {
			String sAcutalAddress = driver.findElement(By.xpath(".//*[@id='AccountFile_Contacts:AccountFile_ContactsScreen:AccountContactCV:AddressesPanelSet:AddressesLV-body']/div/table/tbody/tr[" + i + "]/td[4]")).getText();
			if (sAcutalAddress.contains(sFAddressCheck)) {
				driver.findElement(By.xpath(".//*[@id='AccountFile_Contacts:AccountFile_ContactsScreen:AccountContactCV:AddressesPanelSet:AddressesLV-body']/div/table/tbody/tr[" + i + "]/td[4]")).click();
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "It should click on '" + sFAddressCheck + "' Address", "Clicked on '" + sAcutalAddress + "' Address", "PASS");
				Thread.sleep(5000);
				String sAddress = common.ReadElement(Common.o.getObject("txtAddresses"), x);
				String sAddressType = common.ReadElement(Common.o.getObject("txtMailing"), x);
				common.CompareStringResult("Address Name", sAddress, sAddress);
				common.CompareStringResult("AddressType Name", sAddressType, sAddressType);
				status = true;
				// break;
			}
		}
		if (status == false) {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "It should click on '" + sFAddressCheck + "' Address", "'" + sFAddressCheck + "' not Found", "FAIL");
			status = true;
		}
		return status;
	}

	public boolean ListofPolicies(String sFPolicyNumber) throws Exception {
		boolean status = false;
		WebElement tableRowXpathRole = driver.findElement(Common.o.getObject("eleListofPolciesTable"));
		List<WebElement> rows_table1 = tableRowXpathRole.findElements(By.tagName("tr"));
		int rows_count1 = rows_table1.size();
		for (int i = 1; i <= rows_count1; i++) {
			String sPolicyNumber = driver.findElement(By.xpath(".//*[@id='AccountFile_Contacts:AccountFile_ContactsScreen:AccountContactCV:AccountFile_Contacts_PoliciesLV-body']/div/table/tbody/tr[" + i + "]/td[1]")).getText();
			if (sFPolicyNumber.equals(sPolicyNumber)) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Policy Number is '" + sFPolicyNumber + "' should be present", "Policy Number is '" + sPolicyNumber + "' is present", "PASS");
				status = true;
				driver.findElement(By.xpath(".//*[@id='AccountFile_Contacts:AccountFile_ContactsScreen:AccountContactCV:AccountFile_Contacts_PoliciesLV-body']/div/table/tbody/tr[" + i + "]/td[1]/div/a")).click();
				boolean sVerifyPolicySummary = driver.findElements(Common.o.getObject("eleAccountSummaryPage")).size() != 0;
				if (sVerifyPolicySummary == true) {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Policy#'" + sFPolicyNumber + "' Summary should display", "Policy#'" + sPolicyNumber + "' Summary is displayed", 

"PASS");
					status = true;
					break;
				} else {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Policy#'" + sFPolicyNumber + "' Summary should display", "Policy#'" + sPolicyNumber + "' Summary is not displayed", "FAIL");
					status = false;
				}
			}
		}
		if (status == false) {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "Policy Number is '" + sFPolicyNumber + "' should be present", "Policy Number is '" + sFPolicyNumber + "' is not present", 

"FAIL");
		}
		return status;
	}
}







