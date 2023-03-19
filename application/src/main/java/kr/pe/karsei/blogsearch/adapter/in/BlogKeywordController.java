package kr.pe.karsei.blogsearch.adapter.in;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import kr.pe.karsei.blogsearch.dto.RequestBlogKeyword;
import kr.pe.karsei.blogsearch.dto.FetchBlogKeyword;
import kr.pe.karsei.blogsearch.dto.FetchBlogKeywordTop;
import kr.pe.karsei.blogsearch.mapper.BlogKeywordMapper;
import kr.pe.karsei.blogsearch.port.in.BlogKeywordQueryUseCase;
import lombok.RequiredArgsConstructor;
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
     * @param request 파라미터
     * @return 블로그 검색 결과
     */
    @GetMapping("search")
    public ResponseEntity<FetchBlogKeyword.Info> search(
            @ModelAttribute @Valid final RequestBlogKeyword request) {
        FetchBlogKeyword.Info info = queryUseCase.findBlog(BlogKeywordMapper.mapRequestToParam(request));
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
