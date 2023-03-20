package kr.pe.karsei.blogsearch.mapper;

import kr.pe.karsei.blogsearch.adapter.out.BlogKeywordCountJpaEntity;
import kr.pe.karsei.blogsearch.dto.FetchBlogKeyword;
import kr.pe.karsei.blogsearch.dto.FetchBlogKeywordTop;
import kr.pe.karsei.client.kakao.dto.KakaoBlogSearch;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class BlogKeywordMapper {
    public static KakaoBlogSearch.Param mapSearchParamToKakaoBlogSearchParam(final Pageable pageable, final String query) {
        return KakaoBlogSearch.Param.builder()
                .query(query)
                .sort(pageable.getSort()
                        .stream()
                        .findFirst()
                        .map(o -> switch (o.getProperty()) {
                            case "accuracy" -> KakaoBlogSearch.Param.Sort.ACCURACY;
                            case "recency" -> KakaoBlogSearch.Param.Sort.RECENCY;
                            default -> KakaoBlogSearch.Param.Sort.ACCURACY;
                        })
                        .orElse(KakaoBlogSearch.Param.Sort.ACCURACY))
                .page((int) pageable.getOffset())
                .size(pageable.getPageSize())
                .build();
    }

    public static FetchBlogKeyword mapKakaoBlogSearchToSearchBlogInfo(final KakaoBlogSearch.Info kakaoSearchInfo) {
        List<FetchBlogKeyword.Document> documents = new ArrayList<>();
        for (KakaoBlogSearch.Info.Document document : kakaoSearchInfo.getDocuments()) {
            documents.add(FetchBlogKeyword.Document.builder()
                    .blogName(document.getBlogName())
                    .contents(document.getContents())
                    .dateTime(document.getDateTime())
                    .title(document.getTitle())
                    .url(document.getUrl())
                    .build());
        }
        FetchBlogKeyword.Meta meta = FetchBlogKeyword.Meta.builder()
                .pageableCount(kakaoSearchInfo.getMeta().getPageableCount())
                .totalCount(kakaoSearchInfo.getMeta().getTotalCount())
                .build();
        return FetchBlogKeyword.builder()
                .documents(documents)
                .meta(meta)
                .build();
    }

    public static List<FetchBlogKeywordTop> mapCountEntityListToDto(final List<BlogKeywordCountJpaEntity> entities) {
        return entities.stream()
                .map(e -> new FetchBlogKeywordTop(e.getKeyword(), e.getHit()))
                .collect(Collectors.toList());
    }
}
