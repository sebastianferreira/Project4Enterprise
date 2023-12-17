package project4_FALL;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import java.sql.Statement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/SupplierInsertServlet")
public class AddSupplierRecord extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) 
        throws IOException, ServletException {

        // Extract parameters from the request
        String snum = request.getParameter("snum");
        String sname = request.getParameter("sname");
        String status = request.getParameter("status");
        String city = request.getParameter("city");
        String message = "";

        // Use try-with-resources for efficient connection handling
        try (Connection connection = getDBConnection()) {
            // Prepare and execute the insert statement
            String sql = "INSERT INTO suppliers (snum, sname, status, city) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, snum);
                pstmt.setString(2, sname);
                pstmt.setInt(3, Integer.parseInt(status));
                pstmt.setString(4, city);
                int result = pstmt.executeUpdate();
                message = (result > 0) ? "Supplier record inserted successfully." : "Supplier record insertion failed.";
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            message = "Error: " + e.getMessage();
        } finally {
            // Set the execution result and forward to the JSP
            request.setAttribute("executionResult", message);
            request.getRequestDispatcher("/dataentryHome.jsp").forward(request, response);
        }
    }

    private Connection getDBConnection() throws IOException, SQLException, ClassNotFoundException {
        Properties props = new Properties();
        String path = getServletContext().getRealPath("/WEB-INF/lib/dataentry.properties");
        try (FileInputStream in = new FileInputStream(path)) {
            props.load(in);
        }

        String drivers = props.getProperty("MYSQL_DB_DRIVER_CLASS");
        String connectionURL = props.getProperty("MYSQL_DB_URL");
        String username = props.getProperty("MYSQL_DB_USERNAME");
        String password = props.getProperty("MYSQL_DB_PASSWORD");

        Class.forName(drivers);
        return DriverManager.getConnection(connectionURL, username, password);
    }
}
