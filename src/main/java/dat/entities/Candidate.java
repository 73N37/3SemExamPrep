package dat.entities;

@lombok.Getter
@jakarta.persistence.Entity
@jakarta.persistence.Table(name = "candidates")
public
class
Candidate {
    @jakarta.persistence.Id
    @jakarta.persistence.GeneratedValue(
            strategy = jakarta.persistence.GenerationType.IDENTITY
    )
    private java.lang.Long id;

    @lombok.Setter
    private java.lang.String name;

    @lombok.Setter
    private java.lang.String phone;

    @lombok.Setter
    private java.lang.String education;

    private double averagePopularityScore;

    @jakarta.persistence.ManyToMany(
            cascade = {
                    jakarta.persistence.CascadeType.PERSIST,
                    jakarta.persistence.CascadeType.MERGE
            },
            fetch = jakarta.persistence.FetchType.LAZY
    )
    @jakarta.persistence.JoinTable(
            name = "candidate_skill",
            joinColumns = @jakarta.persistence.JoinColumn(
                    name = "candidate_id"
            ),
            inverseJoinColumns = @jakarta.persistence.JoinColumn(
                    name = "skill_id"
            )
    )
    private java.util.Set<dat.entities.Skill> skills = new java.util.HashSet<>();

    protected Candidate(){} // Required by JPA & Jackson

    public
    Candidate
            (
                    java.lang.String                    name,
                    java.lang.String                    phone,
                    java.lang.String                    education,
                    java.util.Set<dat.entities.Skill>   skills
            )
    {
        this.name       =   name;
        this.phone      =   phone;
        this.education  =   education;
        this.skills     =   skills;
    }

    public
    Candidate
            (
                    dat.dtos.CandidateDTO dto
            )
    {
        if (
                dto.id() != null
        ) this.id = dto.id();

        this.name       = dto.name();

        this.phone      = dto.phone();

        this.education  = dto.education();

        this.skills     = dto.skills().stream().map(
                dat.entities.Skill::new
        ).collect(
                java.util.stream.Collectors.toSet()
        );
    }

    public
    void
    addSkill(
            dat.entities.Skill skill
    ) {
        this.skills.add(
                skill
        );
    }

    public
    void
    removeSkill(
            dat.entities.Skill skill
    ) {
        this.skills.remove(skill);
    }

    public
    void
    setAveragePopularityScore
            (
                    @org.jetbrains.annotations.NotNull
                    double value
            )
    {
        this.averagePopularityScore = value;
    }
}