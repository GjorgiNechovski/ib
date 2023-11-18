package mk.ukim.finki.ib.lab.services.interfaces;


import mk.ukim.finki.ib.lab.models.exceptions.EmailNotExistentException;
import mk.ukim.finki.ib.lab.models.exceptions.EmailTakenException;
import mk.ukim.finki.ib.lab.models.exceptions.UserNameExistsException;
import mk.ukim.finki.ib.lab.models.User;
import mk.ukim.finki.ib.lab.models.exceptions.PasswordsDontMatchException;

public interface IAuthService {
    User loginUser(String username, String password);
    void createUser(String username, String name, String lastName, String email, String password, String repeatPassword) throws PasswordsDontMatchException, UserNameExistsException, EmailTakenException;
    void checkEmailExistence(String email) throws EmailNotExistentException;
    void changePassword(String email, String password, String repeatPassword) throws PasswordsDontMatchException;
    void confirmRegisterToken(String token);
}
