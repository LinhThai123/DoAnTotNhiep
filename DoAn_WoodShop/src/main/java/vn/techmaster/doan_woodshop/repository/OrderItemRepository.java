package vn.techmaster.doan_woodshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.techmaster.doan_woodshop.entity.OrderItem;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem , String> {
}
