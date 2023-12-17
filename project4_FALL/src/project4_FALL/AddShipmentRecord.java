/* Name: Sebastian Ferreira
Course: CNT 4714 – Fall 2023 – Project Three
Assignment title: A Three-Tier Distributed Web-Based Application
Date: December 5, 2023
*/

package project4_FALL;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ShipmentInsertServlet")
public class AddShipmentRecord extends HttpServlet {

    private Statement statement;
    
    // Check if a supplier exists
    private boolean supplierExists(String snum, Connection conn) throws SQLException {
        String query = "SELECT COUNT(*) FROM suppliers WHERE snum = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, snum);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }
    
    // Check if a part exists
    private boolean partExists(String pnum, Connection conn) throws SQLException {
        String query = "SELECT COUNT(*) FROM parts WHERE pnum = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, pnum);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }
    
    // Check if a job exists
    private boolean jobExists(String jnum, Connection conn) throws SQLException {
        String query = "SELECT COUNT(*) FROM jobs WHERE jnum = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, jnum);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String snum = request.getParameter("snum");
        String pnum = request.getParameter("pnum");
        String jnum = request.getParameter("jnum");
        String quantity = request.getParameter("quantity");
        String message = "";
        
        // Print values to console for debugging
        System.out.println("snum: " + snum);
        System.out.println("pnum: " + pnum);
        System.out.println("jnum: " + jnum);
        System.out.println("quantity: " + quantity);


        try (Connection connection = getDBConnection()) {
            // Check if snum, pnum, jnum exist
            if (!supplierExists(snum, connection) || !partExists(pnum, connection) || !jobExists(jnum, connection)) {
                message = "Error: One of the foreign keys does not exist.";
            } else {
                // Prepare and execute the insert statement
                String sql = "INSERT INTO shipments (snum, pnum, jnum, quantity) VALUES (?, ?, ?, ?)";
                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.setString(1, snum);
                    pstmt.setString(2, pnum);
                    pstmt.setString(3, jnum);
                    pstmt.setInt(4, Integer.parseInt(quantity));
                    int result = pstmt.executeUpdate();
                    message = (result > 0) ? "Shipment record inserted successfully." : "Shipment record insertion failed.";
                }

                // Query to get updated data and append to message
                try (Statement statement = connection.createStatement()) {
                    ResultSet rs = statement.executeQuery("SELECT * FROM shipments");
                    if (!rs.isBeforeFirst()) {
                        message += " No data found in shipments table.";
                    } else {
                        message += " " + ResultSetToHTMLFormatterClass.getHtmlRows(rs);
                    }
                }
            }

            // Forward to JSP with the results
            request.setAttribute("executionResult", message);
            request.getRequestDispatcher("/dataentryHome.jsp").forward(request, response);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            message = "Error: " + e.getMessage();
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
