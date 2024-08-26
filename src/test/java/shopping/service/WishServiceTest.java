package shopping.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import shopping.model.Wish;
import shopping.model.Member;
import shopping.model.Product;
import shopping.repository.MemberRepository;
import shopping.repository.ProductRepository;
import shopping.repository.WishRepository;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class WishServiceTest {

    @Autowired
    private WishService wishService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private WishRepository wishRepository;

    @Test
    public void testAddWish() {
        Member member = new Member();
        member.setEmail("test@test.com");
        member.setPassword("password");
        member = memberRepository.save(member);

        Product product = new Product();
        product.setName("Product 1");
        product.setPrice(1000);
        product.setImageUrl("http://example.com/image1.jpg");
        product = productRepository.save(product);

        Wish wish = wishService.addWish(member.getId(), product.getId());
        assertThat(wish).isNotNull();
        assertThat(wish.getMember().getId()).isEqualTo(member.getId());
        assertThat(wish.getProduct().getId()).isEqualTo(product.getId());
    }

    @Test
    public void testGetWishesByMemberId() {
        Member member = new Member();
        member.setEmail("test@test.com");
        member.setPassword("password");
        member = memberRepository.save(member);

        Product product1 = new Product();
        product1.setName("Product 1");
        product1.setPrice(1000);
        product1.setImageUrl("http://example.com/image1.jpg");
        product1 = productRepository.save(product1);

        Product product2 = new Product();
        product2.setName("Product 2");
        product2.setPrice(2000);
        product2.setImageUrl("http://example.com/image2.jpg");
        product2 = productRepository.save(product2);

        wishService.addWish(member.getId(), product1.getId());
        wishService.addWish(member.getId(), product2.getId());

        List<Wish> wishes = wishService.getWishesByMemberId(member.getId());
        assertThat(wishes).hasSize(2);
    }

    @Test
    public void testDeleteWish() {
        Member member = new Member();
        member.setEmail("test@test.com");
        member.setPassword("password");
        member = memberRepository.save(member);

        Product product = new Product();
        product.setName("Product 1");
        product.setPrice(1000);
        product.setImageUrl("http://example.com/image1.jpg");
        product = productRepository.save(product);

        Wish wish = wishService.addWish(member.getId(), product.getId());
        wishService.deleteWish(wish.getId());

        assertThat(wishRepository.findById(wish.getId())).isEmpty();
    }

    @Test
    public void testAddWishWithInvalidMemberOrProduct() {
        assertThrows(IllegalArgumentException.class, () -> {
            wishService.addWish(999L, 999L);
        });
    }
}