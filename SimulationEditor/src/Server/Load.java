/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 *
 * @author cardosoken
 */
public class Load {

    public String readData() throws IOException {
        String JSONData = "";
        URL oracle = new URL("http://192.168.56.10/serss/simulation/simulation.json");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(oracle.openStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            JSONData += inputLine;
        }

        in.close();
        return JSONData;
    }
}
