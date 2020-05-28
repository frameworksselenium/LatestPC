/**
 * @ClassPurpose This Class used for the User generated documents usecase
 * @Scriptor Kumar
 * @ReviewedBy
 * @ModifiedBy Sojan David
 * @LastDateModified 3/24/2017
 */
package com.pc.screen;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.PCThreadCache;

public class UserGeneDocsShellPolicies {
	public static String sheetName = "UserGeneDocsShellPolicies";
	Common common = CommonManager.getInstance().getCommon();
	//static Logger logger = Logger.getLogger(sheetName);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();

	/**
	 * @function Use to perform all the objects/elements/functions from the User
	 *           generated documents excel sheet
	 * @return true/false
	 * @throws Exception
	 */
	public Boolean SCRUserGeneDocsShellPolicies() throws Exception {
		Boolean status = false;
		status = common.ClassComponent(sheetName, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

	/**
	 * @function This function use to select the Document Template dynamically
	 * @param value(Template
	 *            Name)
	 * @return status
	 * @throws Exception
	 */
	public boolean SelectDocsTemplate(String value) throws Exception {
		boolean status = false;
		String[] sValue = value.split(":::");
		try {
			switch (sValue[0].toUpperCase()) {
			case "ONE":
				status = common.ActionOnTable(Common.o.getObject("eleDocumentSearchResult"), 2, 0, sValue[1], sValue[2], "a");
				break;
			case "TWO":
				status = common.ActionOnTable(Common.o.getObject("eleDocumentFinalizeTable"), 5, 2, sValue[1], sValue[2], "a");
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	public boolean selectDocument(String value) throws Exception {
		boolean blnStatus = false;
		boolean blnSearchString = false;
		String strPageCount = null;
		String[] sValue = value.split(":::");
		blnSearchString = common.ActionOnTable(Common.o.getObject("eleDocumentSearchResult"), 2, 0, sValue[1], sValue[2], "a");
		if (blnSearchString) {
			blnStatus = true;
		}
		Outer:
		// while(!blnSearchString)
		if (!blnSearchString) {
			int icheckNextPageButton = common.ElementSize(Common.o.getObject("eleNextPage"));
			if (icheckNextPageButton > 0) {
				blnStatus = common.ElementExist(Common.o.getObject("eleNextPage"));
				if (blnStatus) {
					strPageCount = common.ReadElement(Common.o.getObject("eleCommonPageNumber"), Integer.valueOf

(HTML.properties.getProperty("LONGWAIT")));
					// strPageCount = strPageCount.substring(strPageCount.length()-2);
					strPageCount = SCRCommon.getIntFromString(strPageCount);
					for (int i = 0; i <= Integer.parseInt(strPageCount); i++) {
						blnStatus = common.SafeAction(Common.o.getObject("eleNextPage"), "", "eleNextPage");
						blnSearchString = common.ActionOnTable(Common.o.getObject("eleDocumentSearchResult"), 2, 0, 

sValue[1], sValue[2], "a");
						if (blnSearchString) {
							HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), 

PCThreadCache.getInstance().getProperty("methodName"), "System should search object in the table cell and click on object. Object name is '" + 

value + "'", "System searched object in the table and clicked on object. object name is '" + value + "'", "PASS");
							break Outer;
						}
					}
				} else {
					logger.info("" + value + " does not exist in Documents Table");
				}
			} else {
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "" + value + " should available in the my Queues Table", "" + value + " is not available in the my Queues Table", 

"FAIL");
			}
		}
		if (!blnSearchString) {
			logger.info("" + value + " does not exist in Documents Table");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "" + value + " should available in the my Queues Table", "" + value + " is not available in the my Queues Table", "FAIL");
		} else {
			blnStatus = true;
		}
		return blnStatus;
	}

	/**
	 * @function This function use to select the Document Template dynamically
	 * @param value(Template
	 *            Name)
	 * @return status
	 * @throws Exception
	 */
	/**
	 * public boolean selectTemplateDocs1(String value) throws Exception { boolean
	 * blnStatus = false; boolean blnSearchString = false; String strPageCount =
	 * null; String docName = null; String[] sValue = value.split(":::"); docName =
	 * sValue[1]; try { if(sValue[0].equalsIgnoreCase("ONE")) blnSearchString =
	 * SCRCommon.ActionOnTable(common.o.getObject("eleDocumentSearchResult"), 2,
	 * 0,docName,sValue[2], "a"); else if(sValue[0].equalsIgnoreCase("TWO"))
	 * blnSearchString =
	 * SCRCommon.ActionOnTable(common.o.getObject("eleDocumentFinalizeTable"), 5,
	 * 0,docName,sValue[2], "a"); Outer: if(!blnSearchString) { int
	 * icheckNextPageButton = common.ElementSize(Common.o.getObject("eleNextPage"));
	 * if(icheckNextPageButton > 0) { strPageCount =
	 * common.ReadElement(Common.o.getObject("eleCommonPageNumber"),
	 * Integer.valueOf(HTML.properties.getProperty("LONGWAIT"))); strPageCount =
	 * SCRCommon.getIntFromString(strPageCount); for(int i =0; i
	 * <Integer.parseInt(strPageCount); i++) { blnStatus =
	 * common.SafeAction(Common.o.getObject("eleNextPage"), "", "eleNextPage");
	 * blnStatus = common.SafeAction(Common.o.getObject("eleSearchResultTitle"), "",
	 * "eleSearchResultTitle"); if(sValue[0].equalsIgnoreCase("ONE"))
	 * blnSearchString =
	 * SCRCommon.ActionOnTable(common.o.getObject("eleDocumentSearchResult"), 2,
	 * 0,docName,sValue[2], "a"); else if(sValue[0].equalsIgnoreCase("TWO"))
	 * blnSearchString =
	 * SCRCommon.ActionOnTable(common.o.getObject("eleDocumentFinalizeTable"), 5,
	 * 0,docName,sValue[2], "a");
	 * 
	 * if(blnSearchString) {
	 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
	 * PCThreadCache.getInstance().getProperty("methodName"), "System should search
	 * document in the table cell and click on it. Doc name is '" +docName+
	 * "'","System searched the document '"+docName+"' and selected it", "PASS");
	 * break Outer; } } }else{ logger.info("Document with name '"+docName+" does not
	 * exist in un attqched document Table"); } } if(!blnSearchString){
	 * logger.info(""+docName+" does not exist in document Table");
	 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
	 * PCThreadCache.getInstance().getProperty("methodName"), ""+docName+" should
	 * available in the document search result Table",""+docName+" is not available
	 * in the document search result Table", "FAIL"); }else{ blnStatus = true; } }
	 * catch (Exception e) {
	 * HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"),
	 * PCThreadCache.getInstance().getProperty("methodName"), ""+docName+" should
	 * available in the un attached doc Table",""+docName+" is not available in
	 * theun attached doc Table", "FAIL"); e.printStackTrace(); } return blnStatus;
	 * 
	 * }
	 **/
	public boolean selectTemplateDocs(String value) throws Exception {
		boolean blnStatus = false;
		boolean blnSearchString = false;
		String strPageCount = null;
		String docName = value;

		try {

			blnSearchString = SCRCommon.ActionOnTable(common.o.getObject("eleDocumentSearchResult"), 2, 0, docName, "Select", "a");

			Outer: if (!blnSearchString) {
				int icheckNextPageButton = common.ElementSize(Common.o.getObject("eleNextPage"));
				if (icheckNextPageButton > 0) {
					strPageCount = common.ReadElement(Common.o.getObject("eleCommonPageNumber"), Integer.valueOf

(HTML.properties.getProperty("LONGWAIT")));
					strPageCount = SCRCommon.getIntFromString(strPageCount);
					for (int i = 0; i < Integer.parseInt(strPageCount); i++) {
						blnStatus = common.SafeAction(Common.o.getObject("eleNextPage"), "", "eleNextPage");
						blnStatus = common.SafeAction(Common.o.getObject("eleSearchResultTitle"), "", 

"eleSearchResultTitle");
						blnSearchString = SCRCommon.ActionOnTable(common.o.getObject("eleDocumentSearchResult"), 2, 0, 

docName, "Select", "a");
						if (blnSearchString) {
							HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), 

PCThreadCache.getInstance().getProperty("methodName"), "System should search document in the table cell and click on it. Doc name is '" + docName 

+ "'", "System searched the document '" + docName + "' and selected it", "PASS");
							break Outer;
						}
					}
				} else {
					logger.info("Document with name '" + docName + " does not exist in un attqched document Table");
				}
			}
			if (!blnSearchString) {
				logger.info("" + docName + " does not exist in document Table");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "" + docName + " should available in the document search result Table", "" + docName + " is not available in the document search result Table", "FAIL");
			} else {
				blnStatus = true;
			}
		} catch (Exception e) {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "" + docName + " should available in the un attached doc Table", "" + docName + " is not available in theun attached doc Table", "FAIL");
			e.printStackTrace();
		}
		return blnStatus;

	}

	/**
	 * @function This function use to select the Document Template dynamically
	 * @param value(Template
	 *            Name)
	 * @return status
	 * @throws Exception
	 */
	public boolean UploadSpecifiedFile(String value) throws Exception {
		boolean status = false;
		// String[] sValue = value.split(":::");

		try {
			WebElement ele1 = driver.findElement(Common.o.getObject("eleBrowseToUploadBtn"));
			ele1.click();

			// WebElement ele1 = driver.findElement(By.name("fileContent"));
			// ele1.click();
			Thread.sleep(2000);
			Runtime.getRuntime().exec(value);
			// ele1.sendKeys("C:/Selenium/WorkSpace/test.txt");

			// driver.findElement(Common.o.getObject("eleBrowseToUploadBtn")).click();
			// Thread.sleep(2000);
			// Runtime.getRuntime().exec(value);

		} catch (Exception e) {//
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * @function Use to verify the usecase results
	 * @param sFuncValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean VerifyResults(String sFuncValue) throws Exception {
		boolean status = false;
		String[] sValue = sFuncValue.split(":::");
		logger.info("Verifying the Results");
		String Value = null;
		switch (sValue[0].toUpperCase()) {
		case "VERIFYCCRECIPIENT":
			logger.info(sValue[0]);
			String Value1 = common.ReadElementGetAttribute(Common.o.getObject("lsjUGDCC_DeliveryType"), "value", Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
			String Value2 = common.ReadElementGetAttribute(Common.o.getObject("lsjUGD_DeliveryType"), "value", Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
			status = common.CompareStringResult(sValue[0], Value1, Value2);
			break;

		case "VERIFYTOCRECIPIENT":
			logger.info(sValue[0]);
			Value = common.ReadElementGetAttribute(Common.o.getObject("lstUGD_DeliveryType"), "value", Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			break;

		case "VERIFYTEMPLATE":
			logger.info(sValue[0]);
			Value = common.GetTextFromTable(Common.o.getObject("eleTempTable"), 1, sValue[1]);
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			break;
		case "VERIFYDOCUMENTGENERATED":
			logger.info(sValue[0]);
			Value = common.ReadElement(Common.o.getObject("eleUGDDocType"), Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
			status = common.CompareStringResult(sValue[0], sValue[1], Value);
			// Value = common.ReadElement(Common.o.getObject("eleUGDDocArchivalStatus"),
			// Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
			// status = common.CompareStringResult(sValue[0], sValue[2], Value);
			break;
		}
		if (!status) {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "" + sValue[0] + " case should PASS", "" + sValue[0] + " case should not fail", "FAIL");
			status = true;
		}
		return status;
	}
}