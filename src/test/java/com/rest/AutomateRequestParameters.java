package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class AutomateRequestParameters {


    @Test
    public void singleQueryParameter(){


        given().
                baseUri("https://postman-echo.com").
               // param("foo1", "bar1").
                queryParams("foo1", "bar1").
                log().all().
        when().
                get("/get").
        then().
                log().all().
                assertThat().
                statusCode(200);
    }


    @Test
    public void pathParameter(){


        given().
                baseUri("https://reqres.in").
                       pathParam("userId", "2").
                log().all().
        when().
                get("/api/users/{userId}").
        then().
                log().all().
                assertThat().
                statusCode(200);
    }
}
