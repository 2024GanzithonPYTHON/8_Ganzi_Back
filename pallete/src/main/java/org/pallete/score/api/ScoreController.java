package org.pallete.score.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
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

    @Operation(summary = "인증된 사용자가 diaryId에 따라 점수를 생성", description = "인증된 사용자가 diaryId에 따라 해당 일기에 점수를 추가합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "응답 생성에 성공하였습니다."),
            @ApiResponse(responseCode = "401", description = "인증이 필요합니다.")
    })
    @PostMapping("/{diaryId}")
    public ResponseEntity<Response<ScoreInfoResDto>> scoreSave(@PathVariable("diaryId") Long diaryId, @RequestBody ScoreSaveReqDto scoreSaveReqDto, HttpServletRequest request) {
        ScoreInfoResDto scoreInfoResDto = scoreService.scoreSave(diaryId, scoreSaveReqDto,request);
        return ResponseEntity.ok(Response.ok(scoreInfoResDto));
    }

    @Operation(summary = "인증된 사용자가 자신의 점수를 전체 조회", description = "인증된 사용자가 자신의 점수를 전체 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "응답 생성에 성공하였습니다."),
            @ApiResponse(responseCode = "401", description = "인증이 필요합니다.")
    })
    @GetMapping
    public ResponseEntity<Response<ScoreListResDto>> scoreFindAll(HttpServletRequest request) {
        ScoreListResDto scoreListResDto = scoreService.scoreFindAll(request);
        return ResponseEntity.ok(Response.ok(scoreListResDto));
    }

    @Operation(summary = "인증된 사용자가 자신의 일기에 따라 점수를 각각 한 개씩 조회", description = "인증된 사용자가 diaryId에 따라 자신의 점수를 한 개씩 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "응답 생성에 성공하였습니다."),
            @ApiResponse(responseCode = "401", description = "인증이 필요합니다.")
    })
    @GetMapping("/{diaryId}")
    public ResponseEntity<Response<ScoreInfoResDto>> scoreFindOne(@PathVariable("diaryId") Long diaryId, HttpServletRequest request) {
        ScoreInfoResDto scoreInfoResDto = scoreService.scoreFindOne(diaryId, request);
        return ResponseEntity.ok(Response.ok(scoreInfoResDto));

    }
}
