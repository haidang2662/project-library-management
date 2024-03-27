package entity;

import constant.UserRole;

public class User {

    private static int autoId;

    private int id ;
    private String email;
    private String password;
    private String fullName;
    private String phone;
    private String address;
    private UserRole role; // quyền của user: ADMIN/USER

    public User() {
        this.id = autoId++;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", passWord='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", phone=" + phone +
                ", address='" + address + '\'' +
                ", role=" + role +
                '}';
    }
}
