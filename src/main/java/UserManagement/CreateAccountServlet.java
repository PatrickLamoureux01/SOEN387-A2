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


@WebServlet(name = "CreateAccountServlet", value = "/CreateAccountServlet")
public class CreateAccountServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        TestSuite ts = new TestSuite();
        Connection conn = DBConnection.getConnection();
        HttpSession session = request.getSession(true);
        String email = request.getParameter("email");
        String fname = request.getParameter("fName");
        String lname = request.getParameter("lName");

        // Generate token
        String alphanum = "0123456789ABCDEFGHJKMNPQRSTVWXYZ";
        Random random = ThreadLocalRandom.current();
        int alphabetLength = alphanum.length();
        char[] chars = new char[10];
        for (int i = 0; i < 10; i++)
            chars[i] = alphanum.charAt(random.nextInt(alphabetLength));
        String token =  String.valueOf(chars);
        session.setAttribute("s_token",token);

        try {
            String sql = "INSERT INTO users(email,fName,lName,validation_token) VALUES(?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,email);
            stmt.setString(2,fname);
            stmt.setString(3,lname);
            stmt.setString(4,token);
            stmt.executeUpdate();
            session.setAttribute("r_type","signup");
            ts.checkUserRecentlyCreated(email);
            RequestDispatcher rd = request.getRequestDispatcher("EmailServlet");
            rd.forward(request,response);


        } catch(Exception e) {
            e.printStackTrace();
        }

    }
}
