package com.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

@AllArgsConstructor
public @Data class Person {
	
	private @Setter int id;
	private @Setter String name;
	private @Setter int age;
	private @Setter String description;
	
}
