package program.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import program.database.PostgresThirdHandler;
import program.logic.solurion.NodeSolution;
import program.model.compressedGraph.Graph;
import program.model.mathStatement.MathStatement;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/post")
public class PostService {

    @POST
    @Path("/saveModel")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveModel(Graph graph) {
        PostgresThirdHandler postgresThirdHandler = PostgresThirdHandler.getInstance();
        int updatedRows = postgresThirdHandler.insertModel(graph);

        if (!(updatedRows > 0)) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error processing request").build();
        } else {
            return Response.status(Response.Status.OK).build();
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
    public Response calculate(program.model.graph.Graph graph) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
//        EdgeSolution edgeSolution = new EdgeSolution();
        NodeSolution nodeSolution = new NodeSolution();
        String jsonString = null;
//        try {
////            MathStatement mathStatement = edgeSolution.getSolution(graph);
//
////            MathStatement mathStatement = nodeSolution.getSolution(graph);
////            jsonString = objectMapper.writeValueAsString(mathStatement);
////            System.out.println(jsonString);
////            Graph graph = objectMapper.readValue(query, Graph.class);
////        } catch (JsonProcessingException e) {
////            e.printStackTrace();
////            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error processing request").build();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        return Response.ok(jsonString).build();
    }
}
