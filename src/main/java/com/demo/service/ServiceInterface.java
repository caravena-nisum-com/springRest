package com.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface ServiceInterface<T> {
    public void add(T entity);

    public void update(T entity);

    public T getById(int id);

    public T deleteById(int id);

    public List<T> getAll();

    public boolean exist(int id);
}
