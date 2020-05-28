package com.pc.driver;

import java.util.Properties;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.PropertyConfigurator;

public  class LoggerClass {

/*public static Logger getLogger(Class clazz){

    org.apache.log4j.Logger log = Logger.getLogger(clazz);
    Properties props=new Properties();
    props.setProperty("log4j.appender.file","org.apache.log4j.RollingFileAppender");
     props.setProperty("log4j.appender.logfile","org.apache.log4j.DailyRollingFileAppender");
    props.setProperty("log4j.appender.logfile.DatePattern","'.'yyyy-MM-dd");
    props.setProperty("log4j.appender.logfile.layout","org.apache.log4j.PatternLayout");
    props.setProperty("log4j.appender.logfile.layout.ConversionPattern","%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n");
    props.setProperty("log4j.appender.logfile.File","/home/ekhaavi/workspace/TEST_2/mylogfile.log");
    props.setProperty("log4j.logger.com.com.test.log4j.conf","INFO, logfile");

    PropertyConfigurator.configure(props);
    return log;

}*/

/*public static Logger getThreadLogger(String str){

    org.apache.log4j.Logger log = Logger.getLogger(str);
    Properties props=new Properties();
    props.setProperty("log4j.appender.file","org.apache.log4j.DailyRollingFileAppender");
    props.setProperty("log4j.appender.file.DatePattern","'.'yyyy-MM-dd");
    props.setProperty("log4j.appender.file.layout","org.apache.log4j.PatternLayout");
    props.setProperty("log4j.appender.file.File",".//Reports//Log//"+Thread.currentThread().getName()+".log");
    props.setProperty("log4j.appender.file.layout.ConversionPattern","%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n");
    props.setProperty("log4j.logger."+"Thread" + Thread.currentThread().getName(),"DEBUG, file");

    PropertyConfigurator.configure(props);
    return log;

 }*/

public static Logger getThreadLogger(String str, String tc){

	if(tc.contains(("_")))
			{
		String tcs[]= tc.split("_");
		tc = tcs[0];
			}
    org.apache.log4j.Logger log = Logger.getLogger(tc);
    Properties props=new Properties();
    props.setProperty("log4j.appender.file","org.apache.log4j.DailyRollingFileAppender");
    props.setProperty("log4j.appender.stdout","org.apache.log4j.ConsoleAppender");
    props.setProperty("log4j.appender.file.DatePattern","'.'yyyy-MM-dd");
    props.setProperty("log4j.appender.file.layout","org.apache.log4j.PatternLayout");
    props.setProperty("log4j.appender.file.File",".//Reports//HTMLReports//Log//"+tc+".log");
    props.setProperty("log4j.appender.file.layout.ConversionPattern","%p - %d{dd-MM-yyyy HH:mm:ss} - %C{1} - %M - %m%n");
    props.setProperty("log4j.appender.stdout.layout","org.apache.log4j.PatternLayout");
    props.setProperty("log4j.appender.stdout.layout.ConversionPattern","%5p %d{h:mm a}  %M - %m%n");
    props.setProperty("log4j.logger."+tc,"DEBUG, file, stdout");
    PropertyConfigurator.configure(props);
    return log;

 }
 }
