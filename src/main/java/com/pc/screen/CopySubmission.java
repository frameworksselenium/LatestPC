package com.pc.screen;

import java.io.IOException;
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

public class CopySubmission {

	public static String sheetname = "CopySubmission";
	Common common = CommonManager.getInstance().getCommon();
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName
(),PCThreadCache.getInstance().getProperty("TCID"));
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();

	public Boolean SCRCopySubmission() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);

		if (!status) {
			status = false;
		}
		return status;
	}
	
	public Boolean ValidatePolicyNumber() throws IOException
		{
		Boolean Flag = false;
		WebElement PolicyTable = driver.findElement(By.xpath("//div[contains(@id,'PSPolicyTermInfo')]/div/table/tbody"));
		List<WebElement> totalRows = PolicyTable.findElements(By.tagName("tr"));
		int RowCnt = totalRows.size();
		for(int i=1;i<RowCnt;i++)
		{
			String policystatusinRow1 = driver.findElement(By.xpath("//div[contains(@id,'PSPolicyTermInfo')]/div/table/tbody/tr["+i

+"]/td[10]")).getText();
			String policystatusinRow2 = driver.findElement(By.xpath("//div[contains(@id,'PSPolicyTermInfo')]/div/table/tbody/tr["+(i

+1)+"]/td[10]")).getText();
			System.out.println(policystatusinRow1);
			System.out.println(policystatusinRow2);
			if(!policystatusinRow1.equalsIgnoreCase(policystatusinRow2))
			{
				String policyNumberinRow1 = driver.findElement(By.xpath("//div[contains(@id,'PSPolicyTermInfo')]/div/table/tbody/tr["+i+"]/td[2]")).getText();
				String policyNumberinRow2 = driver.findElement(By.xpath("//div[contains(@id,'PSPolicyTermInfo')]/div/table/tbody/tr["+(i+1)+"]/td[2]")).getText();
				System.out.println(policyNumberinRow1);
				System.out.println(policyNumberinRow2);
				
				if(!policyNumberinRow1.equalsIgnoreCase(policyNumberinRow2))
				{
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Policy number should be different for the copy submission policy","Copy Submission Policy number is not as same as previous submission", "PASS");
					Flag = true;
					break;
				}
				
			}			
			
		}
		return Flag;
	}
}