package bank.repository;

import org.springframework.data.repository.CrudRepository;



public interface ProductRepository extends CrudRepository<Product, Long> {

    @Override
    void delete(Product deleted);
}
