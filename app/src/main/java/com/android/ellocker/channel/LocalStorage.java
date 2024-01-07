package com.android.ellocker.channel;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class LocalStorage {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    String token;

    public LocalStorage(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences("STORAGE_LOGIN_API", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public String getToken() {
        token = sharedPreferences.getString("TOKEN", "");
        Log.d("LocalStorage", "Token: " + token);
        return token;
    }

    public void setToken(String token) {
        editor.putString("TOKEN", token);
        editor.commit();
        this.token = token;
    }

    public void clearToken() {
        editor.remove("TOKEN");
        editor.apply();
        this.token = null;
        Log.d("LocalStorage", "Token cleared");
    }
}

