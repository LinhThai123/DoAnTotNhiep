package vn.techmaster.doan_woodshop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDTO {
    private String id;

    private String name;

    private String slug;

    private long price;

    private int quantity;

    private String image;

    private Timestamp createdAt;

//    private long promotionPrice;

    public ProductDTO(String name, String slug, long price, int quantity, String image) {
        this.name = name;
        this.slug = slug;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
    }
}
