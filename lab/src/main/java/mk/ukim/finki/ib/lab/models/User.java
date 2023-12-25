package mk.ukim.finki.ib.lab.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import mk.ukim.finki.ib.lab.models.enums.UserRole;

import java.util.Objects;

@Entity
@Table(name = "user")
@Data
@EqualsAndHashCode
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name="name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "salt")
    private String salt;

    @Column(name = "confirmation_token")
    private String confirmationToken;

    @Column(name = "change_password_token")
    private String changePasswordToken;

    @Column(name = "log_in_number")
    private int logInNumber;

    @Column(name = "active")
    private boolean active;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role;


    public User(String username, String name, String lastName, String email, String password, String salt) {
        this.username = username;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.salt = salt;
        this.role = UserRole.USER;
    }
}
