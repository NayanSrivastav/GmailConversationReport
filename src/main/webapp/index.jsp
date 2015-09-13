<%-- 
    Document   : index
    Created on : 11 Sep, 2015, 1:11:07 AM
    Author     : nayan
--%>

<%@page import="com.mycompany.mailreport.GoogleSignIn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <a href="<%=new GoogleSignIn().buildLoginUrl()%>">Login</a>
    </body>
</html>
