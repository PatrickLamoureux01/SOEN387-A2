package com.example.a1;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "UpdateServlet", value = "/UpdateServlet")
public class UpdateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        Poll thePoll = (Poll) session.getAttribute("poll");

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

        PollBusiness.UpdatePoll(thePoll,name,question,pollChoices);
        request.setAttribute("poll", thePoll);
        session.setAttribute("poll",thePoll);
        request.getRequestDispatcher("manager_index.jsp").forward(request, response);
    }
}
