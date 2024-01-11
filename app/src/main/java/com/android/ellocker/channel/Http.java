package com.android.ellocker.channel;
import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Http {
    Context context;

    public static String API = "http://192.168.100.148:6969/api/";
    private String url, method = "GET", data = null, respone = null;
    private Integer statusCode = 0;
    private Boolean token = false;

    private LocalStorage localStorage;


    public Http(Context context, String url){
        this.context = context;
        this.url = url;
        localStorage = new LocalStorage(context);
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setToken(Boolean token) {
        this.token = token;
    }

    public String getRespone() {
        return respone;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void send(){
        try {

            URL sURL = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) sURL.openConnection();
            Log.d("Http_method", "Request Method: " + method);

            connection.setRequestMethod(method);
            connection.setRequestProperty("Content-Type","application/json");
            connection.setRequestProperty("X-Requested-With", "XMLHttpRequest");


            if(token){
                connection.setRequestProperty("Authorization","Bearer " + localStorage.getToken());
            }

            if (! method.equals("GET")) {
                connection.setDoOutput(true);
            }

            if (data != null){
                OutputStream os = connection.getOutputStream();
                os.write(data.getBytes());
                os.flush();
                os.close();
            }
            statusCode = connection.getResponseCode();

            InputStreamReader isr;

            if (statusCode >= 200 && statusCode <= 299){
                isr = new InputStreamReader(connection.getInputStream());
            }else {
                isr = new InputStreamReader(connection.getErrorStream());
            }

            BufferedReader br = new BufferedReader(isr);
            StringBuffer sb = new StringBuffer();
            String line;

            while((line = br.readLine()) != null){
                sb.append(line);
            }

            br.close();
            respone = sb.toString();
            Log.d("Http_response", "Server Response: " + respone);

        }catch (IOException e){
            Log.e("Http", "IOException: " + e.getMessage());
            e.printStackTrace();

        }
    }
}