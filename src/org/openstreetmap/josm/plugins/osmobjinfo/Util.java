/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openstreetmap.josm.plugins.osmobjinfo;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import org.openstreetmap.josm.tools.HttpClient;
import org.openstreetmap.josm.tools.HttpClient.Response;

/**
 *
 * @author ruben
 */
public class Util {

    public static int getNumChangeset(String user) throws IOException {
        int numchanges = 0;
        String url = "http://hdyc.neis-one.org/search/" + user;
        Response resp = HttpClient.create(new URL(url), "GET").setRequestBody(url.getBytes()).connect();
        System.out.println(resp.getResponseCode());
        try (JsonReader jsonReader = Json.createReader(new StringReader(resp.fetchContent()))) {
            JsonObject jsonObject = jsonReader.readObject();
            JsonObject jsonChangeset = jsonObject.getJsonObject("changesets");
            numchanges = Integer.parseInt(jsonChangeset.getString("no"));
            System.out.println(numchanges);

        } catch (IOException ex) {
        }
        resp.disconnect();
        return numchanges;
    }

    public static String typeofMaper(int d) {

        String l = "nothing";
        if (d < 25) {
            l = "A Hit-and-Run Mapper";
        } else if (d < 100) {
            l = "A Newbie";
        } else if (d < 500) {
            l = "A Casual Mapper";
        } else if (d < 1E3) {
            l = "A Heavy Mapper";
        } else if (d < 2500) {
            l = "A Heavy Mapper 2.0";
        } else if (d < 5E3) {
            l = "An Addicted Mapper";
        } else if (d < 1E4) {
            l = "A Crazy Mapper";
        } else if (d < 2E4) {
            l = "An Ueber Mapper";
        } else if (d >= 2E4) {
            l = "An Epic Mapper";
        } else if (d > 3E4) {
            l = "A Bot ;)";
        }

//      if (d > 5E3) {
//        for (b = m = 0; b < i.length; b++) m += i[b][1];
//        m /= i.length;
//        m = m * 0.5;
//        n = "true";
//        for (b = 0; b < i.length; b++)
//          if (i[b][1] < m) n = "false";
//        if (n == "true") l = "A Bot ;)"
//      }
//      
//      if (d > 3E4) l = "A Bot ;)";
//    }
        return l;
    }
}
