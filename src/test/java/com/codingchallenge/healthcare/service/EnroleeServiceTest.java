package com.codingchallenge.healthcare.service;

import com.codingchallenge.healthcare.dto.Dependent;
import com.codingchallenge.healthcare.dto.Enrolee;
import com.codingchallenge.healthcare.exception.InvalidInputException;
import com.codingchallenge.healthcare.exception.NoEnroleeFoundException;
import com.codingchallenge.healthcare.model.EnroleeModel;
import com.codingchallenge.healthcare.repository.EnroleeRespository;
import com.codingchallenge.healthcare.util.EnroleeTransformer;
import com.codingchallenge.healthcare.util.ErrorCodes;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Sravani
 * @version $ 9/30/20
 */
public class EnroleeServiceTest {

    EnroleeRespository enroleeRespository = Mockito.mock(EnroleeRespository.class);

    EnroleeService enroleeService;

    @Test
    public void testAddOrUpdateEnrolee(){
        EnroleeModel model = EnroleeTransformer.transformToModel(mockEnrolee());
        Mockito.when(enroleeRespository.save(Mockito.any(EnroleeModel.class)))
                .thenReturn(model);
        enroleeService = new EnroleeService(enroleeRespository);
        enroleeService.addOrUpdateEnrolee(mockEnrolee());
        verify(enroleeRespository, times(1)).save(Mockito.any(EnroleeModel.class));
    }

    @Test
    public void testGetEnrolee() throws NoEnroleeFoundException {
        EnroleeModel model = EnroleeTransformer.transformToModel(mockEnrolee());
        Mockito.when(enroleeRespository.findById(Mockito.anyString()))
                .thenReturn(Optional.of(model));
        enroleeService = new EnroleeService(enroleeRespository);
        Enrolee response = enroleeService.getEnrolee("k123");
        assertNotNull(response);
        assertEquals("k123", response.getId());
    }

    @Test
    public void testGetEnrolee_NoRecords() throws NoEnroleeFoundException {
        EnroleeModel model = EnroleeTransformer.transformToModel(mockEnrolee());
        Mockito.when(enroleeRespository.findById(Mockito.anyString()))
                .thenReturn(Optional.empty());
        enroleeService = new EnroleeService(enroleeRespository);

        NoEnroleeFoundException exception = assertThrows(NoEnroleeFoundException.class, () -> enroleeService.getEnrolee("k123"));
        assertEquals(ErrorCodes.NO_RECORDS_FOUND.getCode(), exception.getErrorCode());
    }


    @Test
    public void testRemoveEnrolee(){

        Mockito.doNothing()
                .when(enroleeRespository).deleteById(Mockito.anyString());

        enroleeService = new EnroleeService(enroleeRespository);
        enroleeService.removeEnrolee("k123");
        verify(enroleeRespository, times(1)).deleteById(Mockito.anyString());
    }

    @Test
    public void testAddOrUpdateDependents() throws InvalidInputException {
        EnroleeModel model = EnroleeTransformer.transformToModel(mockEnrolee());
        Mockito.when(enroleeRespository.findById(Mockito.anyString()))
                .thenReturn(Optional.of(model));
        Mockito.when(enroleeRespository.save(Mockito.any(EnroleeModel.class)))
                .thenReturn(model);
        enroleeService = new EnroleeService(enroleeRespository);

        enroleeService.addOrUpdateDependents("k123", mockDependents());

    }

    @Test
    public void testAddOrUpdateDependents_InvalidInput() throws InvalidInputException {
        EnroleeModel model = EnroleeTransformer.transformToModel(mockEnrolee());
        Mockito.when(enroleeRespository.findById(Mockito.anyString()))
                .thenReturn(Optional.empty());

        enroleeService = new EnroleeService(enroleeRespository);
        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> enroleeService.addOrUpdateDependents("k123", mockDependents()));
        assertEquals(ErrorCodes.INVALID_INPUT.getCode(), exception.getErrorCode());

    }

    @Test
    public void testRemoveDependents() throws InvalidInputException {
        EnroleeModel model = EnroleeTransformer.transformToModel(mockEnrolee());
        Mockito.when(enroleeRespository.findById(Mockito.anyString()))
                .thenReturn(Optional.of(model));
        Mockito.when(enroleeRespository.save(Mockito.any(EnroleeModel.class)))
                .thenReturn(model);
        enroleeService = new EnroleeService(enroleeRespository);
        enroleeService.removeDependent("k123", "dep123");
        verify(enroleeRespository, times(1)).save(Mockito.any(EnroleeModel.class));
        verify(enroleeRespository, times(1)).findById(Mockito.anyString());

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
