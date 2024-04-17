package com.example.vothanhtrung_shop;

public class UserAdmin {
    private String UserAdminId;
    private String UserAdminName;
    private String email;
    private int roles;

    public UserAdmin() {
        // Default constructor required for calls to DataSnapshot.getValue(UserAdmin.class)
    }

    public UserAdmin(String UserAdminId, String UserAdminName, String email, int roles) {
        this.UserAdminId = UserAdminId;
        this.UserAdminName = UserAdminName;
        this.email = email;
        this.roles = roles;
    }

    // Getter và Setter cho các thuộc tính
    public String getUserAdminId() {
        return UserAdminId;
    }

    public void setUserAdminId(String UserAdminId) {
        this.UserAdminId = UserAdminId;
    }

    public String getUserAdminName() {
        return UserAdminName;
    }

    public void setUserAdminName(String UserAdminName) {
        this.UserAdminName = UserAdminName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRoles() {
        return roles;
    }

    public void setRoles(int roles) {
        this.roles = roles;
    }
}
