package Server;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 *
 * @author cardosoken
 */
public class LoadFiles {

    public String readFiles() throws IOException {
        String JSONData = "";
        //  Some of the code is implemented to handle HTTPS self signed certicicate issues:http://stackoverflow.com/questions/2893819/telling-java-to-accept-self-signed-ssl-certificate (Disable Certificate Validation )
        TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) {
                }
            }
        };

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (GeneralSecurityException e) {
                System.err.println("Unable to install trusting manager for the Load files functon");
        }
        // Now you can access an https URL without having the certificate in the truststore
        try {

            URL oracle = new URL("https://students.btsi.lu/carke/simulation/simulation.php?getFiles");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(oracle.openStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                JSONData += inputLine;
            }

            in.close();
        } catch (MalformedURLException e) {
            System.err.println("LoadFiles: Unable to load files.");
        }
        return JSONData;
    }
}
