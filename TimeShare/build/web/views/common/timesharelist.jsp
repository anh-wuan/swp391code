<%-- 
    Document   : timesharelist
    Created on : Jun 3, 2024, 1:19:30 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>TimeShare Management</title>
    </head>
    <body>
        <%@ include file="header.jsp"%>

        <div class="container profile-container">
            <h1 class="text-center mb-4 mt-5">TimeShare Management</h1>

            <c:if test="${param.success ne null}">
                <div class="alert alert-success" role="alert">
                    Update success!
                </div>
            </c:if>
                <c:if test="${param.fail ne null}">
                <div class="alert alert-danger" role="alert">
                    Update fail!
                </div>
            </c:if>
                <div class="w-100 text-right">
                <button type="button" class="btn btn-primary mb-3" data-toggle="modal" data-target="#addRoomModal">Add Room</button>
            </div>
                

    </body>
</html>
