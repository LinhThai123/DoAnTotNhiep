package vn.techmaster.doan_woodshop.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordReq {

    @NotBlank(message = "Nhập mật khẩu cũ ")
    private String oldpassword ;

    @NotBlank(message = "Nhập mật khẩu mới ")
    @Size( min = 1 , max = 10 , message = "Nhập mật khẩu mới phải 6 ký tự trở lên ")
    private String newpassword ;

    @NotBlank(message = "Nhập lại mật khẩu cũ  ")
    @Size( min = 1 , max = 10 , message = "Nhập mật khẩu mới phải 6 ký tự trở lên ")
    private String retypepassword ;

}
