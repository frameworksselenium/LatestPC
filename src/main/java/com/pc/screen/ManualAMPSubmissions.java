
/**
 * @ClassPurpose This Class used for the Manual AMP Submission usecase
 * @Scriptor Kumar
 * @ReviewedBy
 * @ModifiedBy Sojan David
 * @LastDateModified 3/24/2017
 */
package com.pc.screen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.PCThreadCache;

public class ManualAMPSubmissions {

	public static String sheetname = "ManualAMPSubmissions";
	Common common = CommonManager.getInstance().getCommon();
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
	public static List<String> tList = new ArrayList<String>();
	/*
	 * public static int n = 0; public static int k = 0;
	 */

	/**
	 * @function Use to perform all the objects/elements/functions from the Manual
	 *           AMP Submission excel sheet
	 * @return true/false
	 * @throws Exception
	 */
	public Boolean SCRManualAMPSubmissions() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

	/**
	 * @function Function to Select the Specified Current Activity
	 * @param sValue
	 * @return
	 * @throws Exception
	 */
	public boolean SelectCurrentActivities(String sValue) throws Exception {
		boolean status = false;
		status = common.ActionOnTable(Common.o.getObject("eleActivityTable"), 2, 2, sValue, sValue, "a");
		return status;
	}

	// /**
	// * @function Function to Select the Specified Policy Transaction
	// * @param sValue
	// * @return true/false
	// * @throws Exception
	// */
	// public boolean SelectPolicyTransaction(String sValue) throws Exception
	// {
	// boolean status = false;
	// String[] arrValue = sValue.split(":::");
	// WebElement mytable =
	// driver.findElement(Common.o.getObject("eleAccountSummary_PolicyTranTbl"));
	// List<WebElement> allrows = mytable.findElements(By.tagName("tr"));
	// if (tList.size() == 0)
	// {
	// for(int i = 0; i <= allrows.size()-1; i++)
	// {
	// List<WebElement> Cells = allrows.get(i).findElements(By.tagName("td"));
	// String readText = Cells.get(4).getText();
	//
	// if (readText.contains(arrValue[0]))
	// {
	// String sReadText = Cells.get(0).getText();
	// if (sReadText.contains(arrValue[1]))
	// {
	// String sTrans = Cells.get(1).getText();
	// tList.add(sTrans);
	//
	// }
	// }
	// }
	// }
	// for (int j=k; j <=tList.size()-1;j++)
	// {
	// for(int i = n; i <= allrows.size()-1; i++)
	// {
	// List<WebElement> CellToClick = allrows.get(i).findElements(By.tagName("a"));
	// for(WebElement element: CellToClick)
	// {
	// String objName = element.getText();
	// if(objName.contains(tList.get(j)))
	// {
	// n=i+1;
	// k=j+1;
	// element.click();
	// status = true;
	// break;
	// }
	// }
	// if (status==true)
	// {
	// break;
	// }
	// }
	// if (status==true)
	// {
	// break;
	// }
	//
	// }
	//
	//
	// return status;
	// }


	public boolean SelectPolicyTransaction(String sFuncValue) throws Exception {
		boolean status = false;
		String sParameter[] = sFuncValue.split(":::");
		// status =
		// common.ActionOnTable(Common.o.getObject("eleAccountSummary_PolicyTranTbl"),
		// 2, 3, sParameter[0], sParameter[1], "a");
		status = common.ActionOnTable(Common.o.getObject("eleAccountSummary_PolicyTranTbl"), 2, 4, sParameter[0], sParameter[1], "a");
		return status;
	}

	/**
	 * @function Use to verify the results of the AMP Submission use case
	 * @param funValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean ActivityResults(String funValue) throws Exception {

		boolean status = false;
		String[] sValue = funValue.split(":::");
		String sTableValue = null;
		String wait = HTML.properties.getProperty("SHORTWAIT");
		Integer x = Integer.valueOf(wait);
		/*switch (sValue[0].toUpperCase()) {
		case "VERIFYACTIVITYLIST":
			logger.info("Validating the '" + sValue[0] + "'");
			status = ActivityTable(sValue[1]);
			break;

		case "VERIFYACTIVITYCLOSED":
			logger.info("Validating the '" + sValue[0] + "'");
			status = common.ElementExistOrNotFalse("" + sValue[0] + "", "Activity", "Activity", Common.o.getObject

("eleActivityFollowUpList"));
			break;

		case "VERIFYNOTESWARNING":
			logger.info("Validating the '" + sValue[0] + "'");
			String sExpectedViewNotesWarning = sValue[1];
			String sAcutualViewNotesWarning = common.ReadElement(Common.o.getObject("txtViewNoteWarningMsg"), x);
			status = common.CompareStringResult("" + sValue[0] + "", sExpectedViewNotesWarning, sAcutualViewNotesWarning);
			break;

		case "VERIFYCANCELCHECK":
			logger.info("Validating the '" + sValue[0] + "'");
			status = common.ElementExistOrNotFalse("", "Activity Cancel Button should not exist", "Activity Cancel Button is not exist", Common.o.getObject("eleActivityCancel"));
			break;

		case "ASSIGNTOFIELD":
			logger.info("Validating the '" + sValue[0] + "'");
			sTableValue = common.GetTextFromTable(Common.o.getObject("eleActivityTable"), 3, sValue[1]);
			status = common.CompareStringResult("" + sValue[0] + "", sValue[1], sTableValue);
			break;

		case "PARTNEREXCEPTION":
			logger.info("Validating the '" + sValue[0] + "'");
			sTableValue = common.GetTextFromTable(Common.o.getObject("eleActivityTable"), 3, sValue[1]);
			status = common.CompareStringResult("" + sValue[0] + "", sValue[1], sTableValue);
			break;

		case "SEARCHFORASSIGNRESET":
			logger.info("Validating the '" + sValue[0] + "'");
			status = common.ElementExistOrNotFalse("" + sValue[0] + "", "Queue Value", "Queue Value", Common.o.getObject

("eleMAAssignQueue"));
			break;
		}*/
		if (status == false) {
			status = true;
		}
		return status;
	}

	/**
	 * @function uses the select the activity table
	 * @param sFActivitySubject
	 * @return true/false
	 * @throws Exception
	 */
	public boolean ActivityTable(String sFActivitySubject) throws Exception {
		boolean status = false;
		String wait = HTML.properties.getProperty("SHORTWAIT");
		Integer x = Integer.valueOf(wait);
		WebElement tableRowXpathRole = driver.findElement(Common.o.getObject("eleActivityTable"));
		List<WebElement> rows_table1 = tableRowXpathRole.findElements(By.tagName("tr"));
		int rows_count1 = rows_table1.size();
		for (int i = 1; i <= rows_count1; i++) {
			String sActvitySubject = common.ReadElement(By.xpath(".//*[@id='AccountFile_Summary:AccountFile_SummaryScreen:AccountFile_Summary_ActivitiesLV-body']/div/table/tbody/tr['+i+']/td[3]"), x);
			if (sActvitySubject.equals(sFActivitySubject)) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Activity should Exist", "Activity Exist", "PASS");
				status = true;
				break;
			}
		}
		if (status == false) {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Activity should Exist", "Activity Not Exist", "FAIL");
			status = false;
		}
		return status;
	}

	/**
	 * @function Use to close the activity
	 * @return true/false
	 */
	public boolean CloseCreate() {
		boolean status = false;
		status = common.MouseHoverAction(Common.o.getObject("eleRemainder"), Common.o.getObject("eleRemainderFollowUp"));
		return status;
	}

	/**
	 * @function This function use to select the activiy
	 * @param strValue
	 * @return true/false
	 * @throws IOException
	 */
	public boolean SelectActivity(String strValue) throws IOException {
		boolean status = false;
		status = common.SelectActivity(strValue);
		return status;
	}

	/**
	 * @function Use to de
	 * @param sFunctionValue
	 * @return
	 * @throws Throwable
	 */
	public boolean lineOfBusiness(String sFunctionValue) throws Throwable {
		boolean status = false;
		String[] sValue = sFunctionValue.split(":::");
		int sRowCount = SCRCommon.TableRowCount(Common.o.getObject("eleCLAtblLineOfBusiness"));
/*			switch ("sValue[0].toUpperCase()") {
		case "LOB":
			logger.info(sValue[0]);
			try {
				status = SCRCommon.DataWebTable(Common.o.getObject("eleCLAtblLineOfBusiness"), sRowCount, 1, sValue[1], "single", "div");
				status = common.SafeAction(Common.o.getObject("lstLOBval"), sValue[2], "lst");
				// status =
				// SCRCommon.DataWebTable(Common.o.getObject("eleCLAtblLineOfBusiness"),
				// sRowCount, 2, sValue[3],"single");
				status = common.SafeAction(Common.o.getObject("lstLOSval"), sValue[3], "lst");
			} catch (Throwable e) {
				e.printStackTrace();
				if (status == false) {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should complete performing the add class", "System not completed performing the Line of Business table", "FAIL");
				}
			}
			break;
		case "LOS":
			logger.info(sValue[0]);
			try {
				status = SCRCommon.DataWebTable(Common.o.getObject("eleCLAtblLineOfBusiness"), sRowCount, 2, sValue[1], "single", 

"div");
				status = common.SafeAction(Common.o.getObject("lstLOSval"), sValue[2], "lst");
			} catch (Exception e) {
				e.printStackTrace();
				if (status == false) {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should complete performing the add class", "System not completed performing the Line of Business table", 

"FAIL");
				}
			}
			break;
		case "EXPOSURESTATE":
			logger.info(sValue[0]);
			try {
				status = common.ActionOnTable(Common.o.getObject("eleLOBtbl"), 3, 3, "<none>", "a");
				status = getExposureStateList(sValue[1]);
				status = common.SafeAction(Common.o.getObject("eleCommonUpdate"), "", "eleCommonUpdate");
				status = SCRCommon.PageVerify("CLATitlePolicyInfo");
			} catch (Exception e) {
				e.printStackTrace();
				if (status == false) {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should complete performing the add class", "System not completed performing the Line of Business table", 

"FAIL");
				}
			}
			break;

		}*/
		return status;
	}

	/**
	 * @function Use to get the list of exposure state and select the state
	 *           according to the sheet
	 * @param sFuncValue
	 * @return true/false
	 */
	public boolean getExposureStateList(String sFuncValue) {
		boolean status = false;
		logger.info("Entering into the exposure state function");
		Actions objActions = new Actions(ManagerDriver.getInstance().getWebDriver());
		WebElement sExpsoureStateList = common.returnObject(Common.o.getObject("eleExposureStateList"));
		List<WebElement> sGetList = sExpsoureStateList.findElements(By.tagName("li"));
		for (WebElement element : sGetList) {
			String objName = element.getText();
			if (objName.contains(sFuncValue)) {
				objActions.doubleClick(element).build().perform();
				status = true;
				break;
			}
		}
		return status;
	}

}