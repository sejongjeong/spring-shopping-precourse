package shopping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shopping.model.Product;
import shopping.repository.ProductRepository;
import shopping.util.BadWordFilter;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final BadWordFilter badWordFilter;

    @Autowired
    public ProductService(ProductRepository productRepository, BadWordFilter badWordFilter) {
        this.productRepository = productRepository;
        this.badWordFilter = badWordFilter;
    }

    public Product createProduct(Product product) {
        if (badWordFilter.containsBadWords(product.getName())) {
            throw new IllegalArgumentException("Product name contains inappropriate language.");
        }
        return productRepository.save(product);
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        if (badWordFilter.containsBadWords(updatedProduct.getName())) {
            throw new IllegalArgumentException("Product name contains inappropriate language.");
        }
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(updatedProduct.getName() != null ? updatedProduct.getName() : product.getName());
                    product.setPrice(updatedProduct.getPrice() != 0 ? updatedProduct.getPrice() : product.getPrice());
                    product.setImageUrl(updatedProduct.getImageUrl() != null ? updatedProduct.getImageUrl() : product.getImageUrl());
                    return productRepository.save(product);
                })
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}