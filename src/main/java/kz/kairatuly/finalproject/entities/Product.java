package kz.kairatuly.finalproject.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "t_products")
@Getter
@Setter
public class Product extends BaseEntity{

    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    private int price;
    private int amount;
    private String promoCode;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<ProductCategory> categories;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Pictures> pictures;

    @PrePersist
    protected void prePersist(){
        promoCode = UUID.randomUUID().toString();
    }
}
