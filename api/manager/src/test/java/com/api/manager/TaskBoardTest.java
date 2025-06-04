package com.api.manager;

import com.api.manager.auth.service.JwtUserDetailService;
import com.api.manager.dto.TaskDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskBoardTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUserDetailService service;
    @Autowired
    private ObjectMapper objectMapper;

    private final String basePath = "/task_board/217/";

    TaskDTO valid() {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setDetail("prhmeplkmreklerplk lkplekhl;, ;lre,l,");
        return taskDTO;
    }

    @Test
    @SneakyThrows
    @WithUserDetails("test3422")
    void createOk() {
        String jsObj = objectMapper.writeValueAsString(valid());

        mockMvc.perform(MockMvcRequestBuilders.post(basePath + "create?id_sprint=314").contentType(MediaType.APPLICATION_JSON).content(jsObj)).
                andExpect(status().isOk()).andDo(print());

    }

    @Test
    @SneakyThrows
    @WithUserDetails("test3422")
    void createForbidden() {
        String jsObj = objectMapper.writeValueAsString(valid());
        mockMvc.perform(MockMvcRequestBuilders.post(basePath + "create?id_sprint=100000000").contentType(MediaType.APPLICATION_JSON).content(jsObj)).
                andExpect(status().isForbidden()).andDo(print());
    }

    @Test
    @SneakyThrows
    @WithUserDetails("test3422")
    void createForbiddenProject() {
        String jsObj = objectMapper.writeValueAsString(valid());

        mockMvc.perform(MockMvcRequestBuilders.post("/task_board/121/" + "create?id_sprint=10").contentType(MediaType.APPLICATION_JSON).content(jsObj)).
                andExpect(status().isForbidden()).andDo(print());

    }
}
