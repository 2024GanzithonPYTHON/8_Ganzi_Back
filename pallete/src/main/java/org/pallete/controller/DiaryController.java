package org.pallete.controller;

import lombok.RequiredArgsConstructor;
import org.pallete.domain.dto.Response;
import org.pallete.domain.dto.diaryDto.DiaryRequestDto;
import org.pallete.domain.dto.diaryDto.DiaryResponseDto;
import org.pallete.service.DiaryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class DiaryController {

    private final DiaryService diaryService;


    @GetMapping("/community")
    public ResponseEntity<Response<Page<DiaryResponseDto>>> getDiaryList(@PageableDefault(page = 1) Pageable pageable,
                                       @RequestBody DiaryRequestDto diaryRequestDto) {
        Page<DiaryResponseDto> diaries = diaryService.getList(pageable);
        return ResponseEntity.ok(Response.ok(diaries));
    }

    @GetMapping("/user/{userId}/date/{date}")
    public ResponseEntity<Response<DiaryResponseDto>> getDiaryByDate(@PathVariable Long userId,
                                                                     @PathVariable LocalDate date) {
        return ResponseEntity.ok(Response.ok(diaryService.getDiaryByDate(userId, date)));
    }



    @PostMapping()
    public ResponseEntity<Response<DiaryResponseDto>> createPost(@Validated @RequestBody DiaryRequestDto diaryRequestDto){

        String userEmail = diaryRequestDto.getUserEmail();
        DiaryResponseDto diaryResponseDto = diaryService.createDiary(diaryRequestDto, userEmail);
        return ResponseEntity.ok(Response.ok(diaryResponseDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<DiaryResponseDto>> getDiary(@PathVariable Long diaryId){
        return ResponseEntity.ok(Response.ok(diaryService.getDiary(diaryId)));
    }
}
