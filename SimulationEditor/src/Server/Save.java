/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;
import static javafx.css.StyleOrigin.USER_AGENT;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 *
 * @author cardosoken
 */
public class Save {

    /**
     * Note: Saving worked perfectly fine on the VM box server. Permissions are
     * normally correctly set (775). The server does not simple respond to the
     * request back.
     *
     * @param pData JSONData
     * @param pFileName Name of the file
     * @throws MalformedURLException
     * @throws ProtocolException
     * @throws IOException
     */
    public void sendData(String pData, String pFileName) throws MalformedURLException, ProtocolException, IOException {

        String json = pData;
        String url = "http://192.168.56.10/serss/simulation/simulation.php";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setDoOutput(true);
        con.setRequestMethod("POST");
        OutputStream os = con.getOutputStream();
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        //wr.write(new String("json=" + json).getBytes());
        String param = "json=" + URLEncoder.encode(json, "UTF-8") + "&" + "name=" + URLEncoder.encode(pFileName, "UTF-8");
        wr.write(param.getBytes());
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println(responseCode);

    }
}
