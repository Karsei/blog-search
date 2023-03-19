package kr.pe.karsei.blogsearch.dto;

import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FetchBlogKeyword {
    @Getter
    public static class Info {
        private final List<Document> documents;
        private final Meta meta;

        @Builder
        public Info(final List<Document> documents,
                    final Meta meta) {
            this.documents = documents;
            this.meta = meta;
        }

        @Getter
        public static class Document {
            /**
             * 블로그의 이름
             */
            private final String blogName;

            /**
             * 블로그 글 요약
             */
            private final String contents;

            /**
             * 블로그 글 작성시간
             */
            private final ZonedDateTime dateTime;

            /**
             * 블로그 글 제목
             */
            private final String title;

            /**
             * 블로그 글 URL
             */
            private final String url;

            @Builder
            public Document(final String blogName,
                            final String contents,
                            final ZonedDateTime dateTime,
                            final String title,
                            final String url) {
                this.blogName = blogName;
                this.contents = contents;
                this.dateTime = dateTime;
                this.title = title;
                this.url = url;
            }
        }

        @Getter
        public static class Meta {
            /**
             * total_count 중 노출 가능 문서 수
             */
            private final int pageableCount;

            /**
             * 검색된 문서 수
             */
            private final int totalCount;

            @Builder
            public Meta(final int pageableCount,
                        final int totalCount) {
                this.pageableCount = pageableCount;
                this.totalCount = totalCount;
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
        private final int page;

        /**
         * 한 페이지에 보여질 문서 수
         */
        private final int size;

        @Builder
        public Param(final String query,
                     final Sort sort,
                     final int page,
                     final int size) {
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
            RECENCY
        }
    }
}