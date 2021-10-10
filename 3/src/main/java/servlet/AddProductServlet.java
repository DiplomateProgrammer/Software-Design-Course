package servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author akirakozov
 */
public class AddProductServlet extends AbstractServlet {

    @Override
    protected void handleRequest(Statement statement, HttpServletRequest request, HttpServletResponse response)
            throws IOException, SQLException {
        String name = request.getParameter("name");
        long price = Long.parseLong(request.getParameter("price"));
        String sql = "INSERT INTO PRODUCT " +
                "(NAME, PRICE) VALUES (\"" + name + "\"," + price + ")";
        statement.executeUpdate(sql);
        response.getWriter().println("OK");
    }
}
