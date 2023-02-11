package ge.ufc.restapi.services;

import ge.ufc.restapi.database.Database;
import ge.ufc.restapi.database.Query;
import ge.ufc.restapi.exceptions.ValidatorException;
import ge.ufc.restapi.model.Card;
import ge.ufc.restapi.model.CardHolder;
import ge.ufc.restapi.selectors.CardHolderSelector;
import ge.ufc.restapi.selectors.CardSelector;
import ge.ufc.restapi.utils.Validator;
import org.apache.log4j.Logger;

import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;

public class RegistrationServiceImpl implements RegistrationServiceI {
  private static final Validator validator = new Validator();
  private static final Logger logger = Logger.getLogger(RegistrationServiceImpl.class);
  private final Set<Card> cardSet;
  private final Set<CardHolder> cardHolderSet;

  public RegistrationServiceImpl() {
    cardSet = new CardSelector().select();
    cardHolderSet = new CardHolderSelector().select();
  }

  @Override
  public Response registrarCardHolder(CardHolder cardHolder) {
    try {
      validator.validate(cardHolder);
    } catch (ValidatorException e) {
      logger.error(e);
      return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }
    if (cardHolderSet.contains(cardHolder)) return Response.status(Response.Status.NOT_ACCEPTABLE).entity("already exists!").build();

    try (Connection connection = Database.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(Query.INSERT_CARDHOLDER.getQ())) {
      preparedStatement.setInt(1, cardHolder.getId());
      preparedStatement.setString(2, cardHolder.getFirstName());
      preparedStatement.setString(3, cardHolder.getLastName());
      preparedStatement.setString(4, cardHolder.getEmail());
      preparedStatement.execute();
    } catch (SQLException e) {
      logger.error(e);
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
    }
    cardHolderSet.add(cardHolder);
    return Response.status(Response.Status.CREATED).build();
  }

  @Override
  public Response registrarCard(Card card) {
    try {
      validator.validate(card);
    } catch (ValidatorException e) {
      logger.error(e);
      return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }
    if (cardSet.contains(card)) return Response.status(Response.Status.NOT_ACCEPTABLE).entity("already exists!").build();
    if (cardHolderSet.stream().noneMatch(c -> c.getId() == card.getCardHolderId()))
      return Response.status(Response.Status.CONFLICT).entity("card owner not found").build();

    try (Connection connection = Database.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(Query.INSERT_CARD.getQ())) {
      preparedStatement.setLong(1, card.getCardNumber());
      preparedStatement.setInt(2, card.getCCV());
      preparedStatement.setDate(3, card.getExpDate());
      preparedStatement.setDouble(4, card.getBalance());
      preparedStatement.setInt(5, card.getCardHolderId());
      preparedStatement.execute();
    } catch (SQLException e) {
      logger.error(e);
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
    }
    cardSet.add(card);
    return Response.status(Response.Status.CREATED).build();
  }
}