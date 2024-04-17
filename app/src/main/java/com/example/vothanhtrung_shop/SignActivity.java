package com.example.vothanhtrung_shop;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SignActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText password_reEditText;
    private EditText userNameEditText;
    private Button registerButton;
    private ApiCaller apiCaller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        emailEditText = findViewById(R.id.editemail);
        passwordEditText = findViewById(R.id.password);
        password_reEditText = findViewById(R.id.password_re);
        userNameEditText = findViewById(R.id.username);

        registerButton = findViewById(R.id.register_button);
        apiCaller = ApiCaller.getInstance(this);


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String userName = userNameEditText.getText().toString();
                String password_re=password_reEditText.getText().toString();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(userName)) {
                    showToast("Vui lòng nhập đầy đủ thông tin");
                    return;
                }

                if (!isValidPassword(password)) {
                    showToast("Mật khẩu phải chứa ít nhất 6 ký tự và bao gồm cả chữ hoa, chữ thường, số và ký tự đặc biệt");
                    return;
                }
                if (!password.equals(password_re)) {
                    showToast("Mật khẩu nhập lại không khớp");
                    return;
                }

                User newUser = new User(userName, password, email, "null", "null");

                apiCaller.addUser(newUser, new ApiCaller.ApiResponseListener<User>() {
                    @Override
                    public void onSuccess(User response) {
                        showToast("Đăng ký thành công");
                        Log.d("avav", "Đăng kí người dùng thành công. Username: " + response.getUsername());
                        // Chuyển tới MainActivity khi đăng ký thành công
                        Intent intent = new Intent(SignActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish(); // Đóng Activity hiện tại
                    }

                    @Override
                    public void onError(String errorMessage) {
                        showToast("Đăng ký thất bại: " + errorMessage);
                        Log.d("avav", "Đăng kí người dùng không thành công " + errorMessage);
                    }
                });
            }
        });

    }

    private boolean isValidPassword(String password) {
        String passwordPattern = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{6,}";
        return password.matches(passwordPattern);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}