package org.pallete.diary.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.pallete.diary.domain.dto.Response;
import org.pallete.diary.domain.dto.diaryDto.DiaryListResDto;
import org.pallete.diary.domain.dto.diaryDto.DiaryRequestDto;
import org.pallete.diary.domain.dto.diaryDto.DiaryResponseDto;
import org.pallete.diary.service.DiaryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
public class DiaryController {

    private final DiaryService diaryService;

    @Operation(summary = "모든 사용자가 전체 일기 5개씩 조회", description = "커뮤니티 - 모든 사용자가 전체 일기를 한 페이지에 5개씩 최신순으로 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "응답 생성에 성공하였습니다."),
            @ApiResponse(responseCode = "401", description = "인증이 필요합니다.")
    })
    @GetMapping("/community")
    public ResponseEntity<Response<Page<DiaryResponseDto>>> getDiaryList(@PageableDefault(page = 1) Pageable pageable) {
        Page<DiaryResponseDto> diaries = diaryService.getList(pageable);
        return ResponseEntity.ok(Response.ok(diaries));
    }

    @Operation(summary = "인증된 사용자가 각자의 일기를 전체 조회", description = "메인 페이지 - 인증된 사용자가 각자의 일기를 전체 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "응답 생성에 성공하였습니다."),
            @ApiResponse(responseCode = "401", description = "인증이 필요합니다.")
    })
    @GetMapping("/user")
    public ResponseEntity<Response<DiaryListResDto>> getDiaryByDate(HttpServletRequest request) {
        return ResponseEntity.ok(Response.ok(diaryService.getDiaryByUserEmail(request)));
    }

    @Operation(summary = "모든 사용자가 랜덤하게 오늘의 5개 일기 조회", description = "메인 페이지 상단 레코드 - 모든 사용자가 랜덤하게 오늘의 5개 일기를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "응답 생성에 성공하였습니다."),
            @ApiResponse(responseCode = "401", description = "인증이 필요합니다.")
    })
    @GetMapping()
    public ResponseEntity<Response<List<DiaryResponseDto>>> getRandomDiaries(){
        List<DiaryResponseDto> diaries = diaryService.getRandomDiaries();
        return ResponseEntity.ok(Response.ok(diaries));
    }

    @Operation(summary = "인증된 사용자가 일기 생성", description = "인증된 사용자가 일기를 생성합니다.(제목, 내용, 사진)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "응답 생성에 성공하였습니다."),
            @ApiResponse(responseCode = "401", description = "인증이 필요합니다.")
    })
    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response<DiaryResponseDto>> createPost(
                                                                 @Valid @RequestPart("diary") DiaryRequestDto diaryRequestDto,
                                                                 @RequestParam(value = "diaryImage", required=false) MultipartFile diaryImage,
                                                                 HttpServletRequest request) throws IOException {

        DiaryResponseDto diaryResponseDto = diaryService.createDiary(diaryRequestDto, diaryImage, request);
        return ResponseEntity.ok(Response.ok(diaryResponseDto));
    }

    @Operation(summary = "모든 사용자가 일기 id에 따라 해당 일기를 한 개 조회", description = "커뮤니티(팝업) - 모든 사용자가 일기 id에 따라 해당 일기를 한 개 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "응답 생성에 성공하였습니다."),
            @ApiResponse(responseCode = "401", description = "인증이 필요합니다.")
    })
    @GetMapping("/{diaryId}")
    public ResponseEntity<Response<DiaryResponseDto>> getDiary(@PathVariable Long diaryId){
        return ResponseEntity.ok(Response.ok(diaryService.getDiary(diaryId)));
    }

    @Operation(summary = "인증된 사용자가 날짜별로 각자의 일기 하나 조회", description = "메인 페이지(달력) - 인증된 사용자가 날짜별로 각자의 일기를 한 개 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "응답 생성에 성공하였습니다."),
            @ApiResponse(responseCode = "401", description = "인증이 필요합니다.")
    })
    @GetMapping("/date")
    public ResponseEntity<Response<DiaryResponseDto>> getDiaryByDate(HttpServletRequest request,
                                                                     @RequestParam LocalDate date) {
        return ResponseEntity.ok(Response.ok(diaryService.getDiaryByDate(request, date)));
    }

    @Operation(summary = "인증된 사용자가 자신이 좋아요 누른 일기 리스트 조회", description = "레코드 모아보기 - 인증된 사용자가 자신이 좋아요 누른 일기 리스트를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "응답 생성에 성공하였습니다."),
            @ApiResponse(responseCode = "401", description = "인증이 필요합니다.")
    })
    @GetMapping("/user/like")
    public ResponseEntity<Response<DiaryListResDto>> getUserLikeDiary(HttpServletRequest request) {
        DiaryListResDto diaryListResDto = diaryService.findDiaryUserLikes(request);
        return ResponseEntity.ok(Response.ok(diaryListResDto));
    }

    @Operation(summary = "사용자가 작성한 점수가 가장 높은 최신 일기 조회", description = "사용자가 작성한 점수가 가장 높은 일기를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "응답 생성에 성공하였습니다."),
            @ApiResponse(responseCode = "401", description = "인증이 필요합니다."),
            @ApiResponse(responseCode = "404", description = "해당 사용자의 일기가 없습니다.")
    })
    @GetMapping("/top")
    public ResponseEntity<Response<DiaryResponseDto>> getTopDiary(HttpServletRequest request) {
        DiaryResponseDto topDiary = diaryService.getTopDiary(request);
        return ResponseEntity.ok(Response.ok(topDiary));
    }

}
