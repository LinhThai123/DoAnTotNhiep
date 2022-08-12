package vn.techmaster.doan_woodshop.entity;

import lombok.Getter;
import lombok.Setter;
import vn.techmaster.doan_woodshop.model.dto.PostDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Setter
@Getter
@Entity
@Table(name = "post")
public class Post extends BaseEntity implements Serializable {

    @Column(name = "title", nullable = false, length = 300)
    private String title;

    @Column(name = "slug", nullable = false, length = 600)
    private String slug;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private BlogStatus status;

    @Column(name = "published_at")
    private Timestamp publishedAt;

    public Post() {
    }

    public Post(String id, Timestamp createdAt, Timestamp modifiedAt, User createdBy, User modifiedBy, String title, String slug, String description, String content, String thumbnail, BlogStatus status, Timestamp publishedAt) {
        super(id, createdAt, modifiedAt, createdBy, modifiedBy);
        this.title = title;
        this.slug = slug;
        this.description = description;
        this.content = content;
        this.thumbnail = thumbnail;
        this.status = status;
        this.publishedAt = publishedAt;
    }

    @Transient
    public String getPostImagePath() {
        if(thumbnail == null || super.getId() == null ) return null ;
        return "/post-photos/" + this.thumbnail;
    }
}
