package vn.techmaster.doan_woodshop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "feeback")
public class Feeback {

    @GenericGenerator(name = "random_id", strategy = "vn.techmaster.doan_woodshop.model.custom.RandomIdGenerator")
    @Id
    @GeneratedValue(generator = "random_id")
    private String id;

    @Column(name = "fullname" )
    private String fullname ;

    @Column(name = "email")
    private String email ;

    @Column(name = "title" )
    private String title ;

    @Column(name = "content")
    private String content ;

    @Column(name = "create_at")
    private Timestamp createdAt;
}
