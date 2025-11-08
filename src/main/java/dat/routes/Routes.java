package dat.routes;

public
class
Routes
{

    private final SkillRoute        skillRoute      = new SkillRoute();
    private final CandidateRoute    candidateRoute  = new CandidateRoute();


    public
    io.javalin.apibuilder.EndpointGroup
    getRoutes(

    )   {
        return () ->
        {
                io.javalin.apibuilder.ApiBuilder.path(
                        "/skills",
                        skillRoute.getRoutes()
                );

            io.javalin.apibuilder.ApiBuilder.path(
                        "/candidates",
                        candidateRoute.getRoutes()
                );
        };
    }
}
