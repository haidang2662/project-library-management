package main;

import entity.User;
import view.Menu;

public class Main {

    public static User loggedInUser;

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.initializeData();
        menu.showMenu();
    }

}