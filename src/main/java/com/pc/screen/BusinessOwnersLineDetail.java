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
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.PCThreadCache;

public class BusinessOwnersLineDetail {

	public static String sheetname = "BusinessOwnersLineDetail";
	//static Logger logger = Logger.getLogger(sheetname);
	//private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(),PCThreadCache.getInstance().getProperty("TCID"));
	Common common = CommonManager.getInstance().getCommon();
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();

	public Boolean SCRBusinessOwnersLineDetail() throws Exception {
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
		By sProperties = By.id("SubmissionWizard:LOBWizardStepGroup:LineWizardStepSet:BP7LineScreen:BP7LinePanelSet:BP7LineCoveragesPanelSet:MandatoryCoverages:BP7CoveragesDV_ref");
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
	 * @function This funciton is to fill the NGS Coverage from the coverage sheet
	 * @return
	 */
	public Boolean coverages(String sheetName) {
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
	 * @function This function is to fill the NGS Coverage from the coverage sheet
	 * @return
	 */
	public Boolean addCoverage(String sheetName) {
		Boolean status = false;
		try {
			status = SCRCommon.coverageSheet(sheetName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}
}