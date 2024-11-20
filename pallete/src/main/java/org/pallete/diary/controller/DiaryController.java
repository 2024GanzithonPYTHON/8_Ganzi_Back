package org.pallete.diary.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.pallete.diary.domain.dto.Response;
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


    @GetMapping("/community")
    public ResponseEntity<Response<Page<DiaryResponseDto>>> getDiaryList(@PageableDefault(page = 1) Pageable pageable) {
        Page<DiaryResponseDto> diaries = diaryService.getList(pageable);
        return ResponseEntity.ok(Response.ok(diaries));
    }

    @GetMapping("/user/{userId}/date/{date}")
    public ResponseEntity<Response<DiaryResponseDto>> getDiaryByDate(@PathVariable Long userId,
                                                                     @PathVariable LocalDate date) {
        return ResponseEntity.ok(Response.ok(diaryService.getDiaryByDate(userId, date)));
    }

    //main에서 랜덤 5개 get
    @GetMapping()
    public ResponseEntity<Response<List<DiaryResponseDto>>> getRandomDiaries(){
        List<DiaryResponseDto> diaries = diaryService.getRandomDiaries();
        return ResponseEntity.ok(Response.ok(diaries));
    }


    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response<DiaryResponseDto>> createPost(
                                                                 @Validated DiaryRequestDto diaryRequestDto,
                                                                 @RequestParam(value = "diaryImage", required=false) MultipartFile diaryImage,
                                                                 HttpServletRequest request) throws IOException {

        DiaryResponseDto diaryResponseDto = diaryService.createDiary(diaryRequestDto, diaryImage, request);
        return ResponseEntity.ok(Response.ok(diaryResponseDto));
    }

    @GetMapping("/{diaryId}")
    public ResponseEntity<Response<DiaryResponseDto>> getDiary(@PathVariable Long diaryId){
        return ResponseEntity.ok(Response.ok(diaryService.getDiary(diaryId)));
    }
}
