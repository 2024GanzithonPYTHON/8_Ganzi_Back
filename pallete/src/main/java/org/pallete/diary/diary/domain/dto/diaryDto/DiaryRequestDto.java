package org.pallete.diary.diary.domain.dto.diaryDto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DiaryRequestDto {

    @NotNull
    private String title;

    @NotNull
    private String content;

    private boolean isVisible;

    private String diaryImage;

    /**
     * 시큐리티 merge시 없애줄 필드.
     */
    private String userEmail;
}
