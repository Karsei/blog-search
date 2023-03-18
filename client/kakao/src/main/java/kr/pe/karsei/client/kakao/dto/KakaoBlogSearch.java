package kr.pe.karsei.client.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class KakaoBlogSearch {
    @Getter
    public static class Info {
        private List<Document> documents;
        private Meta meta;

        @Getter
        public static class Document {
            /**
             * 블로그의 이름
             */
            @JsonProperty(value = "blogname")
            private String blogName;

            /**
             * 블로그 글 요약
             */
            private String contents;

            /**
             * 블로그 글 작성시간, ISO 8601
             * <p>[YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].000+[tz]</p>
             */
            @JsonProperty(value = "datetime")
            private ZonedDateTime dateTime;

            /**
             * 검색 시스템에서 추출한 대표 미리보기 이미지 URL, 미리보기 크기 및 화질은 변경될 수 있음
             */
            private String thumbnail;

            /**
             * 블로그 글 제목
             */
            private String title;

            /**
             * 블로그 글 URL
             */
            private String url;
        }

        @Getter
        public static class Meta {
            /**
             * 현재 페이지가 마지막 페이지인지 여부
             * <p>값이 false면 page를 증가시켜 다음 페이지를 요청할 수 있음</p>
             */
            @JsonProperty(value = "is_end")
            private boolean isEnd;

            /**
             * total_count 중 노출 가능 문서 수
             */
            @JsonProperty(value = "pageable_count")
            private int pageableCount;

            /**
             * 검색된 문서 수
             */
            @JsonProperty(value = "total_count")
            private int totalCount;
        }
    }

    @Getter
    public static class Param {
        /**
         * 검색을 원하는 질의어
         */
        private final String query;

        /**
         * 결과 문서 정렬 방식
         */
        private final Sort sort;

        /**
         * 결과 페이지 번호
         */
        private final int page;

        /**
         * 한 페이지에 보여질 문서 수
         */
        private final int size;

        @Builder
        public Param(String query, Sort sort, int page, int size) {
            this.query = query;
            this.sort = sort;
            this.page = page;
            this.size = size;
        }

        public enum Sort {
            /**
             * 정확도순
             */
            ACCURACY,

            /**
             * 최신순
             */
            RECENCY;

            @JsonValue
            @Override
            public String toString() {
                return this.name().toLowerCase();
            }
        }
    }
}
