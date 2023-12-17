<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!-- dataEntry.jsp -->

<html lang="en">
<head>
    <meta charset="utf-8" />
    <title>CNT 4714 Project 4 Data Entry Home Page</title> 
    <style type="text/css">
        table, th, td {
            border-collapse:collapse;
            margin-left: auto;  /* Centering the table */
            margin-right: auto; /* Centering the table */
        }
        th, td {
            border:2px solid yellow;
            color:lightseagreen;
            font-size:0.5em;
            padding:10px;
            text-align:center;
        }
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
            color:red;
            text-align: center;
            font-size:xx-large;
        }
        h4{
            text-align: center;
            display:  block;
            background-color: black;
            color:blue;
        }
        p{
            color:white;
            font-size:medium;
            text-align:center;
        }
         .button-container {
            text-align: center; /* Centering the buttons */
            margin-top: 20px;   /* Optional: Adds some space above the buttons */
        }
        input[type="submit"], input[type="button"] {
            margin: 5px; /* Adds space around buttons */
            /* Other button styling as needed */
        }
        
   </style>  
</head>
<body>
    <h1>Welcome to the Fall 2023 Project 4 Enterprise System</h1>
    <h4>Data Entry Application</h4><br><hr>
    <p>You are connected to the project 4 Enterprise System database as a <span style="color: red;">data-entry-level</span> user. </p>
    <p>Enter the data values in a form below to add a new record to the corresponding database table.</p><hr><br>
    <p>Suppliers Record Insert</p>
    <form action="/project4/SupplierInsertServlet" method="post"> <!-- here the "your-server-endpoint" should be replaced with the actual URL where the form data should be submitted -->
        <table>
            <tr>
                <th>snum</th>
                <th>sname</th>
                <th>status</th>
                <th>city</th>
            </tr>
            <tr>
                <td><input type="text" name="snum" id="snum"></td>
                <td><input type="text" name="sname" id="sname"></td>
                <td><input type="text" name="status" id="status"></td>
                <td><input type="text" name="city" id="city"></td>
            </tr>
        </table>
        <div class="button-container">
            <input type="submit" value="Enter Supplier Record Into Database">
            <input type="button" value="Clear Data and Results" onclick="clearForm()">
        </div>
    </form>
    <script>
        function clearForm() {
            document.getElementById("snum").value = "";
            document.getElementById("sname").value = "";
            document.getElementById("status").value = "";
            document.getElementById("city").value = "";
        }
    </script><hr><br>
    <p>Parts Record Insert</p>
    <form action="/project4/PartInsertServlet" method="post"> <!-- here the "your-server-endpoint" should be replaced with the actual URL where the form data should be submitted -->
        <table>
            <tr>
                <th>pnum</th>
                <th>pname</th>
                <th>color</th>
                <th>weight</th>
                <th>city</th>
            </tr>
            <tr>
                <td><input type="text" name="pnum" id="pnum"></td>
                <td><input type="text" name="pname" id="pname"></td>
                <td><input type="text" name="color" id="color"></td>
                <td><input type="text" name="weight" id="weight"></td>
                <td><input type="text" name="city" id="city"></td>
            </tr>
        </table>
        <div class="button-container">
            <input type="submit" value="Enter Part Record Into Database">
            <input type="button" value="Clear Data and Results" onclick="clearForm()">
        </div>
    </form>
    <script>
        function clearForm() {
            document.getElementById("pnum").value = "";
            document.getElementById("pname").value = "";
            document.getElementById("color").value = "";
            document.getElementById("weight").value = "";
            document.getElementById("city").value = "";
        }
    </script><hr><br>
    <p>Jobs Record Insert</p>
    <form action="/project4/JobInsertServlet" method="post"> <!-- here the "your-server-endpoint" should be replaced with the actual URL where the form data should be submitted -->
        <table>
            <tr>
                <th>jnum</th>
                <th>jname</th>
                <th>numworkers</th>
                <th>city</th>
            </tr>
            <tr>
                <td><input type="text" name="jnum" id="jnum"></td>
                <td><input type="text" name="jname" id="jname"></td>
                <td><input type="text" name="numworkers" id="numworkers"></td>
                <td><input type="text" name="city" id="city"></td>
            </tr>
        </table>
        <div class="button-container">
            <input type="submit" value="Enter Job Record Into Database">
            <input type="button" value="Clear Data and Results" onclick="clearForm()">
        </div>
    </form>
    <script>
        function clearForm() {
            document.getElementById("jnum").value = "";
            document.getElementById("jname").value = "";
            document.getElementById("numworkers").value = "";
            document.getElementById("city").value = "";
        }
    </script><hr><br>
     <p>Shipments Record Insert</p>
    <form action="/project4/ShipmentInsertServlet" method="post"> <!-- here the "your-server-endpoint" should be replaced with the actual URL where the form data should be submitted -->
        <table>
            <tr>
                <th>snum</th>
                <th>pnum</th>
                <th>jnum</th>
                <th>quantity</th>
            </tr>
            <tr>
                <td><input type="text" name="snum" id="snum"></td>
                <td><input type="text" name="pnum" id="pnum"></td>
                <td><input type="text" name="jnum" id="jnum"></td>
                <td><input type="text" name="quantity" id="quantity"></td>
            </tr>
        </table>
        <div class="button-container">
            <input type="submit" value="Enter Shipments Record Into Database">
            <input type="button" value="Clear Data and Results" onclick="clearForm()">
        </div>
    </form>
    <script>
        function clearForm() {
            document.getElementById("snum").value = "";
            document.getElementById("pnum").value = "";
            document.getElementById("jnum").value = "";
            document.getElementById("quantity").value = "";
        }
    </script><hr>
    <p>Execution Results:</p>
    <table>
    <%= request.getAttribute("executionResult") != null ? request.getAttribute("executionResult") : "No results to display." %>
    </table>
</body>
</html>