package bank.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;

@Data
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "id")})
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String role;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

}
