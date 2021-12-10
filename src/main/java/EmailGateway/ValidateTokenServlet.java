package EmailGateway;

import Classes.DBConnection;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet(name = "ValidateTokenServlet", value = "/ValidateTokenServlet")
public class ValidateTokenServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Connection conn = DBConnection.getConnection();
        HttpSession session = request.getSession(true);
        String token = (String) session.getAttribute("token");
        String password = request.getParameter("password");
        String hashed = "";

        try {

            // validate the user
            String update_users_sql = "UPDATE users SET isValidated = 1 WHERE token = ?";
            PreparedStatement validate = conn.prepareStatement(update_users_sql);
            validate.setString(1, token);
            validate.executeUpdate();

            // hash password
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");

                byte[] messageDigest = md.digest(password.getBytes());

                BigInteger no = new BigInteger(1, messageDigest);
                String hashtext = no.toString(16);
                while (hashtext.length() < 32) {
                    hashtext = "0" + hashtext;
                }
                hashed = hashtext;
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            // set the password
            String update_pass_sql = "UPDATE users SET password = ? WHERE token = ?";
            PreparedStatement pass = conn.prepareStatement(update_pass_sql);
            pass.setString(1, hashed);
            pass.setString(2,token);
            pass.executeUpdate();
            session.setAttribute("validated_token","true");
            request.getRequestDispatcher("index.jsp").forward(request, response);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
