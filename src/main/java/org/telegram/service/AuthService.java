package org.telegram.service;

import org.telegram.db.DataBase;
import org.telegram.entity.User;
import org.telegram.entity.enums.Role;
import org.telegram.entity.enums.Status;

import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;
import java.util.List;

import static org.telegram.db.DataBase.*;

public class AuthService {
    private final Scanner scanner = new Scanner(System.in);

    public static void service() {
        while (true) {
            System.out.println("""
                    0 - Exit
                    1 - Sign Up
                    2 - Sign In
                    """);
            int choice = intscan.nextInt();
            switch (choice) {
                case 0 -> {
                    return;
                }
                case 1 -> signUp();
                case 2 -> signIn();
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void signUp() {
        User user = new User();
        user.setId(UUID.randomUUID().toString());

        System.out.println("Enter login: ");
        String login = strscan.nextLine().trim();

        if (users.stream().anyMatch(u -> u.getLogin().equals(login))) {
            System.out.println("Such a user exists on the network 😔...");
            return;
        }

        user.setLogin(login);

        System.out.println("Enter name: ");
        user.setName(strscan.nextLine().trim());

        System.out.println("Enter password: ");
        user.setPassword(strscan.nextLine().trim());

        /*System.out.println("Enter balance: ");
        while (!intscan.hasNextDouble()) {
            System.out.println("Invalid input. Please enter a valid number:");
            intscan.next();
        }
        user.setBalance(intscan.nextDouble());
        strscan.nextLine();
*/
        user.setUserStatus(Status.ACTIVE);
        chooseRole(user);
        users.add(user);
        System.out.println("User successfully registered!");
    }

    public static void signIn() {
        System.out.println("Enter login: ");
        String login = strscan.nextLine().trim();

        System.out.println("Enter password: ");
        String password = strscan.nextLine().trim();

        Optional<User> userOptional = users.stream()
                .filter(u -> Objects.equals(u.getLogin(), login) && Objects.equals(u.getPassword(), password))
                .findFirst();

        if (userOptional.isEmpty()) {
            System.out.println("This user does not exist on the network. 😔...");
            return;
        }

        User currentUser = userOptional.get();
        if (currentUser.getUserRole() == Role.USER) {
            UserService.service();
        } else if (currentUser.getUserRole() == Role.SELLER) {
            SellerService.sellerService();
        }
    }

    private static void chooseRole(User user) {
        System.out.println("""
                Choose Role: 
                1. User
                2. Seller
                """);
        int choice;
        while (true) {
            if (intscan.hasNextInt()) {
                choice = intscan.nextInt();
                strscan.nextLine();
                if (choice == 1) {
                    user.setUserRole(Role.USER);
                    break;
                } else if (choice == 2) {
                    user.setUserRole(Role.SELLER);
                    break;
                }
            }
            System.out.println("Error entering information, please try again. 😊");
            strscan.nextLine();
        }
    }
}
