package dat.security.daos;


/**
 * Purpose: To handle security in the API
 * Author: Thomas Hartmann
 */
public class SecurityDAO implements ISecurityDAO {

    private static ISecurityDAO instance;
    private static jakarta.persistence.EntityManagerFactory emf;

    public SecurityDAO(jakarta.persistence.EntityManagerFactory _emf) {
        emf = _emf;
    }

    private jakarta.persistence.EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public dk.bugelhartmann.UserDTO getVerifiedUser(String username, String password) throws dat.security.exceptions.ValidationException {
        try (jakarta.persistence.EntityManager em = getEntityManager()) {
            dat.security.entities.User user = em.find(dat.security.entities.User.class, username);
            if (user == null)
                throw new jakarta.persistence.EntityNotFoundException("No user found with username: " + username); //RuntimeException
            user.getRoles().size(); // force roles to be fetched from db
            if (!user.verifyPassword(password))
                throw new dat.security.exceptions.ValidationException("Wrong password");
            return new dk.bugelhartmann.UserDTO(user.getUsername(), user.getRoles().stream().map(r -> r.getRoleName()).collect(java.util.stream.Collectors.toSet()));
        }
    }

    @Override
    public dat.security.entities.User createUser(String username, String password) {
        try (jakarta.persistence.EntityManager em = getEntityManager()) {
            dat.security.entities.User userEntity = em.find(dat.security.entities.User.class, username);
            if (userEntity != null)
                throw new jakarta.persistence.EntityExistsException("dat.security.entities.User with username: " + username + " already exists");
            userEntity = new dat.security.entities.User(username, password);
            em.getTransaction().begin();
            dat.security.entities.Role userRole = em.find(dat.security.entities.Role.class, "user");
            if (userRole == null)
                userRole = new dat.security.entities.Role("user");
            em.persist(userRole);
            userEntity.addRole(userRole);
            em.persist(userEntity);
            em.getTransaction().commit();
            return userEntity;
        }catch (Exception e){
            e.printStackTrace();
            throw new dat.security.exceptions.ApiException(400, e.getMessage());
        }
    }

    @Override
    public dat.security.entities.User addRole(dk.bugelhartmann.UserDTO userDTO, String newRole) {
        try (jakarta.persistence.EntityManager em = getEntityManager()) {
            dat.security.entities.User user = em.find(dat.security.entities.User.class, userDTO.getUsername());
            if (user == null)
                throw new jakarta.persistence.EntityNotFoundException("No user found with username: " + userDTO.getUsername());
            em.getTransaction().begin();
                dat.security.entities.Role role = em.find(dat.security.entities.Role.class, newRole);
                if (role == null) {
                    role = new dat.security.entities.Role(newRole);
                    em.persist(role);
                }
                user.addRole(role);
                //em.merge(user);
            em.getTransaction().commit();
            return user;
        }
    }
    
//    public
//    dat.security.entities.User
//    addRole(
//            dat.security.entities.dat.security.entities.Role role
//    )   {
//        
//    }
}

