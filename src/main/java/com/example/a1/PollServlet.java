package com.example.a1;

import Classes.DBConnection;
import Classes.Poll;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


@WebServlet(name = "com.example.a1.PollServlet", value = "/PollServlet")
public class PollServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Connection conn = DBConnection.getConnection();
        HttpSession session = request.getSession(true);
        String parameterType = request.getParameter("type");

        //Poll thePoll = (Poll) session.getAttribute("poll");

        switch (parameterType) {
            case "vote_anon":
                List<Poll> pollsToVoteAnon = new ArrayList<Poll>();

                try {
                    String select_polls_sql = "SELECT * FROM polls WHERE status='running'";
                    Statement select_stmt = conn.createStatement();
                    ResultSet rs = select_stmt.executeQuery(select_polls_sql);
                    while (rs.next()) {
                        String id = rs.getString("poll_id");
                        String name = rs.getString("name");
                        String question = rs.getString("question");
                        Poll poll = new Poll(id, name, question);
                        pollsToVoteAnon.add(poll);
                    }
                    session.setAttribute("pollsToVoteAnon",pollsToVoteAnon);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                response.sendRedirect("AccessPollsAnon.jsp");
                return;
            case "vote":
                List<Poll> pollsToVote = new ArrayList<Poll>();

                try {
                    String select_polls_sql = "SELECT * FROM polls WHERE status='running'";
                    Statement select_stmt = conn.createStatement();
                    ResultSet rs = select_stmt.executeQuery(select_polls_sql);
                    while (rs.next()) {
                        String id = rs.getString("poll_id");
                        String name = rs.getString("name");
                        String question = rs.getString("question");
                        Poll poll = new Poll(id, name, question);
                        pollsToVote.add(poll);
                    }
                    session.setAttribute("pollsToVote",pollsToVote);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                response.sendRedirect("AccessPolls.jsp");
                return;
            case "delete":
                List<Poll> pollsToDelete = new ArrayList<Poll>();

                try {
                    String select_polls_sql = "SELECT * FROM polls WHERE createdBy = ? AND status <> 'closed' AND poll_id NOT IN(SELECT poll_id from vote)";
                    PreparedStatement select_polls = conn.prepareStatement(select_polls_sql);
                    select_polls.setInt(1,(int)session.getAttribute("user_id"));
                    ResultSet rs = select_polls.executeQuery();
                    while (rs.next()) {
                        String id = rs.getString("poll_id");
                        String name = rs.getString("name");
                        String question = rs.getString("question");
                        Poll poll = new Poll(id, name, question);
                        pollsToDelete.add(poll);
                    }
                    session.setAttribute("pollsToDelete",pollsToDelete);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                response.sendRedirect("PollDeletion.jsp");
                return;
            case "run":
                List<Poll> runningPolls = new ArrayList<Poll>();
                try {
                    String select_polls_sql = "SELECT * FROM polls WHERE status='created'";
                    Statement select_stmt = conn.createStatement();
                    ResultSet rs = select_stmt.executeQuery(select_polls_sql);
                    while (rs.next()) {
                        String id = rs.getString("poll_id");
                        String name = rs.getString("name");
                        String question = rs.getString("question");
                        Poll poll = new Poll(id, name, question);
                        runningPolls.add(poll);
                    }
                    session.setAttribute("runningPolls",runningPolls);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                response.sendRedirect("RunPoll.jsp");
                return;
            case "close":
                List<Poll> closePolls = new ArrayList<Poll>();
                try {
                    String select_polls_sql = "SELECT * FROM polls WHERE status <> 'closed'";
                    Statement select_stmt = conn.createStatement();
                    ResultSet rs = select_stmt.executeQuery(select_polls_sql);
                    while (rs.next()) {
                        String id = rs.getString("poll_id");
                        String name = rs.getString("name");
                        String question = rs.getString("question");
                        Poll poll = new Poll(id, name, question);
                        closePolls.add(poll);
                    }
                    session.setAttribute("closePolls",closePolls);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                response.sendRedirect("ClosePoll.jsp");
                return;
            case "release":
                List<Poll> releasePolls = new ArrayList<Poll>();
                try {
                    String select_polls_sql = "SELECT * FROM polls WHERE status='running'";
                    Statement select_stmt = conn.createStatement();
                    ResultSet rs = select_stmt.executeQuery(select_polls_sql);
                    while (rs.next()) {
                        String id = rs.getString("poll_id");
                        String name = rs.getString("name");
                        String question = rs.getString("question");
                        Poll poll = new Poll(id, name, question);
                        releasePolls.add(poll);
                    }
                    session.setAttribute("releasePolls",releasePolls);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                response.sendRedirect("ReleasePoll.jsp");
                return;
            case "unrelease":
                List<Poll> unreleasePolls = new ArrayList<Poll>();
                try {
                    String select_polls_sql = "SELECT * FROM polls WHERE status='released'";
                    Statement select_stmt = conn.createStatement();
                    ResultSet rs = select_stmt.executeQuery(select_polls_sql);
                    while (rs.next()) {
                        String id = rs.getString("poll_id");
                        String name = rs.getString("name");
                        String question = rs.getString("question");
                        Poll poll = new Poll(id, name, question);
                        unreleasePolls.add(poll);
                    }
                    session.setAttribute("unreleasePolls",unreleasePolls);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                response.sendRedirect("UnreleasePoll.jsp");
                return;
            case "update":
                List<Poll> updatePolls = new ArrayList<Poll>();
                try {
                    String select_polls_sql = "SELECT * FROM polls WHERE status='running'";
                    Statement select_stmt = conn.createStatement();
                    ResultSet rs = select_stmt.executeQuery(select_polls_sql);
                    while (rs.next()) {
                        String id = rs.getString("poll_id");
                        String name = rs.getString("name");
                        String question = rs.getString("question");
                        Poll poll = new Poll(id, name, question);
                        updatePolls.add(poll);
                    }
                    session.setAttribute("updatePolls",updatePolls);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                response.sendRedirect("UpdatePoll.jsp");
                return;
            case "clear":
                List<Poll> clearPolls = new ArrayList<Poll>();
                try {
                    String select_polls_sql = "SELECT * FROM polls WHERE status='running'";
                    Statement select_stmt = conn.createStatement();
                    ResultSet rs = select_stmt.executeQuery(select_polls_sql);
                    while (rs.next()) {
                        String id = rs.getString("poll_id");
                        String name = rs.getString("name");
                        String question = rs.getString("question");
                        Poll poll = new Poll(id, name, question);
                        clearPolls.add(poll);
                    }
                    session.setAttribute("clearPolls",clearPolls);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                response.sendRedirect("ClearPoll.jsp");
                return;
            case "getpolls":
                List<Poll> getPolls = new ArrayList<Poll>();
                try {
                    String select_polls_sql = "SELECT * FROM polls WHERE createdBy = ?";
                    PreparedStatement select_stmt = conn.prepareStatement(select_polls_sql);
                    select_stmt.setInt(1,(int)session.getAttribute("user_id"));
                    ResultSet rs = select_stmt.executeQuery();
                    while (rs.next()) {
                        String id = rs.getString("poll_id");
                        String name = rs.getString("name");
                        String question = rs.getString("question");
                        String status = rs.getString("status");
                        Poll poll = new Poll(id, name, question,status);
                        getPolls.add(poll);
                    }
                    session.setAttribute("getPolls",getPolls);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                response.sendRedirect("GetListPolls.jsp");
                return;
            case "download":
                List<Poll> downloadPolls = new ArrayList<Poll>();
                try {
                    String select_polls_sql = "SELECT * FROM polls WHERE status='released'";
                    Statement select_stmt = conn.createStatement();
                    ResultSet rs = select_stmt.executeQuery(select_polls_sql);
                    while (rs.next()) {
                        String id = rs.getString("poll_id");
                        String name = rs.getString("name");
                        String question = rs.getString("question");
                        Poll poll = new Poll(id, name, question);
                        downloadPolls.add(poll);
                    }
                    session.setAttribute("downloadPolls",downloadPolls);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                response.sendRedirect("DownloadPoll.jsp");
                return;
            case "viewres":
                List<Poll> resPolls = new ArrayList<Poll>();
                try {
                    String select_polls_sql = "SELECT * FROM polls WHERE status='released'";
                    Statement select_stmt = conn.createStatement();
                    ResultSet rs = select_stmt.executeQuery(select_polls_sql);
                    while (rs.next()) {
                        String id = rs.getString("poll_id");
                        String name = rs.getString("name");
                        String question = rs.getString("question");
                        Poll poll = new Poll(id, name, question);
                        resPolls.add(poll);
                    }
                    session.setAttribute("resPolls",resPolls);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                response.sendRedirect("ViewResults.jsp");
                return;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(true);

        Connection conn = DBConnection.getConnection();

        String name = request.getParameter("pollName");
        String question =  request.getParameter("pollQuestion");
        String choices = request.getParameter("pollChoices");
        String[] choicesTemp = choices.split(",");

        // Generate poll_id
        String alphanum = "0123456789ABCDEFGHJKMNPQRSTVWXYZ";
        Random random = ThreadLocalRandom.current();
        int alphabetLength = alphanum.length();
        char[] chars = new char[10];
        for (int i = 0; i < 10; i++)
            chars[i] = alphanum.charAt(random.nextInt(alphabetLength));
        String poll_id =  String.valueOf(chars);

        try {
            // POLL INSERT
            String insert_poll_sql = "INSERT INTO polls(poll_id,name,question,createdBy) VALUES(?,?,?,?)";
            PreparedStatement insert_poll = conn.prepareStatement(insert_poll_sql);
            insert_poll.setString(1,poll_id);
            insert_poll.setString(2,name);
            insert_poll.setString(3,question);
            insert_poll.setInt(4,(int)session.getAttribute("user_id"));
            insert_poll.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // CHOICES INSERT
        for (String c : choicesTemp) {
            if (c.contains(":")) { // if there is a description
                String[] descTemp = c.split(":");
                try {
                    String insert_choice_sql = "INSERT INTO choices(name,description,poll_id) VALUES(?,?,?)";
                    PreparedStatement insert_choice = conn.prepareStatement(insert_choice_sql);
                    insert_choice.setString(1,descTemp[0]);
                    insert_choice.setString(2,descTemp[1]);
                    insert_choice.setString(3,poll_id);
                    insert_choice.executeUpdate();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                try {
                    String insert_choice_sql = "INSERT INTO choices(name,poll_id) VALUES(?,?)";
                    PreparedStatement insert_choice = conn.prepareStatement(insert_choice_sql);
                    insert_choice.setString(1,c);
                    insert_choice.setString(2,poll_id);
                    insert_choice.executeUpdate();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        request.getRequestDispatcher("manager_index.jsp").forward(request, response);
    }
}
