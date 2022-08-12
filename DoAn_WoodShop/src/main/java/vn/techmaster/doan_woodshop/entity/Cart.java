package vn.techmaster.doan_woodshop.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "cart")
public class Cart extends BaseEntity {
    @OneToOne(mappedBy = "cart")
    private User user;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "cart_product",
//            joinColumns = @JoinColumn(name = "cart_id"),
//            inverseJoinColumns = @JoinColumn(name = "product_id"))
//    private List<Product> products;

    @OneToMany(mappedBy = "cart")
    private List<CartProduct> cartProducts = new ArrayList<>();
}
