package program.rest;

import program.database.PostgresThirdHandler;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/delete")
public class DeleteService {
    @DELETE
    @Path("/model")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteModel(@QueryParam("name") String name) {
        PostgresThirdHandler postgresThirdHandler = PostgresThirdHandler.getInstance();
        postgresThirdHandler.deleteModel(name);
        return Response.ok("{\"message\": \"Удалено\"}").build();
    }

    @DELETE
    @Path("/node")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteNode(@QueryParam("id") int id) {
        return Response.ok().build();
    }

    @DELETE
    @Path("/edge")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteEdge(@QueryParam("id") int id) {
        return Response.ok().build();
    }

    @DELETE
    @Path("/equipment")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteEquipment(@QueryParam("id") int id) {
        return Response.ok().build();
    }
}
