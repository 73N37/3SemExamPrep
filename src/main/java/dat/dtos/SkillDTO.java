package dat.dtos;

public
record
SkillDTO(
        @com.fasterxml.jackson.annotation.JsonProperty("id")
                java.lang.Long                  id,

        @com.fasterxml.jackson.annotation.JsonProperty("name")
        @org.jetbrains.annotations.NotNull
                java.lang.String                name,

        @com.fasterxml.jackson.annotation.JsonProperty("slug")
        @org.jetbrains.annotations.NotNull
                java.lang.String                slug,

        @com.fasterxml.jackson.annotation.JsonProperty("category")
        @jakarta.persistence.Enumerated(
                jakarta.persistence.EnumType.STRING
        )
                dat.entities.SkillCategory      category,

        @com.fasterxml.jackson.annotation.JsonProperty("description")
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
