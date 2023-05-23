package arbuz.subscriptionapi.repository;

import arbuz.subscriptionapi.entities.product.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
