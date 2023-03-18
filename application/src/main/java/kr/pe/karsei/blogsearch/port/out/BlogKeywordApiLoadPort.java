package kr.pe.karsei.blogsearch.port.out;

import kr.pe.karsei.blogsearch.dto.FetchBlogKeyword;

public interface BlogKeywordApiLoadPort {
    /**
     * 블로그를 검색합니다.
     * @param params 검색 파라미터
     * @return 결과
     */
    FetchBlogKeyword.Info searchBlog(FetchBlogKeyword.Param params);
}
