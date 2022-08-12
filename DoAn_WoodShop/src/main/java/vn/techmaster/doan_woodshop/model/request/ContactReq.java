package vn.techmaster.doan_woodshop.model.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactReq {

    private String id;

    @NotBlank(message = "Tên không được để trống ")
    private String fullname ;

    private String email ;

    @NotEmpty(message = "Title cannot null")
    @NotNull(message = "Title cannot null")
    @Size(min = 1 , max = 300 , message = "Độ dài tên category không quá 300 ký tự ")
    private String title ;

    @NotNull(message = "Nội dung rỗng")
    @NotEmpty(message = "Nội dung rỗng")
    private String content ;

    private Timestamp createdAt;


}
