package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

public class AutomatePost {

  //  RequestSpecification requestSpecification;
   // ResponseSpecification responseSpecification;

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
    public void validatePostRequestBDDStyle(){
        String payload = "{\n" +
                "    \"workspace\": {\n" +
                "        \"id\": \"76ab0426-f24b-4379-82ff-182c0011fced\",\n" +
                "        \"name\": \"My RestAssured Workspace\",\n" +
                "        \"type\": \"personal\",\n" +
                "        \"description\": \"RestAssured created this.\"\n" +
                "    }\n" +
                "}";
        given().
                body(payload).
                post("/workspaces").
                then().
                assertThat().
                body("workspace.name", equalTo("My RestAssured Workspace"),
                        "workspace.id", matchesPattern("^[a-z0-9-]{36}$"));

    }
// something isn't right with the path
    /* @Test
   public void validatePostRequestNonBDDStyle(){
        String payload = "{\n" +
                "    \"workspace\": {\n" +
                "        \"id\": \"76ab0426-f24b-4379-82ff-182c0011fced\",\n" +
                "        \"name\": \"My RestAssured Workspace\",\n" +
                "        \"type\": \"personal\",\n" +
                "        \"description\": \"RestAssured created this.\"\n" +
                "    }\n" +
                "}";
        Response response = with().
                body(payload).
                post("/workspaces");
        assertThat(response.path("workspace.name", equalTo("My RestAssured Workspace")));
        assertThat(response.path("workspace.id", matchesPattern("^[a-z0-9-]{36}$")));


    }*/

    @Test
    public void validatePostRequestPayloadFromFile(){
        File file = new File("src/main/resources/CreateWorkspacePayload.json");
        given().
                body(file).
                post("/workspaces").
                then().
                assertThat().
                body("workspace.name", equalTo("My RestAssured Workspace"),
                        "workspace.id", matchesPattern("^[a-z0-9-]{36}$"));

    }

    @Test
    public void validatePostRequestPayloadAsMAp(){
        HashMap<String, Object> mainObject = new HashMap<String, Object>();

        HashMap<String, String> nestedObject = new HashMap<String, String>();
        nestedObject.put("name", "My third Workspace");
        nestedObject.put("type", "personal");
        nestedObject.put("description", "Rest Assured created this");

        mainObject.put("workspace", nestedObject);
        given().
                body(mainObject).
        when().
                post("/workspaces").
        then().
                assertThat().
                body("workspace.name", equalTo("My third Workspace"),
                        "workspace.id", matchesPattern("^[a-z0-9-]{36}$"));

    }

}
