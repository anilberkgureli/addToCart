import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
//        Connection conn = null;
//        try {
//            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mydb", "postgres", "a13s2d4A");
//            System.out.println("Bağlantı başarılı");
//        } catch (SQLException e) {
//            System.out.println("Bağlantı hatası: " + e.getMessage());
//        } finally {
//            try {
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (SQLException ex) {
//                System.out.println("Bağlantı kapatma hatası: " + ex.getMessage());
//            }
//        }

        try {
            ShoppingCartDAO dao = new ShoppingCartDAO();

            // Örnek ürünleri ekleme
            dao.add(new ShoppingCart(1, "Kalem", 2, 2.5));
            dao.add(new ShoppingCart(2, "Defter", 1, 5.0));
            dao.add(new ShoppingCart(3, "Silgi", 3, 1.0));

            // Sepetteki toplam fiyatı gösterme
            System.out.println("Toplam Fiyat: " + dao.getTotalPrice());

            // Tüm öğeleri gösterme
            ShoppingCart[] items = dao.getAllItems();
            for (int i = 0; i < items.length; i++) {
                System.out.println("Item ID: " + items[i].getItemID());
                System.out.println("Item Name: " + items[i].getItemName());
                System.out.println("Quantity: " + items[i].getQuantity());
                System.out.println("Price: " + items[i].getPrice());
                System.out.println("-----------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
