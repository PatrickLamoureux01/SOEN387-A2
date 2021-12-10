package UserManagement;

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

@WebServlet(name = "ChangePasswordServlet", value = "/ChangePasswordServlet")
public class ChangePasswordServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        String password = request.getParameter("password");
        int user_id = (int) session.getAttribute("user_id");
        String hashed = "";
        Connection conn = DBConnection.getConnection();

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

        try {
            // validate password
            String sql = "SELECT * FROM users WHERE user_id=? AND password=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1,user_id);
            stmt.setString(2,hashed);
            ResultSet rs = stmt.executeQuery();

            if (!rs.isBeforeFirst() ) {
                session.setAttribute("invalid_change", "true");
                RequestDispatcher dispatcher = request.getRequestDispatcher("change_password.jsp");
                dispatcher.forward(request, response);
            } else {
                rs.next();
                session.setAttribute("r_type","change");
                request.getRequestDispatcher("set_pass.jsp?type=change").forward(request, response);
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

    }
}
