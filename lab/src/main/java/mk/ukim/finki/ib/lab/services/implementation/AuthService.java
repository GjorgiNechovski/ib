package mk.ukim.finki.ib.lab.services.implementation;

import mk.ukim.finki.ib.lab.models.exceptions.*;
import mk.ukim.finki.ib.lab.models.User;
import mk.ukim.finki.ib.lab.services.interfaces.IAuthService;
import mk.ukim.finki.ib.lab.services.interfaces.IEmailService;
import mk.ukim.finki.ib.lab.services.interfaces.ISaltingService;
import org.springframework.stereotype.Service;
import mk.ukim.finki.ib.lab.repository.UserRepository;

import java.util.Random;


@Service
public class AuthService implements IAuthService {
    private final UserRepository userRepository;
    private final ISaltingService saltingService;
    private final IEmailService emailService;

    public AuthService(UserRepository userRepository, SaltingService saltingService, EmailService emailService) {
        this.userRepository = userRepository;
        this.saltingService = saltingService;
        this.emailService = emailService;
    }

    @Override
    public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email);

        if (user != null) {

            if (!user.isActive()){
                throw new UserNotActiveException();
            }
            String salt = user.getSalt();

            if (saltingService.validatePassword(password, user.getPassword(), salt)) {
                int number = new Random().nextInt(900000) + 100000;

                emailService.loginConfirmationEmail(email, number);

                user.setLogInNumber(number);
                userRepository.save(user);
                return user;
            }
        }

        return null;
    }

    @Override
    public void createUser(String username, String name, String lastName, String email, String password, String repeatPassword) throws PasswordsDontMatchException, UserNameExistsException, EmailTakenException {
        checkFormErrors(username, email, password, repeatPassword);

        String salt = saltingService.generateSalt();
        String hashedPassword = saltingService.hashPassword(password, salt);

        User user = new User(username, name, lastName, email, hashedPassword, salt);

        String confirmationToken = saltingService.generateSalt();
        user.setConfirmationToken(confirmationToken);

        userRepository.save(user);

        emailService.sendConfirmationEmail(email, confirmationToken);
    }
    @Override
    public void changePassword(String email, String password, String repeatPassword) throws PasswordsDontMatchException {
        User user = userRepository.findByEmail(email);

        if (!password.equals(repeatPassword)){
            throw new PasswordsDontMatchException();
        }

        String salt = saltingService.generateSalt();
        String hashedPassword = saltingService.hashPassword(password, salt);

        user.setPassword(hashedPassword);
        user.setSalt(salt);

        userRepository.save(user);

    }

    @Override
    public void sendChangePasswordEmail(String email) {
        User user = userRepository.findByEmail(email);

        String confirmationToken = saltingService.generateSalt();
        user.setChangePasswordToken(confirmationToken);

        userRepository.save(user);

        emailService.sendChangePasswordEmail(email, confirmationToken);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void checkUserCode(User user, int code) throws AuthCodeNotMatchingException {
        if (user.getLogInNumber() != code)
            throw new AuthCodeNotMatchingException();
    }

    @Override
    public void confirmChangePasswordToken(String email, String token) throws UserNotExistentException, TokensDoNotMatchException {
        User user = userRepository.findByEmail(email);

        if (user==null){
            throw new UserNotExistentException();
        }

        if (!user.getChangePasswordToken().equals(token)){
            System.out.println(token + " " + user.getChangePasswordToken());
            throw new TokensDoNotMatchException();
        }
    }


    @Override
    public void confirmRegisterToken(String token) {
        User user = userRepository.findByConfirmationToken(token);

        if (user!=null){
            user.setActive(true);
            user.setConfirmationToken(null);
            userRepository.save(user);
        }
    }


    public void checkEmailExistence(String email) throws EmailNotExistentException {
        if (userRepository.findByEmail(email)==null){
            throw new EmailNotExistentException();
        }
    }

    private void checkFormErrors(String username, String email, String password, String repeatPassword) throws PasswordsDontMatchException, UserNameExistsException, EmailTakenException {
        if(!password.equals(repeatPassword)){
            throw new PasswordsDontMatchException();
        }

        if(userRepository.findByUsername(username)!=null){
            throw new UserNameExistsException();
        }

        if (userRepository.findByEmail(email) != null){
            throw new EmailTakenException();
        }
    }

}
