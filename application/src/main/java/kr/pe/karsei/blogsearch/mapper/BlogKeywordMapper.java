package kr.pe.karsei.blogsearch.mapper;

import kr.pe.karsei.blogsearch.entity.BlogKeywordCountJpaEntity;
import kr.pe.karsei.blogsearch.dto.FetchBlogKeyword;
import kr.pe.karsei.blogsearch.dto.FetchBlogKeywordTop;
import kr.pe.karsei.client.kakao.dto.KakaoBlogSearch;
import kr.pe.karsei.client.naver.dto.NaverBlogSearch;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.time.ZoneId;
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
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .build();
    }

    public static FetchBlogKeyword mapKakaoBlogSearchToSearchBlogInfo(final Pageable pageable, final KakaoBlogSearch.Info kakaoSearchInfo) {
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
        FetchBlogKeyword.Pagination pagination = FetchBlogKeyword.Pagination.builder()
                .totalCount(kakaoSearchInfo.getMeta().getTotalCount())
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .build();
        return FetchBlogKeyword.builder()
                .documents(documents)
                .pagination(pagination)
                .build();
    }

    public static NaverBlogSearch.Param mapSearchParamToNaverBlogSearchParam(final Pageable pageable, final String query) {
        return NaverBlogSearch.Param.builder()
                .query(query)
                .sort(pageable.getSort()
                        .stream()
                        .findFirst()
                        .map(o -> switch (o.getProperty()) {
                            case "accuracy" -> NaverBlogSearch.Param.Sort.SIM;
                            case "recency" -> NaverBlogSearch.Param.Sort.DATE;
                            default -> NaverBlogSearch.Param.Sort.SIM;
                        })
                        .orElse(NaverBlogSearch.Param.Sort.SIM))
                .start(pageable.getPageNumber())
                .display(pageable.getPageSize())
                .build();
    }

    public static FetchBlogKeyword mapNaverBlogSearchToSearchBlogInfo(final Pageable pageable, final NaverBlogSearch.Info naverSearchInfo) {
        List<FetchBlogKeyword.Document> documents = new ArrayList<>();
        for (NaverBlogSearch.Info.Item document : naverSearchInfo.getItems()) {
            documents.add(FetchBlogKeyword.Document.builder()
                    .blogName(document.getBloggerName())
                    .contents(document.getDescription())
                    .dateTime(document.getDateTime().atStartOfDay(ZoneId.of("Asia/Seoul")))
                    .title(document.getTitle())
                    .url(document.getLink())
                    .build());
        }
        FetchBlogKeyword.Pagination pagination = FetchBlogKeyword.Pagination.builder()
                .totalCount(naverSearchInfo.getTotal())
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .build();
        return FetchBlogKeyword.builder()
                .documents(documents)
                .pagination(pagination)
                .build();
    }

    public static List<FetchBlogKeywordTop> mapCountEntityListToDto(final List<BlogKeywordCountJpaEntity> entities) {
        return entities.stream()
                .map(e -> new FetchBlogKeywordTop(e.getKeyword(), e.getHit()))
                .collect(Collectors.toList());
    }
}
