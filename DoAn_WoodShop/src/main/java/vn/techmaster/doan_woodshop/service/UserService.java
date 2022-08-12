package vn.techmaster.doan_woodshop.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import vn.techmaster.doan_woodshop.entity.Product;
import vn.techmaster.doan_woodshop.entity.Role;
import vn.techmaster.doan_woodshop.entity.User;
import vn.techmaster.doan_woodshop.exception.NotFoundException;

import java.util.List;

public interface UserService {
    public List<User> listAll() ;

    public List<Role> listRole () ;

    public User getByEmail(String email) ;

    public Page<User> listByPage(int pageNumber , String sortField , String sortDir, String keyword) ;

    public User saveUser(User user) ;

    public User updateAccount(User userInForm);

    public String encodePassword(User user) ;

    public boolean isEmailUnique(Integer id,String email);

    public User getUserById(Integer id) throws NotFoundException;

    public void delete(Integer id) throws NotFoundException ;

    public void updateUserEnabledStatus(Integer id ,boolean enabled);

    public User getCurrentlyLoggedInUser (Authentication authentication) ;

    Page<User> findByFirstNameContaining(String keyword, Pageable pageable);

}
