package ge.ufc.restapi.model;

import org.codehaus.jackson.annotate.JsonProperty;

import javax.xml.bind.annotation.*;
import java.sql.Date;

@XmlRootElement(name = "Card")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "cardNumber", "CCV", "expDate", "balance", "cardHolderId" })

public class Card {

  @XmlElement
  @JsonProperty("cardNumber")
  private long cardNumber;
  @XmlElement
  @JsonProperty("CCV")
  private int CCV;
  @XmlElement
  @JsonProperty("expDate")
  private Date expDate;
  @XmlElement
  @JsonProperty("balance")
  private double balance;
  @XmlElement
  @JsonProperty("cardHolderId")
  private int cardHolderId;

  public Card() {}

  public Card(long cardNumber, int CCV, Date expDate, int cardHolderId) {
    this.cardNumber = cardNumber;
    this.CCV = CCV;
    this.expDate = expDate;
    this.cardHolderId = cardHolderId;
  }

  public long getCardNumber() {
    return cardNumber;
  }

  public int getCCV() {
    return CCV;
  }

  public Date getExpDate() {
    return expDate;
  }

  public double getBalance() {
    return balance;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Card card = (Card) o;
    return cardNumber == card.cardNumber && CCV == card.CCV && expDate.toLocalDate().equals(card.expDate.toLocalDate());
  }

  @Override
  public int hashCode() {
    return Integer.hashCode(CCV) + Long.hashCode(cardNumber);
  }

  public int getCardHolderId() {
    return cardHolderId;
  }

}
