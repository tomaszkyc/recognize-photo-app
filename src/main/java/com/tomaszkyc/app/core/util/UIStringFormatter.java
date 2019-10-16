package com.tomaszkyc.app.core.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class UIStringFormatter {

    private static final Logger log = LogManager.getLogger(UIStringFormatter.class);


    public static String formatAsNewLineDelimitedText(final String text,
                                                      String textDelimiter,
                                                      long numberOfCharsToDelimit) {

        //split by textDelimiter
        List<String> splitedText = new ArrayList<>( Arrays.asList( text.split(textDelimiter) ) );

        long totalCharCounter = 0;
        int idx = 0;


        StringJoiner formatedText = new StringJoiner( System.lineSeparator() );

        while( totalCharCounter < text.length() ) {

            long actualCharCounter = 0;
            StringBuilder textToAdd = new StringBuilder();

            Iterator itSplittedText = splitedText.listIterator();
            while( itSplittedText.hasNext() ) {

                String textFragment = (String)itSplittedText.next();
                textToAdd.append(textFragment);
                textToAdd.append( textDelimiter );
                actualCharCounter += textFragment.length() + 1;

                //check if is above limit
                if (actualCharCounter >= numberOfCharsToDelimit) {

                    itSplittedText.remove();
                    break;
                }
                else {
                    itSplittedText.remove();
                }

            }

            totalCharCounter += actualCharCounter;
            formatedText.add( textToAdd.toString() );


        }
        return formatedText.toString().substring( 0, formatedText.toString().length() - 1 );
    }

}
