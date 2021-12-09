package Votes;

import Classes.DBConnection;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Random;

@WebServlet(name = "VoteServlet", value = "/VoteServlet")
public class VoteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        Connection conn = DBConnection.getConnection();

        String answer = request.getParameter("pollChoice");
        String id = (String) session.getAttribute("pollId");

        try {
            // insert vote
            String insert_vote_sql = "INSERT INTO vote(poll_id,choice_id) VALUES(?,?)";
            PreparedStatement insert_vote = conn.prepareStatement(insert_vote_sql);
            insert_vote.setString(1,id);
            insert_vote.setString(2,answer);
            insert_vote.executeUpdate();

            Random generator = new Random();
            int pin = generator.nextInt(1000000);
            session.setAttribute("pin",pin);

            // insert pin
            String insert_pin_sql = "INSERT INTO polluser(user_id,poll_id,pin) VALUES(?,?,?)";
            PreparedStatement insert_pin = conn.prepareStatement(insert_pin_sql);
            insert_pin.setInt(1,(int) session.getAttribute("user_id"));
            insert_pin.setString(2,id);
            insert_pin.setInt(3,pin);
            insert_pin.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        request.getRequestDispatcher("thankyou.jsp").forward(request, response);

    }
}
