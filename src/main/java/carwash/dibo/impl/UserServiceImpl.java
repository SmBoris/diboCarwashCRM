package carwash.dibo.impl;

import carwash.dibo.model.Role;
import carwash.dibo.model.User;
import carwash.dibo.common.UserRole;
import carwash.dibo.repository.RoleRepository;
import carwash.dibo.repository.UserRepository;
import carwash.dibo.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    public final UserRepository userRepository;
    public final RoleRepository roleRepository;
    public final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        setDefaultRole(user);
        userRepository.save(user);
    }

    @Override
    public User findByUserName(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public void addRole(User user, Role role) {
    }

    @Override
    public User getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        return findByUserName(userDetails.getUsername());
    }

    public void setDefaultRole(User user){
        Role defaultRole = roleRepository.findByName(UserRole.ROLE_EMPLOYEE.name());

        if (defaultRole == null){
            user.setRoles(Collections.singleton(new Role(UserRole.ROLE_EMPLOYEE.name())));
        }
        else user.setRoles(Collections.singleton(defaultRole));
    }
}
