package servlet;

import entity.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends ProductServlet {
    public GetProductsServlet(){
        super();
    }

    @Override
    protected void handleRequest(Statement statement, HttpServletRequest request, HttpServletResponse response)
            throws IOException, SQLException {
        List<Product> products = productDAO.getProducts();
        List<String> info = products.stream().map(Product::toHttpString).collect(Collectors.toList());
        writeHttpResponse(response, info);
    }
}
