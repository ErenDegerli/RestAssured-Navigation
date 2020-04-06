package stepDefinitions;
import io.cucumber.java.Before;
import java.io.IOException;

public class Hooks {

    @Before("@DeletePlace")
    public void beforeScenario() throws IOException {
        //execute this code only when place id is null
        //write the code which will give you the place id
        StepDefinition stepDefinition = new StepDefinition();
        if(StepDefinition.placeId ==null) {
            stepDefinition.add_Place_Payload_with("Shetty", "French", "Asia");
            stepDefinition.user_calls_with_http_request("addPlaceAPI", "POST");
            stepDefinition.verify_place_Id_created_maps_to_using("Shetty", "getPlaceAPI");
        }
    }
}
