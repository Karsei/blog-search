package kr.pe.karsei.blogsearch.port.out;

public interface BlogKeywordCollectSavePort {
    /**
     * 키워드의 검색 횟수를 증가시킵니다.
     * @param keyword 키워드
     */
    void increaseKeywordHit(final String keyword);
}
