package UserManagement;

import Classes.DBConnection;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

@WebServlet(name = "UserLoginServlet", value = "/UserLoginServlet")
public class UserLoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        String email = request.getParameter("email");
        String passcode = request.getParameter("passcode");
        String hashed = "";
        Connection conn = DBConnection.getConnection();

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] messageDigest = md.digest(passcode.getBytes());

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
            String sql = "SELECT user_id,fName,lName FROM users WHERE email=? AND password=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,email);
            stmt.setString(2,hashed);
            ResultSet rs = stmt.executeQuery();

            if (!rs.isBeforeFirst() ) {
                session.setAttribute("invalid", "true");
                RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
                dispatcher.forward(request, response);
            } else {
                rs.next();
                session.setAttribute("user_id", rs.getInt("user_id"));
                session.setAttribute("fName", rs.getString("fName"));
                session.setAttribute("lName", rs.getString("lName"));
                request.getRequestDispatcher("manager_index.jsp").forward(request, response);
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
