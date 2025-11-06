package dat.daos.impl;

@lombok.NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public
class
SkillDAO
        implements dat.daos.IDAO<   dat.dtos.SkillDTO, 
                                    java.lang.Long> 
{

    private static SkillDAO                                 instance;
    private static jakarta.persistence.EntityManagerFactory emf;
    
    /*
    001100
    111111
    110011
     */

    public
    static
    SkillDAO
    getInstance
            (
                    @org.jetbrains.annotations.NotNull
                    jakarta.persistence.EntityManagerFactory _emf
            )
    {
        if (instance == null)
        {
            emf         = _emf;
            instance    = new SkillDAO();
        }
        return instance;
    }

    /*
    001100
    111111
    110011
     */

    @Override
    public
    dat.dtos.SkillDTO
    read
            (
                    @org.jetbrains.annotations.NotNull
                    java.lang.Long id
            )   throws dat.exceptions.ApiException
    {
        try
                (
                        jakarta.persistence.EntityManager em = emf.createEntityManager()
                )
        {
            dat.entities.Skill skill = em.find
                    (
                            dat.entities.Skill.class,
                            id
                    );
            if
            (
                    skill != null
            )
            {
                return new dat.dtos.SkillDTO(skill);
            }
            else
            {
                throw new dat.exceptions.ApiException
                        (
                                502,
                                "Was not able to find a database entry with id=["+id+"]\nSkillDAO.read(Long)"
                        );
            }
        }
    }

    /*
    001100
    111111
    110011
     */

    @Override
    public
    java.util.List<dat.dtos.SkillDTO>
    readAll
            () throws dat.exceptions.ApiException
    {
        try
                (
                        jakarta.persistence.EntityManager em = emf.createEntityManager()
                )
        {
            jakarta.persistence.TypedQuery<dat.dtos.SkillDTO> query = em.createQuery
                    (
                            "SELECT new dat.dtos.SkillDTO(s) FROM Skill s",
                            dat.dtos.SkillDTO.class
                    );

            if
            (
                            query.getResultList() != null   ||
                            !query.getResultList().isEmpty()
            )
            {
                return query.getResultList();
            }
            else
            {
                throw new dat.exceptions.ApiException
                        (
                                502,
                                "Was not able to retrieve any database entries\nSkillDAO.readAll()"
                        );
            }
        }
    }

    /*
    001100
    111111
    110011
     */

    public
    java.util.List<dat.dtos.SkillDTO>
    readByCategory
            (
                    @org.jetbrains.annotations.NotNull
                    dat.entities.SkillCategory category
            )   throws dat.exceptions.ApiException
    {
        try
                (
                        jakarta.persistence.EntityManager em = emf.createEntityManager()
                )
        {
            jakarta.persistence.TypedQuery<dat.dtos.SkillDTO> query = em.createQuery
                    (
                            "SELECT new dat.dtos.SkillDTO(s) " +
                               "FROM Skill s " +
                               "WHERE s.category = :category",
                                dat.dtos.SkillDTO.class
                    );
            query.setParameter
                    (
                            "category",
                                category
                    );
            if
            (
                    query.getResultList() != null ||
                    !query.getResultList().isEmpty()
            )
            {
                return query.getResultList();
            }
            else throw new dat.exceptions.ApiException
                    (
                            502,
                            "Was not able to retrieve any database entries with category=[ " + category.toString() + " ]\nSkillDAO.readByCategory(SkillCategory)"
                    );
        }
    }

    /*
    001100
    111111
    110011
     */

    public
    java.util.List<dat.dtos.SkillDTO>
    readBySlug
            (
                    @org.jetbrains.annotations.NotNull
                    java.lang.String slug
            )   throws dat.exceptions.ApiException
    {
        java.util.List<dat.dtos.SkillDTO> all = readAll();
        if
        (
                    all == null ||
                    all.isEmpty()
        ) throw new dat.exceptions.ApiException
                (
                        502,
                        "Was not able to retrieve any database entries\nSkillDAO.readBySlug(String)"
                );

        return all.stream()
                .filter(index -> slug == null ? index.getSlug() == null : slug.equals(index.getSlug()))
                .toList();
    }

    /*
    001100
    111111
    110011
     */

    @Override
    public
    dat.dtos.SkillDTO
    create
            (
                    @org.jetbrains.annotations.NotNull
                    dat.dtos.SkillDTO skillDTO
            ) throws dat.exceptions.ApiException
    {
        try
                (
                        jakarta.persistence.EntityManager em = emf.createEntityManager()
                )
        {
            em.getTransaction().begin();

            dat.entities.Skill skill = new dat.entities.Skill
                    (
                            skillDTO
                    );

            try
            {
                em.persist
                        (
                                skill
                        );
                em.getTransaction().commit();
            }
            catch (Exception e)
            {
                throw new dat.exceptions.ApiException
                        (
                                502,
                                "An error happen while trying to perist a entry to the database based on this DTO=[ " + skillDTO.toString() + "]\nSkillDAO.create(SkillDTO)"
                        );
            }

            return new dat.dtos.SkillDTO
                    (
                            skill
                    );
        }
    }

    /*
    001100
    111111
    110011
     */

    @Override
    public
    dat.dtos.SkillDTO
    update
            (
                    @org.jetbrains.annotations.NotNull
                    java.lang.Long id,

                    @org.jetbrains.annotations.NotNull
                    dat.dtos.SkillDTO skillDTO
            ) throws dat.exceptions.ApiException
    {
        try
                (
                        jakarta.persistence.EntityManager em = emf.createEntityManager()
                )
        {
            em.getTransaction().begin();

            dat.entities.Skill skill = em.find
                    (
                            dat.entities.Skill.class,
                            id
                    );

            if (skill != null)
            {
                skill.setName
                        (
                                skillDTO.getName()
                        );

                skill.setCategory
                        (
                                skillDTO.getCategory()
                        );

                skill.setDescription
                        (
                                skillDTO.getDescription()
                        );

                skill.setSlug
                        (
                                skillDTO.getSlug()
                        );

                dat.entities.Skill mergedSkill = em.merge
                        (
                                skill
                        );

                em.getTransaction().commit();

                return new dat.dtos.SkillDTO
                        (
                                mergedSkill
                        );
            }
            em.getTransaction().rollback();
            throw new dat.exceptions.ApiException
                    (
                            502,
                            "Was unable to find a Skill in the database based on [ " + skillDTO.toString() + " ]\nSkillDAO.update(Long,SkillDTO)"
                    );
        }
    }

    /*
    001100
    111111
    110011
     */

    @Override
    public
    void
    delete
            (
                    @org.jetbrains.annotations.NotNull
                    java.lang.Long id
            ) throws dat.exceptions.ApiException
    {
        try
                (
                        jakarta.persistence.EntityManager em = emf.createEntityManager()
                )
        {
            em.getTransaction().begin();

            dat.entities.Skill skill = em.find
                    (
                            dat.entities.Skill.class,
                            id
                    );

            if (skill != null)
            {
                em.remove(skill);
                em.getTransaction().commit();
            }
            else
            {
                throw new dat.exceptions.ApiException
                        (
                                502,
                                "Was unable to find an entry in the database with id="+id+" SkillDAO.delete(Long)"
                        );
            }
        }
    }

    /*
    001100
    111111
    110011
     */

    @Override
    public
    boolean
    validatePrimaryKey
            (
                    @org.jetbrains.annotations.NotNull
                    java.lang.Long id
            )
    {
        // What makes this method unique is that it does not throw an exception,
        // because it is meant to be validated if a database entry exists (based on its PK) in the database
        // to avoid triggering an exception in the other methods withing this class.
        try
                (
                        jakarta.persistence.EntityManager em = emf.createEntityManager()
                )
        {
            dat.entities.Skill skill = em.find
                    (
                            dat.entities.Skill.class,
                            id
                    );
            return (skill != null) ? true : false;
        }
    }

    /*
    001100
    111111
    110011
     */
    
    public 
    void
    populate
            ()
    {
        try
                (
                        jakarta.persistence.EntityManager em = emf.createEntityManager()
                )
        {
            em.getTransaction().begin();

            java.util.Set skills = new java.util.HashSet();

            // Clear existing data from database
            em.createQuery
                    (
                            "DELETE FROM Skill"
                    ).executeUpdate();

            // Create sample skills
            skills.add(new dat.entities.Skill
                    (
                            "Java",
                            "java",
                            dat.entities.SkillCategory.PROG_LANG,
                            "General-purpose programming language"
                    ));


            skills.add(new dat.entities.Skill
                    (
                            "Spring Boot",
                            "spring-boot",
                            dat.entities.SkillCategory.FRAMEWORK,
                            "Java framework for microservices"
                    ));

            skills.add(new dat.entities.Skill
                    (
                            "PostgreSQL",
                            "postgresql",
                            dat.entities.SkillCategory.DB,
                            "Relational database system"
                    ));

            skills.add(new dat.entities.Skill
                    (
                            "Docker",
                            "docker",
                            dat.entities.SkillCategory.DEVOPS,
                            "Container platform"
                    ));

            skills.add(new dat.entities.Skill
                    (
                            "React",
                            "react",
                            dat.entities.SkillCategory.FRONTEND,
                            "JavaScript UI library"
                    ));

            skills.add(new dat.entities.Skill
                    (
                            "JUnit",
                            "junit",
                            dat.entities.SkillCategory.TESTING,
                            "Testing framework for projects build in Java"
                    ));

            skills.add(new dat.entities.Skill
                    (
                            "TensorFlow",
                            "tensorflow",
                            dat.entities.SkillCategory.DATA,
                            "Data science and analytics tool"
                    ));

            skills.add(new dat.entities.Skill
                    (
                            "React",
                            "react",
                            dat.entities.SkillCategory.FRAMEWORK,
                            "Application framework build in NodeJS"
                    ));

            // I changed this since I only use the variable names to persist.
            // I might as well just create a HashSet to avoid duplicates,
            // while avoiding redundant variables, since it can just use a lambda
            // expression to persist every object within my 'skills' HasSet
            skills.stream().forEach( x -> em.persist(x));
            em.getTransaction().commit();
        }
    }
}
