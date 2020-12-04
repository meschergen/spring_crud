package web.model;

import javax.persistence.*;

/**
 * 04.12.2020
 *
 * @author MescheRGen
 */

@Entity
@Table(name = "users")
public class User {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    public User(){ }

    public User(String firstName, String lastName, String email){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public User(Long id, String firstName, String lastName, String email){
        this(firstName, lastName, email);
        this.id = id;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() { return id; }

    @Column(name = "first_name")
    public String getFirstName() { return firstName; }

    @Column(name = "last_name")
    public String getLastName() { return lastName; }

    @Column(name = "email")
    public String getEmail() { return email; }


    public void setId(Long id) { this.id = id; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "\nId = "              + id
                + "\nFirst Name = "   + firstName
                + "\nLast Name = "    + lastName
                + "\nEmail = "        + email
                + "\n";
    }
}
