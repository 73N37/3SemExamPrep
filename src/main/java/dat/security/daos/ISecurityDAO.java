package dat.security.daos;

public interface ISecurityDAO {
    dk.bugelhartmann.UserDTO
    getVerifiedUser(
            java.lang.String            username,
            java.lang.String            password
    ) throws dat.security.exceptions.ValidationException;

    dat.security.entities.User
    createUser(
            java.lang.String            username,
            java.lang.String            password
    );

    dat.security.entities.User
    addRole(
            dk.bugelhartmann.UserDTO    user,
            java.lang.String            newRole
    );
}
