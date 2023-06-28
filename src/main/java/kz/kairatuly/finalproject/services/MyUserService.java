package kz.kairatuly.finalproject.services;

import kz.kairatuly.finalproject.entities.Permission;
import kz.kairatuly.finalproject.entities.User;
import kz.kairatuly.finalproject.repositories.PermissionRepository;
import kz.kairatuly.finalproject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User Not Found");
        }
        return user;
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User user = (User) authentication.getPrincipal();
            return user;
        }
        return null;
    }

    public Boolean registerUser(String email, String password, String rePassword, String fullName) {
        Boolean result = null;
        User user = userRepository.findByEmail(email);
        if (user == null) {
            result = Boolean.FALSE;
            if (password.equals(rePassword)) {
                User newUser = new User();
                newUser.setEmail(email);
                newUser.setPassword(passwordEncoder.encode(password));
                newUser.setFullName(fullName);
                Permission permission = permissionRepository.findByRole("ROLE_USER");
                List<Permission> permissionList = new ArrayList<>();
                permissionList.add(permission);
                newUser.setPermissions(permissionList);
                userRepository.save(newUser);
                result = Boolean.TRUE;
            }
        }
        return result;
    }

    public User insertUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
