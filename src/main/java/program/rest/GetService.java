package program.rest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import program.database.PostgresSecondHandler;
import program.database.PostgresThirdHandler;
import program.model.locale.ModalConfig;
import program.model.style.StyleRuleList;
import program.utils.Constant;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.util.function.Supplier;

@Path("/get")
public class GetService {
    @GET
    @Path("/modelName")
    @Produces(MediaType.APPLICATION_JSON) //можно добавить количество моделей в запросе
    public Response getModelName() {
        return getData(PostgresThirdHandler.getInstance()::readModelNames);
    }

    @GET
    @Path("/sources")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSources(@QueryParam("type") String type) {
        return getData(() -> PostgresSecondHandler.getInstance().readSources(type));
    }

    @GET
    @Path("/consumers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getConsumers(@QueryParam("type") String type) {
        return getData(() -> PostgresSecondHandler.getInstance().readConsumers(type));
    }

    @GET
    @Path("/lines")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLines(@QueryParam("type") String type) {
        return getData(() -> PostgresSecondHandler.getInstance().readLines(type));
    }

    @GET
    @Path("/model")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getModel(@QueryParam("name") String name) {
        return getData(() -> PostgresThirdHandler.getInstance().readModelByName(name));
    }

    @GET
    @Path("/style")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStyle() {
        return readJson(Constant.STYLE_PATH, StyleRuleList.class);
    }

    @GET
    @Path("/locale")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLocale() {
        return readJson(Constant.RU_LOCALE_PATH, ModalConfig.class);
    }

    private <T> Response readJson(String path, Class<T> valueType) {
        ObjectMapper objectMapper = new ObjectMapper();
        T result;

        try {
            result = objectMapper.readValue(new File(path), valueType);
        } catch (IOException e) {
            // Log the exception instead of printing the stack trace
            // Consider using a logger like SLF4J or Log4j
            System.err.println("Failed to read JSON from " + path + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        return Response.ok(result).build();
    }

    private <T> Response getData(Supplier<T> dataSupplier) {
        T data = dataSupplier.get();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String jsonString;
        try {
            jsonString = objectMapper.writeValueAsString(data);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        return Response.ok(jsonString).build();
    }
}
