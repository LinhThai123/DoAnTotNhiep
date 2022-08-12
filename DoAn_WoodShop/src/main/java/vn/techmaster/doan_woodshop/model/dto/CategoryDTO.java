package vn.techmaster.doan_woodshop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO implements Serializable {
    private String id ;

    private String name ;

    private String code ;

    private Boolean is_check = false ;
}
