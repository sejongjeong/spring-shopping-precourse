package shopping.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shopping.model.Member;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    public void testRegisterMember() {
        Member member = new Member();
        member.setEmail("test@example.com");
        member.setPassword("password123");

        Member savedMember = memberService.registerMember(member);
        assertThat(savedMember.getId()).isNotNull();
        assertThat(savedMember.getEmail()).isEqualTo("test@example.com");
    }

    @Test
    public void testLoginMember() {
        Member member = new Member();
        member.setEmail("login@example.com");
        member.setPassword("password");
        memberService.registerMember(member);

        Optional<String> token = memberService.loginMember("login@example.com", "password");
        assertThat(token).isPresent();
    }
}