package org.telegram.db;

import lombok.Getter;
import lombok.Setter;
import org.telegram.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataBase {
    public static Scanner intscan = new Scanner(System.in);
    public static Scanner strscan = new Scanner(System.in);

    @Getter
    @Setter
    private static User currentUser;

    public static List<User> users = new ArrayList<>();
    public static List<Shop> shops = new ArrayList<>();
    public static List<Product> products = new ArrayList<>();
    public static List<History> histories = new ArrayList<>();
    public static List<Basket> baskets = new ArrayList<>();
}
