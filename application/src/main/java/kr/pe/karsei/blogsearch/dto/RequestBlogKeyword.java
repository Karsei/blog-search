package kr.pe.karsei.blogsearch.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestBlogKeyword {
    /**
     * 검색 키워드
     */
    @NotBlank
    private String query;

    /**
     * 검색 결과 정렬 방법
     */
    private FetchBlogKeyword.Param.Sort sort = FetchBlogKeyword.Param.Sort.ACCURACY;

    /**
     * 검색 시작 위치
     */
    private int page = 1;

    /**
     * 한 번에 표시할 검색 결과 개수
     */
    private int size = 10;
}
