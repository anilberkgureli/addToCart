import java.sql.*;

public class ShoppingCartDAO {
    private Connection connection;

    public ShoppingCartDAO() throws SQLException {
        String url = "jdbc:postgresql://localhost/mydb";
        String username = "postgres";
        String password = "a13s2d4A";
        connection = DriverManager.getConnection(url, username, password);
    }

    public int getLastItemID() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT MAX(id) FROM products");
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt(1);
    }

    public void add(ShoppingCart item) throws SQLException {
        PreparedStatement checkIdStatement = connection.prepareStatement("SELECT COUNT(*) FROM products WHERE id = ?");
        checkIdStatement.setInt(1, item.getItemID());
        ResultSet result = checkIdStatement.executeQuery();
        result.next();
        int count = result.getInt(1);

        if (count > 0) {
            throw new SQLException("The item with ID " + item.getItemID() + " already exists in the database.");
        }

        else {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO products (id, name, stock, price) VALUES (?, ?, ?, ?)");
            preparedStatement.setInt(1, item.getItemID());
            preparedStatement.setString(2, item.getItemName());
            preparedStatement.setInt(3, item.getQuantity());
            preparedStatement.setDouble(4, item.getPrice());
            preparedStatement.executeUpdate();
        }
    }

    public void delete(int itemID) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM products WHERE id = ?");
        preparedStatement.setInt(1, itemID);
        preparedStatement.executeUpdate();
    }

    public void updateQuantity(int itemID, int newQuantity) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE products SET stock = ? WHERE id = ?");
        preparedStatement.setInt(1, newQuantity);
        preparedStatement.setInt(2, itemID);
        preparedStatement.executeUpdate();
    }

    public double getTotalPrice() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT SUM(stock * price) FROM products");
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getDouble(1);
    }

    public ShoppingCart[] getAllItems() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM products");
        ResultSet resultSet = preparedStatement.executeQuery();
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnCount = resultSetMetaData.getColumnCount();
        ShoppingCart[] items = new ShoppingCart[columnCount];
        while (resultSet.next()) {
            items[0] = new ShoppingCart(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getDouble(4));
        }
        return items;
    }

    public void clearCart() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM products");
        preparedStatement.executeUpdate();
    }
}