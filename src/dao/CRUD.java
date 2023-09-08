package dao;

import java.util.List;


public interface CRUD<T> {

    public T create(T obj);

    public void delete(T obj);

    public T findByPk(T obj);

    public void deleteMany();

    public T update(T obj);

    public List<T> findMany();



}
