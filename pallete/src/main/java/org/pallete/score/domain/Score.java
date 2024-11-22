package org.pallete.score.domain;

import jakarta.persistence.*;
import lombok.*;
import org.pallete.common.BaseTimeEntity;
import org.pallete.diary.domain.Diary;
import org.pallete.login.model.User;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Score extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "score_id", nullable = false)
    private Long id;

    private int score;
    private String review;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "diary_id")
    private Diary diary;

    @Builder
    public Score(int score, String review, User user, Diary diary) {
        this.score = score;
        this.review = review;
        this.user = user;
        this.diary = diary;
    }

}
