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
                        new java.util.HashSet(
                                getSkills().stream().filter(
                                        x ->  x.getName().equals("cpp")
                                                ||  x.getSlug().equals("docker")
                                ).collect(
                                        java.util.stream.Collectors.toSet()
                                )
                        )
                ),

                new dat.entities.Candidate(
                        "Thomas Anderson",
                        "8888888",
                        "Self thought",
                        new java.util.HashSet(
                                getSkills().stream().filter(
                                        x ->  x.getName().equals("junit")
                                                ||  x.getSlug().equals("html")
                                ).collect(
                                        java.util.stream.Collectors.toSet()
                                )
                        )
                ),

                new dat.entities.Candidate(
                        "Trinity",
                        "444",
                        "1x Bachelor",
                        new java.util.HashSet(
                                getSkills().stream().filter(
                                        x ->  x.getName().equals("java")
                                                ||  x.getSlug().equals("cpp")
                                ).collect(
                                        java.util.stream.Collectors.toSet()
                                )
                        )
                ),

                new dat.entities.Candidate(
                        "Morpheus",
                        "333",
                        "1x Masters",
                        new java.util.HashSet(
                                getSkills().stream().filter(
                                        x ->  x.getName().equals("postgresql")
                                                ||  x.getSlug().equals("docker")
                                ).collect(
                                        java.util.stream.Collectors.toSet()
                                )
                        )
                ),

                new dat.entities.Candidate(
                        "Dozer",
                        "111",
                        "2x Masters",
                        new java.util.HashSet(
                                getSkills().stream().filter(
                                        x ->  x.getName().equals("postgresql")
                                                ||  x.getSlug().equals("html")
                                ).collect(
                                        java.util.stream.Collectors.toSet()
                                )
                        )
                ),

                new dat.entities.Candidate(
                        "Cipher",
                        "666",
                        "2x Masters",
                        new java.util.HashSet(
                                getSkills().stream().filter(
                                        x ->  x.getName().equals("postgresql")
                                                ||  x.getSlug().equals("html")
                                ).collect(
                                        java.util.stream.Collectors.toSet()
                                )
                        )
                )
        );
    }
}
