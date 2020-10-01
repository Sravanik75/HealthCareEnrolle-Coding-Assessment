package com.codingchallenge.healthcare.util;

import com.codingchallenge.healthcare.dto.Dependent;
import com.codingchallenge.healthcare.dto.Enrolee;
import com.codingchallenge.healthcare.model.EnroleeModel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Sravani
 * @version $ 9/30/20
 */
public class EnroleeTransformerTest {

    @Test
    public void testTransformToModel(){
        EnroleeModel model = EnroleeTransformer.transformToModel(mockEnrolee());
        assertNotNull(model);
        assertEquals("k123", model.getId());
        assertEquals("Sravani", model.getName());
        assertEquals(true, model.isStatus());
        assertEquals(1, model.getDependents().size());
    }

    @Test
    public void testTransformToDto(){

        Enrolee enrolee = EnroleeTransformer.transformToDto(mockEnroleeModel());
        assertNotNull(enrolee);
        assertEquals("k12345", enrolee.getId());
        assertEquals("SravaniDemo", enrolee.getName());
        assertEquals(false, enrolee.isStatus());
        assertEquals(1, enrolee.getDependents().size());
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

    public EnroleeModel mockEnroleeModel(){
        EnroleeModel enrolee = new EnroleeModel();
        enrolee.setId("k12345");
        enrolee.setName("SravaniDemo");
        enrolee.setStatus(false);
        List<Dependent> dependentList = new ArrayList<>();
        dependentList.add(new Dependent("123","Akhil",null));
        enrolee.setDependents(dependentList);
        return enrolee;
    }
}
