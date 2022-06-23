package gherkinDefinitions;

import arquetipo.features.ServicesConfiguration;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Steps;
import serenitySteps.SerenitySteps;

import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class StepDefs {

    @Steps
    SerenitySteps steps = new SerenitySteps();

    @When("^I send a \"(.*)\" simple request to the service \"(.*)\" for the endpoint \"(.*)\"$")
    public void iSendASimpleRequestToTheServiceForTheEndpoint(String method, String service, String endpoint) throws UnsupportedEncodingException {
        service = ServicesConfiguration.buildEndpoint(service);
        Serenity.setSessionVariable(ServicesConfiguration.SERVICE).to(service);
        endpoint = service + endpoint;
        System.out.println("La url es: " + endpoint);
        steps.sendRequest(method, endpoint);
    }

    @When("^I send a \"(.*)\" request to the service \"(.*)\" for the endpoint \"(.*)\" with the following json:$")
    public void viSendARequestToTheEndpointWithTheFollowingJson(String method, String service, String endpoint, String json) throws Throwable {
        service = ServicesConfiguration.buildEndpoint(service);
        Serenity.setSessionVariable(ServicesConfiguration.SERVICE).to(service);
        endpoint = service + endpoint;
        System.out.println("La url es: \n" + endpoint);
        System.out.println("EL json enviado es: \n" + json);
        steps.sendRequestWithJson(method, endpoint, json);
    }

    @When("^I send a \"(.*)\" request to the service \"(.*)\" for the endpoint \"(.*)\" with the following json file path \"([^\"]*)\"$")
    public void viSendARequestToTheEndpointWithTheFollowingJsonFile(String method, String service, String endpoint, String jsonPath) throws Throwable {
        String json = new String(Files.readAllBytes(Paths.get("src/main/resources/json/" + jsonPath)));
        viSendARequestToTheEndpointWithTheFollowingJson(method, service, endpoint, json);
    }

    @Then("^the response should return status code \"([^\"]*)\"$")
    public void theResponseShouldReturnStatusCode(int status) throws Throwable {
        steps.verifyStatus(status);
    }

    @Then("^the response body should be JSON: \"([^\"]*)\"$")
    public void theResponseBodyShouldBeJSON(String responseBody) throws Throwable {
        steps.verifyBody(responseBody);
    }

    @Then("^the response body should be this JSON:$")
    public void theResponseBodyShouldBeThisJSON(String responseBody) throws Throwable {
        steps.verifyBodyJson(responseBody);
    }

    @Then("^the response body should be JSON: \"(.*)\" - \"(.*)\" - \"(.*)\"$")
    public void theResponseBodyShouldBeJSONWithEndpoint(String responseBody, String endPointId) throws Throwable {
        steps.verifyBody(responseBody, endPointId);
    }

    @Then("^the response body should (be|contain) this value \"([^\"]*)\" in this path \"([^\"]*)\"$")
    public void theResponseBodyShouldContainThisValueInThisPath(String compare, String value, String keyPath) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        steps.verifyKeyValueOfBody(compare, value, keyPath);
    }

    @Then("^the json schema should be:$")
    public void theJsonSchemaShouldBe(String jsonSchema) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        steps.verifyJsonSchema(jsonSchema);
    }

    @Then("^the json schema should be \"([^\"]*)\"$")
    public void theJsonSchemaShouldBeFromFile(String jsonSchemaPath) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        steps.verifyJsonSchemaFromFile(jsonSchemaPath);
    }

    @Then("^the response body should contain these values \"([^\"]*)\" in this path \"([^\"]*)\"$")
    public void theResponseBodyShouldContainTheseValuesInThisPath(List<String> value, String keyPath) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        steps.verifyKeyValuesOfBody(value, keyPath);
    }

    @When("^I save the session variable \"([^\"]*)\" from this json path \"([^\"]*)\"$")
    public void theResponseBodyShouldContainTheseValuesInThisPath(String session_variable, String jsonpath) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        steps.saveSessionVariable(session_variable, jsonpath);
    }
}
