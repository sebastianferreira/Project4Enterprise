<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!-- rootHome.jsp -->
    
<%
    String sqlStatement = (String) request.getAttribute("sqlStatement");
    if (sqlStatement == null) {
        sqlStatement = ""; // Default value if the attribute isn't set
    }
    String messages = (String) request.getAttribute("messages");
    if (messages == null) {
        messages = ""; // Default value if the attribute isn't set
    }
%>

<html lang="en">
<head>
    <meta charset="utf-8" />
    <title>CNT 4714 Project 4 Root Home Page</title> 
    <style type="text/css">
       body {
           background-color:black;
           color:lightseagreen;
           font-size: 2em;
           font-family: Verdana, Arial, sans-serif;
        }
       input {
           background-color:blanchedalmond;
           color: black;
           font-size:0.5em;
        }
        h1{
            color:yellow;
            text-align: center;
        }
        h2{
            text-align: center;
            display:  block;
            background-color: black;
            color:green;
        }
        h3{
            color:black;
            text-align:center;
        }
        p{
            color:white;
            font-size:medium;
            text-align:center;
        }
        textarea.sql-input {
            width: 80%;        /* Width of the text area */
            height: 150px;     /* Height of the text area */
            background-color: lightblue; /* Background color */
            color: black;      /* Text color */
            font-size: 1em;    /* Text font size */
            padding: 10px;     /* Padding inside the text area */
            border: 1px solid #000; /* Border around the text area */
            margin: 10px 0;    /* Margin around the text area */
            resize: vertical;  /* Allow vertical resizing, disable horizontal */
        }
        h6{
            color:white;
            text-align:center;
        }
   </style>  
    
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"> </script>
    <script type="text/javascript">
        function eraseText() {
            // next line illustrates a straight javascript technique
            // document.getElementById("cmd").innerHTML = " ";
            // next line illustrates a jquery technique
            $("#cmd").html("");
        }
        function eraseData() {
            // next line illustrates a straight javascript technique
            // document.getElementById("data").innerHTML = "";
            // next line illustrates a jquery technique
            $("#data").remove();
        }
        </script>
</head>
<body>
    <h1>Welcome to the Fall 2023 Project 4 Enterprise System</h1>
    <h2>A Servlet/JSP-based Multi-tiered Enterprise Application Using a Tomcat Container</h2><br><hr>
    <p>You are connected to the project 4 Enterprise System database as a <span style="color: red;">root-level</span> user. </p>
    <p>Please enter any SQL query or update command in the box below.</p><br>
    <form action="/project4/rootuser" method="post">
        <textarea id="cmd" name="sqlStatement" cols=60 rows=8><%=sqlStatement%></textarea><br>
        <input type="submit" value="Execute command" /> &nbsp; &nbsp; &nbsp;
        <input type="reset" value="Reset Form" onclick="javascript:eraseText();" /> &nbsp; &nbsp; &nbsp;
        <input type="button" value="Clear Results" onclick="javascript:eraseData();" />
    </form><br>
    <p>All execution results will appear below this line</p><br><hr>
    <h6>Execution Results:</h6>
    <br>
    <center>
        <table id="data">
                <%=messages%>
        </table>
    </center>
</body>
</html>