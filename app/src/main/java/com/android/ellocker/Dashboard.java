package com.android.ellocker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.ellocker.channel.Http;
import com.android.ellocker.channel.LocalStorage;
import com.android.ellocker.user.User;

import org.json.JSONException;
import org.json.JSONObject;

import io.github.muddz.styleabletoast.StyleableToast;

public class Dashboard extends AppCompatActivity implements View.OnClickListener {

    User userDashboard;
    TextView tvemail, tvnamadepan, tvnamabelakang,tvnomorponsel, tvusername;

    ImageView ivhome,ivprofile,ivlogout;
    Intent pindahlogin,check;
    LocalStorage localStorage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Log.d("Debug FC", "Dashboard");

        tvusername = findViewById(R.id.tvusername);
        ivhome = findViewById(R.id.ivhome);
        ivprofile = findViewById(R.id.ivprofile);
        ivlogout = findViewById(R.id.ivlogout);
        ivlogout.setOnClickListener(this);

        localStorage = new LocalStorage(this);


        getUser(new UserCallback() {
            @Override
            public void onUserReceived(User user) {
                userDashboard = setUserInfo(user);
            }

            @Override
            public void onFailure(String message) {

                doLogout(message);

            }
        });

    }

    private void getUser(UserCallback userCallback) {

        JSONObject params = new JSONObject();

        try {
            params.put("api_token", localStorage.getToken());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String data = params.toString();
        String url = "http://192.168.100.148:6969/api" + "/user/mobile";

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


                        if(code == 200) {
                            try {
                                JSONObject response = new JSONObject(http.getRespone());

                                User user = new User(response.getString("user_id"),
                                        response.getString("front_name"),
                                        response.getString("last_name"),
                                        response.getString("email"),
                                        response.getString("user_phone"),
                                        Integer.parseInt(response.getString("role_id")),
                                        Integer.parseInt(response.getString("status_user_id")));

                                userCallback.onUserReceived(user);



                            } catch (JSONException e) {
                                e.printStackTrace();
                                userCallback.onFailure("Error parsing JSON");
                            }
                        }else if(code == 401){
                            try {
                                JSONObject response = new JSONObject(http.getRespone());

                                StyleableToast.makeText(Dashboard.this, response.getString("message"),R.style.exampleToast_allert).show();

                                localStorage.clearToken();

                                pindahlogin = new Intent(Dashboard.this,Login.class);
                                startActivity(pindahlogin);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }else {

                            try {
                                JSONObject response = new JSONObject(http.getRespone());

                                localStorage.clearToken();

                                pindahlogin = new Intent(Dashboard.this,Login.class);
                                startActivity(pindahlogin);
                                userCallback.onFailure("System Failed : " + response.getString("message") + " " + code);
                            } catch (JSONException e) {
                                e.printStackTrace();

                            }
                        }
                    }
                });
            }
        }).start();
    }

    private void doLogout(String message){
        JSONObject params = new JSONObject();

        try {
            params.put("api_token", localStorage.getToken());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = params.toString();
        String url = "http://192.168.100.148:6969/api/logout/mobile";

        new Thread(new Runnable() {
            @Override
            public void run() {
                Http http = new Http(Dashboard.this,url);
                http.setMethod("POST");
                http.setData(data);
                http.setToken(true);
                http.send();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Integer code = http.getStatusCode();
                        if(code == 200){
                            localStorage.clearToken();
                            StyleableToast.makeText(Dashboard.this, message, R.style.exampleToast_true).show();
                            pindahlogin = new Intent(Dashboard.this,Login.class);
                            startActivity(pindahlogin);
                        }else{
                            StyleableToast.makeText(Dashboard.this,"Gagal Logout" + message, R.style.exampleToast_error).show();

                        }
                    }
                });
            }
        }).start();
    }

    private User setUserInfo(User user) {
        String[] frontname = user.getFrontName().split(" ");
        String username = frontname[0] + " " + user.getLastName();

        tvusername.setText(username);

        return user;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ivlogout){
            doLogout("Akan di alihkan ke login");
        }
    }

    public interface UserCallback {
        void onUserReceived(User user);
        void onFailure(String message);
    }
}