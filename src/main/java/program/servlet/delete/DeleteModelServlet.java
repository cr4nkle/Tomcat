package program.servlet.delete;

import program.database.PostgresThirdHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet("/delete/model")
public class DeleteModelServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        if (name == null || name.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Name parameter is required");
            return;
        }

        PostgresThirdHandler postgresThirdHandler = PostgresThirdHandler.getInstance();
        int isDeleted = postgresThirdHandler.deleteModel(name);

        if (isDeleted > 0) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"message\": \"Удалено\"}");
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Model not found");
        }
    }
}

