package org.telegram.service;

import org.telegram.db.DataBase;
import org.telegram.entity.Product;
import org.telegram.entity.Shop;

import java.util.Optional;
import java.util.UUID;

import static org.telegram.db.DataBase.intscan;

public class SellerService {
    public static void sellerService (){
        String menu = """
                       Saleer Menu: 
                   0.Exit
                   1.add product 
                   2.edit product
                   3.create shop
                   4.show shops
                   5.Show history 
                   6.delete profile
                   """;

        while (true) {
            System.out.println(menu);
            int choice = intscan.nextInt();
            switch (choice) {
                case 0 -> {
                    System.out.println("Returning to previous menu...");
                    return;
                }
                case 1 -> addProduct();
                case 2 -> editProduct();
                case 3 -> createShop();
                case 4 -> showShops();
                case 5 -> showHistory();
                case 6 -> deleteProfile();
                default -> System.out.println("error");
            }
        }
    }


    private static void addProduct() {
        System.out.println("Enter the shop name: ");
        String shopName = DataBase.strscan.nextLine().trim();

        Optional<Shop> shoppde = DataBase.shops
                .stream()
                .filter(s7s -> s7s.getName().equals(shopName)).findFirst();

        if (shoppde.isEmpty()) {
            System.out.println("Shop not found!");
            return;
        }

        Shop shop = shoppde.get();
        Product product = new Product();
        product.setId(UUID.randomUUID().toString());
        product.setShopId(shop.getId());

        System.out.println("Enter the product name: ");
        product.setName(DataBase.strscan.nextLine().trim());

        System.out.println("Enter the product price: ");
        product.setPrice(DataBase.intscan.nextDouble());

        DataBase.products.add(product);
        System.out.println("Product added successfully!");
    }

    private static void editProduct() {
        System.out.println("Enter product name to edit: ");
        String productName = DataBase.strscan.nextLine().trim();
        Optional<Product> productOpt = DataBase.products
                .stream()
                .filter(p -> p.getName().equals(productName))
                .findFirst();
        if (productOpt.isEmpty()) {
            System.out.println("Product not found!");
            return;
        }
        Product product = productOpt.get();
        System.out.println("1. Change Name ");
        System.out.println("2. Change Price ");
        System.out.println("3. Delete Product");

        int choice = DataBase.intscan.nextInt();
        DataBase.strscan.nextLine();
        switch (choice) {
            case 1 -> {
                System.out.println("Enter new name: ");
                product.setName(DataBase.strscan.nextLine().trim());
            }
            case 2 -> {
                System.out.println("Enter new price: ");
                product.setPrice(DataBase.intscan.nextDouble());
            }
            case 3 -> DataBase.products.remove(product);
            default -> System.out.println("Invalid choice!");
        }
    }

    private static void createShop() {
        Shop shop = new Shop();
        shop.setId(UUID.randomUUID().toString());
        System.out.println("Enter the shop name: ");
        shop.setName(DataBase.strscan.nextLine().trim());
        DataBase.shops.add(shop);
        System.out.println("Shop created !");
    }

    private static void showShops() {
        System.out.println("List of shops:");
        DataBase.shops.forEach(shop -> System.out.println("- " + shop.getName()));
    }


    private static void showHistory() {
        System.out.println("Purchase history:");
        DataBase.histories.forEach(history ->
                System.out.println("Spent: " + history.getOverallPrice() + " | Products: " + history.getProducts().size()));
    }

    private static void deleteProfile() {
        System.out.println("Profile deleted!");
    }

}
