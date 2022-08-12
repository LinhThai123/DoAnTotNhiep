package vn.techmaster.doan_woodshop.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.techmaster.doan_woodshop.entity.User;

import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ProductRep {

    private String id;

    @NotNull(message = "Tên sản phẩm trống")
    @NotEmpty(message = "Tên sản phẩm trống")
    @Size(min = 1, max = 300, message = "Độ dài tên sản phẩm từ 1 - 300 ký tự")
    private String name;

    @Min(1)
    @Max(100)
    private int quantity;

    private String slug ;

    private long price;

    private String status;

    private String image;

    @NotNull(message = "Mô tả trống")
    @NotEmpty(message = "Mô tả trống")
    private String description;

    @NotNull(message = "Danh mục trống")
    private String category_id;

    private Timestamp createdAt;

    private Timestamp modifiedAt;

    private User createdBy;

    private User modifiedBy;

    private Boolean isCheck = false;
}
