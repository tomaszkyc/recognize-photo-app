package com.tomaszkyc.app.core.services.apiconnection;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Set;

public class URLBuilder {


    public static String build(String pattern, Map<String, String> parameters) throws Exception{

        if (StringUtils.isEmpty(pattern)){
            throw new NullPointerException("Pattern is null or empty");
        }

        if ( parameters.size() == 0 ) {
            throw new Exception("No parameters given.");
        }

        String buildedURL = pattern;

        Set<String> keys = parameters.keySet();
        for(String key : keys) {

            buildedURL = buildedURL.replaceAll( key, parameters.get( key ) );

        }


        return buildedURL;
    }


}
