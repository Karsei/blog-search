package kr.pe.karsei.blogsearch.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FetchBlogKeywordTop {
    /**
     * 키워드
     */
    private final String keyword;

    /**
     * 검색한 횟수
     */
    private final int hit;

    @Builder
    public FetchBlogKeywordTop(final String keyword,
                               final int hit) {
        this.keyword = keyword;
        this.hit = hit;
    }
}
