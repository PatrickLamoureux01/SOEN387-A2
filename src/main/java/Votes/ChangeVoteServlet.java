package Votes;

import Classes.Choice;
import Classes.DBConnection;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ChangeVoteServlet", value = "/ChangeVoteServlet")
public class ChangeVoteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        Connection conn = DBConnection.getConnection();
        String pin = request.getParameter("pin");
        String poll_id = "";
        String name = "";
        String question = "";
        List<Choice> choices = new ArrayList<>();

        try {
            // get poll id
            String select_poll_sql = "SELECT poll_id FROM polluser WHERE pin=?";
            PreparedStatement select_stmt = conn.prepareStatement(select_poll_sql);
            select_stmt.setInt(1, Integer.parseInt(pin));
            ResultSet rs = select_stmt.executeQuery();
            while (rs.next()) {
                poll_id = rs.getString("poll_id");
            }

            if (poll_id == "") {
                response.sendRedirect("dne.jsp");
            }

            // get poll info
            String select_pollinfo_sql = "SELECT * FROM polls WHERE poll_id=?";
            PreparedStatement select_poll = conn.prepareStatement(select_pollinfo_sql);
            select_poll.setString(1, poll_id);
            ResultSet rs2 = select_poll.executeQuery();
            while (rs2.next()) {
                 name = rs2.getString("name");
                 question = rs2.getString("question");
            }

            // get choices
            String select_pollchoices_sql = "SELECT * FROM choices WHERE poll_id=?";
            PreparedStatement select_choices = conn.prepareStatement(select_pollchoices_sql);
            select_choices.setString(1,poll_id);
            ResultSet rs3 = select_choices.executeQuery();
            while (rs3.next()) {
                int id = rs3.getInt("choice_id");
                String text = rs3.getString("name");
                String description = rs3.getString("description");
                Choice choice = new Choice(id,text,description);
                choices.add(choice);
            }
            session.setAttribute("pollId",poll_id);
            session.setAttribute("pollName",name);
            session.setAttribute("pollQ",question);
            session.setAttribute("choices",choices);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        response.sendRedirect("VotePoll.jsp");
    }
}
