package com.android.ellocker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.ellocker.channel.Http;
import com.android.ellocker.helper.Validotor;
import com.android.ellocker.user.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import io.github.muddz.styleabletoast.StyleableToast;


public class Register extends AppCompatActivity implements View.OnClickListener, Validotor {

    TextView tvlogin;
    Intent pindahlogin;

    Button btndaftar;

    TextInputEditText tietnamadepan, tietnamabelakang, tietnomorponsel, tietemail, tietpassword, tietverifpassword;
    TextInputLayout tilnamadepan, tilnamabelakang, tilnomorponsel, tilemail, tilpassword, tilverifpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tvlogin = findViewById(R.id.tvlogin);
        tvlogin.setOnClickListener(this);

        tietnamadepan = findViewById(R.id.tietnamadepan);
        tietnamabelakang = findViewById(R.id.tietnamabelakang);

        tietnomorponsel = findViewById(R.id.tietnomorponsel);
        tietnomorponsel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateUserPhone(s.toString(), tietnomorponsel);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tilnomorponsel = findViewById(R.id.tilnomorponsel);

        tietemail = findViewById(R.id.tietemail);
        tietemail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateEmail(s.toString(), tietemail);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tilemail = findViewById(R.id.tilemail);

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
        tilpassword = findViewById(R.id.tilpassword);


        tietverifpassword = findViewById(R.id.tietverifpassword);
        tilverifpass = findViewById(R.id.tilverifpassword);

        btndaftar = findViewById(R.id.btndaftar);
        btndaftar.setOnClickListener(this);
    }

    private void checkRegister(User user) {
        if (user.getFrontName().isEmpty() || user.getLastName().isEmpty() || user.getNomorPonsel().isEmpty()
                || user.getEmail().isEmpty() || tietpassword.getText().toString().isEmpty() || tietverifpassword.getText().toString().isEmpty()){
            StyleableToast.makeText(this, "Semua Nilai Harus di isi", R.style.exampleToast_allert).show();
        }else {
            if (!tietpassword.getText().toString().equals(tietverifpassword.getText().toString())){
                StyleableToast.makeText(this, "Verifikasi Password Tidak Cocok", R.style.exampleToast_error).show();
            }else{
                StyleableToast.makeText(this, "Bersiap Mengirim", R.style.exampleToast_allert).show();
                sendRegister(user);
            }
        }
    }

    private void sendRegister(User user) {
        JSONObject params = new JSONObject();
        try {
            params.put("namadepan", user.getFrontName());
            params.put("namabelakang", user.getLastName());
            params.put("email", user.getEmail());
            params.put("password", tietpassword.getText().toString());
            params.put("nomorponsel", user.getNomorPonsel());
        }catch (JSONException e){
            e.printStackTrace();

        }


        String data = params.toString();
        String url = Http.API + "register/mobile";

        new Thread(new Runnable() {
            @Override
            public void run() {

                Http http = new Http(Register.this,url);
                http.setMethod("POST");
                http.setData(data);
                http.send();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Integer code = http.getStatusCode();
                        if(code == 201 || code == 200){
                            try {
                                JSONObject response = new JSONObject(http.getRespone());

                                StyleableToast.makeText(Register.this, "Register Berhasil", R.style.exampleToast_true).show();
                                pindahlogin = new Intent(Register.this, Login.class);
                                startActivity(pindahlogin);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }

                        }else if( code >= 400){
                            try {
                                JSONObject response = new JSONObject(http.getRespone());
                                String msg = response.getString("message");
                                StyleableToast.makeText(Register.this, msg, R.style.exampleToast_error).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else{
                            StyleableToast.makeText(Register.this, "ERROR : " + code, R.style.exampleToast_error).show();
                        }
                    }
                });

            }
        }).start();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btndaftar){
            User user = new User("-1",tietnamadepan.getText().toString(),
                    tietnamabelakang.getText().toString(),
                    tietemail.getText().toString(),
                    tietnomorponsel.getText().toString(),
                    -1,-1);

            checkRegister(user);
        }else if(view.getId() == R.id.tvlogin){
            pindahlogin = new Intent(this, Login.class);
            startActivity(pindahlogin);
        }
    }
}