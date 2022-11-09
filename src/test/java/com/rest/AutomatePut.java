package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

public class AutomatePut {

    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;

    @BeforeClass
    public void beforeClass(){

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.
                setBaseUri("https://api.postman.com").
                addHeader("X-Api-Key", "PMAK-6331ebfc94f4872d2ab8dc8a-38202b389429be56ffa4067233df7c4f09").
                setContentType(ContentType.JSON).
                log(LogDetail.ALL);
        RestAssured.requestSpecification = requestSpecBuilder.build();


        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        RestAssured.responseSpecification = responseSpecBuilder.build();
    }


    @Test
    public void validatePutRequestBDDStyle(){
        String workspaceId = "8b8c7710-3a14-451b-8d03-e43b23675961";
        String payload = "{\n" +
        "    \"workspace\": {\n" +
        "        \"name\": \"My New RestAssured Workspace\",\n" +
        "        \"type\": \"personal\",\n" +
        "        \"description\": \"This is created by RestAssured\"\n" +
        "    }\n" +
        "}";
        given().
                body(payload).
        when().
                put("/workspaces/" + workspaceId).
        then().
        log().all().
                body("workspace.name", equalTo("My RestAssured Workspace"),
                        "workspace.id", matchesPattern("^[a-z0-9-]{36}$"),
                        "workspace.id", equalTo(workspaceId));

    }
}
