package org.telegram.service;
import lombok.Data;
import org.telegram.db.DataBase;
import org.telegram.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.telegram.db.DataBase.intscan;


public class UserService {
    public static void service (){
        String menu = """
                       User Menu: 
                   0.Exit
                   1.Show shops
                   2.Search product
                   3.Add to basket
                   4.Buy
                   5.Show history 
                   6.Show balance 
                   """;

        while (true) {
            System.out.println(menu);
            int choice = intscan.nextInt();
            switch (choice) {
                case 0 -> {return;}
                case 1 -> showShops();
                case 2 -> searchProduct();
                case 3 -> addToBasket();
                case 4 -> buyProduct();
                case 5 -> showHistory();
                case 6 -> showBalance();
                default -> System.out.println("error");
            }
        }
    }

    /// METODLARR

    private static void showShops() {
        System.out.println("LIST OF SHOPS: ");
        List<Shop> shops = DataBase.shops;
        if (shops.isEmpty()) {
            System.out.println("No shops");
            return;
        }
        System.out.println("SHOPS:");
        shops.forEach(shop -> System.out.println(" - " + shop.getName()));
    }


    private static void searchProduct() {
        System.out.println("Enter product name: ");
        String productName = DataBase.strscan.nextLine().toLowerCase();

        List<Product> products = DataBase.products.stream()
                .filter(p -> p.getName().toLowerCase().contains(productName))
                .toList();

        if (products.isEmpty()) {
            System.out.println("No products found.");
            return;
        }

        System.out.println("Found products:");
        products.forEach(product ->
                System.out.println(" - " + product.getName() + " | Price: " + product.getPrice())
        );
    }

    private static void addToBasket() {
        System.out.println("Select a shop:");
        List<Shop> shops = DataBase.shops;
        for (int i = 0; i < shops.size(); i++) {
            System.out.println((i + 1) + ". " + shops.get(i).getName());
        }
        int shopChoice = intscan.nextInt() - 1;
        if (shopChoice < 0 || shopChoice >= shops.size()) {
            System.out.println("Invalid shop selection!");
            return;
        }
        Shop selectedShop = shops.get(shopChoice);

        if (selectedShop.getProducts().isEmpty()) {
            System.out.println("No products available in this shop.");
            return;
        }

        System.out.println("Available products:");
        selectedShop.getProducts().forEach(product ->
                System.out.println(" - " + product.getName() + " | Price: " + product.getPrice())
        );

        System.out.println("Enter product name to add to basket: ");
        DataBase.strscan.nextLine(); // Consume newline
        String productName = DataBase.strscan.nextLine().toLowerCase();

        Optional<Product> productOpt = selectedShop.getProducts().stream()
                .filter(p -> p.getName().equalsIgnoreCase(productName))
                .findFirst();

        if (productOpt.isEmpty()) {
            System.out.println("Product not found in this shop!");
            return;
        }

        Product product = productOpt.get();
        User currentUser = DataBase.getCurrentUser();
        Basket basket = DataBase.baskets.stream()
                .filter(b -> b.getId().equals(currentUser.getId()))
                .findFirst()
                .orElseGet(() -> {
                    Basket newBasket = new Basket(currentUser.getId(), new ArrayList<>(), 0.0);
                    DataBase.baskets.add(newBasket);
                    return newBasket;
                });


        basket.getProducts().add(product);
        basket.setOverallPrice(basket.getOverallPrice() + product.getPrice());
        System.out.println("Product added to basket.");
    }


    private static void buyProduct() {
        User currentUser = DataBase.getCurrentUser();
        Basket basket = DataBase.baskets.stream()
                .filter(b -> b.getId().equals(currentUser.getId()))
                .findFirst()
                .orElse(null);

        if (basket == null || basket.getProducts().isEmpty()) {
            System.out.println("Your basket is empty.");
            return;
        }

        double totalPrice = basket.getOverallPrice();
        if (currentUser.getBalance() < totalPrice) {
            System.out.println("Insufficient balance!");
            return;
        }


        currentUser.setBalance(currentUser.getBalance() - totalPrice);
        History history = new History();
        history.setProducts(basket.getProducts());
        history.setOverallPrice(totalPrice);
        DataBase.histories.add(history);

        basket.getProducts().clear();
        basket.setOverallPrice(0.0);

        System.out.println("Purchase successful! Total cost: " + totalPrice);
    }

    private static void showHistory() {
        List<History> histories = DataBase.histories;
        if (histories.isEmpty()) {
            System.out.println("No purchase history.");
            return;
        }

        System.out.println("Purchase history:");
        histories.forEach(history ->
                System.out.println(" - Spent: " + history.getOverallPrice() + " | Products: " + history.getProducts().size())
        );
    }

    private static void showBalance() {
        String menu = """
                 Balance Menu:
             0.Exit
             1.Show Balance
             2.Add  Balance
             """;
        System.out.println(menu);
        int choice = intscan.nextInt();
        switch (choice){
            case 0->{
                return;
            }
            case 1->{
                System.out.println("Your balance: " + DataBase.getCurrentUser().getBalance());
            }
            case 2 -> {
                System.out.print("Enter amount: ");
                double summ = DataBase.intscan.nextDouble();
                if (summ <= 0) {
                    System.out.println("Invalid amount. Please enter a positive value.");
                    return;
                }
                User currentUser = DataBase.getCurrentUser();
                currentUser.setBalance(currentUser.getBalance() + summ);
                System.out.println("successfull: " + currentUser.getBalance());
            }

            default -> {
                System.out.println("error");
            }

        }
    }

}
