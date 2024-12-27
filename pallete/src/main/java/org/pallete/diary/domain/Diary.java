package org.pallete.diary.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.pallete.diary.domain.dto.diaryDto.DiaryRequestDto;
import org.pallete.like.domain.Like;
import org.pallete.login.model.User;
import org.pallete.score.domain.Score;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Diary {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "score_id")
    private Score score;

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    private boolean isVisible = true;

    private LocalDate createdAt;

    private Integer likeCount = 0;

    private String diaryImage;

    public Diary(DiaryRequestDto diaryRequestDto, User user, String diaryImage, LocalDate createdAt) {
        this.user = user;
        this.title = diaryRequestDto.getTitle();
        this.content = diaryRequestDto.getContent();
        this.diaryImage = diaryImage;
        this.createdAt = createdAt;
    }

    // 인증된 사용자 - 좋아요 개수 증가, 감소
    public void saveLikeCount() {
        this.likeCount++;
    }

    public void deleteLikeCount() {
        if (this.likeCount > 0)
            this.likeCount--;
    }
}
