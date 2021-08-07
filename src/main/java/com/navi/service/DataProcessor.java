package com.navi.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class DataProcessor {

    public String processData(String data) {
        try {
            if (data != null) {
                String[] inputs = data.split("\n");
                return processDataArr(inputs);
            } else {
                log.error("Empty data received");
            }
        }catch (Exception e) {
            log.error("Failed to process data : {}, ", data, e);
        }
        return null;
    }

    private String processDataArr(String[] inputs) {
        try {
            List<String>  output = new ArrayList<>();
            for (String input : inputs) {
                String processedOutput = processIndividualInput(input);
                if (processedOutput != null) {
                    output.add(processedOutput);
                }
            }
        }catch (Exception e) {
            log.error("Failed to process inputs : {} with exception", inputs, e);
        }
        return null;
    }

    private String processIndividualInput(String input) {
        log.debug("Starting to parse : {}", input);
        ParsedResponse parsedResponse = null;
        try {
            parsedResponse = LineParserFactory.getParser(input);
            log.debug("Successfully parsed input : {}", input);
        }catch (Exception e) {
            log.error("Failed to parse line input : {} with exception ", input, e);
            return null;
        }
        log.debug("Starting to process input : {}", input);
        try {
            parsedResponse.processInput();
            log.debug("Successfully processed input : {}", input);
        }catch (Exception e) {
            log.error("Failed to process line input : {} with exception ", input, e);
        }
        return null;
    }
}
