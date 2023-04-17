import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        System.out.println("MENU:");
        System.out.println("1- Sepetimi göster");
        System.out.println("2- Ürün ekle");
        System.out.println("3- Ürünleri kaydet");
        System.out.println("4- Sepetimi temizle");

        do {
            System.out.println("Lütfen bir seçenek seçiniz:");
            int choice = scan.nextInt();
            scan.nextLine();

            switch (choice) {
                case 1:
                    try {
                        ShoppingCartDAO dao = new ShoppingCartDAO();

                        // Sepetteki toplam fiyatı gösterme
                        System.out.println("Toplam Fiyat: " + dao.getTotalPrice());

                        // Tüm öğeleri gösterme
                        ShoppingCart[] items = dao.getAllItems();
                        for (int i = 0; i < items.length; i++) {
                            if (items[i] == null) {
                                continue;
                            }
                            System.out.println("Item ID: " + items[i].getItemID());
                            System.out.println("Item Name: " + items[i].getItemName());
                            System.out.println("Quantity: " + items[i].getQuantity());
                            System.out.println("Price: " + items[i].getPrice());
                            System.out.println("-----------------------------");
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    System.out.println("Lütfen eklemek istediğiniz ürünün adını giriniz:");
                    String itemName = scan.nextLine();

                    System.out.println("Lütfen eklemek istediğiniz ürünün fiyatını giriniz:");
                    int price = scan.nextInt();

                    System.out.println("Lütfen eklemek istediğiniz ürünün miktarını giriniz:");
                    int quantity = scan.nextInt();

                    try {
                        ShoppingCartDAO dao = new ShoppingCartDAO();

                        // get the last id
                        int lastID = dao.getLastItemID();

                        // Yeni ID oluştur
                        int newID = lastID + 1;

                        // yeni öğeyi ekleme
                        ShoppingCart newItem = new ShoppingCart(newID, itemName, price, quantity);
                        dao.add(newItem);

                        System.out.println("Ürün başarıyla eklendi!");

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    System.out.println("Tüm ürünleri kaydetmek istediğinizden emin misiniz? (E/H)");
                    String response = scan.nextLine();
                    if (response.equalsIgnoreCase("E")) {
                        try {
                            ShoppingCartDAO dao = new ShoppingCartDAO();
                            ShoppingCart[] items = dao.getAllItems();
                            for (int i = 0; i < items.length; i++) {
                                if (items[i] == null) {
                                    continue;
                                }
                                dao.add(items[i]);
                            }
                            System.out.println("Tüm ürünler başarıyla kaydedildi!");
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    break;

                case 4:
                    System.out.println("Sepetini temizlemek istediğine emin misin? (E/H)");
                    response = scan.nextLine();
                    if (response.equals("E")) {
                        try{
                            ShoppingCartDAO dao = new ShoppingCartDAO();
                            dao.clearCart();
                            System.out.println("Sepet başarıyla temizlendi");
                        }
                        catch (SQLException e){
                            e.printStackTrace();
                        }
                    }
                    else if(response.equals("H")){
                        System.out.println("Ana menüye yönlendiriliyorsunuz.");
                        break;
                    }
                default:
                    System.out.println("Lütfen geçerli bir seçenek seçiniz!");
                    break;
            }

            System.out.println("Başka bir işlem yapmak istiyor musunuz? (E/H)");
            String response = scan.nextLine();
            if (!response.equalsIgnoreCase("E")) {
                break;
            }

        } while (true);

        System.out.println("Program sonlandırılıyor!");
    }
}

