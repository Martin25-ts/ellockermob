package com.android.ellocker.api;

import android.content.Context;
import android.content.Intent;

import com.android.ellocker.Dashboard;
import com.android.ellocker.Login;
import com.android.ellocker.R;
import com.android.ellocker.channel.Http;

import org.json.JSONException;
import org.json.JSONObject;

import io.github.muddz.styleabletoast.StyleableToast;

public class AuthApi {

    Context context;

    private Http httpCallback;

    public AuthApi(Context context) {
        this.context = context;
    }

    /*
    *   do logout use for handle using API logout from web server
    *   doLogout need "äpi_token" as a params for know which user will log out
    *
    * */
    public void doLogout(String apiToken){
        JSONObject params = new JSONObject();

        try {
            params.put("api_token", apiToken);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = params.toString();
        String url = Http.API + "logout/mobile";

        new Thread(new Runnable() {
            @Override
            public void run() {
                Http http = new Http(context,url);
                http.setMethod("POST");
                http.setData(data);
                http.setToken(true);
                http.send();

                setHttpCallback(http);

            }
        }).start();
    }

    /*
     *   sendLogin use for handle using API login from web server
     *   sendLogin need ["usename", "password"] as a params for checking credential
     *
     * */
    public void sendLogin(String username, String password){
        JSONObject params = new JSONObject();

        try {
            params.put("username", username);
            params.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String data = params.toString();
        String url = Http.API + "login/mobile";

        new Thread(new Runnable() {
            @Override
            public void run() {
                Http http = new Http(context,url);
                http.setMethod("POST");
                http.setData(data);
                http.send();

                setHttpCallback(http);
            }
        }).start();
    }


    public Http getHttpCallback() {
        return httpCallback;
    }

    public void setHttpCallback(Http httpCallback) {
        this.httpCallback = httpCallback;
    }
}


