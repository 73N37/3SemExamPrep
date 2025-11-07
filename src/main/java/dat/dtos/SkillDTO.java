package dat.dtos;

public
record
SkillDTO(
                java.lang.Long                  id,

        @org.jetbrains.annotations.NotNull
                java.lang.String                name,

        @org.jetbrains.annotations.NotNull
                java.lang.String                slug,

        @jakarta.persistence.Enumerated(
                jakarta.persistence.EnumType.STRING
        )
                dat.entities.SkillCategory      category,

        @org.jetbrains.annotations.NotNull
                java.lang.String                description
)
{
    // Required by Jackson
    public
    SkillDTO
    ()
    {
        this(
                null,
                "",
                "",
                null,
                ""
        );
    }

    public
    SkillDTO
            (
                    @org.jetbrains.annotations.NotNull
                    dat.entities.Skill entity
            )
    {
        this(
                entity.getId(),
                entity.getName(),
                entity.getSlug(),
                entity.getCategory(),
                entity.getDescription()
        );

    }
}
