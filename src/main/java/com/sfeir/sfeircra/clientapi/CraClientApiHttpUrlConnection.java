package com.sfeir.sfeircra.clientapi;

import com.google.gson.Gson;
import com.sfeir.sfeircra.cra.Cra;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;

/**
 *
 */
public class CraClientApiHttpUrlConnection {
    String baseApiUrl;

    public CraClientApiHttpUrlConnection(String baseApiUrl) {
        this.baseApiUrl = baseApiUrl;
    }

    public String login(String login, String password) {
         try {
            URL url = new URL(baseApiUrl + "api/login");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            StringBuilder body = new StringBuilder();
            body.append("login=");
            body.append(URLEncoder.encode(login));
            body.append("&password=");
            body.append(URLEncoder.encode(password));
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(body.toString());
            writer.flush();
            writer.close();
            os.close();
            conn.connect();
            InputStream in = new BufferedInputStream(conn.getInputStream());
            return readStream(in);
        } catch (IOException e) {
            // Gérer l'erreur
        }
        return null;
    }

    public Cra get(String token) {
        InputStream in = null;
        String jsonResponse = "";


        try {
            URL url = new URL(baseApiUrl + "api/cra?token="+token);
            HttpURLConnection conn =  (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.connect();

            //Prevent special Caracters
            in = new BufferedInputStream(conn.getInputStream());

            jsonResponse = readStream(in);
            Gson gson = new Gson();
            return gson.fromJson(jsonResponse, Cra.class);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Cra getMonth(String month, String year, String token) {
        InputStream in = null;
        String jsonResponse = "";


        try {
            URL url = new URL(baseApiUrl + "/api/cra/" + month + "-"+year+"?token="+token);
            HttpURLConnection conn =  (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            conn.connect();

            //Prevent special Caracters
            in = new BufferedInputStream(conn.getInputStream());

            jsonResponse = readStream(in);
            Gson gson = new Gson();
            return gson.fromJson(jsonResponse, Cra.class);

        } catch (MalformedURLException ex) {

        } catch (IOException ex) {

        }
        return null;
    }

    public boolean update(Cra cra) {
        InputStream in = null;
        String response = "";


        try {
            URL url = new URL(baseApiUrl + "/api/cra/");
            HttpURLConnection conn =  (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            Gson gson = new Gson();
            String body = gson.toJson(cra);
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(body.toString());
            writer.flush();
            writer.close();
            conn.connect();

            //Prevent special Caracters
            in = new BufferedInputStream(conn.getInputStream());

            response = readStream(in);
            return response.equals("CRA UPDATED");

        } catch (MalformedURLException ex) {

        } catch (IOException ex) {

        }
        return false;
    }

    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer data = new StringBuffer("");
        try {
            reader = new BufferedReader(new InputStreamReader(in, Charset.defaultCharset()));
            String line = "";
            while ((line = reader.readLine()) != null) {
                data.append(line);
            }
        } catch (IOException e) {
           e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return data.toString();
    }

}
