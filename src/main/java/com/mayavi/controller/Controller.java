package com.mayavi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Abhishek Garg on 2019-04-12
 */

@RestController
@Api(value="Controller")
public class Controller {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ApiOperation(value = "Test API")
    @GetMapping("/")
    public boolean test(){
        logger.info("ATeamHasNoName is ready to go");
        return true;
    }
}
