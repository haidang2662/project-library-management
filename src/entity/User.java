package entity;

import constant.UserRole;

public class User {

    private int id;
    private String email;
    private String password;
    private String fullName;
    private String phone;
    private String address;
    private UserRole role; // quyền của user: ADMIN/USER

    private double balance; // số dư tài khoản

    public User() {
    }

    public User(int id, String email, String password, String fullName, String phone, String address) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
    }

    public User(String email, String password, UserRole role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(int id, String email, String password, String fullName, String phone, String address, UserRole role, double balance) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
        this.role = role;
        this.balance = balance;
    }

    public User(int id, String email, String password, String fullName, String phone, String address, String role, double balance) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
        this.role = UserRole.valueOf(role);
        this.balance = balance;
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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
