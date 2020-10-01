package com.codingchallenge.healthcare.repository;

import com.codingchallenge.healthcare.model.EnroleeModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author sravani
 * @version $ 9/30/20
 */
@Repository
public interface EnroleeRespository extends MongoRepository<EnroleeModel, String> {

}
