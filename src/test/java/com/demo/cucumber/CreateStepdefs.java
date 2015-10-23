package com.demo.cucumber;

import static org.junit.Assert.assertNotNull;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.demo.domain.Person;
import com.google.gson.Gson;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CreateStepdefs {
    private static Logger LOGGER = LogManager.getLogger(CreateStepdefs.class);
    Person person, personByPost;

    @Given("I have a valid person (.*), (.*), (.*)")
    public void I_have_a_valid_person(String name, int age, String description) {
        System.out.format("Person:%s %n %s \n", name, age, description);
        person = new Person();
        person.setId(0);
        person.setName(name);
        person.setAge(age);
        person.setDescription(description);
    }

    @When("^I create the person$")
    public void I_create_the_person() {
        Gson json = new Gson();
        String url = RunAllTest.URL_BASE + "/create";
        String requestJson = json.toJson(person);
        LOGGER.info(requestJson);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);

        RestTemplate restTemplate = new RestTemplate();
        personByPost = restTemplate.postForObject(url, entity, Person.class);
    }

    @Then("^I should see it persisted$")
    public void I_should_see_it_persisted() {
        RestTemplate restTemplate = new RestTemplate();
        Person person = restTemplate.getForObject(RunAllTest.URL_BASE + "/get" + personByPost, Person.class);
        assertNotNull(person);
    }

}
