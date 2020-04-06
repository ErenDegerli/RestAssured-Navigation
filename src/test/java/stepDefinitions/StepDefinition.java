package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Assert;
import pojo.AddPlace;
import pojo.Location;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StepDefinition extends Utils {

    RequestSpecification res;
    Response response;
    static String placeId;
    TestDataBuild data = new TestDataBuild();

    @Given("Add Place Payload with {string} {string} {string}")
    public void add_Place_Payload_with(String name, String language, String address) throws IOException {
        res = given().spec(requestSpecification()).body(data.addPlacePayload(name,language,address));
    }

    @When("User calls {string} with {string} http request")
    public void user_calls_with_http_request(String resource,String method) {
        //Write your code here that turns the phrase above into concrete actions
        //Constructor will be called with the value of resource which you pass
        APIResources resourceAPI = APIResources.valueOf(resource);
        if (method.equalsIgnoreCase("POST")) {
            response = res.when().post(resourceAPI.getResource());
        } else if(method.equalsIgnoreCase("GET")) {
            response = res.when().get(resourceAPI.getResource());
        }
    }

    @Then("The API call is success with status code {int}")
    public void the_API_call_is_success_with_status_code(long int1) {
        Assert.assertEquals(response.getStatusCode(), int1);
    }

    @Then("{string} in response body is {string}")
    public void in_response_body_is(String keyValue, String expectedValue) {
        Assert.assertEquals(getJsonPath(response,keyValue), expectedValue);
    }
    @Then("Verify place Id created maps to {string} using {string}")
    public void verify_place_Id_created_maps_to_using(String expectedName, String resource) throws IOException {
        //requestspec
        placeId = getJsonPath(response,"place_id");
        res = given().spec(requestSpecification()).queryParam("place_id",placeId);
        user_calls_with_http_request(resource,"GET");
        String actualName = getJsonPath(response,"name");
        Assert.assertEquals(actualName,expectedName);
    }
    @Given("DeletePlace Payload")
    public void deleteplace_Payload() throws IOException {

        res = given().spec(requestSpecification()).body(data.deletePlacePayload(placeId));
    }
}