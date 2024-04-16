package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import program.database.PostgresSecondHelper;
import program.model.metainfo.Line;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class TestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter printWriter = resp.getWriter();

        PostgresSecondHelper postgresSecondHelper = PostgresSecondHelper.getInstance();
        ArrayList<Line> list = postgresSecondHelper.readLines();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(list);
            System.out.println(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        printWriter.print(jsonString);
    }
}
