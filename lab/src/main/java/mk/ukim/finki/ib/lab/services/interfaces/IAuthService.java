package mk.ukim.finki.ib.lab.services.interfaces;


import mk.ukim.finki.ib.lab.models.exceptions.*;
import mk.ukim.finki.ib.lab.models.User;

public interface IAuthService {
    User loginUser(String username, String password);
    void createUser(String username, String name, String lastName, String email, String password, String repeatPassword) throws PasswordsDontMatchException, UserNameExistsException, EmailTakenException;
    void checkEmailExistence(String email) throws EmailNotExistentException;
    void changePassword(String email, String password, String repeatPassword) throws PasswordsDontMatchException;
    void confirmRegisterToken(String token);
    void confirmChangePasswordToken(String email, String token) throws UserNotExistentException, TokensDoNotMatchException;
    void sendChangePasswordEmail(String email);
    User getUserByEmail(String email);
    void checkUserCode(User user, int code) throws AuthCodeNotMatchingException;
}
