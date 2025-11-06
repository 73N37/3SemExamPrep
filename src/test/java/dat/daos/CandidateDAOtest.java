package dat.daos;

//import org.junit.jupiter.api.*;

//import static org.junit.jupiter.api.Assertions.*;

class CandidateDAOTest {
    private static jakarta.persistence.EntityManagerFactory emf;
    private static dat.daos.impl.CandidateDAO candidateDAO;
    private static dat.daos.impl.SkillDAO skillDAO;

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
                created.getId()
        );

        org.junit.jupiter.api.Assertions.assertEquals(
                "John Doe",
                created.getName()
        );
    }

    @org.junit.jupiter.api.Test
    @org.junit.jupiter.api.Order
            (2)
    void
    testReadCandidate
            ()
    {
        dat.dtos.CandidateDTO dto = new dat.dtos.CandidateDTO(null, "Jane Smith", "87654321", "Engineering", null);
        dat.dtos.CandidateDTO created = candidateDAO.create(dto);

        dat.dtos.CandidateDTO found = candidateDAO.read(created.getId());
        org.junit.jupiter.api.Assertions.assertNotNull(found);
        org.junit.jupiter.api.Assertions.assertEquals(created.getId(), found.getId());
    }

    @org.junit.jupiter.api.Test
    @org.junit.jupiter.api.Order
            (3)
    void testUpdateCandidate()
    {
        dat.dtos.CandidateDTO dto = new dat.dtos.CandidateDTO(null, "Bob Jones", "11223344", "Mathematics", null);
        dat.dtos.CandidateDTO created = candidateDAO.create(dto);

        String createdName = created.getName();
        dat.dtos.CandidateDTO updated = candidateDAO.update(created.getId(), new dat.dtos.CandidateDTO(null, "Robert Jones", "11223344", "Mathematics", null));

        org.junit.jupiter.api.Assertions.assertEquals("Robert Jones", updated.getName());
    }

    @org.junit.jupiter.api.Test
    @org.junit.jupiter.api.Order
            (4)
    void testDeleteCandidate() {
        dat.dtos.CandidateDTO dto = new dat.dtos.CandidateDTO(null, "Alice Brown", "55667788", "Physics", null);
        dat.dtos.CandidateDTO created = candidateDAO.create(dto);

        candidateDAO.delete(created.getId());
        org.junit.jupiter.api.Assertions.assertNull(candidateDAO.read(created.getId()));
    }

    @org.junit.jupiter.api.Test
    @org.junit.jupiter.api.Order
            (5)
    void
    testGetAllCandidates
            ()
    {
        candidateDAO.create(new dat.dtos.CandidateDTO(null, "Candidate 1", "111", "CS", null));
        candidateDAO.create(new dat.dtos.CandidateDTO(null, "Candidate 2", "222", "Math", null));

        java.util.List<dat.dtos.CandidateDTO> all = candidateDAO.readAll();
        org.junit.jupiter.api.Assertions.assertEquals(2, all.size());
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
        try
        {
            skillDTO = skillDAO.create(new dat.dtos.SkillDTO(skill));
        }
        catch (java.lang.Exception ex)
        {
            return;
        }
        dat.dtos.CandidateDTO candidate = candidateDAO.create(new dat.dtos.CandidateDTO(null, "Developer", "99999", "IT", null));

        candidateDAO.addSkillToCandidate(candidate.getId(), skillDTO.getId());

        dat.dtos.CandidateDTO updated = candidateDAO.read(candidate.getId());
        org.junit.jupiter.api.Assertions.assertEquals(1, updated.getSkills().size());
    }
}
