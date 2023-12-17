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

@WebServlet("/PartInsertServlet")
public class AddPartRecord extends HttpServlet {

    private Connection connection;
    private Statement statement;
    
    @Override
    public void init() throws ServletException {
        // Initialize the database connection here
        try {
            getDBConnection();
        } catch (Exception e) {
            throw new ServletException("Failed to establish connection", e);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Extract parameters from the request
        String pnum = request.getParameter("pnum");
        String pname = request.getParameter("pname");
        String color = request.getParameter("color");
        String weight = request.getParameter("weight");
        String city = request.getParameter("city");
        String message = "";

        try {
            // Prepare and execute the insert statement
    		String sql = "INSERT INTO parts (pnum, pname, color, weight, city) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            	pstmt.setString(1, pnum);
    			pstmt.setString(2, pname);
    			pstmt.setString(3, color);
    			pstmt.setInt(4, Integer.parseInt(weight));
    			pstmt.setString(5, city);
                int result = pstmt.executeUpdate();
                message = (result > 0) ? "Parts record inserted successfully." : "Parts record insertion failed.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            message = "Error: " + e.getMessage();
        } finally {
        	// Forward to JSP first before closing the connection
            request.setAttribute("executionResult", message);
            request.getRequestDispatcher("/dataentryHome.jsp").forward(request, response);
        
            // Close connection and forward to JSP
            if (connection != null) try { connection.close(); } catch (SQLException ignore) {}}
        
        // After the insert, you want to show the updated table data
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT * FROM parts"); // Adjust the query as needed
            
            // Check if ResultSet is empty
            if (!rs.isBeforeFirst()) {
                message = "No data found in suppliers table.";
            } else {
                // If data exists, process it to generate HTML table
                message = ResultSetToHTMLFormatterClass.getHtmlRows(rs);
            }
        } catch (SQLException e) {
            // Handle exceptions and possibly log them
            message = "Error retrieving updated data: " + e.getMessage();
        } finally {
            // Close connection and forward to JSP
            if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
            }
    }

    private void getDBConnection() throws IOException, SQLException, ClassNotFoundException {
        Properties props = new Properties();
        String path = getServletContext().getRealPath("/WEB-INF/lib/dataentry.properties");
        FileInputStream in = new FileInputStream(path);
        props.load(in);
        in.close();

        String drivers = props.getProperty("MYSQL_DB_DRIVER_CLASS");
        String connectionURL = props.getProperty("MYSQL_DB_URL");
        String username = props.getProperty("MYSQL_DB_USERNAME");
        String password = props.getProperty("MYSQL_DB_PASSWORD");

        Class.forName(drivers);
        connection = DriverManager.getConnection(connectionURL, username, password);
    }
}
