package ge.ufc.restapi.requestresponsemodels;

import java.sql.Date;

public class TransactionHistory {
  public int transactionId;
  public long cardNumber;
  public int amount;
  public Date transactionTime;

  public TransactionHistory(int transactionId, long cardNumber, int amount, Date transactionTime) {
    this.transactionId = transactionId;
    this.cardNumber = cardNumber;
    this.amount = amount;
    this.transactionTime = transactionTime;
  }
}
