package com.makefire.anonymous.controller.board;

import com.makefire.anonymous.domain.board.entity.Board;
import com.makefire.anonymous.rest.controller.api.board.BoardController;
import com.makefire.anonymous.rest.dto.request.board.BoardRequest;
import com.makefire.anonymous.rest.dto.response.board.BoardResponse;
import com.makefire.anonymous.service.board.BoardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * packageName : com.makefire.anonymous.controller.board
 * fileName : BoardControllerTest
 * author : kjho94
 * date : 2022-03-03
 * description :
 * =================================
 * DATE        AUTHOR    NOTE
 * 2022-03-03  kjho94    최초 생성
 * ---------------------------------
 */

@WebMvcTest(BoardController.class)
public class BoardControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BoardService boardService;

    private BoardRequest boardRequest;

    private Board board;

    @BeforeEach
    void setUp() {
        boardRequest = BoardRequest.builder()
                .title("Test title")
                .contents("Test contents")
                .author("Test author")
                .build();
        board = BoardRequest.to(boardRequest);
    }

    @Test
    @DisplayName("BoardController insertBoard 메소드 테스트")
    void insertBoardTest() throws Exception {
        // given
        given(boardService.insertBoard(any(BoardRequest.class)))
                .willReturn(BoardResponse.from(board));

        // when
        final ResultActions actions = mvc.perform(post("/board")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{"
                        + " \"title\" : \"test Title\", "
                        + " \"contents\" : \"test Contents\", "
                        + " \"author\": \"test Author\" "
                        + "}"))
                .andDo(print());

        // then
        actions
                .andExpect(status().isOk()) // FIXME HttpStatusCode 200? 201?
                .andExpect(jsonPath("data.title").value(boardRequest.getTitle()))
                .andExpect(jsonPath("data.contents").value(boardRequest.getContents()))
                .andExpect(jsonPath("data.author").value(boardRequest.getAuthor()))
                .andDo(print());
    }
}