package com.upeg.hb.utils;

import org.junit.BeforeClass;

import com.upeg.utils.ConfigReader;

import io.restassured.RestAssured;

public class APIBaseTest {
	
	@BeforeClass
    public void setUp() {
        // Initialize API base URL and other configurations
        RestAssured.baseURI = ConfigReader.getProperty("apiBaseUrl");
        // Additional configuration if needed

        // Set API authentication if required
        // For example, setting API token
        String apiToken = ConfigReader.getProperty("apiToken");
        if (apiToken != null && !apiToken.isEmpty()) {
            RestAssured.given().header("Authorization", "Bearer " + apiToken);
        }
    }

}
