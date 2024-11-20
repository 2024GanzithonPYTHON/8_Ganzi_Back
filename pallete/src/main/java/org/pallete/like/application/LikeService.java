package org.pallete.like.application;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pallete.common.BusinessException;
import org.pallete.common.ResponseCode;
import org.pallete.diary.domain.Diary;
import org.pallete.diary.repository.DiaryRepository;
import org.pallete.like.domain.Like;
import org.pallete.like.domain.repository.LikeRepository;
import org.pallete.login.model.User;
import org.pallete.login.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.Optional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;

    // 일기에 좋아요 생성
    @Transactional
    public void likeSave(@PathVariable("diaryId") Long diaryId, HttpServletRequest request) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow( () -> new IllegalArgumentException("해당하는 일기가 없습니다."));

        String userEmail = getEmailFromSession(request);
        User user = userRepository.findByEmail(userEmail);

        if(likeRepository.findByDiaryAndUser(diary, user).isPresent()) {
            throw new BusinessException(ResponseCode.ALREADY_LIKE_DIARY);
        }

        Like like = Like.of(diary, user);
        likeRepository.save(like);

        diary.saveLikeCount();
        diaryRepository.save(diary);
    }

    // 일기에 좋아요 취소
    @Transactional
    public void likeDelete(@PathVariable("diaryId") Long diaryId, HttpServletRequest request) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow( () -> new IllegalArgumentException("해당하는 일기가 없습니다."));

        String userEmail = getEmailFromSession(request);
        User user = userRepository.findByEmail(userEmail);

        // 사용자가 좋아요를 누르지 않았을 때 -> likeCount가 이미 0일 때 또 삭제를 시도하면 좋아요를 누르지 않았다고 에러 줌
        Optional<Like> existingLikes = likeRepository.findByDiaryAndUser(diary, user);
        if (existingLikes.isEmpty()) {
            throw new BusinessException(ResponseCode.LIKE_NOT_FOUND);
        }
        Like like = existingLikes.get();
        likeRepository.delete(like);

        // diary의 like count 감소
        diary.deleteLikeCount();
        diaryRepository.save(diary);
    }

    // 세션에서 사용자 이메일 정보 가져오기
    private String getEmailFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userEmail") == null) {
            throw new IllegalStateException("로그인 상태가 아닙니다.");
        }
        return (String) session.getAttribute("userEmail");
    }
}
