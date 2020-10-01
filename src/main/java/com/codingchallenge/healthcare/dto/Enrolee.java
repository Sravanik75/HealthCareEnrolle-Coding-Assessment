package com.codingchallenge.healthcare.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

/**
 * @author sravani
 * @version $ 9/30/20
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class Enrolee {

    @NotEmpty
    String id;

    @NotEmpty
    String name;

    boolean status;

    @JsonFormat(pattern="mm-DD-yyyy")
    Date dob;

    @Valid
    List<Dependent> dependents;
}
