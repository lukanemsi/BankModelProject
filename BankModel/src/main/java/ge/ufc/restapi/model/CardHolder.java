package ge.ufc.restapi.model;

import org.codehaus.jackson.annotate.JsonProperty;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "CardHolder")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "firstName", "lastName", "email" })
public class CardHolder {
  @XmlElement
  @JsonProperty("id")
  private int id;
  @XmlElement
  @JsonProperty("fistName")
  private String firstName;
  @XmlElement
  @JsonProperty("lastName")
  private String lastName;
  @XmlElement
  @JsonProperty("email")
  private String email;

  public CardHolder() {}

  public CardHolder(int id, String first_name, String last_name, String email) {
    this.id = id;
    this.firstName = first_name;
    this.lastName = last_name;
    this.email = email;
  }

  public int getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CardHolder that = (CardHolder) o;
    return id == that.id;
  }

  @Override
  public int hashCode() {
    return Integer.hashCode(id);
  }
}