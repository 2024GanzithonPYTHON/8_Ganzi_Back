package org.pallete.diary.controller;

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


    // 커뮤니티 - 모든 사용자가 전체 일기 한 페이지에 5개씩 조회(최신순)
    @GetMapping("/community")
    public ResponseEntity<Response<Page<DiaryResponseDto>>> getDiaryList(@PageableDefault(page = 1) Pageable pageable) {
        Page<DiaryResponseDto> diaries = diaryService.getList(pageable);
        return ResponseEntity.ok(Response.ok(diaries));
    }

    // 메인 페이지 - 사용자 개인의 일기 전체 조회(로그인 먼저 해야 볼 수 있음)
    @GetMapping("/user")
    public ResponseEntity<Response<DiaryListResDto>> getDiaryByDate(HttpServletRequest request) {
        return ResponseEntity.ok(Response.ok(diaryService.getDiaryByUserEmail(request)));
    }

    //main에서 랜덤 5개 get
    @GetMapping()
    public ResponseEntity<Response<List<DiaryResponseDto>>> getRandomDiaries(){
        List<DiaryResponseDto> diaries = diaryService.getRandomDiaries();
        return ResponseEntity.ok(Response.ok(diaries));
    }

    // 일기 생성
    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response<DiaryResponseDto>> createPost(
                                                                 @Valid @RequestPart("diary") DiaryRequestDto diaryRequestDto,
                                                                 @RequestParam(value = "diaryImage", required=false) MultipartFile diaryImage,
                                                                 HttpServletRequest request) throws IOException {

        DiaryResponseDto diaryResponseDto = diaryService.createDiary(diaryRequestDto, diaryImage, request);
        return ResponseEntity.ok(Response.ok(diaryResponseDto));
    }

    // 다이어리 id에 따른 일기 한 개 조회(모든 사용자 - 커뮤니티에서 해당 일기 누를 떄 팝업으로 해당 일기 한 개 조회)
    @GetMapping("/{diaryId}")
    public ResponseEntity<Response<DiaryResponseDto>> getDiary(@PathVariable Long diaryId){
        return ResponseEntity.ok(Response.ok(diaryService.getDiary(diaryId)));
    }

    // 메인 페이지 - 달력 터치시 -> 사용자 개인의 일기 하나 조회(날짜별)
    @GetMapping("/{userId}/{date}")
    public ResponseEntity<Response<DiaryResponseDto>> getDiaryByDate(@PathVariable Long userId,
                                                                     @PathVariable LocalDate date) {
        return ResponseEntity.ok(Response.ok(diaryService.getDiaryByDate(userId, date)));
    }

    // 레코드 모아보기 - 내가 좋아요 누른 일기 리스트 조회
    @GetMapping("/user/like")
    public ResponseEntity<Response<DiaryListResDto>> getUserLikeDiary(HttpServletRequest request) {
        DiaryListResDto diaryListResDto = diaryService.findDiaryUserLikes(request);
        return ResponseEntity.ok(Response.ok(diaryListResDto));
    }

}
