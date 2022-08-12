package vn.techmaster.doan_woodshop.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Category")
@Table(name = "category")
public class Category implements Serializable {
    @GenericGenerator(name = "random_id", strategy = "vn.techmaster.doan_woodshop.model.custom.RandomIdGenerator")
    @Id
    @GeneratedValue(generator = "random_id")
    private String id;

    @Column(name = "name", nullable = false, length = 300)
    private String name;

    @Column(name = "code", nullable = false, length = 300)
    private String code;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status ;


    @Column(name = "created_at")
    @Temporal(TemporalType.DATE)
    private Date created_at;

    @Column(name = "update_at")
    @Temporal(TemporalType.DATE)
    private Date Updated_at;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = false)
    @JsonManagedReference
    private List<Product> products = new ArrayList<>();

}