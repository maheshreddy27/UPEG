package com.upeg.utils;

import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

public class APIUtils {
	public static Response getRequest(String endpoint) {
        return given()
                .when()
                .get(endpoint);
    }

    // Add more methods for POST, PUT, DELETE requests if needed

    public static boolean isStatusCode200(Response response) {
        return response.getStatusCode() == 200;
    }

    // Add more validation methods for status code, response body, etc.

}
