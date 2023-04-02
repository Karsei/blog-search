package kr.pe.karsei.blogsearch.port.in;

import kr.pe.karsei.blogsearch.dto.FetchBlogKeyword;
import kr.pe.karsei.blogsearch.dto.FetchBlogKeywordTop;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

import java.util.List;

public interface BlogKeywordQueryUseCase {
    /**
     * 블로그를 검색합니다.
     * @param pageable 페이징
     * @param query 질의할 단어
     * @return 블로그 검색 결과
     */
    Mono<FetchBlogKeyword> findBlog(Pageable pageable, String query);

    /**
     * 자주 조회하는 블로그 검색어 목록을 내림차순으로 조회합니다.
     * @param size 출력 개수
     * @return 인기 검색어 목록 결과
     */
    List<FetchBlogKeywordTop> findTopBlogKeywords(int size);
}