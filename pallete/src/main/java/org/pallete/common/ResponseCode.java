package org.pallete.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    //성공
    SUCCESS(200, "COM-000", "OK", HttpStatus.OK),


    //diary 실패
    DIA_AUTENTICATION_FAIL(400,"DIA-001","유저 인증 실패", HttpStatus.BAD_REQUEST),
    DIA_DIA_NOT_FOUND(400,"DIA-002", "게시물 찾지 못함", HttpStatus.BAD_REQUEST);

    final Integer status;
    final String code;
    final String message;
    final HttpStatus httpStatus;

}
