package shopping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shopping.model.Wish;
import shopping.model.Member;
import shopping.model.Product;
import shopping.repository.WishRepository;
import shopping.repository.MemberRepository;
import shopping.repository.ProductRepository;
import java.util.List;
import java.util.Optional;

@Service
public class WishService {

    @Autowired
    private WishRepository wishRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProductRepository productRepository;

    public Wish addWish(Long memberId, Long productId) {
        Optional<Member> member = memberRepository.findById(memberId);
        Optional<Product> product = productRepository.findById(productId);

        if (member.isPresent() && product.isPresent()) {
            Wish wish = new Wish();
            wish.setMember(member.get());
            wish.setProduct(product.get());
            return wishRepository.save(wish);
        } else {
            throw new IllegalArgumentException("Invalid member or product ID");
        }
    }

    public List<Wish> getWishesByMemberId(Long memberId) {
        return wishRepository.findByMemberId(memberId);
    }

    public void deleteWish(Long wishId) {
        wishRepository.deleteById(wishId);
    }
}