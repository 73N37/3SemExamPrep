package dat.controllers.impl;

public 
class
CandidateController
        implements dat.controllers.IController< dat.dtos.CandidateDTO,
                                                java.lang.Long          >
{
    private final dat.daos.impl.CandidateDAO    candidateDAO;
    private final dat.daos.impl.SkillDAO        skillDAO;

    public 
    CandidateController()
    {
        jakarta.persistence.EntityManagerFactory emf    = dat.config.HibernateConfig.getEntityManagerFactory();
        
        this.candidateDAO   = dat.daos.impl.CandidateDAO.getInstance(
                emf
        );

        this.skillDAO       = dat.daos.impl.SkillDAO.getInstance(
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

            candidateDTO = candidateDAO.read(
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
                    candidateDTO.getClass()
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
            candidateDTOs = candidateDAO.readAll();
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
                    candidateDTOs.getClass()
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

            candidateDTO = candidateDAO.create(
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
                    candidateDTO.getClass()
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

            candidateDTO = candidateDAO.update(
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

            candidateDAO.delete(
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
        return candidateDAO.validatePrimaryKey(
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
                    id -> candidateDAO.validateSkillId(
                            id
                    ),
                    "Not a valid skill id"
            ).get();

            candidateDTO = candidateDAO.addSkillToCandidate(
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
                    candidateDTO.getClass()
            );
        }
    }

    public
    void
    filterByCategory(
            io.javalin.http.Context ctx
    ) {
        java.lang.String                        category    =   null;
        java.util.List<dat.dtos.CandidateDTO>   filtered    =   new java.util.ArrayList();
        java.util.List<dat.dtos.CandidateDTO>   unfiltered  =   new java.util.ArrayList();

        try {
            category = ctx.queryParam("category");

            if (
                    category == null    ||
                    category.isEmpty()
            ) {
                ctx.status(400);
                ctx.json("{\"msg\": \"Category parameter is required\"}");
                return;
            }

            unfiltered = candidateDAO.readAll();

            for (
                    dat.dtos.CandidateDTO candidate : unfiltered
            ) {
                java.util.Set<dat.dtos.SkillDTO> unfilteredSkills = candidate.skills();
                for (
                        dat.dtos.SkillDTO skill : unfilteredSkills
                ) {
                    if (
                            skill.category().toString().equals(
                                    category
                            )
                    ) {
                        filtered.add(candidate);
                    }
                }
            }



            if (
                    filtered == null    ||
                    filtered.isEmpty()
            ) {
                ctx.status(404);
                ctx.json("{\"msg\": \"No candidates found with skills in category: " + category + "\"}");
                return;
            }

            ctx.status(
                    200
            );

            ctx.json(
                    filtered,
                    dat.dtos.CandidateDTO.class
            );

        } catch (
                IllegalArgumentException ex
        ) {
            ctx.status(400);
            ctx.json("{\"msg\": \"Invalid category: " + category + "\"}");
        } catch (
                java.lang.Exception ex
        ) {
            new dat.controllers.impl.ExceptionController().exceptionHandler(ex, ctx);
        }
    }

    public
    void
    getSkillByCandidate(
            io.javalin.http.Context ctx
    ) {
        java.lang.Long                          candidateId =   null;
        java.lang.Long                          skillId     =   null;
        dat.dtos.CandidateDTO                   candidate;
        dat.dtos.SkillDTO                       skill;

        try {
            candidateId =   ctx.pathParamAsClass(
                    "candidateId",
                    java.lang.Long.class
            ).check(
                    this::validatePrimaryKey,
                    "Not a valid primary key"
            ).get();

            skillId     =   ctx.pathParamAsClass(
                    "skillId",
                    java.lang.Long.class
            ).check(
                    this::validatePrimaryKey,
                    "Not valid primary key"
            ).get();

            dat.entities.Skill      skillEntity;
            skill       =   skillDAO.read(
                    skillId
            );
            skillEntity     =   new dat.entities.Skill(
                    skill
            );
            //dat.dtos.

            dat.entities.Candidate  candidateEntity;
            candidate   =   candidateDAO.read(
                    candidateId
            );
            candidateEntity =   new dat.entities.Candidate(
                    candidate
            );

            //candidateEntity.addSkill(skillEntity);

            candidate = new dat.dtos.CandidateDTO(candidateEntity);

            if  (
                    candidate       ==  null    ||
                    candidateEntity ==  null    ||
                    skill           ==  null    ||
                    skillEntity     ==  null
            )   {
                ctx.res().setStatus(
                        502
                );
            }   else    {
                ctx.res().setStatus(
                        200
                );
                ctx.json(
                        skill,
                        skill.getClass()
                );
            }


        }   catch (
                IllegalArgumentException ex
        )   {
            ctx.status(
                    400
            );
            ctx.json("{\"msg\": \"Invalid candidateId: " + candidateId + " or skillId: "+ skillId + "\"}");
        }   catch (
                dat.exceptions.ApiException ex
        )   {
            new dat.controllers.impl.ExceptionController().apiExceptionHandler(
                    ex,
                    ctx
            );
        }   catch (
                java.lang.Exception ex
        )   {
            new dat.controllers.impl.ExceptionController().exceptionHandler(
                    ex,
                    ctx
            );
        }
    }

    public
    void
    getAllSkillsByCandidate(
            io.javalin.http.Context ctx
    ) {
        java.lang.Long                      candidateId =   null;
        dat.dtos.CandidateDTO               candidate;
        java.util.List<dat.dtos.SkillDTO>   skills      =   new java.util.ArrayList();

        try {
            candidateId =   ctx.pathParamAsClass(
                    "candidateId",
                    java.lang.Long.class
            ).check(this::validatePrimaryKey,
                    "Invalid primary Key"
            ).get();

            candidate   =   candidateDAO.read(
                    candidateId
            );

            dat.entities.Candidate  candidateEntity =   new dat.entities.Candidate(
                    candidate
            );

            for (
                    dat.entities.Skill skill : candidateEntity.getSkills()
            )   {
                if (
                        skill == null
                ) continue;
                skills.add(new dat.dtos.SkillDTO(
                        skill
                ));
            }

            if (
                    skills == null  ||
                    skills.isEmpty()
            )   {
                ctx.res().setStatus(
                        502
                );
            }   else    {
                ctx.res().setStatus(
                        200
                );
                ctx.json(
                        skills,
                        skills.getClass()
                );
            }

        }   catch (
                IllegalArgumentException ex
        )   {
            ctx.status(
                    400
            );
            ctx.json("{\"msg\": \"Invalid candidateId: " + candidateId + "\"}");
        }   catch (
                dat.exceptions.ApiException ex
        )   {
            new dat.controllers.impl.ExceptionController().apiExceptionHandler(
                    ex,
                    ctx
            );
        }   catch (
                java.lang.Exception ex
        )   {
            new dat.controllers.impl.ExceptionController().exceptionHandler(
                    ex,
                    ctx
            );
        }
    }

    public
    void
    deleteSkillByCandidate(
            io.javalin.http.Context ctx
    ) {
        java.lang.Long  candidateId =   null;
        java.lang.Long  skillId     =   null;

        try {
            candidateId =   ctx.pathParamAsClass(
                    "candidateId",
                    java.lang.Long.class
            ).check(this::validatePrimaryKey,
                    "Invalid primary key"
            ).get();

            skillId     =   ctx.pathParamAsClass(
                    "skillId",
                    java.lang.Long.class
            ).check(this::validatePrimaryKey,
                    "Invalid primary ket"
            ).get();

           dat.dtos.CandidateDTO                candidateDTO        =   candidateDAO.read(
                   candidateId
           );
           dat.entities.Candidate               candidateEntity     =   new dat.entities.Candidate(
                   candidateDTO
           );


           for (
                   dat.entities.Skill skill : candidateEntity.getSkills()
           )    {
               if   (
                       skill.getId() == skillId
               )    candidateEntity.removeSkill(skill);

           }

           skillDAO.delete(
                   skillId
           );

           candidateDAO.update(
                   candidateId,
                   new dat.dtos.CandidateDTO(
                           candidateEntity
                   )
           );

            if  (
                    candidateId     ==  null    ||
                    skillId         ==  null
            )   {
                ctx.res().setStatus(
                        502
                );
            }   else    {
                ctx.res().setStatus(
                        204
                );
            }

        }  catch (
                IllegalArgumentException ex
        )   {
            ctx.status(
                    400
            );
            ctx.json("{\"msg\": \"Invalid candidateId: " + candidateId + "\"}");
        }   catch (
                dat.exceptions.ApiException ex
        )   {
            new dat.controllers.impl.ExceptionController().apiExceptionHandler(
                    ex,
                    ctx
            );
        }   catch (
                java.lang.Exception ex
        )   {
            new dat.controllers.impl.ExceptionController().exceptionHandler(
                    ex,
                    ctx
            );
        }
    }

    public
    void
    populate(
            io.javalin.http.Context ctx
    ) {
        candidateDAO.populate(
                dat.config.Populate.populateCandidates()
        );

        ctx.res().setStatus(
                200
        );

        ctx.json(
                "{ \"message\": \"Candidates have been populate\" }"
        );
    }
}