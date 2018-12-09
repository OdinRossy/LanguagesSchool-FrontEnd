package com.company.view;

import com.company.model.abstraction.User;

public class CurrentUser {
    private static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        CurrentUser.user = user;
        System.out.println("Current user: " + CurrentUser.user.toString());
    }

    public static void updateCurrentStudent(User user) {
        CurrentUser.user.setFirstName(user.getFirstName());
        CurrentUser.user.setMiddleName(user.getMiddleName());
        CurrentUser.user.setLastName(user.getLastName());
        CurrentUser.user.setUsername(user.getUsername());
        CurrentUser.user.setPassword(user.getPassword());
        CurrentUser.user.setBirthdate(user.getBirthdate());
        CurrentUser.user.setMale(user.isMale());
        System.out.println("Update user: " + CurrentUser.user.toString());
    }

    public static void removeCurrentStudent() {
        System.out.println("Log out user: " + CurrentUser.user.toString());
        user = null;
    }
}
