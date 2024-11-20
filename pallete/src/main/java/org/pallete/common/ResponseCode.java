package org.pallete.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    //성공
    SUCCESS(200, "COM-000", "OK", HttpStatus.OK),
    LIKE_SAVE_SUCCESS(200, "LIKE-001","좋아요가 성공적으로 저장되었습니다.", HttpStatus.OK),
    LIKE_DELETE_SUCCESS(200, "LIKE-002", "좋아요가 성공적으로 삭제되었습니다.", HttpStatus.OK),

    //diary 실패
    DIA_AUTENTICATION_FAIL(400,"DIA-001","유저 인증 실패", HttpStatus.BAD_REQUEST),
    DIA_DIA_NOT_FOUND(400,"DIA-002", "게시물 찾지 못함", HttpStatus.BAD_REQUEST),

    // s3 이미지 관련 예외
    FILE_NOT_FOUND(400, "FILE-01", "파일을 찾지 못 함", HttpStatus.BAD_REQUEST),

    // like 예외
    ALREADY_LIKE_DIARY(400, "LIKE-002", "이미 좋아요를 누른 일기", HttpStatus.BAD_REQUEST),
    LIKE_NOT_FOUND(400, "LIKE-003", "좋아요를 찾을 수 없음", HttpStatus.BAD_REQUEST);

    final Integer status;
    final String code;
    final String message;
    final HttpStatus httpStatus;

}
