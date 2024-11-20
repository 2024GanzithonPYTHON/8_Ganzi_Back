package org.pallete.diary.diary.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.pallete.common.BusinessException;
import org.pallete.common.ResponseCode;
import org.pallete.diary.domain.Diary;
import org.pallete.diary.domain.User;
import org.pallete.diary.domain.dto.diaryDto.DiaryRequestDto;
import org.pallete.diary.domain.dto.diaryDto.DiaryResponseDto;
import org.pallete.diary.repository.DiaryRepository;
import org.pallete.diary.repository.UserRepository;
import org.pallete.diary.service.S3Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;
    private final S3Service s3Service;


    public Page<DiaryResponseDto> getList(Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 10;
        LocalDate today = LocalDate.now();
        Page<Diary> DiaryPages = diaryRepository.findAllByCreatedAtAndIsVisibleTrue(today ,
                PageRequest.of(page, pageLimit, Sort.Direction.DESC, "id"));

        Page<DiaryResponseDto> diaryResponseDto = DiaryPages.map(
                diaryPage -> new DiaryResponseDto(
                        diaryPage.getTitle(),
                        diaryPage.getContent(),
                        diaryPage.getCreatedAt(),
                        diaryPage.getDiaryImage()
                ));
        return diaryResponseDto;
    }

    public DiaryResponseDto getDiaryByDate(Long userId, LocalDate date) {
        Diary diary = diaryRepository.findByUserIdAndCreatedAt(userId, date)
                .orElseThrow(() -> new BusinessException(ResponseCode.DIA_DIA_NOT_FOUND));

        return new DiaryResponseDto(diary, date);
    }

    @Transactional
    public DiaryResponseDto createDiary(DiaryRequestDto diaryRequestDto, MultipartFile multipartFile, HttpServletRequest request) throws IOException {
        // 세션에서 이메일 가져오기
        String userEmail = getEmailFromSession(request);  // 세션에서 이메일을 가져오는 메서드 호출

        // 유저 조회
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new BusinessException(ResponseCode.DIA_AUTENTICATION_FAIL));

        // 이미지 처리
        String diaryImage = null;
        if (multipartFile != null && !multipartFile.isEmpty()) {
            diaryImage = s3Service.upload(multipartFile, "diary");
        }

        // 다이어리 저장
        Diary diary = new Diary(diaryRequestDto, user, diaryImage);
        diary = diaryRepository.save(diary); // 다이어리 저장 후 반환

        // 다이어리 응답 DTO 반환
        return new DiaryResponseDto(diary);
    }

    public DiaryResponseDto getDiary(Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new BusinessException(ResponseCode.DIA_DIA_NOT_FOUND));
        return new DiaryResponseDto(diary);
    }

    public List<DiaryResponseDto> getRandomDiaries(){
        LocalDate today = LocalDate.now();
        return diaryRepository.findRandomDiariesTodayAndIsVisibleTrue(today, PageRequest.of(0, 5))
                .stream()
                .map(diary -> new DiaryResponseDto(diary, today))
                .collect(Collectors.toList());
    }

    // 세션에서 이메일 가져오기
    private String getEmailFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userEmail") == null) {
            throw new IllegalStateException("로그인 상태가 아닙니다.");
        }
        return (String) session.getAttribute("userEmail");
    }
}