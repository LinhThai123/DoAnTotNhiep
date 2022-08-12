package vn.techmaster.doan_woodshop.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import vn.techmaster.doan_woodshop.entity.User;
import vn.techmaster.doan_woodshop.repository.UserRepository;

public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.getUserByEmail(email);
        if(user != null)
        {
            return new CustomUserDetails(user);
        }
        throw new UsernameNotFoundException("Could not find user with email : " + email);
    }
}
