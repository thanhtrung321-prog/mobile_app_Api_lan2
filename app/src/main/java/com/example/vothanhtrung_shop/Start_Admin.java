package com.example.vothanhtrung_shop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Start_Admin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_admin);
        Button ButttonloginAdmin=findViewById(R.id.ButttonloginAdmin);
        ButttonloginAdmin.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View v) {
                                                     Intent intent = new Intent(Start_Admin.this,LoginAdminActivity.class);
                                                    startActivity(intent);
                                                 }
                                             }
        );
    }
}