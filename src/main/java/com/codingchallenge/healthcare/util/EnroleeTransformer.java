package com.codingchallenge.healthcare.util;

import com.codingchallenge.healthcare.dto.Enrolee;
import com.codingchallenge.healthcare.model.EnroleeModel;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Sravani
 * @version $ 9/30/20
 */
public class EnroleeTransformer {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static EnroleeModel transformToModel(Enrolee enrolee){
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        EnroleeModel model =  objectMapper.convertValue(enrolee, EnroleeModel.class);
        return model;
    }

    public static Enrolee transformToDto(EnroleeModel enrolee){
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return  objectMapper.convertValue(enrolee, Enrolee.class);
    }


}
