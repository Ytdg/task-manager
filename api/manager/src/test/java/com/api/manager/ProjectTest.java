package com.api.manager;

import com.api.manager.auth.service.JwtUserDetailService;
import com.api.manager.model.ProjectDb;
import com.api.manager.model.dto.ProjectDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//project/get_all
//project/create

//{
//    "login":"test3422",
//    "password":12345678,
//    "name":"test423"
//     "id":41
//}
@SpringBootTest
@AutoConfigureMockMvc
public class ProjectTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUserDetailService service;
    @Autowired
    private ObjectMapper objectMapper;

    private final String basePath = "/project/";
//+
    @Test
    @WithUserDetails("test3422")
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(basePath + "get_all")).andExpect(status().isOk());
    }
//+
    @Test
    @WithUserDetails("notAuthorization")
    void getAllIncorrectUserRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(basePath + "get_all")).andExpect(status().isForbidden()).andDo(print());
    }
//+
    @Test
    @WithUserDetails("test3422")
    void getAllChekResult() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(basePath + "get_all")).andExpect(status().isOk()).andReturn();
        String content = result.getResponse().getContentAsString();
        List<ProjectDb> myObjects = objectMapper.readValue(content, new TypeReference<>() {
        });
        for (ProjectDb db : myObjects) {
            assertEquals(47L, db.getCreator().getId(), "Incorrect ProjectDB");
            assertEquals(41L, db.getCreator().getId(), "Incorrect ProjectDB");
        }
    }
//+
    //Test create
    @Test
    @WithUserDetails("hophay")
    void createProjectEmptyParam() throws Exception {
        ProjectDTO projectDTO = new ProjectDTO();
        String jsObj = objectMapper.writeValueAsString(projectDTO);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(basePath + "create").contentType(MediaType.APPLICATION_JSON).
                content(jsObj)).andExpect(status().isBadRequest()).andReturn();
    }
//+
    @Test
    @WithUserDetails("hophay")
    void createProjectIncorrectParamName() throws Exception {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setName("");
        String jsObj = objectMapper.writeValueAsString(projectDTO);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(basePath + "create").contentType(MediaType.APPLICATION_JSON).
                content(jsObj)).andExpect(status().isBadRequest()).andReturn();
    }
//+
    @Test
    @WithUserDetails("hophay")
    void createProjectIncorrectParamCreator() throws Exception {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setCreator(null);
        String jsObj = objectMapper.writeValueAsString(projectDTO);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(basePath + "create").contentType(MediaType.APPLICATION_JSON).
                content(jsObj)).andExpect(status().isBadRequest()).andReturn();
    }
//+
    @Test
    @WithUserDetails("hophay")
    void createProjectGenerate() throws Exception {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setName("TestProject");
        for (int i = 0; i < 2; i++) {
            String jsObj = objectMapper.writeValueAsString(projectDTO);
            mockMvc.perform(MockMvcRequestBuilders.post(basePath + "create").contentType(MediaType.APPLICATION_JSON).
                    content(jsObj)).andExpect(status().isOk());
        }
    }
//+
    //Test save id=14
    @Test
    @WithUserDetails("test3422")
    void emptyNameSaveProject() throws Exception {
        ProjectDTO projectDTO = new ProjectDTO();
        String jsObj = objectMapper.writeValueAsString(projectDTO);
        mockMvc.perform(MockMvcRequestBuilders.post(basePath + "14/update").contentType(MediaType.APPLICATION_JSON).
                content(jsObj)).andExpect(status().isBadRequest()).andDo(print());
    }
//+
    @Test
    @WithUserDetails("test3422")
    void inCorrectIdSaveProject() throws Exception {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setName("InvalidTest3422");
        String jsObj = objectMapper.writeValueAsString(projectDTO);
        mockMvc.perform(MockMvcRequestBuilders.post(basePath + "10000000000000/update").contentType(MediaType.APPLICATION_JSON).
                content(jsObj)).andExpect(status().isConflict()).andDo(print());
    }
//-
    @Test
    @WithUserDetails("hophay")
    void notAuthUserOnProjectSave() throws Exception {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setName("InvalidHopHay");
        String jsObj = objectMapper.writeValueAsString(projectDTO);
        mockMvc.perform(MockMvcRequestBuilders.post(basePath + "13/update").contentType(MediaType.APPLICATION_JSON).
                content(jsObj)).andExpect(status().isForbidden()).andDo(print());
    }


}
