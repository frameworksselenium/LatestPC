
/**
 * @ClassPurpose This Class used for the ResearchUnattachedDocument usecase
 * @Scriptor Rajeshwari
 * @ReviewedBy
 * @ModifiedBy
 * @LastDateModified 3/22/2017
 */
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

public class ResearchUnattachedDocument {

	public static String sheetname = "ResearchUnattachedDocument";
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
	public Boolean SCRResearchUnattachedDocument() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);

		if (!status) {
			status = false;
		}
		return status;
	}

	/**
	 * @function used to select the specific xml doc form the table
	 * @param xmlDocName
	 * @return
	 * @throws Exception
	 */
	public boolean SelectDocument(String xmlDocName) throws Exception {
		boolean status = false;
		status = common.ActionOnTable(common.o.getObject("eleUnattachedDocTable"), 0, 0, xmlDocName, "a");
		status = common.ElementExistOrNotTrue("DocumentDetails", "Verify document details page is getting displayed", "Document details is getting displayed ", common.o.getObject("eleDocumentDtlsTitle"));
		return status;

	}

	/**
	 * @function method used to do validation based on condition passed.
	 * @param funValue
	 * @return ture/false
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
		}
		return status;
	}
}