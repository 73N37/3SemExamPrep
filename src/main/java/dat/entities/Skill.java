package dat.entities;
@lombok.Getter
@jakarta.persistence.Table( name = "skill")
@jakarta.persistence.Entity
public class Skill
{
    @jakarta.persistence.Id
    @jakarta.persistence.GeneratedValue(
            strategy = jakarta.persistence.GenerationType.IDENTITY
    )
    private Long id;

    @lombok.Setter
    @jakarta.persistence.Enumerated(
            jakarta.persistence.EnumType.STRING
    )
    @jakarta.persistence.Column(
            name = "category"
    )
    private SkillCategory category;

    @lombok.Setter
    private String name;

    @lombok.Setter
    private String description;

    @lombok.Setter
    private String slug;

    @jakarta.persistence.ManyToMany(
            mappedBy = "skills",
            fetch = jakarta.persistence.FetchType.LAZY
    )
    private java.util.Set<Candidate> candidates = new java.util.HashSet<>();


    @lombok.Setter
    @jakarta.persistence.Transient // Not persisted to database
    private Long popularityScore;

    @lombok.Setter
    @jakarta.persistence.Transient  // Not persisted to database
    private Long averageSalary;

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
                dto.getId() != null
        ) this.id           = dto.getId();
        this.name           = dto.getName();
        this.slug           = dto.getSlug();
        this.description    = dto.getDescription();
        this.category       = dto.getCategory();
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