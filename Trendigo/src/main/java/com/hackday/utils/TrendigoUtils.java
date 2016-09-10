package com.hackday.utils;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;

/**
 * Created by yogeesh.rajendra on 9/10/16.
 */
public class TrendigoUtils {

    private static Logger logger =Logger.getLogger(TrendigoUtils.class.getName());

    /**
     * Methd to get merchant id out of request if present
     *
     * @param incomingData
     * @return
     */
    public static JSONObject getRequestJson(InputStream incomingData) throws IOException {

        StringBuilder input = new StringBuilder();
        String line = null;

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));

            while ((line = in.readLine()) != null) {
                input.append(line);
            }

        } catch (IOException e) {
            logger.info("Error Reading JSON " + e.getMessage());
            logger.info("Line is : "+ line);
            throw e;
        }

        //If JSON is empty then throw bad request
        if (input.length() <= 0) {
            return null;
        }

        JSONObject payLoad = new JSONObject(input.toString());

        return payLoad;
    }
}
