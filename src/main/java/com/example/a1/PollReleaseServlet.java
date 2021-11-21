package com.example.a1;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet(name = "PollReleaseServlet", value = "/PollReleaseServlet")
public class PollReleaseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        Connection conn = DBConnection.getConnection();
        String id = request.getParameter("poll_to_release");

        try
        {
            // set status back to created
            String update_poll_sql = "UPDATE polls SET status = 'released' WHERE poll_id = ?";
            PreparedStatement update_poll = conn.prepareStatement(update_poll_sql);
            update_poll.setString(1,id);
            update_poll.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        request.getRequestDispatcher("manager_index.jsp").forward(request, response);

    }
}
