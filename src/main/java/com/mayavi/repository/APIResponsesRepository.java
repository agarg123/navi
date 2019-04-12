package com.mayavi.repository;

import com.mayavi.models.APIResponses;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Abhishek Garg on 2019-04-12
 */

public interface APIResponsesRepository extends JpaRepository<APIResponses, Long> {

    APIResponses findByApiCallName(String apiCallName);
}
