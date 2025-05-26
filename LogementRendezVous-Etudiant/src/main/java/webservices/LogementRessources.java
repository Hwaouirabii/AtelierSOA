package webservices;

import metiers.LogementBusiness;
import entities.Logement;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/logement")
public class LogementRessources {

    private LogementBusiness help = new LogementBusiness();

    // Get all logements
    @GET
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response
                .status(Response.Status.OK)
                .header("Access-Control-Allow-Origin", "*")
                .entity(help.getLogements())
                .build();
    }

    // Get logement by reference
    @GET
    @Path("/{reference}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByReference(@PathParam("reference") int reference) {
        Logement logement = help.getLogementsByReference(reference);
        if (logement != null) {
            return Response.ok(logement).header("Access-Control-Allow-Origin", "*").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .header("Access-Control-Allow-Origin", "*")
                    .entity("Logement not found").build();
        }
    }

    // Get logements by delegation
    @GET
    @Path("/delegation/{delegation}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByDelegation(@PathParam("delegation") String delegation) {
        List<Logement> logements = help.getLogementsByDeleguation(delegation);
        return Response.ok(logements).header("Access-Control-Allow-Origin", "*").build();
    }

    // Add a new logement
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addLogement(Logement logement) {
        boolean added = help.addLogement(logement);
        if (added) {
            return Response.status(Response.Status.CREATED)
                    .header("Access-Control-Allow-Origin", "*")
                    .entity("Logement added successfully").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .header("Access-Control-Allow-Origin", "*")
                    .entity("Failed to add logement").build();
        }
    }

    // Update existing logement
    @PUT
    @Path("/update/{reference}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateLogement(@PathParam("reference") int reference, Logement logement) {
        boolean updated = help.updateLogement(reference, logement);
        if (updated) {
            return Response.ok("Logement updated")
                    .header("Access-Control-Allow-Origin", "*").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .header("Access-Control-Allow-Origin", "*")
                    .entity("Logement not found").build();
        }
    }

    // Delete logement
    @DELETE
    @Path("/delete/{reference}")
    public Response deleteLogement(@PathParam("reference") int reference) {
        boolean deleted = help.deleteLogement(reference);
        if (deleted) {
            return Response.ok("Logement deleted")
                    .header("Access-Control-Allow-Origin", "*").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .header("Access-Control-Allow-Origin", "*")
                    .entity("Logement not found").build();
        }
    }
}
