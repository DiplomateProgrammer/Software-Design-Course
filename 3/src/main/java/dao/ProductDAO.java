package dao;

import db.DBUtils;
import entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private ResultSet executeQuery(String query) throws SQLException {
        Statement stmt = DBUtils.getStatement();
        return stmt.executeQuery(query);
    }

    private List<Product> toProductList(ResultSet rs) throws SQLException {
        List<Product> products = new ArrayList<>();
        while (rs.next()) {
            String name = rs.getString("name");
            long price = rs.getLong("price");
            products.add(new Product(name, price));
        }
        return products;
    }

    private List<Product> getProductsByQuery(String query) throws SQLException {
        return toProductList(executeQuery(query));
    }

    private void executeUpdate(String query) throws SQLException {
        Statement stmt = DBUtils.getStatement();
        stmt.executeUpdate(query);
    }

    public void addProduct(Product product) throws SQLException {
        executeUpdate("INSERT INTO PRODUCT " +
                "(NAME, PRICE) VALUES  (\"" + product.getName() + "\"," + product.getPrice() + ")");
    }

    public List<Product> getProducts() throws SQLException {
        return getProductsByQuery("SELECT * FROM PRODUCT");
    }

    public List<Product> getMaxProducts() throws SQLException {
        return getProductsByQuery("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1");
    }

    public List<Product> getMinProducts() throws SQLException {
        return getProductsByQuery("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1");
    }

    public long sumProductPrice() throws SQLException {
        return executeQuery("SELECT SUM(PRICE) AS RES FROM PRODUCT").getLong("res");
    }

    public long countProducts() throws SQLException {
        return executeQuery("SELECT COUNT(*) AS RES FROM PRODUCT").getLong("res");
    }
}
