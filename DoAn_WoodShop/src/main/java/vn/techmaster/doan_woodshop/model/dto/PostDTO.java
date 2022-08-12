package vn.techmaster.doan_woodshop.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDTO {
    private long id;

    private String slug;

    private String title;

    private String content ;

    private String description ;

    private String create_at;

    private String published_at;

    private String status;

    public PostDTO() {
    }

    public PostDTO(long id, String slug, String title, String content, String description, String create_at, String published_at, String status) {
        this.id = id;
        this.slug = slug;
        this.title = title;
        this.content = content;
        this.description = description;
        this.create_at = create_at;
        this.published_at = published_at;
        this.status = status;
    }
}
