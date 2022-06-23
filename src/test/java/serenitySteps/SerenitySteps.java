package serenitySteps;

import arquetipo.features.ServicesConfiguration;
import arquetipo.features.ServicesSupport;
import com.google.gson.JsonParser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Step;
import org.junit.Assert;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.*;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static net.serenitybdd.rest.SerenityRest.rest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;

public class SerenitySteps {

    private static final String RESPONSE_SESSION_VARIABLE = "response";
    public static final String AUTHTOKEN = "authToken_";
    public static final String AUTHORIZATION = "Authorization";
    public static final String CACHE_CONTROL = "cache-control";
    public static final String NO_CACHE = "no-cache";


    public static final String CONTENT_TYPE = "Content-Type";
    public static final String ACCEPT = "Accept";
    public static final String APPLICATION_JSON = "application/json";

    public static final String ENDPOINT = "endpoint";
    public static final String TIMESTAMP = "timeStamp";
    public static final String ID = "id";
    public static final String URI = "<URI>";
    public static final String VALUE_NOT_FOUND = "Value not found";
    public static final String YYYYMMDDTHH = "yyyy-MM-dd'T'HH";
    public static final String YYYYMMDDTHHMMSSSSS = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    public static final String YYYYMMDDTHHMMSS = "yyyy-MM-dd'T'HH:mm:ss";


    /**
     * Send a simple request
     *
     * @param method   -> POST,GET,PUT,DELETE...
     * @param endpoint -> is the endpoint
     */
    @Step
    public void sendRequest(String method, String endpoint) throws UnsupportedEncodingException {
        RequestSpecification specification;
        specification = rest()
                .header(ACCEPT, NO_CACHE)
                .header(ACCEPT, APPLICATION_JSON);

        Response response = buildRequest(specification, method, java.net.URLDecoder.decode(endpoint, StandardCharsets.UTF_8.name()))
                .then()
                .extract()
                .response();
        Serenity.setSessionVariable(RESPONSE_SESSION_VARIABLE).to(response);
        System.out.println("La respuesta Json es: \n" + response.asString());
    }


    /**
     * Send a request with a Json
     *
     * @param method   -> Can be PUT,POST,DELETE,GET...
     * @param endpoint -> is the endpoint
     * @param json     -> is the json
     */
    @Step
    public void sendRequestWithJson(String method, String endpoint, String json) {
        RequestSpecification specification = rest()
                .header(CACHE_CONTROL, NO_CACHE)
                .header(ACCEPT, APPLICATION_JSON)
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .body(json);

        Response response = buildRequest(specification, method, endpoint)
                .then()
                .extract()
                .response();
        Serenity.setSessionVariable(RESPONSE_SESSION_VARIABLE).to(response);
        System.out.println("La respuesta Json es: \n" + response.asString());
    }

    /**
     * Build the request
     *
     * @param specification-> the specifications can be : headers, body, params...
     * @param method->        Can be PUT,POST,DELETE,GET...
     * @param endpoint->      is the endpoint
     * @return the request
     */
    private Response buildRequest(RequestSpecification specification, String method, String endpoint) {
        Response response;

        switch (method) {
            case "GET":
                response = specification
                        .relaxedHTTPSValidation()
                        .when()
                        .get(endpoint);
                break;
            case "POST":
                response = specification
                        .relaxedHTTPSValidation()
                        .when()
                        .post(endpoint);
                break;
            case "HEAD":
                response = specification
                        .relaxedHTTPSValidation()
                        .when()
                        .head(endpoint);
                break;
            case "DELETE":
                response = specification
                        .relaxedHTTPSValidation()
                        .when()
                        .delete(endpoint);
                break;
            case "PUT":
                response = specification
                        .relaxedHTTPSValidation()
                        .when()
                        .put(endpoint);
                break;
            case "PATCH":
                response = specification
                        .relaxedHTTPSValidation()
                        .when()
                        .patch(endpoint);
                break;
            default:
                response = specification
                        .relaxedHTTPSValidation()
                        .when()
                        .get(endpoint);
                break;
        }
        return response;
    }

    /**
     * Verify the response code
     *
     * @param statusCode -> the status code to check
     */
    @Step
    public void verifyStatus(int statusCode) {
        Response response = Serenity.sessionVariableCalled(RESPONSE_SESSION_VARIABLE);
        ServicesSupport.verifyStatusCode(statusCode, response.statusCode());
    }


    /**
     * Verify body response that is equal. This method compare two jsons
     *
     * @param pathJson   -> access to a jsonPath
     * @param endpoint-> is the endpoint
     * @throws ParseException
     */
    @Step
    public void verifyBody(String pathJson, String... endpoint) throws ParseException {
        File file;
        file = new File("src/main/resources/json/" + pathJson);
        String responseJson = "";
        String responseString = "";


        try {
            responseJson = ServicesSupport.jsonFileToJsonString(file);
        } catch (Exception e) {
            e.getMessage();
        }

        Response response = Serenity.sessionVariableCalled(RESPONSE_SESSION_VARIABLE);
        responseString = response.getBody().asString();

        if (endpoint != null && responseJson.contains("<" + ENDPOINT + ">")) {
            responseJson = responseJson.replaceAll("<" + ENDPOINT + ">", endpoint[0]);
        }


        assertThat(
                "Unexpected JSON.",
                JsonParser.parseString(responseString.trim()),
                equalTo(JsonParser.parseString(responseJson.trim()))
        );
    }

    /**
     * Verify body response that is equal. This method compare two jsons
     *
     * @param json-> json to verify the response
     */
    @Step
    public void verifyBodyJson(String json) {

        String responseString = "";

        Response response = Serenity.sessionVariableCalled(RESPONSE_SESSION_VARIABLE);
        responseString = response.getBody().asString();

        assertThat(
                "Unexpected JSON.",
                JsonParser.parseString(responseString.trim()),
                equalTo(JsonParser.parseString(json.trim()))
        );
    }

    /**
     * Verify response body that is contained. This method compares two jsons
     *
     * @param pathJson     -> access to a jsonPath
     * @param endpointId-> is the endpoint
     * @throws ParseException
     */
    @Step
    public void verifyContainsBody(String pathJson, String... endpointId) throws ParseException {
        File file;
        file = new File("src/main/resources/json/" + pathJson);
        String responseJson = "";
        try {
            responseJson = ServicesSupport.jsonFileToJsonString(file);
        } catch (Exception e) {
            e.getMessage();
        }
        String responseString = "";
        String timeStamp1 = "";
        String timeStamp2 = "";

        Response response = Serenity.sessionVariableCalled(RESPONSE_SESSION_VARIABLE);
        responseString = response.getBody().asString();

        if (responseJson.contains(URI)) {
            responseJson = responseJson.replaceAll(URI, Serenity.sessionVariableCalled(ServicesConfiguration.SERVICE));
        }

        if (endpointId != null && responseJson.contains("<" + ENDPOINT + ">")) {
            responseJson = responseJson.replaceAll("<" + ENDPOINT + ">", endpointId[0]);
        }

        assertTrue("Json files are different", responseString.replaceAll("\\s+", "").contains(responseJson.replaceAll("\\s+", "")));
    }

    /**
     * This method compares the input value with the value of the json path given.
     *
     * @param compare-> the value to be compared
     * @param value     -> is the value of the key
     * @param keyPath-> is the keyPath
     */
    @Step
    public void verifyKeyValueOfBody(String compare, String value, String keyPath) {
        Response response = Serenity.sessionVariableCalled(RESPONSE_SESSION_VARIABLE);
        String actualValue;
        boolean booleano = false;
        if ("null".equalsIgnoreCase(value)) {
            Assert.assertNull("the value of the key is not null", response.jsonPath().get(keyPath));
        } else {
            Assert.assertNotNull("the value of the key is null", response.jsonPath().get(keyPath).toString());
            actualValue = response.jsonPath().get(keyPath).toString();
            String[] actualValues = actualValue.split(", ");
            for (int i = 0; i < actualValues.length; i++) {
                if ("contain".equals(compare) && actualValues[i].contains(value)) {
                    booleano = true;//assertTrue(VALUE_NOT_FOUND, actualValues[i].contains(value));
                } else if (actualValues[i].equals(value)) {
                    booleano = true;//assertEquals(VALUE_NOT_FOUND, actualValues[i], value);
                }
            }
            assertTrue(booleano);
        }
    }

    /**
     * Check values of the keypath (array)
     *
     * @param value     -> values to check
     * @param keyPath-> is the keypath
     */
    @Step
    public void verifyKeyValuesOfBody(List<String> value, String keyPath) {
        Response response = Serenity.sessionVariableCalled(RESPONSE_SESSION_VARIABLE);
        String actualValue;
        Assert.assertNotNull("the value of the key is null", response.jsonPath().get(keyPath).toString());
        actualValue = response.jsonPath().get(keyPath).toString().replaceAll("\\[|\\]", "");
        String[] actualValues = actualValue.split(", ");
        Set<String> actualValueSet = new HashSet<>();
        actualValueSet.addAll(Arrays.asList(actualValues));


        for (int i = 0; i < actualValueSet.size(); i++) {
            assertTrue(VALUE_NOT_FOUND, actualValueSet.contains(value.get(i)));
        }
    }


    /**
     * Check json schema in the response
     *
     * @param jsonSchemaPath -> Schema json to verify - path
     */
    @Step
    public void verifyJsonSchemaFromFile(String jsonSchemaPath) throws ParseException {
        File schema;
        schema = new File("src/main/resources/jsonSchema/" + jsonSchemaPath);
        Response response = Serenity.sessionVariableCalled(RESPONSE_SESSION_VARIABLE);
        response.then().body(matchesJsonSchema(schema));
    }

    /**
     * Check json schema in the response
     *
     * @param jsonSchema -> Schema json to verify
     */
    @Step
    public void verifyJsonSchema(String jsonSchema) {
        Response response = Serenity.sessionVariableCalled(RESPONSE_SESSION_VARIABLE);
        response.then().body(matchesJsonSchema(jsonSchema));
    }

    @Step
    public void saveSessionVariable(String session_variable, String jsonpath) {
        Response response = Serenity.sessionVariableCalled(RESPONSE_SESSION_VARIABLE);
        try {
            Serenity.setSessionVariable(session_variable).to(response.then().extract().path(jsonpath));
            System.out.println("La varialbe de session guardada tiene el valor " + response.then().extract().path(jsonpath));
        } catch (IllegalStateException e) {
            System.out.println("No se ha podido guardar la variable de session " + session_variable);
        }
    }
}