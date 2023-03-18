package kr.pe.karsei.blogsearch.service;

import lombok.Getter;

@Getter
public class BlogKeywordCollectEvent {
    private final String keyword;

    public BlogKeywordCollectEvent(String keyword) {
        this.keyword = keyword;
    }
}
