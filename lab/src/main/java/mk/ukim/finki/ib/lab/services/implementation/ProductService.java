package mk.ukim.finki.ib.lab.services.implementation;

import mk.ukim.finki.ib.lab.models.Product;
import mk.ukim.finki.ib.lab.models.User;
import mk.ukim.finki.ib.lab.repository.ProductRepository;
import mk.ukim.finki.ib.lab.services.interfaces.IProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAll() {
        return this.productRepository.findAll();
    }

    @Override
    public void addProduct(String name, int price, User studio) {
        Product product = new Product(name,price,studio);

        this.productRepository.save(product);
    }
}
