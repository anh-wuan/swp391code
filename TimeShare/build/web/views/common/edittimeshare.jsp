<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit TimeShare</title>
</head>
<body>
    <h1>Edit TimeShare</h1>

    <!-- Form sửa TimeShare -->
    <form action="updateTimeshare.jsp" method="post">
        <!-- Hiển thị thông tin của TimeShare hiện tại -->
        <input type="hidden" name="timeshareId" value="${param.timeshareId}">
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" value="${timeshare.TSname}" required><br>
        <label for="address">Address:</label>
        <input type="text" id="address" name="address" value="${timeshare.Address}" required><br>
        <!-- Thêm các trường khác của TimeShare cần sửa -->
        <input type="submit" value="Edit TimeShare">
    </form>
</body>
</html>
