package ge.ufc.restapi.services;

import ge.ufc.restapi.model.Card;
import ge.ufc.restapi.model.CardHolder;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/registration")
public interface RegistrationServiceI {
  @POST
  @Path("/cardholder")
  @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  @Produces(MediaType.TEXT_PLAIN)
  Response registrarCardHolder(CardHolder cardHolder);

  @POST
  @Path("/card")
  @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  @Produces(MediaType.TEXT_PLAIN)
  Response registrarCard(Card card);

}
