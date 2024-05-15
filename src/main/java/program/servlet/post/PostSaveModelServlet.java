package program.servlet.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import program.database.PostgresThirdHandler;
import program.model.compressedGraph.Graph;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/post/saveModel")
public class PostSaveModelServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Graph graph = objectMapper.readValue(req.getReader(), Graph.class);

        PostgresThirdHandler postgresThirdHandler = PostgresThirdHandler.getInstance();
        int updatedRows = postgresThirdHandler.insertModel(graph);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        if (updatedRows > 0) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("{\"message\": \"Модель успешно сохранена\"}");
        } else {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"message\": \"Ошибка сохранения модели\"}");
        }
    }
}

