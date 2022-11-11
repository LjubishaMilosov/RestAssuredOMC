package com.rest;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class RequestParameters {

    @Test
    public void pathParameter(){

        given().
                baseUri("https://reqres.in").
                pathParam("userId", "2").
                log().all().
        when().
                get("/api/users/{userId}").
        then().
                assertThat().
                statusCode(200);

    }

    @Test
    public void multiPartFormData() {

        given().
                baseUri("https://postman-echo.com").
                multiPart("foo1", "bar1").
                multiPart("foo2", "bar2").
                log().all().
        when().
                post("/post").
        then().
                log().all().
                assertThat().
                statusCode(200);
    }

    }
