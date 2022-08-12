package vn.techmaster.doan_woodshop.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryRep {

    private String id ;

    @NotEmpty(message = "Category cannot null")
    @NotNull(message = "Category cannot null")
    @Size(min = 1 , max = 300 , message = "Độ dài tên category không quá 300 ký tự ")
    private String name ;

    @NotEmpty(message = "Code cannot null")
    @NotNull(message = "Code cannot null")
    private String code ;

    @NotEmpty
    private String status;

    private Date created_at;

    private Date updated_at;

    private Boolean isCheck = false ;
    
}
