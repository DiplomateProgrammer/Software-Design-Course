package servlet;

import dao.ProductDAO;
import db.DBUtils;
import entity.Product;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author volhovm
 */
public abstract class ProductServlet extends HttpServlet {
    protected final ProductDAO productDAO;
    public ProductServlet() {
        this.productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            Statement statement = DBUtils.getStatement();
            handleRequest(statement, request, response);
            statement.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    protected abstract void handleRequest(Statement statement, HttpServletRequest request, HttpServletResponse response)
            throws IOException, SQLException;

    protected void writeHttpResponse(HttpServletResponse response, List<String> info) throws IOException {
        response.getWriter().println("<html><body>");
        for (String s: info) {
            response.getWriter().println(s);
        }
        response.getWriter().println("</body></html>");
    }
}
