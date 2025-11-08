package dat.controllers;


public interface IController<DTO, ID> {
    void read(io.javalin.http.Context ctx);
    void readAll(io.javalin.http.Context ctx);
    void create(io.javalin.http.Context ctx);
    void update(io.javalin.http.Context ctx);
    void delete(io.javalin.http.Context ctx);
    boolean validatePrimaryKey(ID d);
    DTO validateEntity(io.javalin.http.Context ctx) ;

}
