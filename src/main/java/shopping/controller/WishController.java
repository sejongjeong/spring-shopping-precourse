package shopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shopping.model.Wish;
import shopping.service.WishService;
import java.util.List;

@RestController
@RequestMapping("/api/wishes")
public class WishController {

    @Autowired
    private WishService wishService;

    @PostMapping
    public ResponseEntity<Wish> addWish(@RequestParam Long memberId, @RequestParam Long productId) {
        Wish wish = wishService.addWish(memberId, productId);
        return new ResponseEntity<>(wish, HttpStatus.CREATED);
    }

    @DeleteMapping("/{wishId}")
    public ResponseEntity<Void> deleteWish(@PathVariable Long wishId) {
        wishService.deleteWish(wishId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<Wish>> getWishes(@RequestParam Long memberId) {
        List<Wish> wishes = wishService.getWishesByMemberId(memberId);
        return new ResponseEntity<>(wishes, HttpStatus.OK);
    }
}