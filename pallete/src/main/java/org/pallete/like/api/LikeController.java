package org.pallete.like.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pallete.common.ResponseCode;
import org.pallete.diary.domain.dto.Response;
import org.pallete.like.application.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping
public class LikeController {

    @Autowired
    private final LikeService likeService;

    @PostMapping("like/{diaryId}")
    public ResponseEntity<ResponseCode> likeSave(@PathVariable("diaryId") Long diaryId,
                                                 Principal principal) {
        likeService.likeSave(diaryId, principal);
        return ResponseEntity.ok(ResponseCode.LIKE_SAVE_SUCCESS);
    }

    @DeleteMapping("unlike/{diaryId}")
    public ResponseEntity<ResponseCode> likeDelete(@PathVariable("diaryId") Long diaryId,
                                                       Principal principal) throws IOException {
        likeService.likeDelete(diaryId, principal);
        return ResponseEntity.ok(ResponseCode.LIKE_DELETE_SUCCESS);
    }
}
