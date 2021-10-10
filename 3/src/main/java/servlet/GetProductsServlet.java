package servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends AbstractServlet {
    @Override
    protected void handleRequest(Statement statement, HttpServletRequest request, HttpServletResponse response)
            throws IOException, SQLException {
        ResultSet rs = statement.executeQuery("SELECT * FROM PRODUCT");
        AbstractServlet.dumpItems(response, rs, "All items that we have");
    }
}
