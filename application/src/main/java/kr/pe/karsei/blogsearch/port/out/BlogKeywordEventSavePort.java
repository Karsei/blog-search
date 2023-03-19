package kr.pe.karsei.blogsearch.port.out;

public interface BlogKeywordEventSavePort {
    /**
     * 이벤트를 저장합니다.
     * @param keyword 키워드
     */
    void create(String keyword);
}
