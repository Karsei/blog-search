package kr.pe.karsei.blogsearch.service;

import lombok.Getter;

@Getter
public class BlogKeywordCountEvent {
    private final String keyword;

    public BlogKeywordCountEvent(String keyword) {
        this.keyword = keyword;
    }
}
