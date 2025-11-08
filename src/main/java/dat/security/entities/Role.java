package dat.security.entities;

/**
 * Purpose: To handle security in the API
 *  Author: Thomas Hartmann
 */
@jakarta.persistence.Entity
@jakarta.persistence.Table(
        name = "roles"
)
@jakarta.persistence.NamedQueries(
        @jakarta.persistence.NamedQuery(
                name = "Role.deleteAllRows",
                query = "DELETE from Role"
        )
)
public
class
Role
        implements java.io.Serializable
{

    @java.io.Serial
    private static final long serialVersionUID = 1L;

    @jakarta.persistence.Id
    @jakarta.persistence.Basic(
            optional = false
    )
    @jakarta.persistence.Column(
            name = "name",
            length = 20
    )
    private java.lang.String name;

    @lombok.ToString.Exclude
    @jakarta.persistence.ManyToMany(
            mappedBy = "roles"
    )
    private java.util.Set<User> users = new java.util.HashSet<>();

    public Role() {}

    public Role(java.lang.String roleName) {
        this.name = roleName;
    }

    public java.lang.String getRoleName() {
        return name;
    }

    public java.util.Set<User> getUsers() {
        return users;
    }

    @Override
    public java.lang.String toString() {
        return "Role{" + "roleName='" + name + '\'' + '}';
    }
}