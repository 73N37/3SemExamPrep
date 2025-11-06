package dat.daos;

class SkillDAOTest
{
//TODO=============[Class-scope variables]=============================
    private static jakarta.persistence.EntityManagerFactory emf;
    private static dat.daos.impl.SkillDAO                   skillDAO;

//TODO=============[Test setup methods]=============================
    @org.junit.jupiter.api.BeforeAll
    static
    void
    setUpAll
            ()
    {
        emf         = dat.config.HibernateConfig.getEntityManagerFactoryForTest();
        skillDAO    = dat.daos.impl.SkillDAO.getInstance(emf);
    }

    @org.junit.jupiter.api.BeforeEach
    void
    setUp
            ()
    {
        try (jakarta.persistence.EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();

            em.createQuery(
                    "DELETE FROM Candidate"
            ).executeUpdate();

            em.createQuery(
                    "DELETE FROM dat.entities.Skill"
            ).executeUpdate();

            em.getTransaction().commit();
        }
    }

    @org.junit.jupiter.api.AfterAll
    static
    void
    tearDownAll
            ()
    {
        if (emf != null)
        {
            emf.close();
        }
    }

//TODO=============[Testable methods]=============================
    @org.junit.jupiter.api.Test
    @org.junit.jupiter.api.Order
            (1)
    void
    testGetInstance
            ()
    {
        dat.daos.impl.SkillDAO newInstance = dat.daos.impl.SkillDAO.getInstance(
                emf
        );

        org.junit.jupiter.api.Assertions.assertEquals(
                skillDAO,
                newInstance
        );
    }

    @org.junit.jupiter.api.Test
    @org.junit.jupiter.api.Order
            (2)
    void 
    testCreateSkill
            ()
    {
    //TODO [Method-scope variable declarations]
        dat.entities.Skill   skill;
        dat.dtos.SkillDTO    dto;
        dat.dtos.SkillDTO    created;
        try
        {
            //TODO [Method-scope variable definitions]
            skill = new dat.entities.Skill(
                    "Java",
                    "java",
                    dat.entities.SkillCategory.PROG_LANG,
                    "Programming language"
            );

            dto = new dat.dtos.SkillDTO(
                    skill
            );

            created = skillDAO.create(
                    dto
            );
        }
        catch (java.lang.Exception ex)
        {
            return;
        }

        //TODO [Assertions]
        org.junit.jupiter.api.Assertions.assertNotNull(
                created.getId()
        );

        org.junit.jupiter.api.Assertions.assertEquals(
                "Java",
                created.getName()
        );

        org.junit.jupiter.api.Assertions.assertEquals(
                "java",
                created.getSlug()
        );

        org.junit.jupiter.api.Assertions.assertEquals(
                dat.entities.SkillCategory.PROG_LANG,
                created.getCategory()
        );
    }

    @org.junit.jupiter.api.Test
    @org.junit.jupiter.api.Order
            (3)
    void
    testReadSkill
            ()
    {
//TODO [Method-scope variable declarations]
        dat.entities.Skill       skill;
        dat.dtos.SkillDTO    dto;
        dat.dtos.SkillDTO    created;
        dat.dtos.SkillDTO    found;
        try
        {
            //TODO [Method-scope variable definitions]
            skill   = new dat.entities.Skill(
                    "Python",
                    "python",
                    dat.entities.SkillCategory.PROG_LANG,
                    "Scripting language"
            );
            
            dto     = new dat.dtos.SkillDTO(
                    skill
            );
            
            created = skillDAO.create(
                    dto
            );
            
            found   = skillDAO.read(
                    created.getId()
            );
        }
        catch (java.lang.Exception ex)
        {
            return;
        }
        
        //TODO [Assertions]
        org.junit.jupiter.api.Assertions.assertNotNull(
                found
        );

        org.junit.jupiter.api.Assertions.assertEquals(
                created.getId(),
                found.getId()
        );

        org.junit.jupiter.api.Assertions.assertEquals(
                created.getName(),
                found.getName()
        );

        org.junit.jupiter.api.Assertions.assertEquals(
                created.getCategory(),
                found.getCategory()
        );
    }

    @org.junit.jupiter.api.Test
    @org.junit.jupiter.api.Order
            (4)
    void
    testUpdateSkill
            () 
    {
        //TODO [Method-scope variable declarations]
        dat.entities.Skill       skill;
        dat.dtos.SkillDTO    dto;
        dat.dtos.SkillDTO    created;
        dat.dtos.SkillDTO    updateDTO;
        dat.dtos.SkillDTO    updated;
        try
        {
            // TODO [Method-scope variable definiations]
            skill       = new dat.entities.Skill(
                    "PostgreSQL",
                    "postgresql",
                    dat.entities.SkillCategory.DB,
                    "Database"
            );
            
            dto         = new dat.dtos.SkillDTO(
                    skill
            );
            
            created     = skillDAO.create(
                    dto
            );
            
            updateDTO   = new dat.dtos.SkillDTO(
                    created.getId(),
                    "PostgreSQL Advanced",
                    "postgresql",
                    skill.getCategory(),
                    "Advanced database management"
            );
            
            updated     = skillDAO.update(
                    created.getId(),
                    updateDTO
            );
        }
        catch (java.lang.Exception ex)
        {
            return;
        }

        // TODO [Assertions]
        // created assertions
        org.junit.jupiter.api.Assertions.assertEquals(
                "PostgreSQL",
                created.getName()
        );
        
        org.junit.jupiter.api.Assertions.assertEquals(
                "postgresql",
                created.getSlug()
        );
        
        org.junit.jupiter.api.Assertions.assertEquals(
                dat.entities.SkillCategory.DB,
                created.getCategory()
        );
        
        org.junit.jupiter.api.Assertions.assertEquals(
                "Database",
                created.getDescription()
        );


        //updated assertions
        org.junit.jupiter.api.Assertions.assertEquals(
                "PostgreSQL Advanced",
                updated.getName()
        );

        org.junit.jupiter.api.Assertions.assertEquals(
                "postgresql",
                updated.getSlug()
        );

        org.junit.jupiter.api.Assertions.assertEquals(
                dat.entities.SkillCategory.DB,
                updated.getCategory()
        );
        
        org.junit.jupiter.api.Assertions.assertEquals(
                "Advanced database management",
                updated.getDescription()
        );


        //relation assertions
        org.junit.jupiter.api.Assertions.assertEquals(
                created.getId(),
                updated.getId()
        );

        org.junit.jupiter.api.Assertions.assertNotEquals(
                created.getName(),
                updated.getName()
        );

        org.junit.jupiter.api.Assertions.assertEquals(
                created.getSlug(),
                updated.getSlug()
        );

        org.junit.jupiter.api.Assertions.assertEquals(
                created.getCategory(),
                updated.getCategory()
        );

        org.junit.jupiter.api.Assertions.assertNotEquals(
                created.getDescription(),
                updated.getDescription()
        );
    }

    @org.junit.jupiter.api.Test
    @org.junit.jupiter.api.Order
            (6)
    void 
    testDeleteSkill
            ()
    {   //TODO [Method-scope variable declarations]
        dat.entities.Skill  skill;
        dat.dtos.SkillDTO   dto;
        dat.dtos.SkillDTO   created;

        try
        {   //TODO [Method-scop variable definitions]
            skill = new dat.entities.Skill(
                    "MongoDB",
                    "mongodb",
                    dat.entities.SkillCategory.DB,
                    "NoSQL database"
            );

            dto = new dat.dtos.SkillDTO(
                    skill
            );

            created = skillDAO.create(
                    dto
            );

            //TODO Actual test
            skillDAO.delete(
                    created.getId()
            );
            // Should return null since the entry with 'created.getId()' should be deleted
            org.junit.jupiter.api.Assertions.assertNull(
                    skillDAO.read(
                            created.getId()
                    )
            );
        }
        catch (java.lang.Exception ex)
        {
            return;
        }
    }

    @org.junit.jupiter.api.Test
    @org.junit.jupiter.api.Order
            (7)
    void
    testGetAllSkills
            ()
    {
        try
        {
            // Creating 3 database entries
            skillDAO.create(
                    new dat.dtos.SkillDTO(
                            new dat.entities.Skill(
                                    "Java",
                                    "java",
                                    dat.entities.SkillCategory.PROG_LANG,
                                    "Programming language")
                    )
            );

            skillDAO.create(
                    new dat.dtos.SkillDTO(
                            new dat.entities.Skill(
                                    "Docker",
                                    "docker",
                                    dat.entities.SkillCategory.DEVOPS,
                                    "Containerization")
                    )
            );

            skillDAO.create(
                    new dat.dtos.SkillDTO(
                            new dat.entities.Skill(
                                    "React",
                                    "react",
                                    dat.entities.SkillCategory.FRONTEND,
                                    "UI library")
                    )
            );

            // Retrieving all databse entries
            java.util.List<dat.dtos.SkillDTO> all = skillDAO.readAll();

            // Asserting that the amount of entries in the database is 3
            org.junit.jupiter.api.Assertions.assertEquals(
                    3,
                    all.size()
            );
        }
        catch
        (java.lang.Exception ex)
        {
            return;
        }
    }

    @org.junit.jupiter.api.Test
    @org.junit.jupiter.api.Order
            (8)
    void
    testReadSkillsByCategory
            ()
    {
        try
        {
            skillDAO.create(
                    new dat.dtos.SkillDTO(
                            new dat.entities.Skill(
                                    "Java",
                                    "java",
                                    dat.entities.SkillCategory.PROG_LANG,
                                    "Programming")
                    )
            );

            skillDAO.create(
                    new dat.dtos.SkillDTO(
                            new dat.entities.Skill(
                                    "Python",
                                    "python",
                                    dat.entities.SkillCategory.PROG_LANG,
                                    "Programming")
                    )
            );

            skillDAO.create(
                    new dat.dtos.SkillDTO(
                            new dat.entities.Skill(
                                    "PostgreSQL",
                                    "postgresql",
                                    dat.entities.SkillCategory.DB,
                                    "Database")
                    )
            );

            java.util.List<dat.dtos.SkillDTO> progLangSkills = skillDAO.readByCategory(
                    dat.entities.SkillCategory.PROG_LANG
            );

            org.junit.jupiter.api.Assertions.assertEquals(
                    2,
                    progLangSkills.size()
            );

            org.junit.jupiter.api.Assertions.assertTrue(
                    progLangSkills.stream().allMatch(
                            s -> s.getCategory() == dat.entities.SkillCategory.PROG_LANG
                    )
            );
        }
        catch (java.lang.Exception ex)
        {
            return;
        }
    }

    @org.junit.jupiter.api.Test
    @org.junit.jupiter.api.Order
            (9)
    void
    testReadSkillBySlug
            ()
    {
        try
        {
            dat.entities.Skill skill = new dat.entities.Skill(
                    "Spring Boot",
                    "spring-boot",
                    dat.entities.SkillCategory.FRAMEWORK,
                    "Java framework"
            );

            dat.dtos.SkillDTO created = skillDAO.create(
                    new dat.dtos.SkillDTO(
                            skill
                    )
            );

            java.util.List<dat.dtos.SkillDTO> found = skillDAO.readBySlug(
                    "spring-boot"
            );

            org.junit.jupiter.api.Assertions.assertNotNull(
                    found
            );

            org.junit.jupiter.api.Assertions.assertEquals(
                    "Spring Boot",
                    found.get(
                            0
                    ).getName()
            );

            org.junit.jupiter.api.Assertions.assertEquals(
                    created.getId(),
                    found.get(
                            0
                    ).getId()
            );
        }
        catch (java.lang.Exception ex)
        {
            return;
        }

    }

    @org.junit.jupiter.api.Test
    @org.junit.jupiter.api.Order
            (10)
    void
    testReadNonExistentSkill
            ()
    {
        try
        {
            org.junit.jupiter.api.Assertions.assertNull(
                    skillDAO.read(99999L)       // 'L' is to tell the compiler that this is NOT an integer, but a Long
            );
        }
        catch (java.lang.Exception ex)
        {
            return;
        }

    }
}
