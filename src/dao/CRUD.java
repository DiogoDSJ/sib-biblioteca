package dao;

public interface CRUD<T> {

    public T create(T obj);

    public void delete(T obj);

    public T findById(T id);
}
