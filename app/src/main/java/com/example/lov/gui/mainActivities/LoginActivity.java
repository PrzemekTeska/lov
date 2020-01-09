package com.example.lov.gui.mainActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lov.DB.DataBaseHandler;
import com.example.lov.R;
import com.example.lov.service.DateStringConverter;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class LoginActivity extends AppCompatActivity {

    private DateStringConverter dateStringConverter=new DateStringConverter();
    DataBaseHandler dataBaseHandler;
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

        loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                logIn(view);
            }
        });

    }

    public void init() {
        loginBtn = findViewById(R.id.loginBtn);
        goToRegister = findViewById(R.id.textView6);
        userName = findViewById(R.id.userName);
        password = findViewById(R.id.password);
        dataBaseHandler = new DataBaseHandler(this);
    }

    public void logIn(View view) {
        String user = userName.getText().toString();
        String pass = password.getText().toString();

        /*  Date date1=null;
         Date date2=null;
          try {
          date1= dateStringConverter.getDate("07-01-2000");
           date2= dateStringConverter.getDate("03-05-2001");
           String str= dateStringConverter.getString(date1);
          }catch (Exception e){}    */

        if (user.equals("") || pass.equals(""))
            Toast.makeText(getApplicationContext(), "Fields are empty", Toast.LENGTH_SHORT).show();
        else {
            try {
                Boolean checkUser = dataBaseHandler.checkUserName(user);
                if (!checkUser) {
                    Boolean checkPass = dataBaseHandler.checkPassword(SHA1(pass), user);
                    if (checkPass) {
                        Toast.makeText(getApplicationContext(), "LOG IN CORRECT", Toast.LENGTH_SHORT).show();

                   /*     dataBaseHandler.insertGoalIntoDataBase("goal123",date1,date2);
                        dataBaseHandler.insertActivityIntoDataBase("sss","dziala",5,"goal123");
                        dataBaseHandler.insertActivityIntoDataBase("fasfac","dziala",5,"goal123");
                        dataBaseHandler.getAllActivities();     */

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
