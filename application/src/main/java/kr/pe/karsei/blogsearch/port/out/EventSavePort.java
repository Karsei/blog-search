package kr.pe.karsei.blogsearch.port.out;

public interface EventSavePort {
    /**
     * 이벤트를 저장합니다.
     * @param keyword 키워드
     */
    void create(final String keyword);
}
