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
    private Long id;

    @lombok.Setter
    private String name;

    @lombok.Setter
    private String phone;

    @lombok.Setter
    private String education;


    private double averagePopularityScore;

    @jakarta.persistence.ManyToMany(
            fetch = jakarta.persistence.FetchType.EAGER
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
                    java.lang.String name,
                    java.lang.String phone,
                    java.lang.String education,
                    java.util.Set<dat.dtos.SkillDTO> skills
            )
    {
        this.name = name;
        this.phone = phone;
        this.education = education;
        skills.stream();
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

        this.name = dto.name();

        this.phone = dto.phone();

        this.education = dto.education();

        this.skills = dto.skills();
    }

    public
    void
    addSkill
            (
                    dat.entities.Skill skill
            )
    {
        this.skills.add(skill);
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