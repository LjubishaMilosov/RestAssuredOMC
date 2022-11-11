package com.rest;

import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class UploadFile {

    @Test
    public void uploadFileMultiPartFormData() {
        String attributes = "{\"name\":\"CV.pdf\",\"parent\":{\"id\":\"123456\"}}";
        given().
                baseUri("https://postman-echo.com").
                multiPart("file", new File("CV.pdf")).
                multiPart("attributes", attributes, "application/json").
                log().all().
        when().
                post("/post").
        then().
                log().all().
                assertThat().
                statusCode(200);
    }
}
