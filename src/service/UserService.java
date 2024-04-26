package service;

import constant.Regex;
import constant.UserRole;
import entity.User;
import main.Main;
import util.FileUtil;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class UserService {

    private final FileUtil<User> fileUtil = new FileUtil<>(); // FILE - thêm thộc tính này, với UserService thì sẽ là FileUtil<User>, voi Book sẽ là FileUtil<Book>
    private static final String USER_DATA_FILE = "users.json";
    private static final String ADMIN_EMAIL = "admin@gmail.com";
    private static final String ADMIN_PASSWORD = "admin";
    private static int AUTO_ID; // FILE - thêm auto id ở đây, bỏ auto id ở entity đi
    private List<User> users;

    public void setUsers() {
        List<User> userList = fileUtil.readDataFromFile(USER_DATA_FILE, User[].class);
        users = userList != null ? userList : new ArrayList<>();
    }

    // FILE - lưu dữ liệu vào file, hàm nãy sẽ được gọi mỗi khi co sự thay đổi về user, ví dụ xóa user, thêm user, cập nhật user
    public void saveUserData() {
        fileUtil.writeDataToFile(users, USER_DATA_FILE);
    }

    // FILE - hàm tạo user admin
    public void createDefaultAdminUser() {
        if (users == null || users.isEmpty()) {
            createAdmin();
            return;
        }
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(ADMIN_EMAIL)
                    && user.getPassword().equalsIgnoreCase(ADMIN_PASSWORD)) {
                return;
            }
        }
        createAdmin();
    }

    private void createAdmin() {
        User user = new User(ADMIN_EMAIL, ADMIN_PASSWORD, UserRole.ADMIN);
        user.setId(0);
        users.add(user);
        saveUserData();
    }

    // FILE - tìm auto id
    public void findCurrentAutoId() {
        int maxId = -1;
        for (User user : users) {
            if (user.getId() > maxId) {
                maxId = user.getId();
            }
        }
        AUTO_ID = maxId + 1;
    }

    public void register() {
        User user = createUserCommonInfo();
        user.setRole(UserRole.USER);
        users.add(user);
        showUser(user);
        saveUserData();
    }

    public void createUserForAdmin() {
        User user = createUserCommonInfo();
        System.out.println("Chọn quyền của user:");
        System.out.println("1. User");
        System.out.println("2. Admin");
        int choice;
        while (true) {
            try {
                choice = new Scanner(System.in).nextInt();
                if (choice != 1 && choice != 2) {
                    System.out.print("Lựa chọn là 1 hoặc 2, vui lòng chọn lại: ");
                    continue;
                }
                break;
            } catch (InputMismatchException ex) {
                System.out.print("Lựa chọn là số nguyên, vui lòng chọn lại: ");
            }
        }
        user.setRole(choice == 1 ? UserRole.USER : UserRole.ADMIN);
        users.add(user);
        showUser(user);
        saveUserData(); // FILE - khi có thay đổi về list user, can luu vao file
    }


    public void showUser(User user) {
        printHeader();
        showUserDetail(user);
    }

    public void showUsers(List<User> users1) {
        printHeader();
        for (User user : users1) {
            showUserDetail(user);
        }
    }

    public void printHeader() {
        System.out.printf("%-5s%-30s%-30s%-20s%-20s%-10s%-10s%n", "Id", "Name", "Email", "Phone", "Address", "Role", "Balance");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------");
    }

    public void showUserDetail(User user) {
        System.out.printf("%-5s%-30s%-30s%-20s%-20s%-10s%-10s%n", user.getId(), user.getFullName(), user.getEmail(), user.getPhone(), user.getAddress(), user.getRole(), user.getBalance());
    }

    public User createUserCommonInfo() {
        String email;
        String password;
        String phone;
        while (true) {
            System.out.println("Mời bạn nhập email : ");
            email = new Scanner(System.in).nextLine();
            if (!email.matches(Regex.EMAIL_REGEX)) {
                System.out.println("Email không đúng định dạng vui lòng nhập lại");
                continue;
            }
            boolean coTrungEmailKhong = false;
            for (User user1 : users) {
                if (email.equalsIgnoreCase(user1.getEmail())) {
                    System.out.println("Email đã tồn tại vui lòng nhập lại");
                    coTrungEmailKhong = true;
                    break;
                }
            }
            if (coTrungEmailKhong == false) {
                break;
            }
        }
        while (true) {
            System.out.println("Mới bạn nhập password (8 -> 16 ký tự cả chữ thường, chữ hoa và cả số)");
            password = new Scanner(System.in).nextLine();
            if (!password.matches(Regex.PASSWORD_REGEX)) {
                System.out.println("Password không đúng định dạng vui lòng nhập lại ");
                continue;
            }
            break;
        }
        String name;
        while (true) {
            System.out.print("Mời bạn nhập tên: ");
            name = new Scanner(System.in).nextLine();
            if (!name.matches(".*\\d.*") && !name.isEmpty()) { // Kiểm tra nếu tên không chứa ký tự số và không rỗng
                break;
            } else {
                System.out.println("Tên không hợp lệ. Vui lòng nhập lại.");
            }
        }

        while (true) {
            System.out.println("Mời bạn nhập SĐT (đầu 0 và có 9 so tiep theo): ");
            phone = new Scanner(System.in).nextLine();
            if (!phone.matches(Regex.VN_PHONE_REGEX)) {
                System.out.println("Số điện thoại không đúng định dạng , vui lòng nhập lại ");
                continue;
            }
            break;
        }
        System.out.println("Mời bạn nhập địa chỉ : ");
        String address = new Scanner(System.in).nextLine();
        return new User(AUTO_ID++, email, password, name, phone, address);
    }


    public User login() {
        int count = 0;
        while (count < 5) {
            count++;
            String email;
            while (true) {
                System.out.print("Mời bạn nhập email: ");
                email = new Scanner(System.in).nextLine();
                if (!email.matches(Regex.EMAIL_REGEX)) {
                    System.out.println("Email không đúng định dạng vui lòng nhập lại");
                    continue;
                }
                break;
            }
            System.out.print("Mời bạn nhập mật khẩu : ");
            String password = new Scanner(System.in).nextLine();
            for (User user : users) {
                if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equals(password)) {
                    return user;
                }
            }
            System.out.println("Thông tin đăng nhập chưa chính xác. Đăng nhập thất bại " + (count) + " lần, vui lòng thử lại.");
        }
        return null;
    }

    public User findUserById(int idUser) {
        for (User user : users) {
            if (user.getId() == idUser) {
                return user;
            }
        }
        return null;
    }

    public void deleteUserById(int idUserDelete) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == idUserDelete) {
                users.remove(i);
                System.out.println("User có ID trên đã được xóa");
                saveUserData();// FILE - khi có thay đổi về list user, can luu vao file
                return;
            }
        }
        System.out.println("Không tồn tại user với id vừa nhập!");
    }

    public void updateUserInformation(int idUserUpdate) {

        User user = findUserById(idUserUpdate);
        System.out.println("Mời bạn chọn phần thông tin muốn chỉnh sửa: ");
        System.out.println("1. Email");
        System.out.println("2. Password");
        System.out.println("3. Tên");
        System.out.println("4. Số điện thoại");
        System.out.println("5. Địa chỉ");
        System.out.println("6. Thoát");
        int featureChoice;
        while (true) {
            try {
                featureChoice = new Scanner(System.in).nextInt();
                if (featureChoice < 1 || featureChoice > 6) {
                    System.out.println("Chức năng là số từ 1 tới 6, vui lòng nhập lại: ");
                    continue;
                }
                break;
            } catch (InputMismatchException ex) {
                System.out.print("Lựa chọn phải là một số nguyên, vui lòng nhập lại: ");
            }
        }
        switch (featureChoice) {
            case 1:
                String newEmail;
                while (true) {
                    System.out.println("Mời bạn nhập email mới: ");
                    newEmail = new Scanner(System.in).nextLine();
                    if (!newEmail.matches(Regex.EMAIL_REGEX)) {
                        System.out.println("Email không đúng định dạng vui lòng nhập lại");
                        continue;
                    }
                    boolean coTrungEmailKhong = false;
                    for (User user1 : users) {
                        if (newEmail.equalsIgnoreCase(user1.getEmail()) && user1.getId() != user.getId()) {
                            System.out.println("Email đã tồn tại vui lòng nhập lại");
                            coTrungEmailKhong = true;
                            break;
                        }
                    }
                    if (coTrungEmailKhong == false) {
                        break;
                    }
                }
                user.setEmail(newEmail);
                break;
            case 2:
                String newPassword;
                while (true) {
                    System.out.println("Mới bạn nhập password (8 -> 16 ký tự cả chữ thường, chữ hoa và cả số)");
                    newPassword = new Scanner(System.in).nextLine();
                    if (!newPassword.matches(Regex.PASSWORD_REGEX)) {
                        System.out.println("Password không đúng định dạng vui lòng nhập lại ");
                        continue;
                    }
                    break;
                }
                user.setPassword(newPassword);
                break;
            case 3:
                System.out.println("Mời bạn nhập tên mới: ");
                String newName = new Scanner(System.in).nextLine();
                user.setFullName(newName);
                break;
            case 4:
                String newPhone;
                while (true) {
                    System.out.println("Mời bạn nhập SĐT (đầu 0 và có 9 so tiep theo): ");
                    newPhone = new Scanner(System.in).nextLine();
                    if (!newPhone.matches(Regex.VN_PHONE_REGEX)) {
                        System.out.println("Số điện thoại không đúng định dạng , vui lòng nhập lại ");
                        continue;
                    }
                    break;
                }
                user.setPhone(newPhone);
                break;
            case 5:
                System.out.println("Mời bạn nhập địa chỉ mới : ");
                String newAddress = new Scanner(System.in).nextLine();
                user.setAddress(newAddress);
                break;
            case 6:
                return;
        }
        showUser(user);
        saveUserData();// FILE - khi có thay đổi về list user, can luu vao file
    }


    public void showBalance() {
        User user = getLoggedInUser();
        System.out.println("Số dư tài khoản của bạn đọc " + user.getBalance());
    }

    public void updateUserBalance(int idUser, double amount) {
        for (User user : users) {
            if (user.getId() == idUser) {
                user.setBalance(user.getBalance() + amount);
                System.out.println("Số dư tài khoản của bạn đọc " + user.getBalance());
                saveUserData();// FILE - khi có thay đổi về list user, can luu vao file
                return;
            }
        }
    }

    public User getLoggedInUser() {
        for (User userTemp : users) {
            if (userTemp.getId() == Main.loggedInUser.getId()) {
                return userTemp;
            }
        }
        return null;
    }

    public void findUserByName() {
        System.out.println("Mời bạn nhập tên của User : ");
        String name = new Scanner(System.in).nextLine();
        List<User> users1 = new ArrayList<>();

        for (User user : users) {
            if (user.getFullName() != null && user.getFullName().toLowerCase().contains(name.toLowerCase())) {
                users1.add(user);
            }
        }
        showUsers(users1);
    }

}
