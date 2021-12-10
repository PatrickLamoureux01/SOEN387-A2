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

        String email = request.getParameter("email");
        String token = (String) request.getAttribute("token");
        String res = "";
        String html = "<html><body>Greetings,<br>Thanks for signing up to the poll service. Please verify your account by clicking <a href=\"http://localhost:8980/A3_war/set_pass.jsp?type=signup&token="+token+"\">here</a></body></html>";

        try {
            EmailUtil.sendEmail(host, port, user, pass, "soen387temp@gmail.com","SOEN387 - Please validate your account",html);
            res = "Success";
        } catch (Exception ex) {
            ex.printStackTrace();
            res = "Error: " + ex.getMessage();
        } finally {
            request.setAttribute("Message", res);
            getServletContext().getRequestDispatcher("/email_sent.jsp").forward(request,response);
        }

    }
}
