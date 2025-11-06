package dat.controllers;

import io.javalin.http.Context;

public interface IController<T, D> {
    void read(Context ctx) throws dat.exceptions.ApiException;
    void readAll(Context ctx) throws dat.exceptions.ApiException;
    void create(Context ctx) throws dat.exceptions.ApiException;
    void update(Context ctx) throws dat.exceptions.ApiException;
    void delete(Context ctx) throws dat.exceptions.ApiException;
    boolean validatePrimaryKey(D d);
    T validateEntity(Context ctx) ;

}
