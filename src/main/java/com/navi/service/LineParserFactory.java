package com.navi.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class LineParserFactory {

    @Autowired
    List<ParsedResponse> parsedResponses;

    private static final Map<String, ParsedResponse> parsedResponseServiceCache = new HashMap<>();

    public static ParsedResponse getParser(String input) throws Exception {
        Pattern pattern = Pattern.compile("\\S+");
        Matcher matcher = pattern.matcher(input);
        ParsedResponse parsedResponse = null;
        if (matcher.find()) {
            parsedResponse = parsedResponseServiceCache.get(matcher.group());
            if (parsedResponse == null) {
                log.error("Unknown command type : {}", matcher.group());
                throw new Exception("Unknown Command type : " + matcher.group());
            }
            parsedResponse.setParams(matcher);
        }
        return parsedResponse;
    }

    @PostConstruct
    public void init() {
        for (ParsedResponse parsedResponse : parsedResponses) {
            parsedResponseServiceCache.put(parsedResponse.getType(), parsedResponse);
        }
    }
}
