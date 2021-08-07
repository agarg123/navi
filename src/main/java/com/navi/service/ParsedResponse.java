package com.navi.service;

import java.util.regex.Matcher;

public abstract class ParsedResponse {
    public abstract String getType();

    public abstract void setParams(Matcher matcher) throws Exception;

    public abstract void processInput() throws Exception;
}
