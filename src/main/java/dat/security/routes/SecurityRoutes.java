package dat.security.routes;

import com.fasterxml.jackson.databind.ObjectMapper;

import dat.utils.Utils;
//import dat.security.controllers.SecurityController;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

/**
 * Purpose: To handle security in the API
 *  Author: Thomas Hartmann
 */
public
class
SecurityRoutes
{
    private static com.fasterxml.jackson.databind.ObjectMapper jsonMapper = new dat.utils.Utils().getObjectMapper();
    private static dat.security.controllers.SecurityController securityController = dat.security.controllers.SecurityController.getInstance();
    public static EndpointGroup getSecurityRoutes() {
        return ()->{
            path("/auth", ()->{
                get(
                        "/healthcheck",
                        securityController::healthCheck,
                        dat.security.enums.Role.ANYONE
                );

                get(
                        "/test",
                        ctx->ctx.json(
                                jsonMapper.createObjectNode().put(
                                        "msg",
                                        "Hello from Open Deployment"
                                )
                        ),
                        dat.security.enums.Role.ANYONE
                );

                post(
                        "/login",
                        securityController.login(),
                        dat.security.enums.Role.ANYONE
                );

                post(
                        "/register",
                        securityController.register(),
                        dat.security.enums.Role.ANYONE
                );

                post(
                        "/user/addrole",
                        securityController.addRole(),
                        dat.security.enums.Role.USER
                );

//                get(
//                        "/candidate/{id}/skills",
//                        securityController.getSkills(io.javalin.http.Contextt ctx),
//                        dat.security.enums.Role.USER
//                );
            });
        };
    }
    public static EndpointGroup getSecuredRoutes(){
        return ()->{
            path("/protected", ()->{
                get("/user_demo", (ctx)->ctx.json(jsonMapper.createObjectNode().put("msg", "Hello from USER Protected")), dat.security.enums.Role.USER);
                get("/admin_demo", (ctx)->ctx.json(jsonMapper.createObjectNode().put("msg", "Hello from ADMIN Protected")), dat.security.enums.Role.ADMIN);
            });
        };
    }
}
