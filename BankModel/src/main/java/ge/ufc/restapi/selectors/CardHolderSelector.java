package ge.ufc.restapi.selectors;

import ge.ufc.restapi.database.Database;
import ge.ufc.restapi.database.Query;
import ge.ufc.restapi.model.CardHolder;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class CardHolderSelector implements Selector<CardHolder> {
  private static final Logger logger = Logger.getLogger(CardHolderSelector.class);

  @Override
  public Set<CardHolder> select() {
    Set<CardHolder> set = new HashSet<>();
    try (Connection connection = Database.getInstance().getConnection()) {
      try (PreparedStatement preparedStatement = connection.prepareStatement(Query.SELECT_ALL_CARDHOLDERS.getQ())) {
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
          while (resultSet.next()) {
            CardHolder cardHolder = new CardHolder(resultSet.getInt("id"), resultSet.getString("first_name"),
                resultSet.getString("last_name"), resultSet.getString("email"));
            set.add(cardHolder);
          }
        }
      }
    } catch (SQLException e) {
      logger.error(e);
    }
    return set;
  }
}


