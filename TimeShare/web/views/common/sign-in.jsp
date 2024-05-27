<%-- 
    Document   : sigin-in.jsp
    Created on : May 18, 2024, 10:16:54 PM
    Author     : Datnt
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Login</h1>
        <form action="auth" method="POST">
            <input type="hidden" value="login" name="action"/>
        <div>
            <label for="username">Username:</label>
            <input type="text" id="username" name="userName">
        </div>
        <div>
            <label for="password">Password:</label>
            <input type="password" id="password" name="password">
        </div>
        <div>
            <input type="submit" value="Login">
        </div>
        </form>
        <div style="color: red">${ERRORMESSAGE}</div>
         <a href="auth?action=sign-up">Sign up</a>
    </body>
</html>
