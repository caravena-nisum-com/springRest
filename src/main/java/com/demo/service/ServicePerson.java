package com.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.demo.domain.Person;
import com.demo.mapper.ServiceInterface;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class ServicePerson implements ServiceInterface<Person> {

	private Map<Integer, Person> persons;
	public ServicePerson() {
		persons = new HashMap<Integer,Person>();
	}
	@Override
    public void add(Person entity) {
		entity.setId(entity.getId());
		persons.put((int) entity.getId(), entity);
	}

	@Override
	public void update(Person entity) {
        persons.remove(entity.getId());
		persons.put(entity.getId(), entity);
	}

	@Override
	public Person getById(int id) {
		return persons.get(id);
	}

	@Override
	public Person deleteById(int id) {
		Person person = getById(id);
		persons.remove(id);
		return person;
	}

	@Override
	public List<Person> getAll() {
		List<Person> list = new ArrayList<Person>();
		for(Map.Entry<Integer, Person> entry : persons.entrySet()){
			list.add(entry.getValue());
		}
		return list;
	}

	@Override
	public boolean exist(int id) {
		return persons.containsKey(id);
	}
}
