package dat.security.controllers;

/**
 * Purpose: To handle security in the API
 * Author: Thomas Hartmann
 */
public
class
SecurityController
        implements ISecurityController
{
    com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();

    dk.bugelhartmann.ITokenSecurity tokenSecurity = new dk.bugelhartmann.TokenSecurity();

    private static dat.security.daos.ISecurityDAO securityDAO;

    private static SecurityController instance;

    private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(
            SecurityController.class
    );

    private
    SecurityController(){}

    public
    static
    SecurityController
    getInstance(

    ) { // Singleton because we don't want multiple instances of the same class
        if (
                instance == null

        ) {
            instance = new SecurityController();

        }
        securityDAO = new dat.security.daos.SecurityDAO(
                dat.config.HibernateConfig.getEntityManagerFactory()
        );

        return instance;
    }

    @Override
    public
    io.javalin.http.Handler
    login(

    ) {
        return (
                ctx
        ) -> {
            com.fasterxml.jackson.databind.node.ObjectNode returnObject = objectMapper.createObjectNode(); // for sending json messages back to the client

            try {
                dk.bugelhartmann.UserDTO user = ctx.bodyAsClass(
                        dk.bugelhartmann.UserDTO.class
                );

                dk.bugelhartmann.UserDTO verifiedUser = securityDAO.getVerifiedUser(
                        user.getUsername(),
                        user.getPassword()
                );

                String token = createToken(
                        verifiedUser
                );

                ctx.status(
                        200
                ).json(
                        returnObject
                                .put(
                                        "token",
                                        token)
                                .put(
                                        "username",
                                        verifiedUser.getUsername()
                                )
                );

            } catch (
                    jakarta.persistence.EntityNotFoundException |
                    dat.security.exceptions.ValidationException e

            ) {
                ctx.status(
                        401
                );

                System.out.println(
                        e.getMessage()
                );

                ctx.json(
                        returnObject.put(
                                "msg",
                                e.getMessage()
                        )
                );
            }
        };
    }

    @Override
    public
    io.javalin.http.Handler
    register(

    ) {
        return (
                ctx
        ) -> {
            com.fasterxml.jackson.databind.node.ObjectNode returnObject = objectMapper.createObjectNode();

            try {
                dk.bugelhartmann.UserDTO userInput = ctx.bodyAsClass(
                        dk.bugelhartmann.UserDTO.class
                );

                dat.security.entities.User created = securityDAO.createUser(
                        userInput.getUsername(),
                        userInput.getPassword()
                );

                String token = createToken(
                        new dk.bugelhartmann.UserDTO(
                                created.getUsername(),
                                java.util.Set.of(
                                        "USER"
                                )
                        )
                );

                ctx.status(
                        io.javalin.http.HttpStatus.CREATED
                ).json(
                        returnObject
                                .put(
                                        "token",
                                        token)
                                .put(
                                        "username",
                                        created.getUsername()
                                )
                );

            } catch (
                    jakarta.persistence.EntityExistsException e
            ) {
                ctx.status(
                        io.javalin.http.HttpStatus.UNPROCESSABLE_CONTENT
                );

                ctx.json(
                        returnObject.put(
                                "msg",
                                "dat.security.entities.User already exists"
                        )
                );
            }
        };
    }

    @Override
    public
    io.javalin.http.Handler
    authenticate() throws io.javalin.http.UnauthorizedResponse
    {

        //com.fasterxml.jackson.databind.node.ObjectNode returnObject = objectMapper.createObjectNode();

        return (
                ctx
        ) -> {
            // This is a preflight request => OK
            if (
                    ctx.method().toString().equals(
                            "OPTIONS"
                    )
            ) {
                ctx.status(
                        200
                );
                return;
            }

            java.lang.String header = ctx.header(
                    "Authorization"
            );

            if (
                    header == null
            ) {
                throw new io.javalin.http.UnauthorizedResponse(
                        "Authorization header missing"
                );

            }

            java.lang.String[] headerParts = header.split(
                    " "
            );

            if (
                    headerParts.length != 2
            ) {
                throw new io.javalin.http.UnauthorizedResponse(
                        "Authorization header malformed"
                );
            }

            String token = headerParts[1];

            dk.bugelhartmann.UserDTO verifiedTokenUser = verifyToken(
                    token
            );

            if (
                    verifiedTokenUser == null
            ) {
                throw new io.javalin.http.UnauthorizedResponse(
                        "Invalid dat.security.entities.User or Token"
                );

            }

            logger.info(
                    "dat.security.entities.User verified: " + verifiedTokenUser
            );

            ctx.attribute(
                    "user",
                    verifiedTokenUser
            );
        };
    }

    @Override
    // Check if the user's roles contain any of the allowed roles
    public
    boolean
    authorize(
            dk.bugelhartmann.UserDTO user,
            java.util.Set<io.javalin.security.RouteRole> allowedRoles
    ) {
        if (
                user == null
        ) {
            throw new io.javalin.http.UnauthorizedResponse(
                    "You need to log in, dude!"
            );
        }
        java.util.Set<java.lang.String> roleNames = allowedRoles.stream()   // Convert RouteRoles to  java.util.Set of Strings
                .map(
                        io.javalin.security.RouteRole::toString
                ).collect(
                        java.util.stream.Collectors.toSet()
                );

        return user.getRoles().stream()
                .map(
                        String::toUpperCase
                ).anyMatch(
                        roleNames::contains
                );
        }

    @Override
    public
    java.lang.String
    createToken(
            dk.bugelhartmann.UserDTO user
    ) {
        try {
            java.lang.String ISSUER;
            java.lang.String TOKEN_EXPIRE_TIME;
            java.lang.String SECRET_KEY;

            if (
                    System.getenv(
                            "DEPLOYED") != null
            ) {
                ISSUER = System.getenv(
                        "ISSUER"
                );

                TOKEN_EXPIRE_TIME = System.getenv(
                        "TOKEN_EXPIRE_TIME"
                );

                SECRET_KEY = System.getenv(
                        "SECRET_KEY"
                );

            } else {
                ISSUER = dat.utils.Utils.getPropertyValue(
                        "ISSUER",
                        "config.properties"
                );

                TOKEN_EXPIRE_TIME = dat.utils.Utils.getPropertyValue(
                        "TOKEN_EXPIRE_TIME",
                        "config.properties"
                );

                SECRET_KEY = dat.utils.Utils.getPropertyValue(
                        "SECRET_KEY",
                        "config.properties"
                );

            }
            return tokenSecurity.createToken(
                    user,
                    ISSUER,
                    TOKEN_EXPIRE_TIME,
                    SECRET_KEY
            );
        } catch (
                java.lang.Exception e
        ) {
            e.printStackTrace();
            throw new dat.security.exceptions.ApiException(
                    500,
                    "Could not create token"
            );
        }
    }

    @Override
    public
    dk.bugelhartmann.UserDTO
    verifyToken(
            java.lang.String token
    ) {
        boolean IS_DEPLOYED = (
                System.getenv(
                        "DEPLOYED"
                ) != null
        );

        java.lang.String SECRET = IS_DEPLOYED ?
                System.getenv(
                        "SECRET_KEY"
                ) : dat.utils.Utils.getPropertyValue(
                        "SECRET_KEY",
                        "config.properties"
        );

        try {
            if (
                    tokenSecurity.tokenIsValid(
                            token,
                            SECRET
                    ) && tokenSecurity.tokenNotExpired(
                            token
                    )
            ) {
                return tokenSecurity.getUserWithRolesFromToken(
                        token
                );
            } else {
                throw new dat.security.exceptions.NotAuthorizedException(
                        403,
                        "Token is not valid"
                );
            }
        } catch (
                java.text.ParseException |
                com.nimbusds.jose.JOSEException |
                dat.security.exceptions.NotAuthorizedException e
        ) {
            e.printStackTrace();
            throw new dat.security.exceptions.ApiException(
                    io.javalin.http.HttpStatus.UNAUTHORIZED.getCode(),
                    "Unauthorized. Could not verify token"
            );
        }
    }

    public
    @org.jetbrains.annotations.NotNull
    io.javalin.http.Handler
    addRole(

    ) {
        return (
                ctx
        ) -> {
            com.fasterxml.jackson.databind.node.ObjectNode returnObject = objectMapper.createObjectNode();

            try {
                // get the role from the body. the json is {"role": "manager"}.
                // We need to get the role from the body and the username from the token
                java.lang.String newRole = ctx.bodyAsClass(
                        com.fasterxml.jackson.databind.node.ObjectNode.class
                ).get(
                        "role"
                ).asText();

                dk.bugelhartmann.UserDTO user = ctx.attribute(
                        "user"
                );

                dat.security.entities.User updatedUser = securityDAO.addRole(
                        user,
                        newRole
                );

                ctx.status(
                        200
                ).json(
                        returnObject.put(
                                "msg",
                                "Role " + newRole + " added to user"
                        )
                );

            } catch (
                    jakarta.persistence.EntityNotFoundException e
            ) {
                ctx.status(
                        404
                ).json(
                        "{\"msg\": \"dat.security.entities.User not found\"}"
                );
            }
        };
    }

    // Health check for the API. Used in deployment
    public
    void
    healthCheck(
            @org.jetbrains.annotations.NotNull
            io.javalin.http.Context ctx
    ) {
        ctx.status(
                200
        ).json(
                "{\"msg\": \"API is up and running\"}"
        );
    }

}
