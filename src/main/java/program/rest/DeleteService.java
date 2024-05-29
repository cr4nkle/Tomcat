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
        if(!name.isEmpty() && name != null){
            return Response.ok("{\"message\": \"Удалено\"}").build();
        }else{
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Параметр name не указан или null!!!")
                    .build();
        }

    }
}
