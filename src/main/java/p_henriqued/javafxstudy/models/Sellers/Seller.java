package p_henriqued.javafxstudy.models.Sellers;


import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import p_henriqued.javafxstudy.models.Department.Department;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity(name = "Seller")
@Table(name = "seller")

@NoArgsConstructor
@Getter @Setter
@EqualsAndHashCode(of = "id")
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private LocalDate birthDate;
    private BigDecimal baseSalary;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

}
