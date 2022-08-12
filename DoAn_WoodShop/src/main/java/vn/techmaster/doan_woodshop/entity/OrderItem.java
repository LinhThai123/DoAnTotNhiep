package vn.techmaster.doan_woodshop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderItem {
    @GenericGenerator(name = "random_id", strategy = "vn.techmaster.doan_woodshop.model.custom.RandomIdGenerator")
    @Id
    @GeneratedValue(generator = "random_id")
    private String id;

    @Column(name = "name", nullable = false, length = 300)
    private String fullName;

    private String phone ;

    private String email ;

    private String note ;

    @Column(nullable = false)
    private Integer status;

    @Column
    private Timestamp createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "order_item", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OrderDetail> orderDetails = new ArrayList<>();
}
