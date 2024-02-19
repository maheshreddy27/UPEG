package com.upeg.pageobject.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.upeg.hb.utils.BasePage;

public class AdminLoginPage extends BasePage {
	@FindBy(id = "sample_id")
    private WebElement sampleid;
		
	public AdminLoginPage(WebDriver driver) {
		super(driver);
	}
	
	

}
