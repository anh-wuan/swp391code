<!-- addTimeshare.jsp -->
<html>
<head>
    <title>Add Timeshare</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
    <div class="container">
        <h1>Add New Timeshare</h1>
        <form action="addTimeshare" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label for="name">Name:</label>
                <input type="text" id="name" name="name" required>
            </div>
            <div class="form-group">
                <label for="image">Image:</label>
                <input type="file" id="image" name="image" accept="image/*">
            </div>
            <div class="form-group">
                <label for="address">Address:</label>
                <input type="text" id="address" name="address" required>
            </div>
            <div class="form-group">
                <label for="status">Status:</label>
                <input type="number" id="status" name="status" required>
            </div>
            <div class="form-group">
                <label for="userId">User ID:</label>
                <input type="number" id="userId" name="userId" required>
            </div>
            <div class="form-group">
                <label for="start_date">Start Date:</label>
                <input type="datetime-local" id="start_date" name="start_date" required>
            </div>
            <div class="form-group">
                <label for="end_date">End Date:</label>
                <input type="datetime-local" id="end_date" name="end_date" required>
            </div>
            <div class="form-group">
                <label for="description">Description:</label>
                <textarea id="description" name="description" required></textarea>
            </div>
            <input type="submit" value="Add Timeshare">
        </form>
    </div>
</body>
</html>
