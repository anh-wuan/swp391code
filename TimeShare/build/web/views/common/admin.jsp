<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Panel</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }

        .container {
            max-width: 800px;
            margin: 20px auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        h1 {
            text-align: center;
            color: #333;
        }

        label {
            font-weight: bold;
        }

        input[type="text"],
        textarea,
        input[type="file"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }

        input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            padding: 14px 20px;
            margin: 8px 0;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            width: 100%;
        }

        input[type="submit"]:hover {
            background-color: #45a049;
        }

        .form-group {
            margin-bottom: 20px;
        }

        .form-group:after {
            content: "";
            display: table;
            clear: both;
        }

        .form-group label {
            float: left;
            width: 30%;
            margin-top: 10px;
            text-align: right;
            margin-right: 2%;
        }

        .form-group input,
        .form-group textarea {
            float: left;
            width: 68%;
        }
    </style>
    <!-- Include jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <!-- Include jQuery UI library for datetimepicker -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-datetimepicker/2.5.20/jquery.datetimepicker.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-datetimepicker/2.5.20/jquery.datetimepicker.full.min.js"></script>
    <script>
        // Function to initialize datetimepicker for start date and end date fields
        $(function() {
            $('#start_date').datetimepicker({
                format: 'Y-m-d H:i:s',
                step: 1
            });
            $('#end_date').datetimepicker({
                format: 'Y-m-d H:i:s',
                step: 1
            });
        });
    </script>
</head>
<body>
    <div class="container">
        <h1>Admin Panel</h1>
        <div class="form-group">
            <form action="addTimeshare" method="post" enctype="multipart/form-data">
                <label for="name">Name:</label>
                <input type="text" id="name" name="name">
                <label for="address">Address:</label>
                <input type="text" id="address" name="address">
                <label for="start_date">Start Date:</label>
                <input type="text" id="start_date" name="start_date">
                <label for="end_date">End Date:</label>
                <input type="text" id="end_date" name="end_date">
                <label for="description">Description:</label>
                <textarea id="description" name="description"></textarea>
                <input type="submit" value="Add Timeshare">
            </form>
        </div>

        <div class="form-group">
            <form action="editTimeshare" method="post">
                <label for="edit_timeShareId">Timeshare ID:</label>
                <input type="text" id="edit_timeShareId" name="timeShareId">
                <label for="edit_name">Name:</label>
                <input type="text" id="edit_name" name="name">
                <label for="edit_address">Address:</label>
                <input type="text" id="edit_address" name="address">
                <label for="edit_start_date">Start Date:</label>
                <input type="text" id="edit_start_date" name="start_date">
                <label for="edit_end_date">End Date:</label>
                <input type="text" id="edit_end_date" name="end_date">
                <label for="edit_description">Description:</label>
                <textarea id="edit_description" name="description"></textarea>
                <input type="submit" value="Edit Timeshare">
            </form>
        </div>

        <div class="form-group">
            <form action="deleteTimeshare" method="get">
                <label for="delete_timeShareId">Timeshare ID to Delete:</label>
                <input type="text" id="delete_timeShareId" name="timeShareId">
                <input type="submit" value="Delete Timeshare">
            </form>
        </div>
    </div>
</body>
</html>
