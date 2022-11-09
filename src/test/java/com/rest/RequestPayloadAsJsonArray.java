package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

public class RequestPayloadAsJsonArray {

    ResponseSpecification customResponseSpecification;

    @BeforeClass
    public void beforeClass(){


        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.
                setBaseUri("https://07ee9a14-ed53-426d-806d-ee08211c2281.mock.pstmn.io").
                addHeader("x-mock-match-request-body", "true").
                /*setConfig(config.encoderConfig(EncoderConfig.encoderConfig().
                        appendDefaultContentCharsetToContentTypeIfUndefined(false))).*/
                setContentType("application/json;charset=utf-8").
                log(LogDetail.ALL);

        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType("application/json;charset=utf-8").
                log(LogDetail.ALL);
        customResponseSpecification = responseSpecBuilder.build();
    }


    @Test
    public void validatePostRequestPayloadJsonArrayAsLis(){
        HashMap<String, String> obj5001 = new HashMap<String, String>();
        obj5001.put("id", "5001");
        obj5001.put("type", "None");

        HashMap<String, String> obj5002 = new HashMap<String, String>();
        obj5002.put("id", "5002");
        obj5002.put("type", "Glazed");

        List<HashMap<String, String>> jsonList = new ArrayList<HashMap<String, String>>();
        jsonList.add(obj5001);
        jsonList.add(obj5002);

        given().
                body(jsonList).
        when().
                post("/post").
        then().spec(customResponseSpecification).
                assertThat().
                body("msg", equalTo("Success"));

    }
}
