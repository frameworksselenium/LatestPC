/**
 * @ClassPurpose This Class used for the UnattachedDocument usecase
 * @Scriptor Rajeshwari
 * @ReviewedBy
 * @ModifiedBy
 * @LastDateModified 3/22/2017
 */
package com.pc.screen;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.pc.driver.LoggerClass;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.PCThreadCache;

public class UnattachedDocument {

	public static String sheetname = "UnattachedDocument";
	Common common = CommonManager.getInstance().getCommon();
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));

	/**
	 * @function Use to perform all the objects/elements/functions from the
	 *           ResearchUnattachedDocument sheet
	 * @return true/false
	 * @throws Exception
	 */
	public Boolean SCRUnattachedDocument() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);

		if (!status) {
			status = false;
		}
		return status;
	}

	/**
	 * @function used to select the specific xml doc from the table
	 * @param xmlDocName
	 * @return true/false
	 * @throws Exception
	 */
	public boolean SelectDocument(String docName) throws Exception {
		boolean blnStatus = false;
		boolean blnSearchString = false;
		String strPageCount;
		try {

			blnSearchString = SCRCommon.ActionOnTable(common.o.getObject("eleUnattachedDocTable"), 0, 0, docName, "a");
			if (blnSearchString) {
				blnStatus = true;
			}
			Outer:

			if (!blnSearchString) {
				int icheckNextPageButton = common.ElementSize(Common.o.getObject("eleNextPage"));
				if (icheckNextPageButton > 0) {
					strPageCount = common.ReadElement(Common.o.getObject("eleCommonPageNumber"), Integer.valueOf(HTML.properties.getProperty("LONGWAIT")));
					strPageCount = SCRCommon.getIntFromString(strPageCount);
					for (int i = 0; i < Integer.parseInt(strPageCount); i++) {
						blnStatus = common.SafeAction(Common.o.getObject("eleNextPage"), "", "eleNextPage");
						blnStatus = common.SafeAction(Common.o.getObject("eleUnattachDocScreenTitle"), "", "eleUnattachDocScreenTitle");
						blnSearchString = SCRCommon.ActionOnTable(common.o.getObject("eleUnattachedDocTable"), 0, 0, docName, "a");
						if (blnSearchString) {
							HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), 

PCThreadCache.getInstance().getProperty("methodName"), "System should search object in the table cell and click on object. Object name is '" + 

docName + "'", "System searched object in the table and clicked on object. object name is '" + docName + "'", "PASS");
							break Outer;
						}
					}
				} else {
					logger.info("Document with anme '" + docName + " does not exist in un attqched document Table");
				}
			}
			if (!blnSearchString) {
				logger.info("" + docName + " does not exist in un attqched document Table");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance

().getProperty("methodName"), "" + docName + " should available in the un attached doc Table", "" + docName + " is not available in theun attached doc Table", "FAIL");
			} else {
				blnStatus = true;
			}
		} catch (Exception e) {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "" + docName + " should available in the un attached doc Table", "" + docName + " is not available in theun attached doc Table", 

"FAIL");
			e.printStackTrace();
		}
		return blnStatus;

	}

	/**
	 * @function used to verify the Testcase results
	 * @param funValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean VerifyResults(String funValue) throws Exception {
		boolean status = true;
		String[] sValue = funValue.split(":::");
		String wait = HTML.properties.getProperty("SHORTWAIT");
		Integer x = Integer.valueOf(wait);
		String ActValue;
		switch (sValue[0].toUpperCase()) {

		case "VERIFYALERTCANCEL":
			logger.info("Validating the '" + sValue[0] + "'");

			// handled for Email adn fax field
			String[] arrValues = sValue[1].split("->");
			ActValue = common.ReadElementFromListEditBox(Common.o.getObject(arrValues[0]), x);
			status = common.CompareStringResult(sValue[3], arrValues[1], ActValue);

			ActValue = common.ReadElementFromListEditBox(Common.o.getObject("edtEmailOrFaxText"), x);
			status = common.CompareStringResult("Email/Fax Text validation", sValue[2], ActValue);

			ActValue = common.ReadElementFromListEditBox(Common.o.getObject("lstDeliveryMethod"), x);
			status = common.CompareStringResult("Delivery Type validation", sValue[3], ActValue);
			break;

		case "VERIFYALERTOK":
			logger.info("Validating the '" + sValue[0] + "'");
			status = common.ElementExistOrNotTrue("Document Detail", "verify whether the Document details screen is displayed after cliking on Ok in Alert Popup", "Document details screen is displayed after cliking on Ok in Alert Popup", Common.o.getObject(sValue[1]));

		case "VERIFYERRMSG":
			ActValue = common.ReadElement(Common.o.getObject("eleCommonZeroResults"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult(sValue[0], sValue[1], ActValue);
			break;
		case "VERIFYCFMMSG":
			ActValue = common.ReadElement(Common.o.getObject("eleUnAttachedMessage"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult(sValue[0], sValue[1], ActValue);
			break;
		case "VERIFYEDITABLE":
			ActValue = common.ReadElementGetAttribute((Common.o.getObject("lstMissingInfoUnattached")), "value", Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			status = common.CompareStringResult(sValue[0], sValue[1], ActValue);
			break;
		}
		return status;
	}

	public boolean selectDocIntakeTransType1(String strVal) throws Exception {
		boolean status = false;

		status = SCRCommon.DataWebTable(common.o.getObject("eleDocIntakeTable"), 1, 1, "", "Single", "");
		status = common.SafeAction(common.o.getObject("lstIntakeTransType"), strVal, "lstIntakeTransType");

		return status;
	}

	public static boolean selectDocIntakeTransType(String strReadString) throws Exception {
		Common common = CommonManager.getInstance().getCommon();
		boolean status = false;
		WebElement mytable = common.returnObject(common.o.getObject("eleDocIntakeTable"));
		List<WebElement> rows_table = mytable.findElements(By.tagName("tr"));
		List<WebElement> Columns_row = rows_table.get(0).findElements(By.tagName("td"));
		List<WebElement> CellElements = Columns_row.get(1).findElements(By.tagName("div"));
		for (WebElement element : CellElements) {
			element.click();
			status = common.SafeAction(common.o.getObject("lstIntakeTransType"), strReadString, "lstIntakeTransType");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search and click on document '" + strReadString + "' from document table", " document'" + strReadString + "' is available and clicked from the document table", "PASS");
			break;
		}
		if (!status) {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should document '" + strReadString + "' from document section", "Searched document'" + strReadString + "' not available in the document table", "FAIL");
		}
		return status;
	}
}