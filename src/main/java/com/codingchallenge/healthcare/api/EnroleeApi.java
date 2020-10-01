package com.codingchallenge.healthcare.api;


import com.codingchallenge.healthcare.dto.Dependent;
import com.codingchallenge.healthcare.dto.Enrolee;
import com.codingchallenge.healthcare.dto.EnroleeApiResponse;
import com.codingchallenge.healthcare.exception.InvalidInputException;
import com.codingchallenge.healthcare.exception.NoEnroleeFoundException;
import com.codingchallenge.healthcare.service.EnroleeService;
import com.codingchallenge.healthcare.util.AppConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Sravani
 * @version $ 9/30/20
 */
@Slf4j
@RestController
@Validated
@AllArgsConstructor
@RequestMapping("/api/v1")
public class EnroleeApi {

    private final EnroleeService enroleeService;

    /**
     * Method to get enrolee details given an enrolee id
     *
     * @param id enrolee id
     * @return Enrolee details
     */
    @GetMapping(value = "/enrolees/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_STREAM_JSON_VALUE})
    public ResponseEntity<Enrolee> getEnrolee(@PathVariable("id") @NotBlank String id) throws NoEnroleeFoundException {
        log.info("getEnrolee request received for "+id);
        return ResponseEntity
                .ok(enroleeService.getEnrolee(id));
    }

    /**
     * add enrolee details
     * @param enrolee
     * @return
     */

    @PostMapping(value = "/enrolees",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EnroleeApiResponse> addEnrolee(@Valid @RequestBody Enrolee enrolee) {
        log.info("addEnrolee request received for "+enrolee.toString());
        enroleeService.addOrUpdateEnrolee(enrolee);
        return ResponseEntity
                .ok(new EnroleeApiResponse(AppConstants.SUCCESS, "Operation completed.", null));
    }

    /**
     * update enrolee details
     * @param enrolee
     * @return
     */

    @PutMapping(value = "/enrolees",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EnroleeApiResponse> updateEnrolee(@Valid @RequestBody Enrolee enrolee) {
        log.info("updateEnrolee request received for "+enrolee.toString());
        enroleeService.addOrUpdateEnrolee(enrolee);
        return ResponseEntity
                .ok(new EnroleeApiResponse(AppConstants.SUCCESS, "Operation completed.", null));
    }

    /**
     * Delete an enrolee by id
     * @param enroleeId
     * @return
     */
    @DeleteMapping(value = "/enrolees/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EnroleeApiResponse> deleteEnrolee(@PathVariable("id") @NotBlank String enroleeId) {
        log.info("deleteEnrolee request received for "+enroleeId);
        enroleeService.removeEnrolee(enroleeId);
        return ResponseEntity
                .ok(new EnroleeApiResponse(AppConstants.SUCCESS, "Operation completed.", null));
    }

    /**
     * Add dependents
     * @param enroleeId
     * @param dependentList
     * @return
     * @throws InvalidInputException
     */
    @PostMapping(value = "/enrolees/{id}/dependents",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EnroleeApiResponse> addDependents(@PathVariable("id") @NotBlank String enroleeId,
                                                            @Valid @RequestBody List<Dependent> dependentList) throws InvalidInputException {
        log.info("addDependents request received for enrolee "+enroleeId+"-- data "+dependentList.toString());
        enroleeService.addOrUpdateDependents(enroleeId, dependentList);
        return ResponseEntity
                .ok(new EnroleeApiResponse(AppConstants.SUCCESS, "Operation completed.", null));
    }


    /**
     * Update dependents
     * @param enroleeId
     * @param dependentList
     * @return
     * @throws InvalidInputException
     */
    @PutMapping(value = "/enrolees/{id}/dependents",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EnroleeApiResponse> updateDependents(@PathVariable("id") @NotBlank String enroleeId,
                                                            @Valid @RequestBody List<Dependent> dependentList) throws InvalidInputException {
        log.info("updateDependents request received for enrolee "+enroleeId+"-- data "+dependentList.toString());
        enroleeService.addOrUpdateDependents(enroleeId, dependentList);
        return ResponseEntity
                .ok(new EnroleeApiResponse(AppConstants.SUCCESS, "Operation completed.", null));
    }


    /**
     * Delete enrolee dependent by id
     * @param enroleeId
     * @param dependentId
     * @return
     * @throws InvalidInputException
     */
    @DeleteMapping(value = "/enrolees/{id}/dependents/{dependentId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EnroleeApiResponse> deleteEnrolee(@PathVariable("id") @NotBlank String enroleeId,
                                                            @PathVariable("dependentId") @NotBlank String dependentId) throws InvalidInputException {
        log.info("deleteDependents request received for enrolee "+enroleeId+"-- dependentId "+dependentId);
        enroleeService.removeDependent(enroleeId, dependentId);
        return ResponseEntity
                .ok(new EnroleeApiResponse(AppConstants.SUCCESS, "Operation completed.", null));
    }
}