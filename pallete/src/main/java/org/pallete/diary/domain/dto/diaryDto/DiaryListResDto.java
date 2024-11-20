package org.pallete.diary.domain.dto.diaryDto;

import lombok.Builder;

import java.util.List;

@Builder
public record DiaryListResDto(
        List<DiaryResponseDto> diaries
) {
    public static DiaryListResDto from(List<DiaryResponseDto> diaries) {
        return DiaryListResDto.builder()
                .diaries(diaries)
                .build();
    }
}
