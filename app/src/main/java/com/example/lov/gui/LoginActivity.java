package com.example.lov.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lov.DB.DataBaseCreater;
import com.example.lov.R;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {

    DataBaseCreater dataBaseCreater;
    EditText password, userName;
    Button loginBtn;
    TextView goToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        goToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logIn(view);
            }
        });

    }

    public void init() {
        loginBtn = findViewById(R.id.loginBtn);
        goToRegister = findViewById(R.id.textView6);
        userName = findViewById(R.id.userName);
        password = findViewById(R.id.password);
        dataBaseCreater = new DataBaseCreater(this);
    }

    public void logIn(View view) {
        String user = userName.getText().toString();
        String pass = password.getText().toString();
        if (user.equals("") || pass.equals(""))
            Toast.makeText(getApplicationContext(), "Fields are empty", Toast.LENGTH_SHORT).show();
        else {
            try {
                Boolean checkUser = dataBaseCreater.checkUserName(user);
                if (!checkUser) {
                    Boolean checkPass = dataBaseCreater.checkPassword(SHA1(pass), user);
                    if (checkPass) {
                        dataBaseCreater.insertGoalIntoDataBase("aa","aaa");
                        dataBaseCreater.insertActivityIntoDataBase("aa","aaa");
                        Toast.makeText(getApplicationContext(), "LOG IN CORRECT", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Username or password is incorrect", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "User doesnt exist", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Something went wrong please try again", Toast.LENGTH_LONG).show();
            }
        }
    }

    public static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        byte[] sha1hash = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : sha1hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
