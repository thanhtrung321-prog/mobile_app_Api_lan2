package com.example.vothanhtrung_shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vothanhtrung_shop.HomeAdminActivity;
import com.example.vothanhtrung_shop.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginAdminActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");

        EditText EmailLoginAdmin = findViewById(R.id.loginemailAdmin);
        EditText Passwordadmin = findViewById(R.id.loginPasswordadmin);
        Button loginAdmin = findViewById(R.id.login_button_admin);

        loginAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = EmailLoginAdmin.getText().toString().trim();
                String password = Passwordadmin.getText().toString().trim();

                // Đăng nhập người dùng với email và mật khẩu
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginAdminActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    if (user != null) {
                                        // Lấy UID của người dùng đăng nhập thành công
                                        String userId = user.getUid();
                                        // Kiểm tra trong cơ sở dữ liệu Firebase xem người dùng có vai trò admin hay không
                                        mDatabase.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if (dataSnapshot.exists()) {
                                                    UserAdmin currentUser = dataSnapshot.getValue(UserAdmin.class);
                                                    if (currentUser != null && currentUser.getRoles() == 2) {
                                                        // Người dùng có vai trò admin, chuyển đến trang HomeAdminActivity
                                                        Intent intent = new Intent(LoginAdminActivity.this, HomeAdminActivity.class);
                                                        startActivity(intent);
                                                        finish(); // Kết thúc activity hiện tại
                                                    } else {
                                                        // Người dùng không có vai trò admin, thông báo lỗi
                                                        Toast.makeText(LoginAdminActivity.this, "Người dùng không phải là Admin", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                // Xử lý lỗi nếu có
                                            }
                                        });
                                    }
                                } else {
                                    // Đăng nhập thất bại, hiển thị thông báo lỗi
                                    Toast.makeText(LoginAdminActivity.this, "Sai tài khoản mật khẩu", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
