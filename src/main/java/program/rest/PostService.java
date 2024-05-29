package program.rest;

import program.database.PostgresThirdHandler;
import program.logic.solver.LinearSolver;
import program.logic.statement.LinearStatement;
import program.logic.statement.NonLinearStatement;
import program.model.graph.Graph;
import program.model.mathStatement.MathStatement;
import program.model.mathStatement.Problem;
import program.model.mathStatement.Solution;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.time.Duration;
import java.time.LocalTime;
import java.util.function.Supplier;

@Path("/post")
public class PostService {

    @POST
    @Path("/save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveModel(Graph graph) {
        ObjectMapper objectMapper = new ObjectMapper();
        String str;
        try {
            str = objectMapper.writeValueAsString(graph);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(str);
        PostgresThirdHandler postgresThirdHandler = PostgresThirdHandler.getInstance();
        int updatedRows = postgresThirdHandler.insertModel(graph);

        if (updatedRows > 0) {
            return Response.ok("{\"message\": \"Сохранено\"}").build();
        } else {
            return Response.ok("{\"message\": \"Проблема\"}").build();
        }
    }

    @POST
    @Path("/calculate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response calculate(@QueryParam("type") String type, Problem problem) {
        LocalTime time1 = LocalTime.now();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsoString = null;

        try {
            if (type.equals("linear")) {
                MathStatement mathStatement = LinearStatement.getLinearStatement(problem);
                LinearSolver solver = LinearSolver.getInstance();
                jsoString = objectMapper.writeValueAsString(solver.optimate(mathStatement));

            } else if (type.equals("nonlinear")) {
                jsoString = objectMapper.writeValueAsString(NonLinearStatement.getSolution(problem));
            }
        } catch (Exception e) {
            new RuntimeException(e);
        }

        LocalTime time2 = LocalTime.now();
        Duration duration = Duration.between(time1, time2);

        System.out.println(duration.toMillis() + " миллисекунд");
        return Response.ok(jsoString).build();
    }
}
