package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import io.restassured.specification.SpecificationQuerier;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class ResponseSpecificationExample {
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;

    @BeforeClass
    public void beforeClass(){
        /* requestSpecification = with().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "PMAK-6331ebfc94f4872d2ab8dc8a-38202b389429be56ffa4067233df7c4f09").
                log().all();*/
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.
                setBaseUri("https://api.postman.com").
                addHeader("X-Api-Key", "PMAK-6331ebfc94f4872d2ab8dc8a-38202b389429be56ffa4067233df7c4f09").
                log(LogDetail.ALL);
        requestSpecification = requestSpecBuilder.build();

       /* responseSpecification = RestAssured.expect().
                statusCode(200).
                contentType(ContentType.JSON);*/

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        responseSpecification = responseSpecBuilder.build();
    }


   @Test
   public void validateStatusCode(){
        given(requestSpecification).
               header("dummyHeader", "dummyValue").
               get("/workspaces").
       then().spec(responseSpecification);

   }

    @Test
    public void validateResponseBody(){
        Response response = given(requestSpecification).get("/workspaces").
        then().spec(responseSpecification).
                extract().
                response();
        assertThat(response.path("workspaces[0].name").toString(), equalTo("My Workspace"));

    }



}
