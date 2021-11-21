package com.example.a1;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "UserLoginServlet", value = "/UserLoginServlet")
public class UserLoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        String email = request.getParameter("email");
        String passcode = request.getParameter("passcode");
        Connection conn = DBConnection.getConnection();

        Statement stmt = null;
        try {
            stmt = DBConnection.conn.createStatement();
            // PREPARED STATEMENTS ARE BROKEN AND DO NOT WORK.
            String sql = "SELECT user_id FROM users WHERE email='" + email+"' AND password='" + passcode + "'";
            ResultSet rs = stmt.executeQuery(sql);

            if (!rs.isBeforeFirst() ) {
                session.setAttribute("invalid", "true");
                RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
                dispatcher.forward(request, response);
            } else {
                response.sendRedirect("manager_index.jsp");
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
/*
        if (!passcode.equals("a")) {
            session.setAttribute("invalid", "true");
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect("manager_index.jsp");
        }*/
    }
}
