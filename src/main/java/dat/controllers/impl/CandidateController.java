package dat.controllers.impl;

public 
class
CandidateController
        implements dat.controllers.IController< dat.dtos.CandidateDTO,
                                                java.lang.Long          >
{
    private final dat.daos.impl.CandidateDAO dao;

    public 
    CandidateController()
    {
        jakarta.persistence.EntityManagerFactory emf    = dat.config.HibernateConfig.getEntityManagerFactory();
        
        this.dao                                        = dat.daos.impl.CandidateDAO.getInstance(
                emf
        );
    }

    @Override
    public 
    void 
    read(
            io.javalin.http.Context ctx
    ) {
        java.lang.Long          id              = null;
        dat.dtos.CandidateDTO   candidateDTO    = null;

        try {
            id =     ctx.pathParamAsClass(
                    "id",
                    java.lang.Long.class
            ).check(
                    this::validatePrimaryKey,
                    "Not a valid id"
            ).get();

            candidateDTO = dao.read(
                    id
            );
        } catch (
                dat.exceptions.ApiException ex
        ) {
            new dat.controllers.impl.ExceptionController().apiExceptionHandler(
                    ex ,
                    ctx
            );
        }
        catch (
                java.lang.Exception ex
        ) {
            new dat.controllers.impl.ExceptionController().exceptionHandler(
                    ex ,
                    ctx
            );
        }

        if (
                id              == null ||
                candidateDTO    == null
        )   {
            ctx.res().setStatus(
                    502
            );
        }   else {
            ctx.res().setStatus(
                    200
            );

            ctx.json(
                    candidateDTO,
                    dat.dtos.CandidateDTO.class
            );
        }
    }

    @Override
    public
    void
    readAll(
            io.javalin.http.Context ctx
    ) {
        java.util.List<dat.dtos.CandidateDTO> candidateDTOs = null;

        try {
            candidateDTOs = dao.readAll();
        }   catch (
                dat.exceptions.ApiException ex
        ) {
            new dat.controllers.impl.ExceptionController().apiExceptionHandler(
                    ex ,
                    ctx
            );
        }
        catch (
                java.lang.Exception ex
        ) {
            new dat.controllers.impl.ExceptionController().exceptionHandler(
                    ex ,
                    ctx
            );
        }

        if (
                candidateDTOs == null   ||
                candidateDTOs.isEmpty()
        )   {
            ctx.res().setStatus(
                    502
            );
        }   else {
            ctx.res().setStatus(
                    200
            );

            ctx.json(
                    candidateDTOs,
                    dat.dtos.CandidateDTO.class
            );
        }
    }

    @Override
    public
    void
    create(
            io.javalin.http.Context ctx
    ) {
        dat.dtos.CandidateDTO jsonRequest   = null;
        dat.dtos.CandidateDTO candidateDTO  = null;
        try {
            jsonRequest = validateEntity(
                    ctx
            );

            candidateDTO = dao.create(
                    jsonRequest
            );
        } catch (
                dat.exceptions.ApiException ex
        ) {
            new dat.controllers.impl.ExceptionController().apiExceptionHandler(
                    ex ,
                    ctx
            );
        }
        catch (
                java.lang.Exception ex
        ) {
            new dat.controllers.impl.ExceptionController().exceptionHandler(
                    ex ,
                    ctx
            );
        }

        if (
                jsonRequest     ==  null    ||
                candidateDTO    ==  null
        )   {
            ctx.res().setStatus(
                    502
            );
        }   else    {
            ctx.res().setStatus(
                    201
            );

            ctx.json(
                    candidateDTO,
                    dat.dtos.CandidateDTO.class
            );
        }
    }

    @Override
    public
    void
    update(
            io.javalin.http.Context ctx
    ) {
        java.lang.Long          id              = null;
        dat.dtos.CandidateDTO   candidateDTO    = null;
        try {
            id = ctx.pathParamAsClass(
                    "id",
                    java.lang.Long.class
            ).check(
                    this::validatePrimaryKey,
                    "Not a valid id"
            ).get();

            candidateDTO = dao.update(
                    id,
                    validateEntity(ctx)
            );
        }  catch (
                dat.exceptions.ApiException ex
        ) {
            new dat.controllers.impl.ExceptionController().apiExceptionHandler(
                    ex ,
                    ctx
            );
        }
        catch (
                java.lang.Exception ex
        ) {
            new dat.controllers.impl.ExceptionController().exceptionHandler(
                    ex ,
                    ctx
            );
        }

        if (
                id              == null ||
                candidateDTO    == null
        )   {
            ctx.res().setStatus(
                    502
            );
        }   else    {
            ctx.res().setStatus(
                    200
            );

            ctx.json(
                    candidateDTO,
                    dat.dtos.CandidateDTO.class
            );
        }
    }

    @Override
    public
    void
    delete(
            io.javalin.http.Context ctx
    ) {
        java.lang.Long id   =   null;

        try {
            id = ctx.pathParamAsClass(
                    "id",
                    java.lang.Long.class
            ).check(
                    this::validatePrimaryKey,
                    "Not a valid id"
            ).get();

            dao.delete(
                    id
            );
        } catch (
                dat.exceptions.ApiException ex
        ) {
            new dat.controllers.impl.ExceptionController().apiExceptionHandler(
                    ex ,
                    ctx
            );
        }
        catch (
                java.lang.Exception ex
        ) {
            new dat.controllers.impl.ExceptionController().exceptionHandler(
                    ex ,
                    ctx
            );
        }

        if(
                id == null
        )   {
            ctx.res().setStatus(
                    502
            );
        }   else    {
            ctx.res().setStatus(
                    204
            );
        }



    }

    @Override
    public
    boolean
    validatePrimaryKey(
            java.lang.Long id
    ) {
        return dao.validatePrimaryKey(
                id
        );
    }

    @Override
    public
    dat.dtos.CandidateDTO
    validateEntity(
            io.javalin.http.Context ctx
    ) {
        return ctx.bodyValidator(
                dat.dtos.CandidateDTO.class
                ).check(
                        c -> c.name() != null || c.name().equals(""),
                        "Name must be set"
                ).check(
                                c -> c.phone() != null || c.phone().equals(""),
                                "Phone must be set"
                ).check(
                                        c -> c.education() != null || c.education().equals(""),
                                        "Education must be set"
                ).get();
    }

    public
    void
    addSkillToCandidate(
            io.javalin.http.Context ctx
    ) {
        java.lang.Long          candidateId     =   null;
        java.lang.Long          skillId         =   null;
        dat.dtos.CandidateDTO   candidateDTO    =   null;
        try  {
            candidateId = ctx.pathParamAsClass(
                    "candidateId",
                    java.lang.Long.class
            ).check(
                    this::validatePrimaryKey,
                    "Not a valid candidate id"
            ).get();

            skillId = ctx.pathParamAsClass(
                    "skillId",
                    java.lang.Long.class
            ).check(
                    id -> dao.validateSkillId(
                            id
                    ),
                    "Not a valid skill id"
            ).get();

            candidateDTO = dao.addSkillToCandidate(
                    candidateId,
                    skillId
            );
        } catch (java.lang.Exception ex){
            new dat.controllers.impl.ExceptionController().exceptionHandler(
                    ex,
                    ctx
            );
        }

        if (
                candidateId     ==  null    ||
                skillId         ==  null    ||
                candidateDTO    ==  null
        )   {
            ctx.res().setStatus(
                    502
            );
        }   else {
            ctx.res().setStatus(
                    200
            );

            ctx.json(
                    candidateDTO,
                    dat.dtos.CandidateDTO.class
            );
        }

        ctx.res().setStatus(
                200
        );

        ctx.json(
                candidateDTO,
                dat.dtos.CandidateDTO.class
        );
    }

    // In CandidateController
    public
    void
    filterByCategory(
            io.javalin.http.Context ctx
    ) {
        java.lang.String                        category    = null;
        java.util.List<dat.dtos.CandidateDTO>   filtered    = new java.util.ArrayList<>();

        try {
            category = ctx.queryParam(
                    "category"
            );

            filtered = dao.filterByCategory(
                    dat.entities.SkillCategory.valueOf(
                            category.toUpperCase()
                    )
            );

        } catch (
                java.lang.Exception ex
        ){
            new dat.controllers.impl.ExceptionController().exceptionHandler(
                    ex,
                    ctx
            );
        }

        if (
                filtered            == null ||
                filtered.isEmpty()          ||
                category            == null
        ) {
            ctx.res().setStatus(
                    502
            );
        } else {
            ctx.res().setStatus(
                    200
            );
            ctx.json(
                    filtered,
                    dat.dtos.CandidateDTO.class
            );
        }
    }

    public
    void
    populate(
            io.javalin.http.Context ctx
    ) {
        dao.populate(
                dat.config.Populate.getCandidates()
        );

        ctx.res().setStatus(
                200
        );

        ctx.json(
                "{ \"message\": \"Candidates have been populate\" }"
        );
    }
}
