package com.mayavi.util;

import com.mayavi.models.APIResponses;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Abhishek Garg on 2019-04-12
 */

public class FormatUtil {

    public static String formatDmiQueryLeadResponse(String value, String leadId){
        String response = value.replaceFirst("\"Id\":\"(\\w+)", "\"Id\":\""+ leadId);
        return response.replaceFirst("(url\".*Lead/)(\\w+)","$1" + leadId);
    }

    public static boolean findIfPatternExists(String query, String regex){
        // Create a pattern to be searched
        Pattern pattern = Pattern.compile(regex);

        // Search above pattern in "geeksforgeeks.org"
        Matcher m = pattern.matcher(query);

        return m.find();
    }

    public static void printResponse(APIResponses apiResponses) {
        System.out.println(apiResponses.getApiResponse());
    }
}
