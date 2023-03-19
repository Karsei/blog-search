package kr.pe.karsei.blogsearch.port.in;

import kr.pe.karsei.blogsearch.dto.FetchBlogKeyword;
import kr.pe.karsei.blogsearch.dto.FetchBlogKeywordTop;

import java.util.List;

public interface BlogKeywordQueryUseCase {
    /**
     * 블로그를 검색합니다.
     * @param param 파라미터
     * @return 블로그 검색 결과
     */
    FetchBlogKeyword.Info findBlog(FetchBlogKeyword.Param param);

    /**
     * 자주 조회하는 블로그 검색어 목록을 내림차순으로 조회합니다.
     * @param size 출력 개수
     * @return 인기 검색어 목록 결과
     */
    List<FetchBlogKeywordTop> findTopBlogKeywords(int size);
}