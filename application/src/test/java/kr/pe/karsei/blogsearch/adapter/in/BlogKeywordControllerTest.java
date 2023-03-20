package kr.pe.karsei.blogsearch.adapter.in;

import kr.pe.karsei.blogsearch.dto.FetchBlogKeyword;
import kr.pe.karsei.blogsearch.dto.FetchBlogKeywordTop;
import kr.pe.karsei.blogsearch.port.in.BlogKeywordQueryUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@AutoConfigureMockMvc
class BlogKeywordControllerTest {
    @MockBean
    private BlogKeywordQueryUseCase queryUseCase;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void search() throws Exception {
        // given
        String query = "한글날";
        String sort = "accuracy";
        Pageable pageable = PageRequest.of(1, 10, Sort.by(sort));

        List<FetchBlogKeyword.Document> documents = new ArrayList<>();
        documents.add(FetchBlogKeyword.Document.builder()
                .blogName("너무나도어렵네")
                .contents("<b>한글날</b> 공휴일 추가수당에 대해 알아보도록 하겠습니다. 이 게시물을 전체적으로 읽어주시면 <b>한글날</b> 공휴일 추가수당을 알아두시는 데에 보탬이 될 것입니다. <b>한글날</b> 공휴일 추가수당의 정보가 필요하다면 전체 다 읽어주세요. 이제 밑에서 <b>한글날</b> 공휴일 추가수당을 알려드리겠습니다. <b>한글날</b> 공휴일 추가수당 통풍에...")
                .dateTime(ZonedDateTime.of(2023, 2, 19, 22, 44, 34, 0, ZoneId.of("Asia/Seoul")))
                .title("<b>한글날</b> 공휴일 추가수당 보기쉽게 정리완료했습니다.")
                .url("http://starnews.heetsu.com/636")
                .build());
        FetchBlogKeyword.Pagination pagination = FetchBlogKeyword.Pagination.builder()
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .totalCount(346398)
                .build();
        FetchBlogKeyword info = FetchBlogKeyword.builder()
                .documents(documents)
                .pagination(pagination)
                .build();
        given(queryUseCase.findBlog(any(Pageable.class), anyString())).willReturn(info);

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/search")
                        .param("query", query)
                        .param("sort", sort)
                        .param("page", String.valueOf(pagination.getPage()))
                        .param("size", String.valueOf(pagination.getSize())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.documents").exists())
                .andExpect(jsonPath("$.pagination").exists())
                .andDo(print());
    }

    @Test
    void searchTopKeywords() throws Exception {
        // given
        List<FetchBlogKeywordTop> list = new ArrayList<>();
        list.add(new FetchBlogKeywordTop("한글날", 3));
        list.add(new FetchBlogKeywordTop("세종대왕", 4));
        given(queryUseCase.findTopBlogKeywords(anyInt())).willReturn(list);

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/top-keywords"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[1]").exists())
                .andDo(print());
    }
}