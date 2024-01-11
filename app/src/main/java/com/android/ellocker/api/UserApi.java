package com.android.ellocker.api;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;

import com.android.ellocker.Dashboard;
import com.android.ellocker.Login;
import com.android.ellocker.R;
import com.android.ellocker.channel.Http;
import com.android.ellocker.channel.LocalStorage;
import com.android.ellocker.user.User;

import org.json.JSONException;
import org.json.JSONObject;

import io.github.muddz.styleabletoast.StyleableToast;

public class UserApi {



    private Http httpCallBack;

    Context context;
    private User user;

    public UserApi(Context context) {
        this.context = context;
    }

    public void setUserInfo(TextView tvusername) {


        String[] frontname = this.user.getFrontName().split(" ");

        String username = frontname[0] + " " + this.user.getLastName();
        tvusername.findViewById(R.id.tvusername);
        tvusername.setText(username);



    }

    public void getUser(String apiToken) {

        JSONObject params = new JSONObject();


        try {
            params.put("api_token", apiToken);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String data = params.toString();
        String url = Http.API + "user/mobile";

        new Thread(new Runnable() {
            @Override
            public void run() {
                Http http = new Http(context,url);
                http.setData(data);
                http.setMethod("POST");
                http.send();

                setHttpCallBack(http);

            }
        }).start();
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Http getHttpCallBack() {
        return httpCallBack;
    }

    public void setHttpCallBack(Http httpCallBack) {
        this.httpCallBack = httpCallBack;
    }


}
