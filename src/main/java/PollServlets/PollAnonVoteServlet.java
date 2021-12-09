package PollServlets;

import Classes.Choice;
import Classes.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "PollAnonVoteServlet", value = "/PollAnonVoteServlet")
public class PollAnonVoteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        Connection conn = DBConnection.getConnection();
        String tmp = request.getParameter("poll_to_vote");
        String poll_id = tmp.split("-")[0];
        String name = tmp.split("-")[1];
        String question = tmp.split("-")[2];
        List<Choice> choices = new ArrayList<>();

        try {
            // get choices
            String select_pollchoices_sql = "SELECT * FROM choices WHERE poll_id=?";
            PreparedStatement select_stmt = conn.prepareStatement(select_pollchoices_sql);
            select_stmt.setString(1,poll_id);
            ResultSet rs = select_stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("choice_id");
                String text = rs.getString("name");
                String description = rs.getString("description");
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
        response.sendRedirect("VotePollAnon.jsp");
    }
}
