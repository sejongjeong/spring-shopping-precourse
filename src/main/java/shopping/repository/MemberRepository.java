package shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping.model.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
}