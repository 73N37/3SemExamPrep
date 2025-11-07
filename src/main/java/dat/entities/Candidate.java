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


    private double AveragePopularityScore;

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
    private java.util.Set<Skill> skills = new java.util.HashSet<>();

    protected Candidate(){} // Required by JPA & Jackson

    public
    Candidate
            (
                    java.lang.String name,
                    java.lang.String phone,
                    java.lang.String education,
                    java.util.Set<Skill> skills
            )
    {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.education = education;
        this.skills = skills;
    }


    public
    Candidate
            (
                    dat.dtos.CandidateDTO dto
            )
    {
        if (dto.getId() != null) this.id = dto.getId();
        this.name = dto.getName();
        this.phone = dto.getPhone();
        this.education = dto.getEducation();
        this.skills = dto.getSkills();
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
        this.AveragePopularityScore = value;
    }
}