package p_henriqued.javafxstudy.Servicies.DepartmentService;

import lombok.Getter;
import p_henriqued.javafxstudy.Repositories.Repository.Repository;
import p_henriqued.javafxstudy.models.Department.Department;

import java.util.List;


@Getter
public class DepartmentService {

    private Repository<Department> repository;

    public DepartmentService() {
        this.repository = new Repository<Department>();
    }

    public List<Department> findAllDepartments(){
        return repository.findAll(Department.class);
    }

}
