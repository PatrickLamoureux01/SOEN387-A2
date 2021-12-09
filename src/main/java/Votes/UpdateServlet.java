package Votes;

import Classes.DBConnection;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet(name = "UpdateServlet", value = "/UpdateServlet")
public class UpdateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(true);

        Connection conn = DBConnection.getConnection();
        String name = request.getParameter("pollName");
        String question =  request.getParameter("pollQuestion");
        String id = request.getParameter("pollId");
        String choices = request.getParameter("pollChoices");
        String[] choicesTemp = choices.split(",");


        try {
            // POLL UPDATE
            String update_poll_sql = "UPDATE polls SET name=?, question=? WHERE poll_id = ?";
            PreparedStatement update_poll = conn.prepareStatement(update_poll_sql);
            update_poll.setString(1,name);
            update_poll.setString(2,question);
            update_poll.setString(3,id);
            update_poll.executeUpdate();

            // wipe out choices
            String delete_choices_sql = "DELETE FROM choices WHERE poll_id = ?";
            PreparedStatement delete_choices = conn.prepareStatement(delete_choices_sql);
            delete_choices.setString(1,id);
            delete_choices.executeUpdate();

            // re-create choices
            for (String c : choicesTemp) {
                if (c.contains(":")) { // if there is a description
                    String[] descTemp = c.split(":");
                    try {
                        String insert_choice_sql = "INSERT INTO choices(name,description,poll_id) VALUES(?,?,?)";
                        PreparedStatement insert_choice = conn.prepareStatement(insert_choice_sql);
                        insert_choice.setString(1,descTemp[0]);
                        insert_choice.setString(2,descTemp[1]);
                        insert_choice.setString(3,id);
                        insert_choice.executeUpdate();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    try {
                        String insert_choice_sql = "INSERT INTO choices(name,poll_id) VALUES(?,?)";
                        PreparedStatement insert_choice = conn.prepareStatement(insert_choice_sql);
                        insert_choice.setString(1,c);
                        insert_choice.setString(2,id);
                        insert_choice.executeUpdate();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        request.getRequestDispatcher("manager_index.jsp").forward(request, response);
    }
}
