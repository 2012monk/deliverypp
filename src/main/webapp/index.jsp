<%@ page import="java.sql.Connection" %>
<%@ page import="static com.deli.deliverypp.DB.ConnHandler.getConn" %>
<%@ page import="static com.deli.deliverypp.DB.ConnHandler.close" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %>
</h1>
<br/>
<a href="hello-servlet">Hello Servlet</a>
</body>
</html>

<%
    Connection conn = getConn();
    close(conn);
%>