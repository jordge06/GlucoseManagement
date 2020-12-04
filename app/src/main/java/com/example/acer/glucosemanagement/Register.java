package com.example.acer.glucosemanagement;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import org.mindrot.jbcrypt.BCrypt;

public class Register extends AppCompatActivity {

    Button btnReg;
    EditText txtUser, txtPass, txtEmail;
    TextView btnSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnReg = findViewById(R.id.btnReg);
        btnSign = findViewById(R.id.btnSign);
        txtUser = findViewById(R.id.txtUser);
        txtPass = findViewById(R.id.txtPass);
        txtEmail = findViewById(R.id.txtEmail);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                String username = txtUser.getText().toString().trim();
//                String password = txtPass.getText().toString().trim();
//                String email = txtEmail.getText().toString().trim();
//
//                if (!(username.isEmpty() || password.isEmpty() || email.isEmpty())) {
//                    db_accounts db = new db_accounts(Register.this);
//                    BCrypt bc = new BCrypt();
//
//                    if (db.checkUser(username)) {
//                        String hashedPassword = bc.hashpw(password, bc.gensalt());
//
//                        db.insertUserDetails(username, hashedPassword, email);
//
//                        Toast.makeText(Register.this, "Register Successfully!", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(Register.this, "Username Already Taken!", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(Register.this, "Empty Fields!", Toast.LENGTH_SHORT).show();
//                }
            }
        });

        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });
    }
}
