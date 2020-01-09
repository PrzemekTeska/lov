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

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    DataBaseHandler dataBaseHandler;
    EditText password, passwordRep, userName, email, emailRep;
    TextView goToLogin;
    Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();

        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register(view);
            }
        });
    }

    public void init() {
        registerBtn = findViewById(R.id.registerBtn);
        userName = findViewById(R.id.userName);
        email = findViewById(R.id.email);
        emailRep = findViewById(R.id.repEmail);
        password = findViewById(R.id.password);
        passwordRep = findViewById(R.id.repPassword);
        goToLogin = findViewById(R.id.textView6);
        dataBaseHandler = new DataBaseHandler(this);
    }

    public void register(View view) {
        String user = userName.getText().toString();
        String pass = password.getText().toString();
        String passRep = passwordRep.getText().toString();
        String mail = email.getText().toString();
        String mailRep = emailRep.getText().toString();

        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(mail);
        if (user.equals("") || pass.equals("") || passRep.equals("") || mail.equals("") || mailRep.equals(""))
            Toast.makeText(getApplicationContext(), "Fields are empty", Toast.LENGTH_SHORT).show();
        else if (user.length() < 6)
            Toast.makeText(getApplicationContext(), "Username is too short", Toast.LENGTH_SHORT).show();
        else if (!matcher.matches())
            Toast.makeText(getApplicationContext(), "Its not a real email", Toast.LENGTH_SHORT).show();
        else if (pass.length() < 6)
            Toast.makeText(getApplicationContext(), "Password is too short", Toast.LENGTH_SHORT).show();
        else if (pass.length() > 15)
            Toast.makeText(getApplicationContext(), "Password is too long", Toast.LENGTH_SHORT).show();
        else if (user.equals(pass))
            Toast.makeText(getApplicationContext(), "Username and password cant be the same", Toast.LENGTH_SHORT).show();
        else if (pass.equals(passRep) && mail.equals(mailRep)) {
            try {
                Boolean checkUser = dataBaseHandler.checkUserName(user);
                if (checkUser) {
                    Boolean checkEmail = dataBaseHandler.checkEmail(mail);
                    if (checkEmail) {
                        Boolean insert = dataBaseHandler.insertUserIntoDataBase(user, mail, SHA1(pass));
                        if (!insert)
                            Toast.makeText(getApplicationContext(), "Something went wrong please try again", Toast.LENGTH_LONG).show();
                        else {
                            Toast.makeText(getApplicationContext(), "Account created you can login now", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        }
                    } else
                        Toast.makeText(getApplicationContext(), "This email Already exists", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getApplicationContext(), "This username Already exists", Toast.LENGTH_SHORT).show();
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
