package org.pallete.domain.dto.diaryDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.pallete.domain.Diary;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiaryResponseDto {
    private String title;
    private Long id;
    private String content;
    private LocalDate createdAt;
    private int point;
    private String review;

    public DiaryResponseDto(Diary diary) {
        this.id = diary.getId();
        this.title = diary.getTitle();
        this.content = diary.getContent();
        this.createdAt = diary.getCreatedAt();
    }


    public DiaryResponseDto(String title,
                            String content,
                            LocalDate createdAt) {
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

    public DiaryResponseDto(Diary diary,
                            LocalDate date) {
        this.id = diary.getId();
        this.title = diary.getTitle();
        this.content = diary.getContent();
        this.createdAt = date;
        this.point = diary.getScore().getPoint();
        this.review = diary.getScore().getReview();
    }

}
