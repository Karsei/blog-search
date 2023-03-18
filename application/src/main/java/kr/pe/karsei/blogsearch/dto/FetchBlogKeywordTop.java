package kr.pe.karsei.blogsearch.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FetchBlogKeywordTop {
    private String keyword;
    private Integer hit;
}
