package com.rest;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.Assert;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class Test {

    @org.testng.annotations.Test

    public void validateGetStatusCode(){
        given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "PMAK-6331ebfc94f4872d2ab8dc8a-38202b389429be56ffa4067233df7c4f09").
        when().
                get("/workspaces").
        then().

                log().all().
                assertThat().
                statusCode(200).
                body("workspaces.name", hasItems("My Workspace", "Automation Testing", "HerokuappCHallenge"),
                "workspaces.type", hasItems("personal", "personal", "personal"),
                        "workspaces[0].name", is(equalTo("My Workspace")),
                        "workspaces.size()", is(equalTo(3)),
                        "workspaces.name", hasItem("Automation Testing"));
    }


    @org.testng.annotations.Test
    public void extractResponse(){
        Response res = given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "PMAK-6331ebfc94f4872d2ab8dc8a-38202b389429be56ffa4067233df7c4f09").
                when().
                get("/workspaces").
                then().

                log().all().
                assertThat().
                statusCode(200).
                extract().
                response();
        System.out.println("response" + res.asString());
    }

    @org.testng.annotations.Test
    public void extractSingleValueFromResponse(){
        String name = given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "PMAK-6331ebfc94f4872d2ab8dc8a-38202b389429be56ffa4067233df7c4f09").
                when().
                get("/workspaces").
                then().

                log().all().
                assertThat().
                statusCode(200).
                extract().
                response().path("workspaces[0].name");
        //   LESSONS 57 & 58 :
        System.out.println("workspace name = " + name);

       // assertThat(name, equalTo("My Workspace"));

        Assert.assertEquals(name,"My Workspace");

       // System.out.println("workspace name " + JsonPath.from(res).getString("workspaces[0].name"));


        /*JsonPath jsonPath = new JsonPath(res.asString());
        System.out.println("workspace name " + jsonPath.getString("workspaces[0].name"));*/

       // System.out.println("workspace name = " + res.path("workspaces[0].name"));
    }

    @org.testng.annotations.Test
    public void validateResponseBody(){
        given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "PMAK-6331ebfc94f4872d2ab8dc8a-38202b389429be56ffa4067233df7c4f09").
                when().
                get("/workspaces").
                then().

                log().all().
                assertThat().
                statusCode(200).
                body("workspaces.name", contains("My Workspace", "Automation Testing", "HerokuappCHallenge"),
                        "workspaces.name", is(not(emptyArray())),
                        "workspaces.name", hasSize(3),
                     //   "workspaces.name", everyItem(startsWith("My"))
                        "workspaces[0]", hasKey("id"),
                        "workspaces[2]", hasValue("HerokuappCHallenge"),
                        "workspaces[2]", hasEntry("id", "3a3c93f2-ebd9-4502-b3cc-fff51e2a1cd9"),
                        "workspaces[2]", not(equalTo(Collections.EMPTY_MAP)),
                        "workspaces[2]", not(empty()),
                        "workspaces[0].name", allOf(startsWith("My"), containsString("Workspace")),
                        "workspaces[0].name", anyOf(not(hasValue("HerokuappCHallenge")), hasKey("id"), hasValue("76ab0426-f24b-4379-82ff-182c0011fced")),
                        "workspaces[0].name", equalTo("My Workspace")
                );


    }


    @org.testng.annotations.Test

    public void multipleHeaders(){
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("header", "value1");
        headers.put("x-mock-match-request-headers", "header");

       /* Header header = new Header("header", "value1");
        Header matchHeader = new Header("x-mock-match-request-headers", "header");
        Headers headers = new Headers(header, matchHeader);*/
         given().
                baseUri("https://07ee9a14-ed53-426d-806d-ee08211c2281.mock.pstmn.io").
                 headers(headers).

         when().
                get("/get").
         then().
                 log().all().
                 assertThat().
                 statusCode(200);

    }


}
