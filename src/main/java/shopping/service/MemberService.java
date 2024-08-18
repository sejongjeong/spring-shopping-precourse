package shopping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shopping.model.Member;
import shopping.repository.MemberRepository;
import shopping.util.JwtUtil;

import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public Member registerMember(Member member) {
        return memberRepository.save(member);
    }

    public Optional<String> loginMember(String email, String password) {
        return memberRepository.findByEmail(email)
                .filter(member -> member.getPassword().equals(password))
                .map(member -> JwtUtil.generateToken(member.getEmail()));
    }
}