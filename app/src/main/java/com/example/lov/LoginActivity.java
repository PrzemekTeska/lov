package com.example.lov;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class LoginActivity extends AppCompatActivity {

    DataBaseHelper dataBaseHelper;
    EditText password, userName;
    Button loginBtn;
    TextView goToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        goToRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
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
        userName =findViewById(R.id.userName);
        password =findViewById(R.id.password);
        dataBaseHelper = new DataBaseHelper(this);
    }

    public void logIn(View view) {
        String user = userName.getText().toString();
        String pass = password.getText().toString();
        if(user.equals("")||pass.equals(""))Toast.makeText(getApplicationContext(),"Fields are empty",Toast.LENGTH_SHORT).show();
        else {
            try {
                Boolean checkUser= dataBaseHelper.checkUserName(user);
                if(!checkUser) {
                    Boolean checkPass = dataBaseHelper.checkPassword(generateHash(pass),user);
                    if(checkPass){
                        Toast.makeText(getApplicationContext(),"LOG IN CORRECT",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));}
                    else{
                        Toast.makeText(getApplicationContext(),"Username or password is incorrect",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"User doesnt exist",Toast.LENGTH_SHORT).show();
                }


            } catch (NoSuchAlgorithmException e) {
                Toast.makeText(getApplicationContext(), "Something went wrong please try again", Toast.LENGTH_LONG).show();
            }
            //  dataBaseHelper.
        }
    }



    private String generateHash(String passwordToHash)throws NoSuchAlgorithmException{
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(salt);
        byte[] bytes = md.digest(passwordToHash.getBytes());
        StringBuilder sb = new StringBuilder();
        for(int i=0; i< bytes.length ;i++)
        {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        String generatedPassword = sb.toString();

        return generatedPassword;
    }
}
