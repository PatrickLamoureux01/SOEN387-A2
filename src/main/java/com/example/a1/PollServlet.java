package com.example.a1;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;



import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

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

        String name = request.getParameter("pollName");
        String question =  request.getParameter("pollQuestion");
        String choices = request.getParameter("pollChoices");
        String[] choicesTemp = choices.split(",");
        ArrayList<Choice> pollChoices = new ArrayList<>();

        for (String c : choicesTemp) {
            if (c.contains(":")) { // if there is a description
                String[] descTemp = c.split(":");
                Choice ch = new Choice(descTemp[0],descTemp[1]);
                pollChoices.add(ch);
            } else {
                Choice ch = new Choice(c);
                pollChoices.add(ch);
            }
        }
        Poll newPoll = new Poll();
        PollBusiness.CreatePoll(newPoll,name,question,pollChoices);
        request.setAttribute("poll", newPoll);
        session.setAttribute("poll",newPoll);
        request.getRequestDispatcher("manager_index.jsp").forward(request, response);
    }

}
