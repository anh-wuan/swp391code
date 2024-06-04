package controller;

import com.google.gson.JsonObject;
import dao.InvoicesDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import model.Invoices;
import model.User;
import vnpay.Config;

@WebServlet(name = "PaymentController", urlPatterns = {"/payment"})
public class PaymentController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse respone) throws ServletException, IOException {
        //format datetime for vnpay in vietnam
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        String bank_code = request.getParameter("bankCode");
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        String vnp_TxnRef = Config.getRandomNumber(8);
        String vnp_OrderInfo;
        if (request.getParameter("info") != null) {
            vnp_OrderInfo = request.getParameter("info");
        } else {
            vnp_OrderInfo = "Booking room";
        }
        String vnp_IpAddr = Config.getIpAddress(request);
        String vnp_TmnCode = Config.vnp_TmnCode;

        int amount = Integer.parseInt(request.getParameter("amount").replaceAll(",", "")) * 100;
        System.out.println(amount);
        Map vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        if (bank_code != null && !bank_code.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bank_code);
        } else {
            vnp_Params.put("vnp_BankCode", "NCB");
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = request.getParameter("language");
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_Returnurl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        String vnp_CreateDate = formatter.format(cld.getTime());

        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        //Add Params of 2.1.0 Version
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        //Build data to hash and querystring
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;
        com.google.gson.JsonObject job = new JsonObject();
        job.addProperty("code", "00");
        job.addProperty("message", "success");
        job.addProperty("data", paymentUrl);
        //Get user payment
        User user = (User) request.getSession().getAttribute("User");
        //Get bill amount
        String billAmount = request.getParameter("amount").replaceAll(",", "");
        //Get bill info
        if (vnp_OrderInfo.contains("Enroll")) {
            billAmount = "-" + billAmount;
        }
        //Get bill Date
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String payDate = formatter2.format(cld.getTime());
        
        
        String checkIn = request.getParameter("checkIn");
        String checkOut = request.getParameter("checkOut");
        String numPeople_raw = request.getParameter("numPeople");
        String numRoom_raw = request.getParameter("numRoom");
        String note = request.getParameter("note");
        String id_raw = request.getParameter("id");
        
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        // Specify the input date format
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd MMMM, yyyy");

        
        System.out.println("checkIn: " + checkIn);
        System.out.println("checkOut: " + checkOut);
        System.out.println("numPeople_raw: " + numPeople_raw);
        System.out.println("numRoom_raw: " + numRoom_raw);
        System.out.println("note: " + note);
        System.out.println("id_raw: " + id_raw);
        
        
        //Create bill
        try {
            int numPeople = Integer.parseInt(numPeople_raw);
            int numRoom = Integer.parseInt(numRoom_raw);
            int id = Integer.parseInt(id_raw);
            
            Invoices invoice = new Invoices();
            Date date = inputFormat.parse(checkIn);
            invoice.setCheckInDate(date);
            date = inputFormat.parse(checkOut);
            invoice.setCheckOutDate(date);
            System.out.println("nam");
            invoice.setNumberPerson(numPeople);
            invoice.setNumberRoom(numRoom);
            invoice.setNote(note);
            invoice.setReservationStatus(0);
            invoice.setRoomID(id);
            invoice.setTransactionCode(vnp_TxnRef);
            System.out.println("nam1");
            invoice.setUserID(user.getUserId());
            System.out.println("nam2");
            System.out.println(invoice);
            new InvoicesDAO().createNewInvoice(invoice);
        } catch (Exception e) {
            System.out.println("PaymentController: " + e.getMessage());
        }
        respone.sendRedirect(paymentUrl);
        
    }
    
    

}
