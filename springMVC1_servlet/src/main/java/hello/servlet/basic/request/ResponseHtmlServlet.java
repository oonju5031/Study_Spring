package hello.servlet.basic.request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "responseHtmlServlet", urlPatterns = "/response-html")
public class ResponseHtmlServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Content-Type: text/html; charset = utf-8
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");

        // Servlet으로 HTML을 작성할 땐 아래와 같이 직접 작성하여야 한다.
        PrintWriter writer = response.getWriter();
        writer.println("<html>");
        writer.println("<body>");
        writer.println("    <div>HTML 테스트</div>");
        writer.println("</body>");
        writer.println("</html>");
    }
}
