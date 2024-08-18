package shopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shopping.model.Member;
import shopping.service.MemberService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<Member> registerMember(@Valid @RequestBody Member member) {
        return ResponseEntity.ok(memberService.registerMember(member));
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginMember(@RequestParam String email, @RequestParam String password) {
        return memberService.loginMember(email, password)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(401).body("Invalid credentials"));
    }
}