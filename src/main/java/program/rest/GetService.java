package program.rest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import program.database.PostgresHelper;
import program.model.ModelName;
import program.model.graph.Element;
import program.model.graph.Graph;
import program.model.metainfo.Consumer;
import program.model.metainfo.Line;
import program.model.metainfo.Source;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/get")
public class GetService {
    @GET
    @Path("/modelName")
    @Produces(MediaType.APPLICATION_JSON) //можно добавить количество моделей в запросе
    public Response getModelName() {
        PostgresHelper postgresHelper = PostgresHelper.getInstance();
        ModelName modelName = postgresHelper.readModelNames();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = null;
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            jsonString = objectMapper.writeValueAsString(modelName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.ok(jsonString).build();
    }

    @GET
    @Path("/sources")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSources() {
        PostgresHelper postgresHelper = PostgresHelper.getInstance();
        ArrayList<Source> list = postgresHelper.readSources();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String jsonString = null;

        try {
            jsonString = objectMapper.writeValueAsString(list);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Response.ok(jsonString).build();
    }

    @GET
    @Path("/consumers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getConsumers() {
        PostgresHelper postgresHelper = PostgresHelper.getInstance();
        ArrayList<Consumer> list = postgresHelper.readConsumers();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String jsonString = null;

        try {
            jsonString = objectMapper.writeValueAsString(list);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Response.ok(jsonString).build();
    }

    @GET
    @Path("/lines")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLines() {
        PostgresHelper postgresHelper = PostgresHelper.getInstance();
        ArrayList<Line> list = postgresHelper.readLines();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String jsonString = null;

        try {
            jsonString = objectMapper.writeValueAsString(list);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Response.ok(jsonString).build();
    }

    @GET
    @Path("/model")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getModel(@QueryParam("name") String name) {
        PostgresHelper postgresHelper = PostgresHelper.getInstance();
        Graph graph = postgresHelper.readModelByName(name);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String jsonString = null;

        try {
            jsonString = objectMapper.writeValueAsString(graph);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.ok(jsonString).build();
    }
}
