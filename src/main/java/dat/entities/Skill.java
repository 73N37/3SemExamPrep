package dat.entities;
@lombok.Getter
@jakarta.persistence.Table( name = "skill")
@jakarta.persistence.Entity
public
class
Skill
{
    @jakarta.persistence.Id
    @jakarta.persistence.GeneratedValue(
            strategy = jakarta.persistence.GenerationType.IDENTITY
    )
    private java.lang.Long id;

    @lombok.Setter
    @jakarta.persistence.Enumerated(
            jakarta.persistence.EnumType.STRING
    )
    @jakarta.persistence.Column(
            name = "category"
    )
    private dat.entities.SkillCategory category;

    @lombok.Setter
    private java.lang.String name;

    @lombok.Setter
    private java.lang.String description;

    @lombok.Setter
    private java.lang.String slug;

    @jakarta.persistence.ManyToMany(
            mappedBy = "skills",
            fetch = jakarta.persistence.FetchType.LAZY
    )
    private java.util.Set<Candidate> candidates = new java.util.HashSet<>();


    @lombok.Setter
    private java.lang.Long popularityScore;

    @lombok.Setter
    private java.lang.Long averageSalary;

    protected Skill (){}    // Required by JPA & Jackson

    public
    Skill
            (
                    @org.jetbrains.annotations.NotNull
                    java.lang.String name,

                    @org.jetbrains.annotations.NotNull
                    java.lang.String slug,

                    @org.jetbrains.annotations.NotNull
                    dat.entities.SkillCategory category,

                    @org.jetbrains.annotations.NotNull
                    java.lang.String description

            )
    {
        this.name           = name;
        this.slug           = slug;
        this.category       = category;
        this.description    = description;
    }

    public
    Skill
            (
                    @org.jetbrains.annotations.NotNull
                    dat.dtos.SkillDTO dto
            )
    {
        if (
                dto.id() != null
        ) this.id           = dto.id();
        this.name           = dto.name();
        this.slug           = dto.slug();
        this.description    = dto.description();
        this.category       = dto.category();
    }

    public
    void
    addCandidate
            (
                    dat.entities.Candidate candidate
            )
    {
        candidates.add(
                candidate
        );

        candidate.addSkill(
                this
        );
    }

}