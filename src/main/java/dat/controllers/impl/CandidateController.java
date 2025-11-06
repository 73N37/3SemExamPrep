package dat.controllers.impl;

public 
class
CandidateController
        implements dat.controllers.IController< dat.dtos.CandidateDTO,
                                                java.lang.Long          >
{
    private final dat.daos.impl.CandidateDAO dao;

    public 
    CandidateController
            ()
    {
        jakarta.persistence.EntityManagerFactory emf    = dat.config.HibernateConfig.getEntityManagerFactory();
        
        this.dao                                        = dat.daos.impl.CandidateDAO.getInstance(
                emf
        );
    }

    @Override
    public 
    void 
    read
            (
                    io.javalin.http.Context ctx
            )   throws dat.exceptions.ApiException
    {
        java.lang.Long id =     ctx.pathParamAsClass(
                "id",
                java.lang.Long.class
                ).check(
                        this::validatePrimaryKey,
                        "Not a valid id"
                ).get();

        dat.dtos.CandidateDTO candidateDTO = dao.read(
                id
        );

        ctx.res().setStatus(
                200
        );

        ctx.json(
                candidateDTO,
                dat.dtos.CandidateDTO.class
        );
    }

    @Override
    public
    void
    readAll
            (
                    io.javalin.http.Context ctx
            )   throws dat.exceptions.ApiException
    {
        java.util.List<dat.dtos.CandidateDTO> candidateDTOs = dao.readAll();

        ctx.res().setStatus(
                200
        );

        ctx.json(
                candidateDTOs,
                dat.dtos.CandidateDTO.class
        );
    }

    @Override
    public
    void
    create
            (
                    io.javalin.http.Context ctx
            )   throws dat.exceptions.ApiException
    {
        dat.dtos.CandidateDTO jsonRequest = validateEntity(
                ctx
        );

        dat.dtos.CandidateDTO candidateDTO = dao.create(
                jsonRequest
        );

        ctx.res().setStatus(
                201
        );

        ctx.json(
                candidateDTO,
                dat.dtos.CandidateDTO.class
        );
    }

    @Override
    public
    void
    update
            (
                    io.javalin.http.Context ctx
            )   throws dat.exceptions.ApiException
    {
        Long id = ctx.pathParamAsClass(
                    "id",
                    java.lang.Long.class
                ).check(
                        this::validatePrimaryKey,
                        "Not a valid id"
                ).get();

        dat.dtos.CandidateDTO candidateDTO = dao.update(
                id,
                validateEntity(ctx)
        );

        ctx.res().setStatus(
                200
        );

        ctx.json(
                candidateDTO,
                dat.dtos.CandidateDTO.class
        );
    }

    @Override
    public
    void
    delete
            (
                    io.javalin.http.Context ctx
            )   throws dat.exceptions.ApiException
    {
        Long id = ctx.pathParamAsClass(
                    "id",
                    java.lang.Long.class
                ).check(
                        this::validatePrimaryKey,
                        "Not a valid id"
                ).get();

        dao.delete(
                id
        );

        ctx.res().setStatus(
                204
        );
    }

    @Override
    public
    boolean
    validatePrimaryKey
            (
                    java.lang.Long id
            )
    {
        return dao.validatePrimaryKey(
                id
        );
    }

    @Override
    public
    dat.dtos.CandidateDTO
    validateEntity
            (
                    io.javalin.http.Context ctx
            )
    {
        return ctx.bodyValidator(
                dat.dtos.CandidateDTO.class
                ).check(
                        c -> c.getName() != null && !c.getName().isEmpty(),
                        "Name must be set").check(
                                c -> c.getPhone() != null && !c.getPhone().isEmpty(),
                                "Phone must be set").check(
                                        c -> c.getEducation() != null && !c.getEducation().isEmpty(),
                                        "Education must be set").get();
    }

    public
    void
    addSkillToCandidate
            (
                    io.javalin.http.Context ctx
            )
    {
        Long candidateId = ctx.pathParamAsClass(
                "candidateId",
                java.lang.Long.class
                ).check(
                        this::validatePrimaryKey,
                        "Not a valid candidate id"
                ).get();

        Long skillId = ctx.pathParamAsClass(
                "skillId",
                java.lang.Long.class
                ).check(
                        id -> dao.validateSkillId(id),
                        "Not a valid skill id").get();

        dat.dtos.CandidateDTO candidateDTO = dao.addSkillToCandidate(
                candidateId,
                skillId
        );

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
    filterByCategory
            (
                    io.javalin.http.Context ctx
            )   throws dat.exceptions.ApiException
    {
        String category = ctx.queryParam(
                "category"
        );

        if (
                category == null
        )
        {
            readAll(ctx);
            return;
        }

        java.util.List<dat.dtos.CandidateDTO> filtered = dao.filterByCategory(
                dat.entities.SkillCategory.valueOf(
                        category.toUpperCase()
                )
        );

        ctx.res().setStatus(
                200
        );

        ctx.json(
                filtered,
                dat.dtos.CandidateDTO.class
        );
    }

    public
    void
    populate
            (
                    io.javalin.http.Context ctx
            )
    {
        dao.populate();
        ctx.res().setStatus(
                200
        );
        ctx.json(
                "{ \"message\": \"Candidates have been populate\" }"
        );
    }
}
