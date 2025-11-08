package dat.security.controllers;


public interface IAccessController {
    void accessHandler(io.javalin.http.Context ctx);
}
