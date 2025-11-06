package dat.controllers.impl;

public 
class
SkillController 
        implements dat.controllers.IController< dat.dtos.SkillDTO, 
                                                java.lang.Long>
{

    private final dat.daos.impl.SkillDAO dao;

    public 
    SkillController
            ()
    {
        jakarta.persistence.EntityManagerFactory emf    = dat.config.HibernateConfig.getEntityManagerFactory();
        this.dao                                        = dat.daos.impl.SkillDAO.getInstance(emf);
    }

    @Override
    public 
    void
    read
            (
                    io.javalin.http.Context ctx
            )
    {
        java.lang.Long  id  =   ctx.pathParamAsClass("id",
                                                     java.lang.Long.class)
                                   .check(this::validatePrimaryKey,
                                         "Not a valid id (Primary Key)")
                                   .get();

        dat.dtos.SkillDTO skillDTO = null;
        try
        {
            skillDTO = dao.read(Long.valueOf(id));

            if (skillDTO == null) throw new java.lang.Exception("skillDTO is not allowed to be null\nSkillController.read(Context)");
        } 
        catch
        (
                dat.exceptions.ApiException ex
        )
        {
            new dat.controllers.impl.ExceptionController().apiExceptionHandler(ex ,ctx);
        }
        catch
        (
                java.lang.Exception ex
        )
        {
            new dat.controllers.impl.ExceptionController().exceptionHandler(ex ,ctx);
        }

        ctx.res().setStatus
                (
                        200
                );

        ctx.json
                (
                        skillDTO,
                        dat.dtos.SkillDTO.class
                );
    }

    @Override
    public
    void
    readAll
            (
                    io.javalin.http.Context ctx
            )
    {
        java.util.List<dat.dtos.SkillDTO> skillDTOs = null;
        try
        {
            skillDTOs = dao.readAll();
            if (skillDTOs == null || skillDTOs.isEmpty()) throw new java.lang.Exception("skillDTOs is not allowed to be null\nSkillController.readAll()");
        }
        catch
        (
                dat.exceptions.ApiException ex
        )
        {
            new dat.controllers.impl.ExceptionController().apiExceptionHandler(ex ,ctx);
        }
        catch
        (
                java.lang.Exception ex
        )
        {
            new dat.controllers.impl.ExceptionController().exceptionHandler(ex ,ctx);
        }

        ctx.res().setStatus
                (
                        200
                );

        ctx.json
                (
                        skillDTOs,
                        dat.dtos.SkillDTO.class
                );
    }

    @Override
    public
    void
    create
            (
                    io.javalin.http.Context ctx
            )
    {
        dat.dtos.SkillDTO jsonRequest   = validateEntity(ctx);
        dat.dtos.SkillDTO skillDTO      = null;

        try
        {
            skillDTO = dao.create(jsonRequest);
            if (skillDTO == null) throw new java.lang.Exception("skillDTO is not allowed to be null\nSkillController.create(Context)");
        }
        catch
        (
                dat.exceptions.ApiException ex
        )
        {
            new dat.controllers.impl.ExceptionController().apiExceptionHandler(ex ,ctx);
        }
        catch
        (
                java.lang.Exception ex
        )
        {
            new dat.controllers.impl.ExceptionController().exceptionHandler(ex ,ctx);
        }

        ctx.res().setStatus
                (
                        201
                );

        ctx.json
                (
                        skillDTO,
                        dat.dtos.SkillDTO.class
                );
    }

    @Override
    public
    void
    update
            (
                    io.javalin.http.Context ctx
            )
    {
        Long id = ctx.pathParamAsClass("id", Long.class)
                .check(this::validatePrimaryKey, "Not a valid id")
                .get();

        dat.dtos.SkillDTO skillDTO = null;

        try
        {
            skillDTO = dao.update
                    (
                            Long.valueOf(id),
                            validateEntity(ctx)
                    );

            if (skillDTO == null) throw new java.lang.Exception("skillDTO is not allowed to be null\nSkillController.update(Context)");
        }
        catch
        (
                dat.exceptions.ApiException ex
        )
        {
            new dat.controllers.impl.ExceptionController().apiExceptionHandler(ex ,ctx);
        }
        catch
        (
                java.lang.Exception ex
        )
        {
            new dat.controllers.impl.ExceptionController().exceptionHandler(ex ,ctx);
        }

        ctx.res().setStatus(200);
        ctx.json(skillDTO, dat.dtos.SkillDTO.class);
    }

    @Override
    public
    void
    delete
            (
                    io.javalin.http.Context ctx
            )
    {
        try
        {
            Long id = ctx.pathParamAsClass("id", Long.class)
                    .check(this::validatePrimaryKey, "Not a valid id")
                    .get();
            dao.delete(Long.valueOf(id));
            if (id == null) throw new java.lang.Exception("id is noty allowed to be null\nSkillController.delete(Context");
        }
        catch
        (
                dat.exceptions.ApiException ex
        )
        {
            new dat.controllers.impl.ExceptionController().apiExceptionHandler(ex ,ctx);
        }
        catch
        (
                java.lang.Exception ex
        )
        {
            new dat.controllers.impl.ExceptionController().exceptionHandler(ex ,ctx);
        }
        ctx.res().setStatus
                (
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
        return dao.validatePrimaryKey(id);
    }

    @Override
    public
    dat.dtos.SkillDTO
    validateEntity
            (
                    io.javalin.http.Context ctx
            )
    {
        return ctx.bodyValidator
                        (
                                dat.dtos.SkillDTO.class
                        ).check(s -> s.getName()        != null && !s.getName().isEmpty(), "Skill name must be set")
                         .check(s -> s.getCategory()    != null, "Skill category must be set")
                         .check(s -> s.getDescription() != null && !s.getDescription().isEmpty(), "Skill description must be set")
                         .get();
    }

    public
    void
    populate
            (
                    io.javalin.http.Context ctx
            )
    {
        dao.populate();
        ctx.res().setStatus
                (
                        200
                );
        ctx.json
                (
                        "{ \"message\": \"Candidates have been populate\" }"
                );
    }
}