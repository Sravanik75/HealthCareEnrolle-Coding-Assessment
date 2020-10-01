package com.codingchallenge.healthcare.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * @author Sravani
 * @version $ 9/30/20
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@ToString
public class Dependent {

    @NotEmpty
    String id;

    @NotEmpty
    String name;

    @JsonFormat(pattern="mm-DD-yyyy")
    Date dob;
}
