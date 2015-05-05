/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cohesion.imgrecog.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Calvin He
 */
public class RequestReader {

    public static JSONObject ToJsonObject(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        String line = null;
        JSONObject json = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            Logger.getLogger(RequestReader.class.getName()).log(Level.SEVERE, null, e);
        }

        try {
            json = new JSONObject(sb.toString());
        } catch (JSONException ex) {
            Logger.getLogger(RequestReader.class.getName()).log(Level.SEVERE, null, ex);
        }

        return json;
    }
}
