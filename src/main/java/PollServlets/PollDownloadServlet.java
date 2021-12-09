package PollServlets;

import Classes.Choice;
import Classes.DBConnection;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@WebServlet(name = "PollDownloadServlet", value = "/PollDownloadServlet")
public class PollDownloadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        Connection conn = DBConnection.getConnection();
        String poll_id = request.getParameter("poll_to_download");
        String format = request.getParameter("format");
        String pollName = "";
        String pollQuestion = "";
        String pollTime = "";
        List<Integer> votes = new ArrayList<Integer>();
        List<Choice> choices = new ArrayList<Choice>();

        try {
            // get poll info
            String select_pollinfo_sql = "SELECT * FROM polls WHERE poll_id=?";
            PreparedStatement select_pollinfo = conn.prepareStatement(select_pollinfo_sql);
            select_pollinfo.setString(1,poll_id);
            ResultSet rs = select_pollinfo.executeQuery();
            while (rs.next()) {
                pollName = rs.getString("name");
                pollQuestion = rs.getString("question");
                pollTime = rs.getString("creation_time");
            }
            pollTime = pollTime.replace(':','-');

            // get choices
            String select_pollchoices_sql = "SELECT * FROM choices WHERE poll_id=?";
            PreparedStatement select_stmt = conn.prepareStatement(select_pollchoices_sql);
            select_stmt.setString(1,poll_id);
            ResultSet rs2 = select_stmt.executeQuery();
            while (rs2.next()) {
                int id = rs2.getInt("choice_id");
                //System.out.println(id);
                String text = rs2.getString("name");
                //System.out.println(text);
                String description = rs2.getString("description");
                //System.out.println(description);
                Choice choice = new Choice(id,text,description);
                choices.add(choice);
            }

            // get votes
            String select_votes_sql = "SELECT * FROM vote WHERE poll_id=?";
            PreparedStatement select_votes = conn.prepareStatement(select_votes_sql);
            select_votes.setString(1,poll_id);
            ResultSet rs3 = select_votes.executeQuery();
            while (rs3.next()) {
                int x = rs3.getInt("choice_id");
                votes.add(x);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (format.equals("TXT")) {

            String targetPath = "D:\\User\\OneDrive - Concordia University - Canada\\School\\Concordia\\Semester_6\\SOEN387\\Assignments\\A2\\";
            String filename = pollName+"-"+pollTime+".txt";
            File newFile = new File(targetPath,filename);
            try {
                PrintWriter output = new PrintWriter(newFile);

                output.write("Poll name: " + pollName + "\n");
                output.write("Poll Question: " + pollQuestion + "\n");
                for (Choice x : choices) {
                    output.write(x.getText() + ": " + Collections.frequency(votes,x.getId()) + "\n");
                }
                output.flush();
                output.close();

            } catch (Exception ex) {
                System.out.println(ex);
            }

        } else if (format.equals("XML")) {

            String targetPath = "D:\\User\\OneDrive - Concordia University - Canada\\School\\Concordia\\Semester_6\\SOEN387\\Assignments\\A2\\";
            String filename = pollName+"-"+pollTime+".xml";
            File newFile = new File(targetPath,filename);
            try {
                PrintWriter output = new PrintWriter(newFile);
                output.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
                output.write("<POLL>\n");
                output.write("\t<POLL INFO>\n");
                output.write("\t\t<POLL NAME>\n");
                output.write("\t\t\t"+pollName+"\n");
                output.write("\t\t</POLL NAME>\n");
                output.write("\t\t<POLL NAME>\n");
                output.write("\t\t\t"+pollQuestion+"\n");
                output.write("\t\t</POLL NAME>\n");
                output.write("\t</POLL INFO>\n");
                output.write("\t<VOTES>\n");
                for (Choice x : choices) {
                    output.write("\t\t<CHOICE>\n");
                    output.write("\t\t\t"+x.getText()+"\n");
                    output.write("\t\t</CHOICE>\n");
                    output.write("\t\t<COUNT>\n");
                    output.write("\t\t\t"+Collections.frequency(votes,x.getId())+"\n");
                    output.write("\t\t</COUNT>\n");
                }
                output.write("\t</VOTES>\n");
                output.write("</POLL>\n");

                output.flush();
                output.close();

            } catch (Exception ex) {
                System.out.println(ex);
            }

        } else {

            String targetPath = "D:\\User\\OneDrive - Concordia University - Canada\\School\\Concordia\\Semester_6\\SOEN387\\Assignments\\A2\\";
            String filename = pollName+"-"+pollTime+".json";
            File newFile = new File(targetPath,filename);
            try {
                PrintWriter output = new PrintWriter(newFile);
                output.write("{\n");
                output.write("\t\"poll_info\": {\n");
                output.write("\t\t\"name\": \""+pollName + "\"\n");
                output.write("\t\t\"question\": \""+pollQuestion + "\"\n");
                output.write("\t}\n");
                output.write("\t\"votes\": {\n");
                for (Choice x : choices) {
                    output.write("\t\t\""+x.getText()+"\": \""+Collections.frequency(votes,x.getId()) + "\"\n");
                }
                output.write("\t}\n");
                output.write("}\n");

                output.flush();
                output.close();

            } catch (Exception ex) {
                System.out.println(ex);
            }

        }
        request.getRequestDispatcher("manager_index.jsp").forward(request, response);
    }
}
