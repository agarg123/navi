package com.navi.controller;

import com.navi.service.DataProcessor;
import com.navi.service.DataPurgerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Abhishek Garg on 2019-04-12
 */

@RestController
@Api(value="Controller")
@Slf4j
@RequestMapping(value = "navi")
public class Controller {


    @Autowired
    private DataProcessor dataProcessor;

    @Autowired
    DataPurgerService dataPurgerService;

    @ApiOperation(value = "Process Ledgers")
    @RequestMapping(value = "/data", method = RequestMethod.POST, headers="Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public String processData(@RequestBody String data) {
            dataPurgerService.purgeAll();
           return dataProcessor.processData(data);
    }

    @ApiOperation(value = "Process Ledgers")
    @RequestMapping(value = "/file", method = RequestMethod.POST, headers="Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public String processCsvFile(@RequestParam("file") MultipartFile file) {
        dataPurgerService.purgeAll();
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                String completeData = new String(bytes);
                return dataProcessor.processData(completeData);
            }catch (Exception e) {
                log.error("Failed to process the file with exception ", e);
            }
        }
        return null;
    }

}
