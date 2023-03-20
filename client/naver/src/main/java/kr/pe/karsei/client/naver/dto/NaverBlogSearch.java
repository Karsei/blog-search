package kr.pe.karsei.client.naver.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NaverBlogSearch {
    @Getter
    public static class Info {
        private final List<Item> items;
        private final int total;
        private final int display;

        @Builder
        public Info(final List<Item> items,
                    final int total,
                    final int display) {
            this.items = items;
            this.total = total;
            this.display = display;
        }

        @Getter
        public static class Item {
            /**
             * 블로그의 이름
             */
            @JsonProperty(value = "bloggername")
            private final String bloggerName;

            /**
             * 블로그 글 요약
             */
            private final String description;

            /**
             * 블로그 글 작성시간
             * <p>[YYYYMMDD]</p>
             */
            @JsonFormat(pattern = "yyyyMMdd")
            @JsonProperty(value = "postdate")
            private final LocalDate dateTime;

            /**
             * 블로그 글 제목
             */
            private final String title;

            /**
             * 블로그 글 URL
             */
            private final String link;

            @Builder
            public Item(final String bloggerName,
                        final String description,
                        final LocalDate dateTime,
                        final String title,
                        final String link) {
                this.bloggerName = bloggerName;
                this.description = description;
                this.dateTime = dateTime;
                this.title = title;
                this.link = link;
            }
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
        private final int start;

        /**
         * 한 페이지에 보여질 문서 수
         */
        private final int display;

        @Builder
        public Param(final String query,
                     final Sort sort,
                     final int start,
                     final int display) {
            this.query = query;
            this.sort = sort;
            this.start = start;
            this.display = display;
        }

        public enum Sort {
            /**
             * 정확도순
             */
            SIM,

            /**
             * 최신순
             */
            DATE;

            @JsonValue
            @Override
            public String toString() {
                // 실제 API 에서 대문자로는 먹히지 않는다. 사양대로 소문자로 해야 한다.
                return this.name().toLowerCase();
            }
        }
    }
}