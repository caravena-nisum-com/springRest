Feature: Creation of a Person
    Scenario: As a Client, I want a service that allow me create a new person
        Given I have a valid person Pepe, 10, Bad Kid
        When I create the person
        Then I should see it persisted