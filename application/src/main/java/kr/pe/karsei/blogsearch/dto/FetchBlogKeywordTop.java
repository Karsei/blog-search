package kr.pe.karsei.blogsearch.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FetchBlogKeywordTop {
    /**
     * 키워드
     */
    private String keyword;

    /**
     * 검색한 횟수
     */
    private Integer hit;
}
