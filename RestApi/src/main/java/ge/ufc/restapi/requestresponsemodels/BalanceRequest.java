package ge.ufc.restapi.requestresponsemodels;

import org.codehaus.jackson.annotate.JsonProperty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "request")
@XmlType(propOrder = { "cardNumber", "amount" })
public class BalanceRequest {
  @JsonProperty("cardNumber")
  private long cardNumber;
  @JsonProperty("amount")
  private int amount;

  public BalanceRequest(long cardNumber, int amount) {
    this.cardNumber = cardNumber;
    this.amount = amount;
  }

  public BalanceRequest() {}

  public long getCardNumber() {
    return cardNumber;
  }

  public int getAmount() {
    return amount;
  }

  @XmlElement(name = "cardNumber")
  public void setCardNumber(long cardNumber) {
    this.cardNumber = cardNumber;
  }

  @XmlElement(name = "amount")
  public void setAmount(int amount) {
    this.amount = amount;
  }
}
