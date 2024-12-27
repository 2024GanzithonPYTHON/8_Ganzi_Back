package org.pallete.diary.domain.dto.diaryDto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @Size(max = 500, message = "500자 이상 작성할 수 없습니다.")
    private String content;

    private boolean isVisible;

    private int year;
    private int month;
    private int dayOfMonth;

    public Boolean getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(Boolean isVisible) {
        this.isVisible = isVisible;
    }
}
