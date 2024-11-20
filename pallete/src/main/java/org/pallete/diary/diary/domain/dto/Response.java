package org.pallete.diary.diary.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pallete.common.ResponseCode;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {
    @NotNull
    private Integer status;
    @NotNull
    private String code;
    @NotNull
    private String message;
    private T data;

    /**
     * 성공응답
     */
    public static <T> Response<T> ok (){
        Response<T> response = new Response<>();
        response.status = ResponseCode.SUCCESS.getStatus();
        response.code = ResponseCode.SUCCESS.getCode();
        response.message = ResponseCode.SUCCESS.getMessage();
        return response;
    }

    public static <T> Response<T> ok (T data){
        Response<T> response = new Response<>();
        response.status = ResponseCode.SUCCESS.getStatus();
        response.code = ResponseCode.SUCCESS.getCode();
        response.message = ResponseCode.SUCCESS.getMessage();
        response.data = data;
        return response;
    }

}
