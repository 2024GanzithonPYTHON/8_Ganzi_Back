package org.pallete.like.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pallete.common.ResponseCode;
import org.pallete.like.application.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping
public class LikeController {

    @Autowired
    private final LikeService likeService;

    @PostMapping("like/{diaryId}")
    public ResponseEntity<String> likeSave(@PathVariable("diaryId") Long diaryId,
                                           HttpServletRequest request) {
        likeService.likeSave(diaryId, request);
        return ResponseEntity.ok("좋아요가 성공적으로 저장되었습니다.");
    }

    @DeleteMapping("unlike/{diaryId}")
    public ResponseEntity<String> likeDelete(@PathVariable("diaryId") Long diaryId,
                                                       HttpServletRequest request) throws IOException {
        likeService.likeDelete(diaryId, request);
        return ResponseEntity.ok("좋아요가 성공적으로 삭제되었습니다.");
    }
}
