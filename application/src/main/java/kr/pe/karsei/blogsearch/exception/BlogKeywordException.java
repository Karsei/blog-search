package kr.pe.karsei.blogsearch.exception;

import lombok.Getter;

@Getter
public class BlogKeywordException extends RuntimeException {
    private final BlogKeywordErrorCode errorCode;

    public BlogKeywordException(final BlogKeywordErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public BlogKeywordException(final BlogKeywordErrorCode errorCode, final Throwable e) {
        super(errorCode.getMessage(), e);
        this.errorCode = errorCode;
    }
}
