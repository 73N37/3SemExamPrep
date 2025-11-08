package dat.routes;

public
class
SkillRoute {

    private final dat.controllers.impl.SkillController skillController = new dat.controllers.impl.SkillController();

    protected
    io.javalin.apibuilder.EndpointGroup
    getRoutes(

    ) {

        return () ->
        {
            io.javalin.apibuilder.ApiBuilder.get(
                    "/populate",
                    skillController::populate
            );

            io.javalin.apibuilder.ApiBuilder.post(
                    "/",
                    skillController::create
            );

            io.javalin.apibuilder.ApiBuilder.get(
                    "/",
                    skillController::readAll
            );

            io.javalin.apibuilder.ApiBuilder.get(
                    "/{id}",
                    skillController::read
            );

            io.javalin.apibuilder.ApiBuilder.put(
                    "/{id}",
                    skillController::update
            );

            io.javalin.apibuilder.ApiBuilder.delete(
                    "/{id}",
                    skillController::delete
            );
        };
    }
}
