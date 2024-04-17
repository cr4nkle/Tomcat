package program.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import program.database.PostgresFirstHandler;
import program.database.PostgresThirdHandler;
import program.logic.calculate.EdgeSolution;
import program.logic.calculate.NodeSolution;
import program.model.graph.Graph;
import program.model.mathStatement.MathStatement;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/post")
public class PostService {

    @POST
    @Path("/saveModel")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveModel(Graph graph) {
        PostgresFirstHandler postgresFirstHandler = PostgresFirstHandler.getInstance();
        boolean isInserted = postgresFirstHandler.insertModel(graph);
        System.out.println(isInserted);
        if (!isInserted) {//тут всегда false почему-то
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        return Response.status(Response.Status.OK).build();
    }
    @POST
    @Path("/test/saveModel")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response testSaveModel(Graph graph) {
        PostgresThirdHandler postgresThirdHandler = PostgresThirdHandler.getInstance();
        postgresThirdHandler.insertModel(graph);

        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Path("/calculate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response calculate(@QueryParam("type") String type, Graph graph) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        EdgeSolution edgeSolution = new EdgeSolution();
        NodeSolution nodeSolution = new NodeSolution();
        String jsonString = null;

        System.out.println(type);
        try {
            MathStatement mathStatement = edgeSolution.getSolution(graph);
//            MathStatement mathStatement = nodeSolution.getSolution(graph);
            jsonString = objectMapper.writeValueAsString(mathStatement);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error processing request").build();
        }

        return Response.ok(jsonString).build();
    }
}
