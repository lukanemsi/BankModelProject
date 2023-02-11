package ge.ufc.restapi.selectors;

import ge.ufc.restapi.database.Database;
import ge.ufc.restapi.database.Query;
import ge.ufc.restapi.model.Card;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class CardSelector implements Selector<Card> {
  private static final Logger logger = Logger.getLogger(CardSelector.class);

  @Override
  public Set<Card> select() {
    Set<Card> set = new HashSet<>();
    try (Connection connection = Database.getInstance().getConnection()) {
      try (PreparedStatement preparedStatement = connection.prepareStatement(Query.SELECT_ALL_CARDS.getQ())) {
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
          while (resultSet.next()) {
            Card card = new Card(resultSet.getLong("card_number"), resultSet.getInt("ccv"), resultSet.getDate("expDate"),
                resultSet.getInt("cardholder_id"));
            set.add(card);
          }
        }
      }
    } catch (SQLException e) {
      logger.error(e);
    }
    return set;
  }
}
