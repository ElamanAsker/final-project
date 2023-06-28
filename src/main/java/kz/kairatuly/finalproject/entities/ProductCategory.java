package kz.kairatuly.finalproject.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "t_categories")
@Getter
@Setter
public class ProductCategory extends BaseEntity{
    private String name;
    private String description;
    private String  picture;
}
