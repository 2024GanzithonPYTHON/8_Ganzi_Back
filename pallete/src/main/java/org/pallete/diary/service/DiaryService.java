package org.pallete.diary.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.pallete.common.BusinessException;
import org.pallete.common.ResponseCode;
import org.pallete.diary.domain.Diary;
import org.pallete.diary.domain.dto.diaryDto.DiaryListResDto;
import org.pallete.diary.domain.dto.diaryDto.DiaryRequestDto;
import org.pallete.diary.domain.dto.diaryDto.DiaryResponseDto;
import org.pallete.diary.repository.DiaryRepository;
import org.pallete.login.model.User;
import org.pallete.login.repository.UserRepository;
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
                        diaryPage.getId(),
                        diaryPage.getTitle(),
                        diaryPage.getContent(),
                        diaryPage.getCreatedAt(),
                        diaryPage.getDiaryImage()
                ));
        return diaryResponseDto;
    }

    public DiaryListResDto getDiaryByUserEmail(HttpServletRequest request) {
        String userEmail = getEmailFromSession(request);
        List<DiaryResponseDto> diaryResponseDtoList = diaryRepository.findByUserEmail(userEmail)
                .stream().map(DiaryResponseDto::from)
                .toList();

        return DiaryListResDto.from(diaryResponseDtoList);
    }

    @Transactional
    public DiaryResponseDto createDiary(DiaryRequestDto diaryRequestDto, MultipartFile multipartFile, HttpServletRequest request) throws IOException {
        String userEmail = getEmailFromSession(request);

        User user = userRepository.findByEmail(userEmail);

        // 이미지 처리
        String diaryImage = null;
        if (multipartFile != null && !multipartFile.isEmpty()) {
            diaryImage = s3Service.upload(multipartFile, "diary");
        }

        Boolean isVisible = diaryRequestDto.getIsVisible() != null ? diaryRequestDto.getIsVisible() : true;

        Diary diary = new Diary(diaryRequestDto, user, diaryImage);
        diary.setIsVisible(isVisible);
        diary = diaryRepository.save(diary);

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

    // 사용자의 날짜별 일기 조회
    public DiaryResponseDto getDiaryByDate(Long userId, LocalDate date) {
        Diary diary = diaryRepository.findByUserIdAndCreatedAt(userId, date)
                .orElseThrow(() -> new BusinessException(ResponseCode.DIA_DIA_NOT_FOUND));

        return new DiaryResponseDto(diary, date);
    }

    // 사용자가 좋아요 누른 일기 리스트 조회
    @Transactional
    public DiaryListResDto findDiaryUserLikes(HttpServletRequest request) {
        String userEmail = getEmailFromSession(request);
        User user = userRepository.findByEmail(userEmail);

        // 사용자가 좋아요를 누른 Diary 조회
        List<Diary> diaries = diaryRepository.findDiariesLikedByUserEmail(userEmail);

        List<DiaryResponseDto> diaryResponseDtoList = diaries.stream()
                .map(DiaryResponseDto::from)
                .collect(Collectors.toList());

        return DiaryListResDto.from(diaryResponseDtoList);
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