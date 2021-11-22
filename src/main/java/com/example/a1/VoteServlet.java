package com.example.a1;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.stream.IntStream;

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

            // insert pin



        } catch (Exception ex) {
            ex.printStackTrace();
        }

        request.getRequestDispatcher("thankyou.jsp").forward(request, response);

    }
}
