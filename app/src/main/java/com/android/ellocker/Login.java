package com.android.ellocker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.ellocker.channel.Http;
import com.android.ellocker.channel.LocalStorage;
import com.android.ellocker.helper.Validotor;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import io.github.muddz.styleabletoast.StyleableToast;
public class Login extends AppCompatActivity  implements View.OnClickListener, Validotor {
    TextView tvdaftar;
    TextInputEditText tietemail, tietpassword;
    Intent pindahregister,pindahdashboard;
    Button btnmasuk;
    LocalStorage localStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        localStorage = new LocalStorage(Login.this);

        if(localStorage.getToken() != null && !localStorage.getToken().isEmpty()){
            Log.d("Debug FC", "has token Login");
            pindahdashboard = new Intent(Login.this, Dashboard.class);
            startActivity(pindahdashboard);
            Log.d("Debug FC", "has token block");
        }

        Log.d("Debug FC", "hasnt token login");

        ImageView ivlogo = findViewById(R.id.ivlogo);
        ivlogo.setImageResource(R.drawable.logo);

        tietemail = findViewById(R.id.tietemail);
        tietemail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateEmail(s.toString(),tietemail);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tietpassword = findViewById(R.id.tietpassword);
        tietpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validatePassword(s.toString(), tietpassword);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tvdaftar = findViewById(R.id.tvdaftar);
        tvdaftar.setOnClickListener(this);
        btnmasuk = findViewById(R.id.btnmasuk);
        btnmasuk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnmasuk){
            checkLogin(tietemail.getText().toString(), tietpassword.getText().toString());
        }else if(v.getId() == R.id.tvdaftar){
            pindahregister = new Intent(this, Register.class);
            startActivity(pindahregister);
        }
    }

    private void checkLogin(String email, String password) {
        if(email.isEmpty() || password.isEmpty()){
            StyleableToast.makeText(this, "Email atau Password Tidak Boleh Kosong", R.style.exampleToast_error).show();

        }else{
            sendLogin();
        }
    }

    private void sendLogin() {
        JSONObject params = new JSONObject();

        try {
            params.put("username", tietemail.getText().toString());
            params.put("password", tietpassword.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String data = params.toString();
        String url = "http://192.168.100.148:6969/api/login/mobile";

        new Thread(new Runnable() {
            @Override
            public void run() {

                Http http = new Http(Login.this,url);
                http.setMethod("POST");
                http.setData(data);
                http.send();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        String msg;
                        Integer code = http.getStatusCode();
                        if (code == 200){
                            try {
                                JSONObject response = new JSONObject(http.getRespone());
                                String token = response.getString("token");

                                localStorage.setToken(token);

                                StyleableToast.makeText(Login.this,"Berhasil Login",R.style.exampleToast_true).show();

                                pindahdashboard = new Intent(Login.this, Dashboard.class);
                                startActivity(pindahdashboard);

                                finish();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else if (code == 422){
                            try {
                                JSONObject response = new JSONObject(http.getRespone());
                                msg = response.getString("message");
                                StyleableToast.makeText(Login.this,msg,R.style.exampleToast_error).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else if(code == 401){
                            StyleableToast.makeText(Login.this,"Email atau Password Salah",R.style.exampleToast_error).show();
                        }else {
                            StyleableToast.makeText(Login.this,"ERROR CODE : " + code,R.style.exampleToast_error).show();
                        }
                    }
                });

            }
        }).start();
    }
}