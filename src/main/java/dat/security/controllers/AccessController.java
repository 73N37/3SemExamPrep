package dat.security.controllers;


/**
 * Purpose: To handle security in the API at the route level
 *  Author: Jon Bertelsen
 */

public class AccessController implements IAccessController {

    SecurityController securityController = SecurityController.getInstance();

    /**
     * This method checks if the user has the necessary roles to access the route.
     * @param ctx
     */
    public void accessHandler(io.javalin.http.Context ctx) {

        // If no roles are specified on the endpoint, then anyone can access the route
        if (ctx.routeRoles().isEmpty() || ctx.routeRoles().contains(dat.security.enums.Role.ANYONE)){
           return;
        }

        // Check if the user is authenticated
        try {
            securityController.authenticate().handle(ctx);
        } catch (io.javalin.http.UnauthorizedResponse e) {
            throw new io.javalin.http.UnauthorizedResponse(e.getMessage());
        } catch (Exception e) {
            throw new io.javalin.http.UnauthorizedResponse("You need to log in, dude! Or you token is invalid.");
        }

        // Check if the user has the necessary roles to access the route
        dk.bugelhartmann.UserDTO user = ctx.attribute("user");
        java.util.Set<io.javalin.security.RouteRole> allowedRoles = ctx.routeRoles(); // roles allowed for the current route
        if (!securityController.authorize(user, allowedRoles)) {
            throw new io.javalin.http.UnauthorizedResponse("Unauthorized with roles: " + user.getRoles() + ". Needed roles are: " + allowedRoles);
        }
    }
}
