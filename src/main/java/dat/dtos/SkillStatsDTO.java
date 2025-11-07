package dat.dtos;

//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.JsonProperty;
//
//import java.time.ZonedDateTime;
//
//@lombok.Getter
//@lombok.NoArgsConstructor
//@lombok.AllArgsConstructor
//@JsonIgnoreProperties(ignoreUnknown = true)
//public class SkillStatsDTO {
//    private String id;
//    private String slug;
//    private String name;
//
//    @JsonProperty("categoryKey")
//    private String categoryKey;
//
//    private String description;
//
//    @JsonProperty("popularityScore")
//    private Long popularityScore;
//
//    @JsonProperty("averageSalary")
//    private Long averageSalary;
//
//    @JsonProperty("updatedAt")
//    private ZonedDateTime updatedAt;
//}

public record SkillStatsDTO(
                                                                            java.lang.Long          id,
                                                                            java.lang.String        name,
                                                                            java.lang.String        slug,
                                                                            java.lang.String        description,
        @com.fasterxml.jackson.annotation.JsonProperty("categoryKey")       java.lang.String        categoryKey,
        @com.fasterxml.jackson.annotation.JsonProperty("popularityScore")   java.lang.Long          popularityScore,
        @com.fasterxml.jackson.annotation.JsonProperty("averageSalary")     java.lang.Long          averageSalary,
        @com.fasterxml.jackson.annotation.JsonProperty("updatedAt")          java.time.ZonedDateTime updatedAt
)
{

}