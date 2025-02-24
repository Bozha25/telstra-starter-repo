package stepDefinitions;

import au.com.telstra.simcardactivator.SimCardActivator;
import au.com.telstra.simcardactivator.entity.SimRequest;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;

import java.util.Map;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = SimCardActivator.class, loader = SpringBootContextLoader.class)
public class SimCardActivatorStepDefinitions {


    private static final String BASE_URL = "/api/sim";
    private static final String EMAIL = "test@email.com";
    private ResponseEntity<String> response;
    private String lastActivatedIccid;

    @Autowired
    private TestRestTemplate restTemplate;


    @Given("the SIM card activation service is running")
    public void the_sim_card_activation_service_is_running() {
        // No specific action needed, assuming the service is running.
    }

    @When("I submit an activation request with ICCID {string}")
    public void i_submit_an_activation_request_with_iccid(String iccid){
        lastActivatedIccid = iccid;
        String url = BASE_URL + "/activate";

        SimRequest simRequest = new SimRequest(iccid, EMAIL);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<SimRequest> request = new HttpEntity<>(simRequest, headers);

        response = restTemplate.postForEntity(url, request, String.class);
    }

    @Then("the activation should be successful")
    public void the_activation_should_be_successful() {
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Then("the activation should fail")
    public void the_activation_should_fail() {
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @And("I should be able to query the activation status as {string}")
    public void i_should_be_able_to_query_the_activation_status_as(String expectedStatus) {
        String url = BASE_URL + "/status/" + lastActivatedIccid;
        ResponseEntity<Map> statusResponse = restTemplate.getForEntity(url, Map.class);

        Assertions.assertEquals(HttpStatus.OK, statusResponse.getStatusCode());
        String actualStatus = statusResponse.getBody().get("status").toString();
        Assertions.assertEquals(expectedStatus, actualStatus);
    }


}