package service;

import constant.UserRole;
import entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserService { // chuyên quản lý đăng ký, đăng nhập, đăng xuất, quên mật khẩu, ...

    private List<User> users = new ArrayList<>();


    public void register() {
        User user = new User();
        String email;
        while (true) {
            System.out.println("Mời bạn nhập email : ");
            email = new Scanner(System.in).nextLine();
            if (!email.matches("")) {
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

        user.setEmail(email);
        System.out.println("Mới bạn nhập password");
        user.setPassword(new Scanner(System.in).nextLine()); // check passord 8 -> 16 ký tự cả hoa , cả thường , cả số
        System.out.println("Mời bạn nhập tên : ");
        user.setFullName(new Scanner(System.in).nextLine());
        System.out.println("Mời bạn nhập SĐT : ");
        user.setPhone(new Scanner(System.in).nextLine()); // check đầu 0 và có 9 so tiep theo
        System.out.println("Mời bạn nhập địa chỉ : ");
        user.setAddress(new Scanner(System.in).nextLine());
        user.setRole(UserRole.USER);
        users.add(user);
        // bắt user nhập email, pass, họ tên, ....
        // kiểm tra xem đã có tài khoản nào trùng email với email vùa nhập không
        // nếu trùng thì thông báo là đã có email đó tồn tại rồi, vui lòng email khác
        // nếu không thì tạo 1 object User
        // add object đso vào List<User>
        // ghi dữ liệu vào file (để sau)

    }


    public User login() {
        int count = 0;
        while (count <= 5) {
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
            System.out.println("Thông tin đăng nhập chưa chĩnh xác , vui lòng thử lại");
        }

        // xử lý cho user nhập email và pass,
        // check trong List<User> xem có user nào có email và pass trùng với cái mà người dùng nhập không
        // nếu trùng thì return ra User tìm được
        // nếu không trùng bắt nhập lại
        // nhập lại tối đa 5 lần thì dừng và trả ra null
        return null;
    }

    public User findUserById(int idUser) {
        for (int j = 0; j < users.size(); j++) {
            if (users.get(j).getId() == idUser) {
                return users.get(j);
            }
        }
        return null;
    }

    public void deleteUserById() {
        while (true) {
            System.out.println("Mời bạn nhập ID của User muốn xóa ");
            int idUserDelete = new Scanner(System.in).nextInt();
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getId() == idUserDelete) {
                    users.remove(users.get(i));
                    System.out.println("User có ID trên đã được xóa ");
                    return;
                } else
                    System.out.println("Thông tin ID không chính xác , vui lòng nhập lại ");
                break;
            }
        }
    }
    public void updateUserInformation(){
        System.out.println("Mời bạn nhập ID của user muốn cập nhật");
        int idUser = new Scanner(System.in).nextInt();
        User user = findUserById(idUser);
        if (user == null) {
            System.out.println("Thông tin không chính xác , vui lòng nhập lại : ");// chưa chắc chắn làm vòng lặp ở đoạn này . Vứt dòng 167 ra ngoài còn đâu để hết vào while true hoặc vứt hết vào while true luôn
        }
        if (user != null) {
            System.out.println("Mời bạn chọn phần thông tin muốn chỉnh sửa : ");
            int choice1 = new Scanner(System.in).nextInt();
            System.out.println("1 : Email");
            System.out.println("2 : Password");
            System.out.println("3 : Tên");
            System.out.println("4 : Số điện thoại");
            System.out.println("5 : địa chỉ");
            switch (choice1) {
                case 1:
                    System.out.println("Mời bạn nhập email mới : ");
                    String newEmail = new Scanner(System.in).nextLine();
                    user.setEmail(newEmail);
                    break;
                case 2:
                    System.out.println("Mời bạn nhập password mới : ");
                    String newPassword = new Scanner(System.in).nextLine();
                    user.setPassword(newPassword);
                    break;
                case 3:
                    System.out.println("Mời bạn nhập tên mới : ");
                    String newName = new Scanner(System.in).nextLine();
                    user.setFullName(newName);
                    break;
                case 4:
                    System.out.println("Mời bạn nhập SĐT mới : ");
                    String newPhone = new Scanner(System.in).nextLine();
                    user.setPhone(newPhone);
                    break;
                case 5:
                    System.out.println("Mời bạn nhập địa chỉ mới : ");
                    String newAdress = new Scanner(System.in).nextLine();
                    user.setAddress(newAdress);
                    break;
            }
        }
    }

}
