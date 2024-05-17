package program.servlet.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import program.logic.statement.LinearStatement;
import program.model.mathStatement.MathStatement;
import program.model.mathStatement.Problem;
import program.model.mathStatement.Solution;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;

import static program.logic.solver.LinearSolver.optimate;

//@WebServlet("/api/post/calculate")
public class PostCalculateServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Problem problem = objectMapper.readValue(req.getReader(), Problem.class);
        LocalTime time1 = LocalTime.now();
        MathStatement mathStatement = LinearStatement.getLinearStatement(problem);
        Solution solution = optimate(mathStatement);
        LocalTime time2 = LocalTime.now();
        Duration duration = Duration.between(time1, time2);
        System.out.println(duration.toMillis() + " миллисекунд");

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(HttpServletResponse.SC_OK);
        objectMapper.writeValue(resp.getWriter(), solution);
    }
}

