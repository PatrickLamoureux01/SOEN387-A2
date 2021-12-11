package EmailGateway;

import Classes.DBConnection;
import TestSuite.TestSuite;
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


        TestSuite ts = new TestSuite();
        Connection conn = DBConnection.getConnection();
        HttpSession session = request.getSession(true);
        String type = (String) session.getAttribute("r_type");
        String password = request.getParameter("password");
        String hashed = "";

        if (type.equals("signup")) {
            String token = (String) session.getAttribute("s_token");
            try {

                // validate the user
                String update_users_sql = "UPDATE users SET isValidated = 1 WHERE validation_token = ?";
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
                String update_pass_sql = "UPDATE users SET password = ? WHERE validation_token = ?";
                PreparedStatement pass = conn.prepareStatement(update_pass_sql);
                pass.setString(1, hashed);
                pass.setString(2,token);
                pass.executeUpdate();
                session.setAttribute("forgot_token","false");
                session.setAttribute("validated_token","true");
                request.getRequestDispatcher("index.jsp").forward(request, response);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (type.equals("forgot")){
            String token = (String) session.getAttribute("f_token");

            try {

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
                String update_pass_sql = "UPDATE users SET password = ? WHERE forgot_token = ?";
                PreparedStatement pass = conn.prepareStatement(update_pass_sql);
                pass.setString(1, hashed);
                pass.setString(2, token);
                pass.executeUpdate();
                session.setAttribute("validated_token","false");
                session.setAttribute("forgot_token", "true");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else {
        try {
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

            ts.write("Current MD5 hash for "+ts.getEmail((Integer) session.getAttribute("user_id"))+": "+ts.getPassword((Integer) session.getAttribute("user_id"))+"\n");
            // set the password
            String update_pass_sql2 = "UPDATE users SET password = ? WHERE user_id = ?";
            PreparedStatement pass2 = conn.prepareStatement(update_pass_sql2);
            pass2.setString(1, hashed);
            pass2.setInt(2, (Integer) session.getAttribute("user_id"));
            pass2.executeUpdate();
            ts.checkPasswordChanged(ts.getEmail((Integer) session.getAttribute("user_id")));
            session.setAttribute("validated_token", "false");
            session.setAttribute("forgot_token", "true");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        }
    }
}
