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

public class AssignmentPostComplexJson {

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


        List<Integer> rgbaArray2 = new ArrayList<Integer>();
        rgbaArray2.add(0);
        rgbaArray2.add(0);
        rgbaArray2.add(0);
        rgbaArray2.add(1);

        HashMap<String,Object> codeHashMap2 = new HashMap<String,Object>();
        codeHashMap2.put("rgba", rgbaArray2);
        codeHashMap2.put("hex", "#FFF");

        HashMap<String,Object> colorsHashMap2 = new HashMap<String,Object>();
        colorsHashMap2.put("color", "white");
        colorsHashMap2.put("category", "value");
        colorsHashMap2.put("code", codeHashMap2);

        List<Integer> rgbaArray1 = new ArrayList<Integer>();
        rgbaArray1.add(255);
        rgbaArray1.add(255);
        rgbaArray1.add(255);
        rgbaArray1.add(1);

        HashMap<String,Object> codeHashMap1 = new HashMap<String,Object>();
        codeHashMap1.put("rgba", rgbaArray1);
        codeHashMap1.put("hex", "#000");

        HashMap<String,Object> colorsHashMap1 = new HashMap<String,Object>();
        colorsHashMap1.put("color", "black");
        colorsHashMap1.put("category", "hue");
        colorsHashMap1.put("type", "primary");
        colorsHashMap1.put("code", codeHashMap1);

        List<HashMap> colorsArrayList = new ArrayList<HashMap>();
        colorsArrayList.add(colorsHashMap1);
        colorsArrayList.add(colorsHashMap2);

        HashMap<String,List> mainHashMap = new HashMap<String,List>();
        mainHashMap.put("colors", colorsArrayList);




        given().
                body(mainHashMap).
        when().
                post("/postAssignment").
        then().spec(customResponseSpecification).
                assertThat().
                body("msg", equalTo("Success"));
    }
}
