package project4_FALL;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import java.sql.*;

public class ResultSetToHTMLFormatterClass {

/*
* The getHtmlRows() method takes the inbound ResultSet object (a table returned from an
* SQL database query opertaion) and reformats it into a HTML encoded table suitable for
* return and display in any web-browser
* 
*  IMPORTANT NOTE: Its important to notice that this is a static synchronized method. 
*  This is because we only want to allow at most 1 thread to be executing this code
*  at one time to eliminate the possibility of interleaved return results
*/
	
	public static synchronized String getHtmlRows(ResultSet results) throws SQLException {
	        StringBuilder htmlRows = new StringBuilder();
	        ResultSetMetaData metaData = results.getMetaData();
	        int columnCount = metaData.getColumnCount();

	        // Start table and set header row
	        htmlRows.append("<table><tr>");
	        // Loop through columns to create header
	        for (int i = 1; i <= columnCount; i++) {
	            htmlRows.append("<th>").append(metaData.getColumnName(i)).append("</th>");
	        }
	        htmlRows.append("</tr>");

	        // Table body with zebra striping for rows
	        int rowCounter = 0; // Counter for zebra striping
	        while (results.next()) {
	            // Determine row class for striping
	            String rowClass = (rowCounter % 2 == 0) ? "firstColor" : "secondColor";
	            htmlRows.append("<tr class='").append(rowClass).append("'>");

	            // Loop through each column in this row
	            for (int i = 1; i <= columnCount; i++) {
	                // Append data from ResultSet to table cell
	                htmlRows.append("<td>").append(results.getString(i)).append("</td>");
	            }
	            htmlRows.append("</tr>");
	            rowCounter++; // Increment row counter for next iteration
	        }

	        // Close table tag
	        htmlRows.append("</table>");

	        // Convert StringBuilder to String and return
	        return htmlRows.toString();

		
	}
}
