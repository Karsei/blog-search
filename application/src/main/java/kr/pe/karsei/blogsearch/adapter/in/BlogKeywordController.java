package kr.pe.karsei.blogsearch.adapter.in;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import kr.pe.karsei.blogsearch.dto.FetchBlogKeyword;
import kr.pe.karsei.blogsearch.dto.FetchBlogKeywordTop;
import kr.pe.karsei.blogsearch.port.in.BlogKeywordQueryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RequestMapping
@RestController
@RequiredArgsConstructor
public class BlogKeywordController {
    private final BlogKeywordQueryUseCase queryUseCase;

    /**
     * 블로그를 검색합니다.
     * @param query 질의할 단어
     * @param page 페이지
     * @param size 출력할 개수
     * @param sort 정렬
     * @return 블로그 검색 결과
     */
    @GetMapping("search")
    public ResponseEntity<FetchBlogKeyword> search(
            @RequestParam @Valid @NotBlank final String query,
            @RequestParam(required = false, defaultValue = "1") @Valid @Min(1) @Max(10) final int page,
            @RequestParam(required = false, defaultValue = "10") @Valid @Min(1) @Max(50) final int size,
            @RequestParam(required = false, defaultValue = "accuracy") @Valid @NotBlank final String sort) {
        FetchBlogKeyword info = queryUseCase.findBlog(PageRequest.of(page, size, Sort.by(sort)), query);
        return ResponseEntity.ok(info);
    }

    /**
     * 인기 검색어 목록을 조회합니다.
     * @param size 출력 개수
     * @return 인기 검색어 목록 결과
     */
    @GetMapping("top-keywords")
    public ResponseEntity<List<FetchBlogKeywordTop>> searchTopKeywords(
            @RequestParam(defaultValue = "10") @Valid @Max(10) @Positive final int size) {
        List<FetchBlogKeywordTop> keywords = queryUseCase.findTopBlogKeywords(size);
        return ResponseEntity.ok(keywords);
    }
}
