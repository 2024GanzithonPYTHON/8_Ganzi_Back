package org.pallete.diary.domain.dto.diaryDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.pallete.diary.domain.Diary;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiaryResponseDto {
    private String title;
    private Long id;
    private String content;
    private LocalDate createdAt;
    private boolean isVisible;
    private String diaryImage;

    public DiaryResponseDto(Diary diary) {
        this.id = diary.getId();
        this.title = diary.getTitle();
        this.content = diary.getContent();
        this.createdAt = diary.getCreatedAt();
        this.isVisible = diary.getIsVisible();
        this.diaryImage = diary.getDiaryImage();
    }

    public DiaryResponseDto(Long id, String title,
                            String content,
                            LocalDate createdAt, String diaryImage) {
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.diaryImage = diaryImage;
    }

    public DiaryResponseDto(Diary diary,
                            LocalDate date) {
        this.id = diary.getId();
        this.title = diary.getTitle();
        this.content = diary.getContent();
        this.createdAt = date;
    }

    public static DiaryResponseDto from(Diary diary) {
        return new DiaryResponseDto(
                diary.getId(),
                diary.getTitle(),
                diary.getContent(),
                diary.getCreatedAt(),
                diary.getDiaryImage()
        );
    }

}
