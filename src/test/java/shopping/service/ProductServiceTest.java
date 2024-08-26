package shopping.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import shopping.model.Product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    public void testCreateProduct() {
        Product product = new Product("Test Product", 99.99, "http://example.com/image.jpg");

        Product savedProduct = productService.createProduct(product);

        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getId()).isNotNull();
    }

    @Test
    public void testCreateProductWithBadWord() {
        Product product = new Product("fuck Product", 99.99, "http://example.com/image.jpg");

        assertThatThrownBy(() -> productService.createProduct(product))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Product name contains inappropriate language.");
    }

    @Test
    public void testGetProductById() {
        Product product = new Product("Test Product", 99.99, "http://example.com/image.jpg");

        Product savedProduct = productService.createProduct(product);

        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getId()).isNotNull();
        assertThat(savedProduct.getName()).isEqualTo("Test Product");
    }

    @Test
    public void testUpdateProductWithBadWord() {
        Product product = new Product("Test Product", 99.99, "http://example.com/image.jpg");

        Product savedProduct = productService.createProduct(product);
        savedProduct.setName("fuck Update");

        assertThatThrownBy(() -> productService.updateProduct(savedProduct.getId(), savedProduct))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Product name contains inappropriate language.");
    }
}