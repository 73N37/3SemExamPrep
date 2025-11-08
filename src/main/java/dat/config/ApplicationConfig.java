package dat.config;

public
class
ApplicationConfig
{

    private static dat.routes.Routes                            routes              = new dat.routes.Routes();

    private static com.fasterxml.jackson.databind.ObjectMapper  jsonMapper          = new dat.utils.Utils().getObjectMapper();

    private static dat.security.controllers.SecurityController  securityController  = dat.security.controllers.SecurityController.getInstance();

    private static dat.security.controllers.AccessController    accessController    = new dat.security.controllers.AccessController();

    private static org.slf4j.Logger                             logger              = org.slf4j.LoggerFactory.getLogger(
            ApplicationConfig.class
    );

    private static int count = 1;

    public
    static
    void
    configuration(
            io.javalin.config.JavalinConfig config
    ) {
        config.showJavalinBanner = false;

        config.bundledPlugins.enableRouteOverview(
                "/routes",
                dat.security.enums.Role.ANYONE
        );

        config.router.contextPath = "/api"; // base path for all endpoints

        config.router.apiBuilder(
                routes.getRoutes()
        );

        config.router.apiBuilder(
                dat.security.routes.SecurityRoutes.getSecuredRoutes()
        );

        config.router.apiBuilder(
                dat.security.routes.SecurityRoutes.getSecurityRoutes()
        );
    }

    public
    static
    io.javalin.Javalin
    startServer(
            int port
    ) {
        io.javalin.Javalin app = io.javalin.Javalin.create(
                ApplicationConfig::configuration
        );

        app.beforeMatched(
                accessController::accessHandler
        );

        app.after(
                ApplicationConfig::afterRequest
        );

        app.exception(
                java.lang.Exception.class,
                ApplicationConfig::generalExceptionHandler
        );

        app.exception(
                dat.security.exceptions.ApiException.class,
                ApplicationConfig::apiExceptionHandler
        );

        app.start(
                port
        );

        try {
            new dat.security.daos.SecurityDAO(
                    dat.config.HibernateConfig.getEntityManagerFactory()
            ).createUser(
                    "user",
                    "test123"
            );//.addRole();
        } catch (
                java.lang.Exception ex
        )   {
            System.out.println("User already exists \n" + ex.getMessage() );
        }

        return app;
    }

    public
    static
    void
    afterRequest(
            io.javalin.http.Context ctx
    ) {
        java.lang.String requestInfo = ctx.req().getMethod() + " " + ctx.req().getRequestURI();

        logger.info(
                " Request {} - {} was handled with status code {}",
                count++,
                requestInfo,
                ctx.status()
        );
    }

    public
    static
    void
    stopServer(
            io.javalin.Javalin app
    ) {
        app.stop();
    }

    private
    static
    void
    generalExceptionHandler(
            java.lang.Exception e,
            io.javalin.http.Context ctx
    ) {
        logger.error(
                "An unhandled exception occurred",
                e.getMessage()
        );

        ctx.json(
                dat.utils.Utils.convertToJsonMessage(
                        ctx,
                        "error",
                        e.getMessage()
                )
        );
    }

    public
    static
    void
    apiExceptionHandler(
            dat.security.exceptions.ApiException    e,
            io.javalin.http.Context                 ctx
    ) {
        ctx.status(
                e.getCode()
        );

        logger.warn(
                "An API exception occurred: Code: {}, Message: {}",
                e.getCode(),
                e.getMessage()
        );

        ctx.json(
                dat.utils.Utils.convertToJsonMessage(
                        ctx,
                        "warning",
                        e.getMessage()
                )
        );
    }
}
