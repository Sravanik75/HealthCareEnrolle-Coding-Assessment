package com.codingchallenge.healthcare.api;

import com.codingchallenge.healthcare.dto.Dependent;
import com.codingchallenge.healthcare.dto.Enrolee;
import com.codingchallenge.healthcare.exception.NoEnroleeFoundException;
import com.codingchallenge.healthcare.service.EnroleeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Sravani
 * @version $ 9/30/20
 */
@WebMvcTest(controllers = EnroleeApi.class)
@ExtendWith(SpringExtension.class)
public class EnroleeApiTest {

    @MockBean
    EnroleeService enroleeService;

    @InjectMocks
    EnroleeApi enroleeApi;

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testGetEnrolee_200OK() throws Exception {

        Mockito.when(enroleeService.getEnrolee(Mockito.anyString()))
                .thenReturn(new Enrolee("k123","kiran",true, null, null));

        mockMvc.perform(get("/api/v1/enrolees/k123")
                .contentType("application/json"))
                .andExpect(status().isOk());


    }

    @Test
    public void testGetEnrolee_204NoResults() throws Exception {

        Mockito.when(enroleeService.getEnrolee(Mockito.anyString()))
                .thenThrow(new NoEnroleeFoundException("no_record_found","no results"));

        mockMvc.perform(get("/api/v1/enrolees/k123")
                .contentType("application/json"))
                .andExpect(status().isNoContent());


    }


    @Test
    public void testAddEnrolee_200OK() throws  Exception{
        Mockito
                .doNothing()
                .when(enroleeService).addOrUpdateEnrolee(Mockito.any());
        mockMvc.perform(post("/api/v1/enrolees")
                .content(objectMapper.writeValueAsString(mockEnrolee()))
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddEnrolee_400BadRequest() throws  Exception{

        Enrolee enrolee = mockEnrolee();
        enrolee.setId("");
        mockMvc.perform(post("/api/v1/enrolees")
                .content(objectMapper.writeValueAsString(enrolee))
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void testUpdateEnrolee_200OK() throws  Exception{
        Mockito
                .doNothing()
                .when(enroleeService).addOrUpdateEnrolee(Mockito.any());
        mockMvc.perform(put("/api/v1/enrolees")
                .content(objectMapper.writeValueAsString(mockEnrolee()))
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateEnrolee_400BadRequest() throws  Exception{

        Enrolee enrolee = mockEnrolee();
        enrolee.setId("");
        mockMvc.perform(put("/api/v1/enrolees")
                .content(objectMapper.writeValueAsString(enrolee))
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteEnrolee() throws Exception {
        Mockito.doNothing()
                .when(enroleeService).removeEnrolee(Mockito.anyString());
        mockMvc.perform(delete("/api/v1/enrolees/k123")
                .contentType("application/json"))
                .andExpect(status().isOk());

    }


    @Test
    public void testAddEnroleeDependents_200OK() throws  Exception{
        Mockito
                .doNothing()
                .when(enroleeService).addOrUpdateDependents(Mockito.anyString(), Mockito.anyList());
        mockMvc.perform(post("/api/v1/enrolees/k123/dependents")
                .content(objectMapper.writeValueAsString(mockEnrolee().getDependents()))
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddEnroleeDependents_400BadRequest() throws  Exception{

        List<Dependent> dependentList = new ArrayList<>();
        dependentList.add(new Dependent("","Akhil",null));
        mockMvc.perform(post("/api/v1/enrolees/k123/dependents")
                .content(objectMapper.writeValueAsString(dependentList))
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void testUpdateEnroleeDependents_200OK() throws  Exception{
        Mockito
                .doNothing()
                .when(enroleeService).addOrUpdateDependents(Mockito.anyString(), Mockito.anyList());
        mockMvc.perform(put("/api/v1/enrolees/k123/dependents")
                .content(objectMapper.writeValueAsString(mockEnrolee().getDependents()))
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateEnroleeDependents_400BadRequest() throws  Exception{

        List<Dependent> dependentList = new ArrayList<>();
        dependentList.add(new Dependent("","Akhil",null));
        mockMvc.perform(put("/api/v1/enrolees/k123/dependents")
                .content(objectMapper.writeValueAsString(dependentList))
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteEnroleeDependent() throws Exception {
        Mockito.doNothing()
                .when(enroleeService).removeDependent(Mockito.anyString(), Mockito.anyString());
        mockMvc.perform(delete("/api/v1/enrolees/k123/dependents/123")
                .contentType("application/json"))
                .andExpect(status().isOk());

    }

    public Enrolee mockEnrolee(){
        Enrolee enrolee = new Enrolee();
        enrolee.setId("k123");
        enrolee.setName("Sravani");
        enrolee.setStatus(true);
        List<Dependent> dependentList = new ArrayList<>();
        dependentList.add(new Dependent("123","Akhil",null));
        enrolee.setDependents(dependentList);
        return enrolee;
    }

    public List<Dependent> mockDependents(){
        Enrolee enrolee = new Enrolee();
        enrolee.setId("k123");
        enrolee.setName("Sravani");
        enrolee.setStatus(true);
        List<Dependent> dependentList = new ArrayList<>();
        dependentList.add(new Dependent("123","Akhil",null));
        return dependentList;
    }

}
