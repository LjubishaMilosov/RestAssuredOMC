package com.rest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasItem;

public class RequestSpecificationExample {
    RequestSpecification requestSpecification;

    @BeforeClass
    public void beforeClass(){
        /* requestSpecification = with().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "PMAK-6331ebfc94f4872d2ab8dc8a-38202b389429be56ffa4067233df7c4f09").
                log().all();*/
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
       /* requestSpecBuilder.setBaseUri("https://api.postman.com");
        requestSpecBuilder.
        addHeader("X-Api-Key", "PMAK-6331ebfc94f4872d2ab8dc8a-38202b389429be56ffa4067233df7c4f09");
        requestSpecBuilder.log(LogDetail.ALL);*/
        requestSpecBuilder.
                setBaseUri("https://api.postman.com").
                addHeader("X-Api-Key", "PMAK-6331ebfc94f4872d2ab8dc8a-38202b389429be56ffa4067233df7c4f09").
                log(LogDetail.ALL);
        requestSpecification = requestSpecBuilder.build();
    }


    @Test
    public void queryTest(){
        QueryableRequestSpecification queryableRequestSpecification = SpecificationQuerier.
                query(requestSpecification);
        System.out.println(queryableRequestSpecification.getBaseUri());
        System.out.println(queryableRequestSpecification.getHeaders());
    }


   @Test
   public void validateGetStatusCode(){
       Response response = given(requestSpecification).
               header("dummyHeader", "dummyValue").
               get("/workspaces").then().log().all().extract().response();
       assertThat(response.statusCode(), is(equalTo(200)));

   }

    @Test
    public void validateResponseBody(){
        Response response = given(requestSpecification).get("/workspaces").then().log().all().extract().response();
        assertThat(response.statusCode(), is(equalTo(200)));
        assertThat(response.path("workspaces[0].name").toString(), equalTo("My Workspace"));

    }



}
