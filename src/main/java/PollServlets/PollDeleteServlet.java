package PollServlets;

import Classes.DBConnection;
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

        try
        {
            // wipe out poll
            String delete_poll_sql = "DELETE FROM polls WHERE poll_id = ?";
            PreparedStatement delete_poll = conn.prepareStatement(delete_poll_sql);
            delete_poll.setString(1,id);
            delete_poll.executeUpdate();

            // wipe out choices
            String delete_choices_sql = "DELETE FROM choices WHERE poll_id = ?";
            PreparedStatement delete_choices = conn.prepareStatement(delete_choices_sql);
            delete_choices.setString(1,id);
            delete_choices.executeUpdate();

            // wipe out votes
            String delete_votes_sql = "DELETE FROM vote WHERE poll_id = ?";
            PreparedStatement delete_votes = conn.prepareStatement(delete_votes_sql);
            delete_votes.setString(1,id);
            delete_votes.executeUpdate();

            // wipe out pollUser
            String delete_pollUser_sql = "DELETE FROM pollUser WHERE poll_id = ?";
            PreparedStatement delete_pollUser = conn.prepareStatement(delete_pollUser_sql);
            delete_pollUser.setString(1,id);
            delete_pollUser.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        request.getRequestDispatcher("manager_index.jsp").forward(request, response);
    }
}
