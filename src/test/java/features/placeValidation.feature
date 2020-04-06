Feature: Validating Place API's
  @AddPlace @Regression
  Scenario Outline: Verify if place being successfully added using AddPlaceAPI
    Given Add Place Payload with "<name>" "<language>" "<address>"
    When User calls "addPlaceAPI" with "Post" http request
    Then The API call is success with status code 200
    And "status" in response body is "OK"
    And "scope" in response body is "APP"
    And Verify place Id created maps to "<name>" using "getPlaceAPI"

    Examples:
      | name    | language    |   address        |
      |AAhouse  | English     |World cross centre|
      |BBhouse  | Spanish     |Sea cross centre  |
    @DeletePlace @Regression
    Scenario: Verify if Delete Place functionality working
      Given DeletePlace Payload
      When User calls "deletePlaceAPI" with "Post" http request
      Then The API call is success with status code 200
      And "status" in response body is "OK"