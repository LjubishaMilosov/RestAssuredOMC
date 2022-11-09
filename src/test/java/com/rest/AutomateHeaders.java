package com.rest;

import io.restassured.http.Header;
import io.restassured.http.Headers;

import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;

public class AutomateHeaders {


    @org.testng.annotations.Test

    public void multipleHeaders(){

        Header header = new Header("header", "value1");
        Header matchHeader = new Header("x-mock-match-request-headers", "header");
        given().
                baseUri("https://07ee9a14-ed53-426d-806d-ee08211c2281.mock.pstmn.io").
                header(header).
                header(matchHeader).
        when().
                get("/").
        then().
                log().all().
                assertThat().
                statusCode(200);
    }


    @org.testng.annotations.Test

    public void multipleHeaders1(){

        Header header = new Header("header", "value1");
        Header matchHeader = new Header("x-mock-match-request-headers", "header");
        Headers headers = new Headers(header, matchHeader);

        given().
                baseUri("https://07ee9a14-ed53-426d-806d-ee08211c2281.mock.pstmn.io").
                headers(headers).
        when().
                get("/").
        then().
                log().all().
                assertThat().
                statusCode(200);
    }


    @org.testng.annotations.Test

    public void multipleHeaders2(){

        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("header", "value1");
        headers.put("x-mock-match-request-headers", "header");

        given().
                baseUri("https://07ee9a14-ed53-426d-806d-ee08211c2281.mock.pstmn.io").
                headers(headers).
        when().
                get("/").
        then().
                log().all().
                assertThat().
                statusCode(200);
    }


    @org.testng.annotations.Test

    public void multipleHeaders3(){

        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("header", "value1");
        headers.put("x-mock-match-request-headers", "header");

        given().
                baseUri("https://07ee9a14-ed53-426d-806d-ee08211c2281.mock.pstmn.io").
                header("multiValueHeader", "value1", "value2").
                log().headers().
        when().
                get("/").
        then().
                log().all().
                assertThat().
                statusCode(200);
    }

    @org.testng.annotations.Test

    public void assertResponseHeaders(){

        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("header", "value1");
        headers.put("x-mock-match-request-headers", "header");

        given().
                baseUri("https://07ee9a14-ed53-426d-806d-ee08211c2281.mock.pstmn.io").
                headers(headers).
                log().headers().
        when().
                get("/").
        then().
                log().all().
                assertThat().
                statusCode(200).
                header("responseHeader", "resValue1").
                header("X-RateLimit-Limit", "120");
    }

    @org.testng.annotations.Test

    public void assertResponseHeaders1(){

        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("header", "value1");
        headers.put("x-mock-match-request-headers", "header");

        given().
                baseUri("https://07ee9a14-ed53-426d-806d-ee08211c2281.mock.pstmn.io").
                headers(headers).
                log().headers().
        when().
                get("/").
        then().
                log().all().
                assertThat().
                statusCode(200).
                headers("responseHeader", "resValue1",
                 "X-RateLimit-Limit", "120");

    }

    @org.testng.annotations.Test

    public void extractResponseHeaders(){

        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("header", "value1");
        headers.put("x-mock-match-request-headers", "header");

        Headers extractedHeaders = given().
                baseUri("https://07ee9a14-ed53-426d-806d-ee08211c2281.mock.pstmn.io").
                headers(headers).
        when().
                get("/").
        then().
                assertThat().
                statusCode(200).
                extract().
                headers();

        System.out.println("Header name = " + extractedHeaders.get("responseHeader").getName());
        System.out.println("Header value = " + extractedHeaders.get("responseHeader").getValue());
        System.out.println("Header value = " + extractedHeaders.getValue("responseHeader"));
    }


    @org.testng.annotations.Test

    public void extractAllResponseHeaders(){

        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("header", "value1");
        headers.put("x-mock-match-request-headers", "header");

        Headers extractedHeaders = given().
                baseUri("https://07ee9a14-ed53-426d-806d-ee08211c2281.mock.pstmn.io").
                headers(headers).
                when().
                get("/").
                then().
                assertThat().
                statusCode(200).
                extract().
                headers();

        for (Header header: extractedHeaders){
            System.out.println("header name = " + header.getName() + ", ");
            System.out.println("header value = " + header.getValue());
        }
    }

    @org.testng.annotations.Test

    public void extractMultiValueResponseHeader(){

        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("header", "value1");
        headers.put("x-mock-match-request-headers", "header");

        Headers extractedHeaders = given().
                baseUri("https://07ee9a14-ed53-426d-806d-ee08211c2281.mock.pstmn.io").
                headers(headers).
                when().
                get("/").
                then().
                assertThat().
                statusCode(200).
                extract().
                headers();

        List<String> values = extractedHeaders.getValues("multiValueHeader");
        for (String value:values){
               System.out.println(value);
        }
    }




}
