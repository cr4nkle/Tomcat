package program.rest;

import program.database.PostgresThirdHandler;
import program.logic.solver.LinearSolver;
import program.logic.statement.LinearStatement;
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

@Path("/post")
public class PostService {

    @POST
    @Path("/saveModel")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveModel(Graph graph) {
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
    public Response calculate(Problem problem){
        
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(problem);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(jsonString);

        LocalTime time1 = LocalTime.now();

        MathStatement mathStatement = LinearStatement.getLinearStatement(problem);
        LinearSolver solver = LinearSolver.getInstance();
        Solution solution = solver.optimate(mathStatement);

        try {
            jsonString = objectMapper.writeValueAsString(solution);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(jsonString);

        LocalTime time2 = LocalTime.now();
        Duration duration = Duration.between(time1, time2);

        System.out.println(duration.toMillis() + " миллисекунд");
        return Response.ok(solution).build();
    }
}
