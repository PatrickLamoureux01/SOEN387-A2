package com.example.a1;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;



import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.System.out;



@WebServlet(name = "com.example.a1.PollServlet", value = "/PollServlet")
public class PollServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Connection conn = DBConnection.getConnection();
        HttpSession session = request.getSession(true);
        String parameterType = request.getParameter("type");

        //Poll thePoll = (Poll) session.getAttribute("poll");

        switch (parameterType) {
            case "delete":
                List<Poll> pollsToDelete = new ArrayList<Poll>();

                try {
                    String select_polls_sql = "SELECT * FROM polls WHERE createdBy = ? AND poll_id NOT IN(SELECT poll_id from vote)";
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
                    session.setAttribute("polls",pollsToDelete);
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
                //PollBusiness.ClosePoll(thePoll);
                request.getRequestDispatcher("manager_index.jsp").forward(request, response);
                break;
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
            case "download":
                //PollBusiness.DownloadPollDetails(thePoll);
                request.getRequestDispatcher("manager_index.jsp").forward(request, response);
                break;

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
        //ArrayList<Choice> pollChoices = new ArrayList<>();

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
                    PreparedStatement insert_choice = conn.prepareStatement(insert_choice_sql,Statement.RETURN_GENERATED_KEYS);
                    insert_choice.setString(1,descTemp[0]);
                    insert_choice.setString(2,descTemp[1]);
                    insert_choice.setString(3,poll_id);
                    insert_choice.executeUpdate();
                    /*ResultSet generatedId = insert_choice.getGeneratedKeys();
                    if (generatedId.next()) {
                        String insert_pollChoice_sql = "INSERT INTO pollchoice(choice_id,poll_id) VALUES(?,?)";
                        PreparedStatement insert_pollchoice = conn.prepareStatement(insert_pollChoice_sql);
                        insert_pollchoice.setInt(1,generatedId.getInt(1));
                        insert_pollchoice.setString(2,poll_id);
                        insert_pollchoice.executeUpdate();
                    }*/

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                try {
                    String insert_choice_sql = "INSERT INTO choices(name,poll_id) VALUES(?,?)";
                    PreparedStatement insert_choice = conn.prepareStatement(insert_choice_sql,Statement.RETURN_GENERATED_KEYS);
                    insert_choice.setString(1,c);
                    insert_choice.setString(2,poll_id);
                    insert_choice.executeUpdate();
                    /*ResultSet generatedId = insert_choice.getGeneratedKeys();
                    if (generatedId.next()) {
                        String insert_pollChoice_sql = "INSERT INTO pollchoice(choice_id,poll_id) VALUES(?,?)";
                        PreparedStatement insert_pollchoice = conn.prepareStatement(insert_pollChoice_sql);
                        insert_pollchoice.setInt(1,generatedId.getInt(1));
                        insert_pollchoice.setString(2,poll_id);
                        insert_pollchoice.executeUpdate();
                    }*/
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        Poll newPoll = new Poll();
        //PollBusiness.CreatePoll(newPoll,name,question,pollChoices);
        request.setAttribute("poll", newPoll);
        session.setAttribute("poll",newPoll);
        request.getRequestDispatcher("manager_index.jsp").forward(request, response);
    }
}
