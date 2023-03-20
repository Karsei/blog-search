package kr.pe.karsei.blogsearch.adapter.in;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import kr.pe.karsei.blogsearch.dto.FetchBlogKeyword;
import kr.pe.karsei.blogsearch.dto.FetchBlogKeywordTop;
import kr.pe.karsei.blogsearch.port.in.BlogKeywordQueryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
     * @param pageable 페이징
     * @return 블로그 검색 결과
     */
    @GetMapping("search")
    public ResponseEntity<FetchBlogKeyword> search(@RequestParam final String query,
                                                   @PageableDefault(page = 1, size = 10, sort = {"accuracy"}) final Pageable pageable) {
        FetchBlogKeyword info = queryUseCase.findBlog(pageable, query);
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
