package com.example.a1;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet(name = "ManagerServlet", value = "/ManagerServlet")
public class ManagerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(true);

        String passcode = request.getParameter("passcode");

        Connection conn = DBConnection.getConnection();

        Statement stmt = null;
        try {
            stmt = conn.createStatement();


            String sql = "SELECT user_id, username FROM user_details";
            ResultSet rs = stmt.executeQuery(sql);
// Extract data from result set
            while (rs.next()) {
//Retrieve by column name
                int id = rs.getInt("user_id");
                String first = rs.getString("username");

                System.out.println("ID: " + id);
                System.out.println("FName: " + first);
// User your data here (i.e. print or store in Array list)
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        if (!passcode.equals("a")) {
            session.setAttribute("invalid", "true");
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect("manager_index.jsp");
        }
    }
}
