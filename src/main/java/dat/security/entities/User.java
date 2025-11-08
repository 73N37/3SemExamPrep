package dat.security.entities;

/**
 * Purpose: To handle security in the API
 * Author: Thomas Hartmann
 */
@jakarta.persistence.Entity
@jakarta.persistence.Table(
        name = "users"
)
@jakarta.persistence.NamedQueries(
        @jakarta.persistence.NamedQuery(
                name = "User.deleteAllRows",
                query = "DELETE from User"
        )
)
@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.ToString
public
class
User
        implements  java.io.Serializable,
                    ISecurityUser
{

    @java.io.Serial
    private static final long serialVersionUID = 1L;

    @jakarta.persistence.Id
    @jakarta.persistence.Basic(
            optional = false
    )
    @jakarta.persistence.Column(
            name = "username",
            length = 25
    )
    private java.lang.String username;


    @jakarta.persistence.Basic(
            optional = false
    )
    @jakarta.persistence.Column(
            name = "password"
    )
    private java.lang.String password;

    @jakarta.persistence.JoinTable(
            name = "user_roles",
            joinColumns = {
                    @jakarta.persistence.JoinColumn(
                            name = "user_name",
                            referencedColumnName = "username"
                    )
            }, inverseJoinColumns = {
                    @jakarta.persistence.JoinColumn(
                            name = "role_name",
                            referencedColumnName = "name"
                    )
            }
    )
    @jakarta.persistence.ManyToMany(
            fetch = jakarta.persistence.FetchType.EAGER,
            cascade = jakarta.persistence.CascadeType.PERSIST
    )
    private java.util.Set<Role> roles = new java.util.HashSet<>();

    public
    java.util.Set<java.lang.String>
    getRolesAsStrings(

    ) {
        if (
                roles.isEmpty()
        ) {
            return null;
        }
        java.util.Set<java.lang.String> rolesAsStrings = new java.util.HashSet<>();
        roles.forEach(
                (role
                ) -> {
                    rolesAsStrings.add(
                            role.getRoleName()
                    );
                }
        );
        return rolesAsStrings;
    }

    public
    boolean
    verifyPassword(
            java.lang.String pw
    ) {
        return org.mindrot.jbcrypt.BCrypt.checkpw(
                pw,
                this.password
        );
    }

    public
    User(
            java.lang.String userName,
            java.lang.String userPass
    ) {
        this.username = userName;
        this.password = org.mindrot.jbcrypt.BCrypt.hashpw(
                userPass,
                org.mindrot.jbcrypt.BCrypt.gensalt()
        );
    }

    public
    User(
            java.lang.String userName,
            java.util.Set<Role> roleEntityList
    ) {
        this.username = userName;
        this.roles = roleEntityList;
    }

    public
    void
    addRole(
            Role role
    ) {
        if (
                role == null
        ) {
            return;
        }
        roles.add(
                role
        );
        role.getUsers().add(
                this
        );
    }

    public
    void
    removeRole(
            java.lang.String userRole
    ) {
        roles.stream()
                .filter(
                        role -> role.getRoleName().equals(
                                userRole
                        )
                )
                .findFirst()
                .ifPresent(
                        role -> {
                            roles.remove(
                                    role
                            );
                            role.getUsers().remove(
                                    this
                            );
                });
    }
}

