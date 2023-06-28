package kz.kairatuly.finalproject.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "t_permissions")
@Getter
@Setter
public class Permission extends BaseEntity implements GrantedAuthority {

    @Column(name = "role")
    private String role;
    @Column(name = "name")
    private String name;

    @Override
    public String getAuthority() {
        return role;
    }
}
