package vn.techmaster.doan_woodshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.techmaster.doan_woodshop.entity.Role;
import vn.techmaster.doan_woodshop.entity.User;
import vn.techmaster.doan_woodshop.exception.NotFoundException;
import vn.techmaster.doan_woodshop.repository.RoleRepository;
import vn.techmaster.doan_woodshop.repository.UserRepository;
import vn.techmaster.doan_woodshop.security.CustomUserDetails;
import vn.techmaster.doan_woodshop.service.UserService;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class UserServiceIpml implements UserService {

    public static final int USER_PER_PAGE = 4;

    @Autowired
    private UserRepository userRepository ;

    @Autowired private RoleRepository roleRepository ;


    @Autowired private PasswordEncoder passwordEncoder ;

    @Override
    public List<User> listAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public List<Role> listRole() {
        return (List<Role>) roleRepository.findAll();
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    @Override
    public Page<User> listByPage(int pageNumber, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending()  : sort.descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, USER_PER_PAGE,sort);
        if(keyword != null)
        {
            return userRepository.findAll(keyword, pageable);
        }
        return userRepository.findAll(pageable);
    }

    @Override
    public User saveUser(User user) {
        boolean  isUpdatingUser  = (user.getId() != null);
        if(isUpdatingUser)
        {
            User existingUser = userRepository.findById(user.getId()).get();
            if(user.getPassword().isEmpty())
            {
                user.setPassword(existingUser.getPassword());
            }else
            {
                encodePassword(user);
            }
        }
        else
        {
            encodePassword(user);
        }
        return userRepository.save(user);
    }

    @Override
    public User updateAccount(User userInForm) {
        User userInDB = userRepository.findById(userInForm.getId()).get();

        if (!userInForm.getPassword().isEmpty()) {
            userInDB.setPassword(userInForm.getPassword());
            encodePassword(userInDB);
        }

        if (userInForm.getPhotos() != null) {
            userInDB.setPhotos(userInForm.getPhotos());
        }

        userInDB.setFirstName(userInForm.getFirstName());
        userInDB.setLastName(userInForm.getLastName());

        return userRepository.save(userInDB);
    }

    @Override
    public String encodePassword(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return encodedPassword;
    }

    @Override
    public boolean isEmailUnique(Integer id, String email) {
        User userByEmail = userRepository.getUserByEmail(email);
        if(userByEmail == null) return true;

        boolean isCreatingNew = (id == null);
        if(isCreatingNew)
        {
            if(userByEmail != null) return false;
        }
        else
        {
            if(userByEmail.getId() != id)
            {
                return false;
            }
        }

        return true;
    }

    @Override
    public User getUserById(Integer id) throws NotFoundException {
        try {
            return userRepository.findById(id).get();
        } catch (NoSuchElementException ex) {
            // TODO: handle exception
            throw new NotFoundException("Could not find any user with ID" + id);
        }
    }

    @Override
    public void delete(Integer id) throws NotFoundException {
        Long countById = userRepository.countById(id);
        if(countById == null || countById == 0)
        {
            throw new NotFoundException("Could not find any user with ID" + id);
        }

        userRepository.deleteById(id);
    }

    @Override
    public void updateUserEnabledStatus(Integer id, boolean enabled) {
        userRepository.updateEnabledStatus(id, enabled);
    }

    @Override
    public User getCurrentlyLoggedInUser(Authentication authentication) {
        if(authentication == null) return null ;

        User user = null ;
        Object principal = authentication.getPrincipal() ;
        if(principal instanceof CustomUserDetails) {
            user = ((CustomUserDetails) principal).getUser() ;
        }
        return user ;
    }

    @Override
    public Page<User> findByFirstNameContaining(String keyword, Pageable pageable) {
        return userRepository.findByFirstNameContaining(keyword , pageable);
    }


}