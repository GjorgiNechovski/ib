package mk.ukim.finki.ib.lab.repository;

import mk.ukim.finki.ib.lab.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
