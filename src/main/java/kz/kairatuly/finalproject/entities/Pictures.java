package kz.kairatuly.finalproject.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "t_pictures")
@Getter
@Setter
public class Pictures extends BaseEntity{

    private String image;
}
