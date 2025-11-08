package dat.services;

@lombok.Setter
public
class
SkillStatsBuilder
{
    private java.lang.Long            id;
    private java.lang.String          slug;
    private java.lang.String          name;
    private java.lang.String          categoryKey;
    private java.lang.String          description;
    private java.lang.Long            popularityScore;
    private java.lang.Long            averageSalary;
    private java.time.ZonedDateTime   updatedAt;

    public SkillStatsBuilder() {}

    public
    SkillStatsBuilder
            (
                    dat.dtos.SkillStatsDTO dto
            )
    {
        this.id                 = dto.id();
        this.slug               = dto.slug();
        this.name               = dto.name();
        this.categoryKey        = dto.categoryKey();
        this.description        = dto.description();
        this.popularityScore    = dto.popularityScore();
        this.averageSalary      = dto.averageSalary();
        this.updatedAt          = dto.updatedAt();
    }

    public
    dat.dtos.SkillStatsDTO
    build
            ()
    {
        return new dat.dtos.SkillStatsDTO(
                id,
                slug,
                name,
                categoryKey,
                description,
                popularityScore,
                averageSalary,
                updatedAt
        );
    }
}
