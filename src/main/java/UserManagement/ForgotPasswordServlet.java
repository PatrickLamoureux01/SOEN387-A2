package UserManagement;

import Classes.DBConnection;
import TestSuite.TestSuite;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@WebServlet(name = "ForgotPasswordServlet", value = "/ForgotPasswordServlet")
public class ForgotPasswordServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        TestSuite ts = new TestSuite();

        Connection conn = DBConnection.getConnection();
        HttpSession session = request.getSession(true);
        String email = request.getParameter("email");

        // Generate token
        String alphanum = "0123456789ABCDEFGHJKMNPQRSTVWXYZ";
        Random random = ThreadLocalRandom.current();
        int alphabetLength = alphanum.length();
        char[] chars = new char[10];
        for (int i = 0; i < 10; i++)
            chars[i] = alphanum.charAt(random.nextInt(alphabetLength));
        String token =  String.valueOf(chars);
        session.setAttribute("f_token",token);

        try {
            // wipe previous password
            String sql = "UPDATE users SET password = null WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,email);
            stmt.executeUpdate();

            // get the user's name
            String get_name_sql = "SELECT fName,lName FROM users WHERE email=?";
            PreparedStatement get_name = conn.prepareStatement(get_name_sql);
            get_name.setString(1,email);
            ResultSet rs = get_name.executeQuery();
            rs.next();
            session.setAttribute("fName", rs.getString("fName"));
            session.setAttribute("lName", rs.getString("lName"));

            // set forgot token
            String sql2 = "UPDATE users SET forgot_token = ? WHERE email = ?";
            PreparedStatement stmt2 = conn.prepareStatement(sql2);
            stmt2.setString(1,token);
            stmt2.setString(2,email);
            stmt2.executeUpdate();
            ts.write("Forgot token generated is: "+token+"\n");
            ts.checkForgotToken(email);
            session.setAttribute("r_type","forgot");
            RequestDispatcher rd = request.getRequestDispatcher("EmailServlet");
            rd.forward(request,response);


        } catch(Exception e) {
            e.printStackTrace();
        }

    }
}
