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
                        "Cross-platform Object Oriented Programming language"
                ),

                new dat.entities.Skill(
                        "C++",
                        "cpp",
                        dat.entities.SkillCategory.PROG_LANG,
                        "Lower-level Object Oriented Programming language"
                ),

                new dat.entities.Skill(
                        "PostgreSQL",
                        "postgresql",
                        dat.entities.SkillCategory.DB,
                        "Database language"
                ),

                new dat.entities.Skill(
                        "Docker",
                        "docker",
                        dat.entities.SkillCategory.DEVOPS,
                        "Virtual server to test network applications"
                ),

                new dat.entities.Skill(
                        "HTML",
                        "html",
                        dat.entities.SkillCategory.FRONTEND,
                        "Markdown language for creating static webpages"
                ),

                new dat.entities.Skill(
                        "JUnit",
                        "junit",
                        dat.entities.SkillCategory.TESTING,
                        "A framework for testing applications created in Java"
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
