package mk.ukim.finki.ib.lab.services.interfaces;

import mk.ukim.finki.ib.lab.models.Product;
import mk.ukim.finki.ib.lab.models.User;

import java.util.List;

public interface IProductService {
    List<Product> findAll();
    void addProduct(String name, int price, User studio);
}
