package com.upeg.hb.utils;

import java.io.IOException;
import java.net.URI;
//import java.net.http.HttpClient;
import java.nio.file.Paths;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.upeg.utils.ConfigReader;
import com.upeg.utils.ExtentManager;
import io.github.bonigarcia.wdm.WebDriverManager;


public class BaseTest {

	protected WebDriver driver;
    protected String baseUrl;
    public static final Logger log = LogManager.getLogger(BaseTest.class);
    private ExtentReports extent = ExtentManager.getInstance();
    public ExtentTest test;

    @BeforeMethod
    public void setUp() {
    	
    	// Initialize WebDriver
    	String browserName=ConfigReader.getProperty("browser");
    	if (browserName.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup(); // Automatically downloads and manages chromedriver
            driver = new ChromeDriver();
        } else if (browserName.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup(); // Automatically downloads and manages geckodriver
            driver = new FirefoxDriver();
        } else if (browserName.equalsIgnoreCase("ie")) {
            WebDriverManager.iedriver().setup(); // Automatically downloads and manages IEDriverServer
            driver = new InternetExplorerDriver();
        } else {
            throw new IllegalArgumentException("Invalid browser name: " + browserName);
        }
        driver.manage().window().maximize();
        
        //Test execution started
        test = extent.createTest("UPEG");

        // Set base URL
        baseUrl = ConfigReader.getProperty("baseUrl");

        // Implicitly wait for elements to be present before throwing NoSuchElementException
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterMethod
    public void closeBrowser() {
        // Quit the WebDriver instance after test execution
        if (driver != null) {
            driver.quit();
        }
    }
    
    @AfterSuite
    public void tearDown() {
        extent.flush();
    }

}
