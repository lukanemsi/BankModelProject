package ge.ufc.restapi.services;

import ge.ufc.restapi.requestresponsemodels.BalanceRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/operations")
public interface OperationServiceI {

  @PUT
  @Path("/fillBalance")
  @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  @Produces(MediaType.TEXT_PLAIN)
  Response fillBalance(BalanceRequest request);
  @PUT
  @Path("/transaction/{cardNumber}")
  @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  @Produces(MediaType.TEXT_PLAIN)
  Response transaction(BalanceRequest request,@PathParam("cardNumber") long cardNumber);

  @GET
  @Path("/checkBalance/{personId}")
  @Produces(MediaType.APPLICATION_JSON)
  Response balance(@PathParam("personId") int personId);

  @GET
  @Path("/history/{personId}")
  @Produces(MediaType.APPLICATION_JSON)
  Response transactionHistory(@PathParam("personId") int personId);

}
