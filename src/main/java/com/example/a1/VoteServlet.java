package com.example.a1;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
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
        Poll p = (Poll) session.getAttribute("poll");

        String answer = request.getParameter("pollChoice");

        ArrayList<Choice> cs = p.getChoices();
        int index = IntStream.range(0, cs.size())
                .filter(i -> cs.get(i).text.equals(answer))
                .findFirst()
                .orElse(-1);

        p.upvote(index);
        request.getRequestDispatcher("thankyou.jsp").forward(request, response);

    }
}
