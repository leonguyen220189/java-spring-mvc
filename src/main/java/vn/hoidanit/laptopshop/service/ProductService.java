package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Optional<Product> fetchProductById(long id) {
        return this.productRepository.findById(id);
    }

    public Product saveProduct(Product product) {
        return this.productRepository.save(product);
    }

    public void deleteProductById(long id) {
        this.productRepository.deleteById(id);
    }

    public List<Product> fetchProducts() {
        return this.productRepository.findAll();
    }
}
