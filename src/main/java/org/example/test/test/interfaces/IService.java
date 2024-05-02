package org.example.test.test.interfaces;

import java.util.List;

public interface IService<T> {

    public void add(T t);
    public void delete(int t);
    public List<T> getAll();
    public void update(T t);
    public T getById(int id);
}
