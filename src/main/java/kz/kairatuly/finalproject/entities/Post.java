package kz.kairatuly.finalproject.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "t_posts")
@Getter
@Setter
public class Post extends BaseEntity{

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String  picture;

    private Date createAt;

    private Date updateAt;

    @PrePersist
    protected void prePersist(){
        createAt = new Date();
    }

    @PreUpdate
    protected void preUpdate(){
        updateAt = new Date();
    }

    @ManyToOne(fetch = FetchType.EAGER)
    private User author;
}
