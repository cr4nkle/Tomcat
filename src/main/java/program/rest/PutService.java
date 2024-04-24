package program.rest;

import program.model.graph.Edge;
import program.model.graph.Equipment;
import program.model.graph.Graph;
import program.model.graph.Node;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/put")
public class PutService {
    @PUT
    @Path("/model")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateModel(@QueryParam("name") String name, Graph graph) {
        return Response.ok().build();
    }

    @PUT
    @Path("/node")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateNode(@QueryParam("id") String name, Node node) {
        return Response.ok().build();
    }

    @PUT
    @Path("/edge")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEdge(@QueryParam("id") String name, Edge edge) {
        return Response.ok().build();
    }

    @PUT
    @Path("/equipment")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEquipment(@QueryParam("id") String name, Equipment equipment) {
        return Response.ok().build();
    }
}
