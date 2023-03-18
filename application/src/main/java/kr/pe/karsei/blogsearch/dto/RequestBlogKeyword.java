package kr.pe.karsei.blogsearch.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RequestBlogKeyword {
    @NotBlank
    private String query;
    private FetchBlogKeyword.Param.Sort sort = FetchBlogKeyword.Param.Sort.ACCURACY;
    private int page = 1;
    private int size = 10;
}
