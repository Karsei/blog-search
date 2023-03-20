package kr.pe.karsei.blogsearch.service;

import lombok.Getter;

@Getter
public class BlogKeywordFetchEvent {
    private final String keyword;

    public BlogKeywordFetchEvent(final String keyword) {
        this.keyword = keyword;
    }
}
