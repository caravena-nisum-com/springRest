# SYNOPSIS
Spring Rest Project 1.0

tags: Java, Spring, Rest-Service, RestAssured, Gradle, Jenkins, SonarQube

The idea about this project is create a simple RestService Using SpringRest.
For testing was used Junit, RestAssured and build with gradle.
And SonarQube for static cheking

## REST service
I want a REST Service that allow me see the datas of a Person in format JSON, wish are:
* id, name, age and description.

Use cases:

* Create a new person:

	Given: the user request ".../rest/person/create"
	And: and the JSON object with the data.
	When: I pass the request to the REST Service by POST method.
	Then: I get a JSON message of SUCCESS.

* Get person by ID:

	Given: the user request ".../rest/person{id}".
	When: I pass the request to the REST Service by GET method.
	Then: I get the datas of a person witch the id in JSON format.

* Get a list of persons:

	Given: the user request ".../rest/persons/list".
	When: I pass the request to the REST Service by GET method.
	Then: I get a list of persons in JSON format.
	
* Delete person by ID:

	Given: the user request ".../rest/person/delete/{id}".
	When: I pass the request to the REST Service by DELETE method.
	Then: I delete the person with the corresponding id.
	And: I get a JSON message of SUCCESS.

* Update a person:

	Given: the user request ".../rest/person/update".
	And: and the JSON object with the data.
	When: I pass the request to the REST Service by PUT method.
	Then: I get a JSON message of SUCCESS.	
		
## Build
* Default build. Runs unit tests, sonarQube

        $ gradle
               
* For run springBoot

         $ gradle bootRun      