package ca.group20.sysc4806project.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Order(2)
public class RespondentControllerTest {
    private final static String CONTROLLER_URL = "/api/v0/respondents/";

    @Autowired
    private MockMvc mvc;

    private String text_answer, number_answer;

    @BeforeEach
    void setUp() {
        text_answer = "{\"type\":\"text\",\"question\":{\"type\": \"text\",\"id\": 1},\"answer\": \"answer\"}";
        number_answer = "{\"type\":\"number\",\"question\":{\"type\": \"number\",\"id\": 2},\"answer\": 1}";
    }

    @Test
    void addTextAnswer() throws Exception {
        mvc.perform(post(CONTROLLER_URL + "/answer")
                .contentType(MediaType.APPLICATION_JSON).content(text_answer)).andExpect(status().isCreated());
    }
    @Test
    void addNumberAnswer() throws Exception {
        mvc.perform(post(CONTROLLER_URL + "/answer")
                .contentType(MediaType.APPLICATION_JSON).content(number_answer)).andExpect(status().isCreated());
    }
}