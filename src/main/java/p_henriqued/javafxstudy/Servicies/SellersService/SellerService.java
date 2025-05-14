package p_henriqued.javafxstudy.Servicies.SellersService;

import lombok.Getter;
import p_henriqued.javafxstudy.Repositories.Repository.Repository;
import p_henriqued.javafxstudy.models.Sellers.Seller;

import java.util.List;
import java.util.Objects;

@Getter
public class SellerService {

    private Repository<Seller> repository;

    public SellerService() {
        this.repository = new Repository<>(Seller.class);
    }

    public List<Seller> findAllDepartments(){
        return repository.findAll();
    }

    public void saveOrUpdate(Seller seller){
        if (Objects.isNull(seller.getId())) {
            repository.save(seller);
        } else {
            repository.update(seller);
        }
    }

    public void remove(Long id){
        repository.delete(id);
    }
}
