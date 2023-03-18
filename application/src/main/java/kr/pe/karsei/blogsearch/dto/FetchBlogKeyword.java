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
            private final String blogName;
            private final String contents;
            private final ZonedDateTime dateTime;
            private final String title;
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
            private final boolean isEnd;
            private final int pageableCount;
            private final int totalCount;

            @Builder
            public Meta(final boolean isEnd,
                        final int pageableCount,
                        final int totalCount) {
                this.isEnd = isEnd;
                this.pageableCount = pageableCount;
                this.totalCount = totalCount;
            }
        }
    }

    @Getter
    public static class Param {
        private final String query;
        private final Sort sort;
        private final int page;
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
            ACCURACY, RECENCY;
        }
    }
}