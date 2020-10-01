package com.codingchallenge.healthcare.service;

import com.codingchallenge.healthcare.dto.Dependent;
import com.codingchallenge.healthcare.dto.Enrolee;
import com.codingchallenge.healthcare.exception.InvalidInputException;
import com.codingchallenge.healthcare.exception.NoEnroleeFoundException;
import com.codingchallenge.healthcare.model.EnroleeModel;
import com.codingchallenge.healthcare.repository.EnroleeRespository;
import com.codingchallenge.healthcare.util.EnroleeTransformer;
import com.codingchallenge.healthcare.util.ErrorCodes;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Sravani
 * @version $ 9/30/20
 */
@Service
@AllArgsConstructor
@Slf4j
public class EnroleeService {
    private EnroleeRespository enroleeRespository;

    public void addOrUpdateEnrolee(Enrolee enrolee){
        log.info("addEnrolee request received for "+enrolee.toString());
        enroleeRespository
                .save(EnroleeTransformer.transformToModel(enrolee));
    }

    public Enrolee getEnrolee(String enroleeId) throws NoEnroleeFoundException {
        log.info("getEnrolee request received for "+enroleeId);
        Optional<EnroleeModel> model = enroleeRespository
                .findById(enroleeId);
        if(model.isPresent()){
            return EnroleeTransformer.transformToDto(model.get());
        }else{
            throw new NoEnroleeFoundException(ErrorCodes.NO_RECORDS_FOUND.getCode(), "No records found");
        }


    }

    public void removeEnrolee(String enroleeId){
        log.info("deleteEnrolee request received for "+enroleeId);
        enroleeRespository.deleteById(enroleeId);
    }

    public void addOrUpdateDependents(String enroleeId, List<Dependent> newDependents) throws InvalidInputException {
        log.info("addOrUpdateDependents request received for enrolee "+enroleeId+"-- data "+newDependents.toString());
        Optional<EnroleeModel> enroleeModelOptional = enroleeRespository.findById(enroleeId);
        if(enroleeModelOptional.isPresent()){
            EnroleeModel enroleeModel = enroleeModelOptional.get();
            enroleeModel
                    .setDependents(mergeDependents(newDependents, enroleeModel.getDependents()));
            enroleeRespository.save(enroleeModel);
        }else{
            throw new InvalidInputException(ErrorCodes.INVALID_INPUT.getCode(), "Enrolee dosen't exist");
        }

    }

    public void removeDependent(String enroleeId, String dependentId) throws InvalidInputException {
        log.info("deleteDependents request received for enrolee "+enroleeId+"-- dependentId "+dependentId);
        Optional<EnroleeModel> enroleeModelOptional = enroleeRespository.findById(enroleeId);
        if(enroleeModelOptional.isPresent()){
            EnroleeModel enroleeModel = enroleeModelOptional.get();
            List<Dependent> effectiveDependents = enroleeModelOptional
                    .get()
                    .getDependents()
                    .stream()
                    .filter(d -> ! d.getId().equalsIgnoreCase(dependentId))
                    .collect(Collectors.toList());
            enroleeModel
                    .setDependents(effectiveDependents);
            enroleeRespository.save(enroleeModel);
        }else{
            throw new InvalidInputException(ErrorCodes.INVALID_INPUT.getCode(), "Enrolee dosen't exist");
        }
    }

    /**
     * If the input dependent is already present it will be updated otherwise the dependent will be added to the enrolee
     * @param newDependents
     * @param existingDependents
     * @return mergedDependents
     */

    private List<Dependent> mergeDependents(List<Dependent> newDependents, List<Dependent> existingDependents) {
        List<Dependent> mergedList = new ArrayList<>();
        List<String> newDependentIds = newDependents
                .stream()
                .map(d -> d.getId())
                .collect(Collectors.toList());

        List<Dependent> untouchedDependents = existingDependents
                .stream()
                .filter(d -> ! newDependentIds.contains(d.getId()))
                .collect(Collectors.toList());

        if(untouchedDependents != null && ! untouchedDependents.isEmpty()){
            mergedList.addAll(untouchedDependents);
        }
        mergedList.addAll(newDependents);
        return mergedList;

    }

}
