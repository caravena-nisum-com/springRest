package com.demo.controller;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.when;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.demo.domain.Person;
import com.demo.service.ServiceInterface;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;

@RunWith(MockitoJUnitRunner.class)
public class TestPersonController {

    List<Person> persons = new ArrayList<Person>();

    @Mock
    ServiceInterface<Person> service;

    @InjectMocks
    PersonController personController;

    @Before
    public void setupPersonController() {

        for (int i = 0; i < 3; i++) {
            persons.add(new Person(i, "John" + " " + i, 20, "description" + " " + i));
        }

        Mockito.when(service.exist(0)).thenReturn(true);
        Mockito.when(service.exist(1)).thenReturn(true);
        Mockito.when(service.exist(2)).thenReturn(true);

        Mockito.when(service.getById(0)).thenReturn(persons.get(0));
        Mockito.when(service.getById(1)).thenReturn(persons.get(1));
        Mockito.when(service.getById(2)).thenReturn(persons.get(2));

        Mockito.when(service.getAll()).thenReturn(persons);

        Mockito.when(service.deleteById(0)).thenReturn(persons.get(0));
        Mockito.when(service.deleteById(1)).thenReturn(persons.get(1));
        Mockito.when(service.deleteById(2)).thenReturn(persons.get(2));

        RestAssuredMockMvc.standaloneSetup(personController);
    }

    @After
    public void resetRestAssuredMockMvc() {
        RestAssuredMockMvc.reset();
    }

    /**
     * AC4: Create a new person Given: the user request ".../rest/person/create"
     * And: and the JSON object with the data When: I pass the request to the
     * REST Service by POST method. Then: I get a JSON message of SUCCESS.
     */
    @Test
    public void testCreateNewPerson() {
        given().param("name", "pepe").param("age", 10).param("description", "hace popo").when()
                .post("/rest/person/create").then().statusCode(200).body("id", equalTo(1));
    }

    /**
     * AC1: Get person by id: Given: the user request ".../rest/person{id}".
     * When: I pass the request to the REST Service by GET method. Then: I get
     * the datas of a person witch the id in JSON format.
     */
    @Test
    public void testGetPersonById() {
        when().get("/rest/person/get/{id}", 0).then().statusCode(200).body("name", equalTo("John 0"))
                .body("age", equalTo(20)).body("description", equalTo("description 0"));
    }

    /**
     * AC2: Get a list of persons: Given: the user request
     * ".../rest/persons/list". When: I pass the request to the REST Service by
     * GET method. Then: I get a list of persons in JSON format.
     */
    @Test
    public void testGetListOfPersons() {
        when().get("/rest/person/list").then().statusCode(200).body("name[0]", equalTo("John 0"))
                .body("age[0]", equalTo(20)).body("description[0]", equalTo("description 0"))
                .body("name[1]", equalTo("John 1")).body("age[1]", equalTo(20))
                .body("description[1]", equalTo("description 1")).body("name[2]", equalTo("John 2"))
                .body("age[2]", equalTo(20)).body("description[2]", equalTo("description 2"));
    }

    /**
     * AC3: Delete person by ID Given: the user request
     * ".../rest/person/delete/{id}". When: I pass the request to the REST
     * Service by DELETE method. Then: I delete the person with the
     * corresponding id And: I get a JSON message of SUCCESS.
     */
    @Test
    public void testDeletePersonById() {
        given().contentType("application/json").when().delete("/rest/person/delete/{id}", 1).then().statusCode(200)
                .body("name", equalTo("John 1")).body("age", equalTo(20)).body("description", equalTo("description 1"));
    }

    /**
     * AC5: Update a person Given: the user request ".../rest/person/update"
     * And: and the JSON object with the data When: I pass the request to the
     * REST Service by PUT method. Then: I get a JSON message of SUCCESS
     */
    @Test
    public void testUpdatePerson() {
        given().contentType(ContentType.JSON).body(new Person(1, "Juan", 90, "New description")).when()
                .put("/rest/person/update").then().statusCode(200);
    }
}
