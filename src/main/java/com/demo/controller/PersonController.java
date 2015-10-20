package com.demo.controller;


import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.domain.Person;
import com.demo.mapper.ServiceInterface;

import lombok.Setter;

@RequestMapping("/rest/person")
@RestController
public class PersonController {
	
	@Autowired
	@Setter public ServiceInterface<Person> service;

    private final AtomicLong counter = new AtomicLong();
    
    @RequestMapping(value="/get/{id}",method=GET, produces={APPLICATION_JSON_VALUE})
    public Person getPersonById(@PathVariable("id") int id){
    	return service.getById(id);
    }
    
    @RequestMapping(value="/create",method=POST, produces= "application/json")
    public Person createNewPerson(@RequestParam(value="name") String name, 
    						   @RequestParam(value="age") int age, 
    						   @RequestParam(value="description") String description) {
    	Person person = new Person((int) counter.incrementAndGet(), name, (int)age, description);
    	service.add(person);
    	return person;
    }
    
    @RequestMapping(value="/list",method=GET, produces="application/json")
    public List<Person> getListOfPersons(){
    	return service.getAll();
    }
    
    @RequestMapping(value="/delete/{id}",method=DELETE,produces={APPLICATION_JSON_VALUE})
    public Person deletePersonById(@PathVariable("id") int id){
    	return service.deleteById(id);
    }
    
    @ExceptionHandler
    @RequestMapping(value="/update",method=PUT,produces="application/json",consumes="application/json")
    @ResponseBody
    public ResponseEntity<Person> updatePerson(@RequestBody Person person){
    	if(service.exist(person.getId())){
    		service.update(person);
    		return new ResponseEntity<Person>(person,OK);
    	}else{
    		return new ResponseEntity<Person>(NOT_ACCEPTABLE);
    	}
    }
}