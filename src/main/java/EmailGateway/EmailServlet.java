package EmailGateway;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "EmailServlet", value = "/EmailServlet")
public class EmailServlet extends HttpServlet {

    private String host;
    private String port;
    private String user;
    private String pass;

    public void init() {
        ServletContext context = getServletContext();
        host = context.getInitParameter("host");
        port = context.getInitParameter("port");
        user = context.getInitParameter("user");
        pass = context.getInitParameter("pass");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        String type = (String) session.getAttribute("r_type");
        String email = request.getParameter("email");
        String fname;
        String lname;
        String html;

        if (type.equals("signup")) {
            fname = request.getParameter("fName");
            lname = request.getParameter("lName");
            String token = (String) session.getAttribute("s_token");
            html = "<html><body>Greetings "+fname+" "+lname+",<br>Thanks for signing up to the poll service. Please verify your account by clicking <a href=\"http://localhost:8980/A3_war/set_pass.jsp?type=signup&token="+token+"\">here</a></body></html>";
            try {
                EmailUtil.sendEmail(host, port, user, pass, "soen387temp@gmail.com","SOEN387 - Please validate your account",html);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                getServletContext().getRequestDispatcher("/email_sent.jsp").forward(request,response);
            }
        } else {
            fname = (String) session.getAttribute("fName");
            lname = (String) session.getAttribute("lName");
            String token = (String) session.getAttribute("f_token");
            html = "<html><body>Greetings "+fname+" "+lname+",<br>It seems you have forgotten your password!. Please click <a href=\"http://localhost:8980/A3_war/set_pass.jsp?type=forgot&token="+token+"\">here</a> to re-initialize it.</body></html>";
            try {
                EmailUtil.sendEmail(host, port, user, pass, "soen387temp@gmail.com","SOEN387 - Forgotten Password",html);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                getServletContext().getRequestDispatcher("/email_sent.jsp").forward(request,response);
            }

        }


    }
}
