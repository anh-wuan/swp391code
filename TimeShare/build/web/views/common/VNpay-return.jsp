<%@page import="java.net.URLEncoder"%>
<%@page import="java.nio.charset.StandardCharsets"%>
<%@page import="vnpay.Config"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.TimeZone"%>
<%@page import="dao.InvoicesDAO"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
        <meta name="description" content="">
        <meta name="author" content="">
        <title>VNPAY RESPONSE</title>
        <!-- Bootstrap core CSS -->
        <link href="assets/bootstrap.min.css" rel="stylesheet"/>
        <!-- Custom styles for this template -->
        <link href="assets/jumbotron-narrow.css" rel="stylesheet"> 
        <script src="assets/jquery-1.11.3.min.js"></script>
        <link rel="icon" href="img/logo.png">
        <style>
            .title {
                font-weight: bold
            }
            .info {
                padding-left: 20px;
            }

            table {
                margin: auto
            }
        </style>
        <style>
            .thankyou-wrapper{
                width:100%;
                height:auto;
                margin:auto;
                background:#ffffff;
                padding:10px 0px 50px;
            }
            .thankyou-wrapper h1{
                font:100px Arial, Helvetica, sans-serif;
                text-align:center;
                color:#333333;
                padding:0px 10px 10px;
            }
            .thankyou-wrapper p{
                font:26px Arial, Helvetica, sans-serif;
                text-align:center;
                color:#333333;
                padding:5px 10px 10px;
            }
            .thankyou-wrapper a{
                font:26px Arial, Helvetica, sans-serif;
                text-align:center;
                color:#ffffff;
                display:block;
                text-decoration:none;
                width:250px;
                background:#E47425;
                margin:10px auto 0px;
                padding:15px 20px 15px;
                border-bottom:5px solid #F96700;
            }
            .thankyou-wrapper a:hover{
                font:26px Arial, Helvetica, sans-serif;
                text-align:center;
                color:#ffffff;
                display:block;
                text-decoration:none;
                width:250px;
                background:#F96700;
                margin:10px auto 0px;
                padding:15px 20px 15px;
                border-bottom:5px solid #F96700;
            }
        </style>

    </head>
    <body>
        <%
            //Begin process return from VNPAY
            Map fields = new HashMap();
            for (Enumeration params = request.getParameterNames(); params.hasMoreElements();) {
                String fieldName = URLEncoder.encode((String) params.nextElement(), StandardCharsets.US_ASCII.toString());
                String fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII.toString());
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    fields.put(fieldName, fieldValue);
                }
            }

            String vnp_SecureHash = request.getParameter("vnp_SecureHash");
            if (fields.containsKey("vnp_SecureHashType")) {
                fields.remove("vnp_SecureHashType");
            }
            if (fields.containsKey("vnp_SecureHash")) {
                fields.remove("vnp_SecureHash");
            }
            String signValue = Config.hashAllFields(fields);

        %>
        <!--Begin display -->
        <div class="container">
            <div class="table-responsive">
                <section class="login-main-wrapper">
                    <div class="main-container">
                        <div class="login-process">
                            <div class="login-main-container">
                                <div class="thankyou-wrapper">
                                    <h1><img src="https://t3.ftcdn.net/jpg/02/91/52/22/360_F_291522205_XkrmS421FjSGTMRdTrqFZPxDY19VxpmL.jpg" alt="thanks" /></h1>
                                    <p>for contacting us, we will get in touch with you soon... </p>
                                    <table class="table-responsive">
                                        <tr>
                                            <td class="title">Merchant Transaction Code:</td>
                                            <td class="info"><%=request.getParameter("vnp_TxnRef")%></td>
                                        </tr> 
                                        <tr>
                                            <td class="title">Amount:</td>
                                            <td class="info"><%=String.format("%,.0f",Double.parseDouble(request.getParameter("vnp_Amount"))/100)%></td>
                                        </tr>  
                                        <tr>
                                            <td class="title">Order info:</td>
                                            <td class="info"><%=request.getParameter("vnp_OrderInfo")%></td>
                                        </tr> 
                                        <tr>
                                            <td class="title">VNPAY Response Code:</td>
                                            <td class="info"><%=request.getParameter("vnp_ResponseCode")%></td>
                                        </tr> 
                                        <tr>
                                            <td class="title">VNPAY Transaction Code:</td>
                                            <td class="info"><%=request.getParameter("vnp_TransactionNo")%></td>
                                        </tr> 
                                        <tr>
                                            <td class="title">Bank Code:</td>
                                            <td class="info"><%=request.getParameter("vnp_BankCode")%></td>
                                        </tr> 
                                        <tr>
                                            <td class="title">Pay Date:</td>
                                            <td class="info"><%=request.getParameter("vnp_PayDate")%></td>
                                        </tr> 
                                        <tr>
                                            <td class="title">Payment Status:</td>
                                            <td class="info">
                                                <%
                                                    String transactionCode = (String)request.getParameter("vnp_TxnRef");
                                                    if (signValue.equals(vnp_SecureHash)) {
                                                        if ("00".equals(request.getParameter("vnp_TransactionStatus"))) {
                                                            boolean a = new InvoicesDAO().updateInvoiceStatusByTransactionCode(transactionCode);  
                                                            out.print(a);
                                                        } else {
                                        
                                                        }
                                                    } else {
                                                        out.print("Failed");
                                                    }
                                                %>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colspan="2" style="text-align: center">
                                                <a style="margin-top: 20px" href="./home" type="button" class="btn btn-lg btn-default md-btn-flat mt-2 mr-3">Back to home</a>
                                            </td>
                                        </tr>
                                </div> 
                                </table>

                                <div class="clr"></div>
                            </div>
                            <div class="clr"></div>
                        </div>
                    </div>
                    <div class="clr"></div>
            </div>
        </section>

    </div>

    <p>
        &nbsp;
    </p>
    <footer class="footer text-center" style="text-align: center">
        <p>&copy; VNPAY 2020</p>
    </footer>
</div>  
</body>
</html>
