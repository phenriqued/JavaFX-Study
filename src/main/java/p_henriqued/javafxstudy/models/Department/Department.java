package p_henriqued.javafxstudy.models.Department;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Department {

    private Long id;
    private String name;

}
