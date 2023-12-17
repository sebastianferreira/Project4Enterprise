/* Name: Sebastian Ferreira
Course: CNT 4714 – Fall 2023 – Project Three
Assignment title: A Three-Tier Distributed Web-Based Application
Date: December 5, 2023
*/
package project4_FALL;
// A simple servlet to process an HTTP get request.
// Main servlet in first-example web-app

// Users of Tomcat 10 onwards should be aware that, as a result of the move from Java EE to Jakarta EE as part of the
// transfer of Java EE to the Eclipse Foundation, the primary package for all implemented APIs has changed
// from javax.* to jakarta.*. This will almost certainly require code changes to enable applications to migrate
// from Tomcat 9 and earlier to Tomcat 10 and later. 

//import javax.servlet.*;   //used for Tomcat 9.x and earlier only
//import javax.servlet.http.*;  //used for Tomcat 9.x and earlier only
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;

@WebServlet("/authentication")
public class authentication extends HttpServlet {   
   // process "get" requests from clients
   @Override
protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException  {

	   String username = request.getParameter("username");
       String password = request.getParameter("password");

       String role = authenticateUser(username, password); // Implement this method

       if (role != null) {
           switch (role) {
              case "root":
                 response.sendRedirect("rootHome.jsp");
                 break;
              case "client":
                 response.sendRedirect("clientHome.jsp");
                 break;
              case "dataentry":
                 response.sendRedirect("dataentryHome.jsp");
                 break;
              case "accountant":
                 response.sendRedirect("accountantHome.jsp");
                 break;
              default:
                 response.sendRedirect("errorpage.html");
                 break;
           }
        } else {
           response.sendRedirect("errorpage.html");
        }
     
   
      
   } //end doGet() method
   
   
   /*
    *Read the credentials.txt file
    *Check if the username and password match
    *Return the user's role if successful, null otherwise
    */
   private String authenticateUser(String username, String password) { 
	   String credentialsPath = getServletContext().getRealPath("/WEB-INF/lib/credentials.txt");
	    try (BufferedReader reader = new BufferedReader(new FileReader(credentialsPath))) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            String[] parts = line.split(":");
	            if (parts.length == 3) {
	                String fileUsername = parts[0];
	                String filePassword = parts[1];
	                String role = parts[2];

	                if (fileUsername.equals(username) && filePassword.equals(password)) {
	                    return role;  // Return the role if credentials match
	                }
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return null;  // Return null if no match found
   }
   
} //end WelcomeServlet class