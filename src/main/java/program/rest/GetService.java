package program.rest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import program.database.PostgresFirstHandler;
import program.database.PostgresSecondHelper;
import program.model.ModelName;
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
import java.util.function.Supplier;

@Path("/get")
public class GetService {
    @GET
    @Path("/modelName")
    @Produces(MediaType.APPLICATION_JSON) //можно добавить количество моделей в запросе
    public Response getModelName() {
        return getData(PostgresFirstHandler.getInstance()::readModelNames);
    }

    @GET
    @Path("/sources")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSources() {
        return getData(PostgresSecondHelper.getInstance()::readSources);
    }

    @GET
    @Path("/consumers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getConsumers() {
        return getData(PostgresSecondHelper.getInstance()::readConsumers);
    }

    @GET
    @Path("/lines")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLines() {
        return getData(PostgresSecondHelper.getInstance()::readLines);
    }

    @GET
    @Path("/model")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getModel(@QueryParam("name") String name) {
        return getData(() -> PostgresFirstHandler.getInstance().readModelByName(name));
    }

    private <T> Response getData(Supplier<T> dataSupplier) {
        T data = dataSupplier.get();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String jsonString = null;

        try {
            jsonString = objectMapper.writeValueAsString(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Response.ok(jsonString).build();
    }
}
