package p_henriqued.javafxstudy.Servicies.DepartmentService;

import lombok.Getter;
import p_henriqued.javafxstudy.Repositories.Repository.Repository;
import p_henriqued.javafxstudy.models.Department.Department;

import java.util.List;
import java.util.Objects;


@Getter
public class DepartmentService {

    private Repository<Department> repository;

    public DepartmentService() {
        this.repository = new Repository<>(Department.class);
    }

    public List<Department> findAllDepartments(){
        return repository.findAll();
    }

    public void saveOrUpdate(Department department){
        if (Objects.isNull(department.getId())) {
            repository.save(department);
        } else {
            repository.update(department);
        }
    }

}
