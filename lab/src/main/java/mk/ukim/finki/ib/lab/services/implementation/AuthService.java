package mk.ukim.finki.ib.lab.services.implementation;

import mk.ukim.finki.ib.lab.exceptions.EmailNotExistentException;
import mk.ukim.finki.ib.lab.exceptions.PasswordsDontMatchException;
import mk.ukim.finki.ib.lab.exceptions.UserNameExistsException;
import mk.ukim.finki.ib.lab.exceptions.EmailTakenException;
import mk.ukim.finki.ib.lab.models.User;
import mk.ukim.finki.ib.lab.services.interfaces.IAuthService;
import org.springframework.stereotype.Service;
import mk.ukim.finki.ib.lab.repository.UserRepository;


@Service
public class AuthService implements IAuthService {
    private final UserRepository userRepository;
    private final SaltingService saltingService;

    public AuthService(UserRepository userRepository, SaltingService saltingService) {
        this.userRepository = userRepository;
        this.saltingService = saltingService;
    }

    @Override
    public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email);

        if (user != null) {
            String salt = user.getSalt();

            if (saltingService.validatePassword(password, user.getPassword(), salt)) {
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

        userRepository.save(user);
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
