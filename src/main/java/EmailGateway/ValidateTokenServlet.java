package EmailGateway;

import Classes.DBConnection;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
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
        String token = request.getParameter("token");


        try {

            String update_users_sql = "UPDATE users SET isValidated = 1 WHERE token = ?";
            PreparedStatement validate = conn.prepareStatement(update_users_sql);
            validate.setString(1, token);
            validate.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
/*
        // verify token
        try {
            String sql = "SELECT * FROM users WHERE email=? AND token=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, (String) session.getAttribute("user_email"));
            stmt.setString(2,token);
            ResultSet rs = stmt.executeQuery();

            if (!rs.isBeforeFirst() ) {
                session.setAttribute("invalid_token", "true");
                RequestDispatcher dispatcher = request.getRequestDispatcher("EnterTokenSignup.jsp");
                dispatcher.forward(request, response);
            } else {
                rs.next();
                // update users table
                try {
                    String update_users_sql = "UPDATE users SET isValidated = 1 WHERE email = ?";
                    PreparedStatement validate = conn.prepareStatement(update_users_sql);
                    validate.setString(1, (String) session.getAttribute("user_email"));
                    validate.executeUpdate();

                } catch(Exception e) {
                    e.printStackTrace();
                }
                session.setAttribute("validated_token", "true");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
*/


    }
}
