package kr.pe.karsei.blogsearch.mapper;

import kr.pe.karsei.blogsearch.adapter.out.BlogKeywordCollectJpaEntity;
import kr.pe.karsei.blogsearch.dto.RequestBlogKeyword;
import kr.pe.karsei.blogsearch.dto.FetchBlogKeyword;
import kr.pe.karsei.blogsearch.dto.FetchBlogKeywordTop;
import kr.pe.karsei.client.kakao.dto.KakaoBlogSearch;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class BlogKeywordMapper {
    public static FetchBlogKeyword.Param mapRequestToParam(RequestBlogKeyword request) {
        return new FetchBlogKeyword.Param(request.getQuery(), request.getSort(), request.getPage(), request.getSize());
    }

    public static List<FetchBlogKeywordTop> mapSearchEntityListToDto(List<BlogKeywordCollectJpaEntity> entities) {
        return entities.stream()
                .map(e -> new FetchBlogKeywordTop(e.getKeyword(), e.getHit()))
                .collect(Collectors.toList());
    }

    public static KakaoBlogSearch.Param mapSearchParamToKakaoBlogSearchParam(FetchBlogKeyword.Param params) {
        return KakaoBlogSearch.Param.builder()
                .query(params.getQuery())
                .sort(KakaoBlogSearch.Param.Sort.valueOf(params.getSort().name()))
                .page(params.getPage())
                .size(params.getSize())
                .build();
    }

    public static FetchBlogKeyword.Info mapKakaoBlogSearchToSearchBlogInfo(KakaoBlogSearch.Info kakaoSearchInfo) {
        List<FetchBlogKeyword.Info.Document> documents = new ArrayList<>();
        for (KakaoBlogSearch.Info.Document document : kakaoSearchInfo.getDocuments()) {
            documents.add(FetchBlogKeyword.Info.Document.builder()
                    .blogName(document.getBlogName())
                    .contents(document.getContents())
                    .dateTime(document.getDateTime())
                    .title(document.getTitle())
                    .url(document.getUrl())
                    .build());
        }
        FetchBlogKeyword.Info.Meta meta = FetchBlogKeyword.Info.Meta.builder()
                .isEnd(kakaoSearchInfo.getMeta().isEnd())
                .pageableCount(kakaoSearchInfo.getMeta().getPageableCount())
                .totalCount(kakaoSearchInfo.getMeta().getTotalCount())
                .build();
        return new FetchBlogKeyword.Info(documents, meta);
    }
}
