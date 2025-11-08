package dat.utils;

/**
 * Purpose: Utility class to read properties from a file
 * Author: Thomas Hartmann
 */
public
class
Utils
{
    public
    static
    void
    main(
            java.lang.String[] args
    ) {
        System.out.println(
                getPropertyValue(
                        "db.name",
                        "properties-from-pom.properties"
                )
        );
    }

    public
    static
    java.lang.String
    getPropertyValue(
            java.lang.String propName,
            java.lang.String resourceName
    )  {
        // REMEMBER TO BUILD WITH MAVEN FIRST. Read the property file if not deployed (else read system vars instead)
        // Read from ressources/config.properties or from pom.xml depending on the ressourceName
        try (
                java.io.InputStream is = Utils.class.getClassLoader().getResourceAsStream(
                        resourceName
                )
        ) {
            java.util.Properties prop = new java.util.Properties();
            prop.load(
                    is
            );

            String value = prop.getProperty(
                    propName
            );

            if (
                    value != null
            ) {
                return value.trim();  // Trim whitespace
            } else {
                throw new dat.security.exceptions.ApiException(
                        500,
                        String.format(
                                "Property %s not found in %s",
                                propName,
                                resourceName
                        )
                );
            }
        } catch (
                java.io.IOException ex
        ) {
            ex.printStackTrace();
            throw new dat.security.exceptions.ApiException(
                    500,
                    java.lang.String.format(
                            "Could not read property %s. Did you remember to build the project with MAVEN?",
                            propName
                    )
            );
        }
    }

    public
    com.fasterxml.jackson.databind.ObjectMapper
    getObjectMapper(

    ) {
        com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();

        objectMapper.configure(
                com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                false
        ); // Ignore unknown properties in JSON

        objectMapper.registerModule(
                new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule()
        ); // Serialize and deserialize java.time objects

        objectMapper.writer(
                new com.fasterxml.jackson.core.util.DefaultPrettyPrinter()
        );

        return objectMapper;
    }

    public
    static
    java.lang.String
    convertToJsonMessage(
            io.javalin.http.Context ctx,
            java.lang.String property,
            java.lang.String message
    ) {
        java.util.Map<java.lang.String, java.lang.String> msgMap = new java.util.HashMap<>();

        msgMap.put(
                property,
                message
        );  // Put the message in the map

        msgMap.put(
                "status",
                String.valueOf(
                        ctx.status()
                )
        );  // Put the status in the map

        com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();

        try {
            return objectMapper.writeValueAsString(
                    msgMap
            );  // Convert the map to JSON

        } catch (
                java.lang.Exception e
        ) {
            return "{\"error\": \"Could not convert  message to JSON\"}";
        }
    }
}
