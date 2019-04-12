package com.mayavi.controller;

import com.mayavi.models.APIResponses;
import com.mayavi.models.GenericResponse;
import com.mayavi.repository.APIResponsesRepository;
import com.mayavi.util.Constants;
import com.mayavi.util.FormatUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Abhishek Garg on 2019-04-12
 */

@RestController
@Api(value="Controller")
@RequestMapping(value = "mayavi")
public class Controller {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private APIResponsesRepository aPIResponsesRepository;

    @ApiOperation(value = "Test API")
    @GetMapping("/test")
    public boolean test(){
        logger.info("ATeamHasNoName is ready to go");
        return true;
    }

    @ApiOperation(value = "DMI Get lead")
    @RequestMapping(value = "/services/data/v39.0/sobjects/lead/{leadId}", method = RequestMethod.GET, headers="Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public String dmiQueryLead(@ApiParam(value = "lead Id", required = true) @PathVariable String leadId) {
        APIResponses apiResponses = aPIResponsesRepository.findByApiCallName(Constants.DMI_GET_LEAD_API_NAME);
        return FormatUtil.formatDmiQueryLeadResponse(apiResponses.getApiResponse(), leadId);
    }

    @ApiOperation(value = "DMI NSDL query, fetches user name based on pan")
    @RequestMapping(value = "/karza/pan", method = RequestMethod.POST, headers="Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public String dmiNsdlPanVerification() {

        APIResponses apiResponses = aPIResponsesRepository.findByApiCallName(Constants.DMI_NSDL_CHECK_API_NAME);
        if (apiResponses==null){
            return null;
        }
        return apiResponses.getApiResponse();
    }

    @ApiOperation(value = "Update api response")
    @RequestMapping(value = "/update/{apiName}", method = RequestMethod.POST, headers="Accept=application/json")
    public GenericResponse updateResponse(@ApiParam(value = "api name", required = true)@PathVariable String apiName,
                                 @RequestBody String response){
        APIResponses apiResponses = aPIResponsesRepository.findByApiCallName(apiName);
        if (apiResponses!=null){
            apiResponses.setApiResponse(response);
            aPIResponsesRepository.save(apiResponses);
            return new GenericResponse<String>(true, "Successfully updated the response");
        }
        return new GenericResponse<String>(false, "Failed to update the response");
    }

    @ApiOperation(value = "DMI Submit contact/Query lead/ Get lead")
    @RequestMapping(value = "services/data/v39.0/query/", method = RequestMethod.GET, headers="Accept=application/json")
    public String dmiSubmitContact(@RequestParam(name = "q") String q){

        APIResponses apiResponses = null;
        if (FormatUtil.findIfPatternExists(q, Constants.DMI_SUBMIT_CONTACT_REGEX)){
            apiResponses = aPIResponsesRepository.findByApiCallName(Constants.DMI_SUBMIT_CONTACT_API_NAME);

        }else if(FormatUtil.findIfPatternExists(q, Constants.DMI_QUERY_BANK_DETAIL_REGEX)){
            apiResponses = aPIResponsesRepository.findByApiCallName(Constants.DMI_QUERY_BANK_DETAIL_API_NAME);

        }else if(FormatUtil.findIfPatternExists(q, Constants.DMI_QUERY_LEAD_DETAIL_REGEX)){
            apiResponses = aPIResponsesRepository.findByApiCallName(Constants.DMI_QUERY_LEAD_API_NAME);
        }
        if (apiResponses==null){
            return null;
        }
        return apiResponses.getApiResponse();
    }

    @ApiOperation(value = "DMI Create lead")
    @RequestMapping(value = "services/data/v39.0/sobjects/lead/", method = RequestMethod.POST, headers="Accept=application/json")
    public String dmiCreateLead(){

        APIResponses apiResponses = aPIResponsesRepository.findByApiCallName(Constants.DMI_CREATE_LEAD_API_NAME);
        if (apiResponses==null){
            return null;
        }
        return apiResponses.getApiResponse();
    }


    @ApiOperation(value = "Get Default response for given API call name")
    @RequestMapping(value = "getResponse/{apiName}", method = RequestMethod.GET, headers="Accept=application/json")
    public String getApiResponse(@ApiParam(value = "api name", required = true)@PathVariable String apiName){
        APIResponses apiResponses = aPIResponsesRepository.findByApiCallName(apiName);
        if (apiResponses==null){
            return "API hasn't been configured yet";
        }
        return apiResponses.getApiResponse();
    }

    @ApiOperation(value = "DMI create contact")
    @RequestMapping(value = "services/data/v39.0/sobjects/contact/", method = RequestMethod.POST, headers="Accept=application/json")
    public String dmiCreateContact(){
        APIResponses apiResponses = aPIResponsesRepository.findByApiCallName(Constants.DMI_CREATE_CONTACT_API_NAME);
        if (apiResponses==null){
            return "API hasn't been configured yet";
        }
        return apiResponses.getApiResponse();
    }

//    @ApiOperation(value = "DMI Cibil")
//    @RequestMapping(value = "services/data/v39.0/sobjects/contact/", method = RequestMethod.POST, headers="Accept=application/json")
//    public String dmiCibil(){
//        APIResponses apiResponses = aPIResponsesRepository.findByApiCallName(Constants.DMI_CREATE_CONTACT_API_NAME);
//        if (apiResponses==null){
//            return "API hasn't been configured yet";
//        }
//        return apiResponses.getApiResponse();
//    }
}
