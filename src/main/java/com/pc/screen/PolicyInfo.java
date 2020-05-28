

package com.pc.screen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;








//import org.apache.commons.collections.functors.ForClosure;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;



import com.pc.driver.LoggerClass;
//import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptExecutor;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.PCThreadCache;
import com.pc.utilities.XlsxReader;

public class PolicyInfo {

	public static String sheetname = "PolicyInfo";
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();
	SCRCommon scrcommon=new SCRCommon();
	public Boolean SCRPolicyInfo() throws Exception {

		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

	public boolean VerifyResults(String sFuncValue) throws Exception {
		boolean status = false;
		String[] sValue = sFuncValue.split(":::");
		logger.info("Verifying the Results");
		String Value = null;
		switch (sValue[0].toUpperCase()) {
		case "VERIFYPOLICYINFO":
			logger.info(sValue[0]);
			Value = common.ReadElementGetAttribute(Common.o.getObject("edtIndusCode"), "value", Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			Value = common.ReadElementGetAttribute(Common.o.getObject("edtNAICS"), "value", Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
			status = common.CompareStringResult(sValue[0], sValue[2], Value);
			Value = common.ReadElementGetAttribute(Common.o.getObject("edtMSI"), "value", Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
			status = common.CompareStringResult(sValue[0], sValue[3], Value);
			Value = common.ReadElementGetAttribute(Common.o.getObject("edtEBS"), "value", Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
			status = common.CompareStringResult(sValue[0], sValue[4], Value);
			Value = common.ReadElementGetAttribute(Common.o.getObject("lstOrganisationType"), "value", Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
			status = common.CompareStringResult(sValue[0], sValue[5], Value);
			Value = common.ReadElementGetAttribute(Common.o.getObject("edtFEIN"), "value", Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
			status = common.CompareStringResult(sValue[0], sValue[6], Value);
			Value = common.ReadElementGetAttribute(Common.o.getObject("lstProductType"), "value", Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
			status = common.CompareStringResult(sValue[0], sValue[7], Value);
			Value = common.ReadElementGetAttribute(Common.o.getObject("lstSubmissionSource"), "value", Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
			status = common.CompareStringResult(sValue[0], sValue[8], Value);
			Value = common.ReadElementGetAttribute(Common.o.getObject("lstSalesAgreementCode"), "value", Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
			status = common.CompareStringResult(sValue[0], sValue[9], Value);
			break;
		case "VERIFYPOLICYINFOSCREEN":
			Value = common.ReadElement(Common.o.getObject("elePolicyInfoAssert"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			break;
		case "VERIFYBORSTARTDATE":
			logger.info(sValue[0]);

			Value = common.ReadElement(Common.o.getObject("elePolBORStartDate"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("PolicyBORStartDate", SCRCommon.ReturnCurrentDate(), Value);
			break;

		case "VERIFYBORENDDATE":
			logger.info(sValue[0]);
			Value = common.ReadElementGetAttribute(Common.o.getObject("elePolBOREndDate"), "value", Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("PolicyBOREnd", sValue[1], Value);
			break;

		case "BORDATE":
			logger.info(sValue[0]);

			Value = common.ReadElement(Common.o.getObject("elePolBORStartDate"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("PolicyBORStartDate", SCRCommon.ReturnCurrentDate(), Value);
			Value = common.ReadElement(Common.o.getObject("elePolBOREndDate"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult("PolicyBOREndDate", SCRCommon.ReturnSixtyDaysFromDate(), Value);
			break;
		case "VERIFYSORBORNOTSET":
			logger.info(sValue[0]);
			Value = common.ReadElement(Common.o.getObject("elePolBORStartDate"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			if (Value.equals(SCRCommon.ReturnCurrentDate())) {
				status = false;
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Current Date should not match", "Current Date is matching", "FAIL");
			} else {
				status = true;
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Current Date should not match", "Current Date is not matching", "PASS");
			}

			Value = common.ReadElement(Common.o.getObject("elePolBOREndDate"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			if (Value.equals(SCRCommon.ReturnSixtyDaysFromDate())) {
				status = false;
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Sixty Days from Current Date should not match", "Sixty Days from Current Date is matching", "FAIL");
			} else {
				status = true;
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Sixty Days from Current Date should not match", "Sixty Days from Current Date is not matching", "PASS");
			}

			break;
		}
		if (!status) {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "" + sValue[0] + " case should PASS", "" + sValue[0] + " case should not fail", "FAIL");
			status = true;
		}
		return status;
	}

	/**
	 * @function use to select the check box of the particular Named Insured in
	 *           Additional Named Insured table
	 * @param sReadString
	 * @return true/false
	 * @throws Exception
	 */
	public boolean SelectNamedInsured(String sReadString) throws Exception {
		boolean status = false;
		JavascriptExecutor js = (JavascriptExecutor) ManagerDriver.getInstance().getWebDriver();
		status = SCRCommon.ActionOnTableCheckBox(Common.o.getObject("eleAddInsDBAAddedTable"), 2, 0, sReadString, "img", "elePolicyInfoAssert");
		status = SCRCommon.JavaScript(js);
		return status;
	}

	public void verifyRadio(String eleName) {

		List<WebElement> radio_options = ManagerDriver.getInstance().getWebDriver().findElements(Common.o.getObject(eleName));
		for (WebElement option : radio_options) {
			if (option.isSelected()) {
				System.out.println(option.getAttribute("innerHTML"));
				break;
			}
		}
	}

	public boolean submissionOriginalDate(String strFuncValue) throws Exception {
		boolean status = false;
		String[] strSplitValue = strFuncValue.split(":::");
		String strDate = null;
		switch (strSplitValue[0].toUpperCase()) {
		case "CURRENTDATE":
			strDate = SCRCommon.ReturnCurrentDate();
			status = common.SafeAction(Common.o.getObject("edtSubmissionOrginalDate"), strDate, "edt");
			break;
		case "INCREASEDECREASEDATE":
			strDate = SCRCommon.returnDate(Integer.parseInt(strSplitValue[1]));
			status = common.SafeAction(Common.o.getObject("edtSubmissionOrginalDate"), strDate, "edt");
			break;
		}
		return status;
	}

	public boolean mMUserNBReceivedDate(String strFuncValue) throws Exception {
		boolean status = false;
		String[] strSplitValue = strFuncValue.split(":::");
		String strDate = null;
		switch (strSplitValue[0].toUpperCase()) {
		case "CURRENTDATE":
			strDate = SCRCommon.ReturnCurrentDate();
			status = common.SafeAction(Common.o.getObject("edtMMUserReceivedDate"), strDate, "edt");
			break;
		case "INCREASEDECREASEDATE":
			strDate = SCRCommon.returnDate(Integer.parseInt(strSplitValue[1]));
			status = common.SafeAction(Common.o.getObject("edtMMUserReceivedDate"), strDate, "edt");
			break;
		}
		return status;
	}

	public boolean VerifyDropDownPolicy(String value) throws Exception {
		Boolean status = false;
		By ele = Common.o.getObject("lstPolicyInfo_PolicyType");
		String id = ManagerDriver.getInstance().getWebDriver().findElement(ele).getAttribute("id");
		String[] val = value.split("&&");
		for (String string : val) {

			String[] sValue = string.split(":::");
			if (sValue[sValue.length - 1].equalsIgnoreCase("FALSE")) {
				status = SCRCommon.VerifyDropDownFalse(id, sValue[0]);
			} else
				status = SCRCommon.VerifyDropDownValue(id, sValue[0]);
			if (!status)
				return false;
		}
		return status;// ("lstPolicyInfo_PolicyType",val);
	}

	public Boolean ComparePolicyType(String val) throws IOException {
		Boolean status = false;
		WebElement ele = ManagerDriver.getInstance().getWebDriver().findElement(common.o.getObject("lstPolicyInfo_PolicyType"));
		String org = ele.getAttribute("value");
		if (org.equalsIgnoreCase(val)) {
			status = true;
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "Policy Type Should be Restricted to " + val, "Policy Type is Restricted to " + val, "PASS");
		} else
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "Policy Type Should be Restricted to " + val, "Policy Type is not Restricted to " + val, "FAIL");
		return status;
	}

	public Boolean RemoveInsuredName(String funValue) {
		boolean status = false;
		String[] arrCovName = funValue.split(":::");
		int iRowCnt = 0;
		try {
			// status=common.SafeAction(Common.o.getObject("eljBlanketPopup_AddBlanket"),
			// "eljBlanketPopup_AddBlanket", "eljBlanketPopup_AddBlanket");
			iRowCnt = SCRCommon.TableRowCount(Common.o.getObject("eleAddInsDBAAddedTable"));
			if (iRowCnt > 0) {
				if (!(arrCovName[0].toUpperCase().equals("ALL"))) {
					for (int i = 0; i < arrCovName.length; i++) {
						status = common.ActionOnTable1(Common.o.getObject("eleAddInsDBAAddedTable"), 2, 0, arrCovName[i], 

"img");
					}

				} else if (arrCovName[0].toUpperCase().equals("ALL")) {
					status = common.SafeAction(Common.o.getObject("eleInsuredAllRemove"), "ele", "eleInsuredAllRemove");
				}
				status = common.SafeAction(Common.o.getObject("eleAddInsDBARemove"), "ele", "eleAddInsDBARemove");

			} else {
				logger.info("No records available in Blanket Table to Remove the coverages!!");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Remove the Coverages dispalyed in blanket screen", "No Coverages displayed to Add to Blanket in Search Results", 

"WARNING");
				status = false;
				// status=common.SafeAction(Common.o.getObject("eljBlanketPopup_ReturnLink"),
				// "elj", "eljBlanketPopup_ReturnLink");

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return status;

	}

	public Boolean FillInsuredDetail() throws Exception {
		Boolean status = false;

		logger.info("Entering into New Named Insured Function");
		XlsxReader sXL = XlsxReader.getInstance();// new XlsxReader( PCThreadCache.getInstance().getProperty("DataSheetName)
		String TCID = null;
		String TCIteration = null;
		// Common common = CommonManager.getInstance().getCommon();
		Boolean tcAvailable = false;
		String sFuncValue = "InsuredNamedData";
		int rowcount = sXL.getRowCount(sFuncValue);
		for (int i = 2; i <= rowcount; i++) {
			TCID = PCThreadCache.getInstance().getProperty("TCID");
			TCIteration = PCThreadCache.getInstance().getProperty("Iteration");
			String TCIDAdd = sXL.getCellData(sFuncValue, "ID", i);
			String TCIDIteration = sXL.getCellData(sFuncValue, "Iteration", i);

			if (TCIDAdd.equals(TCID) && TCIDIteration.equals(TCIteration)) {
				tcAvailable = true;
				int colcount = sXL.getColumnCount(sFuncValue);
				for (int j = 2; j <= colcount; j++) {
					String sColName = sXL.getCellData(sFuncValue, j, 1);
					String sCellValue = sXL.getCellData(sFuncValue, j, i);
					if (!sCellValue.isEmpty()) {
						status = common.SafeAction(Common.o.getObject(sColName), sCellValue, sColName);

					}

				}

			}
		}
		if (!tcAvailable) {
			logger.info("" + TCID + " is not available in the InsuredNamedData Sheet");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "Validate the Testcase availability in datasheet - " + sFuncValue, "Testcass not available in Datasheet", "FAIL");

		} else if (!status) {
			logger.info("" + TCID + " is not available in the InsuredNamedData Sheet");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "Validate the Testcase availability in datasheet - " + sFuncValue, "Testcass not available in Datasheet", "FAIL");
		}
		return status;
	}

	public static boolean verifyErrorMsgText(String sValue) throws IOException {
	 loggers  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
		int c = 0;
		boolean status = false;
		Common common = CommonManager.getInstance().getCommon();
		List<String> a = new ArrayList<String>();
		String[] expMsg = null;
		boolean matchStatus = false;
		// String expectedText=null;
		if (common.ElementSize(Common.o.getObject("elePolicyInfoErrorMessage")) == 0) {
			loggers.info("No Error/Warning messages in screen to validate");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "Valdiate any error/Warning is displayed on screen", "No warning message is displayed on screen", "PASS");
			// status=true;
			return status;
		} else {
			WebElement eleErrtble = ManagerDriver.getInstance().getWebDriver().findElement(Common.o.getObject

("elePolicyInfoErrorMessage"));
			List<WebElement> eleDiv = eleErrtble.findElements(By.className("message")); // all div
			List<WebElement> eleErrIcon = eleErrtble.findElements(By.tagName("img")); // all div
			expMsg = sValue.split("::");

			for (String expectedText : expMsg) {
				loggers.info("Expected Message - " + expectedText);
				String type = null;
				for (int i = 0; i < eleDiv.size(); i++) {
					matchStatus = false;
					String actWarningMsg = eleDiv.get(i).getText();
					String TypeMsg = eleErrIcon.get(i).getAttribute("class");
					String[] Msgtype = TypeMsg.split("_");
					type = Msgtype[0];
					if (expectedText.equals(actWarningMsg) && Msgtype[0].equals("error")) {
						c++;
						matchStatus = true;
						loggers.info("Expected " + Msgtype[0] + " Message is matching with actual message '" + 

expectedText + "'");
						break;
					}

				}
				if (!matchStatus) {
					loggers.info("Expected " + type + " Message is NOT matching with actual message '" + expectedText + "'");
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Expected error text should matching with actual text '" + expectedText + "'", "Expected error text is not matching with actual text '" + expectedText + "'", "FAIL");
					break;
				}
				if (c == expMsg.length) {
					// HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
					// PCThreadCache.getInstance().getProperty("methodName"), "Expected error text
					// should matching with actual text '" + expectedText + "'","Expected error text
					// is matching with actual text '" + expectedText + "'", "PASS");
					status = true;
					break;
				}

			}
			if (!status) {
				loggers.info("Number of failed Expected Message: " + (expMsg.length - c) + " , In out of Total " + expMsg.length + " Expected Message ");
			}

		}

		return status;

	}

	public Boolean verifyInsured(String strReadString) throws IOException {
		boolean Status = false;
		boolean SearchString = false;
		String[] sfunVal = strReadString.split(":::");
		WebElement mytable = ManagerDriver.getInstance().getWebDriver().findElement(common.o.getObject("eleAddInsDBAAddedTable"));
		List<WebElement> allrows = mytable.findElements(By.tagName("tr"));
		for (int i = 0; i <= allrows.size() - 1; i++) {
			List<WebElement> Cells = allrows.get(i).findElements(By.tagName("td"));
			String readText = Cells.get(2).getText();
			if (readText.contains(sfunVal[1])) {
				SearchString = true;
				break;
			}
			//

		}
		if (sfunVal[0].equalsIgnoreCase("VISIBLETRUE")) {
			if (SearchString == true) {
				// if(Common.WaitUntilClickable(Common.o.getObject(ele),
				// Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT"))))
				// {
				logger.info("System displayed '" + sfunVal[1] + "'");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should display '" + sfunVal[1] + "'", "System displayed '" + sfunVal[1] + "'", "PASS");
				Status = true;
			} else {
				logger.info("System not displayed '" + sfunVal[1] + "'");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should display '" + sfunVal[1] + "'", "System not displayed '" + sfunVal[1] + "'", "FAIL");
				// Status = false;
			}
		} else if (sfunVal[0].equalsIgnoreCase("VISIBLEFALSE")) {
			if (SearchString == false) {
				logger.info("System not displayed '" + sfunVal[1] + "'");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should not display '" + sfunVal[1] + "'", "System not displayed '" + sfunVal[1] + "'", "PASS");
				Status = true;
			} else {
				logger.info("System displayed '" + sfunVal[1] + "'");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "System should not display '" + sfunVal[1] + "'", "System displayed '" + sfunVal[1] + "'", "FAIL");
				// Status = false;
			}
		}
		return Status;
	}
	
	public Boolean verifyClasscodeValuePresent(String strReadString) throws Exception {
		boolean status=false;
		
		Common common = CommonManager.getInstance().getCommon();

		//String sSetValue=strReadString.trim();
		String[] sSetValue = strReadString.split(":::");
		String Value = null;
		// set values for code
		status = common.SafeAction(Common.o.getObject("elePolicyInfo_PredominantClassCode"), "ele", "ele");
		status = common.SafeAction(Common.o.getObject("edtPolicyInfo_PreClassCode_Popup"), sSetValue[0], 

"edtPolicyInfo_PreClassCode_Popup");
		status = common.SafeAction(Common.o.getObject("eljCommonSearch"), "elj", "elj");
		
		if(sSetValue[1].contains("YES"))
		{
			status=SCRCommon.validate("VALIDATE_ELE_TEXT");
			if(status)
			{
				ManagerDriver.getInstance().getWebDriver().findElement(By.xpath("//a[contains(text(),'Return to Policy Info')]")).click();
				
			}
		}
		else
		{
		
		// Verify result table and set the code
			status = common.ElementExistOrNotTrue("Table existence", "Table should be displayed for Validating & selecting the Value", "Predominant CLass code search Result table dispalyed as expected", Common.o.getObject("elePredominateSearchRes"));
			if (!(sSetValue[0].isEmpty())) {
				status = common.ActionOnTable_JS(Common.o.getObject("elePredominateSearchRes"), 1, 0, sSetValue[0], "a");
		
				String Classcode=ManagerDriver.getInstance().getWebDriver().findElement(Common.o.getObject

("elePredominantClassCode")).getAttribute("value").trim();
				if(Classcode.contains(sSetValue[0]))
				{
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Verify whether class code can be Added successfully", "As expected, class code had been selected", "PASS");
				}
				else
				{
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "Verify whether class code can be Added successfully", "Class code had not been selected", "FAIL");
				}		
			}

		}
		
		return status;
	}
	
	public boolean VerifySegmentfieldValues(String sReadString) throws Exception {
		boolean status=false;
		int counter=0;
		String[] sSetValue = sReadString.split(":::");
		if(sSetValue.length>=1)
		{
			ManagerDriver.getInstance().getWebDriver().findElement(Common.o.getObject("lstNGS_IndustryDetails_Segment")).click();
			List<WebElement> allOptions = ManagerDriver.getInstance().getWebDriver().findElements(Common.o.getObject

("lstIndustryDetails_Segment"));
		    for (WebElement temp : allOptions) 
		    {
		    	String[] segmentvalue=temp.getText().split("\\r?\\n");
		    	if(segmentvalue.length>1)
		    	{
		    		for (String seg_value_temp : segmentvalue) 
		    		{
		    			for (String sSetValue_temp : sSetValue)
		    			{
		    				if(sSetValue_temp.trim().contains(seg_value_temp))
		    				{
		    					counter++;
		    				}
		    			}
		    		}
		    	}
		    }
		}
		if(sSetValue.length==counter)
		{
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "Policy Info should  only display those segments that can write the NGS Product ", "Only Expected segments are available in the field", "PASS");
			status=true;
		}
		else
		{
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "Policy Info should  only display those segments that can write the NGS Product ", "Expected segments are not available in the field", "FAIL");
			status=false;
		}
		return status;
	}

}
