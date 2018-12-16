package fys;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@WebServlet(name = "Servlet", urlPatterns = {"/index.html"})
//little bit of a hack. no need for extra url because browsers always look for index.html
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
            out.println("<title>WiFi in het vliegtuig dsadsadsada| Corendon</title>");
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

    String firstName = "null";
    String lastName = "null";
    String ticketNumber = "";
    String ticketStatus = "";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String pattern = "(.*)=(.*)&(.*)=(.*)";
        String userdata = request.getReader().lines().collect(Collectors.joining());

        //json stuff
        JSONParser parser = new JSONParser();

        //create search pattern object
        Pattern r = Pattern.compile(pattern);

        //create matcher object
        Matcher m = r.matcher(userdata);


        if (m.find()) {
            //ifirstName = m.group(2);
            lastName = m.group(2); //4
            ticketNumber = m.group(4); //6

        } else {
            System.out.println("NO MATCH");
        }
        //verify ticket
        String payload = Payloadbuilder("Ticket");
        String APIresult = RequestAPI(payload, "Ticket");
        System.out.println(APIresult);

        try{
            Object obj = parser.parse(APIresult);
            JSONObject jsonobj = (JSONObject) obj;
            System.out.println(obj);

            ticketStatus = (String)jsonobj.get("ticketStatus");



        }catch(ParseException pe){

            System.out.println("position: " + pe.getPosition());
            System.out.println(pe);
        }


        // Laat ik erin als voorbeeld
        out.println("<script>");
        out.println(String.format("console.log(\"%s\")", ticketStatus));
        out.println("</script>");


    }

    public String Payloadbuilder(String choice) {
        String payload = "";

        if (choice.equals("Ticket")) {
            payload = "{\n" +
                    "\"function\": \"Check\"," +
                    "\"teamId\": \"IC106-5\"," +
                    "\"teamKey\": \"61d3f4b1959805acf5dafcd3dca7f7a6\"," +
                    "\"requestId\" : \"1\"," +
                    String.format("\"firstName\" : \"%s\",", firstName) +
                    String.format("\"lastName\" : \"%s\",", lastName) +
                    String.format("\"ticketNumber\" : \"%s\"", ticketNumber) +
                    "}";
        } else if (choice.equals("Passenger")) {

            payload = "{\n" +
                    "\"function\": \"List\",\n" +
                    "\"teamId\": \"IC106-5\",\n" +
                    "\"teamKey\": \"61d3f4b1959805acf5dafcd3dca7f7a6\",\n" +
                    "\"requestId\" : \"1\"\n" +
                    "}";
        } else {
            //flight payload
            payload = "{" +
                    "\"function\": \"List\"," +
                    "\"teamId\": \"IC106-5\"," +
                    "\"teamKey\": \"61d3f4b1959805acf5dafcd3dca7f7a6\"," +
                    "\"requestId\" : \"1\"" +
                    "}";
        }

    return payload;
}

    protected static String RequestAPI(String payload, String function) throws UnsupportedOperationException, IOException {
        String result = null;
        System.out.println(payload);
        StringEntity entity = new StringEntity(payload,
                ContentType.APPLICATION_FORM_URLENCODED);
        System.out.println(payload);
        HttpClient httpClient = HttpClientBuilder.create().build();
        String choice = function;
        String url = String.format("http://fys.securidoc.nl:11111/%s", "Ticket");
        HttpPost dbrequest = new HttpPost(url);
        dbrequest.setEntity(entity);

        HttpResponse response = httpClient.execute(dbrequest);
        System.out.println(response.getStatusLine().getStatusCode());

        HttpEntity entityresponse = response.getEntity();
        if (entityresponse != null) {
            InputStream instream = entityresponse.getContent();
            result = convertStreamToString(instream);
        }
        return result;
    }

    private static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}

