package com.example.vothanhtrung_shop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;
import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnLogin = findViewById(R.id.login_button_admin);
        EditText txtUsername = findViewById(R.id.loginemailAdmin);
        EditText txtPassword = findViewById(R.id.loginPasswordadmin);
        CheckBox remember = findViewById(R.id.checkboxsave);

        TextView btnDangki = findViewById(R.id.signup);

        btnDangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ApiCaller apiCaller = ApiCaller.getInstance(this);

        remember.setChecked(getRememberMe(LoginActivity.this));
        if (remember.isChecked()) {
            txtUsername.setText(getUsername(LoginActivity.this));
            txtPassword.setText(getPassword(LoginActivity.this));
        }
        if (getUsername(LoginActivity.this).isEmpty()) {
            remember.setChecked(false);
            txtUsername.setText("");
            txtPassword.setText("");
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                apiCaller.makeStringRequest(apiCaller.url + "/users/name/" + txtUsername.getText().toString(), new ApiCaller.ApiResponseListener<String>() {
                    @Override
                    public void onSuccess(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            User.setId(jsonObject.getInt("id"));
                            if (BCrypt.checkpw(txtPassword.getText().toString(), jsonObject.getString("pass"))) {
                                Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                if (remember.isChecked()) {
                                    setUser(LoginActivity.this, txtUsername.getText().toString().trim(),
                                            txtPassword.getText().toString().trim(), remember.isChecked());
                                } else {
                                    setUser(LoginActivity.this, "", "", remember.isChecked());
                                }


                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("userObject", response);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Sai mật khẩu", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(String errorMessage) {
                        Toast.makeText(getApplicationContext(), "Người dùng không tồn tại!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private void setUser(LoginActivity loginActivity, String s, String s1, boolean checked) {
        SharedPreferences sharedPreferences = loginActivity.getSharedPreferences("my_shared_pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", s);
        editor.putString("password", s1);
        editor.putBoolean("remember_me", checked);
        editor.apply();
    }

    private boolean getRememberMe(LoginActivity loginActivity) {
        SharedPreferences sharedPreferences = loginActivity.getSharedPreferences("my_shared_pref", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("remember_me", false);
    }

    private String getPassword(LoginActivity loginActivity) {
        SharedPreferences sharedPreferences = loginActivity.getSharedPreferences("my_shared_pref", Context.MODE_PRIVATE);
        return sharedPreferences.getString("password", "");
    }

    private String getUsername(LoginActivity loginActivity) {
        SharedPreferences sharedPreferences = loginActivity.getSharedPreferences("my_shared_pref", Context.MODE_PRIVATE);
        return sharedPreferences.getString("username", "");
    }
}