package org.pallete.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Score {

    @Id @GeneratedValue
    private Long id;

    @JoinColumn(name = "diary_id")
    @OneToOne(fetch = FetchType.LAZY)
    private Diary diary;

    private int point;

    private String review;


}
