package vn.techmaster.doan_woodshop.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import vn.techmaster.doan_woodshop.entity.BlogStatus;
import vn.techmaster.doan_woodshop.entity.User;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostReq {

    private String id;

    @NotNull(message = "Tiêu đề rỗng")
    @NotEmpty(message = "Tiêu đề rỗng")
    @Size(min = 1, max = 300, message = "Độ dài tiêu đề từ 1 - 300 ký tự")
    private String title;

    @NotNull(message = "Nội dung rỗng")
    @NotEmpty(message = "Nội dung rỗng")
    private String content;

    private String description;

    private String status;

    private String thumbnail;

    private MultipartFile imageFile;

    private String slug;

    private Timestamp createdAt;

    private Timestamp modifiedAt;

    private Timestamp publishedAt;

    private User createdBy;

    private User modifiedBy;

    private Boolean isCheck = false;

}
