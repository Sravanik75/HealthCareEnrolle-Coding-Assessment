package com.codingchallenge.healthcare.model;

import com.codingchallenge.healthcare.dto.Dependent;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/**
 * @author Sravani
 * @version $ 9/30/20
 */
@Document
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnroleeModel {

    @Id
    String id;
    String name;
    boolean status;
    @JsonFormat(pattern="mm-DD-yyyy")
    Date dob;
    List<Dependent> dependents;

}
