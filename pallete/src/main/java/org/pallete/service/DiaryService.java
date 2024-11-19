package org.pallete.service;

import lombok.RequiredArgsConstructor;
import org.pallete.common.BusinessException;
import org.pallete.common.ResponseCode;
import org.pallete.domain.Diary;
import org.pallete.domain.User;
import org.pallete.domain.dto.diaryDto.DiaryRequestDto;
import org.pallete.domain.dto.diaryDto.DiaryResponseDto;
import org.pallete.repository.DiaryRepository;
import org.pallete.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;


    public Page<DiaryResponseDto> getList(Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 10;
        LocalDate today = LocalDate.now();
        Page<Diary> DiaryPages = diaryRepository.findAllByCreatedAt(today ,
                PageRequest.of(page, pageLimit, Sort.Direction.DESC, "id"));

        Page<DiaryResponseDto> diaryResponseDto = DiaryPages.map(
                diaryPage -> new DiaryResponseDto(
                        diaryPage.getTitle(),
                        diaryPage.getContent(),
                        diaryPage.getCreatedAt()));
        return diaryResponseDto;
    }

    public DiaryResponseDto getDiaryByDate(Long userId, LocalDate date) {
        Diary diary = diaryRepository.findByDate(userId, date)
                .orElseThrow(() -> new BusinessException(ResponseCode.DIA_DIA_NOT_FOUND));

        return new DiaryResponseDto(diary, date);
    }



    @Transactional
    public DiaryResponseDto createDiary(DiaryRequestDto diaryRequestDto, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new BusinessException(ResponseCode.DIA_AUTENTICATION_FAIL))

        Diary diary = diaryRepository.save(new Diary(diaryRequestDto, user));
        return new DiaryResponseDto(diary);
    }


    public DiaryResponseDto getDiary(Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new BusinessException(ResponseCode.DIA_DIA_NOT_FOUND));
        return new DiaryResponseDto(diary);
    }
}