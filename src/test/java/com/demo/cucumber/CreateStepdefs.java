package com.demo.cucumber;

import static org.junit.Assert.assertNotNull;

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

    Person[] persons;

    @Given("^I have a valid person (.*), (.*), (.*)$")
    public void I_have_a_valid_person(String name, int age, String description) {
        persons[0] = new Person();
        persons[0].setName(name);
        persons[0].setAge(age);
        persons[0].setDescription(description);
    }

    @When("^I create the person$")
    public void I_create_the_person() {
        Gson json = new Gson();
        String url = RunAllTest.URL_BASE + "/create";
        String requestJson = json.toJson(persons[0]);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);

        RestTemplate restTemplate = new RestTemplate();
        persons[1] = restTemplate.postForObject(url, entity, Person.class);
    }

    @Then("^I should see it persisted$")
    public void I_should_see_it_persisted() {
        RestTemplate restTemplate = new RestTemplate();
        Person person = restTemplate.getForObject(RunAllTest.URL_BASE + "/get" + persons[1].getId(), Person.class);
        assertNotNull(person);
    }
}
