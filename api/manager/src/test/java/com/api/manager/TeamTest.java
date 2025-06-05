package com.api.manager;

import com.api.manager.auth.service.JwtUserDetailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TeamTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtUserDetailService service;
    @Autowired
    private ObjectMapper objectMapper;
    private final String basePath = "/team/216";

    @Test
    @WithUserDetails("test3422")
    void getAllUserRoleOnProject() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(basePath + "/get_user_role")).andExpect(status().isOk()).andDo(print());
    }

    @Test
    @WithUserDetails("test3422")
    void getAllNotAuthority() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/team/1/" + "get_user_role")).andExpect(status().isForbidden()).andDo(print());
    }


}
