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

        HttpSession session = request.getSession(true);
        String parameterType = request.getParameter("type");

        Poll thePoll = (Poll) session.getAttribute("poll");

        switch (parameterType) {
            case "run":
                PollBusiness.RunPoll(thePoll);
                request.getRequestDispatcher("manager_index.jsp").forward(request, response);
                break;
            case "close":
                PollBusiness.ClosePoll(thePoll);
                request.getRequestDispatcher("manager_index.jsp").forward(request, response);
                break;
            case "release":
                PollBusiness.ReleasePoll(thePoll);
                request.getRequestDispatcher("manager_index.jsp").forward(request, response);
                break;
            case "unrelease":
                PollBusiness.UnreleasePoll(thePoll);
                request.getRequestDispatcher("manager_index.jsp").forward(request, response);
                break;
            case "update":
                request.getRequestDispatcher("update_poll.jsp").forward(request, response);
                break;
            case "clear":
                PollBusiness.ClearPoll(thePoll);
                request.getRequestDispatcher("manager_index.jsp").forward(request, response);
                break;
            case "download":
                PollBusiness.DownloadPollDetails(thePoll);
                request.getRequestDispatcher("manager_index.jsp").forward(request, response);
                break;

        }
        //System.out.println(thePoll.getStatus());
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
            String insert_poll_sql = "INSERT INTO polls(poll_id,name,question) VALUES(?,?,?)";
            PreparedStatement insert_poll = conn.prepareStatement(insert_poll_sql);
            insert_poll.setString(1,poll_id);
            insert_poll.setString(2,name);
            insert_poll.setString(3,question);
            insert_poll.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // CHOICES INSERT
        for (String c : choicesTemp) {
            if (c.contains(":")) { // if there is a description
                String[] descTemp = c.split(":");
                try {
                    String insert_choice_sql = "INSERT INTO choices(name,description) VALUES(?,?)";
                    PreparedStatement insert_choice = conn.prepareStatement(insert_choice_sql,Statement.RETURN_GENERATED_KEYS);
                    insert_choice.setString(1,descTemp[0]);
                    insert_choice.setString(2,descTemp[1]);
                    insert_choice.executeUpdate();
                    ResultSet generatedId = insert_choice.getGeneratedKeys();
                    if (generatedId.next()) {
                        String insert_pollChoice_sql = "INSERT INTO pollchoice(choice_id,poll_id) VALUES(?,?)";
                        PreparedStatement insert_pollchoice = conn.prepareStatement(insert_pollChoice_sql);
                        insert_pollchoice.setInt(1,generatedId.getInt(1));
                        insert_pollchoice.setString(2,poll_id);
                        insert_pollchoice.executeUpdate();
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                try {
                    String insert_choice_sql = "INSERT INTO choices(name) VALUES(?)";
                    PreparedStatement insert_choice = conn.prepareStatement(insert_choice_sql,Statement.RETURN_GENERATED_KEYS);
                    insert_choice.setString(1,c);
                    insert_choice.executeUpdate();
                    ResultSet generatedId = insert_choice.getGeneratedKeys();
                    if (generatedId.next()) {
                        String insert_pollChoice_sql = "INSERT INTO pollchoice(choice_id,poll_id) VALUES(?,?)";
                        PreparedStatement insert_pollchoice = conn.prepareStatement(insert_pollChoice_sql);
                        insert_pollchoice.setInt(1,generatedId.getInt(1));
                        insert_pollchoice.setString(2,poll_id);
                        insert_pollchoice.executeUpdate();
                    }
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
