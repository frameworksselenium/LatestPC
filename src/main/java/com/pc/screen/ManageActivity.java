/**
 * @ClassPurpose This Class used to create activites for account and polcies
 * @Scriptor Krishna
 * @ReviewedBy
 * @ModifiedBy Sojan
 * @LastDateModified 4/12/2017
 */
package com.pc.screen;

import java.io.IOException;
import java.util.Date;
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

public class ManageActivity {

	public static String sheetname = "ManageActivity";
	Common common = CommonManager.getInstance().getCommon();
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));

	/**
	 * @function Use to perform all the objects/elements/functions from the
	 *           ManageActivity excel sheet
	 * @return true/false
	 * @throws Exception
	 */
	public Boolean SCRManageActivity() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

	/**
	 * @function use to verify the results of the testcase
	 * @param funValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean ActivityResults(String funValue) throws Exception {

		boolean status = false;
		String[] sValue = funValue.split(":::");
		String sTableValue = null;
		String value = null;
		String wait = HTML.properties.getProperty("SHORTWAIT");
		Integer x = Integer.valueOf(wait);
		switch (sValue[0].toUpperCase()) {
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
			status = common.ElementExistOrNotFalse("" + sValue[0] + "", "Queue Value", "Queue Value", Common.o.getObject("eleMAAssignQueue"));
			break;
		}
		if (status == false) {
			status = true;
		}
		return status;
	}

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
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Activity should Exist", "Activity Exist", "PASS");
				status = true;
				break;
			}
		}
		if (status == false) {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "Activity should Exist", "Activity Not Exist", "FAIL");
			status = false;
		}
		return status;
	}

	public boolean CloseCreate() {
		boolean status = false;
		status = common.MouseHoverAction(Common.o.getObject("eleRemainder"), Common.o.getObject("eleRemainderFollowUp"));
		return status;
	}

	/**
	 * @function use to select the new activity accoridng to the text
	 * @param strValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean SelectActivity(String strValue) throws Exception {
		boolean status = false;
		String[] sFuncValue = strValue.split(":::");
		switch (sFuncValue[0].toUpperCase()) {
		case "SCROLL":
			for (int i = 0; i <= Integer.valueOf(sFuncValue[2]); i++) {
				status = common.SafeAction(Common.o.getObject("eleActvityScrollBottom"), "eleActvityScrollBottom", 

"eleActvityScrollBottom");
			}
			status = common.SelectActivity(sFuncValue[1]);
			break;
		case "NOSCROLL":
			status = common.SelectActivity(sFuncValue[1]);
			break;
		case "POLICYTRANSACTION":
			status = selectPolicyTransaction(sFuncValue[1]);
			break;
		}
		return status;
	}

	/**
	 * @function This function use to Create Activity as per the input
	 * @return String
	 * @throws IOException
	 * @throws Exception
	 */
	public boolean selectPolicyTransaction(String strValue) throws IOException {
		boolean Status = false;
		// By option = By.xpath("//span[starts-with(text(),'"+strValue+"')]");
		By option = By.xpath("//*[contains(@id,'AgentofRecordTransfer')]//span[contains(text(), '" + strValue + "')]");
		try {
			// Status = CommonManager.getInstance().getCommon().SafeAction(option,
			// "scl","scl");
			Status = CommonManager.getInstance().getCommon().SafeAction(option, "ele", "ele");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Thread ID = " + Thread.currentThread().getId() + " Error Occured =" + e.getMessage(), e);
		}
		if (Status) {
			// logger.info("Clicked on '" + option + "' element or button or link and
			// element '"+ option + "'");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "System should click on '" + option + "' element or button or link", "Clicked on '" + option + "' element or button or link", 

"PASS");
		} else {
			// logger.info("Object not enabled or displayed or not clickable '"+ option +
			// "'");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "System should display '" + option + "' in screen", "'" + option + "' not displayed in screen", "FAIL");
		}

		return Status;
	}

	/**
	 * @fucntion use to
	 * @param sReadActionCol
	 * @param sReadString
	 * @param sTagname
	 * @return
	 * @throws Exception
	 */
	public boolean CurrentActivityTable(int sReadActionCol, String sReadString, String sTagname) throws Exception {
		boolean status = false;
		status = common.ActionOnTable(Common.o.getObject("elepolicySummary_Activitytbl"), 2, sReadActionCol, sReadString, sReadString, 

sTagname);
		return status;
	}

	/**
	 * @function This function use to verify the results
	 * @param funValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean VerifyResults(String funValue) throws Exception {
		boolean status = false;
		String[] sValue = funValue.split(":::");
		String Value = null;
		switch (sValue[0].toUpperCase()) {
		case "CURRENTACTIVITY":
			logger.info("Validating the '" + sValue[0] + "'");
			// status = CurrentActivityTable(2, sValue[1], "a");
			By sActivityElement1 = By.xpath("//a[@class='g-actionable' and contains(text(),'" + sValue[1] + "')]");
			status = common.SafeAction(sActivityElement1, "eleActivityElement", "eleActivityElement");
			break;
		case "CHECKACTIVITY":
			logger.info("Validating the '" + sValue[0] + "'");
			// Value =
			// common.GetTextFromTable(Common.o.getObject("eleShellSummaryActivityTable"),
			// 2, sValue[1]);
			// status = common.CompareStringResult(sValue[0], sValue[1], Value);
			// By sActivityElement = By.xpath(".//a[contains(text(),'"+sValue[1]+"')]");
			By sActivityElement = By.xpath("//a[@class='g-actionable' and contains(text(),'" + sValue[1] + "')]");
			status = common.ElementExistOrNotTrue("ActivityCheck", "'" + sValue[1] + "' actvity should be created", "'" + sValue[1] + 

"' actvity is creatred", sActivityElement);
			break;
		case "CHECKACTIVITYCLA":
			logger.info("Validating the '" + sValue[0] + "'");
			Value = common.GetTextFromTable(Common.o.getObject("eleActivityTable"), 2, sValue[1]);
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			// By sActivityElement = By.xpath(".//a[contains(text(),'"+sValue[1]+"')]");
			// By sActivityElement = By.xpath("//a[@class='g-actionable' and
			// contains(text(),'"+sValue[1]+"')]");
			// status = common.ElementExistOrNotTrue("ActivityCheck", "'"+sValue[1]+"'
			// actvity should be created", "'"+sValue[1]+"' actvity is creatred",
			// sActivityElement);
			break;
		case "ACTIVITYSTATUS":
			logger.info("Validating the '" + sValue[0] + "'");
			Value = common.ReadElementGetAttribute(Common.o.getObject("eleActivity_Status"), "value", Integer.valueOf

(HTML.properties.getProperty("VERYLONGWAIT")));
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			break;
		case "VALIDATION":
			logger.info("Validating the '" + sValue[0] + "'");
			Value = common.ReadElementGetAttribute(Common.o.getObject("eleActivity_Status"), "value", Integer.valueOf

(HTML.properties.getProperty("VERYLONGWAIT")));
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			break;
		case "DATECHECK":
			logger.info("Validating the '" + sValue[0] + "'");
			Value = common.ReadElementGetAttribute(Common.o.getObject("eleActivity_EscalationDate"), "value", Integer.valueOf

(HTML.properties.getProperty("VERYLONGWAIT")));
			status = common.CompareStringResult(sValue[0], SCRCommon.ReturnDate(5), Value);
			break;

		case "VERIFYPOLICYTRANSACTION":
			logger.info(sValue[0]);
			Value = common.GetTextFromTable(Common.o.getObject("eleAccountSummary_PolicyTranTbl"), 5, 8, sValue[1]);
			status = common.CompareStringResult("verify display of biling Number", sValue[2], Value);
			if (sValue[3].toUpperCase().equalsIgnoreCase("SPAN") || sValue[3].toUpperCase().equalsIgnoreCase("DIV")) {
				status = common.ActionOnTable(Common.o.getObject("eleAccountSummary_PolicyTranTbl"), 8, 8, sValue[2], sValue

[3].toLowerCase());
				if (status) {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Billing method '" + sValue[2] + "' should display without hyperlink", "'" + sValue[0] + "' displayed without hyperlink", "PASS");
				} else {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Billing method '" + sValue[2] + "' should display without hyperlink", "'" + sValue[0] + "' NOT displayed without hyperlink", "FAIL");
				}
			} else if (sValue[3].toUpperCase().equalsIgnoreCase("LINK")) {
				status = common.ActionOnTable(Common.o.getObject("eleAccountSummary_PolicyTranTbl"), 8, 8, sValue[2], "a");
				if (status) {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Billing method '" + sValue[2] + "' should display with hyperlink", "'" + sValue[0] + "' displayed with hyperlink", "PASS");
				} else {
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Billing method '" + sValue[2] + "' should display with hyperlink", "'" + sValue[0] + "' NOT displayed with hyperlink", "FAIL");
				}
			} else {
				status = false;
			}
			break;
		case "VERIFYSUBANDDESC":
			logger.info("Verification started for '" + sValue[0] + "'");
			Value = common.ReadElement(Common.o.getObject("eleActivitySubjectVal"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("Verify subject for the activity", sValue[1], Value);
			if (!sValue[2].toUpperCase().equals("EMPTY")) {
				Value = common.ReadElementGetAttribute(Common.o.getObject("eleActivityDescVal"), "value", Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
				status = common.CompareStringResult("Verify Descriptionfor the activity", sValue[2], Value);
				break;
			} else {
				Value = common.ReadElementGetAttribute(Common.o.getObject("eleActivityDescVal"), "value", Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
				if (Value.trim().isEmpty()) {
					status = true;
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), " Description for Activity " + sValue[0] + " should be empty", "Description is empty", "PASS");
				} else {
					status = false;
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), " Description for Activity " + sValue[0] + " should be empty", "Description is NOT empty. It shown as '" + Value + 

"'", "FAIL");
				}
			}
			break;

		}
		if (status == false) {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "" + sValue[0] + " case should PASS", "" + sValue[0] + " case should not fail", "FAIL");
			status = true;
		}
		return status;
	}

	/**
	 * @function Use to select the created activity
	 * @param sFuncValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean selectCreatedActivity(String sFuncValue) throws Exception {
		boolean status = false;
		// status = common.ActionOnTable(Common.o.getObject("eleActivityTable"), 2, 2,
		// sFuncValue, "div");
		status = common.ActionOnTable(Common.o.getObject("eleActivityTable"), 2, 2, sFuncValue, sFuncValue, "a");
		return status;
	}

	/***
	 * Function to verify billing/ installment plan method drop down values for
	 * different conditions
	 * 
	 * @param sFuncValue
	 * @return true/false
	 * @throws Exception
	 *             First value in the data sheet should be the element name
	 */
	public boolean dropDownListValue(String funValue) throws Exception {
		boolean status = false;
		String[] sValue = funValue.split(":::");
		logger.info("Entering into Verify Billing MethodList function");
		WebElement sBillingMethodList = common.returnObject(Common.o.getObject("eleClassName"));
		List<WebElement> sGetList = sBillingMethodList.findElements(By.tagName("li"));
		Integer eleExpectedCount = sValue.length;
		for (int i = 0; i < eleExpectedCount; i++) {
			for (WebElement element : sGetList) {
				status = false;
				String objName = element.getText();
				if (objName.contains(sValue[i])) {
					status = true;
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Drop down '" + sValue[i] + "' should be having value '" + sValue[i] + "'", "Drop down '" + sValue[i] + "' is having option '" + sValue[i] + "'", "PASS");
					break;
				}
			}
			if (status == false) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Drop Down Value of " + sValue[i] + " should be present", "Drop Down Valsue of " + sValue[i] + " is not present", 

"FAIL");
				status = true;
			}
		}
		return status;
	}

	/**
	 * @function return the date according to the input
	 * @param sFuncValue
	 * @return true/false
	 */
	public boolean escalationDate() {
		boolean status = false;
		// String sDate = SCRCommon.ReturnDate(5);
		try {
			status = common.SafeAction(Common.o.getObject("edtEscalationDate"), String.valueOf(SCRCommon.ReturnDate(5)), "edtEscalationDate");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	public boolean dateFromWidget(String strValue)   {
           
            logger.info("DateFromWidget fn started");
           boolean status=false;
           boolean foundStatus = false;
           
            
       String[] value=strValue.split(":::");
      String pathName = value[0];
      String date=value[1];
      String month=value[2];
      String year=value[3];
      
       String monthYearValue = month.concat(" "+year);
      logger.info(monthYearValue);
      
      WebElement dateWidget = ManagerDriver.getInstance().getWebDriver()
                  .findElement(common.o.getObject(pathName));
     dateWidget.click();
     logger.info("Clicked on Escalation date widget");
      
     //  common.o.getObject("eleEscalationDate");
     WebElement mytable = ManagerDriver.getInstance().getWebDriver()
                 .findElement(Common.o.getObject("eleMonthDateView"));
     String valueFromUI = mytable.getAttribute("innerText");
     logger.info(valueFromUI);
      
     //  String dataValue[] = mytable.getAttribute("value").split(" ");
      
       
      
     
       
       while(!foundStatus)
      {
           // String dataValue[] = mytable.getAttribute("value").split(" ");
             
             // mon = dataValue[0];
             
             
             if(monthYearValue.equalsIgnoreCase(valueFromUI))
            {
                 foundStatus = true;
                 logger.info("Date "+monthYearValue+" selected");
                  break;
            }
            else
            {
                  logger.info("Finding");
                 WebElement monthmove = ManagerDriver.getInstance().getWebDriver()
                               .findElement(Common.o.getObject("navigateRight"));
                 logger.info("Searching for month and year");
                    monthmove.click();
                    valueFromUI = mytable.getAttribute("innerText");
                    if(monthYearValue.equalsIgnoreCase(valueFromUI))
                    {
                        foundStatus = true;
                        logger.info("Month and Year Found.");
                        logger.info(foundStatus);
                           break;
                           
                     }
                    
                   
             }
             status = true;
      }
      

      
       String datePath = "//*[contains(@title,'"+month+" "+date+","+" "+year+"')]";
     WebElement selectDate = ManagerDriver.getInstance().getWebDriver()
                        .findElement(By.xpath(datePath));
     selectDate.click();
     logger.info("Date selected : "+date);
     
      return status;
           
            
     }

	/**
	 * @function Use to select the activity dynamically if there is a scroll also
	 * @param sFuncValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean scrollActivity(String sFuncValue) throws Exception {
		boolean status = false;
		logger.info("Entering into the scrollActivity function");
		String[] arrActivtyName = sFuncValue.split(":::");
		for (int j = 0; j < arrActivtyName.length; j++) {
			for (int i = 0; i <= 50; i++) {
				List<WebElement> sGetList = driver.findElements(By.className("x-menu-item-text"));
				for (WebElement element : sGetList) {
					String objName = element.getText();
					if (objName.equals(arrActivtyName[j])) {
						String readAttriID1 = element.getAttribute("id");
						common.highlightElement(By.id(readAttriID1));
						logger.info("Activity '" + readAttriID1 + "' Found");
						status = common.SafeAction(By.id(readAttriID1), "ele", "ele");

						HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), 

PCThreadCache.getInstance().getProperty("methodName"), "System should click the " + arrActivtyName[j] + " Activity", "System clicked " + 

arrActivtyName[j] + " Activity", "PASS");
						status = true;
						break;
					}
				}
				if (status == false) {
					common.SafeAction(Common.o.getObject("eleActvityScrollBottom"), "eleActvityScrollBottom", 

"eleActvityScrollBottom");
				} else if (status == true) {
					break;
				}
			}
			if (status == false) {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "" + arrActivtyName[j] + "Activity should present in the list", "" + arrActivtyName[j] + "Activity is not present in the list", "FAIL");
			}

		}

		return status;
	}
	/**
	 * public boolean getExpiryDate() { boolean status = false; String expiryDate =
	 * null; try { status = common.SafeAction(common.o.getObject("elePolicy Info"),
	 * "elePolicy Info","elePolicy Info"); expiryDate
	 * =common.ReadElement(common.o.getObject("elePolicyInfo_ExpDate"),
	 * Integer.valueOf(HTML.properties.getProperty("NORMALWAIT"))); //
	 * PCThreadCache.getInstance().setProperty("POlicyExpiryDate", expiryDate); }
	 * catch(Exception e) { e.printStackTrace(); }
	 * 
	 * return status; }
	 **/
}
