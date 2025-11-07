package dat.controllers;

import io.javalin.http.Context;

public interface IController<DTO, ID> {
    void read(Context ctx);
    void readAll(Context ctx);
    void create(Context ctx);
    void update(Context ctx);
    void delete(Context ctx);
    boolean validatePrimaryKey(ID d);
    DTO validateEntity(Context ctx) ;

}
