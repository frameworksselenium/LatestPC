/**
 * @ClassPurpose This Class is to run the test in the remotely in the child machines
 * @Scriptor Banu Pradeep
 * @ReviewedBy
 * @ModifiedBy 
 * @LastDateModified 3/17/2017
 */
package com.pc.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Collections;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class RemoteDriverFactory {
	static Logger log = Logger.getLogger(RemoteDriverFactory.class);

	private static RemoteDriverFactory instance = new RemoteDriverFactory();
	public String hostname;

	public static RemoteDriverFactory getInstance() {
		return instance;
	}

	/**
	 * @function use to create the driver for the client machines
	 * @return WebDriver
	 * @throws MalformedURLException
	 */
	public WebDriver createNewDriver() {
		System.out.println("It is in createnewdriver:::");
		RemoteWebDriver driver = null;
		String execution = HTML.properties.getProperty("TypeOfAutomation");
		String browser = HTML.properties.getProperty("Browser");
		DesiredCapabilities cap = null;
		if (execution.toUpperCase().equalsIgnoreCase("HEADLESS")) {
			log.info("Webdriver Headless Automation started");
			/*
			 * final String phantomJsFilePath = driverPath + "\\phantomjs.exe"; service =
			 * new PhantomJSDriverService.Builder().usingPhantomJSExecutable(new
			 * File(phantomJsFilePath)).usingAnyFreePort() .build(); try{ service.start(); }
			 * catch (IOException e1) {
			 * 
			 * e1.printStackTrace(); } log.info("Headless phantomjs service started");
			 */
			if (browser.toUpperCase().contains("CH")) {

				cap = DesiredCapabilities.chrome();
			}
			if (browser.toUpperCase().contains("FF")) {
				cap = DesiredCapabilities.firefox();
			}
			if (browser.toUpperCase().contains("IE")) {
				cap = DesiredCapabilities.internetExplorer();
			}
			try {
				driver = new RemoteWebDriver(new URL(ManagerPhantomJS.getInstance().getPhantomJSDriverService().getUrl().toString()), cap);
				try {
					hostname = getHostName(driver.getSessionId());
					log.info("Thread ID = " + Thread.currentThread().getId() + " and Machine IP Address = " + hostname + "");
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			log.info("Headless '" + browser.toUpperCase() + "' DesiredCapabilities initiated");
			log.info("Headless '" + browser.toUpperCase() + "' driver started");
		}
		if (execution.toUpperCase().equalsIgnoreCase("HEAD")) {
			log.info("Webdriver Grid Automation started");
			if (browser.toUpperCase().contains("CH")) {
				ChromeOptions options = new ChromeOptions();
				options.addArguments("no-sandbox");
				options.addArguments("start-maximized");
				options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
				options.setExperimentalOption("useAutomationExtension", false);
				cap = DesiredCapabilities.chrome();
				cap.setCapability(ChromeOptions.CAPABILITY, options);
				cap.setBrowserName("chrome");
				if (HTML.properties.getProperty("RemoteType").equalsIgnoreCase("VM")) {
					cap.setPlatform(Platform.WINDOWS);
				} else if (HTML.properties.getProperty("RemoteType").equalsIgnoreCase("DOCKERS")) {
					cap.setPlatform(Platform.LINUX);
				}
			}
			if (browser.toUpperCase().contains("FF")) {
				cap = DesiredCapabilities.firefox();
				cap.setBrowserName("firefox");
				cap.setPlatform(Platform.WINDOWS);
			}
			if (browser.toUpperCase().contains("IE")) {
				cap = DesiredCapabilities.internetExplorer();
				cap.setBrowserName("iexplore");
				cap.setPlatform(Platform.WINDOWS);
			}
			try {
				// driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),cap);
				System.out.println("new URL is :::" + new URL(HTML.properties.getProperty("RemoteURL")));
				System.out.println("cap is :::" + cap);
				driver = new RemoteWebDriver(new URL(HTML.properties.getProperty("RemoteURL")), cap);
				/*
				 * try { hostname = getHostName(driver.getSessionId());
				 * log.info("Thread ID = "+Thread.currentThread().getId()
				 * +" and Machine IP Address = "+hostname+""); } catch (UnknownHostException e)
				 * {
				 * 
				 * e.printStackTrace(); }
				 */
			} catch (MalformedURLException e) {

				e.printStackTrace();
			}
			log.info("Headless '" + browser.toUpperCase() + "' DesiredCapabilities initiated");
			log.info("Headless '" + browser.toUpperCase() + "' driver started");
		}
		return driver; // can be replaced with other browser drivers
	}

	public String getHostName(SessionId session) throws UnknownHostException {
		String hostDetail = null;
		String hostName = "localhost";
		int port = 4444;
		String errorMsg = "Failed to acquire remote webdriver node and port info. Root cause: ";
		try {
			HttpHost host = new HttpHost(hostName, port);
			DefaultHttpClient client = new DefaultHttpClient();
			URL sessionURL = new URL("http://" + hostName + ":" + port + "/grid/api/testsession?session=" + session);
			// System.out.println("URL is : "+sessionURL);
			BasicHttpEntityEnclosingRequest r = new BasicHttpEntityEnclosingRequest("POST", sessionURL.toExternalForm());
			HttpResponse response = client.execute(host, r);
			// JSONObject object = extractObject(response);
			// URL myURL = new URL(object.getString("proxyId"));
			JsonObject myjsonobject = extractObject(response);
			JsonElement url = myjsonobject.get("proxyId");
			// System.out.println(url.getAsString());
			URL myURL = new URL(url.getAsString());
			if ((myURL.getHost() != null) && (myURL.getPort() != -1)) {
				hostDetail = myURL.getHost();
			}
		} catch (Exception e) {
			throw new RuntimeException(errorMsg, e);
		}
		return hostDetail;
	}

	private JsonObject extractObject(HttpResponse resp) throws IOException {
		BufferedReader rd = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
		StringBuffer s = new StringBuffer();
		String line;
		while ((line = rd.readLine()) != null) {
			s.append(line);
		}
		rd.close();
		JsonParser parser = new JsonParser();
		JsonObject objToReturn = (JsonObject) parser.parse(s.toString());
		System.out.println(objToReturn.toString());
		System.out.println(objToReturn.get("proxyId"));
		return objToReturn;
	}
}
