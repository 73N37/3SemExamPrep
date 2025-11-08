package dat.routes;

public
class
CandidateRoute
{
    private final dat.controllers.impl.CandidateController candidateController = new dat.controllers.impl.CandidateController();

    protected
    io.javalin.apibuilder.EndpointGroup
    getRoutes()
    {
        return () ->
        {
//TODO =====================[General]==============================
            io.javalin.apibuilder.ApiBuilder.get(
                    "/populate",
                    candidateController::populate
            );
//TODO =====================[Candidate]============================
            io.javalin.apibuilder.ApiBuilder.get(
                    "/filter",
                    candidateController::filterByCategory
            );

            io.javalin.apibuilder.ApiBuilder.post(
                    "/",
                    candidateController::create
            );

            io.javalin.apibuilder.ApiBuilder.get(
                    "/",
                    candidateController::readAll
            );

            io.javalin.apibuilder.ApiBuilder.get(
                    "/{id}",
                    candidateController::read
            );

            io.javalin.apibuilder.ApiBuilder.patch(
                    "/{id}",
                    candidateController::update
            );

            io.javalin.apibuilder.ApiBuilder.delete(
                    "/{id}",
                    candidateController::delete
            );
//TODO =====================[Skill]================================
            io.javalin.apibuilder.ApiBuilder.get(
                    "/{candidateId}/skills/{skillId}",
                    candidateController::getSkillByCandidate
            );

            io.javalin.apibuilder.ApiBuilder.get(
                    "/{candidateId}/skills",
                    candidateController::getAllSkillsByCandidate
            );

            io.javalin.apibuilder.ApiBuilder.patch(
                    "/{candidateId}/skills/{skillId}",
                    candidateController::addSkillToCandidate
            );
        };
    }
}
