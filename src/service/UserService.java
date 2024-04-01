package service;

import constant.Regex;
import constant.UserRole;
import entity.User;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class UserService { // chuyên quản lý đăng ký, đăng nhập, đăng xuất, quên mật khẩu, ...

    private final List<User> users = Arrays.asList(
            new User(-1, "admin", "admin", UserRole.ADMIN),
            new User(0, "admin", "admin", UserRole.ADMIN),
            new User(1, "admin", "admin", UserRole.ADMIN),
            new User(2, "admin", "admin", UserRole.ADMIN),
            new User(3, "admin", "admin", UserRole.ADMIN)
    );

    public void register() {
        User user = createUserCommonInfo();
        user.setRole(UserRole.USER);
        users.add(user);
        System.out.println(users);
    }

    public void createUserForAdmin() {
        User user = createUserCommonInfo();
        System.out.println("Chọn quyền của user:");
        System.out.println("1. Admin");
        System.out.println("2. User");
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
        if (choice == 1) {
            user.setRole(UserRole.USER);
        } else {
            user.setRole(UserRole.ADMIN);
        }
        users.add(user);
        System.out.println(users);
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
            System.out.println("Mới bạn nhập password (8 -> 16 ký tự cả hoa , cả thường , cả số)");
            password = new Scanner(System.in).nextLine();
            if (!password.matches(Regex.PASSWORD_REGEX)) {
                System.out.println("Password không đúng định dạng vui lòng nhập lại ");
                continue;
            }
            break;
        }
        System.out.println("Mời bạn nhập tên : ");
        String name = new Scanner(System.in).nextLine();
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
        return new User(email, password, name, phone, address);
    }


    public User login() {
        int count = 0;
        while (count < 5) {
            count++;
            System.out.println("Mời bạn nhập email : ");
            String email = new Scanner(System.in).nextLine();
            System.out.println("Mời bạn nhập mật khẩu : ");
            String password = new Scanner(System.in).nextLine();
            for (User user : users) {
                if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equals(password)) {
                    return user;
                }
            }
            System.out.println("Thông tin đăng nhập chưa chính xác. Đăng nhập thất bại " + (count + 1) + "lần, vui lòng thử lại.");
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
                users.remove(users.get(i));
                System.out.println("User có ID trên đã được xóa");
                return;
            }
        }
        System.out.println("Không tồn tại user với id vừa nhập!");
    }

    public void updateUserInformation() {
        User user;
        System.out.print("Mời bạn nhập ID của user muốn cập nhật");
        while (true) {
            int idUser = new Scanner(System.in).nextInt();
            user = findUserById(idUser);
            if (user == null) {
                System.out.print("Thông tin không chính xác , vui lòng nhập lại : ");
                continue;
            }
            break;
        }
        System.out.println("Mời bạn chọn phần thông tin muốn chỉnh sửa: ");
        System.out.println("1. Email");
        System.out.println("2. Password");
        System.out.println("3. Tên");
        System.out.println("4. Số điện thoại");
        System.out.println("5. Địa chỉ");
        int choice1 = new Scanner(System.in).nextInt();
        switch (choice1) {
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
                    System.out.println("Mới bạn nhập password (8 -> 16 ký tự cả hoa , cả thường , cả số)");
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
                System.out.println("Mời bạn nhập tên mới : ");
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
                String newAdress = new Scanner(System.in).nextLine();
                user.setAddress(newAdress);
                break;
        }
        System.out.println(user);

    }

}
