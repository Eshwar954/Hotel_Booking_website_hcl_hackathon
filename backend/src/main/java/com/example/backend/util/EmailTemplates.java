package com.hotelbooking.util;

public class EmailTemplates {
    public static String bookingConfirmation(String name, String reservation) {
        return "Hello " + name + ",\nYour reservation " + reservation + " is confirmed.";
    }
}
