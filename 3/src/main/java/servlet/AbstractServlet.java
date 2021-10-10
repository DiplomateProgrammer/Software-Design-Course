package servlet;

import db.DBUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 * @author volhovm
 */
public abstract class AbstractServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            Statement stmt = DBUtils.getStatement();
            handleRequest(stmt, request, response);
            stmt.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    protected abstract void handleRequest(Statement statement, HttpServletRequest request, HttpServletResponse response)
            throws IOException, SQLException;

    public static void dumpItems(HttpServletResponse response, ResultSet rs, String title)
            throws IOException, SQLException {
        response.getWriter().println("<html><body>");
        response.getWriter().println("<h1>" + title + ": </h1>");
        while (rs.next()) {
            String name = rs.getString("name");
            int price = rs.getInt("price");
            response.getWriter().println(name + "\t" + price + "</br>");
        }
        response.getWriter().println("</body></html>");
    }

}
