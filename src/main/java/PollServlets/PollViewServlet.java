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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "PollViewServlet", value = "/PollViewServlet")
public class PollViewServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        Connection conn = DBConnection.getConnection();
        String poll_id = request.getParameter("poll_to_res");
        String pollName = "";
        String pollQuestion = "";
        String pollTime = "";
        List<Integer> votes = new ArrayList<Integer>();
        List<Choice> choices = new ArrayList<Choice>();
        HashMap<String,Integer> res = new HashMap<String, Integer>();

        try {
            // get poll info
            String select_pollinfo_sql = "SELECT * FROM polls WHERE poll_id=?";
            PreparedStatement select_pollinfo = conn.prepareStatement(select_pollinfo_sql);
            select_pollinfo.setString(1,poll_id);
            ResultSet rs = select_pollinfo.executeQuery();
            while (rs.next()) {
                pollName = rs.getString("name");
                pollQuestion = rs.getString("question");
                pollTime = rs.getString("creation_time");
            }
            pollTime = pollTime.replace(':','-');

            // get choices
            String select_pollchoices_sql = "SELECT * FROM choices WHERE poll_id=?";
            PreparedStatement select_stmt = conn.prepareStatement(select_pollchoices_sql);
            select_stmt.setString(1,poll_id);
            ResultSet rs2 = select_stmt.executeQuery();
            while (rs2.next()) {
                int id = rs2.getInt("choice_id");
                //System.out.println(id);
                String text = rs2.getString("name");
                //System.out.println(text);
                String description = rs2.getString("description");
                //System.out.println(description);
                Choice choice = new Choice(id,text,description);
                choices.add(choice);
            }

            // get votes
            String select_votes_sql = "SELECT * FROM vote WHERE poll_id=?";
            PreparedStatement select_votes = conn.prepareStatement(select_votes_sql);
            select_votes.setString(1,poll_id);
            ResultSet rs3 = select_votes.executeQuery();
            while (rs3.next()) {
                int x = rs3.getInt("choice_id");
                votes.add(x);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        for (Choice x : choices) {
            res.put(x.getText(),Collections.frequency(votes,x.getId()));
        }
        session.setAttribute("pollId",poll_id);
        session.setAttribute("pollName",pollName);
        session.setAttribute("pollQuestion",pollQuestion);
        session.setAttribute("results",res);
        request.getRequestDispatcher("view_results.jsp").forward(request, response);
    }
}
