package PollServlets;

import Classes.DBConnection;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet(name = "PollCloseServlet", value = "/PollCloseServlet")
public class PollCloseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        Connection conn = DBConnection.getConnection();
        String id = request.getParameter("poll_to_close");

        try
        {
            String close_poll_sql = "UPDATE polls SET status='closed' WHERE poll_id = ?";
            PreparedStatement close_poll = conn.prepareStatement(close_poll_sql);
            close_poll.setString(1,id);
            close_poll.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        request.getRequestDispatcher("manager_index.jsp").forward(request, response);

    }
}
