package dat.dtos;

    @com.fasterxml.jackson.annotation.JsonIgnoreProperties(
            ignoreUnknown = true
    )
    @com.fasterxml.jackson.annotation.JsonInclude(
            com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
    )
public
    record
    CandidateDTO(

        @com.fasterxml.jackson.annotation.JsonProperty("id")
        java.lang.Long id,

        @com.fasterxml.jackson.annotation.JsonProperty("name")
        @org.jetbrains.annotations.NotNull
        java.lang.String name,

        @com.fasterxml.jackson.annotation.JsonProperty("phone")
        @org.jetbrains.annotations.NotNull
        java.lang.String phone,

        @com.fasterxml.jackson.annotation.JsonProperty("education")
        @org.jetbrains.annotations.NotNull
        java.lang.String education,

        @com.fasterxml.jackson.annotation.JsonProperty("skills")
        java.util.Set<dat.dtos.SkillDTO> skills
    ) {
    protected
    CandidateDTO
            (){
        this(
                null,
                "",
                "",
                "",
                null
        );
    }

    public
    CandidateDTO(
        dat.entities.Candidate entity
    )
    {
        this (
            entity.getId(),
            entity.getName(),
            entity.getPhone(),
            entity.getEducation(),
                entity.getSkills().stream().map(
                        dat.dtos.SkillDTO::new
                ).collect(
                        java.util.stream.Collectors.toSet()
                )
        );
    }
}
