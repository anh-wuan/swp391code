<%@ page import="java.net.URLEncoder" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset = UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>HomeStay</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/post-timeshare.css">
        <!-- Google Web Fonts -->
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <div class="registersform">
            <div class="flexsForm">
                <form action="addTimeshare" method="POST" enctype="multipart/form-data">
                    <h1>Create Home stay</h1>
                    <label style="color: green">${MESSAGE}</label>     
                    <label style="color: red">${ERROR}</label>

                    <div class="dividerSocial"></div>

                    <div class="textField">
                        <label>Homestay Name</label><input type="text" name="name" /><span class="error"></span>
                    </div>

                    <div class="textField">
                        <label>Address</label><input name="address" type="text" /><span class="error"></span>
                    </div>
                    <div class="textField">
                        <label>Start date</label>
                        <input type="date" name="start_date"/>                 
                    </div>
                    <div class="textField">
                        <label>End Date</label>
                        <input type="date" name="end_date"/>                 
                    </div>
                    <div class="textField">
                        <label>Description</label>
                        <textarea id="description" name="description" style="height: 150px"></textarea>
                        <span class="error"></span>
                    </div>
                    <button type="submit">Create</button>
            </div>
            <span class="divider"></span>
            <div class="loginsForm">
                <h3>Add Image Time Share</h3>
                <input type="file" accept="image/*" name="image" class="registerButton" />
            </div>
        </form>
    </div>
    <jsp:include page="footer.jsp" />

</body>

<script>
    const profilePicture = document.getElementById('profile-picture');
    const imageInput = document.getElementById('image-input');

    profilePicture.addEventListener('click', () => {
        imageInput.disabled = false;
        imageInput.click();
    });
    const updateAvatar = false;
    imageInput.addEventListener('change', () => {
        imageInput.disabled = false;
        const file = imageInput.files[0];
        const formData = new FormData();
        formData.append('image', file);

        const reader = new FileReader();
        reader.onload = () => {
            profilePicture.src = reader.result;
        };
        reader.readAsDataURL(file);
        updateAvatar = true;
    });
    if (!updateAvatar && imageInput.value != null) {
        imageInput.disabled = true;
    }
    document.addEventListener("DOMContentLoaded", function () {
        const phoneInput = document.getElementById("Phone");

        phoneInput.addEventListener("input", function () {
            const regex = /^\d{0,10}$/;
            if (!regex.test(phoneInput.value)) {
                // If validation fails, show a custom error message
                phoneInput.setCustomValidity("Số điện thoại phải gồm 10 chữ số và không chứa ký tự đặc biệt.");
            } else {
                // Clear custom error message
                phoneInput.setCustomValidity("");
            }
        });
    });
</script>
</html>