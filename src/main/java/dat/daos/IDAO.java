package dat.daos;


public interface IDAO<T, I> {

    T read(I i) throws dat.exceptions.ApiException;
    java.util.List<T> readAll() throws dat.exceptions.ApiException;
    T create(T t) throws dat.exceptions.ApiException;
    T update(I i, T t) throws dat.exceptions.ApiException;
    void delete(I i) throws dat.exceptions.ApiException;
    boolean validatePrimaryKey(I i);

}
