package program.servlet.get;

import program.utils.Constant;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@WebServlet("/api/get/locale")
public class GetLocaleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String jsonString = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(Constant.RU_LOCALE_PATH));
            StringBuilder content = new StringBuilder();
            String line = reader.readLine();
            while (line!= null) {
                content.append(line).append(System.lineSeparator());
                line = reader.readLine();
            }
            reader.close();
            jsonString = content.toString();
        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonString);
    }
}

