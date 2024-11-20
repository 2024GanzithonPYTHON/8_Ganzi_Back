package org.pallete.like.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "인증된 사용자가 diaryId에 따라 좋아요 추가", description = "인증된 사용자가 diaryId에 따라 다른 사람들의 일기에 좋아요를 누릅니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "응답 생성에 성공하였습니다."),
            @ApiResponse(responseCode = "401", description = "인증이 필요합니다.")
    })
    @PostMapping("like/{diaryId}")
    public ResponseEntity<String> likeSave(@PathVariable("diaryId") Long diaryId,
                                           HttpServletRequest request) {
        likeService.likeSave(diaryId, request);
        return ResponseEntity.ok("좋아요가 성공적으로 저장되었습니다.");
    }

    @Operation(summary = "인증된 사용자가 diaryId에 따라 좋아요 취소", description = "인증된 사용자가 diaryId에 따라 다른 사람들의 일기에 좋아요를 취소합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "응답 생성에 성공하였습니다."),
            @ApiResponse(responseCode = "401", description = "인증이 필요합니다.")
    })
    @DeleteMapping("unlike/{diaryId}")
    public ResponseEntity<String> likeDelete(@PathVariable("diaryId") Long diaryId,
                                                       HttpServletRequest request) throws IOException {
        likeService.likeDelete(diaryId, request);
        return ResponseEntity.ok("좋아요가 성공적으로 삭제되었습니다.");
    }
}
