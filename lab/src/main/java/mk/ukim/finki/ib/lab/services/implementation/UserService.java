package mk.ukim.finki.ib.lab.services.implementation;

import mk.ukim.finki.ib.lab.models.User;
import mk.ukim.finki.ib.lab.models.enums.UserRole;
import mk.ukim.finki.ib.lab.models.exceptions.AdminCantChangeAdminException;
import mk.ukim.finki.ib.lab.repository.UserRepository;
import mk.ukim.finki.ib.lab.services.interfaces.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {
    private final  UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public void deleteById(int id, User currentUser) throws AdminCantChangeAdminException {

        if (!currentUser.getRole().equals(UserRole.SUPERADMIN)){
            throw new AdminCantChangeAdminException();
        }

        this.userRepository.deleteById(id);
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public void changeRole(int id, UserRole role, User currentUser) throws AdminCantChangeAdminException {

        if (currentUser.getRole().equals(UserRole.ADMIN) && role.equals(UserRole.ADMIN)){
            throw new AdminCantChangeAdminException();
        }

        User user = userRepository.findById(id);

        user.setRole(role);
        userRepository.save(user);
    }
}
