package project4_FALL;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.sql.Statement;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AccountantServlet")
public class AccountantServlet extends HttpServlet {

	private Connection connection;

	public void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String selectedOption = request.getParameter("command");
		String result = "";

		try {
			connection = getDBConnection(); // Method to establish DB connection

			switch (selectedOption) {
			case "option1":
				// Execute stored procedure or SQL for option 1
				result = executeOption1();
				break;
			case "option2":
				// Execute stored procedure or SQL for option 2
				result = executeOption2();
				break;
			case "option3":
				// Execute stored procedure or SQL for option 1
				result = executeOption3();
				break;
			case "option4":
				// Execute stored procedure or SQL for option 1
				result = executeOption4();
				break;
			case "option5":
				// Execute stored procedure or SQL for option 1
				result = executeOption5();
				break;
			}
		} catch (Exception e) {
			result = "Error: " + e.getMessage();
		} finally {
			request.setAttribute("result", result);
			request.getRequestDispatcher("/accountantHome.jsp").forward(request, response);
		}
	}

	private String executeOption1() throws SQLException {
		// Execute your SQL or stored procedure and return formatted results
		try (Statement statement = connection.createStatement()) {
			ResultSet rs = statement.executeQuery("CALL Get_The_Maximum_Status_Of_All_Suppliers()");
			return ResultSetToHTMLFormatterClass.getHtmlRows(rs);
		}
	}

	private String executeOption2() throws SQLException {
		// Execute your SQL or stored procedure and return formatted results
		try (Statement statement = connection.createStatement()) {
			ResultSet rs = statement.executeQuery("CALL Get_The_Sum_Of_All_Parts_Weights()");
			return ResultSetToHTMLFormatterClass.getHtmlRows(rs);
		}
	}

	private String executeOption3() throws SQLException {
		// Execute your SQL or stored procedure and return formatted results
		try (Statement statement = connection.createStatement()) {
			ResultSet rs = statement.executeQuery("CALL Get_The_Total_Number_Of_Shipments()");
			return ResultSetToHTMLFormatterClass.getHtmlRows(rs);
		}
	}

	private String executeOption4() throws SQLException {
		// Execute your SQL or stored procedure and return formatted results
		try (Statement statement = connection.createStatement()) {
			ResultSet rs = statement.executeQuery("CALL Get_The_Name_Of_The_Job_With_The_Most_Workers()");
			return ResultSetToHTMLFormatterClass.getHtmlRows(rs);
		}
	}

	private String executeOption5() throws SQLException {
		// Execute your SQL or stored procedure and return formatted results
		try (Statement statement = connection.createStatement()) {
			ResultSet rs = statement.executeQuery("CALL List_The_Name_And_Status_Of_All_Suppliers()");
			return ResultSetToHTMLFormatterClass.getHtmlRows(rs);

		}
	}

	// Similar methods for other options

	private Connection getDBConnection() throws IOException, SQLException, ClassNotFoundException {
		Properties props = new Properties();
		String path = getServletContext().getRealPath("/WEB-INF/lib/accountant.properties");
		FileInputStream in = new FileInputStream(path);
		props.load(in);
		in.close();

		String drivers = props.getProperty("MYSQL_DB_DRIVER_CLASS");
		String connectionURL = props.getProperty("MYSQL_DB_URL");
		String username = props.getProperty("MYSQL_DB_USERNAME");
		String password = props.getProperty("MYSQL_DB_PASSWORD");

		Class.forName(drivers);
		return connection = DriverManager.getConnection(connectionURL, username, password);
	}
}
