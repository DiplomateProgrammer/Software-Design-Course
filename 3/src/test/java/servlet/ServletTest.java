package servlet;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class ServletTest {

    private static void makeRequest(String request) {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(request);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @BeforeAll
    static void initTable() {
        makeRequest("DROP TABLE IF EXISTS product");
        makeRequest("CREATE TABLE IF NOT EXISTS product" +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " name TEXT NOT NULL, " +
                " price INT NOT NULL)");
        makeRequest("INSERT INTO product (name, price) VALUES (\"cpu1\", 100)");
        makeRequest("INSERT INTO product (name, price) VALUES (\"cpu2\", 200)");
        makeRequest("INSERT INTO product (name, price) VALUES (\"cpu3\", 500)");
    }

    @Mock
    private HttpServletRequest requestMock;
    @Mock
    private HttpServletResponse responseMock;

    private StringWriter stringWriter;
    private PrintWriter printWriter;

    @BeforeEach
    void setup() {
        requestMock = mock(HttpServletRequest.class);
        responseMock = mock(HttpServletResponse.class);
        stringWriter = new StringWriter();
        printWriter = new PrintWriter(stringWriter);
    }

    @Test
    @DisplayName("AddProductServlet test")
    public void addProductServletTest() throws IOException {
        Mockito.when(requestMock.getParameter("name")).thenReturn("cpu4");
        Mockito.when(requestMock.getParameter("price")).thenReturn("600");
        Mockito.when(responseMock.getWriter()).thenReturn(printWriter);

        new AddProductServlet().doGet(requestMock, responseMock);

        Mockito.verify(requestMock, Mockito.atLeastOnce()).getParameter("name");
        Mockito.verify(requestMock, Mockito.atLeastOnce()).getParameter("price");

        String result = stringWriter.toString();
        assertTrue(result.contains("OK"));
    }

    @Test
    @DisplayName("GetProductsServlet test")
    public void getProductServletTest() throws IOException {
        Mockito.when(responseMock.getWriter()).thenReturn(printWriter);

        new GetProductsServlet().doGet(requestMock, responseMock);

        String result = stringWriter.toString();
        assertTrue(result.contains("cpu1"));
        assertTrue(result.contains("100"));
        assertTrue(result.contains("cpu2"));
        assertTrue(result.contains("200"));
        assertTrue(result.contains("cpu3"));
        assertTrue(result.contains("500"));
        assertTrue(result.contains("cpu4"));
        assertTrue(result.contains("600"));
    }

    @Nested
    @DisplayName("QueryServlet tests")
    public class queryServletTest {
        String makeQueryRequest(String command) throws IOException{
            Mockito.when(requestMock.getParameter("command")).thenReturn(command);
            Mockito.when(responseMock.getWriter()).thenReturn(printWriter);

            new QueryServlet().doGet(requestMock, responseMock);

            Mockito.verify(requestMock, Mockito.atLeastOnce()).getParameter("command");

            return stringWriter.toString();
        }

        @Test
        @DisplayName("Test max")
        public void testMax() throws IOException {
            String result = makeQueryRequest("max");
            assertTrue(result.contains("max price"));
            assertTrue(result.contains("cpu4"));
            assertTrue(result.contains("600"));
        }

        @Test
        @DisplayName("Test min")
        public void testMin() throws IOException {
            String result = makeQueryRequest("min");
            assertTrue(result.contains("min price"));
            assertTrue(result.contains("cpu1"));
            assertTrue(result.contains("100"));
        }

        @Test
        @DisplayName("Test sum")
        public void testSum() throws IOException {
            String result = makeQueryRequest("sum");
            assertTrue(result.contains("Summary price"));
            assertTrue(result.contains("1400"));
        }

        @Test
        @DisplayName("Test count")
        public void testCount() throws IOException {
            String result = makeQueryRequest("count");
            assertTrue(result.contains("Number of products"));
            assertTrue(result.contains("4"));
        }

        @Test
        @DisplayName("Test unknown")
        public void testUnknown() throws IOException {
            String result = makeQueryRequest("invalid_command");
            assertTrue(result.contains("Unknown command"));
            assertTrue(result.contains("invalid_command"));
        }
    }
}
