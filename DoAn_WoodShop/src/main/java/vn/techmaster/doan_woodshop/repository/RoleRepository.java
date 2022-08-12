package vn.techmaster.doan_woodshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vn.techmaster.doan_woodshop.entity.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role , Integer> {
}
