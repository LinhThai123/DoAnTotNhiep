package vn.techmaster.doan_woodshop.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vn.techmaster.doan_woodshop.entity.Role;
import vn.techmaster.doan_woodshop.entity.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;


@NoArgsConstructor
@Getter
@Setter
public class CustomUserDetails implements UserDetails {
    private static final long serialVersionUID = 1L;

    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = user.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    public CustomUserDetails(User user) {
        super();
        this.user = user;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }

    public String getFullname() {
        return this.user.getFirstName() + " " + this.user.getLastName();
    }

    public String getFirstname() {
        return this.user.getFirstName();
    }

    public void setFirstName(String firstName) {
        user.setFirstName(firstName);
    }

    public void setLastName(String lastName) {
        user.setLastName(lastName);
    }

    public Integer getId() {
        return user.getId();
    }
}
