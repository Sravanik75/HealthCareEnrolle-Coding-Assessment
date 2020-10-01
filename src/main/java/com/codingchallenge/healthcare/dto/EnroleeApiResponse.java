package com.codingchallenge.healthcare.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

/**
 * @author sravani
 * @version $ 9/30/20
 */
@Getter
@Setter
@AllArgsConstructor() @NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnroleeApiResponse {
    String status;
    String description;
    Map<String, String> errors;
}
