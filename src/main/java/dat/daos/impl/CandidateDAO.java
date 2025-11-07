package dat.daos.impl;

@lombok.NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public
class
CandidateDAO
        implements dat.daos.IDAO<   dat.dtos.CandidateDTO,
                                    java.lang.Long          >
{

    private static CandidateDAO                             instance;
    private static jakarta.persistence.EntityManagerFactory emf;
    private final dat.Service.SkillStatsService             skillStatsService = new dat.Service.SkillStatsService();

    public 
    static
    CandidateDAO
    getInstance
            (
                    jakarta.persistence.EntityManagerFactory _emf
            ) 
    {
        if (instance == null) 
        {
            emf = _emf;
            instance = new CandidateDAO();
        }
        return instance;
    }

    @Override
    public
    dat.dtos.CandidateDTO
    read
            (
                    java.lang.Long id
            )   throws dat.exceptions.ApiException
    {
        try (
                jakarta.persistence.EntityManager em = emf.createEntityManager()
        )
        {
            dat.entities.Candidate candidate = em.find(
                    dat.entities.Candidate.class,
                    id
            );

            if (candidate == null) return null;

            dat.dtos.CandidateDTO dto = new dat.dtos.CandidateDTO(
                    candidate
            );

            enrichWithStats(
                    dto
            );

            return dto;
        }
    }

    private
    void
    enrichWithStats
            (
                    dat.dtos.CandidateDTO dto
            )
    {
        skillStatsService.enrichSkills(dto.getSkills());
        // The skills in dto are already updated by reference since enrichSkills modifies them
    }

    @Override
    public
    java.util.List<dat.dtos.CandidateDTO>
    readAll
            ()  throws dat.exceptions.ApiException
    {
        try (
                jakarta.persistence.EntityManager em = emf.createEntityManager()
        )
        {
            jakarta.persistence.TypedQuery<dat.dtos.CandidateDTO> query = em.createQuery(
                    "SELECT new dat.dtos.CandidateDTO(c) FROM dat.entities.Candidate c",
                    dat.dtos.CandidateDTO.class
            );
            return query.getResultList();
        }
    }

    @Override
    public
    dat.dtos.CandidateDTO
    create
            (
                    dat.dtos.CandidateDTO candidateDTO
            )   throws dat.exceptions.ApiException
    {
        try (
                jakarta.persistence.EntityManager em = emf.createEntityManager()
        )
        {
            em.getTransaction().begin();

            dat.entities.Candidate candidate = new dat.entities.Candidate(
                    candidateDTO
            );

            em.persist(
                    candidate
            );

            em.getTransaction().commit();

            return new dat.dtos.CandidateDTO(
                    candidate
            );
        }
    }

    @Override
    public
    dat.dtos.CandidateDTO
    update
            (
                    java.lang.Long id,
                    dat.dtos.CandidateDTO candidateDTO
            )   throws dat.exceptions.ApiException
    {
        try (
                jakarta.persistence.EntityManager em = emf.createEntityManager()
        )
        {
            em.getTransaction().begin();
            dat.entities.Candidate candidate = em.find(
                    dat.entities.Candidate.class,
                    id
            );

            if (
                    candidate != null
            )
            {
                candidate.setName(
                        candidateDTO.getName()
                );

                candidate.setPhone(
                        candidateDTO.getPhone()
                );

                candidate.setEducation(
                        candidateDTO.getEducation()
                );

                dat.entities.Candidate mergedCandidate = em.merge(
                        candidate
                );

                em.getTransaction().commit();

                return new dat.dtos.CandidateDTO(
                        mergedCandidate
                );
            }
            em.getTransaction().rollback();
            return null;
        }
    }

    @Override
    public
    void
    delete
            (
                    java.lang.Long id
            )
            throws dat.exceptions.ApiException
    {
        try (
                jakarta.persistence.EntityManager em = emf.createEntityManager()
        )
        {
            em.getTransaction().begin();
            dat.entities.Candidate candidate = em.find(
                    dat.entities.Candidate.class,
                    id
            );

            if (
                    candidate != null
            )
            {
                em.remove(
                        candidate
                );
            }
            em.getTransaction().commit();
        }
    }

    @Override
    public
    boolean
    validatePrimaryKey
            (
                    java.lang.Long id
            )
    {
        try (
                jakarta.persistence.EntityManager em    = emf.createEntityManager()
        )
        {
            dat.entities.Candidate candidate            = em.find(
                    dat.entities.Candidate.class,
                    id
            );

            return candidate != null;   // if 'candidate' is NOT null returns true, if 'candidate' is null false is returned
        }
    }

    public
    dat.dtos.CandidateDTO
    addSkillToCandidate
            (
                    java.lang.Long candidateId,
                    java.lang.Long skillId
            )
    {
        try (
                jakarta.persistence.EntityManager em    = emf.createEntityManager()
        )
        {
            em.getTransaction().begin();

            dat.entities.Candidate candidate            = em.find(
                    dat.entities.Candidate.class,
                    candidateId
            );

            dat.entities.Skill skill                    = em.find(
                    dat.entities.Skill.class,
                    skillId
            );

            if (
                    candidate != null && skill != null
            )
            {
                candidate.addSkill(
                        skill
                );

                dat.entities.Candidate mergedCandidate  = em.merge(
                        candidate
                );

                em.getTransaction().commit();

                return new dat.dtos.CandidateDTO(
                        mergedCandidate
                );
            }
            em.getTransaction().rollback();
            return null;
        }
    }

    public
    java.util.List<dat.dtos.CandidateDTO>
    filterByCategory
            (
                    dat.entities.SkillCategory category
            )
    {
        try (
                jakarta.persistence.EntityManager em                        = emf.createEntityManager()
        )
        {
            jakarta.persistence.TypedQuery<dat.entities.Candidate> query    = em.createQuery(
                    "SELECT DISTINCT c FROM dat.entities.Candidate c JOIN c.skills s WHERE s.category = :category",
                    dat.entities.Candidate.class
            );

            query.setParameter(
                    "category",
                    category
            );

            // Creates a new ArrayList iterate through it with stream()[Supplier<T>].
            // Creates objects with map(x) [UnaryOperator<T>]
            // Adds object to the newly created list [Function<T,R>]
            return new java.util.ArrayList<dat.entities.Candidate>().stream()
                    .map(
                            dat.dtos.CandidateDTO::new
                    ).toList();
        }
    }

//    public dat.dtos.CandidateDTO getTopCandidateByPopularity() {
//        try (jakarta.persistence.EntityManager em = emf.createEntityManager()) {
//            jakarta.persistence.TypedQuery<dat.entities.Candidate> query = em.createQuery(
//                    "SELECT c FROM dat.entities.Candidate c LEFT JOIN c.skills s " +
//                            "GROUP BY c.id ORDER BY AVG(s.popularityScore) DESC",
//                    dat.entities.Candidate.class
//            );
//            query.setMaxResults(1);
//            java.util.List<dat.entities.Candidate> results = query.getResultList();
//
//            if (results.isEmpty()) return null;
//
//            dat.entities.Candidate candidate = results.get(0);
//            double avgPopularity = candidate.getSkills().stream()
//                    .mapToLong(dat.entities.Skill::getPopularityScore)
//                    .average()
//                    .orElse(0.0);
//
//            return new dat.dtos.CandidateDTO(
//                    candidate
//            );
//        }
//    }

    public boolean validateSkillId(Long id) {
        return SkillDAO.getInstance(dat.config.HibernateConfig.getEntityManagerFactory()).validatePrimaryKey(id);
    }

    public void populate() {
        try (jakarta.persistence.EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            em.createQuery("DELETE FROM dat.entities.Candidate").executeUpdate();

            dat.entities.Candidate c1 = new dat.entities.Candidate("John Doe", "+45 12345678", "Bachelor in Computer Science",
                    java.util.Set.of(new dat.entities.Skill("Merovingian", "merovingian", dat.entities.SkillCategory.DEVOPS,
                            "The best programmer ever 'born'")));
            dat.entities.Candidate c2 = new dat.entities.Candidate("Jane Smith", "+45 87654321", "Master in Software Engineering",
                    java.util.Set.of(new dat.entities.Skill("Neo", "neo", dat.entities.SkillCategory.DATA, "The chosen one")));
            dat.entities.Candidate c3 = new dat.entities.Candidate("Bob Johnson", "+45 11223344", "Bachelor in Data Science",
                    java.util.Set.of(new dat.entities.Skill("Morpheus", "morpheus", dat.entities.SkillCategory.DB, "The guide")));

            em.persist(c1);
            em.persist(c2);
            em.persist(c3);

            em.getTransaction().commit();
        }
    }
}
