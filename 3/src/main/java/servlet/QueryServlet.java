package servlet;

import entity.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author akirakozov
 */
public class QueryServlet extends ProductServlet {
    public QueryServlet() {
        super();
    }

    protected void handleRequest(Statement statement, HttpServletRequest request, HttpServletResponse response)
            throws IOException, SQLException {
        String command = request.getParameter("command");
        switch (command) {
            case "max" -> handleMax(response);
            case "min" -> handleMin(response);
            case "sum" -> handleSum(response);
            case "count" -> handleCount(response);
            default -> response.getWriter().println("Unknown command: " + command);
        }
    }

    private static List<String> toHttpString(List<Product> products) {
        return products.stream().map(Product::toHttpString).collect(Collectors.toList());
    }

    private void handleMax(HttpServletResponse response) throws SQLException, IOException{
        List<String> products = toHttpString(productDAO.getMaxProducts());
        List<String> output = new ArrayList<>();
        output.add("<h1>Items with max price: </h1>");
        output.addAll(products);
        writeHttpResponse(response, output);
    }
    private void handleMin(HttpServletResponse response) throws SQLException, IOException{
        List<String> products = toHttpString(productDAO.getMinProducts());
        List<String> output = new ArrayList<>();
        output.add("<h1>Items with min price: </h1>");
        output.addAll(products);
        writeHttpResponse(response, output);
    }
    private void handleSum(HttpServletResponse response) throws SQLException, IOException {
        List<String> output = List.of("Summary price: ", Long.toString(productDAO.sumProductPrice()));
        writeHttpResponse(response, output);
    }

    private void handleCount(HttpServletResponse response) throws SQLException, IOException {
        List<String> output = List.of("Number of products: ", Long.toString(productDAO.countProducts()));
        writeHttpResponse(response, output);
    }
}
