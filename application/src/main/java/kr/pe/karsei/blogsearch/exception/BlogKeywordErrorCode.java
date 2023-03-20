package kr.pe.karsei.blogsearch.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BlogKeywordErrorCode {
    ERROR_ON_API(HttpStatus.INTERNAL_SERVER_ERROR, "원격 API 서버로 호출하는 과정에서 오류가 발생했습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    BlogKeywordErrorCode(final HttpStatus httpStatus,
                         final String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
