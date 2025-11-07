package dat.Service;

public
class
SkillStatsService
{
    private static final    java.lang.String                            API_URL = "https://api.example.com/tech-skills";

    private final           java.net.http.HttpClient                    client;

    private final           com.fasterxml.jackson.databind.ObjectMapper objectMapper;

    public
    SkillStatsService
            () {
        this.client         = java.net.http.HttpClient.newHttpClient();

        this.objectMapper   = new com.fasterxml.jackson.databind.ObjectMapper();

        this.objectMapper.registerModule(
                new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule()
        );

        this.objectMapper.disable(
                com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
        );
    }

    public
    java.util.List<dat.dtos.SkillStatsDTO>
    getSkillStats(
            java.util.List<java.lang.String> slugs
    )   throws dat.exceptions.ApiException {
        if (
                slugs == null   ||
                slugs.isEmpty()
        ) {
            throw new dat.exceptions.ApiException(
                    502,
                    "It is NOT allowed to pass an empty or null parameter (java.util.Set<java.lang.String>)\nSkillStatsService.getSkillStats(java.util.Set<java.lang.String>)");
        }

        try {
            String slugParams = slugs.stream()
                    .map(
                            slug -> "slug=" + slug
                    ).collect(
                            java.util.stream.Collectors.joining(
                                    "&"
                            )
                    );

            java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                    .uri(
                            java.net.URI.create(
                                    API_URL + "?" + slugParams
                            )
                    ).GET().build();

            java.net.http.HttpResponse<java.lang.String> response = client.send(
                    request,
                    java.net.http.HttpResponse.BodyHandlers.ofString()
            );

            if (
                    response.statusCode() == 200
            ) {
                java.util.Map<java.lang.String, java.lang.Object> responseMap = objectMapper.readValue(
                        response.body(),
                        java.util.Map.class
                );

                java.util.List<java.util.Map<java.lang.String, java.lang.Object>> dataList = (java.util.List<java.util.Map<java.lang.String, java.lang.Object>>) responseMap.get(
                        "data"
                );

                java.util.List<dat.dtos.SkillStatsDTO> stats = new java.util.ArrayList<>();
                for (
                        java.util.Map<java.lang.String, java.lang.Object> data : dataList
                ) {
                    dat.dtos.SkillStatsDTO dto = objectMapper.convertValue(
                            data,
                            dat.dtos.SkillStatsDTO.class
                    );
                    stats.add(dto);
                }
                return stats;
            }
        } catch (java.io.IOException | InterruptedException e) {
            System.err.println("Failed to fetch skill stats: " + e.getMessage());
        }
        throw new dat.exceptions.ApiException(
                502,
                "An error happen while receiving http response. The method [client.send( java.net.http.HttpRequest request, " +
                        "java.net.http.HttpResponse.BodyHandler)] failed since it did not receive status code 200"
        );
    }

    public
    java.util.List<dat.entities.Skill>
    enrichSkills(
            java.util.Set<dat.entities.Skill> skills
    ) throws dat.exceptions.ApiException {
        if (
                skills == null  ||
                skills.isEmpty()
        ) {
            throw new dat.exceptions.ApiException(
                    502,
                    "It is NOT allowed to pass an empty or null parameter (java.util.Set<dat.entities.Skill>)\nSkillStatsService.enrichSkills(java.util.Set<Skill>)");
        }

        java.util.List<java.lang.String> slugs = skills.stream()
                .map(
                        dat.entities.Skill::getSlug
                ).collect(
                        java.util.stream.Collectors.toList()
                );

        java.util.List<dat.dtos.SkillStatsDTO> stats = getSkillStats(
                slugs
        );

        java.util.Map<java.lang.String, dat.dtos.SkillStatsDTO> statsBySlug = stats.stream()
                .collect(
                        java.util.stream.Collectors.toMap(
                                dat.dtos.SkillStatsDTO::slug,
                                dto -> dto)
                );

        java.util.List<dat.entities.Skill> enrichedSkills = new java.util.ArrayList<>();
        for (dat.entities.Skill skill : skills) {
            dat.dtos.SkillStatsDTO statsDTO = statsBySlug.get(
                    skill.getSlug()
            );

            if (
                    statsDTO != null
            ) {
                skill.setPopularityScore(
                        statsDTO.popularityScore() != null      ?
                        statsDTO.popularityScore().longValue()  :
                        null
                );

                skill.setAverageSalary(
                        statsDTO.averageSalary() != null        ?
                        statsDTO.averageSalary().longValue()    :
                        null
                );
            }

            enrichedSkills.add(
                    skill
            );
        }
        return enrichedSkills;
    }
}
