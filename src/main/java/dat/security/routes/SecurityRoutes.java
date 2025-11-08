package dat.security.routes;

/**
 * Purpose: To handle security in the API
 *  Author: Thomas Hartmann
 */
public
class
SecurityRoutes
{
    private static com.fasterxml.jackson.databind.ObjectMapper jsonMapper           = new dat.utils.Utils().getObjectMapper();

    private static dat.security.controllers.SecurityController securityController   = dat.security.controllers.SecurityController.getInstance();

    public
    static
    io.javalin.apibuilder.EndpointGroup
    getSecurityRoutes()
    {
        return ()->{
            io.javalin.apibuilder.ApiBuilder.path(
                    "/auth",
                    ()->{
                        io.javalin.apibuilder.ApiBuilder.get(
                                "/healthcheck", 
                                securityController::healthCheck,
                                dat.security.enums.Role.ANYONE
                        );

                        io.javalin.apibuilder.ApiBuilder.get(
                                "/test",
                                ctx->ctx.json(
                                        jsonMapper.createObjectNode().put(
                                                "msg",
                                                "Hello from Open Deployment"
                                        )
                                ),
                                dat.security.enums.Role.ANYONE
                        );

                        io.javalin.apibuilder.ApiBuilder.post(
                                "/login",
                                securityController.login(),
                                dat.security.enums.Role.ANYONE
                        );

                        io.javalin.apibuilder.ApiBuilder.post(
                                "/register",
                                securityController.register(),
                                dat.security.enums.Role.ANYONE
                        );

                        io.javalin.apibuilder.ApiBuilder.post(
                                "/user/addrole",
                                securityController.addRole(),
                                dat.security.enums.Role.USER
                        );

                    }
            );
        };
    }
    public
    static
    io.javalin.apibuilder.EndpointGroup
    getSecuredRoutes()
    {
        return ()->{
            io.javalin.apibuilder.ApiBuilder.path(
                    "/protected",
                    ()->{
                        io.javalin.apibuilder.ApiBuilder.get("/user_demo", (ctx)->ctx.json(jsonMapper.createObjectNode().put("msg", "Hello from USER Protected")), dat.security.enums.Role.USER);
                        io.javalin.apibuilder.ApiBuilder.get("/admin_demo", (ctx)->ctx.json(jsonMapper.createObjectNode().put("msg", "Hello from ADMIN Protected")), dat.security.enums.Role.ADMIN);
                    }
            );
        };
    }
}
