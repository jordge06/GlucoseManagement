package com.example.acer.glucosemanagement;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    Button btnLogin;
    EditText txtPassword, txtUsername;
    TextView btnForgot, btnRegister;
    String user1, pass1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        btnLogin = findViewById(R.id.btnLogin);
        txtPassword = findViewById(R.id.txtPassword);
        txtUsername = findViewById(R.id.txtUsername);
        btnRegister = findViewById(R.id.btnRegister);
        btnForgot = findViewById(R.id.btnForgot);



        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String user = txtUsername.getText().toString().trim();
//                String pass = txtPassword.getText().toString().trim();
//
//                if (!user.isEmpty() || pass.isEmpty()) {
//                    db_accounts db = new db_accounts(Login.this);
//                    BCrypt bc = new BCrypt();
//
//                    final ArrayList<HashMap<String, String>> userList = db.GetUserByName(user);
//                    for (Map<String, String> entry : userList) {
//                        user1 = entry.get("username");
//                        pass1 = entry.get("password");
//                    }
//
//                    if (!userList.isEmpty()) {
//                        if (!bc.checkpw(pass, pass1)) {
//                            Toast.makeText(Login.this, "Wrong Username or Password", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(Login.this, "Login Successful!", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(Login.this, MainApp.class);
//                            startActivity(intent);
//                        }
//                    } else {
//                        Toast.makeText(Login.this, "Wrong Username or Password", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(Login.this, "Empty Fields!", Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }
}
