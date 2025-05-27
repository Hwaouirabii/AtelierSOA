package webservices;

import entities.RendezVous;
import metiers.RendezVousBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/rendezvous")
public class RendezVousRessources {

    private RendezVousBusiness business = new RendezVousBusiness();

    // Get all rendez-vous
    @GET
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response.ok(business.getListeRendezVous())
                .header("Access-Control-Allow-Origin", "*")
                .build();
    }

    // Get rendez-vous by ID
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") int id) {
        RendezVous rv = business.getRendezVousById(id);
        if (rv != null) {
            return Response.ok(rv)
                    .header("Access-Control-Allow-Origin", "*")
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .header("Access-Control-Allow-Origin", "*")
                    .entity("Rendez-vous not found")
                    .build();
        }
    }

    // Get rendez-vous by logement reference
    @GET
    @Path("/logement/{reference}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByLogementReference(@PathParam("reference") int reference) {
        List<RendezVous> list = business.getListeRendezVousByLogementReference(reference);
        return Response.ok(list)
                .header("Access-Control-Allow-Origin", "*")
                .build();
    }

    // Add new rendez-vous
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addRendezVous(RendezVous rv) {
        boolean added = business.addRendezVous(rv);
        if (added) {
            return Response.status(Response.Status.CREATED)
                    .header("Access-Control-Allow-Origin", "*")
                    .entity("Rendez-vous added successfully")
                    .build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .header("Access-Control-Allow-Origin", "*")
                    .entity("Failed to add rendez-vous (invalid logement reference)")
                    .build();
        }
    }

    // Update rendez-vous by ID
    @PUT
    @Path("/update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateRendezVous(@PathParam("id") int id, RendezVous rv) {
        boolean updated = business.updateRendezVous(id, rv);
        if (updated) {
            return Response.ok("Rendez-vous updated successfully")
                    .header("Access-Control-Allow-Origin", "*")
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .header("Access-Control-Allow-Origin", "*")
                    .entity("Failed to update rendez-vous")
                    .build();
        }
    }

    // Delete rendez-vous by ID
    @DELETE
    @Path("/delete/{id}")
    public Response deleteRendezVous(@PathParam("id") int id) {
        boolean deleted = business.deleteRendezVous(id);
        if (deleted) {
            return Response.ok("Rendez-vous deleted successfully")
                    .header("Access-Control-Allow-Origin", "*")
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .header("Access-Control-Allow-Origin", "*")
                    .entity("Rendez-vous not found")
                    .build();
        }
    }
}
