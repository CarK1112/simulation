/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

/**
 *
 * @author cardosoken
 */
public class Save {

    public void sendData(String pData) throws MalformedURLException, ProtocolException, IOException {
        String json = pData;
        System.out.println(json);

        String url = "http://192.168.56.10/serss/simulation/simulation.php";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setDoOutput(true);
        con.setRequestMethod("POST");
        con.setRequestProperty("json", json);

        OutputStream os = con.getOutputStream();
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        //wr.write(new String("json=" + json).getBytes());
        String param = "json=" + URLEncoder.encode(json, "UTF-8");
        wr.write(param.getBytes());

        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println(responseCode);
    }

}
