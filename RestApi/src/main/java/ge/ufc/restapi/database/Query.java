package ge.ufc.restapi.database;

public enum Query {
  CHANGE_BALANCE("SELECT CHANGE_BALANCE(?,?)"),
  TRANSACTION("SELECT TRANSACTION(?,?,?)"),
  BALANCES("SELECT GET_BALANCES(?)"),
  SELECT_TRANSACTION_HISTORY("SELECT * FROM transaction_history WHERE cardholder_id = ?"),
  SELECT_ALL_CARDS("SELECT * FROM CARD_INFO"),
  SELECT_ALL_CARDHOLDERS("SELECT * FROM CARDHOLDER"),
  INSERT_CARD("INSERT INTO CARD_INFO VALUES(?, ?, ?, ?, ?)"),
  INSERT_CARDHOLDER("INSERT INTO CARDHOLDER VALUES(?, ?, ?, ?)");
  Query(String q) {
    this.q = q;
  }

  private final String q;

  public String getQ() {
    return q;
  }
}
