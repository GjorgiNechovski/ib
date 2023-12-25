package mk.ukim.finki.ib.lab.services.interfaces;

import mk.ukim.finki.ib.lab.models.User;
import mk.ukim.finki.ib.lab.models.enums.UserRole;
import mk.ukim.finki.ib.lab.models.exceptions.AdminCantChangeAdminException;

import java.util.List;

public interface IUserService {
    List<User> getAllUsers();
    void deleteById(int id, User currentUser) throws AdminCantChangeAdminException;
    User findById(int id);
    void changeRole(int id, UserRole role, User currentUser) throws AdminCantChangeAdminException;
}
