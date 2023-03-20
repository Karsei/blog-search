package kr.pe.karsei.blogsearch.port.out;

import kr.pe.karsei.blogsearch.dto.FetchBlogKeyword;
import org.springframework.data.domain.Pageable;

public interface BlogKeywordApiLoadPort {
    /**
     * 블로그를 검색합니다.
     * @param pageable 페이징
     * @param query 질의할 단어
     * @return 블로그 검색 결과
     */
    FetchBlogKeyword search(Pageable pageable, String query);
}
