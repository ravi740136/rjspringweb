package springweb.controller.test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class GreetingControllerTest {

    @BeforeClass
    public void setup() {
        // Configure RestAssured base URI and port for your application
        RestAssured.baseURI = "http://localhost/rjspringweb";
        RestAssured.port = 8080; // Use the correct port for your application
        basePath = "/api/greetings"; // Set the base path to your API endpoint
    }

    @Test
    public void testCreateGreeting() {
        String greetingContent = "Hello, RestAssured!";
        Response response =
            given()
                .contentType(ContentType.JSON)
                .body(greetingContent)
            .when()
                .post()
            .then()
                .statusCode(201) // Expecting HTTP 201 Created
                .body("id", notNullValue()) // Checking if an ID is returned
                .body("content", equalTo(greetingContent))
                .extract().response();

        System.out.println("Created Greeting: " + response.asString());
    }

    @Test
    public void testGetGreetingById() {
        // Create a new greeting if needed to ensure a valid ID exists
        String greetingContent = "Dynamic Test Greeting";
        int greetingId = createGreetingIfNotExists(greetingContent);

        given()
        .when()
            .get("/" + greetingId)
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("id", equalTo(greetingId))
            .body("content", equalTo(greetingContent));
    }

    
    @Test
    public void testGetAllGreetings() {
        given().log().all()
        .when()
            .get()
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("size()", greaterThan(0)); // Ensure at least one greeting is returned
    }

    @Test
    public void testUpdateGreeting() {
        // Create a new greeting if needed to ensure a valid ID exists
        String initialContent = "Initial Greeting Content";
        int greetingId = createGreetingIfNotExists(initialContent);

        String updatedContent = "Updated Greeting Content";

        given()
            .contentType(ContentType.JSON)
            .body(updatedContent)
        .when()
            .put("/" + greetingId)
        .then()
            .statusCode(200)
            .body("content", equalTo(updatedContent));
    }

 // Utility method to create a greeting if one does not exist and return its ID
    private int createGreetingIfNotExists(String greetingContent) {
        Response response = given()
            .contentType(ContentType.JSON)
            .body(greetingContent)
        .when()
            .post()
        .then()
            .statusCode(anyOf(equalTo(201), equalTo(200))) // Handle both creation and existence cases
            .extract().response();

        return response.jsonPath().getInt("id"); // Extract and return the ID
    }


    @Test
    public void testDeleteGreeting() {
        // Create a new greeting if needed to ensure a valid ID exists
        String greetingContent = "Greeting to be deleted";
        int greetingId = createGreetingIfNotExists(greetingContent);

        // Perform the DELETE operation on the existing greeting
        given()
        .when()
            .delete("/" + greetingId)
        .then()
            .statusCode(204); // Expecting HTTP 204 No Content

        // Verify that the greeting has been deleted
        given()
        .when()
            .get("/" + greetingId)
        .then()
            .statusCode(404); // Expecting HTTP 404 Not Found
    }
    
    @Test
    public void testGetGreetingsByContent() {
        // Given
        String content = "Hello";

        // When & Then
        given().log().all()
            .queryParam("content", content)
        .when()
            .get("/by-content")
        .then() 
            .statusCode(200)
            .body("[0].content", equalTo("Hello"));
    }

    @Test
    public void testGetGreetingsByKeyword() {
        // Given
        String keyword = "Hello";

        // When & Then
        given()
            .queryParam("keyword", keyword)
        .when()
            .get("/by-keyword")
        .then()
            .statusCode(200)
            .body("size()", greaterThan(0))
            .body("[0].content", containsString("Hello"));
    }

    @Test
    public void testGetGreetingsByContent_NoResults() {
        // Given
        String content = "NonExistentContent";

        // When & Then
        given()
            .queryParam("content", content)
        .when()
            .get("/by-content")
        .then()
            .statusCode(200)
            .body("size()", equalTo(0)); // Expecting no results
    }

    @Test
    public void testGetGreetingsByKeyword_NoResults() {
        // Given
        String keyword = "NonExistentKeyword";

        // When & Then
        given()
            .queryParam("keyword", keyword)
        .when()
            .get("/by-keyword")
        .then()
            .statusCode(200)
            .body("size()", equalTo(0)); // Expecting no results
    }

}
