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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/rootuser")
public class RootUser extends HttpServlet {

    @Override 
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        String sqlStatement = request.getParameter("sqlStatement");
        String message = "";

        try (Connection connection = getDBConnection()) {
            try (Statement statement = connection.createStatement()) {
                sqlStatement = sqlStatement.trim(); // Trim the SQL statement

                if(sqlStatement.toLowerCase().startsWith("select")) {
                    try (ResultSet rs = statement.executeQuery(sqlStatement)) {
                        if (!rs.isBeforeFirst()) {
                            message = "No data found for this query";
                        } else {
                            message = ResultSetToHTMLFormatterClass.getHtmlRows(rs);
                        }
                    }
                } else {
                    int result = statement.executeUpdate(sqlStatement);
                    message = result + " rows affected.";
                }
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
            message = "Error: " + e.getMessage();
        } finally {
        	request.setAttribute("sqlStatement", sqlStatement);
            request.setAttribute("messages", message);
            request.getRequestDispatcher("/rootHome.jsp").forward(request, response);
        }
    }

    private Connection getDBConnection() throws IOException, SQLException, ClassNotFoundException {
        Properties props = new Properties();
        String path = getServletContext().getRealPath("/WEB-INF/lib/root.properties");
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
