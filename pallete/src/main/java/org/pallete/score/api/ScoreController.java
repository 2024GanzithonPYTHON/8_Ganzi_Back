package org.pallete.score.api;

import lombok.RequiredArgsConstructor;
import org.pallete.diary.domain.dto.Response;
import org.pallete.score.api.dto.request.ScoreSaveReqDto;
import org.pallete.score.api.dto.response.ScoreInfoResDto;
import org.pallete.score.api.dto.response.ScoreListResDto;
import org.pallete.score.application.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/score")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    // 점수 생성
    @PostMapping("/{diaryId}")
    public ResponseEntity<Response<ScoreInfoResDto>> scoreSave(@PathVariable("diaryId") Long diaryId, @RequestBody ScoreSaveReqDto scoreSaveReqDto, Principal principal) {
        ScoreInfoResDto scoreInfoResDto = scoreService.scoreSave(diaryId, scoreSaveReqDto,principal);
        return ResponseEntity.ok(Response.ok(scoreInfoResDto));
    }

    // 사용자 개인의 점수 전체 조회
    @GetMapping
    public ResponseEntity<Response<ScoreListResDto>> scoreFindAll(Principal principal) {
        ScoreListResDto scoreListResDto = scoreService.scoreFindAll(principal);
        return ResponseEntity.ok(Response.ok(scoreListResDto));
    }

    // 사용자 일기에 따른 점수 각각 조회
    @GetMapping("/{diaryId}")
    public ResponseEntity<Response<ScoreInfoResDto>> scoreFindOne(@PathVariable("diaryId") Long diaryId, Principal principal) {
        ScoreInfoResDto scoreInfoResDto = scoreService.scoreFindOne(diaryId, principal);
        return ResponseEntity.ok(Response.ok(scoreInfoResDto));

    }
}
