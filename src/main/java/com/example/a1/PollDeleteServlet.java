package com.example.a1;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet(name = "PollDeleteServlet", value = "/PollDeleteServlet")
public class PollDeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        Connection conn = DBConnection.getConnection();
        String id = request.getParameter("poll_to_delete");

        //String id = answer.split(":")[0];

        try
        {
            String delete_poll_sql = "DELETE FROM polls WHERE poll_id = ?";
            PreparedStatement delete_poll = conn.prepareStatement(delete_poll_sql);
            delete_poll.setString(1,id);
            delete_poll.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        request.getRequestDispatcher("manager_index.jsp").forward(request, response);
    }
}
