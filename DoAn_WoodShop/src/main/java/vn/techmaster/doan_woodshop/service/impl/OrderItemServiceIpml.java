package vn.techmaster.doan_woodshop.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.techmaster.doan_woodshop.entity.OrderItem;
import vn.techmaster.doan_woodshop.repository.OrderItemRepository;
import vn.techmaster.doan_woodshop.service.OrderItemService;
@Component
public class OrderItemServiceIpml implements OrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository ;
    @Override
    public <S extends OrderItem> S save(S entity) {
        return orderItemRepository.save(entity);
    }
}
