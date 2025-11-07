package dat.config;

public
class
Populate
{
    public
    static
    void
    main
            (
                    String[] args
            )
    {

        jakarta.persistence.EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

        java.util.Set<dat.entities.Skill>       skills      = getSkills();
        java.util.Set<dat.entities.Candidate>   candidates  = getCandidates();

        try (
                jakarta.persistence.EntityManager em = emf.createEntityManager()
        )
        {
            em.getTransaction().begin();

            getCandidates().stream().forEach(x -> em.persist(x));

            em.getTransaction().commit();
        }
    }

    @org.jetbrains.annotations.NotNull
    public
    static
    java.util.Set<dat.entities.Skill>
    getSkills
            ()
    {
        return java.util.Set.of(
                new dat.entities.Skill(
                                "Java",
                                "java",
                                dat.entities.SkillCategory.PROG_LANG,
                                "General-purpose programming language"
                ),

                new dat.entities.Skill(
                        "Spring Boot",
                        "spring-boot",
                        dat.entities.SkillCategory.FRAMEWORK,
                        "Java framework for microservices"
                ),

                new dat.entities.Skill(
                        "PostgreSQL",
                        "postgresql",
                        dat.entities.SkillCategory.DB,
                        "Relational database system"
                ),

                new dat.entities.Skill(
                        "Docker",
                        "docker",
                        dat.entities.SkillCategory.DEVOPS,
                        "Container platform"
                ),

                new dat.entities.Skill(
                        "React",
                        "react",
                        dat.entities.SkillCategory.FRONTEND,
                        "JavaScript UI library"
                ),

                new dat.entities.Skill(
                        "JUnit",
                        "junit",
                        dat.entities.SkillCategory.TESTING,
                        "Testing framework for projects build in Java"
                ),

                new dat.entities.Skill(
                        "TensorFlow",
                        "tensorflow",
                        dat.entities.SkillCategory.DATA,
                        "Data science and analytics tool"
                )
        );
    }

    @org.jetbrains.annotations.NotNull
    public
    static
    java.util.Set<dat.entities.Candidate>
    getCandidates
            ()
    {
        return java.util.Set.of(
                new dat.entities.Candidate(
                        "Merovingian",
                        "null",
                        "2x Ph.D",
                        java.util.Set.of(
                                new dat.entities.Skill(
                                       "C++",
                                       "cpp",
                                       dat.entities.SkillCategory.PROG_LANG,
                                       "The best Object Oriented Programming language"
                                )
                        )
                ),

                new dat.entities.Candidate(
                        "Thomas Anderson",
                        "8888888",
                        "Self thought",
                        java.util.Set.of(
                                new dat.entities.Skill(
                                        "Docker",
                                        "docker",
                                        dat.entities.SkillCategory.DEVOPS,
                                        "Container platform"
                                )
                        )
                ),

                new dat.entities.Candidate(
                        "Trinity",
                        "444",
                        "1x Bachelor",
                        java.util.Set.of(
                                new dat.entities.Skill(
                                        "HTML",
                                        "html",
                                        dat.entities.SkillCategory.FRONTEND,
                                        "Markdown language for static webpages"
                                )
                        )
                ),

                new dat.entities.Candidate(
                        "Morpheus",
                        "333",
                        "1x Masters",
                        java.util.Set.of(
                                new dat.entities.Skill(
                                        "React",
                                        "react",
                                        dat.entities.SkillCategory.FRAMEWORK,
                                        "JavaScript UI framework"
                                )
                        )
                ),

                new dat.entities.Candidate(
                        "Dozer",
                        "111",
                        "2x Masters",
                        java.util.Set.of(
                                new dat.entities.Skill(
                                        "C",
                                        "c",
                                        dat.entities.SkillCategory.PROG_LANG,
                                        "A great functional programming language"
                                )
                        )
                ),

                new dat.entities.Candidate(
                        "Cipher",
                        "666",
                        "2x Masters",
                        java.util.Set.of(
                                new dat.entities.Skill(
                                        "Assembly",
                                        "asm",
                                        dat.entities.SkillCategory.PROG_LANG,
                                        "A low-level programming language"
                                )
                        )
                )
        );
    }
}
