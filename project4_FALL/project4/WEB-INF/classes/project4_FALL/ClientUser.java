/* Name: Sebastian Ferreira
Course: CNT 4714 – Fall 2023 – Project Three
Assignment title: A Three-Tier Distributed Web-Based Application
Date: December 5, 2023
*/

package project4_FALL;

// Sevlet-JSP web app allowing a client-level user to issue any SQL command against project3 db
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
//import javax.servlet.*;   //used for Tomcat 9.x and earlier only
//import javax.servlet.http.*;  //used for Tomcat 9.x and earlier only


@WebServlet("/clientuser")
public class ClientUser extends HttpServlet {
	
/* 
 * The doPost() method handles the execution of the user request, i.e., their SQL command
 * It takes the text from the HTML text area and checks it to determine if it is a SELECT or an UPDATE staement.
 * Based on the result, the command is passed to the appropriate executor.
 * All the results of the query are then passed to the ResultSetToHTMLFormatter class for conversion into a format
 * (HTML table) that can be rendered by any web browser (HTML). All errors or
 * responses by the server are returned to the user's browser session
 */
	
	
	private Connection connection;
    private Statement statement;

    @Override 
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	response.setContentType("text/html;charset=UTF-8");
    	String sqlStatement = request.getParameter("sqlStatement");
        String message = "";

        try {
            getDBConnection(); // Establish DB connection
            statement = connection.createStatement();
            sqlStatement = sqlStatement.trim(); // Trim the SQL statement

            if(sqlStatement.toLowerCase().startsWith("select")) {
                ResultSet rs = statement.executeQuery(sqlStatement);
                
                // check if resultset is empty
                if (!rs.isBeforeFirst()) {
                	System.out.println("No data");
                	message="No data found for this query";
                } else {
                	// if data exists, process it
                    message = ResultSetToHTMLFormatterClass.getHtmlRows(rs); // Convert ResultSet to HTML
                }
            } else {
                // Handle non-select commands here, perhaps returning an error or restriction message
                message = "Update operations are not allowed for client-level users.";
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
            message = "Error: " + e.getMessage();
        } finally {
        	request.setAttribute("sqlStatement", sqlStatement);
            request.setAttribute("messages", message);
            request.getRequestDispatcher("/clientHome.jsp").forward(request, response);
        }
    }

    private void getDBConnection() throws IOException, SQLException, ClassNotFoundException {
        Properties props = new Properties();
        String path = getServletContext().getRealPath("/WEB-INF/lib/client.properties");
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
