package com.pc.driver;

import java.util.ArrayList;
import java.util.List;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;
import org.testng.xml.XmlSuite.ParallelMode;

public class TestNGRunner {

	public static void main(String args[]) {

		System.out.println("test........");
		XmlSuite suite = new XmlSuite();

		suite.setName("TestAll");
		suite.setThreadCount(1);
		suite.setParallel(ParallelMode.METHODS);

		XmlTest test = new XmlTest(suite);
		test.setName("PC");
		test.addParameter("DataSheetName", "Data");
		test.addParameter("Region", "");
		List<XmlClass> classes = new ArrayList<XmlClass>();
		classes.add(new XmlClass("com.pc.driver.ParallelExecDriver"));
		test.setXmlClasses(classes);

		List<XmlSuite> suites = new ArrayList<XmlSuite>();
		suites.add(suite);
		TestNG tng = new TestNG();
		tng.setXmlSuites(suites);
		tng.run();

	}
}
