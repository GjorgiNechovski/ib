package mk.ukim.finki.ib.lab.services.interfaces;

import mk.ukim.finki.ib.lab.models.User;
import mk.ukim.finki.ib.lab.models.enums.UserRole;

import java.util.List;

public interface IUserService {
    List<User> getAllUsers();
    void deleteById(int id);
    User findById(int id);
    void changeRole(int id, UserRole role);
}
