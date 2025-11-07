package dat.dtos;

public record SkillStatsDTO(
            java.lang.Long          id,

            java.lang.String        name,

            java.lang.String        slug,

            java.lang.String        description,

        @com.fasterxml.jackson.annotation.JsonProperty(
                "categoryKey"
        )   java.lang.String        categoryKey,

        @com.fasterxml.jackson.annotation.JsonProperty(
                "popularityScore"
        )   java.lang.Long          popularityScore,

        @com.fasterxml.jackson.annotation.JsonProperty(
                "averageSalary"
        )     java.lang.Long          averageSalary,

        @com.fasterxml.jackson.annotation.JsonProperty(
                "updatedAt"
        )     java.time.ZonedDateTime updatedAt
)
{}