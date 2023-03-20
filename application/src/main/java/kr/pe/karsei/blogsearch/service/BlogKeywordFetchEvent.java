package kr.pe.karsei.blogsearch.service;

import lombok.Getter;

@Getter
public class BlogKeywordFetchEvent {
    private final String keyword;

    public BlogKeywordFetchEvent(String keyword) {
        this.keyword = keyword;
    }
}
