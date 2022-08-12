package vn.techmaster.doan_woodshop.service;

import org.springframework.stereotype.Service;
import vn.techmaster.doan_woodshop.entity.OrderItem;
import vn.techmaster.doan_woodshop.entity.Product;

@Service
public interface OrderItemService {
    <S extends OrderItem> S save(S entity);
}
