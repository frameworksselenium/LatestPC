package com.pc.screen;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
//import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.pc.constants.PCConstants;
import com.pc.driver.LoggerClass;
import com.pc.elements.Elements;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.PCThreadCache;
import com.pc.utilities.XlsxReader;

import org.w3c.dom.Element;
import org.apache.log4j.Logger;

import com.pc.screen.DownloadLogFile;

public class XMLValidation extends DownloadLogFile {

	public static String sheetname = "XMLValidation";
	Common common = CommonManager.getInstance().getCommon();
	//static Logger logger = Logger.getLogger(sheetname);
	private static org.apache.log4j.Logger loggers;
	private org.apache.log4j.Logger logger  = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName

(),PCThreadCache.getInstance().getProperty("TCID"));
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();
	public boolean blnPolicyinLogFound = false;

	public Boolean SCRXMLValidation() throws Exception {
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);

		if (!status) {
			status = false;
		}
		return status;
	}

	public boolean ExactXMLfromLogs(String funcValue) throws Exception {
		boolean status = false;
		String strsValue = null;
		String strPattern = null;
		String strWholeData = null;
		String xpath_timeStamp = null;
		String strXmlNameSpace = null;
		String strPort1 = HTML.properties.getProperty("WEISS_PORT1");
		String strPort2 = HTML.properties.getProperty("WEISS_PORT2");
		String currentCompoentName = PCThreadCache.getInstance().getProperty("methodName");
		String[] strXmlVal = funcValue.split("##");
		String strLogFilePath1 = PCThreadCache.getInstance().getProperty(PCConstants.FormsFolderPath) + "\\" + PCThreadCache.getInstance

().getProperty(PCConstants.RENAMEDPATH);
		// String
		// strLogFilePath2=PCThreadCache.getInstance().getProperty(PCConstants.FormsFolderPath)+"\\WEISS_Log_"+strPort2+".txt";

		XlsxReader xls = XlsxReader.getInstance();

		String tcID = PCThreadCache.getInstance().getProperty("TCID");
		ArrayList<HashMap<String, Object>> resultListRows = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> whereConstraint = new HashMap<String, Object>();
		whereConstraint.clear();
		whereConstraint.put(PCConstants.ID, tcID);
		whereConstraint.put(PCConstants.Iteration, PCThreadCache.getInstance().getProperty("Iteration"));
		whereConstraint.put("Component", currentCompoentName);
		resultListRows = xls.executeSelectQuery(sheetname, whereConstraint);
		for (HashMap<String, Object> processRow : resultListRows) {
			strPattern = (String) processRow.get("Pattern");
			strWholeData = (String) processRow.get("WholeData");
			xpath_timeStamp = (String) processRow.get("TimeStamp");
			strXmlNameSpace = (String) processRow.get("NameSpace");
		}

		status = ConvetLogtoXML(strLogFilePath1, strXmlVal[0], strXmlVal[1], strXmlVal[2], strPattern, strWholeData, xpath_timeStamp, 

strXmlNameSpace);
		if (!status) // port 25
		{
			status = Select_LogPort(strPort2);
			// strsValue="gwlogs:::PolicyCenter:::logs:::FlexCommLog:::PORT2";
			status = downloadLogfile(strsValue);
			status = ConvetLogtoXML(strLogFilePath1, strXmlVal[0], strXmlVal[1], strXmlVal[2], strPattern, strWholeData, 

xpath_timeStamp, strXmlNameSpace);
			System.out.print("Extract XML from Logs - Port 25");
		}
		//
		HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "Xml should be Extracted from downloaded Log file", "Xml Extracted Successfully", "PASS");
		return status;

	}

	public boolean ConvetLogtoXML(String FileName, String PolicyNumber, String RequestType, String ServiceName, String strPattern, String 

strWholeData, String strXpath_timeStamp, String strXmlNameSpace) throws SAXException, IOException, ParserConfigurationException, 

XPathExpressionException {

		BufferedReader reader = new BufferedReader(new FileReader(FileName));
		System.out.println(reader.toString());
		String outFileName = PCThreadCache.getInstance().getProperty(PCConstants.FormsFolderPath) + "\\" + PolicyNumber + "_" + 

ServiceName + "_" + RequestType + "_Output.xml";
		File output = new File(outFileName);
		BufferedWriter writer = new BufferedWriter(new FileWriter(output));

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();

		String line = null;
		StringBuilder stringBuilder = new StringBuilder();
		String ls = System.getProperty("line.separator");
		String pattern = null;
		String strPolicyXpath = null;
		String strTemp = null;
		String xpath_timeStamp = null;
		String xpath_WholeData = null;
		String XmlNameSpace = null;
		Element userElement = null;
		String strNameSpace = null;
		Element userElement_match = null;
		String strNameSpace_Match = null;
		String strNameAttr = null;
		Boolean status;

		List<String> arrTimeStamp = new ArrayList<String>();

		try {
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line.trim());
				stringBuilder.append(ls);
			}
			switch (ServiceName.toUpperCase()) {
			case "FLEXCOMMLOG":
				pattern = strPattern;
				// xpath_timeStamp =
				// "/GetCommissionRates//policyNumber[text()='"+PolicyNumber+"']/parent::GetCommissionRates//requestTime";
				xpath_timeStamp = strXpath_timeStamp;
				xpath_WholeData = strWholeData;
				if (RequestType.equalsIgnoreCase("RESPONSE")) {
					// pattern = "(<\\?xml version)(.*?)(</tns:GetCommissionRates>)";
					pattern = strPattern;
					XmlNameSpace = strXmlNameSpace;
					strNameAttr = "xmlns:ns0";
				} else {
					pattern = strPattern;
					XmlNameSpace = strXmlNameSpace;
					strNameAttr = "xmlns:tns";
				}
				break;
			case "ADDRESSVERIFICATION":
				if (RequestType.equalsIgnoreCase("REQUEST")) {
					pattern = strPattern;
					xpath_WholeData = strWholeData;
				} else if (RequestType.equalsIgnoreCase("RESPONSE")) {
					pattern = strPattern;
					xpath_WholeData = strWholeData;
				}
				break;

			case "OTHERS":
				break;

			}
			Pattern p = Pattern.compile(pattern, Pattern.DOTALL);
			Matcher m = p.matcher(stringBuilder);
			int intloop = 0;
			while (m.find()) {
				try {
					Document document = builder.parse(new InputSource(new StringReader(stringBuilder.substring(m.start(), 

m.end()))));
					String xmlData = (xpath.evaluate(xpath_WholeData, document.getDocumentElement())).trim();
					switch (ServiceName.toUpperCase()) {
					case "FLEXCOMMLOG":
						String xmlData_timeStamp = (xpath.evaluate(xpath_timeStamp, document.getDocumentElement())).trim

();
						userElement = (Element) xpath.evaluate(xpath_WholeData, document, XPathConstants.NODE);
						strNameSpace = userElement.getAttribute(strNameAttr);
						System.out.println(userElement.getAttribute(strNameAttr));
						System.out.println(xmlData_timeStamp);
						System.out.println(xpath.evaluate("/GetCommissionRates//requestTime/text()", 

document.getDocumentElement()));
						if (null != xmlData && xmlData.contains(PolicyNumber) && strNameSpace.contains(strXmlNameSpace)) 

{

							arrTimeStamp.add(xmlData_timeStamp);
							intloop++;

						}
						break;
					case "ADDRESSVERIFICATION":
						// System.out.println("xmlData");
						// System.out.println("*************");
						// System.out.println(" Policy number "+ xpath.evaluate("//CommercialName",
						// document.getDocumentElement()));
						if (null != xmlData && xmlData.contains(PolicyNumber)) {
							System.out.println("Writing into xml file");
							writer.write(stringBuilder.substring(m.start(), m.end()));
							writer.write(ls);
							writer.write(ls);
							writer.flush();
							blnPolicyinLogFound = true;
						}
						break;
					}

				} catch (SAXException e) {
					System.out.println("exception -*" + e.getMessage());
				}
			}
			if (!arrTimeStamp.isEmpty() && ServiceName.equalsIgnoreCase("FlexCommLog")) {
				strTemp = arrTimeStamp.get(0).toString();
				for (int i = 1; i < arrTimeStamp.size(); i++) {
					strTemp = ComparetiemStamp(strTemp, arrTimeStamp.get(i).toString());
				}
				try {
					Matcher m1 = p.matcher(stringBuilder);
					while (m1.find()) {
						Document document1 = builder.parse(new InputSource(new StringReader(stringBuilder.substring

(m1.start(), m1.end()))));
						String xmlData1 = (xpath.evaluate(xpath_WholeData, document1.getDocumentElement())).trim();
						System.out.println(xmlData1);
						userElement_match = (Element) xpath.evaluate(xpath_WholeData, document1, XPathConstants.NODE);
						strNameSpace_Match = userElement_match.getAttribute(strNameAttr);
						if (null != xmlData1 && xmlData1.contains(PolicyNumber) && xmlData1.contains(strTemp) && 

strNameSpace_Match.contains(strXmlNameSpace)) {

							writer.write(stringBuilder.substring(m1.start(), m1.end()));
							writer.write(ls);
							writer.write(ls);
							writer.flush();
							blnPolicyinLogFound = true;
						}

					}
				} catch (SAXException e) {
					System.out.println("2nd while exception -*" + e.getMessage());
				}
			}
		} finally {
			reader.close();
			writer.close();
		}
		return blnPolicyinLogFound;

	}

	public String ComparetiemStamp(String time1, String time2) {
		// String text = "2018-01-09 02:27:24.000334";
		Timestamp ts = Timestamp.valueOf(time1);
		// String text1 = "2018-01-09 02:27:06.000055";
		Timestamp ts1 = Timestamp.valueOf(time2);
		System.out.println(ts1);
		String latesttime;
		System.out.println("Comparing timestamp" + time1 + "___" + time2);
		if (ts.after(ts1)) {
			System.out.println("ts" + ts + " is latest");
			latesttime = time1;
		} else {
			System.out.println("ts1" + ts1 + " is latest");
			latesttime = time2;
		}
		return latesttime;
	}

	/**
	 * 
	 */
	/**
	 * @function This function is to fill the NGS Coverage from the coverage sheet
	 * @return
	 */
	public Boolean ReadXMLDataFromSheet(String sheetName) {
		Boolean status = false;
		try {
			status = SCRCommon.xmlValidation(sheetName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}

	public static boolean PCXMLCompare(String sPCValue, String sDBValue, String CompareFormat, String sPCFieldName, String sDBAliasName) 

throws Exception {
		// TODO Auto-generated method stub
		boolean Status = false;
		String sConvertedFormatPC = null;
		String sConvertedFormatDB = null;
		String splitValue[] = null;
		switch (CompareFormat.toUpperCase()) {
		case "TEXT":
			Status = CompareResults(sPCValue, sDBValue, sPCFieldName, sDBAliasName);
			break;
		case "REMOVESPACE":
			Status = RemoveSpaceCompareResults(sPCValue, sDBValue, sPCFieldName, sDBAliasName);
			break;
		case "SEGMENT":
			switch (sDBValue) {
			case "SC":
				sDBValue = "Small Commercial";
				break;
			case "captiveService":
				sDBValue = "Captive Services";
				break;
			case "keyAccount":
				sDBValue = "Key Account";
				break;
			case "selectCustomer":
				sDBValue = "Select Customer";
				break;
			case "commercialProgramSmall":
				sDBValue = "Commercial Program Small";
				break;
			case "commercialProgramMedium":
				sDBValue = "Commercial Program Medium";
				break;
			}
			Status = CompareResults(sPCValue, sDBValue, sPCFieldName, sDBAliasName);
			break;
		case "SPACECONTAINS":
			sPCValue = sPCValue.replaceAll(" ", "");
			Status = CompareResults(sPCValue, sDBValue, sPCFieldName, sDBAliasName);
			break;
		case "DATETIME":
			try {
				SimpleDateFormat defFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				Date date = defFormat.parse(sDBValue);
				SimpleDateFormat sdfDestination = new SimpleDateFormat("MM/dd/yyyy");
				sConvertedFormatDB = sdfDestination.format(date);
				SimpleDateFormat defFormat1 = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
				Date date1 = defFormat1.parse(sPCValue);
				SimpleDateFormat sdfDestination1 = new SimpleDateFormat("MM/dd/yyyy");
				sConvertedFormatPC = sdfDestination1.format(date1);
				Status = CompareResults(sConvertedFormatPC, sConvertedFormatDB, sPCFieldName, sDBAliasName);
			} catch (ParseException exp) {
				exp.printStackTrace();
			}
			break;
		case "DATE":
			try {
				SimpleDateFormat defFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date date = defFormat.parse(sDBValue);
				SimpleDateFormat sdfDestination = new SimpleDateFormat("MM/dd/yyyy");
				sConvertedFormatDB = sdfDestination.format(date);
				Status = CompareResults(sPCValue, sConvertedFormatDB, sPCFieldName, sDBAliasName);
			} catch (ParseException exp) {
				exp.printStackTrace();
			}
			break;
		case "DATE1":
			try {
				SimpleDateFormat defFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				SimpleDateFormat defFormat1 = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
				Date date = defFormat.parse(sDBValue);
				Date date1 = defFormat1.parse(sPCValue);
				SimpleDateFormat sdfDestination = new SimpleDateFormat("MM/dd/yyyy");
				sConvertedFormatDB = sdfDestination.format(date);
				sConvertedFormatPC = sdfDestination.format(date1);
				Status = CompareResults(sConvertedFormatPC, sConvertedFormatDB, sPCFieldName, sDBAliasName);
			} catch (ParseException exp) {
				exp.printStackTrace();
			}
			break;

		case "TIMESTAMP":
			Status = true;
			break;
		case "DATETIMEAMPM":
			try {
				SimpleDateFormat defFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				Date date = defFormat.parse(sDBValue);
				SimpleDateFormat sdfDestination = new SimpleDateFormat("MM/dd/yyyy");
				sConvertedFormatDB = sdfDestination.format(date);

				SimpleDateFormat defFormat1 = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
				Date date1 = defFormat1.parse(sPCValue);
				SimpleDateFormat sdfDestination1 = new SimpleDateFormat("MM/dd/yyyy");
				sConvertedFormatPC = sdfDestination1.format(date1);
				Status = CompareResults(sConvertedFormatPC, sConvertedFormatDB, sPCFieldName, sDBAliasName);
			} catch (ParseException exp) {
				exp.printStackTrace();
			}
			break;
		case "STATE":
			if (sDBValue == null && sPCValue == null) {
				Status = true;
			}
			switch (sDBValue) {
			case "CT":
				sConvertedFormatDB = "Connecticut";
				break;
			case "OH":
				sConvertedFormatDB = "Ohio";
				break;
			case "US":
				sConvertedFormatDB = "United States";
				break;
			case "CA":
				sConvertedFormatDB = "California";
				break;
			case "TX":
				sConvertedFormatDB = "Texas";
				break;
			case "AR":
				sConvertedFormatDB = "Arkansas";
				break;
			case "AZ":
				sConvertedFormatDB = "Arizona";
				break;
			case "MN":
				sConvertedFormatDB = "Minnesota";
				break;
			case "WI":
				sConvertedFormatDB = "Wisconsin";
				break;
			case "MD":
				sConvertedFormatDB = "Maryland";
				break;
			case "KY":
				sConvertedFormatDB = "Kentucky";
				break;
			case "NE":
				sConvertedFormatDB = "Nebraska";
				break;
			case "SC":
				sConvertedFormatDB = "South Carolina";
				break;
			case "AL":
				sConvertedFormatDB = "Alabama";
				break;
			case "CO":
				sConvertedFormatDB = "Colorado";
				break;
			case "FL":
				sConvertedFormatDB = "Florida";
				break;
			case "NH":
				sConvertedFormatDB = "New Hampshire";
				break;

			}
			Status = CompareResults(sPCValue, sConvertedFormatDB, sPCFieldName, sDBAliasName);
			break;
		case "HYPHEN":
			sConvertedFormatPC = sPCValue.replaceAll("[\\s\\-()]", "");
			Status = CompareResults(sConvertedFormatPC, sDBValue, sPCFieldName, sDBAliasName);
			break;
		/*
		 * case "ADDSPACEBRACKET": sConvertedFormatPC = sPCValue.replaceAll("-","");
		 * sConvertedFormatDB = sDBValue.replaceAll("-",""); Status =
		 * CompareResults(sConvertedFormatPC, sConvertedFormatDB, sPCFieldName,
		 * sDBAliasName); break;
		 */
		case "MAPPCODS":
			if (sDBValue == null && sPCValue == null) {
				Status = true;
			}
			switch (sDBValue) {
			case "WC7WorkersComp":
				sConvertedFormatDB = "Workers Compensation";
				break;
			case "HIGCOMPROP":
				sConvertedFormatDB = "Commercial Property";
				break;
			case "relNonRenewal":
				sConvertedFormatDB = "Release Non Renewal";
				break;
			case "PSRenewal_Ext":
				sConvertedFormatDB = "Renewal";
				break;
			case "custCareTeam":
				sConvertedFormatDB = "Customer Care Team";
				break;
			case "NonRenewing":
				sConvertedFormatDB = "Non-renewing";
				break;
			case "NotTaking":
				sConvertedFormatDB = "Not Taking";
				break;
			case "appetiteChange_Ext":
				sConvertedFormatDB = "Non-Renew - Appetite Change";
				break;
			case "userview":
				sConvertedFormatDB = "View user";
				break;
			case "FinalAudit":
				sConvertedFormatDB = "Final Audit";
				break;
			case "Administrative Fund Assessment Surcharge":
				sConvertedFormatDB = "VT administrative fund Surcharge";
				break;
			case "Debt Reduction Surcharge":
				sConvertedFormatDB = "WV Debt Reduction Surcharge";
				break;
			}
			Status = CompareResults(sPCValue, sConvertedFormatDB, sPCFieldName, sDBAliasName);
			break;
		case "Contains":
			Status = CompareResults(sPCValue, sDBValue, sPCFieldName, sDBAliasName);
			break;
		case "YesNo":
			if (sPCValue.equalsIgnoreCase("Yes"))
				sConvertedFormatPC = "Y";
			else if (sPCValue.equalsIgnoreCase("No"))
				sConvertedFormatPC = "N";
			Status = CompareResults(sConvertedFormatPC, sDBValue, sPCFieldName, sDBAliasName);
			break;
		case "SpaceHyphen":
			sPCValue = sPCValue.replaceAll(" ", "");
			sPCValue = sPCValue.replaceAll("-", "");
			Status = CompareResults(sPCValue, sDBValue, sPCFieldName, sDBAliasName);
			break;
		case "ZIPCODE":
			// strExcelPCFieldValue1= strExcelPCFieldValue.replaceAll(".","");
			splitValue = sDBValue.split("-");
			sDBValue = splitValue[0];
			System.out.println("SplitValue of " + sDBValue + "");
			Status = CompareResults(sPCValue, sDBValue, sPCFieldName, sDBAliasName);
			break;
		case "PRODUCERCODE":
			sPCValue = sPCValue.substring(0, 2);
			Status = CompareResults(sPCValue, sDBValue, sPCFieldName, sDBAliasName);
			break;
		case "COMPANYCODE":
			splitValue = sPCValue.split("Company Code ");
			sPCValue = splitValue[1].trim();
			Status = CompareResults(sPCValue, sDBValue, sPCFieldName, sDBAliasName);
			break;
		case "SUBMISSIONCODE":
			switch (sDBValue) {
			case "eMail":
				sDBValue = "E-mail";
				break;
			}
			Status = CompareResults(sPCValue, sDBValue, sPCFieldName, sDBAliasName);
			break;
		case "DOLLAR":
			sDBValue = "$" + sDBValue + "0";
			Status = CompareResults(sPCValue, sDBValue, sPCFieldName, sDBAliasName);
			break;
		case "DECIMAL":
			String value = "00";
			sDBValue = sDBValue + value;
			Status = CompareResults(sPCValue, sDBValue, sPCFieldName, sDBAliasName);
			break;
		case "CITY":
			switch (sDBValue) {
			case "NORTH LITTLE ROCK":
				sDBValue = "NORTH LITTLE RO";
				break;
			}
			Status = CompareResults(sPCValue, sDBValue, sPCFieldName, sDBAliasName);
		}
		if (!Status) {
			Status = true;
		}
		return Status;
	}

	public static boolean CompareResults(String sPCValue, String sDBValue, String sPCFieldName, String sDBAliasName) throws IOException {
		Boolean Status = false;
		sDBValue.trim();
		sPCValue.trim();
		if (sPCValue.toUpperCase().equals(sDBValue.toUpperCase()) || sDBValue.toUpperCase().equals(sPCValue.toUpperCase())) {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "PC " + sPCFieldName + " Value('" + sPCValue + "') should match with XML " + sDBAliasName + " Value('" + sDBValue + "')", "PC " + 

sPCFieldName + " Value('" + sPCValue + "') is matching with XML " + sDBAliasName + " Value('" + sDBValue + "')", "PASS");
			Status = true;
		} else {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "PC " + sPCFieldName + " Value('" + sPCValue + "') should match with XML " + sDBAliasName + " Value('" + sDBValue + "')", "PC " + 

sPCFieldName + " Value('" + sPCValue + "') is not matching with XML " + sDBAliasName + " Value('" + sDBValue + "')", "FAIL");
		}
		return Status;
	}

	public static boolean RemoveSpaceCompareResults(String sPCValue, String sDBValue, String sPCFieldName, String sDBAliasName) throws 

IOException {
		Boolean Status = false;
		sPCValue = sPCValue.replaceAll("//s+", "");
		sDBValue = sDBValue.replaceAll("//s+", "");
		if (sPCValue.toUpperCase().equals(sDBValue.toUpperCase()) || sDBValue.toUpperCase().equals(sPCValue.toUpperCase())) {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "PC " + sPCFieldName + " Value('" + sPCValue + "') should match with XML " + sDBAliasName + " Value('" + sDBValue + "')", "PC " + 

sPCFieldName + " Value('" + sPCValue + "') is matching with XML " + sDBAliasName + " Value('" + sDBValue + "')", "PASS");
			Status = true;
		} else {
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty

("methodName"), "PC " + sPCFieldName + " Value('" + sPCValue + "') should match with XML " + sDBAliasName + " Value('" + sDBValue + "')", "PC " + 

sPCFieldName + " Value('" + sPCValue + "') is not matching with XML " + sDBAliasName + " Value('" + sDBValue + "')", "FAIL");
		}
		return Status;
	}

	public Boolean trimPolicyNumber(String strFuncValue) {
		Boolean blnStatus = false;
		HashMap<String, Object> updateColumnNameValues = new HashMap<String, Object>();
		HashMap<String, Object> whereConstraint = new HashMap<String, Object>();
		XlsxReader sXL = XlsxReader.getInstance();
		String policyNum = strFuncValue.substring(strFuncValue.length() - 6);
		whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
		updateColumnNameValues.put("TrmPolicyNumber", policyNum);
		PCThreadCache.getInstance().setProperty("TrmPolicyNumber", policyNum);
		/*blnStatus = sXL.executeUpdateQuery(PCConstants.SHEET_XMLVALIDATION, updateColumnNameValues, whereConstraint);
		blnStatus = sXL.executeUpdateQuery(PCConstants.SHEET_FLEXCOMMLOG, updateColumnNameValues, whereConstraint);*/
		return blnStatus;
	}
}