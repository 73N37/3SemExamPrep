package dat.controllers.impl;

public
class
ExceptionController {
    private final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(
            dat.routes.Routes.class
    );

    public
    void
    apiExceptionHandler(
            dat.exceptions.ApiException     e,
            io.javalin.http.Context         ctx
    ) {
        LOGGER.error(
                ctx.attribute(
                        "requestInfo") + " " + ctx.res().getStatus() + " " + e.getMessage()
        );

        ctx.status(
                e.getStatusCode()
        );

        ctx.json(
                new dat.exceptions.Message(
                        e.getStatusCode(),
                        e.getMessage()
                )
        );
    }

    public
    void
    exceptionHandler(
            java.lang.Exception e,
            io.javalin.http.Context ctx
    ) {
        LOGGER.error(
                ctx.attribute("requestInfo") + " " + ctx.res().getStatus() + " " + e.getMessage()
        );

        ctx.status(
                500
        );

        ctx.json(
                new dat.exceptions.Message(
                        500,
                        e.getMessage()
                )
        );
    }

}
