package com.pc.screen;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.PCThreadCache;

public class AccountPolicyTransactions {

	public static String sheetname = "AccountPolicyTransactions";
	//static Logger logger = Logger.getLogger(sheetname);
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));
	public static String AccountNumber;
	public static String AccountName;
	Common common = CommonManager.getInstance().getCommon();

	public Boolean SCRAccountPolicyTransactions() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}

		return status;
	}

	/**
	 * @function This function use to select the specified transaction
	 * @param value(submission
	 *            number)
	 * @return status
	 * @throws Exception
	 */
	public boolean SelectTransaction(String value) throws Exception {
		boolean status = false;
		String sValue = value;
		try {
			status = common.ActionOnTable(Common.o.getObject("eleAPTSearchResultTable"), 3, 3, sValue, sValue, "a");

			if (!status) {
				status = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * @function This function verify policy transaction
	 * @param value
	 *            (account name)
	 * @return status
	 * @throws Exception
	 */
	public boolean VerifyPolicyTransactions(String sFuncValue) throws Exception {
		boolean status = false;
		String sApplnValue = "";
		// String sValue = CreateAccount.AccountName;
		try {
			sApplnValue = common.ReadElement(Common.o.getObject("eleAPTPolicyTransactionAccountName"), Integer.valueOf

(HTML.properties.getProperty("LONGWAIT")));

			if (sApplnValue.equals(sFuncValue)) {
				status = true;
			} else {
				status = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	public boolean SelectProduct(String value) {
		By ele = Common.o.getObject("edtProductList");
		Boolean status = false;
		try {
			status = common.SafeAction(ele, value, "edtProductList");
			ManagerDriver.getInstance().getWebDriver().findElement(ele).sendKeys(Keys.ARROW_DOWN);
			// Thread.sleep(1000);
			By li = By.xpath("//li[text()='" + value + "']");
			status = common.SafeAction(li, "YES", "eleProductListItem");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}

	public boolean VerifyProductList(String value) throws IOException {
		By ele = Common.o.getObject("eleProductTable");
		boolean SearchString = false;
		String readText = null;
		WebElement mytable = ManagerDriver.getInstance().getWebDriver().findElement(ele);
		List<WebElement> allrows = mytable.findElements(By.tagName("tr"));
		for (int i = 0; i < allrows.size(); i++) {
			System.out.println(i);
			SearchString = false;
			List<WebElement> Cells = allrows.get(i).findElements(By.tagName("td"));
			System.out.println(Cells.size());
			readText = Cells.get(2).getText();
			System.out.println(readText);

			if (readText.equalsIgnoreCase(value)) {
				SearchString = true;
				logger.info("Search String available in the table. '" + value + "'");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should search string in table and Search string is '" + value + "'", "System searched string in table and search string is  '" + value + "'", "PASS");

			} else {
				logger.info("Search String not available in the table. '" + value + "'");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should search string in table and Search string is '" + value + "'", "System searched string in table and search string is  '" + value + "'", "FAIL");
				return SearchString;
			}
		}
		return SearchString;
		// findElements(By.xpath("//table[@id='DataTable']/tbody/tr")).size();
	}
}