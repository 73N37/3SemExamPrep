package dat.controllers.impl;

public 
class
SkillController 
        implements dat.controllers.IController< dat.dtos.SkillDTO, 
                                                java.lang.Long>
{

    private final dat.daos.impl.SkillDAO dao;

    public 
    SkillController()
    {
        jakarta.persistence.EntityManagerFactory emf    = dat.config.HibernateConfig.getEntityManagerFactory();
        this.dao                                        = dat.daos.impl.SkillDAO.getInstance(emf);
    }

    @Override
    public 
    void
    read(
            io.javalin.http.Context ctx
    )
    {
        java.lang.Long      id          = null;
        dat.dtos.SkillDTO   skillDTO    = null;

        try {
            id      =   ctx.pathParamAsClass(
                            "id",
                            java.lang.Long.class).check(
                                    this::validatePrimaryKey,
                                    "Not a valid id (Primary Key)"
                            ).get();

            skillDTO =  dao.read(
                            Long.valueOf(
                                    id
                            )
            );
        } catch (
                dat.exceptions.ApiException ex
        ) {
            new dat.controllers.impl.ExceptionController().apiExceptionHandler(
                    ex,
                    ctx
            );
        } catch (
                java.lang.Exception ex
        ) {
            new dat.controllers.impl.ExceptionController().exceptionHandler(
                    ex,
                    ctx
            );
        }

        if (
                skillDTO    == null ||
                id          == null
        ) {
            ctx.res().setStatus(
                    502
            );
        } else {
            ctx.res().setStatus
                    (
                            200
                    );

            ctx.json(
                    skillDTO,
                    dat.dtos.SkillDTO.class
            );
        }
    }

    @Override
    public
    void
    readAll(
            io.javalin.http.Context ctx
    ) {
        java.util.List<dat.dtos.SkillDTO> skillDTOs = null;

        try {
            skillDTOs = dao.readAll();
        } catch (
                dat.exceptions.ApiException ex
        ) {
            new dat.controllers.impl.ExceptionController().apiExceptionHandler(
                    ex,
                    ctx
            );
        } catch (
                java.lang.Exception ex
        ) {
            new dat.controllers.impl.ExceptionController().exceptionHandler(
                    ex,
                    ctx
            );
        }

        if (
                skillDTOs == null ||
                skillDTOs.isEmpty()
        ) {
            ctx.res().setStatus(
                    502
            );
        } else {
            ctx.res().setStatus
                    (
                            200
                    );

            ctx.json(
                    skillDTOs,
                    dat.dtos.SkillDTO.class
            );
        }
    }

    @Override
    public
    void
    create(
            io.javalin.http.Context ctx
    ) {
        dat.dtos.SkillDTO jsonRequest   = validateEntity(ctx);
        dat.dtos.SkillDTO skillDTO      = null;

        try {
            skillDTO = dao.create(
                    jsonRequest
            );
        } catch (
                dat.exceptions.ApiException ex
        )   {
            new dat.controllers.impl.ExceptionController().apiExceptionHandler(
                    ex,
                    ctx
            );
        } catch (
                java.lang.Exception ex
        )   {
            new dat.controllers.impl.ExceptionController().exceptionHandler(
                    ex,
                    ctx
            );
        }

        if (
                skillDTO == null ||
                jsonRequest == null
        )   {
            ctx.res().setStatus(
                    502
            );
        }   else {
            ctx.res().setStatus(
                    201
            );

            ctx.json(
                    skillDTO,
                    dat.dtos.SkillDTO.class
            );
        }
    }

    @Override
    public
    void
    update(
            io.javalin.http.Context ctx
    ) {
        java.lang.Long      id          = null;

        dat.dtos.SkillDTO   skillDTO    = null;

        try
        {
            id = ctx.pathParamAsClass(
                    "id",
                    java.lang.Long.class).check(
                            this::validatePrimaryKey,
                    "Not a valid id"
                    ).get();

            skillDTO = dao.update(
                            Long.valueOf(
                                    id
                            ),
                            validateEntity(
                                    ctx
                            )
                    );
        } catch (
                dat.exceptions.ApiException ex
        ) {
            new dat.controllers.impl.ExceptionController().apiExceptionHandler(
                    ex,
                    ctx
            );
        } catch (
                java.lang.Exception ex
        ) {
            new dat.controllers.impl.ExceptionController().exceptionHandler(
                    ex,
                    ctx
            );
        }

        if (
                skillDTO    == null ||
                id          == null
        ) {
            ctx.res().setStatus(
                    502
            );
        } else {
            ctx.res().setStatus(
                    200
            );
            ctx.json(
                    skillDTO,
                    dat.dtos.SkillDTO.class
            );
        }
    }

    @Override
    public
    void
    delete(
            io.javalin.http.Context ctx
    ) {
        java.lang.Long id = null;
        try
        {
            id = ctx.pathParamAsClass(
                    "id",
                    java.lang.Long.class).check(
                            this::validatePrimaryKey,
                    "Not a valid id"
                    ).get();

            dao.delete(
                    Long.valueOf(
                            id
                    )
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
                id == null
        ) {
            ctx.res().setStatus(
                    502
            );
        } else {
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
        return dao.validatePrimaryKey(id);
    }

    @Override
    public
    dat.dtos.SkillDTO
    validateEntity(
            io.javalin.http.Context ctx
    ) {
        return ctx.bodyValidator
                        (
                                dat.dtos.SkillDTO.class
                        ).check(s -> s.name()        != null, "Skill name must be set")
                         .check(s -> s.slug()        != null, "Skill slug must be set")
                         .check(s -> s.category()    != null, "Skill category must be set")
                         .check(s -> s.description() != null, "Skill description must be set")
                         .get();
    }

    public
    void
    populate(
            io.javalin.http.Context ctx
    ) {
        dao.populate(
                dat.config.Populate.populateSkills()
        );

        ctx.res().setStatus(
                200
        );

        ctx.json(
                "{ \"message\": \"Candidates have been populate\" }"
        );
    }
}