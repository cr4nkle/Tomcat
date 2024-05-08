package program.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import program.database.PostgresThirdHandler;
import program.logic.solurion.LinearStatement;
import program.logic.solurion.NodeSolution;
import program.model.compressedGraph.Graph;
import program.model.mathStatement.MathStatement;
import program.model.mathStatement.Problem;
import program.model.mathStatement.Solution;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;

import static program.logic.solver.LinearSolver.optimate;

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
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

//    @POST
//    @Path("/test/saveModel")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response testSaveModel(Graph graph) {
////        PostgresThirdHandler postgresThirdHandler = PostgresThirdHandler.getInstance();
////        postgresThirdHandler.insertModel(graph);
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
//        String jsonString;
//        try {
//            jsonString = objectMapper.writeValueAsString(graph);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println(jsonString);
//        return Response.status(Response.Status.OK).build();
//    }

    @POST
    @Path("/calculate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response calculate(Problem problem) {
        LocalTime time1 = LocalTime.now();
        MathStatement mathStatement = LinearStatement.getLinearStatement(problem);
        Solution solution = optimate(mathStatement);
        LocalTime time2 = LocalTime.now();
        Duration duration = Duration.between(time1, time2);
        System.out.println(duration.toMillis() + " миллисекунд");
        return Response.ok(solution).build();
    }
}
