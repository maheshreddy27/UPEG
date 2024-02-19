package com.upeg.hb.utils;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.apache.logging.log4j.Logger;

import com.upeg.utils.ApplicationConstants;

public class BasePage {
	protected WebDriver driver;
	protected JavascriptExecutor jse;
	ApplicationConstants applicationConstants = new ApplicationConstants();
	protected static final Logger log = LogManager.getLogger(BaseTest.class);

    public BasePage(WebDriver driver) {
        this.driver = driver;
        jse = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }
    
    // Add common methods or functionalities here
    
    public void exeQuery(String query) throws SQLException {
		/**
		 * Des: This function is used execute Query
		 * 
		 * @param query
		 * @author Mahesh Reddy
		 **/
		String databaseURL = applicationConstants.DBURL;
		String user = applicationConstants.DBUSERNAME;
		String password = applicationConstants.DBPASSWORD;

		java.sql.Connection conn = null;
		conn = DriverManager.getConnection(databaseURL, user, password);
		try (Statement stmt = conn.createStatement()) {
			boolean gotResults = stmt.execute(query);
			if (!gotResults) {
				log.info("No results returned");
			}

		} catch (SQLException ex) {
			log.info("SQL execution" + ex.getMessage());
			ex.printStackTrace();
		} catch (Exception e) {
			log.info("An error occurred." + e.getMessage());
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}

		}
	}
    
    public String verifyDB(String query) throws InterruptedException, SQLException {
		/**
		 * Des: This function is used Verify Data Base value
		 * 
		 * @param query
		 * @author Mahesh Reddy
		 **/
		String databaseURL = ApplicationConstants.DBURL;
		String user = ApplicationConstants.DBUSERNAME;
		String password = ApplicationConstants.DBPASSWORD;

		String actualData = null;
		java.sql.Connection conn = null;
		conn = DriverManager.getConnection(databaseURL, user, password);
		try (Statement stmt = conn.createStatement()) {

			ResultSet rs = stmt.executeQuery(query);

			if (!rs.next()) {
				log.info("ResultSet is empty");
			}

			else {
				do {
					actualData = rs.getString(1);
				} while (rs.next());
				{
					log.info("Actual data " + actualData);

				}
			}
		} catch (SQLException ex) {
			log.info("SQL execution" + ex.getMessage());
			ex.printStackTrace();

		} catch (Exception e) {
			log.info("An error couured" + e.getMessage());

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}

		}
		return actualData;

	}
    
    protected void maxWindow() {
			String script = "if (window.screen){window.moveTo(0, 0);window.resizeTo(window.screen.availWidth,window.screen.availHeight);};";
			jse.executeScript(script);
		
	}

	protected void scrollAndWait(int x, int y, int ms) throws InterruptedException {
		jse.executeScript("scroll(" + x + ", " + y + ");");
		Thread.sleep(ms);
	}

	public void javascriptClick(WebElement element){
	    JavascriptExecutor ex = (JavascriptExecutor)driver;
	    ex.executeScript("arguments[0].click();", element);
	}
	
	public void focusSelectAndSelectByValue(WebElement element, String value){
        Actions build = new Actions(driver);
        build.moveToElement(element).build().perform();
        new Select(element).selectByVisibleText(value);
	}

}
