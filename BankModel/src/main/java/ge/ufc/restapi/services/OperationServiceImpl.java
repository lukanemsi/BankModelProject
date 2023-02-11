package ge.ufc.restapi.services;

import ge.ufc.restapi.database.Database;
import ge.ufc.restapi.database.Query;
import ge.ufc.restapi.model.Card;
import ge.ufc.restapi.model.CardHolder;
import ge.ufc.restapi.requestresponsemodels.BalanceRequest;
import ge.ufc.restapi.requestresponsemodels.TransactionHistory;
import ge.ufc.restapi.selectors.CardHolderSelector;
import ge.ufc.restapi.selectors.CardSelector;
import org.apache.log4j.Logger;

import javax.ws.rs.core.Response;
import java.sql.*;
import java.time.LocalTime;
import java.util.*;

public class OperationServiceImpl implements OperationServiceI {
  private static final Logger logger = Logger.getLogger(OperationServiceImpl.class);
  private final Set<Card> cardSet;
  private final Set<CardHolder> cardHolderSet;

  public OperationServiceImpl()
  {
    cardSet = new CardSelector().select();
    cardHolderSet = new CardHolderSelector().select();
  }

  public Response fillBalance(BalanceRequest request) {
    if(cardSet.stream().noneMatch(x -> x.getCardNumber() == request.getCardNumber()))
      return Response.status(Response.Status.NOT_FOUND).entity("No such person card").build();
    long cardNumber = request.getCardNumber();
    logger.info(LocalTime.now() + " Filling balance for card: " + cardNumber);
    try (Connection connection = Database.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(Query.CHANGE_BALANCE.getQ())) {
      preparedStatement.setLong(1, cardNumber);
      preparedStatement.setInt(2, request.getAmount());
      String done = "balance filled by: " + request.getAmount();
      logger.info(done.substring(0, 16) + " " + LocalTime.now());
      preparedStatement.execute();
      return Response.status(Response.Status.OK).entity(done).build();
    } catch (SQLException e) {
      logger.error(e);
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
    }
  }

  @Override
  public Response transaction(BalanceRequest request, long cardNumber) {
    if(cardSet.stream().noneMatch(x -> x.getCardNumber() == request.getCardNumber()) || cardSet.stream().noneMatch(x -> x.getCardNumber() == cardNumber))
      return Response.status(Response.Status.NOT_FOUND).entity("No such person card").build();

    logger.info(LocalTime.now() + " Transaction started for card " + request.getCardNumber());
    try (Connection connection = Database.getInstance().getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(Query.TRANSACTION.getQ())) {
      preparedStatement.setLong(1, request.getCardNumber());
      preparedStatement.setLong(2, cardNumber);
      preparedStatement.setInt(3, request.getAmount());
      preparedStatement.execute();
      logger.info(LocalTime.now() + " Transaction ended!");
    } catch (SQLException e) {
      logger.error(e);
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
    }
    return Response.status(Response.Status.OK).build();
  }

  @Override
  public Response balance(int personId) {
    if(cardHolderSet.stream().noneMatch(x -> x.getId() == personId))
      return Response.status(Response.Status.NOT_FOUND).entity("No such person found").build();
    logger.info(LocalTime.now() + " Check balance by person" + personId);
    HashMap<Long, Long> map = new HashMap<>();
    try (Connection connection = Database.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(Query.BALANCES.getQ())) {
      preparedStatement.setInt(1, personId);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        while (resultSet.next()) {
          String[] values = resultSet.getObject(1).toString().split(",");
          String cardNumber = values[0].substring(1);
          String amount = values[1].substring(0, values[1].length() - 1);
          map.put(Long.parseLong(cardNumber), Long.parseLong(amount));
        }
      }
    } catch (SQLException e) {
      logger.error(e);
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
    }

    return Response.status(Response.Status.OK).entity(Map.of("All",map, "total:",map.values().stream().reduce(Long::sum).orElse(0L))).build();
  }

  @Override
  public Response transactionHistory(int personId) {
    if(cardHolderSet.stream().noneMatch(x -> x.getId() == personId))
      return Response.status(Response.Status.NOT_FOUND).entity("No such person found").build();

    List<TransactionHistory> histories = new ArrayList<>();
    try (Connection connection = Database.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(Query.SELECT_TRANSACTION_HISTORY.getQ())) {
      preparedStatement.setInt(1, personId);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        while (resultSet.next()) {
          TransactionHistory history = new TransactionHistory(resultSet.getInt("transaction_id"), resultSet.getLong("card_number"),
              resultSet.getInt("amount"), resultSet.getDate("transaction_confirmed"));
          histories.add(history);
        }
      }
    } catch (SQLException e) {
      logger.error(e);
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
    }
    return Response.status(Response.Status.OK).entity(histories).build();
  }

}

