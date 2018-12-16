package fys;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

@WebServlet(name = "Servlet", urlPatterns = {"/index.html"}) //little bit of a hack. no need for extra url because browsers always look for index.html
public class Servlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            //out.println("<!DOCTYPE html>");
            //out.println("<html>");
            //out.println("<head>");
            //out.println("<link href=\"style/style.css\" type=\"text/css\" rel=\"stylesheet\">");
            //out.println("<title>Servlet myServlet</title>");
            //out.println("</head>");
            //out.println("<body>");
            //out.println("<h1>Servlet myServlet at " + request.getContextPath() + "</h1>");
            //out.println("</body>");
            //out.println("</html>");


            //own stuff
            //  DO NOT FORGET TO MOVE RESOURCE(CSS, IMG) FOLDERS TO WEB DIRECTORY
            out.println("<!DOCTYPE html>");
            out.println("<html lang=\"nl\">");
            out.println("<head>");
            out.println("<title>WiFi in het vliegtuig | Corendon</title>");
            out.println("<meta charset=\"UTF-8\">");
            out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
            out.println("<link href=\"style/style.css\" type=\"text/css\" rel=\"stylesheet\">");
            out.println("<script src=\"scripts/myscripts.js\"></script>");
            out.println("</head>");
            out.println("");
            out.println("<body id=\"loginPage\" onload=\"startTime()\">");
            out.println("<header>");
            out.println("<figure>");
            out.println("<img src=\"img/logo.jpg\" alt=\"Corendon_Logo\">");
            out.println("</figure>");
            out.println("<figure class=\"gradient\">");
            out.println("</figure>");
            out.println("</header>");
            out.println("<article class=\"login\">");
            out.println("<h1>Gratis internet in het vliegtuig</h1>");
            out.println("<p>Log in met uw gegevens:</p>");
            out.println("<form action=\"#\" method=\"post\">");
            out.println("<input type=\"text\" name=\"achternaam\" placeholder=\"Achternaam..\" required autofocus>");
            out.println("<input type=\"text\" name=\"boekingsnummer\" placeholder=\"Boekingsnummer..\" required>");
            out.println("<label>");
            out.println("<input type=\"checkbox\" name=\"check\" value=\"check\" required>");
            out.println("Ik ga akkoord met de <a href=\"doc/Voorwaarden.pdf\" target=\"_blank\">voorwaarden</a> en het <a href=\"doc/Privacybeleid.pdf\" target=\"_blank\">privacybeleid</a>");
            out.println("</label>");
            out.println("<input type=\"submit\" value=\"MAAK VERBINDING\">");
            out.println("</form>");
            out.println("</article>");
            out.println("<article class=\"info\">");
            out.println("<h1 id=\"clock\"></h1>");
            out.println("</article>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        out.println("<script>");
        String userdata = request.getReader().lines().collect(Collectors.joining());
        out.println(String.format("console.log(\"%s\")", userdata));
        out.println("</script>");
        if (userdata.contains("admin")) {
            out.println("<meta http-equiv=\"refresh\" content=\"0; URL='http://nu.nl'\" />"); //redirection example
        }

    }
}