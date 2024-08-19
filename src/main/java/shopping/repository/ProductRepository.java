package shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}