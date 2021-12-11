package TestSuite;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "RunTestsServlet", value = "/RunTestsServlet")
public class RunTestsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        TestSuite suite = new TestSuite();

        suite.checkUser("steviewonder@gmail.com");
        suite.checkUser("jeremyclarkson@gmail.com");
        suite.checkAuthentication("rogerwaters@gmail.com","aaa");
        suite.checkAuthentication("rogerwaters@gmail.com","abcdef");

        request.getRequestDispatcher("index.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
