package com.android.ellocker;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.ellocker.adapter.ListLockerAdapter;
import com.android.ellocker.api.AuthApi;
import com.android.ellocker.api.UserApi;
import com.android.ellocker.channel.Http;
import com.android.ellocker.channel.LocalStorage;
import com.android.ellocker.transaction.Transaction;
import com.android.ellocker.user.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.github.muddz.styleabletoast.StyleableToast;

public class Dashboard extends AppCompatActivity implements View.OnClickListener {

    TextView tvusername;

    ImageView ivhome,ivprofile,ivlogout;
    Intent pindahlogin;
    LocalStorage localStorage;

    List<Transaction> lockerList = new ArrayList<Transaction>();
    RecyclerView rvlocker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        tvusername = findViewById(R.id.tvusername);
        ivhome = findViewById(R.id.ivhome);
        ivprofile = findViewById(R.id.ivprofile);
        ivlogout = findViewById(R.id.ivlogout);
        ivlogout.setOnClickListener(this);

        localStorage = new LocalStorage(this);

        // Load For User Info
        setUser();


        // Load for user locker list
        getLockerList();

    }

    public void createListLocker(){
        rvlocker = findViewById(R.id.rvlocker);
        rvlocker.setHasFixedSize(true);
        rvlocker.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,
                false));

        ListLockerAdapter listlockeradapter = new ListLockerAdapter(lockerList);
        rvlocker.setAdapter(listlockeradapter);
    }

    public void setUser(){
        Handler handler = new Handler();
        UserApi userapi = new UserApi(this);
        userapi.getUser(localStorage.getToken());

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Integer code  = userapi.getHttpCallBack().getStatusCode();
                if (code >= 200 && code <= 299){
                    try {
                        JSONObject response = new JSONObject(userapi.getHttpCallBack().getRespone());

                        User user = new User(response.getString("user_id"),
                                response.getString("front_name"),
                                response.getString("last_name"),
                                response.getString("email"),
                                response.getString("user_phone"),
                                Integer.parseInt(response.getString("role_id")),
                                Integer.parseInt(response.getString("status_user_id")));

                        userapi.setUser(user);
                        userapi.setUserInfo(tvusername);


                    } catch (JSONException e) {
                        e.printStackTrace();

                        AuthApi authApi = new AuthApi(Dashboard.this);
                        authApi.doLogout(localStorage.getToken());
                        localStorage.clearToken();

                        StyleableToast.makeText(Dashboard.this,"Gagal mendapatkan user info",R.style.exampleToast_error).show();
                        pindahlogin = new Intent(Dashboard.this, Login.class);
                        startActivity(pindahlogin);
                    }
                }
            }
        },500);

    }

    public void getLockerList(){

        JSONObject params = new JSONObject();

        try {
            params.put("api_token", localStorage.getToken());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String data = params.toString();
        String url = Http.API + "user/getListLocker";

        new Thread(new Runnable() {
            @Override
            public void run() {
                Http http = new Http(Dashboard.this,url);
                http.setData(data);
                http.setMethod("POST");
                http.send();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Integer code = http.getStatusCode();

                        if(code == 200){
                            try {
                                JSONObject response = new JSONObject(http.getRespone());
                                JSONArray transaction = response.getJSONArray("transaction");

                                lockerList.clear();

                                for (int i = 0;  i < transaction.length(); i++){

                                    try {

                                        JSONObject transactionObject = transaction.getJSONObject(i);


                                        lockerList.add(new Transaction(String.valueOf(String.valueOf(transactionObject.getInt("locker_id"))),
                                                transactionObject.getString("locker_number"),
                                                transactionObject.getString("locker_size"),
                                                transactionObject.getInt("status_door") == 1,
                                                transactionObject.getString("location_name"),
                                                transactionObject.getString("location_url"),
                                                String.valueOf(transactionObject.getInt("detial_id")),
                                                transactionObject.getInt("duration"),
                                                String.valueOf(transactionObject.getInt("transaction_id")),
                                                transactionObject.getString("transaction_date"),
                                                String.valueOf(transactionObject.getInt("user_id")),
                                                String.valueOf(transactionObject.getInt("status_transaction_id"))));

                                    }catch (JSONException e){
                                        e.printStackTrace();
                                        Log.e("JSON ERROR", e.getMessage());
                                        Log.e("JSON ERROR", "Error creating JSONObject at index " + i);
                                    }


                                }

                                createListLocker();

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.d("CHECK+API", "ERROR CODE: " + code);
                            }
                        }else{
                            Log.d("CHECK+API", "ERROR CODE: " + code);
                        }
                    }
                });
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ivlogout){
            AuthApi authApi = new AuthApi(Dashboard.this);
            authApi.doLogout(localStorage.getToken());

            localStorage.clearToken();
            pindahlogin = new Intent(Dashboard.this, Login.class);
            startActivity(pindahlogin);
        }
    }



}