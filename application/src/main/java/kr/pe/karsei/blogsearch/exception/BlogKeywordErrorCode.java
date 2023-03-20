package kr.pe.karsei.blogsearch.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BlogKeywordErrorCode {
    BAD_REQUEST_API_REQUEST(HttpStatus.BAD_REQUEST, "요청이 잘못되었습니다."),
    INTERNAL_SERVER_ERROR_API_REQUEST(HttpStatus.INTERNAL_SERVER_ERROR, "원격 API 서버에서 오류가 발생했습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 오류입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    BlogKeywordErrorCode(final HttpStatus httpStatus,
                         final String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
