/**
 * @ClassPurpose Access all the data's for NGS Qualification Page
 * @Scriptor Kumar
 * @ReviewedBy
 * @ModifiedBy Sojan
 * @LastDateModified 3/21/2017
 */
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
import com.pc.utilities.XlsxReader;

public class BusinessOwnersLine {

	public static String sheetname = "BusinessOwnersLine";
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();

	public Boolean SCRBusinessOwnersLine() throws Exception {
		Boolean status = false;
		status = common.ClassComponent(sheetname, Common.o);
		if (!status) {
			status = false;
		}
		return status;
	}

	public Boolean searhSelect1(String sFuncValue) {
		boolean status = false;
		String[] sValue = sFuncValue.split(":::");
		By sProperties = By.id

("SubmissionWizard:LOBWizardStepGroup:LineWizardStepSet:BP7LineScreen:BP7LinePanelSet:BP7LineCoveragesPanelSet:MandatoryCoverages:BP7CoveragesDV_ref");
		WebElement abc = common.returnObject(sProperties);

		List<WebElement> abcd = abc.findElements(By.tagName("div"));

		for (WebElement sText : abcd) {
			String sElementText = sText.getText();
			System.out.println(sElementText);
			if (sElementText.equals(sValue[0])) {
				String sID = sText.getAttribute("id");
				String[] sSplit = sID.split("-");
				By sTargetID = By.id(sSplit[0]);
				WebElement sTargetLegend = common.returnObject(sTargetID);
				List<WebElement> sTargetLegendDiv = sTargetLegend.findElements(By.tagName("label"));
				for (WebElement sLabel : sTargetLegendDiv) {
					String sLabelValue = sLabel.getText();
					if (sLabelValue.equals(sValue[1])) {
						String sLabelID = sLabel.getAttribute("id");
						String[] sLabelIDSplit = sLabelID.split("-");
						String sLabelConcatListID = sLabelIDSplit[0].concat("-inputEl");
						// WebElement sElement = common.returnObject(By.id(sID1));
						try {
							status = common.SafeAction(By.id(sLabelConcatListID), sValue[2], "lst");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					if (status == true) {
						break;
					}
				}
			}
			if (status == true) {
				break;
			}
		}
		return status;
	}

	public Boolean searhSelect(String sFuncValue) {
		boolean status = false;
		String[] sValue = sFuncValue.split(":::");
		List<WebElement> abcd = driver.findElements(By.xpath(".//div[@class='x-component x-fieldset-header-text x-component-default']"));
		for (WebElement sText : abcd) {
			String sElementText = sText.getText();
			if (sElementText.equals(sValue[0])) {
				String sID = sText.getAttribute("id");
				String[] sSplit = sID.split("-");
				By sTargetID = By.id(sSplit[0]);
				WebElement sTargetLegend = common.returnObject(sTargetID);
				List<WebElement> sTargetLegendDiv = sTargetLegend.findElements(By.tagName("label"));
				for (WebElement sLabel : sTargetLegendDiv) {
					String sLabelValue = sLabel.getText();
					if (sLabelValue.equals(sValue[1])) {
						String sLabelID = sLabel.getAttribute("id");
						String[] sLabelIDSplit = sLabelID.split("-");
						String sLabelConcatListID = sLabelIDSplit[0].concat("-inputEl");
						try {
							status = common.SafeAction(By.id(sLabelConcatListID), sValue[2], "lst");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					if (status == true) {
						break;
					}
				}
			}
			if (status == true) {
				break;
			}
		}
		return status;
	}

	/**
	 * @function This function is to fill the NGS Coverage from the coverage sheet
	 * @return
	 */
	public Boolean fillCoverages(String sheetName) {
		Boolean status = false;
		try {
			status = SCRCommon.coverageSheet(sheetName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * @function This funciton is to fill the NGS Coverage from the coverage sheet
	 * @return
	 */
	public Boolean addCoverage(String sheetName) {
		Boolean status = false;
		try {
			status = SCRCommon.addCoverages(sheetName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * @function This function is to fill the NGS Coverage from the coverage sheet
	 * @return
	 */
	public Boolean CoverageValidation(String sheetName) // CoverageValidation
	{
		Boolean status = false;
		try {
			status = SCRCommon.coverageValidation(sheetName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * function used to fill the Umbrella section details
	 * 
	 * @param sFuncValue
	 * @return boolean
	 * @throws Exception
	 */
	public Boolean FillUmbrellaDetail(String sFuncValue) throws Exception {
		Boolean status = false;

		logger.info("Entering into Add NEW UmbrellaDetail Function");
		XlsxReader sXL = XlsxReader.getInstance();// new XlsxReader( PCThreadCache.getInstance().getProperty("DataSheetName)
		String TCID = null;
		String TCIteration = null;
		// Common common = CommonManager.getInstance().getCommon();
		Boolean tcAvailable = false;
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
			logger.info("" + TCID + " is not available in the LocationData Sheet");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "Validate the Testcase availability in datasheet - " + sFuncValue, "Testcass not available in Datasheet", "FAIL");

		} else if (!status) {
			logger.info("" + TCID + " is not available in the LocationData Sheet");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "Validate the Testcase availability in datasheet - " + sFuncValue, "Testcass not available in Datasheet", "FAIL");
		}
		return status;
	}

	/**
	 * @function This function is to fill the NGS Coverage from the coverage sheet
	 * @return
	 */
	public Boolean FLSValidation(String sheetName) // CoverageValidation
	{
		Boolean status = false;
		try {
			status = SCRCommon.flsValidation(sheetName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}
	
//	Method to add default effective date
	public boolean DefaultEffectiveDate(String strFuncValue) throws Exception {
		boolean status = false;
		String[] strSplitValue = strFuncValue.split(":::");
		String strDate = null;
		switch (strSplitValue[0].toUpperCase()) {
		case "CURRENTDATE":
			strDate = SCRCommon.ReturnCurrentDate();
			status = common.SafeAction(Common.o.getObject("edtRetroactiveDate"), strDate, "edt");
			break;
		case "INCREASEDECREASEDATE":
			strDate = SCRCommon.returnDate(Integer.parseInt(strSplitValue[1]));
			status = common.SafeAction(Common.o.getObject("edtRetroactiveDate"), strDate, "edt");
			break;
		}
		return status;
	}

}