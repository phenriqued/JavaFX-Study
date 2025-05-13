package p_henriqued.javafxstudy.models.Department;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Department")
@Table(name = "department")

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

}
