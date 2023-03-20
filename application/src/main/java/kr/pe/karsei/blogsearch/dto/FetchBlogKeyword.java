package kr.pe.karsei.blogsearch.dto;

import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
public class FetchBlogKeyword {
    private final List<Document> documents;
    private final Pagination pagination;

    @Builder
    public FetchBlogKeyword(final List<Document> documents,
                            final Pagination pagination) {
        this.documents = documents;
        this.pagination = pagination;
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
    public static class Pagination {
        /**
         * 현재 페이지
         */
        private final int page;

        /**
         * 출력되는 항목 개수
         */
        private final int size;

        /**
         * 전체 페이지
         */
        private final int totalCount;

        @Builder
        public Pagination(final int page,
                          final int size,
                          final int totalCount) {
            this.page = page;
            this.size = size;
            this.totalCount = totalCount;
        }
    }
}