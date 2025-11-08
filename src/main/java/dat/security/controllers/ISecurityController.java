package dat.security.controllers;

/**
 * Purpose: To handle security in the API
 * Author: Thomas Hartmann
 */
public interface ISecurityController {
    io.javalin.http.Handler login(); // to get a token
    io.javalin.http.Handler register(); // to get a user
    io.javalin.http.Handler authenticate(); // to verify roles inside token
    boolean authorize(dk.bugelhartmann.UserDTO userDTO, java.util.Set<io.javalin.security.RouteRole> allowedRoles); // to verify user roles
    String createToken(dk.bugelhartmann.UserDTO user) throws Exception;
    dk.bugelhartmann.UserDTO verifyToken(String token) throws Exception;
}
