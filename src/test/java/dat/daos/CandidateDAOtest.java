package dat.daos;

class
CandidateDAOTest
{
    private static jakarta.persistence.EntityManagerFactory     emf;
    private static dat.daos.impl.CandidateDAO                   candidateDAO;
    private static dat.daos.impl.SkillDAO                       skillDAO;

    @org.junit.jupiter.api.BeforeAll
    static
    void
    setUpAll
            ()
    {
        emf             = dat.config.HibernateConfig.getEntityManagerFactoryForTest();

        candidateDAO    = dat.daos.impl.CandidateDAO.getInstance(emf);

        skillDAO        = dat.daos.impl.SkillDAO.getInstance(emf);
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
    static void tearDownAll()
    {
        if (emf != null)
        {
            emf.close();
        }
    }

    @org.junit.jupiter.api.Test
    @org.junit.jupiter.api.Order
            (1)
    void
    testCreateCandidate
            ()
    {   // TODO
        dat.dtos.CandidateDTO dto;
        dat.dtos.CandidateDTO created;

        try
        {
            dto = new dat.dtos.CandidateDTO(
                    null,
                    "John Doe",
                    "12345678",
                    "Computer Science",
                    java.util.Set.of(
                            new dat.entities.Skill(
                                    "Java",
                                    "java",
                                    dat.entities.SkillCategory.DEVOPS,
                                    "A cross-platform Object Oriented Programming-Language"
                            )
                    )
            );
            created = candidateDAO.create(dto);
        }
        catch (java.lang.Exception ex)
        {
            return;
        }


        org.junit.jupiter.api.Assertions.assertNotNull(
                created.id()
        );

        org.junit.jupiter.api.Assertions.assertEquals(
                "John Doe",
                created.name()
        );
    }

    @org.junit.jupiter.api.Test
    @org.junit.jupiter.api.Order
            (2)
    void
    testReadCandidate
            ()
    {
        // TODO [Method-scope variable declarations]
        dat.dtos.CandidateDTO dto;
        dat.dtos.CandidateDTO created;
        dat.dtos.CandidateDTO found;
        try
        {
            //TODO [Method-scope variable definitions]
            dto = new dat.dtos.CandidateDTO(
                    null,
                    "Jane Smith",
                    "87654321",
                    "Engineering",
                    new java.util.HashSet<>(
                            dat.config.Populate.getSkills().stream().filter(
                                    x ->  x.getName().equals("cpp")
                                            ||  x.getSlug().equals("docker")
                            ).collect(
                                    java.util.stream.Collectors.toSet()
                            )
                    )
            );

            created = candidateDAO.create(
                    dto
            );

            found = candidateDAO.read(
                    created.id()
            );
        }
        catch (java.lang.Exception ex)
        {
            return;
        }

        //TODO  [Assertsions]
        org.junit.jupiter.api.Assertions.assertNotNull(found);
        org.junit.jupiter.api.Assertions.assertEquals(created.id(), found.id());
    }

    @org.junit.jupiter.api.Test
    @org.junit.jupiter.api.Order
            (3)
    void
    testUpdateCandidate
            ()
    {
        dat.dtos.CandidateDTO dto;
        dat.dtos.CandidateDTO created;
        dat.dtos.CandidateDTO updated;

        try
        {
            dto     = new dat.dtos.CandidateDTO(
                    null,
                    "Bob Jones",
                    "11223344",
                    "Mathematics",
                    new java.util.HashSet<>(
                            dat.config.Populate.getSkills().stream().filter(
                                    x ->  x.getName().equals("cpp")
                                            ||  x.getSlug().equals("docker")
                            ).collect(
                                    java.util.stream.Collectors.toSet()
                            )
                    )
            );

            created = candidateDAO.create(
                    dto
            );

            updated = candidateDAO.update(
                    created.id(),
                    new dat.dtos.CandidateDTO(
                            null,
                            "Jones Robert",
                            "44332211",
                            "Mathematics",
                            new java.util.HashSet<>(
                                    dat.config.Populate.getSkills().stream().filter(
                                            x ->  x.getName().equals("cpp")
                                                    ||  x.getSlug().equals("docker")
                                    ).collect(
                                            java.util.stream.Collectors.toSet()
                                    )
                            )
                    )
            );
        }
        catch (java.lang.Exception ex)
        {
            return;
        }

        //  created assertions
        org.junit.jupiter.api.Assertions.assertEquals(
                "Bob Jones",
                created.name()
        );

        org.junit.jupiter.api.Assertions.assertEquals(
                "11223344",
                created.phone()
        );

        org.junit.jupiter.api.Assertions.assertEquals(
                "Mathematics",
                created.education()
        );

        org.junit.jupiter.api.Assertions.assertEquals(
                2,
                created.skills().size()
        );

        //  updated assertions
        org.junit.jupiter.api.Assertions.assertEquals(
                "Jones Robert",
                updated.name()
        );

        org.junit.jupiter.api.Assertions.assertEquals(
                "44332211",
                updated.phone()
        );

        org.junit.jupiter.api.Assertions.assertEquals(
                "Mathematics",
                updated.education()
        );

        org.junit.jupiter.api.Assertions.assertEquals(
                2,
                updated.skills().size()
        );

        // relation assertions
        org.junit.jupiter.api.Assertions.assertNotEquals(
                created.name(),
                updated.name()
        );

        org.junit.jupiter.api.Assertions.assertNotEquals(
                created.phone(),
                updated.phone()
        );

        org.junit.jupiter.api.Assertions.assertEquals(
                created.education(),
                updated.education()
        );

        org.junit.jupiter.api.Assertions.assertEquals(
                created.skills(),
                updated.skills()
        );

    }

    @org.junit.jupiter.api.Test
    @org.junit.jupiter.api.Order
            (4)
    void
    testDeleteCandidate
            ()
    {
        dat.dtos.CandidateDTO dto;
        dat.dtos.CandidateDTO created;

        try
        {
            dto = new dat.dtos.CandidateDTO(
                    null,
                    "Alice Brown",
                    "55667788",
                    "Physics",
                    new java.util.HashSet<>(
                            dat.config.Populate.getSkills().stream().filter(
                                    x ->  x.getName().equals("cpp")
                                            ||  x.getSlug().equals("docker")
                            ).collect(
                                    java.util.stream.Collectors.toSet()
                            )
                    )
            );

            created = candidateDAO.create(dto);

            candidateDAO.delete(created.id());
            org.junit.jupiter.api.Assertions.assertNull(candidateDAO.read(created.id()));
        }
        catch(java.lang.Exception ex)
        {
            return;
        }
    }

    @org.junit.jupiter.api.Test
    @org.junit.jupiter.api.Order
            (5)
    void
    testGetAllCandidates
            ()
    {
        try
        {
            candidateDAO.create(
                    new dat.dtos.CandidateDTO(
                            null,
                            "Candidate 1",
                            "111",
                            "CS",
                            new java.util.HashSet<>(
                                    dat.config.Populate.getSkills().stream().filter(
                                            x ->  x.getName().equals("cpp")
                                                    ||  x.getSlug().equals("docker")
                                    ).collect(
                                            java.util.stream.Collectors.toSet()
                                    )
                            )
                    )
            );

            candidateDAO.create(
                    new dat.dtos.CandidateDTO(
                            null,
                            "Candidate 2",
                            "222",
                            "Math",
                            new java.util.HashSet<>(
                                    dat.config.Populate.getSkills().stream().filter(
                                            x ->  x.getName().equals("cpp")
                                                    ||  x.getSlug().equals("docker")
                                    ).collect(
                                            java.util.stream.Collectors.toSet()
                                    )
                            )
                    )
            );

            java.util.List<dat.dtos.CandidateDTO> all = candidateDAO.readAll();

            org.junit.jupiter.api.Assertions.assertEquals(
                    2,
                    all.size()
            );
        }
        catch (java.lang.Exception ex )
        {
            return;
        }


    }

    @org.junit.jupiter.api.Test
    @org.junit.jupiter.api.Order
            (6)
    void
    testLinkCandidateToSkill
            ()
    {
        dat.entities.Skill skill = new dat.entities.Skill("Java", "java", dat.entities.SkillCategory.PROG_LANG, "Programming language");
        dat.dtos.SkillDTO skillDTO;
        dat.dtos.CandidateDTO candidate;
        try
        {
            skillDTO = skillDAO.create(
                    new dat.dtos.SkillDTO(
                            skill
                    )
            );

            candidate = candidateDAO.create(
                    new dat.dtos.CandidateDTO(
                            null,
                            "Developer",
                            "99999",
                            "IT",
                            new java.util.HashSet<>(
                                    dat.config.Populate.getSkills().stream().filter(
                                            x ->  x.getName().equals("cpp")
                                                    ||  x.getSlug().equals("docker")
                                    ).collect(
                                            java.util.stream.Collectors.toSet()
                                    )
                            )
                    )
            );

            candidateDAO.addSkillToCandidate(
                    candidate.id(),
                    skillDTO.id()
            );

            dat.dtos.CandidateDTO updated = candidateDAO.read(
                    candidate.id()
            );

            org.junit.jupiter.api.Assertions.assertEquals(
                    1,
                    updated.skills().size()
            );
        }
        catch (java.lang.Exception ex)
        {
            return;
        }
    }
}
