package org.pallete.domain;

import jakarta.persistence.*;

public class Like {

    @Id @GeneratedValue
    private Long id;

    @JoinColumn(name = "diary_id")
    private Diary diary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


}
