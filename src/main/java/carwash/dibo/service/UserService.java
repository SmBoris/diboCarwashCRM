package carwash.dibo.service;

import carwash.dibo.model.Role;
import carwash.dibo.model.User;

public interface UserService {

    User findByUserName(String username);
    User getCurrentUser();

    void saveUser(User user);
    void addRole(User user, Role role);

}
