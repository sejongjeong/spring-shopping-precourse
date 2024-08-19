package shopping.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shopping.model.Product;
import shopping.util.BadWordFilter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    public void testCreateProduct() {
        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(99.99);
        product.setImageUrl("http://example.com/image.jpg");

        Product savedProduct = productService.createProduct(product);

        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getId()).isNotNull();
    }

    @Test
    public void testCreateProductWithBadWord() {
        Product product = new Product();
        product.setName("fuck Product");
        product.setPrice(99.99);
        product.setImageUrl("http://example.com/image.jpg");

        assertThatThrownBy(() -> productService.createProduct(product))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Product name contains inappropriate language.");
    }

    @Test
    public void testGetProductById() {
        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(99.99);
        product.setImageUrl("http://example.com/image.jpg");

        Product savedProduct = productService.createProduct(product);

        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getId()).isNotNull();
        assertThat(savedProduct.getName()).isEqualTo("Test Product");
    }

    @Test
    public void testUpdateProductWithBadWord() {
        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(99.99);
        product.setImageUrl("http://example.com/image.jpg");

        Product savedProduct = productService.createProduct(product);

        savedProduct.setName("fuck Update");

        assertThatThrownBy(() -> productService.updateProduct(savedProduct.getId(), savedProduct))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Product name contains inappropriate language.");
    }
}